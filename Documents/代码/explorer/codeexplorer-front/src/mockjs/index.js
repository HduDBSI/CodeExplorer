import Mock from 'mockjs';
import axios from 'axios';
// 设置 Mock 请求延迟
Mock.setup({
    timeout: '5000' // 模拟 5 秒延迟
  });

// 定义 Mock 数据
Mock.mock('/api/user', 'get', {
    code: 200,
    data: {
      name: '@name',
      age: '@integer(18, 60)',
      email: '@EMAIL'
    },
    message: 'Success'
  });