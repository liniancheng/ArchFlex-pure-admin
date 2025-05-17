<template>
    <div class="main user-layout-register">
        <h2 class="login_text">
            {{ codeMap['system.login.logo.text'] }}
        </h2>
        <h4 class="title_h4">
            数接千载
            <i style="margin-left:20px"/>据联万里
        </h4>
        <a-form ref="formRegister" :form="form" id="formRegister" class="formRegisterChange">
            <a-form-item>
                <a-input
                    size="large"
                    type="text"
                    placeholder="请输入登录名"
                    v-decorator="['loginName', {rules: [{ required: true, message: '请输入登录名！' }], validateTrigger: ['change', 'blur']}]"
                ></a-input>
            </a-form-item>
            <a-form-item>
                <a-input
                    size="large"
                    type="password"
                    autocomplete="false"
                    placeholder="请输入旧密码!"
                    v-decorator="['oldPwd', {rules: [{ required: true, message: '请输入旧密码!' }], validateTrigger: ['change', 'blur']}]"
                ></a-input>
            </a-form-item>

            <a-popover
                placement="rightTop"
                :trigger="['focus']"
                :getPopupContainer="(trigger) => trigger.parentElement"
                v-model="state.passwordLevelChecked">
                <template slot="content">
                    <div :style="{ width: '240px' }">
                        <div :class="['user-register', passwordLevelClass]">强度：<span>{{ passwordLevelName }}</span>
                        </div>
                        <a-progress :percent="state.percent" :showInfo="false" :strokeColor=" passwordLevelColor "/>
                        <div style="margin-top: 10px;">
                            <span>{{ codeMap['system.pwd.rule.msg'] }}</span>
                        </div>
                    </div>
                </template>
                <a-form-item>
                    <a-input
                        size="large"
                        type="password"
                        @click="handlePasswordInputClick"
                        autocomplete="false"
                        placeholder="请输入新密码"
                        v-decorator="['password', {rules: [{ required: true}, { validator: this.handlePasswordLevel }], validateTrigger: ['change', 'blur']}]"
                    ></a-input>
                </a-form-item>
            </a-popover>

            <a-form-item>
                <a-input
                    size="large"
                    type="password"
                    autocomplete="false"
                    placeholder="确认密码"
                    v-decorator="['password2', {rules: [{ required: true }, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur']}]"
                ></a-input>
            </a-form-item>
            <a-form-item>
                <a-button
                    size="large"
                    type="primary"
                    htmlType="submit"
                    class="register-button"
                    :loading="registerBtn"
                    @click.stop.prevent="handleSubmit"
                    :disabled="registerBtn">确定
                </a-button>
                <router-link class="login" :to="{ name: 'login' }">返回登录界面</router-link>
            </a-form-item>

        </a-form>
    </div>
</template>

<script>
import { changeLoginPwd } from '@/api/admin/user/index'
import CryptoJS from '@/utils/CryptoJS'

const levelNames = { 0: '低', 1: '低', 2: '中', 3: '强' }
const levelClass = { 0: 'error', 1: 'error', 2: 'warning', 3: 'success' }
const levelColor = { 0: '#ff0000', 1: '#ff0000', 2: '#ff7e05', 3: '#52c41a' }
export default {
    name: 'Change',
    mixins: [],
    data() {
        return {
            form: this.$form.createForm(this),
            state: {
                time: 60,
                passwordLevel: 0,
                passwordLevelChecked: false,
                percent: 10,
                progressColor: '#FF0000'
            },
            registerBtn: false
        }
    },
    mounted() {
        this.$message.warning(this.$route.params.message)
    },
    computed: {
        codeMap() {
            return this.$store.getters.config
        },
        passwordLevelClass() {
            return levelClass[this.state.passwordLevel]
        },
        passwordLevelName() {
            return levelNames[this.state.passwordLevel]
        },
        passwordLevelColor() {
            return levelColor[this.state.passwordLevel]
        }
    },
    methods: {
        handlePasswordLevel(rule, value, callback) {
            let level = 0
            const list = this.codeMap['system.pwd.rule.pattern'].split(',')
            if (list.length > 0) {
                for (var i = 0; i < list.length; i++) {
                    if (new RegExp(list[i]).test(value)) {
                        level++
                    }
                }
            }
            this.state.passwordLevel = level
            this.state.percent = level / list.length * 100
            if (level === list.length) {
                callback()
            } else {
                if (level === 0) {
                    this.state.percent = 10
                }
                callback(new Error(this.codeMap['system.pwd.rule.msg']))
            }
        },

        handlePasswordCheck(rule, value, callback) {
            const password = this.form.getFieldValue('password')
            if (value === undefined) {
                callback(new Error('请输入密码'))
            }
            if (value && password && value.trim() !== password.trim()) {
                callback(new Error('两次密码不一致'))
            }
            callback()
        },
        handlePasswordInputClick() {
            this.state.passwordLevelChecked = true
        },
        handleSubmit() {
            const { form: { validateFields }, state, $router } = this
            validateFields({ force: true }, (err, values) => {
                if (!err) {
                    changeLoginPwd({
                        loginName: values.loginName,
                        oldPwd: CryptoJS.encrypt(values.oldPwd),
                        newPwd: CryptoJS.encrypt(values.password)
                    }).then(res => {
                        if (res.code === 0) {
                            this.$message.success(res.message)
                            state.passwordLevelChecked = false
                            $router.push({ name: 'login' })
                        } else {
                            this.$message.error(res.message)
                        }
                    })
                }
            })
        },

        requestFailed(err) {
            this.$notification['error']({
                message: '错误',
                description: ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
                duration: 4
            })
            this.registerBtn = false
        }
    },
    watch: {
        'state.passwordLevel'(val) {
            console.log(val)
        },
        codeMap(newVal, oldVal) {
        }
    }
}
</script>
<style lang="less">
@import '~ant-design-vue/es/style/themes/default.less';

.main {
    .login {
        margin-left: 42px;
        margin-bottom: 30px;
    }

    .login_text {
        text-align: center;
        font-size: 32px;
        font-weight: bold;
        // margin-bottom: 60px;
        color: @primary-color;
    }

    h4.title_h4 {
        font-size: 21px;
        font-family: SourceHanSansCN, serif;
        font-weight: 400;
        color: rgba(153, 153, 153, 1);
        text-align: center;
        margin-bottom: 50px;
    }
}

.user-register {
    &.error {
        color: #ff0000;
    }

    &.warning {
        color: #ff7e05;
    }

    &.success {
        color: #52c41a;
    }
}

.user-layout-register {
    .ant-input-group-addon:first-child {
        background-color: #fff;
    }
}
</style>
<style lang="less" scoped>
.user-layout-register {
    & > h3 {
        font-size: 16px;
        margin-bottom: 20px;
    }

    .register-button {
        width: 50%;
    }

    .login {
        float: right;
        line-height: 40px;
    }

    .formRegisterChange {
        width: 360px;
    }
}
</style>
