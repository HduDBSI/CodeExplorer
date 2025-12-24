import axios from 'axios';

// axios实例
const schoolInstance1 = axios.create({
    baseURL: `xxx`, 
    timeout: 1000000,
    headers: {
        'Content-Type': 'application/json',
    }
});

const schoolInstance2 = axios.create({
    baseURL: `xxx`, 
    timeout: 1000000,
    headers: {
        'Content-Type': 'application/json',
    }
});

// 训练地址和训练任务停止
const schoolInstance3 = axios.create({
    baseURL: `xxx`, 
    timeout: 1000000,
    headers: {
        'Content-Type': 'application/json',
    }
});

//返回目录接口
const schoolInstance4 = axios.create({
    baseURL: `xxx`, 
    timeout: 1000000,
    headers: {
        'Content-Type': 'application/json',
    }
});

//返回克隆结果
const schoolInstance5 = axios.create({
    baseURL: `xxx`, 
    timeout: 1000000,
    headers: {
        'Content-Type': 'application/json',
    }
});

// 预处理启动接口和预处理任务停止
const schoolInstance6 = axios.create({
    baseURL: `xxx`, 
    timeout: 1000000,
    headers: {
        'Content-Type': 'application/json',
    }
});

//预处理启动接口
const schoolInstance7 = axios.create({
    baseURL: `xxx`, 
    timeout: 1000000,
    headers: {
        'Content-Type': 'application/json',
    }
});

//停止test接口
const schoolInstance8 = axios.create({
    baseURL: `xxx`, 
    timeout: 1000000,
    headers: {
        'Content-Type': 'application/json',
    }
});

// 公司服务器的axios实例
const companyInstance = axios.create({
    // 公司服务器地址
    baseURL: `http://192.168.30.28:8989`,
    timeout: 1000000,
    headers: {
        'Content-Type': 'application/json',
    }
});

// 为两个实例都添加请求拦截器
function addInterceptors(instance) {
    instance.interceptors.request.use(
        config => {
            const token = localStorage.getItem('token');
            if (token) {
                config.headers.Authorization = `Bearer ${token}`;
            }
            return config;
        },
        error => {
            return Promise.reject(error);
        }
    );

    instance.interceptors.response.use(
        response => {
            return response.data;
        },
        error => {
            if (error.response) {
                switch (error.response.status) {
                    case 401:
                        console.log('未授权，跳转到登录页面');
                        break;
                    case 403:
                        console.log('拒绝访问');
                        break;
                    case 404:
                        console.log('资源不存在');
                        break;
                    case 500:
                        console.log('服务器错误');
                        break;
                    default:
                        console.log(`其他错误: ${error.response.status}`);
                }
            } else if (error.request) {
                console.log('请求超时或网络错误');
            } else {
                console.log('请求错误', error.message);
            }
            return Promise.reject(error);
        }
    );
}

// 添加拦截器
addInterceptors(schoolInstance1);
addInterceptors(schoolInstance2);
addInterceptors(schoolInstance3);
addInterceptors(schoolInstance4);
addInterceptors(schoolInstance5);
addInterceptors(schoolInstance6);
addInterceptors(schoolInstance7);
addInterceptors(schoolInstance8);
addInterceptors(companyInstance);

export { schoolInstance2, schoolInstance1, schoolInstance3,schoolInstance4,schoolInstance5,schoolInstance6,schoolInstance7,schoolInstance8,companyInstance };
