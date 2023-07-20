<template>
  <view>
    <uni-card style="border-radius: 20rpx; " :is-shadow="true" :border="false" margin="15rpx">
      <uni-segmented-control :current="current" :values="items" activeColor="#007aff" @clickItem="onClickItem" />
      <view style="margin-top: 40rpx;">
        <view v-show="current === 0">
          <uni-card style="border-radius: 20rpx; display: flex; flex-direction: column; " :is-shadow="false"
            :border="false" margin="0rpx" spacing="10rpx" padding="10rpx">
            <view>
              <uni-forms ref="loginForm" :modelValue="loginFormData" :rules="rules">
                <uni-forms-item required label="账号" label-width="80" name="username">
                  <uni-easyinput type="text" v-model="loginFormData.username" placeholder="请输入用户名..." />
                </uni-forms-item>
                <uni-forms-item required label="密码" label-width="80" name="password">
                  <uni-easyinput type="password" v-model="loginFormData.password" placeholder="请输入密码..." />
                </uni-forms-item>
              </uni-forms>
              <button style="border-radius: 50rpx;" type="primary" @click="login()">登录</button>
            </view>
          </uni-card>
        </view>
        <view v-show="current === 1">
          <uni-card style="border-radius: 20rpx; display: flex; flex-direction: column; " :is-shadow="false"
            :border="false" margin="0rpx" spacing="10rpx" padding="10rpx">
            <view>
              <uni-forms ref="logonForm" :modelValue="logonFormData" :rules="rules">
                <uni-forms-item required label="账号" label-width="80" name="username">
                  <uni-easyinput type="text" v-model="logonFormData.username" placeholder="请输入用户名..." />
                </uni-forms-item>
                <uni-forms-item required label="密码" label-width="80" name="password">
                  <uni-easyinput type="password" v-model="logonFormData.password" placeholder="请输入密码..." />
                </uni-forms-item>
              </uni-forms>
              <button style="border-radius: 50rpx;" type="primary" @click="logon()">注册</button>
            </view>
          </uni-card>
        </view>
      </view>
    </uni-card>
  </view>
</template>
<script>
  export default {
    data() {
      return {
        loginFormData: {},
        logonFormData: {},
        items: ['登录', '注册'],
        current: 0,
        rules: {
          username: {
            rules: [{
              required: true,
              errorMessage: '用户名不能为空',
            }, {
              minLength: 2,
              maxLength: 4,
              errorMessage: '长度在 {minLength} 到 {maxLength} 个字符',
            }]
          },
          password: {
            rules: [{
              required: true,
              errorMessage: '密码不能为空',
            }, {
              minLength: 4,
              maxLength: 8,
              errorMessage: '长度在 {minLength} 到 {maxLength} 个字符',
            }]
          }
        }
      };
    },
    onLoad() {},
    methods: {
      onClickItem(e) {
        if (this.current != e.currentIndex) {
          this.current = e.currentIndex;
        }
      },
      // submit(ref) {
      //   this.$refs[ref].validate().then(res => {
      //     this.upload().then(imageUrls => {
      //       console.log('图片路径', imageUrls);
      //       this.dynamicFormData.image = imageUrls;
      //       console.log('表单属性', this.dynamicFormData);
      //       uni.request({
      //         url: this.$baseUrl + '/travel/notes',
      //         method: 'POST',
      //         data: this.dynamicFormData,
      //         success: res => {
      //           console.log('success', res);
      //         },
      //         fail: err => {
      //           console.log('fail', err);
      //         },
      //       });
      //       uni.showToast({
      //         title: `校验通过`
      //       })
      //     }).catch(err => {
      //       console.log('err', err);
      //     })
      //   }).catch(err => {
      //     console.log('err', err);
      //   })
      // },

      // try {
      //   uni.setStorageSync('userInfo', this.loginFormData);
      // } catch (e) {
      //   // error
      // };
      // try {
      //   const value = uni.getStorageSync('userInfo');
      //   if (value) {
      //     console.log(value);
      //   }
      // } catch (e) {
      //   // error
      // }
      login() {
        this.$refs.loginForm.validate().then(res => {
          console.log('表单数据信息：', res);
          uni.request({
            url: this.$baseUrl + '/travel/user/getUser',
            method: 'GET',
            data: this.loginFormData,
            success: res => {
              console.log('success', res.data);
              console.log('success', res.data.data);
              if (res.data.data) {
                uni.setStorageSync('userInfo', res.data.data);
                uni.switchTab({
                  url: '/pages/home/home',
                });
                uni.showToast({
                  title: '登录成功',
                  icon: 'none',
                });
              } else {
                uni.showToast({
                  title: '登录失败',
                  icon: 'none',
                });
              }
            },
            fail: err => {
              console.log('fail', err);
            },
          });
        }).catch(err => {
          console.log('表单错误信息：', err);
        });
      },
      logon() {
        this.$refs.logonForm.validate().then(res => {
          uni.request({
            url: this.$baseUrl + '/travel/user',
            method: 'POST',
            data: this.logonFormData,
            success: res => {
              console.log('success', res.data);
              console.log('success', res.data.data);
              if (res.data.data) {
                uni.showToast({
                  title: '注册成功',
                  icon: 'none',
                });
                this.current = 0;
                this.loginFormData = {
                  ...this.logonFormData
                };
                this.logonFormData = {};
              } else {
                uni.showToast({
                  title: '注册失败',
                  icon: 'none',
                });
              }
            },
            fail: err => {
              console.log('fail', err);
            },
          });
          console.log('表单数据信息：', res);
        }).catch(err => {
          console.log('表单错误信息：', err);
        })
      }
    }
  };
</script>
