<template>
    <div id="userLayout" :class="['user-layout-wrapper', isMobile && 'mobile']">
        <div class="container">
            <a-row
                class="container_login"
                :style="{
                    'background': `url('/img/${codeMap['system.login.banner']}') no-repeat`,
                    'background-size': '100% 100%',
                }"
            >
                <a-col class="container_aCol">
                    <router-view/>
                </a-col>
            </a-row>
        </div>
    </div>
</template>

<script>
import { deviceMixin } from '@/store/device-mixin'

export default {
    name: 'UserLayout',
    computed: {
        codeMap() {
            return this.$store.getters.config
        }
    },
    mixins: [deviceMixin],
    mounted() {
        document.body.classList.add('userLayout')
    },
    watch: {
        codeMap() {
        }
    },
    beforeDestroy() {
        document.body.classList.remove('userLayout')
    }
}
</script>

<style lang="less" scoped>
#userLayout.user-layout-wrapper {
    height: 100%;

    &.mobile {
        .container {
            .main {
                max-width: 368px;
                width: 98%;
            }
        }
    }

    .container_login {
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;

        .container_aCol {
            height: fit-content;
            background: rgba(255, 255, 255, 0.88);
            border-radius: 15px;
            padding: 25px;
        }
    }

    .container {
        width: 100%;
        min-height: 100%;
        height: 100%;
        position: relative;
        overflow-x: hidden;

        a {
            text-decoration: none;
        }

        .top {
            text-align: center;

            .header {
                height: 44px;
                line-height: 44px;

                .badge {
                    position: absolute;
                    display: inline-block;
                    line-height: 1;
                    vertical-align: middle;
                    margin-left: -12px;
                    margin-top: -10px;
                    opacity: 0.8;
                }

                .logo {
                    height: 44px;
                    vertical-align: top;
                    margin-right: 16px;
                    border-style: none;
                }

                .title {
                    font-size: 33px;
                    color: rgba(0, 0, 0, 0.85);
                    font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
                    font-weight: 600;
                    position: relative;
                    top: 2px;
                }
            }

            .desc {
                font-size: 14px;
                color: rgba(0, 0, 0, 0.45);
                margin-top: 12px;
                margin-bottom: 40px;
            }
        }

        .main {
            max-width: 600px;
            margin: 0 auto;
        }

        .footer {
            position: absolute;
            width: 100%;
            bottom: 0;
            padding: 0 16px;
            margin: 48px 0 24px;
            text-align: center;

            .links {
                margin-bottom: 8px;
                font-size: 14px;

                a {
                    color: rgba(0, 0, 0, 0.45);
                    transition: all 0.3s;

                    &:not(:last-child) {
                        margin-right: 40px;
                    }
                }
            }

            .copyright {
                color: rgba(0, 0, 0, 0.45);
                font-size: 14px;
            }
        }
    }
}
</style>
