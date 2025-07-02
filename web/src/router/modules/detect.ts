import { $t } from "@/plugins/i18n";
import { detect } from "@/router/enums";

export default {
    path: "/detect",
    redirect: "/detect/tobacco/index",
    meta: {
        icon: "ri/ubuntu-fill",
        title: $t("menus.detect.tobacco.self"),
        rank: detect
    },
    children: [
        {
            path: "/detect/tobacco/index",
            name: "Tobacco",
            component: () => import("@/views/detect/tobacco/index/index.vue"),
            meta: {
                title: $t("menus.detect.tobacco.index")
            }
        },
        {
            path: "/detect/tobacco/batch",
            name: "Batch",
            component: () => import("@/views/detect/tobacco/batch/index.vue"),
            meta: {
                title: $t("menus.detect.tobacco.batch")
            }
        },
        {
            path: "/detect/tobacco/camera",
            name: "Camera",
            component: () => import("@/views/detect/tobacco/camera/index.vue"),
            meta: {
                title: $t("menus.detect.tobacco.camera")
            }
        },
        {
            path: "/detect/tobacco/cameraRecord",
            name: "CameraRecord",
            component: () => import("@/views/detect/tobacco/cameraRecord/index.vue"),
            meta: {
                title: $t("menus.detect.tobacco.cameraRecord")
            }
        },
        {
            path: "/detect/tobacco/image",
            name: "Image",
            component: () => import("@/views/detect/tobacco/image/index.vue"),
            meta: {
                title: $t("menus.detect.tobacco.image")
            }
        },
        {
            path: "/detect/tobacco/imageRecord",
            name: "ImageRecord",
            component: () => import("@/views/detect/tobacco/imageRecord/index.vue"),
            meta: {
                title: $t("menus.detect.tobacco.imageRecord")
            }
        },
        {
            path: "/components/video",
            name: "Video",
            component: () => import("@/views/detect/tobacco/video/index.vue"),
            meta: {
                title: $t("menus.detect.tobacco.video")
            }
        },
        {
            path: "/detect/tobacco/videoRecord",
            name: "VideoRecord",
            component: () => import("@/views/detect/tobacco/videoRecord/index.vue"),
            meta: {
                title: $t("menus.detect.tobacco.videoRecord")
            }
        }
    ]
} satisfies RouteConfigsTable;
