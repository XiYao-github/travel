<template>
  <view>
    <view class="view_icons">
      <image class="image_icons" :src="user.image[0]" mode="aspectFill" />
    </view>
    <uni-list>
      <uni-list-item showArrow title="个人信息" to='/pages/user/user_info/user_info' />
      <uni-list-item showArrow title="我的订单" to='/pages/user/user_order/user_order' />
      <uni-list-item showArrow title="我的游记" to='/pages/user/user_notes/user_notes' />
      <uni-list-item showArrow title="编写游记" to='/pages/user/user_notes/user_notes_add/user_notes_add' />
      <uni-list-item showArrow title="退出登录" clickable @click="exit" />
    </uni-list>
  </view>
</template>

<script>
  export default {
    components: {},
    data() {
      return {
        user: {},
      };
    },
    onLoad() {
      // try {
      //   const userInfo = uni.getStorageSync('userInfo');
      //   if (userInfo) {
      //     this.user = userInfo;
      //     console.log("user", this.user);
      //   }
      // } catch (e) {
      //   // error
      // }
    },
    onShow() {
      try {
        const userInfo = uni.getStorageSync('userInfo');
        if (userInfo) {
          this.user = userInfo;
          console.log("user", this.user);
        }
      } catch (e) {
        // error
      }
      // console.log("user", this.user);
    },
    methods: {
      exit() {
        try {
          uni.removeStorageSync('userInfo');
        } catch (e) {
          // error
        };
        uni.reLaunch({
          url: "/pages/login/login",
          success: () => {
            // #ifdef APP-PLUS 
            plus.navigator.closeSplashscreen();
            // #endif
          }
        });
      }
    }
  };
</script>

<style lang="scss">
  .view_icons {
    flex: 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 500rpx;
    background-color: #f5f5f5;
  }

  .image_icons {
    width: 200rpx;
    height: 200rpx;
    border-radius: 50%;
  }
</style>
