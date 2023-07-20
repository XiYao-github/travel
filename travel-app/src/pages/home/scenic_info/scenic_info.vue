<template>
  <view>
    <view>
      <uni-search-bar bgColor="#ffffff" radius="5" placeholder="搜索旅游景区..." clearButton="auto" cancelButton="none"
        @confirm="search" />
    </view>

    <view>
      <swiper style="height: 400rpx;" indicator-dots autoplay circular>
        <swiper-item v-for="(item, index) in scenic.image" :key="index">
          <image :src="item" mode="aspectFill" style="width: 100%; height: 100%;" />
        </swiper-item>
      </swiper>
    </view>

    <view>
      <uni-card style="border-radius: 20rpx; " :is-shadow="false" :border="false" margin="10rpx">
        <view style="margin-top: 10rpx; display: flex; flex-direction: row; 
          align-items: center; justify-content: space-between;">
          <text style="font-size: 30rpx; color: red; margin-left: 5rpx;">{{scenic.price}}¥</text>
          <uni-rate size="20" readonly :value="scenic.score" />
        </view>
        <view style="padding-top: 30rpx; display: flex; flex-direction: row; 
          align-items: center; justify-content: space-between;">
          <text style="font-size: 50rpx;">{{scenic.name}}</text>
          <uni-tag :circle="true" disabled text="景点" type="primary" />
        </view>
        <view style="padding-top: 30rpx; display: flex; flex-direction: column;">
          <uni-title type="h4" :title="scenic.address" />
          <uni-title :title="scenic.description" />
        </view>
      </uni-card>
    </view>

    <view>
      <uni-card style="border-radius: 20rpx;" :is-shadow="false" :border="false" margin="10rpx" padding="0"
        spacing="0rpx">
        <view
          style="margin-left: 30rpx; margin-right: 20rpx; display: flex; flex-direction: row; align-items: center; justify-content: space-between;">
          <view>
            <uni-title type="h4" title="评论列表" />
          </view>
          <view style="width: 30%; ">
            <uni-data-select v-model="selectValue" :localdata="range" @change="selectChange" />
          </view>
        </view>
        <uni-card style="border-radius: 30rpx;" :border="false" margin="10rpx" padding="10rpx"
          v-for="(item, index) in commentList" :key="index">
          <view style="margin-top: 10rpx; display: flex; flex-direction: row; 
            align-items: center; justify-content: space-between;">
            <view style="display: flex; flex-direction: row; align-items: center; ">
              <image style=" width: 60rpx; height: 60rpx; border-radius: 50%;" :src="item.userInfo.image[0]"
                mode="aspectFill" />
              <uni-title style="margin-left: 10rpx;" type="h3" :title="item.userInfo.username" />
            </view>
            <uni-rate size="20" readonly :value="item.score" />
          </view>
          <view>
            <uni-title :title="item.content" />
          </view>
          <view>
            <uni-grid :column="3" :showBorder="false" :squarer="false">
              <uni-grid-item v-for="(img, indexImage) in item.image" :key="indexImage">
                <image :src="img" mode="aspectFill"
                  style="width: 100%; height: 100%; border-radius: 20rpx; margin: 10rpx;" />
              </uni-grid-item>
            </uni-grid>
          </view>
          <view>
            <text>{{item.createdTime}}</text>
          </view>
        </uni-card>
        <view v-show="commentList.length===0">
          <view style="height: 400rpx; display: flex; flex-direction: column; align-items: center;">
            <uni-title type="h4" align="center" title="暂无评论" />
          </view>
        </view>
      </uni-card>
    </view>
    <view>
      <view style="height: 100rpx;"></view>
      <uni-goods-nav style="position: fixed; bottom: 0; width: 100%;" :options="options" :fill="true"
        :button-group="buttonGroup" @click="onClick" @buttonClick="buttonClick" />
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        scenicId: 1,
        searchValue: '',
        scenic: {
          id: 1,
          name: "景点1",
          description: "长城你曾抵御了北方匈奴，保卫了这块广阔的炎黄子孙的国土，你是龙的象征，是民族的骄傲，这里的每一块砖石，每一个城垛，每一座墙基都像一根硕大的绳子缚住了龙的躯体。漫漫几千年过去了，如今，我们看见天下第一关的大门已经敞开了，中华民族这条巨龙，终将摆脱这条绳子，吹着滔天浩气，闪着万片金鳞，从这扇大门腾飞出去……",
          address: "广阔的炎黄子孙的国土",
          price: 63,
          score: 5,
          image: [
            "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
        },
        selectValue: 0,
        range: [{
          value: 1,
          text: "1星"
        }, {
          value: 2,
          text: "2星"
        }, {
          value: 3,
          text: "3星"
        }, {
          value: 4,
          text: "4星"
        }, {
          value: 5,
          text: "5星"
        }],
        commentList: [{
          id: "1",
          content: "滕王阁位于沿江北路，赣江与抚河交汇处，高大约57.5米感觉比黄鹤楼和岳阳楼都要雄伟，传说中的江南三大名楼之一",
          score: 5,
          createdTime: '1999-7-5 12:34:56',
          image: [
            "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
          userInfo: {
            username: "张三",
            image: [
              "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
              "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
            ],
          },
        }, {
          id: "2",
          username: "张三",
          userImage: "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg",
          content: "滕王阁位于沿江北路，赣江与抚河交汇处，高大约57.5米感觉比黄鹤楼和岳阳楼都要雄伟，传说中的江南三大名楼之一",
          score: 5,
          createdTime: '1999-7-5 12:34:56',
          image: [
            "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
          userInfo: {
            username: "张三",
            image: [
              "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
              "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
            ],
          },
        }, ],
        options: [{
          icon: 'cart',
          text: '购物车',
          info: 2
        }],
        buttonGroup: [{
          text: '加入购物车',
          backgroundColor: '#ff0000',
          color: '#fff'
        }, {
          text: '立即购买',
          backgroundColor: '#ffa200',
          color: '#fff'
        }],
        user: {},
      };
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
      console.log("列表-景点", option.id);
      if (option.id) {
        this.scenicId = option.id;
      }
    },
    onShow() {
      console.log('景点：', this.scenicId);
      uni.request({
        url: this.$baseUrl + '/travel/scenic/info',
        method: 'GET',
        data: {
          id: this.scenicId
        },
        success: res => {
          // console.log('success', res.data);
          // console.log('success', res.data.data);
          this.scenic = res.data.data;
          console.log('scenic', this.scenic);
        },
        fail: err => {
          console.log('fail', err);
        },
      });
      uni.request({
        url: this.$baseUrl + '/travel/comment/list',
        method: 'GET',
        data: {
          itemId: this.scenicId,
          type: 1
        },
        success: res => {
          // console.log('success', res.data);
          // console.log('success', res.data.data);
          this.commentList = res.data.data;
          console.log('commentList', this.commentList);
        },
        fail: err => {
          console.log('fail', err);
        },
      });
      uni.request({
        url: this.$baseUrl + '/travel/shop/count',
        method: 'GET',
        data: {
          userId: this.user.id,
        },
        success: res => {
          // console.log('success', res.data);
          // console.log('success', res.data.data);
          this.options[0].info = res.data.data;
          console.log('count', this.options.info);
        },
        fail: err => {
          console.log('fail', err);
        },
      });
    },
    methods: {
      search(res) {
        this.searchValue = res.value,
          console.log('景点名称', this.searchValue);
        uni.showToast({
          title: '搜索：' + res.value,
          icon: 'none',
        });
        uni.navigateTo({
          url: '/pages/home/scenic_list/scenic_list?name=' + res.value
        });
      },
      selectChange(e) {
        console.log("评分:", e);
        uni.request({
          url: this.$baseUrl + '/travel/comment/list',
          method: 'GET',
          data: {
            itemId: this.scenicId,
            score: e,
            type: 1,
          },
          success: res => {
            // console.log('success', res.data);
            // console.log('success', res.data.data);
            this.commentList = res.data.data;
            console.log('commentList', e, this.commentList);
          },
          fail: err => {
            console.log('fail', err);
          },
        });
      },
      onClick(e) {
        uni.switchTab({
          url: '/pages/order/order',
        });
      },
      buttonClick(e) {
        console.log(e.index)
        uni.request({
          url: this.$baseUrl + '/travel/shop',
          method: 'POST',
          data: {
            userId: this.user.id,
            itemId: this.scenic.id,
            type: 1,
          },
          success: res => {
            console.log('success', res.data);
            console.log('success', res.data.data);
            if (e.index === 1) {
              uni.switchTab({
                url: '/pages/order/order',
              });
            }
            if (e.index === 0) {
              if (res.data.code === 500) {
                uni.showToast({
                  title: res.data.msg,
                  icon: 'none',
                })
              } else {
                this.options[0].info++
                uni.showToast({
                  title: e.content.text,
                  icon: 'none',
                })
              }
            }
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
