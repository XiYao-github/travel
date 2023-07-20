import request from '@/api/request'

export function list(query) {
    return request({
        url: '/travel/admin/list',
        method: 'get',
        params: query
    })
}

export function page(query) {
    return request({
        url: '/travel/admin/page',
        method: 'get',
        params: query
    })
}

export function info(id) {
    return request({
        url: '/travel/admin/info',
        method: 'get',
        params: {id}
    })
}

export function insert(dto) {
    return request({
        url: '/travel/admin',
        method: 'post',
        data: dto
    })
}

export function update(dto) {
    return request({
        url: '/travel/admin',
        method: 'put',
        data: dto
    })
}

export function deleteBatch(list) {
    return request({
        url: '/travel/admin',
        method: 'delete',
        data: list
    })
}

export function getUser(query) {
    return request({
        url: '/travel/admin/getUser',
        method: 'get',
        params: query
    })
}

export default request;