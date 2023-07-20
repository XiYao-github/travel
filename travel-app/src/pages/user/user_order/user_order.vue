<template>
  <view>
    <uni-card :is-full="true" :isShadow="true" v-for="(item, index) in orderList" :key="index">
      <view style="display: flex; flex-direction: row;">
        <image style="border-radius: 30rpx; width: 330rpx; height: 220rpx;" mode="aspectFill"
          :src="item.itemInfo.image[0]" />

        <uni-card :is-full="true" :is-shadow="false" :border="false">
          <view style="margin-top: 0rpx;">
            <text style="font-size: 50rpx; font-weight: bold;"
              @click="itemInfo(item.type,item.itemInfo.id)">{{item.itemInfo.name}}</text>
          </view>
          <view style="margin-top: 20rpx; display: flex; flex-direction: row; align-items: center; ">
            <uni-tag v-show="item.type===1" size="mini" :circle="true" type="primary" :text="item.typeText" disabled />
            <uni-tag v-show="item.type===2" size="mini" :circle="true" type="warning" :text="item.typeText" disabled />
            <text style="margin-left: 10rpx; font-size: 30rpx; color: red;">{{item.price}}¥</text>
          </view>
          <view style=" margin-top: 15rpx;">
            <navigator :url="'/pages/user/user_order/user_order_info/user_order_info?id='+item.id">
              <text style="font-size: 25rpx; color: darkgrey;">订单详情 ></text>
            </navigator>
          </view>
        </uni-card>
      </view>
    </uni-card>
  </view>
</template>
<script>
  export default {
    data() {
      return {
        orderList: [{
          id: 1,
          itemId: 4,
          type: 1,
          typeText: "景点",
          price: 63,
          itemInfo: {
            id: 1,
            name: "1景点",
            price: 63,
            image: [
              "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
            ],
          },
        }, {
          id: 2,
          itemId: 5,
          type: 2,
          typeText: "套餐",
          itemInfo: {
            id: 2,
            name: "2景点",
            price: 66,
            image: [
              "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
            ],
          },
        }, ],
        user: {},
      }
    },
    onLoad() {
      try {
        const userInfo = uni.getStorageSync('userInfo');
        if (userInfo) {
          this.user = userInfo;
          console.log("user", this.user);
        }
      } catch (e) {
        // error
      }
    },
    onShow() {
      this.list();
    },
    methods: {
      itemInfo(type, itemId) {
        if (type === 1) {
          uni.navigateTo({
            url: '/pages/home/scenic_info/scenic_info?id=' + itemId
          });
          console.log('url', type, itemId);
        } else if (type === 2) {
          uni.navigateTo({
            url: '/pages/home/packages_info/packages_info?id=' + itemId
          });
          console.log('url', type, itemId);
        }
      },
      list() {
        console.log('用户：', this.user.id);
        uni.request({
          url: this.$baseUrl + '/travel/order/list',
          method: 'GET',
          data: {
            userId: this.user.id
          },
          success: res => {
            // console.log('success', res.data);
            // console.log('success', res.data.data);
            this.orderList = res.data.data;
            console.log('orderList', this.orderList);
          },
          fail: err => {
            console.log('fail', err);
          },
        });
      },
    }
  }
</script>

<style lang="scss">
</style>
