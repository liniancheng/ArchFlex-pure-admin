/**
 * @Author: liniancheng
 * @Date: 2025-07-02 21:20:27
 * @Description: 图片相关接口定义
 * @version: 1.0
 * @Copyright: all
 * @LastEditors: liniancheng
 * @LastEditTime: 2025-07-02 22:38:27
 */
import { http } from "@/utils/http";

// 查询执行计划
export function fetch(params) {
    return http.post("/crossvalidation/cvchkplan/page", params);
}

// 删除执行计划
export function deletePlan(planId) {
    return http.delete(`/crossvalidation/cvchkplan/${planId}`);
}

// 新增
export function save(params) {
    return http.post("/crossvalidation/cvchkplan", params);
}

// 上传
export function upload(file: File) {
    const formData = new FormData();
    formData.append("file", file);
    return http.post<AnyObject, FormData>(
        "/detect/image/upload",
         formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    );
}
