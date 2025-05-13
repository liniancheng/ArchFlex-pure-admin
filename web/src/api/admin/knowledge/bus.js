import request from '@/utils/request'

export function getByKnowId (knowId) {
    return request.get('/admin/know/' + knowId)
}
export function getByTpye (id) {
    return request.get('/admin/know/getInfoByType/' + id)
}
