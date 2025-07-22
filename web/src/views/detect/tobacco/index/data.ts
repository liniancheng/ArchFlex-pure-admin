import { dayjs, getRandomIntBetween } from "./utils";

const pests = ["虫一", "虫二", "虫三", "虫四", "虫五", "虫六", "虫七"];
const users = ["用户一", "用户二", "用户三", "用户四", "用户五"];

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
    { value: 100, name: "用户一" },
    { value: 200, name: "用户二" },
    { value: 300, name: "用户三" },
    { value: 400, name: "用户四" },
    { value: 500, name: "用户五" }
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

const lineChartData = [820, 932, 901, 934, 1290, 1330, 1320];

/** 数据统计 */
const tableData = Array.from({ length: 30 }).map((_, index) => {
    return {
        id: index + 1,
        user: getRandomIntBetween(13500, 19999),
        recognitionWeight: getRandomIntBetween(12600, 16999),
        minThreshold: getRandomIntBetween(0, 100) / 100,
        aiAssistant: getRandomIntBetween(0, 1),
        date: dayjs().subtract(index, "day").format("YYYY-MM-DD hh:mm:ss")
    };
});


export { pests, users, barChartData, pieChartData, radarChartData, lineChartData, tableData };
