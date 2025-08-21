/**
 * @Author: liniancheng
 * @Date: 2025-08-07 21:20:27
 * @Description: 摄像头相关接口定义
 * @version: 1.0
 * @Copyright: all
 * @LastEditors: liniancheng
 * @LastEditTime: 2025-08-07 22:38:27
 */
import { http } from "@/utils/http";
import axios from "axios";

/* 停止录制 */
export function stopCamera() {
    return http.get("/detect/camera/stopCamera");
}

/* 获取摄像头检测记录 */
export function getRecords(params?: AnyObject) {
    const url = axios.getUri({ url: "/detect/cameraRecords", params: params });
    return http.get<AnyObject, FormData>(url);
}

/* 获取单条摄像头检测记录 */
export function getRecord(id: string) {
    return http.get<AnyObject, FormData>(`/detect/cameraRecords/${id}`);
}

/* 删除摄像头检测记录 */
export function deleteRecord(id: string) {
    return http.delete<AxiosResult, string>(`/detect/cameraRecords/${id}`);
}
