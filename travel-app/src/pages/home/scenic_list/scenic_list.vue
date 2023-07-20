<template>
  <view>
    <view>
      <uni-search-bar bgColor="#ffffff" radius="5" placeholder="搜索旅游景区..." clearButton="auto" cancelButton="none"
        @confirm="search" />
    </view>

    <!--   <view>
      <swiper style="height: 400rpx;" indicator-dots autoplay circular>
        <swiper-item v-for="(item, index) in imageList" :key="index">
          <image :src="item" mode="aspectFill" style="width: 100%; height: 100%;" />
        </swiper-item>
      </swiper>
    </view> -->

    <view>
      <uni-card :is-full="true" :isShadow="true" v-for="(item, index) in scenicList" :key="index">
        <view style="display: flex; flex-direction: row;">
          <image style="border-radius: 30rpx; width: 330rpx; height: 220rpx;" :src="item.image[0]" mode="aspectFill" />

          <uni-card :is-full="true" :is-shadow="false" :border="false">
            <view style="margin-top: 0rpx;">
              <text style="font-size: 50rpx; font-weight: bold;">{{item.name}}</text>
            </view>
            <view style="margin-top: 20rpx;">
              <text style="font-size: 30rpx; color: red;">{{item.price}}¥</text>
            </view>
            <view style=" margin-top: 15rpx;">
              <navigator :url="'/pages/home/scenic_info/scenic_info?id='+item.id">
                <text style="font-size: 25rpx; color: darkgrey;">景点详情 ></text>
              </navigator>
            </view>
          </uni-card>
        </view>
      </uni-card>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        searchValue: '',
        // imageList: [
        //   "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
        //   "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
        // ],
        scenicList: [{
          id: 1,
          name: "景点1",
          description: "长城你曾抵御了北方匈奴，保卫了这块广阔的炎黄子孙的国土，你是龙的象征，是民族的骄傲，这里的每一块砖石，每一个城垛，每一座墙基都像一根硕大的绳子缚住了龙的躯体。漫漫几千年过去了，如今，我们看见天下第一关的大门已经敞开了，中华民族这条巨龙，终将摆脱这条绳子，吹着滔天浩气，闪着万片金鳞，从这扇大门腾飞出去……",
          address: "广阔的炎黄子孙的国土",
          price: 63,
          score: 5,
          image: [
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
        }, {
          id: 2,
          name: "景点1",
          description: "长城你曾抵御了北方匈奴，保卫了这块广阔的炎黄子孙的国土，你是龙的象征，是民族的骄傲，这里的每一块砖石，每一个城垛，每一座墙基都像一根硕大的绳子缚住了龙的躯体。漫漫几千年过去了，如今，我们看见天下第一关的大门已经敞开了，中华民族这条巨龙，终将摆脱这条绳子，吹着滔天浩气，闪着万片金鳞，从这扇大门腾飞出去……",
          address: "广阔的炎黄子孙的国土",
          price: 63,
          score: 3,
          image: [
            "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg"
          ],
        }, {
          id: 3,
          name: "景点1",
          description: "长城你曾抵御了北方匈奴，保卫了这块广阔的炎黄子孙的国土，你是龙的象征，是民族的骄傲，这里的每一块砖石，每一个城垛，每一座墙基都像一根硕大的绳子缚住了龙的躯体。漫漫几千年过去了，如今，我们看见天下第一关的大门已经敞开了，中华民族这条巨龙，终将摆脱这条绳子，吹着滔天浩气，闪着万片金鳞，从这扇大门腾飞出去……",
          address: "广阔的炎黄子孙的国土",
          price: 63,
          score: 5,
          image: [
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
        }, ]
      };
    },
    onLoad(option) {
      console.log("景点", option.name);
      if (option.name) {
        this.searchValue = option.name;
      }
    },
    onShow() {
      console.log('景点：', this.searchValue);
      uni.request({
        url: this.$baseUrl + '/travel/scenic/list',
        method: 'GET',
        data: {
          name: this.searchValue,
          status: 1,
        },
        success: res => {
          // console.log('success', res.data);
          // console.log('success', res.data.data);
          this.scenicList = res.data.data;
          console.log('scenicList', this.scenicList);
        },
        fail: err => {
          console.log('fail', err);
        },
      });
    },
    methods: {
      search(res) {
        this.searchValue = res.value,
          console.log(this.searchValue);
        uni.showToast({
          title: '搜索：' + res.value,
          icon: 'none',
        })
        console.log('景点：', this.searchValue);
        uni.request({
          url: this.$baseUrl + '/travel/scenic/list',
          method: 'GET',
          data: {
            name: this.searchValue
          },
          success: res => {
            // console.log('success', res.data);
            // console.log('success', res.data.data);
            this.scenicList = res.data.data;
            console.log('scenicList', this.scenicList);
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
