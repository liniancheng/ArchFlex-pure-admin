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

export interface DetectImage {
    originalImage: string,  // 原始图片路径
    model: string,  // 模型
    recognitionWeight: string,  // 识别权重
    minThreshold: number, // 最小阈值
    aiAssistant: string, // AI助手使用情况
}

/* 视频上传 */
export function upload(file: File) {
    const formData = new FormData();
    formData.append("file", file);
    return http.post<AnyObject, FormData>(
        "/detect/files/upload",
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    );
}
