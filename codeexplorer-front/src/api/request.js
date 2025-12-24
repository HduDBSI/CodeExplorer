//默认为公司，需要学校的接口则传入instance = schoolInstance
import { schoolInstance1, companyInstance } from '../axios';

// 封装 GET 请求
export function get(url, params = {}, instance = companyInstance) {
    return instance.get(url, { params });
}

// 封装 POST 请求
export function post(url, data = {}, instance = companyInstance) {
    if (Array.isArray(data)) {
        data = JSON.stringify(data);
    }
    return instance.post(url, data);
}

// 封装 PUT 请求
export function put(url, data = {}, instance = companyInstance) {
    return instance.put(url, data);
}

// 封装 DELETE 请求
export function del(url, instance = companyInstance) {
    return instance.delete(url);
}

// import axios from '../axios';

// // 封装 GET 请求
// export function get(url, params = {}) {
//     return axios.get(url, { params });
// }

// // 封装 POST 请求
// export function post(url, data = {}) {
//     return axios.post(url, data);
// }

// // 封装 PUT 请求
// export function put(url, data = {}) {
//     return axios.put(url, data);
// }

// // 封装 DELETE 请求
// export function del(url) {
//     return axios.delete(url);
// }

