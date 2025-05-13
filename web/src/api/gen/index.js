import request from '@/utils/request'

export function code (params) {
    return request({
      url: '/gen/code',
      method: 'post',
      data: params,
      responseType: 'blob',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8'
      }
    })
}

export function fetch (params) {
    return request.request('/gen/table/page', {
        params: params
    })
}
