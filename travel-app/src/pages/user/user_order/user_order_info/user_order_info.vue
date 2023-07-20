<template>
  <view>
    <view>
      <uni-card style="border-radius: 20rpx; " :is-shadow="false" :border="false" margin="10rpx" spacing="0rpx"
        padding="0rpx">
        <uni-section title="订单进度" type="line" padding>
          <uni-steps :active="order.status-1" :options="order.statusList" active-color="#007AFF" direction="column" />
        </uni-section>
      </uni-card>
    </view>

    <view style="display: flex; flex-direction: row; justify-content: space-between; ">
      <button v-show="order.status===2" style="margin: 20rpx; width: 100%; border-radius: 50rpx;" type="warn"
        size="mini" @click="deleteChange">取消订单</button>
      <button v-show="order.status===2" style="margin: 20rpx; width: 100%; border-radius: 50rpx;" type="primary"
        size="mini" @click="change">订单签收</button>
    </view>


    <view v-show="order.status===3">
      <uni-card style="border-radius: 20rpx; " :is-shadow="false" :border="false" margin="10rpx" spacing="0rpx"
        padding="0rpx">
        <uni-section title="评分" type="line" padding>
          <uni-rate v-model="commentData.score" />
        </uni-section>
        <uni-section title="评论" type="line" padding>
          <uni-easyinput type="textarea" autoHeight v-model="commentData.content" placeholder="请输入内容" />
        </uni-section>
        <uni-section title="图片" type="line">
          <uni-file-picker style=" padding: 20rpx; padding-top: 0rpx;" v-model="imageValue" fileMediatype="image"
            mode="grid" s limit="3" @select="select" @delete="deleteImage" />
        </uni-section>
        <button style="margin-top: 20rpx; border-radius: 50rpx;" type="primary" @click="submit()">提交评论</button>
      </uni-card>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        orderId: '1',
        order: {
          id: 1,
          status: 2,
          statusList: [{
            title: '买家下单',
            desc: '2018-11-11'
          }, {
            title: '卖家确认',
            desc: '2018-11-12'
          }, {
            title: '买家签收',
            desc: '2018-11-13'
          }, {
            title: '买家评价',
            desc: '2018-11-14'
          }, {
            title: '交易完成',
            desc: '2018-11-14'
          }, {
            title: '交易取消',
            desc: '2018-11-15'
          }]
        },
        commentData: {},
        imageValue: [], //回显图片集合
        imageList: [], //本地图片路径
      }
    },
    onLoad(option) {
      try {
        const userInfo = uni.getStorageSync('userInfo');
        if (userInfo) {
          this.user = userInfo;
          console.log("user", this.user);
        }
      } catch (e) {
        // error
      }
      console.log("订单", option.id);
      if (option.id) {
        this.orderId = option.id;
      }
    },
    onShow() {
      console.log('我的订单：', this.orderId);
      uni.request({
        url: this.$baseUrl + '/travel/order/info',
        method: 'GET',
        data: {
          id: this.orderId
        },
        success: res => {
          // console.log('success', res.data);
          // console.log('success', res.data.data);
          this.order = res.data.data;
          console.log('order', this.order);
        },
        fail: err => {
          console.log('fail', err);
        },
      });
    },
    methods: {
      // 获取上传状态
      select(e) {
        this.imageList.push(...e.tempFilePaths)
        console.log('选择文件：', e, this.imageList)
      },
      // 文件从列表移除时触发
      deleteImage(e) {
        this.imageList.forEach(function(item, index, arr) {
          if (item === e.tempFilePath) {
            arr.splice(index, 1);
          }
        });
        console.log('取消上传：', e, this.imageList)
      },
      upload() {
        const promises = this.imageList.map(image => {
          return new Promise((resolve, reject) => {
            uni.uploadFile({
              url: this.$baseUrl + '/travel/image/upload',
              filePath: image,
              name: 'file',
              success: (res) => {
                const {
                  data
                } = JSON.parse(res.data)
                console.log('上传图片返回', res)
                resolve(data)
              },
              fail: (err) => {
                reject(err)
              },
            })
          })
        })
        return Promise.all(promises)
      },
      submit() {
        this.upload().then(imageUrls => {
          console.log('图片路径', imageUrls);
          this.commentData.image = imageUrls;
          this.commentData.userId = this.user.id;
          this.commentData.itemId = this.order.itemId;
          this.commentData.type = this.order.type;
          console.log('表单属性', this.commentData);
          uni.request({
            url: this.$baseUrl + '/travel/comment',
            method: 'POST',
            data: this.commentData,
            success: res => {
              console.log('success', res);
              this.comment();
            },
            fail: err => {
              console.log('fail', err);
            },
          });
        }).catch(err => {
          console.log('err', err);
        })
      },
      comment() {
        this.order.status = 4;
        console.log('this.order.status', this.order.status);
        uni.request({
          url: this.$baseUrl + '/travel/order',
          method: 'PUT',
          data: this.order,
          success: res => {
            // console.log('success', res.data);
            // console.log('success', res.data.data);
            this.order = res.data.data;
            console.log('更新order', this.order);
            uni.showToast({
              title: `评论成功`,
              icon: 'none',
            });
          },
          fail: err => {
            console.log('fail', err);
          },
        });
      },
      change() {
        this.order.status = 3;
        console.log('this.order.status', this.order.status);
        uni.request({
          url: this.$baseUrl + '/travel/order',
          method: 'PUT',
          data: this.order,
          success: res => {
            // console.log('success', res.data);
            // console.log('success', res.data.data);
            this.order = res.data.data;
            console.log('更新order', this.order);
            uni.showToast({
              title: `签收成功`,
              icon: 'none',
            });
          },
          fail: err => {
            console.log('fail', err);
          },
        });
      },
      deleteChange() {
        this.order.status = 0;
        console.log('this.order.status', this.order.status);
        uni.request({
          url: this.$baseUrl + '/travel/order',
          method: 'PUT',
          data: this.order,
          success: res => {
            // console.log('success', res.data);
            // console.log('success', res.data.data);
            this.order = res.data.data;
            console.log('更新order', this.order);
            uni.showToast({
              title: `取消订单`,
              icon: 'none',
            });
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
