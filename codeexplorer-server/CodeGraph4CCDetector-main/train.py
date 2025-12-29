from flask import Flask, request, Response, jsonify, stream_with_context
import subprocess
import requests
import datetime
from flask_cors import CORS
from threading import Thread, Lock

app = Flask(__name__)
CORS(app)

# 全局任务字典和线程锁
tasks = {}
tasks_lock = Lock()

# 获取训练参数
def fetch_train_params(train_id):
    url = f"http://47.99.87.94:8989/train/getTrainById/{train_id}"
    try:
        response = requests.get(url, timeout=5)  # 添加超时时间
        if response.status_code == 200:
            return response.json()
        else:
            app.logger.error(f"获取训练参数失败，状态码: {response.status_code}, 返回信息: {response.text}")
            return None
    except Exception as e:
        app.logger.error(f"获取训练参数时出错: {str(e)}")
        return None

# 保存模型信息
def save_model_info(name, model, summary, createTime, updateTime, remark):
    url = "http://47.99.87.94:8989/modelList/addModel"
    data = {
        "name": name,
        "model": model,
        "summary": summary,
        "createTime": createTime,
        "updateTime": updateTime,
        "remark": remark
    }
    try:
        response = requests.post(url, json=data, timeout=5)  # 添加超时时间
        if response.status_code == 200:
            app.logger.info("模型信息成功保存")
        else:
            app.logger.error(f"保存模型信息失败，状态码: {response.status_code}, 返回信息: {response.text}")
    except Exception as e:
        app.logger.error(f"请求保存模型接口时出错: {str(e)}")

# 更新训练信息
def update_train_info(train_id, params, console_output, state):
    url = "http://47.99.87.94:8989/train/updateTrain"
    data = {
        "id": train_id,
        "name": params.get('name', '训练'),
        "model": params.get('model', '代码克隆检测'),
        "dataset_id": params.get('dataset_id', 1),
        "dataset": params.get('dataset', '默认数据集'),
        "state": state,
        "gpu": params.get('gpu', '0'),
        "type": params.get('type', "代码克隆检测"),
        "param": str(params),
        "time": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
        "result": console_output,
        "remark": params.get('remark', ''),
        "summary": params.get('summary', ''),
        "epochs": params.get('epochs', ''),
        "batch_size": params.get('batch_size', ''),
        "lr": params.get('lr', ''),
        "lr_decay_factor": params.get('lr_decay_factor', ''),
        "weight_decay": params.get('weight_decay', ''),
        "lr_decay_step_size": params.get('lr_decay_step_size', ''),
        "hidden": params.get('hidden', ''),
        "num_classes": params.get('num_classes', ''),
        "nheads": params.get('nheads', ''),
        "dropout": params.get('dropout', ''),
        "alpha": params.get('alpha', '')
    }
    try:
        response = requests.put(url, json=data, timeout=5)  # 添加超时时间
        if response.status_code == 200:
            app.logger.info(f"训练信息更新成功，ID: {train_id}")
        else:
            app.logger.error(f"更新训练信息失败，状态码: {response.status_code}, 返回信息: {response.text}")
    except Exception as e:
        app.logger.error(f"请求更新训练接口时出错: {str(e)}")

# 执行训练任务
def execute_training(train_id, train_params):
    global tasks
    command = [
        'python', 'main.py',
        '--epochs', str(train_params.get('epochs', 100)),
        '--batch_size', str(train_params.get('batch_size', 64)),
        '--lr', str(train_params.get('lr', 0.0001)),
        '--lr_decay_factor', str(train_params.get('lr_decay_factor', 0.5)),
        '--weight_decay', str(train_params.get('weight_decay', 0.0005)),
        '--lr_decay_step_size', str(train_params.get('lr_decay_step_size', 50)),
        '--hidden', str(train_params.get('hidden', 16)),
        '--num_classes', str(train_params.get('num_classes', 2)),
        '--nheads', str(train_params.get('nheads', 16)),
        '--dropout', str(train_params.get('dropout', 0.1)),
        '--alpha', str(train_params.get('alpha', 0.2)),
        '--gpu', str(train_params.get('gpu', 0)),
        '--dataset', str(train_params.get('dataset', "googlejam4_src")),
    ]

    process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
    with tasks_lock:
        tasks[train_id] = process

    console_output = ""
    try:
        for line in iter(process.stdout.readline, ''):
            console_output += line
            update_train_info(train_id, train_params, console_output, state=0)  # 0 表示运行中
        process.stdout.close()
        process.wait()
    finally:
        with tasks_lock:
            tasks.pop(train_id, None)

    update_train_info(train_id, train_params, console_output, state=1)  # 1 表示已完成

@app.route('/train', methods=['POST'])
def train_model():
    params = request.json
    train_id = params.get('id')
    if not train_id:
        return jsonify({'error': '未提供训练 ID'}), 400

    train_params = fetch_train_params(train_id)
    if not train_params or not train_params.get("data"):
        return jsonify({'error': '获取训练参数失败'}), 500
    train_params = train_params["data"]

    current_time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    save_model_info(
        name=train_params.get('name', '训练'),
        model=train_params.get('model', '代码克隆检测'),
        summary=train_params.get('remark', '模型描述'),
        createTime=current_time,
        updateTime=current_time,
        remark=train_id
    )

    Thread(target=execute_training, args=(train_id, train_params)).start()

    return jsonify({'code': '任务已启动', 'train_id': train_id}), 200

@app.route('/stop_train', methods=['POST'])
def pause_task():
    params = request.json
    train_id = params.get('id')
    if not train_id:
        return jsonify({'error': '未提供训练 ID'}), 400

    train_params = fetch_train_params(train_id)
    if not train_params or not train_params.get("data"):
        return jsonify({'error': '获取训练参数失败'}), 500
    train_params = train_params["data"]

    with tasks_lock:
        process = tasks.get(train_id)

    if process:
        process.terminate()
        with tasks_lock:
            tasks.pop(train_id, None)
        update_train_info(train_id, train_params, train_params['result'], state=1) 
        return jsonify({'code': '任务已暂停', 'train_id': train_id}), 200
    else:
        return jsonify({'error': '任务未找到或已完成'}), 404

if __name__ == '__main__':
    app.run(host='10.1.21.160', port=8997)
