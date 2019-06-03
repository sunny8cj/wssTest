<template>
<el-row>
  <el-col :span="6" :offset="10">
    <div class="login-container">
    <el-form ref="loginForm">
      <div  class="title-container">
        <h3 class="title">登陆系统</h3>
      </div>
       <el-form-item prop="username" label="用户名：">
         <!-- <i class="el-icon-edit"></i> -->
          <el-input v-model="loginForm.username" type="text"></el-input>
       </el-form-item>
        <el-form-item prop="password" label="密码：">
          <span  @click="showPw">
              <i class="el-icon-view" ></i>
          </span>
          <el-input v-model="loginForm.password" :type="passwordType" ref="pw" @keyup.enter="login"></el-input>
       </el-form-item>
    </el-form>
  <!-- <el-button type="primary" size="large" @click="toRegister">注册</el-button> -->
  <el-button type="primary" size="large" @click="login" :loading="openLoading" style="width:200px">登录</el-button>

  </div>
  </el-col>
</el-row>
</template>

<script>
import post from '@/api'
// import url from '@/serviceAPI.config.js'
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: 'admin',
        password: '@Cddl123456!'
      },
      passwordType: 'password',
      openLoading: false, //  是否开启用户注册的Loading状态
      loading: false,
      showDialog: false,
      redirect: undefined
    }
  },
  created () {
    // if (localStorage.userInfo) {
    //   this.$message('您已经登录过了')
    //   this.$router.push('/gridmap')
    // }
  },
  destroyed () {
  },
  methods: {
    toRegister () {
      this.$router.push('/register')
    },
    showPw () {
      if (this.passwordType === 'password') {
        this.passwordType = 'text'
      } else {
        this.passwordType = 'password'
      }
    },
    login () {

      this.openLoading = true
      post({
        url: 'http://192.168.0.109:8089/login',
        method: 'post',
        data: {
          body: {
              loginName: this.loginForm.username,
              password: this.loginForm.password
          }
        }
      }).then(response  => {
        if(response.body === null){
          this.$message('请输入正确的用户名和密码！')
          this.openLoading = false
        }
        else{
            new Promise((resolve, reject) => {
              localStorage.userInfo = {
                userName: this.loginForm.username
              }
              setTimeout(() => { resolve() }, 500)
            }).then(() => {
              this.$message('登录成功！')
              this.$router.push('/demo6')
            }).catch(err => {
              this.$message('登录状态保存失败')
              console.log(err)
            })
        }
      })
      // axios.post('http://192.168.0.109/login', {
      //   action: 'LUI',
      //   uid: this.loginForm.username,
      //   pwd: this.loginForm.password
      // })
      //   .then(response => {
      //     console.log(response.data.action)
      //     if (response.data.action === 'ERR') {
      //       this.$message('请输入正确的用户名和密码！')
      //       this.openLoading = false
      //     } else {
      //       new Promise((resolve, reject) => {
      //         localStorage.userInfo = {
      //           userName: this.loginForm.username
      //         }
      //         setTimeout(() => { resolve() }, 500)
      //       }).then(() => {
      //         this.$message('登录成功！')
      //         this.$router.push('/demo6')
                sessionStorage.setItem('loginInfo',200)
      //       }).catch(err => {
      //         this.$message('登录状态保存失败')
      //         console.log(err)
      //       })
      //     }
      //   })
    }
  }

}
</script>

<style>
</style>
