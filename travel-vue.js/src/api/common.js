import request from '@/api/request'

export function download(name) {
    return request({
        url: '/travel/image/download',
        method: 'get',
        params: {name}
    })
}

export function upload(file) {
    return request({
        url: '/travel/image/upload',
        method: 'post',
        headers: {
            "Content-Type": "multipart/form-data"
        },
        data: file
    })
}

export default request;

export const imageURL = '/travel/image/upload';