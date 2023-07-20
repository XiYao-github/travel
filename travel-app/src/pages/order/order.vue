<template>
  <view>
    <uni-card :is-full="true" :isShadow="true" v-for="(item, index) in orderList" :key="index">
      <view style="display: flex; flex-direction: row;">
        <image style="border-radius: 30rpx; width: 330rpx; height: 220rpx;" :src="item.itemInfo.image[0]"
          mode="aspectFill" />

        <uni-card :is-full="true" :is-shadow="false" :border="false">
          <view style="margin-top: 0rpx;">
            <text style="font-size: 50rpx; font-weight: bold;"
              @click="itemInfo(item.type,item.itemInfo.id)">{{item.itemInfo.name}}</text>
          </view>
          <view style="margin-top: 20rpx; display: flex; flex-direction: row; align-items: center; ">
            <uni-tag v-show="item.type===1" size="mini" :circle="true" type="primary" :text="item.typeText" disabled />
            <uni-tag v-show="item.type===2" size="mini" :circle="true" type="warning" :text="item.typeText" disabled />
            <text style="margin-left: 10rpx; font-size: 30rpx; color: red;">{{item.itemInfo.price}}¥</text>
          </view>
          <view style="margin-top: 20rpx; ">
            <button style="width: 110rpx; height: 50rpx; font-size: 20rpx; margin-right: 10rpx;" size="mini" type="warn"
              @click="remove(index)">取消</button>
            <button style="width: 110rpx; height: 50rpx; font-size: 20rpx;" size="mini" type="primary"
              @click="pay(index)">支付</button>
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
        itemUrl: '/pages/home/scenic_info/scenic_info',
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
          url: this.$baseUrl + '/travel/shop/list',
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
      remove(index) {
        console.log("删除index", index);
        uni.request({
          url: this.$baseUrl + '/travel/shop',
          method: 'DELETE',
          data: [this.orderList[index].id],
          success: res => {
            // console.log('success', res.data);
            // console.log('success', res.data.data);
            console.log('orderList', this.orderList);
            this.list();
          },
          fail: err => {
            console.log('fail', err);
          },
        });
      },
      pay(index) {
        console.log("支付index", index);
        uni.request({
          url: this.$baseUrl + '/travel/order',
          method: 'POST',
          data: {
            userId: this.user.id,
            itemId: this.orderList[index].itemInfo.id,
            price: this.orderList[index].itemInfo.price,
            type: this.orderList[index].type,
            status: 1,
          },
          success: res => {
            // console.log('success', res.data);
            // console.log('success', res.data.data);
            uni.showToast({
              title: "支付成功",
              icon: 'none',
            });
            this.remove(index);
          },
          fail: err => {
            console.log('fail', err);
          },
        });
      }
    }
  }
</script>

<style lang="scss">

</style>
