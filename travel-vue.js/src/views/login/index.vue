<template>
  <div class="div_image">
    <div class="div_box">
      <el-card shadow="hover">
        <el-form ref="form" :model="formData" :rules="rules">
          <div class="div_title">
            <h1>登录</h1>
          </div>
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="formData.username" clearable
                      style="width: 100%"/>
          </el-form-item>
          <el-form-item prop="password">
            <el-input show-password prefix-icon="el-icon-lock" placeholder="请输入密码" v-model="formData.password" clearable
                      style="width: 100%"/>
          </el-form-item>
          <el-form-item prop="code">
            <div style="display: flex; justify-content: space-between;">
              <div>
                <el-input placeholder="请输入验证码" v-model="formData.code" clearable style="width: 90%"/>
              </div>
              <div @click="refreshCode">
                <codeImage :identifyCode="identifyCode"/>
              </div>
            </div>
          </el-form-item>
          <el-button style="width: 100%" round @click="submit('form')">登录</el-button>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
import codeImage from "@/components/code.vue"
import {getUser} from "@/api/admin"

export default {
  components: {
    codeImage
  },
  data() {
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'));
      } else if (value !== this.identifyCode) {
        callback(new Error('验证码不正确!'));
        this.refreshCode();
      } else {
        callback()
      }
    };
    const validateUsername = (rule, value, callback) => {
      console.log(rule, value, callback)
      if (value === '') {
        callback(new Error('请输入用户名'))
      } else {
        callback()
      }
    };
    const validatePassword = (rule, value, callback) => {
      console.log(rule, value, callback)
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        callback()
      }
    };
    return {
      identifyCodes: '1234567890',
      identifyCode: '',
      formData: {
        username: 'admin',
        password: 'admin',
      },
      rules: {
        account: [
          {required: true, trigger: 'blur', validator: validateUsername},
        ],
        password: [
          {required: true, trigger: 'blur', validator: validatePassword},
        ],
        code: [
          {required: true, trigger: 'blur', validator: validateCode,}
        ]
      }
    }
  },
  mounted() {
    this.identifyCode = '';
    this.makeCode(this.identifyCodes, 4);
  },
  methods: {
    submit(form) {
      this.$refs[form].validate(async (valid) => {
        if (valid) {
          const {data} = await getUser(this.formData);
          if (data.data != null) {
            console.log("数据", data.data);
            //保存token和管理员信息
            this.$message.success("登入成功");
            await this.$router.push('/index');
          } else {
            this.$message.error("用户名或密码输入错误");
            this.refreshCode();
          }
        } else {
          return false;
        }
      });
    },
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min)
    },
    // 切换验证码
    refreshCode() {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[
            this.randomNum(0, this.identifyCodes.length)]
      }
      console.log(this.identifyCode)
      /* alert(this.identifyCode) */
    }
  }
}
</script>

<style scoped>
.el-card {
  background-color: rgba(88, 88, 88, 0.404);
  border: 1px solid transparent;
  display: flex;
  border-radius: 24px;
}

.el-input >>> .el-input__inner {
  background-color: rgba(255, 255, 255, 0.8);
  border: 1px solid transparent;
}

.el-button {
  background-color: rgba(255, 255, 255, 0.404);
  border: 1px solid transparent;
}


.div_title {
  height: 35px;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.div_box {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.div_image {
  background-image: url(@/assets/image/hand.jpg);
  background-size: cover;
  position: fixed;
  width: 100%;
  height: 100%;
}
</style>
