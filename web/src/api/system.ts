import { http } from "@/utils/http";

type Result<T = Array<any>> = {
    success: boolean;
    data?: T;
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
export interface ApiMenuItem {
    aliveFlag?: string; // 后端傻逼，数字要写成字符串
    appId?: string;
    authFlag?: string;
    buttonPermission?: string;
    componentPath?: string;
    createTime?: string;
    delFlag?: string;
    hideFlag?: string;
    httpMethod?: string;
    menuColor?: string;
    menuIcon?: string;
    menuId: string;
    menuName: string;
    menuPath: string;
    menuRouteName: string;
    menuSort?: number;
    menuType: string;
    menuUrl?: string;
    modifyTime?: string;
    parentId?: string;
}
/** 菜单项信息 */
interface MenuItem {
    id?: string;
    appId?: string;
    title: string;
    name: string;
    menuType: number;
    path: string;
    icon?: string;
    component?: string;
    rank?: number;
    children?: MenuItem[];
    parentId?: string;
    auths?: string;
    deleteTag?: boolean;
    hiddenTag?: boolean;
    keepAlive?: boolean;
    createTime?: string;
    modifyTime?: string;
    buttonPermission?: string;
    httpMethod?: string;
    color?: string;
    url?: string;
    // 以下为后端暂无属性
    higherMenuOptions?: Record<string, unknown>[];
    redirect?: string;
    extraIcon?: string;
    enterTransition?: string;
    leaveTransition?: string;
    activePath?: string;
    frameSrc?: string;
    frameLoading?: boolean;
    fixedTag?: boolean;
    showLink?: boolean;
    showParent?: boolean;
}

/** 接口返回的角色信息 */
export interface ApiRoleItem {
    roleId: string;
    roleCode: string;
    roleName: string;
    createTime?: string;
    modifyTime?: string;
    delFlag?: string;
    appId?: string;
    menuIds?: string[];
    ableFlag?: string;
    remark?: string;
}
/** 角色信息 */
export interface RoleItem {
    id?: string;
    name: string;
    code: string;
    status?: string;
    remark?: string;
    menuIds?: string[];
    createTime?: string;
}

/** 接口返回的日志信息 */
export interface ApiLogItem {
    logId: string;
    actionName: string;
    createBy: string;
    moduleName: string;
    params: string;
    remoteAddr: string;
    userAgent: string;
    logStatus: string;
    createTime: string;
    methodName: string;
    serviceId: string;
    operTime: string;
}
/** 日志信息 */
export interface LogItem {
    id: number;
    logId: string;
    actionName: string;
    address: string;
    browser: string;
    username: string;
    module: string;
    summary: string;
    ip: string;
    system: string;
    status: number;
    operatingTime: string;
    methodName: string;
    serviceId: string;
    operTime: string;
}

export const formatMenu = (data: ApiMenuItem[]): MenuItem[] => {
    if (!data || data?.length <= 0) return [];
    return data.map(item => {
        return {
            id: item.menuId,
            appId: item.appId,
            title: item.menuName,
            name: item.menuRouteName,
            menuType: Number(item.menuType),
            delFlag: item.delFlag,
            path: item.menuPath,
            icon: item.menuIcon,
            component: item.componentPath,
            rank: Number(item.menuSort),
            parentId: item.parentId,
            auths: item.authFlag,
            hiddenTag: Number(item.hideFlag) === 1,
            keepAlive: Number(item.aliveFlag) === 1,
            createTime: item.createTime,
            modifyTime: item.modifyTime,
            buttonPermission: item.buttonPermission,
            httpMethod: item.httpMethod,
            color: item.menuColor,
            url: item.menuUrl
        };
    });
};

export const unFormatMenu = (item: MenuItem): ApiMenuItem => {
    return {
        aliveFlag: item?.keepAlive ? "1" : "0",
        appId: item?.appId,
        authFlag: item?.auths.toString(),
        buttonPermission: item?.buttonPermission,
        componentPath: item?.component,
        createTime: item?.createTime,
        delFlag: item?.deleteTag ? "1" : "0",
        hideFlag: item?.hiddenTag ? "1" : "0",
        httpMethod: item?.httpMethod,
        menuColor: item?.color,
        menuIcon: item?.icon,
        menuId: item?.id,
        menuName: item?.title,
        menuPath: item?.path,
        menuRouteName: item?.name,
        menuSort: item?.rank,
        menuType: item?.menuType.toString(),
        menuUrl: item?.url,
        modifyTime: item?.modifyTime,
        parentId: item?.parentId ?? "-1"
    };
};

export const formatRole = (data: ApiRoleItem[]): RoleItem[] => {
    if (!data || data?.length <= 0) return [];
    return data.map(item => {
        return {
            id: item.roleId,
            name: item.roleName,
            code: item.roleCode,
            status: item.ableFlag,
            remark: item.remark,
            createTime: item.createTime
        };
    });
};

export const unFormatRole = (data: RoleItem): ApiRoleItem => {
    return {
        roleId: data.code.toLowerCase(),
        roleName: data.name,
        roleCode: data.code.toUpperCase(),
        ableFlag: data.status,
        remark: data.remark,
        createTime: data.createTime,
        menuIds: data.menuIds
    };
};

export const formatLog = (data: ApiLogItem[]): LogItem[] => {
    if (!data || data?.length <= 0) return [];
    return data.map((item, index) => {
        return {
            id: index + 1,
            logId: item.logId,
            actionName: item.actionName,
            address: item.remoteAddr,
            browser: item.userAgent,
            username: item.createBy,
            module: item.moduleName,
            summary: item.params,
            ip: item.remoteAddr,
            system: item.userAgent,
            status: Number(item.logStatus),
            operatingTime: item.createTime,
            methodName: item.methodName,
            serviceId: item.serviceId,
            operTime: item.operTime
        };
    });
};

export const unFormatLog = (data: RoleItem): ApiRoleItem => {
    return {
        roleId: data.code.toLowerCase(),
        roleName: data.name,
        roleCode: data.code.toUpperCase(),
        ableFlag: data.status,
        remark: data.remark,
        createTime: data.createTime,
        menuIds: data.menuIds
    };
};

/** 处理后端路由接口返回信息 */
const formatMenuData = (data: string) => {
    if (!data) return data;
    const parseData: Result = JSON.parse(data);
    parseData.data = formatMenu(parseData.data as any[]);
    return parseData;
};
const formatRoleData = (data: string) => {
    if (!data) return data;
    const parseData: Result<AnyObject> = JSON.parse(data);
    const { records, ...t } = parseData.data
    parseData.data = {
        ...t,
        list: formatRole(records)
    };
    return parseData;
};
const formatLogData = (data: string) => {
    if (!data) return data;
    const parseData: Result<AnyObject> = JSON.parse(data);
    const { records, ...t } = parseData.data
    parseData.data = {
        ...t,
        list: formatLog(records)
    };
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
    return http.request<ResultTable>(
        "get",
        "/admin/role/page",
        { data },
        {
            transformResponse: formatRoleData
        }
    );
};
/** 修改系统管理-角色 */
export const createRole = (data?: RoleItem) => {
    return http.request<ResultTable>("post", `/admin/role`, {
        data: unFormatRole(data)
    });
};
/** 修改系统管理-角色 */
export const modifyRole = (data?: RoleItem) => {
    return http.request<ResultTable>("put", `/admin/role`, {
        data: unFormatRole(data)
    });
};
/** 删除系统管理-角色 */
export const deleteRole = (id: string) => {
    return http.request<ResultTable>("delete", `/admin/role/${id}`);
};

/** 获取系统管理-菜单管理列表 */
export const getMenuList = (data?: object) => {
    return http.request<Result>(
        "get",
        "/admin/resource/menu/list",
        { data },
        {
            transformResponse: formatMenuData
        }
    );
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
    return http.request<ResultTable>(
        "get",
        "/admin/log/page",
        { data },
        {
            transformResponse: formatLogData
        }
    );
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
    return http.request<Result>("get", "/admin/resource/menu/simpleList");
};

/** 获取角色管理-权限-菜单权限-根据角色 id 查对应菜单 */
export const getRoleMenuIds = (id: string) => {
    return http.request<Result>("get", `/admin/roleMenuRel/ids/${id}`);
};
