import { http } from "@/utils/http";
import qs from "qs";

export type UserResult = {
  /** 头像 */
  avatar: string;
  /** 用户名 */
  username: string;
  /** 昵称 */
  nickname: string;
  /** 当前登录用户的角色 */
  roles: Array<string>;
  /** 按钮级别权限 */
  permissions: Array<string>;
  /** `token` */
  accessToken: string;
  /** 用于调用刷新`accessToken`的接口时所需的`token` */
  refreshToken: string;
  /** `accessToken`的过期时间（剩余秒数） */
  expires: number;
};

export type RefreshTokenResult = {
  success: boolean;
  data: {
    /** `token` */
    accessToken: string;
    /** 用于调用刷新`accessToken`的接口时所需的`token` */
    refreshToken: string;
    /** `accessToken`的过期时间（剩余秒数） */
    expires: number;
  };
};

export type UserInfo = {
  /** 头像 */
  avatar: string;
  /** 用户名 */
  username: string;
  /** 昵称 */
  nickname: string;
  /** 邮箱 */
  email: string;
  /** 联系电话 */
  phone: string;
  /** 简介 */
  description: string;
};

export type UserInfoResult = {
  success: boolean;
  data: UserInfo;
};

type ResultTable = {
  success: boolean;
  data?: {
    /** 列表数据 */
    list: Array<any>;
    /** 总条目数 */
    total?: number;
    /** 每页显示条目个数 */
    pageSize?: number;
    /** 当前页数 */
    currentPage?: number;
  };
};

/** 处理后端登录接口返回信息 */
const formatLoginData = (data: string) => {
  if (!data) return data;
  const parseData = JSON.parse(data);
  const { access_token, refresh_token, expires_in, login_name, ...rest } =
    parseData;
  return {
    ...rest,
    accessToken: access_token,
    refreshToken: refresh_token,
    expires: expires_in,
    username: login_name
  };
};

/** 登录 */
export const getLogin = (data?: object) => {
  return http.request<UserResult>("post", "/auth/oauth/token", {
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    data: qs.stringify(data),
    transformResponse: [
      responseData => {
        return formatLoginData(responseData);
      }
    ]
  });
};

/** 更新登录时间 */
export const updateLoginTime = () => {
  return http.post("/admin/user/updateLoginTime");
};

/** 刷新`token` */
export const refreshTokenApi = (data?: object) => {
  return http.request<RefreshTokenResult>("post", "/refresh-token", { data });
};

/** 账户设置-个人信息 */
export const getMine = (data?: object) => {
  return http.request<UserInfoResult>("get", "/mine", { data });
};

/** 账户设置-个人安全日志 */
export const getMineLogs = (data?: object) => {
  return http.request<ResultTable>("get", "/mine-logs", { data });
};
