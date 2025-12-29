from flask import Flask, Response, request
import os
import json
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

# 读取 clonepair.txt 文件并返回匹配的代码克隆对
def get_clone_pairs(file_path):
    clone_pairs = []  # 用于存储所有合法的克隆对
    with open(file_path, 'r') as file:
        for line in file:
            parts = line.strip().split()
            if len(parts) == 3:
                code1, code2, label = parts
                if label == '1':  # 仅处理标记为 1 的克隆对
                    # 读取每个代码文件的内容
                    code1_content = read_code_file(code1)
                    code2_content = read_code_file(code2)

                    # 如果找到对应代码文件的内容，则将其加入结果列表
                    if code1_content and code2_content:
                        clone_pair = {
                            "code1_path": code1,
                            "code1_content": code1_content,
                            "code2_path": code2,
                            "code2_content": code2_content,
                            "label": int(label)
                        }
                        clone_pairs.append(clone_pair)  # 添加到列表
    return clone_pairs

# 读取代码文件的内容
def read_code_file(file_path):
    try:
        with open(file_path, 'r') as file:
            return file.read()
    except FileNotFoundError:
        return None  # 文件找不到，返回 None

# 定义POST请求的API，接受传入的id
@app.route('/clone_pairs', methods=['POST'])
def post_clone_pairs():
    # 获取 POST 请求的 JSON 数据
    data = request.get_json()

    # 从请求数据中获取 id
    test_id = data.get('id')

    if not test_id:
        return "ID is required", 400

    # 根据传入的 id 生成对应的文件路径
    file_path = f"{test_id}.txt"
    
    if not os.path.exists(file_path):
        return f"File {file_path} not found", 404

    # 获取克隆对列表
    clone_pairs = get_clone_pairs(file_path)

    # 返回 JSON 数组响应
    return Response(json.dumps(clone_pairs, ensure_ascii=False, indent=2), content_type='application/json; charset=utf-8')

if __name__ == '__main__':
    app.run(host='10.1.21.160', port=8999, debug=True)
