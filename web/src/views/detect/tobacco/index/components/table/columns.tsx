import { tableData } from "../../data";
import { delay } from "@pureadmin/utils";
import { ref, onMounted, reactive } from "vue";
import type { PaginationProps } from "@pureadmin/table";
import Empty from "./empty.svg?component";

export function useColumns() {
    const dataList = ref([]);
    const loading = ref(true);
    const columns: TableColumnList = [
        {
            sortable: true,
            label: "序号",
            prop: "id",

        },
        {
            sortable: true,
            label: "用户名",
            prop: "user",
        },
        {
            sortable: true,
            label: "识别权重",
            prop: "recognitionWeight"
        },
        {
            sortable: true,
            label: "最小阈值",
            prop: "minThreshold"
        },
        {
            sortable: true,
            label: "AI助手",
            prop: "aiAssistant",
            filterMultiple: false,
            filterClassName: "pure-table-filter",
            filters: [
                { text: "使用", value: "use" },
                { text: "不使用", value: "notUse" }
            ],
            filterMethod: (value, { aiAssistant }) => {
                return value === "use"
                    ? aiAssistant >= 1
                    : aiAssistant < 1;
            }
        },
        {
            sortable: true,
            label: "时间",
            prop: "date"
        },
    ];

    /** 分页配置 */
    const pagination = reactive<PaginationProps>({
        pageSize: 7,
        currentPage: 1,
        layout: "prev, pager, next",
        total: 0,
        align: "center"
    });

    function onCurrentChange(_page: number) {
        // TODO
        loading.value = true;
        delay(300).then(() => {
            loading.value = false;
        });
    }

    onMounted(() => {
        dataList.value = tableData;
        pagination.total = dataList.value.length;
        loading.value = false;
    });

    return {
        Empty,
        loading,
        columns,
        dataList,
        pagination,
        onCurrentChange
    };
}
