import os
import subprocess
import requests
from flask import Flask, request, jsonify
from flask_cors import CORS
import time
import threading

app = Flask(__name__)
CORS(app)

# 全局变量控制暂停状态
pause_flag = threading.Event()
pause_flag.set()  # 初始时，允许执行

def add_preprocessing(name, sourcePath, remark):
    url = "http://47.99.87.94:8989/dataset/addDataset"
    payload = {
        # "dataset": sourcePath + "_" + name,
        "dataset": sourcePath,
        "state": "1",
        "time": time.strftime('%Y-%m-%d %H:%M:%S', time.localtime()),
        "result": "预处理完成",
        "remark": remark
    }
    response = requests.post(url, json=payload)
    if response.status_code == 200:
        print("Preprocessing dataset successfully added.")
    else:
        print(f"Failed to add preprocessing set: {response.text}")

def update_preprocessing(id, name, sourcePath, state, result, remark):
    url = "http://47.99.87.94:8989/preprocessing/updatePreprocessing"
    payload = {
        "id": id,
        "name": name,
        "dataset": sourcePath,
        "state": state,
        "time": time.strftime('%Y-%m-%d %H:%M:%S', time.localtime()),
        "result": result,
        "remark": remark
    }
    response = requests.put(url, json=payload)
    if response.status_code == 200:
        print("Preprocessing information successfully updated.")
    else:
        print(f"Failed to update preprocessing info: {response.text}")

def execute_command_with_update(cmd, id, name, sourcePath, remark, result_output):
    process = subprocess.Popen(
        cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True
    )
    try:
        for line in process.stdout:
            # 检查暂停标志
            pause_flag.wait()  # 如果暂停标志未设置，任务会在此处暂停
            result_output += line
            # 实时更新
            update_preprocessing(id, name, sourcePath, 0, result_output, remark)
        process.wait()  # 等待命令执行完成
    except Exception as e:
        result_output += f"Error during command execution: {e}\n"
        update_preprocessing(id, name, sourcePath, 0, result_output, remark)
    finally:
        if process.returncode != 0:
            raise subprocess.CalledProcessError(process.returncode, cmd)
    return result_output  # 返回更新后的结果

def execute_commands(id, name, sourcePath, remark):
    
    dataset_cfg_path = os.path.join(".", sourcePath+"cfg")
    dataset_pdg_path = os.path.join(".", sourcePath+"pdg")
    result_output = ""

    if not os.path.exists(dataset_cfg_path):
        os.makedirs(os.path.join(dataset_cfg_path, "corpus"))
    if not os.path.exists(dataset_pdg_path):
        os.makedirs(os.path.join(dataset_pdg_path, "corpus"))
     
    try:
        cmds = [
            (f"echo 生成cfg的语料库 && "
             f"/usr/lib/jvm/java-8-openjdk-amd64/bin/java -cp /tmp/cp_2flr62meze80lbvij6fnukzym.jar "
             f"yoshikihigo.tinypdg.graphviz.createCFGCodeJson ./{sourcePath}"),
            (f"echo 生成pdg的语料库 && "
             f"/usr/lib/jvm/java-8-openjdk-amd64/bin/java -cp /tmp/cp_2flr62meze80lbvij6fnukzym.jar "
             f"yoshikihigo.tinypdg.graphviz.createPDGCodeJson ./{sourcePath}"),
            (f"echo 生成cfg、pdg的模型 && "
             f"/usr/lib/jvm/java-8-openjdk-amd64/bin/java -cp /tmp/cp_2flr62meze80lbvij6fnukzym.jar "
             f"yoshikihigo.tinypdg.graphviz.LearnTest"),
             (f"echo 生成预处理后的cfg数据集 && "
             f"/usr/lib/jvm/java-8-openjdk-amd64/bin/java -cp /tmp/cp_2flr62meze80lbvij6fnukzym.jar "
             f"yoshikihigo.tinypdg.graphviz.createCFGCodeJson ./{sourcePath}"),
             (f"echo 生成预处理后的pdg数据集 && "
             f"/usr/lib/jvm/java-8-openjdk-amd64/bin/java -cp /tmp/cp_2flr62meze80lbvij6fnukzym.jar "
             f"yoshikihigo.tinypdg.graphviz.createPDGCodeJson ./{sourcePath}")
        ]

        for cmd in cmds:
            result_output = execute_command_with_update(cmd, id, name, sourcePath, remark, result_output)

        result_output += "Commands executed successfully.\n"
        update_preprocessing(id, name, sourcePath, 1, result_output, remark)

    except subprocess.CalledProcessError as e:
        result_output += f"Error occurred: {e}\n"
        update_preprocessing(id, name, sourcePath, 0, result_output, remark)

@app.route('/process', methods=['POST'])
def execute():
    data = request.get_json()
    id = data.get("id")

    try:
        response = requests.get(f"http://47.99.87.94:8989/preprocessing/getPreprocessingById/{id}")
        if response.status_code != 200:
            return jsonify({'error': 'Failed to fetch test parameters', 'details': response.text}), 500
        params = response.json()["data"]
    except Exception as e:
        return jsonify({'error': f'Error fetching test parameters: {str(e)}'}), 500

    source_path = params.get("dataset")
    name = params.get("name")
    remark = params.get("remark")

    if not id:
        return jsonify({"error": "id parameters are required"}), 400

    update_preprocessing(id, name, source_path, 0, "", remark)

    thread = threading.Thread(target=execute_commands, args=(id, name, source_path, remark))
    thread.start()
    add_preprocessing(name, source_path, remark)

    return jsonify({"result": "Processing started successfully"})

@app.route('/stop_process', methods=['POST'])
def pause_task():
    data = request.get_json()
    id = data.get("id")
    if not id:
        return jsonify({"error": "id parameter is required"}), 400

    # 更新任务状态为1 (暂停)
    try:
        response = requests.get(f"http://47.99.87.94:8989/preprocessing/getPreprocessingById/{id}")
        if response.status_code != 200:
            return jsonify({'error': 'Failed to fetch test parameters', 'details': response.text}), 500
        params = response.json()["data"]
        name = params.get("name")
        source_path = params.get("dataset")
        remark = params.get("remark")
        result = params.get("result")

        update_preprocessing(id, name, source_path, 1, result, remark)
    except Exception as e:
        return jsonify({'error': f'Error updating task state: {str(e)}'}), 500

    # 清除暂停标志，暂停任务
    pause_flag.clear()
    return jsonify({"result": "Process task paused successfully"})

if __name__ == '__main__':
    app.run(host='10.1.21.160', port=9002, debug=True)
