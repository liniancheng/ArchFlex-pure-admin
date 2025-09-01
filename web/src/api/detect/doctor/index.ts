/**
 * @Author: liniancheng
 * @Date: 2025-09-01 21:20:27
 * @Description: ai-chat相关接口定义
 * @version: 1.0
 * @Copyright: all
 * @LastEditors: liniancheng
 * @LastEditTime: 2025-09-01 22:28:27
 */
import { http } from "@/utils/http";
import { useUserStoreHook } from "@/store/modules/user";
import { storeToRefs } from "pinia";

const stores = useUserStoreHook();
const { username } = storeToRefs(stores);

/* 获取记录 */
export function getRecords(): Promise<AnyObject> {
    return http.get(`/ollama/getRecords?who=${username.value}`);
}

/* 发送消息 */
export function sendMessage(bo: {currentUserName: string, message: string}) {
    return http.post(`/ollama/ai/v3/doctor/stream`, bo);
}
