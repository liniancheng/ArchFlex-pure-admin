import request from '@/utils/request'

export function fetch (params) {
    return request.get('/detect/imageRecord/page', {
        params: params
    })
}
