<template>
    <div
        class="chat-body__wrapper"
        :class="['mt-4', !deviceDetection() && 'ml-16']"
    >
        <component :is="Bing" />
    </div>
</template>

<script setup lang="ts">
import { deviceDetection } from "@pureadmin/utils";
import Bing from "./components/Bing.vue";
</script>

<style lang="scss" scoped>
.chat-body__wrapper {
    height: -webkit-fill-available;
}
</style>
