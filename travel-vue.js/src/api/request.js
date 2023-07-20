import axios from 'axios'

const request = axios.create({
    // baseURL: 'http://localhost:8080',
    withCredentials: true
});

request.interceptors.request.use(
    function (config) {
        // 添加统一的 headers
        config.headers = {
            token: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiMTgyNTg3MzMyODEiLCJpYXQiOjE2ODIyNDIxNTAsImV4cCI6MTY4MjI0OTM1MH0.cXahy6KEaSM7MBlzS7JFbqU-RfA2hduNwGNEtpyThDA'
        }
        return config;
    },
    function (error) {
        return Promise.reject(error);
    }
);

request.interceptors.response.use(
    function (response) {
        // 2xx 范围内走这里
        return response;
    },
    function (error) {
        if (error.response?.status === 400) {
            console.log('请求参数不正确');
            return Promise.resolve(400);
        } else if (error.response?.status === 401) {
            console.log('跳转至登录页面');
            return Promise.resolve(401);
        } else if (error.response?.status === 404) {
            console.log('资源未找到');
            return Promise.resolve(404);
        }
        // 超出 2xx, 比如 4xx, 5xx 走这里
        return Promise.reject(error);
    }
);

export default request;