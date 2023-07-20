import request from '@/api/request'

export function list(query) {
    return request({
        url: '/travel/scenic/list',
        method: 'get',
        params: query
    })
}

export function page(query) {
    return request({
        url: '/travel/scenic/page',
        method: 'get',
        params: query
    })
}

export function info(id) {
    return request({
        url: '/travel/scenic/info',
        method: 'get',
        params: {id}
    })
}

export function insert(dto) {
    return request({
        url: '/travel/scenic',
        method: 'post',
        data: dto
    })
}

export function update(dto) {
    return request({
        url: '/travel/scenic',
        method: 'put',
        data: dto
    })
}

export function deleteBatch(list) {
    return request({
        url: '/travel/scenic',
        method: 'delete',
        data: list
    })
}

export default request;