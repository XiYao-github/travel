import request from '@/api/request'

export function list(query) {
    return request({
        url: '/travel/order/list',
        method: 'get',
        params: query
    })
}

export function page(query) {
    return request({
        url: '/travel/order/page',
        method: 'get',
        params: query
    })
}

export function info(id) {
    return request({
        url: '/travel/order/info',
        method: 'get',
        params: {id}
    })
}

export function insert(dto) {
    return request({
        url: '/travel/order',
        method: 'post',
        data: dto
    })
}

export function update(dto) {
    return request({
        url: '/travel/order',
        method: 'put',
        data: dto
    })
}

export function deleteBatch(list) {
    return request({
        url: '/travel/order',
        method: 'delete',
        data: list
    })
}

export default request;