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
import { ElMessage } from "element-plus";

export interface DetectImage {
    originalImage: string,  // 原始图片路径
    model: string,  // 模型
    recognitionWeight: string,  // 识别权重
    minThreshold: number, // 最小阈值
    aiAssistant: string, // AI助手使用情况
}

/* 图片上传 */
export function getWeightList() {
    // TODO: get weight list
    // http.get("/detect/image/weight");
}

/* 图片检测 */
export function detect(data: DetectImage) {
    return http.post<AnyObject, DetectImage>(
        "/detect/image/detect",
        data,
    );
}
