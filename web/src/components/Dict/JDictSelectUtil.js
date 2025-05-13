/**
 * 字典 util
 */

import { ajaxGetDictItems } from '@/api/admin/dict/info/index'

/**
 * 获取字典数组
 * @param dictCode 字典Code
 * @return List<Map>
 */
export async function initDictOptions (dictCode) {
  if (!dictCode) {
    return '字典Code不能为空!'
  }
  // 获取字典数组
  const res = await ajaxGetDictItems(dictCode)
  return res
}

/**
 * 字典值替换文本通用方法
 * @param dictOptions  字典数组
 * @param text  字典值
 * @return String
 */
export function filterDictText (dictOptions, text) {
  if (text != null && dictOptions instanceof Array) {
    const result = []
    // 允许多个逗号分隔
    const splitText = text.toString().trim().split(',')
    for (const txt of splitText) {
      let dictText = txt
      for (const dictItem of dictOptions) {
        if (txt === dictItem.value.toString()) {
          dictText = dictItem.text
          break
        }
      }
      result.push(dictText)
    }
    return result.join(',')
  }
  return text
}
