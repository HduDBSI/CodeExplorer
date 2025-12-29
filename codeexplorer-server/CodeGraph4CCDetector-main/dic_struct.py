from flask import Flask, jsonify, request
import os
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

def build_file_structure(path):
    # 对目录和文件进行数字排序
    def sorted_naturally(files):
        return sorted(files, key=lambda x: int(os.path.basename(x)) if os.path.basename(x).isdigit() else os.path.basename(x))
    
    file_info = {
        "name": os.path.basename(path),
        "isDirectory": os.path.isdir(path),
    }
    if os.path.isdir(path):
        # 列出文件和目录，并按自然顺序排序
        children = sorted_naturally([os.path.join(path, child) for child in os.listdir(path)])
        files = [build_file_structure(child) for child in children]
        file_info["files"] = files
    else:
        file_info["path"] = path
    return file_info

@app.route('/directoryStructure', methods=['POST'])
def directory_structure():
    data = request.get_json()
    if not data or 'directoryPath' not in data:
        return jsonify({"error": "Missing parameter 'directoryPath'"}), 400

    directory_path = data['directoryPath']
    if not os.path.exists(directory_path):
        return jsonify({"error": f"Directory '{directory_path}' does not exist"}), 400

    file_structure = build_file_structure(directory_path)
    return jsonify(file_structure)

if __name__ == '__main__':
    # 启动 Flask 应用
    app.run(host='10.1.21.160', port=9005, debug=True)
