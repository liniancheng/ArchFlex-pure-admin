/**
 * @Author: liniancheng
 * @Date: 2025-08-05 21:20:27
 * @Description: 视频相关接口定义
 * @version: 1.0
 * @Copyright: all
 * @LastEditors: liniancheng
 * @LastEditTime: 2025-08-05 22:38:27
 */
import { http } from "@/utils/http";
import axios from "axios";

/* 视频上传 */
export function upload(file: File) {
    const formData = new FormData();
    formData.append("file", file);
    return http.post<AnyObject, FormData>("/detect/files/upload", formData, {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    });
}

/* 获取视频检测记录 */
export function getRecords(params?: AnyObject) {
    const url = axios.getUri({ url: "/detect/videoRecords", params: params });
    return http.get<AnyObject, FormData>(url);
}

/* 获取单条视频检测记录 */
export function getRecord(id: string) {
    return http.get<AnyObject, FormData>(`/detect/videoRecords/${id}`);
}

/* 删除视频检测记录 */
export function deleteRecord(id: string) {
    return http.delete<AxiosResult, string>(`/detect/videoRecords/${id}`);
}
