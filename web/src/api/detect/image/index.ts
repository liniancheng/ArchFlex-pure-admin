/**
 * @Author: liniancheng
 * @Date: 2025-07-02 21:20:27
 * @Description: 图片相关接口定义
 * @version: 1.0
 * @Copyright: all
 * @LastEditors: liniancheng
 * @LastEditTime: 2025-08-26 22:28:27
 */
import { http } from "@/utils/http";
import axios from "axios";

export interface DetectImage {
    username: string,  // 用户名
    inputImg: string,  // 原始图片路径
    model: string,  // 模型
    weight: string,  // 识别权重
    conf: number,  // 最小阈值
    kind: string,  // 检测种类
    aiAssistant: string,  // AI助手使用情况
    token: string,
}

export interface DetectResult {
    label: string,
    labelCounts: {
        [key: string]: {
            num: number,
        },
    },  // 检测结果
    allTime: string,  // 总用时
    outImg: string,  // 结果图片路径
    startTime: string,
    endTime: string,
}

/* 图片检测 */
export function detect(data: DetectImage) {
    return http.post<AnyObject, DetectImage>(
        "/detect/image/flaskDetect",
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
