<template>
    <div class="flex flex-col items-center justify-center w-full h-full space-y-4">
        <video ref="videoRef" autoplay muted class="w-[640px] h-[480px] border"></video>
        <canvas ref="canvasRef" class="hidden"></canvas>
        <img :src="resultImage" alt="检测结果" v-if="resultImage" class="w-[640px] h-[480px] border" />

        <div class="space-x-4">
            <button @click="startRecording" class="bg-green-500 text-white px-4 py-2 rounded">开始录制</button>
            <button @click="stopRecording" class="bg-red-500 text-white px-4 py-2 rounded">结束录制</button>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onBeforeUnmount } from 'vue'

const videoRef = ref<HTMLVideoElement | null>(null)
const canvasRef = ref<HTMLCanvasElement | null>(null)
const resultImage = ref<string>('')

let socket: WebSocket | null = null
let intervalId: number | null = null

const startRecording = async () => {
    try {
        const stream = await navigator.mediaDevices.getUserMedia({ video: true })
        if (videoRef.value) {
            videoRef.value.srcObject = stream
        }

        // 如果已经存在 WebSocket 连接，先关闭它
        if (socket) {
            if (socket.readyState === WebSocket.OPEN) {
                socket.close()
            }
            socket = null
        }

        // 创建新的 WebSocket 连接
        socket = new WebSocket('ws://localhost:8898/ws/camera/detect')

        socket.onmessage = (event) => {
            resultImage.value = `data:image/jpeg;base64,${event.data}`
        }

        socket.onopen = () => {
            console.log('WebSocket 已连接')
            intervalId = window.setInterval(() => {
                if (!canvasRef.value || !videoRef.value) return

                const canvas = canvasRef.value
                const video = videoRef.value

                canvas.width = video.videoWidth
                canvas.height = video.videoHeight

                const ctx = canvas.getContext('2d')
                if (ctx) {
                    ctx.drawImage(video, 0, 0, canvas.width, canvas.height)
                    const base64 = canvas.toDataURL('image/jpeg', 0.3)  // 降低质量
                    const payload = base64.replace(/^data:image\/jpeg;base64,/, '')

                    // 检查 WebSocket 状态，确保它处于 OPEN 状态
                    if (socket && socket.readyState === WebSocket.OPEN) {
                        socket.send(payload)
                    }
                }
            }, 300)
        }

        socket.onclose = (event) => {
            console.log('WebSocket 已关闭', event.code, event.reason)
            socket = null
        }

        socket.onerror = (error) => {
            console.error('WebSocket 错误:', error)
            socket = null
        }
    } catch (error) {
        console.error('无法访问摄像头:', error)
    }
}

const stopRecording = () => {
    console.log('调用了 stopRecording 方法')
    if (intervalId) {
        clearInterval(intervalId)
        intervalId = null
    }

    if (socket) {
        // 检查 WebSocket 状态，确保它处于 OPEN 或 CLOSING 状态
        if (socket.readyState === WebSocket.OPEN || socket.readyState === WebSocket.CLOSING) {
            socket.close()
        }
        socket = null
    }

    const stream = videoRef.value?.srcObject as MediaStream
    if (stream) {
        stream.getTracks().forEach(track => track.stop())
    }

    resultImage.value = ''
}

onBeforeUnmount(() => {
    stopRecording()
})
</script>

<style lang="scss" scoped>
video,
img {
    object-fit: contain;
    border-radius: 0.5rem;
}
</style>
