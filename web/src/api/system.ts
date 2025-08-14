import { http } from "@/utils/http";

type Result = {
    success: boolean;
    data?: Array<any>;
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

/** 接口返回的菜单信息 */
interface ApiMenuItem {
    aliveFlag: number;
    appId: string;
    authFlag: number;
    buttonPermission: string;
    componentPath: string;
    createTime: string;
    delFlag: number;
    hideFlag: number;
    httpMethod: string;
    menuColor: string;
    menuIcon: string;
    menuId: string;
    menuName: string;
    menuPath: string;
    menuRouteName: string;
    menuSort: number;
    menuType: number;
    menuUrl: string;
    modifyTime: string;
    parentId: string;
}
/** 菜单项信息 */
interface MenuItem {
    id: string;
    appId: string;
    title: string;
    name: string;
    menuType: number;
    path: string;
    icon: string;
    component: string;
    rank: number;
    children?: MenuItem[];
    parentId: string;
    auths: number;
    hiddenTag: number;
    keepAlive: number;
    createTime: string;
    modifyTime: string;
}

const formatMenu = (data: ApiMenuItem[]): MenuItem[] => {
    if (!data || data?.length <= 0) return [];
    return data.map(item => {
        return {
            id: item.menuId,
            appId: item.appId,
            title: item.menuName,
            name: item.menuRouteName,
            menuType: Number(item.menuType),
            path: item.menuPath,
            icon: item.menuIcon,
            component: item.componentPath,
            rank: Number(item.menuSort),
            parentId: item.parentId,
            auths: item.authFlag,
            hiddenTag: Number(item.hideFlag),
            keepAlive: Number(item.aliveFlag),
            createTime: item.createTime,
            modifyTime: item.modifyTime,
        }
    });
};

/** 处理后端路由接口返回信息 */
const formatMenuData = (data: string) => {
    if (!data) return data;
    const parseData: Result = JSON.parse(data);
    parseData.data = formatMenu(parseData.data);
    return parseData;
};

/** 获取系统管理-用户管理列表 */
export const getUserList = (data?: object) => {
    return http.request<ResultTable>("post", "/user", { data });
};

/** 系统管理-用户管理-获取所有角色列表 */
export const getAllRoleList = () => {
    return http.request<Result>("get", "/list-all-role");
};

/** 系统管理-用户管理-根据userId，获取对应角色id列表（userId：用户id） */
export const getRoleIds = (data?: object) => {
    return http.request<Result>("post", "/list-role-ids", { data });
};

/** 获取系统管理-角色管理列表 */
export const getRoleList = (data?: object) => {
    return http.request<ResultTable>("post", "/role", { data });
};

/** 获取系统管理-菜单管理列表 */
export const getMenuList = (data?: object) => {
    return http.request<Result>("get", "/admin/resource/menu/list", { data }, {
        transformResponse: formatMenuData
    });
};

/** 获取系统管理-部门管理列表 */
export const getDeptList = (data?: object) => {
    return http.request<Result>("post", "/dept", { data });
};

/** 获取系统监控-在线用户列表 */
export const getOnlineLogsList = (data?: object) => {
    return http.request<ResultTable>("post", "/online-logs", { data });
};

/** 获取系统监控-登录日志列表 */
export const getLoginLogsList = (data?: object) => {
    return http.request<ResultTable>("post", "/login-logs", { data });
};

/** 获取系统监控-操作日志列表 */
export const getOperationLogsList = (data?: object) => {
    return http.request<ResultTable>("post", "/operation-logs", { data });
};

/** 获取系统监控-系统日志列表 */
export const getSystemLogsList = (data?: object) => {
    return http.request<ResultTable>("post", "/system-logs", { data });
};

/** 获取系统监控-系统日志-根据 id 查日志详情 */
export const getSystemLogsDetail = (data?: object) => {
    return http.request<Result>("post", "/system-logs-detail", { data });
};

/** 获取角色管理-权限-菜单权限 */
export const getRoleMenu = (data?: object) => {
    return http.request<Result>("post", "/role-menu", { data });
};

/** 获取角色管理-权限-菜单权限-根据角色 id 查对应菜单 */
export const getRoleMenuIds = (data?: object) => {
    return http.request<Result>("post", "/role-menu-ids", { data });
};
