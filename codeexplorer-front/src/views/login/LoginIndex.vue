<template>
  <div class="login">
    <div class="login-container">
      <div class="title">
        <p class="login-title">CodeExplorer</p>
      </div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
        <el-form-item label="账号" prop="name">
          <el-input class="input-field" v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" class="input-field" v-model="ruleForm.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button class="login-button" type="primary" @click="submitForm('ruleForm')">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script>
export default {
  data() {
    return {
      ruleForm: {
        name: '',
        password: '',
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.ruleForm.name === 'bindian' && this.ruleForm.password === 'bindian2024') {
            // 验证通过且用户名和密码匹配，跳转到 home 页面
            this.$router.push({ path: '/home' });
          } else {
            // 用户名或密码不匹配
            alert('用户名或密码错误');
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>
@import "../../css/login.css";
</style>
