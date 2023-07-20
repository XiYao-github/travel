<script>
  export default {
    globalData: {
      text: 'text'
    },
    onLaunch: function() {
      // console.log('App Launch')
      let userInfo = uni.getStorageSync('userInfo');
      if (userInfo) {
        // #ifdef APP-PLUS 
        plus.navigator.closeSplashscreen();
        // #endif
      } else {
        //跳转登录
        uni.reLaunch({
          url: "/pages/login/login",
          success: () => {
            // #ifdef APP-PLUS 
            plus.navigator.closeSplashscreen();
            // #endif
          }
        });
      }
      uni.addInterceptor('request', {
        invoke(args) {
          // 添加统一的 headers
          args.header = {
            token: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50IjoiMTgyNTg3MzMyODEiLCJpYXQiOjE2ODIyNDIxNTAsImV4cCI6MTY4MjI0OTM1MH0.cXahy6KEaSM7MBlzS7JFbqU-RfA2hduNwGNEtpyThDA'
          }
        },
        success(args) {
          console.log('interceptor-success', args)
        },
        fail(err) {
          console.log('interceptor-fail', err)
        },
        complete(res) {
          console.log('interceptor-complete', res)
        }
      })
    },
    onShow: function() {
      setTimeout(() => {
        // #ifdef APP-PLUS 
        plus.navigator.closeSplashscreen()
        // #endif
      }, 2000)
      // console.log('App Show')
    },
    onHide: function() {
      // console.log('App Hide')
    }
  }
</script>

<style lang="scss">
  /*每个页面公共css */
  @import '@/uni_modules/uni-scss/index.scss';
  /* #ifndef APP-NVUE */
  @import '@/static/customicons.css';

  // 设置整个项目的背景色
  page {
    background-color: #f5f5f5;
  }

  /* #endif */
  .example-info {
    font-size: 14px;
    color: #333;
    padding: 10px;
  }
</style>
