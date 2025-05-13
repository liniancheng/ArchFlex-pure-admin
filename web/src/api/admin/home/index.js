import request from '@/utils/request'

export function getHomePage () {
    return request('/admin/base/home/page')
}

export function download (id) {
    return request('/admin/annoAttach/download/' + id, {
        responseType: 'blob',
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        }
      })
}
