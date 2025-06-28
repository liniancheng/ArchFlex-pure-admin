import { http } from "@/utils/http";

type Result = {
  success: boolean;
  data: Array<any>;
};

/** 接口返回的路由信息 */
interface RouteItem {
  id?: string;
  appId?: string;
  name: string;
  routeName?: string;
  type?: string;
  path: string;
  icon?: string | null;
  component?: string | null;
  sort?: number;
  children?: RouteItem[] | null;
  parentId?: string;
  hideFlag?: string;
  aliveFlag?: string;
  createTime?: string;
  modifyTime?: string;
  meta?: AnyObject;
}

/** 处理后的路由信息 */
interface FormattedRoute {
  path: string;
  name: string;
  component?: string;
  meta: {
    title: string;
    icon?: string;
    [key: string]: any; // 其他可能的meta属性
  };
  children?: FormattedRoute[];
}

const formatRoutes = (data: RouteItem[]): FormattedRoute[] => {
  if (!data || data?.length <= 0) return [];
  return data.map(item => {
    const formattedItem: FormattedRoute = {
      path: item.path,
      name: item.name,
      component: item.component,
      meta: {
        title: item.routeName,
        ...item
      },
      children: formatRoutes(item.children)
    };
    return formattedItem;
  });
};

/** 处理后端路由接口返回信息 */
const formatRoutesData = (data: string) => {
  if (!data) return data;
  const parseData: Result = JSON.parse(data);
  parseData.data = formatRoutes(parseData.data);
  return parseData;
};

export const getAsyncRoutes = () => {
  return http.request<Result>("get", "/admin/resource/menu/tree", {
    transformResponse: formatRoutesData
  });
};
