from flask import Flask, jsonify
import subprocess
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

def get_gpu_utilization():
    try:
        # 执行 nvidia-smi 命令，查询 GPU 利用率和内存利用率
        result = subprocess.run(['nvidia-smi', '--query-gpu=index,name,utilization.gpu,memory.used,memory.total', 
                                 '--format=csv,nounits,noheader'], 
                                 stdout=subprocess.PIPE, text=True)
        
        # 解析 nvidia-smi 的输出结果
        gpu_utilization = []
        for line in result.stdout.splitlines():
            index, name, gpu_util, mem_used, mem_total = line.split(', ')
            gpu_utilization.append({
                "GPU_index": int(index),
                "name": name,
                "gpu_utilization_percent": int(gpu_util),
                "memory_utilization_percent": str(round(int(mem_used)/int(mem_total) * 100, 2)) + "%"
            })
        return gpu_utilization
    except Exception as e:
        return {"error": str(e)}

@app.route('/gpu_usage', methods=['GET'])
def gpu_usage():
    gpu_utilization = get_gpu_utilization()
    return jsonify(gpu_utilization)

if __name__ == '__main__':
    # 启动 Flask 应用
    app.run(host='10.1.21.160', port=9000, debug=True)
