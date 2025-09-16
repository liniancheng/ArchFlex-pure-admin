<script setup lang="ts">
import "deep-chat";
import { ref, onMounted } from "vue";
import { useUserStoreHook } from "@/store/modules/user";
import { storeToRefs } from "pinia";
import { getRecords, sendMessage } from "@/api/detect/doctor";
import { Signals } from "deep-chat/dist/types/handler";

const stores = useUserStoreHook();
const { username } = storeToRefs(stores);

const chatRef = ref();
const history = ref([]);

function createChatHandle() {
    chatRef.value.stream = true;
    chatRef.value.connect = {
        handler: (body: AnyObject, signals: Signals) => {
            console.error("有消息来了", body);
            if (body.messages[0].role === "user") {
                sendMessage({
                    currentUserName: username.value,
                    message: body.messages[0].text
                });
            }
            try {
                const source = new EventSource(
                    `http://127.0.0.1:8898/sse/connect?userId=${username.value}`
                );
                let isThinking = false;

                source.onopen = response => {
                    console.log("sse open", response);
                    signals.onOpen();
                };

                source.onmessage = message => {
                    signals.onResponse({ text: message.data });
                };

                source.onerror = message => {
                    signals.onResponse({ error: JSON.stringify(message) });
                };

                source.addEventListener("add", function (e) {
                    console.log("add事件...", e.data);
                    const data = e.data;
                    if (data.includes("<think>")) {
                        isThinking = true;
                        const thinkingContent = data.replace("<think>", "");
                        signals.onResponse({
                            text: thinkingContent,
                            custom: {
                                isThinking: true
                            }
                        });
                    } else if (data.includes("</think>")) {
                        isThinking = false;
                        const finalContent = data.replace("</think>", "");
                        signals.onResponse({
                            text: finalContent,
                            custom: {
                                isThinking: false
                            }
                        });
                    } else {
                        signals.onResponse({
                            text: data,
                            custom: {
                                isThinking: isThinking
                            }
                        });
                    }
                });

                source.addEventListener("finish", function (e) {
                    console.log("finish事件...", e.data);
                });

                source.addEventListener(
                    "customEvent",
                    function (e) {
                        console.log(e.lastEventId, e.data);
                    },
                    false
                );

                signals.stopClicked.listener = () => {
                    // logic to stop your stream, such as creating an abortController
                };
            } catch (e) {
                signals.onResponse({ error: "error" });
            }
        }
    };
}

function loadHistory() {
    getRecords().then(res => {
        history.value = res.data.map((item: any) => ({
            id: item.id,
            text: item.content,
            role: item.chatType === "user" ? "user" : "ai",
            time: item.chatTime
        }));
        chatRef.value.scrollToBottom()
    });
}

onMounted(() => {
    createChatHandle();
    loadHistory();
});
</script>

<template>
    <deep-chat
        ref="chatRef"
        style="
            display: block;
            height: 100%;
            width: 100%;
            background: linear-gradient(
                90deg,
                rgb(239 242 247) 0%,
                7.6029%,
                rgb(237 240 249) 15.2057%,
                20.7513%,
                rgb(235 239 248) 26.297%,
                27.6386%,
                rgb(235 239 248) 28.9803%,
                38.2826%,
                rgb(231 237 249) 47.585%,
                48.1216%,
                rgb(230 236 250) 48.6583%,
                53.1306%,
                rgb(228 236 249) 57.6029%,
                61.5385%,
                rgb(227 234 250) 65.4741%,
                68.7835%,
                rgb(222 234 250) 72.093%,
                75.7603%,
                rgb(219 230 248) 79.4275%,
                82.8265%,
                rgb(216 229 248) 86.2254%,
                87.8354%,
                rgb(213 228 249) 89.4454%,
                91.8605%,
                rgb(210 226 249) 94.2755%,
                95.4383%,
                rgb(209 225 248) 96.6011%,
                98.3005%,
                rgb(208 224 247) 100%
            );
            border-color: #e4e4e4;
            border-radius: 10px;
            box-shadow: 0 0 12px rgba(0, 0, 0, 0.12);
        "
        :textInput="{
            styles: {
                container: {
                    borderRadius: '20px',
                    border: 'unset',
                    width: '90%',
                    marginLeft: '-15px',
                    boxShadow:
                        '0px 0.3px 0.9px rgba(0, 0, 0, 0.12), 0px 1.6px 3.6px rgba(0, 0, 0, 0.16)'
                },
                text: {
                    padding: '10px',
                    paddingLeft: '15px',
                    paddingRight: '34px'
                }
            },
            placeholder: { text: '发送消息', style: { color: '#606060' } }
        }"
        :messageStyles="{
            default: {
                shared: {
                    bubble: {
                        backgroundColor: 'unset',
                        marginTop: '10px',
                        marginBottom: '10px',
                        boxShadow:
                            '0px 0.3px 0.9px rgba(0, 0, 0, 0.12), 0px 1.6px 3.6px rgba(0, 0, 0, 0.16)'
                    }
                },
                user: {
                    bubble: {
                        background:
                            'linear-gradient(130deg, #2870EA 20%, #1B4AEF 77.5%)'
                    }
                },
                ai: {
                    bubble: message => ({
                        background: message.isThinking
                            ? 'rgba(200, 200, 200, 0.5)'
                            : 'rgba(255, 255, 255, 0.9)',
                        opacity: message.isThinking ? 0.7 : 1,
                        transition: 'all 0.3s ease'
                    })
                }
            }
        }"
        :submitButtonStyles="{
            position: 'outside-right',
            submit: {
                container: {
                    default: {
                        bottom: '0.8em',
                        borderRadius: '25px',
                        padding: '6px 5px 4px',
                        backgroundColor: 'unset'
                    },
                    hover: { backgroundColor: '#b0deff4f' },
                    click: { backgroundColor: '#b0deffb5' }
                },
                svg: {
                    content:
                        '<?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?> <svg viewBox=&quot;0 0 24 24&quot; xmlns=&quot;http://www.w3.org/2000/svg&quot;><path d=&quot;m21.426 11.095-17-8A.999.999 0 0 0 3.03 4.242L4.969 12 3.03 19.758a.998.998 0 0 0 1.396 1.147l17-8a1 1 0 0 0 0-1.81zM5.481 18.197l.839-3.357L12 12 6.32 9.16l-.839-3.357L18.651 12l-13.17 6.197z&quot;/></svg>',
                    styles: {
                        default: {
                            filter: 'brightness(0) saturate(100%) invert(10%) sepia(86%) saturate(6044%) hue-rotate(205deg) brightness(100%) contrast(100%)'
                        }
                    }
                }
            },
            loading: {
                svg: {
                    styles: {
                        default: {
                            filter: 'brightness(0) saturate(100%) invert(72%) sepia(0%) saturate(3044%) hue-rotate(322deg) brightness(100%) contrast(96%)'
                        }
                    }
                }
            },
            stop: {
                container: { hover: { backgroundColor: '#ededed94' } },
                svg: {
                    styles: {
                        default: {
                            filter: 'brightness(0) saturate(100%) invert(72%) sepia(0%) saturate(3044%) hue-rotate(322deg) brightness(100%) contrast(96%)'
                        }
                    }
                }
            }
        }"
        :history="history"
    />
</template>
