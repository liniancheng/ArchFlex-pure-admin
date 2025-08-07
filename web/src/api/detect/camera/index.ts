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

/* 视频上传 */
export function stopCamera() {
    return http.get("/camera/stopCamera");
}
