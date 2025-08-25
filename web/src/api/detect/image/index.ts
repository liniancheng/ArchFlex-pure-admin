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
import axios from "axios";

export interface DetectImage {
    originalImage: string,  // 原始图片路径
    model: string,  // 模型
    recognitionWeight: string,  // 识别权重
    minThreshold: number, // 最小阈值
    aiAssistant: string, // AI助手使用情况
}

export interface DetectResult {
    detectionResults: {
        bowl: number,
        broccoli: number,
        hotDog: number,
    },  // 检测结果
    resultFileName: string,  // 结果图片路径
}

/* 图片上传 */
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

/* 图片检测 */
export function detect(data: DetectImage) {
    return http.post<AnyObject, DetectImage>(
        "/detect/image/detect",
        data,
    );
}

/* 获取图像检测记录 */
export function getRecords(params?: AnyObject) {
    const url = axios.getUri({ url: "/detect/imgRecords", params: params });
    return http.get<AnyObject, FormData>(url);
}

/* 获取单条图像检测记录 */
export function getRecord(id: string) {
    return http.get<AnyObject, FormData>(`/detect/imgRecords/${id}`);
}

/* 删除图像检测记录 */
export function deleteRecord(id: string) {
    return http.delete<AxiosResult, string>(`/detect/imgRecords/${id}`);
}
