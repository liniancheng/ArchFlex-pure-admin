import { dayjs, getRandomIntBetween } from "./utils";

const pests = ["烟虫甲虫", "烟虫粉螟", "苍蝇"];
const users = ["车间一", "车间二", "车间三", "车间四", "车间五"];

/** 检测概览 */
const barChartData = [
    {
        name: '图像检测',
        color: "#41b6ff",
        data: [
            [2101, 5288, 4239, 4962, 6752, 5208, 7450],
            [2216, 1148, 1255, 1788, 4821, 1973, 4379]
        ]
    },
    {
        name: '视频检测',
        color: "#e86033ce",
        data: [
            [2101, 3280, 4400, 4962, 5752, 6889, 7600],
            [2116, 3148, 3255, 3788, 4821, 4970, 5390]
        ]
    },
];

const pieChartData = [
    { value: 100, name: "车间一" },
    { value: 200, name: "车间二" },
    { value: 300, name: "车间三" },
    { value: 400, name: "车间四" },
    { value: 500, name: "车间五" }
];

const radarChartData = [
    {
        value: [0.68, 0.36, 0.49, 0.61, 0.75],
        name: "图像"
    },
    {
        value: [0.78, 0.46, 0.79, 0.81, 0.94],
        name: "视频"
    }
];

const lineChartDataX = [
    "3月16日",
    "3月17日",
    "3月18日",
    "3月19日",
    "3月20日",
    "3月21日",
    "3月22日",
    "3月23日",
    "3月24日",
    "3月25日",
];

const lineChartDataY = [820, 932, 901, 934, 1290, 1330, 1320, 1416, 1444, 1560];

/** 数据统计 */
const tableData = Array.from({ length: 30 }).map((_, index) => {
    return {
        id: index + 1,
        user: `操作工${getRandomIntBetween(1, 10)}`,
        recognitionWeight: ['tobacco_pest.pt', 'invasive_species.pt'][getRandomIntBetween(0, 1)],
        minThreshold: getRandomIntBetween(2, 10) / 10,
        aiAssistant: "不使用AI",
        date: dayjs().subtract(index, "day").format("YYYY-MM-DD hh:mm:ss")
    };
});


export {
    pests,
    users,
    barChartData,
    pieChartData,
    radarChartData,
    lineChartDataX,
    lineChartDataY,
    tableData
};
