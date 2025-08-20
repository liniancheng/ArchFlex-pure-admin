/**
 * views system
 */
// role
declare interface RowRoleType {
	roleName: string;
	roleSign: string;
	describe: string;
	sort: number;
	status: boolean;
	createTime: string;
}

interface SysRoleTableType extends TableType {
	data: RowRoleType[];
    total: number;
    loading: boolean;
    param: {
        search: string;
        search1: string;
        search3: string;
        search2: string;
        pageNum: number;
        pageSize: number;
    },
}

declare interface SysRoleState {
	tableData: SysRoleTableType;
}
