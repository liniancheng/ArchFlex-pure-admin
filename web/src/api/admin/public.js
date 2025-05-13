import request from '@/utils/request'

export function publicCode () {
  return request.get('/admin/public/index')
}
