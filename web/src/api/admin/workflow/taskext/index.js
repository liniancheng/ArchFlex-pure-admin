import request from '@/utils/request'

export function fetch (params) {
    return request.request('/admin/userTaskExt/page', {
        params: params
    })
}

export function fetchOne (id) {
    return request.get('/admin/userTaskExt/' + id)
}

export function save (params) {
    return request.post('/admin/userTaskExt', params)
}

export function updateById (params) {
    return request.put('/admin/userTaskExt', params)
}

export function removeById (id) {
    return request.delete('/admin/userTaskExt/' + id)
}
