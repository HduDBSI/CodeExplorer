from flask import Flask, request, jsonify
import subprocess
import requests
import datetime
from flask_cors import CORS
from threading import Thread

app = Flask(__name__)
CORS(app)

# 存储任务进程和进度
tasks = {}

# 调用外部API更新运行结果
def update_test_result(test_id, params, partial_output, state=0):
    current_time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    url = "http://47.99.87.94:8989/test/updateTest"
    data = {
        "id": test_id,
        "name": params.get('name', '测试'),
        "model_id": params.get('model_id', 1),
        "model": params.get('model', '代码克隆'),
        "dataset_id": params.get('dataset_id', 6),
        "dataset": params.get('dataset', '默认数据集'),
        "state": state,
        "gpu": params.get('gpu', '0'),
        "type": params.get('type', '代码克隆'),
        "time": current_time,
        "result": partial_output,
        "remark": params.get('remark', ''),
        "epochs": params.get('epochs', ''),
        "test_batch_size": params.get('test_batch_size', ''),
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
        response = requests.put(url, json=data)
        if response.status_code == 200:
            app.logger.info("部分运行结果成功更新到数据库")
        else:
            app.logger.error(f"更新失败，状态码: {response.status_code}, 返回信息: {response.text}")
    except Exception as e:
        app.logger.error(f"请求更新接口时出错: {str(e)}")

# 生成控制台输出并实时返回和保存
def generate_output(command, params, test_id):
    process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.STDOUT, text=True)
    tasks[test_id] = {'process': process, 'progress': 0}  # 初始化任务进度
    all_output = ""
    
    try:
        # 初始化 state 为 0 表示运行中
        update_test_result(test_id, params, partial_output="", state=0)

        for line in iter(process.stdout.readline, ''):
            all_output += line
            # 更新进度逻辑（假设可以根据输出中的某些关键字估算进度）
            if "epoch" in line.lower():
                epoch_number = int(line.split()[1])  # 根据实际输出解析
                total_epochs = params.get('epochs', 100)
                progress = min(100, (epoch_number / total_epochs) * 100)
                tasks[test_id]['progress'] = progress

            # 实时调用外部API更新控制台输出
            update_test_result(test_id, params, partial_output=all_output, state=0)

    except Exception as e:
        all_output += f"发生错误: {str(e)}"
    finally:
        process.stdout.close()
        process.wait()
        # 设置 state 为 1 表示运行完成
        tasks[test_id]['progress'] = 100  # 确保进度为 100%
        update_test_result(test_id, params, partial_output=all_output, state=1)
        # 移除任务进程
        tasks.pop(test_id, None)

# 后台运行测试任务
def run_test_in_background(test_id, params, command):
    generate_output(command, params, test_id)

@app.route('/test', methods=['POST'])
def test_model():
    if request.content_type != 'application/json':
        return jsonify({'error': 'Unsupported Media Type: Content-Type must be application/json'}), 415

    data = request.json
    if not data or 'id' not in data:
        return jsonify({'error': 'ID is required'}), 400

    test_id = data['id']

    try:
        response = requests.get(f"http://47.99.87.94:8989/test/getTestById/{test_id}")
        if response.status_code != 200:
            return jsonify({'error': 'Failed to fetch test parameters', 'details': response.text}), 500
        params = response.json()["data"]
    except Exception as e:
        return jsonify({'error': f'Error fetching test parameters: {str(e)}'}), 500

    command = [
        'python', 'main_test_fast.py',
        '--epochs', str(params.get('epochs', 100)),
        '--test_batch_size', str(params.get('test_batch_size', 15000)),
        '--lr', str(params.get('lr', 0.001)),
        '--lr_decay_factor', str(params.get('lr_decay_factor', 0.5)),
        '--weight_decay', str(params.get('weight_decay', 0.0005)),
        '--lr_decay_step_size', str(params.get('lr_decay_step_size', 50)),
        '--hidden', str(params.get('hidden', 16)),
        '--num_classes', str(params.get('num_classes', 2)),
        '--nheads', str(params.get('nheads', 16)),
        '--dropout', str(params.get('dropout', 0.1)),
        '--alpha', str(params.get('alpha', 0.2)),
        '--model_index', str(params.get('model_id', 5)),
        '--test_id', str(test_id)
    ]

    Thread(target=run_test_in_background, args=(test_id, params, command)).start()
    return jsonify({'code': '任务已启动', 'test_id': test_id}), 200

@app.route('/progress', methods=['POST'])
def get_progress():
    test_id = request.json['id']
    
    if not test_id or test_id not in tasks:
        return jsonify({'error': f'Task with ID {test_id} not found.'}), 404

    progress = tasks[test_id].get('progress', 0)
    return jsonify({'test_id': test_id, 'progress': progress}), 200

@app.route('/stop_test', methods=['POST'])
def stop_test():
    data = request.json
    if not data or 'id' not in data:
        return jsonify({'error': 'ID is required'}), 400

    test_id = data['id']
    process = tasks.get(test_id, {}).get('process')

    if not process:
        return jsonify({'error': f'Task with ID {test_id} not found or already completed.'}), 404

    process.terminate()
    tasks.pop(test_id, None)
    return jsonify({'code': f'Task {test_id} has been stopped successfully.'}), 200

if __name__ == '__main__':
    app.run(host='10.1.21.160', port=8998, debug=True)
