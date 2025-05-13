<template>
  <div>
    <!-- 个人信息 -->
    <div class="person_top">
      <div class="person_title">
        <img src="~@/assets/img/png_35.png" />
        <h2>我的信息</h2>
      </div>
      <a-row>
        <a-col :span="8">
          <p class="from_head"><a-icon type="user" />姓名：</p>
          <span class="from_span">{{ accountInfo.userName }}</span>
        </a-col>
        <a-col :span="8">
          <p class="from_head"><a-icon type="branches" />所属部门：</p>
          <span class="from_span">{{ accountInfo.branchName }}</span>
        </a-col>
        <a-col :span="8">
          <p class="from_head"><a-icon type="contacts" />平台角色：</p>
          <span class="from_span">{{ accountInfo.roleNames }}</span>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="8">
          <p class="from_head"><a-icon type="tag" />上次登录时间：</p>
          <span class="from_span">{{ accountInfo.lastLoginTime }}</span>
        </a-col>
        <a-col :span="8">
          <p class="from_head"><a-icon type="phone" />电话：</p>
          <span class="from_span">{{ accountInfo.userMobTel }}</span>
        </a-col>
        <a-col :span="8">
          <p class="from_head"><a-icon type="mail" />邮箱：</p>
          <span class="from_span">{{ accountInfo.userEmail }}</span>
        </a-col>
      </a-row>
    </div>
    <div class="person_bottom">
      <div class="person_title">
        <img src="~@/assets/img/png_35.png" />
        <h2>账号安全</h2>
      </div>
      <a-row>
        <a-col :span="10" class="from_right_border from_right_margin">
          <div class="from_left">
            <!--<img src="~@/assets/img/png_354.png" />-->
            <a-icon type="lock" />
          </div>
          <div class="from_right">
            <p class="from_p" @click="handleTabChange(1)">修改密码</p>
            <span class="from_span">定期修改密码有助于提高账户安全性</span>
          </div>
        </a-col>
        <a-col :span="10" style="margin-left:40px">
          <div class="from_left">
            <a-icon type="idcard" />
          </div>
          <div class="from_right">
            <p class="from_p" @click="handleTabChange(2)">修改个人信息</p>
            <span class="from_span">完善个人信息有助于提高功能使用性</span>
          </div>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="10" class="from_right_border">
          <div class="from_left">
            <a-icon type="layout" />
          </div>
          <div class="from_right">
            <p class="from_p" @click="handleDesign">自定义首页</p>
            <span class="from_span">定制、管理自己的首页</span>
          </div>
        </a-col>
        <a-col :span="10" style="margin-left:40px">
          <div class="from_left poweroff">
            <a-icon type="poweroff" />
          </div>
          <div class="from_right">
            <p class="from_p from_p_color" @click="handleLogout">安全退出</p>
            <span class="from_span">使用安全退出保护账户安全</span>
          </div>
        </a-col>
      </a-row>
    </div>
  </div>
</template>
<script>
import { Modal } from 'ant-design-vue'
import { mapActions } from 'vuex'
import { fetchByLoginName } from '@/api/admin/layout/layout/index'
export default {
  name: 'PersonInfo',
  props: {
    accountInfo: {
      type: Object,
      required: true
    }
  },
  data () {
    return {}
  },
  methods: {
    ...mapActions(['Logout']),
    handleTabChange (type) {
      this.$emit('leftTabChange', type)
    },
    // 退出登录
    handleLogout (e) {
      Modal.confirm({
        title: '提醒',
        content: '确定退出？',
        onOk: () => {
          this.Logout().then(res => {
            window.location.href = '/user/login'
          })
        },
        onCancel () {}
      })
    },
    handleDesign () {
      const _this = this
      Modal.confirm({
        title: '确定使用定义布局？',
        content: '使用自定义布局之后，若需要恢复默认布局，可在定制界面点击【默认布局】按钮',
        onOk () {
          fetchByLoginName().then(res => {
            if (res.code === 0) {
              _this.$router.push({ path: '/admin/custom/layoutDesign/' + res.data.layId })
            }
          })
        }
      })
    }
  }
}
</script>
<style lang="less" scoped>
.person_top {
  height: 40%;
  .ant-row {
    margin-bottom: 45px;
  }
}
</style>
