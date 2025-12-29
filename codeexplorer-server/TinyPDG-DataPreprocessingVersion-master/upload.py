from flask import Flask, request, jsonify
import os
import zipfile
import requests
from datetime import datetime
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

UPLOAD_FOLDER = './'
os.makedirs(UPLOAD_FOLDER, exist_ok=True)

def unzip_file(zip_path, extract_to):
    """解压缩文件到指定目录"""
    try:
        with zipfile.ZipFile(zip_path, 'r') as zip_ref:
            zip_ref.extractall(extract_to)
        return True, None
    except Exception as e:
        return False, str(e)

@app.route('/upload', methods=['POST'])
def upload_file():
    if 'file' not in request.files:
        return jsonify({"message": "没有文件上传"}), 400

    file = request.files['file']
    if file.filename == '':
        return jsonify({"message": "未选择文件"}), 400

    # 保存上传的文件
    save_path = os.path.join(UPLOAD_FOLDER, file.filename)
    file.save(save_path)

    # 检查文件是否为zip文件
    if not zipfile.is_zipfile(save_path):
        return jsonify({"message": "上传的文件不是有效的zip文件"}), 400

    # 解压文件
    extract_folder = os.path.splitext(save_path)[0]  # 去掉.zip后缀
    os.makedirs(extract_folder, exist_ok=True)

    success, error = unzip_file(save_path, extract_folder)
    if not success:
        return jsonify({"message": f"解压失败: {error}"}), 500

    # 删除原zip文件
    os.remove(save_path)

    # 调用外部接口保存上传信息
    dataset_name = os.path.basename(extract_folder)
    current_time = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    payload = {
        "dataset": dataset_name,
        "state": -1,
        "time": current_time,
        "result": "未预处理"
    }
    
    try:
        response = requests.post("http://47.99.87.94:8989/dataset/addDataset", json=payload)
        if response.status_code != 200:
            return jsonify({"message": "上传信息保存失败", "error": response.text}), 500
    except Exception as e:
        return jsonify({"message": "调用外部接口失败", "error": str(e)}), 500

    return jsonify({"message": f"文件已成功解压至 {extract_folder}", "dataset": dataset_name})

if __name__ == '__main__':
    app.run(host='10.1.21.160', port=9002, debug=True)
