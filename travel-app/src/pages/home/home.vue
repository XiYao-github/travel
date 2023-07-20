<template>
  <view>
    <view>
      <uni-search-bar bgColor="#ffffff" radius="5" placeholder="搜索旅游景区..." clearButton="auto" cancelButton="none"
        @confirm="search" />
    </view>

    <view>
      <swiper style="height: 400rpx;" indicator-dots autoplay circular>
        <swiper-item v-for="(item, index) in imageList" :key="index">
          <image style="width: 100%; height: 100%;" :src="item.image[0]" mode="aspectFill"
            @click="swiperChange(item.id)" />
        </swiper-item>
      </swiper>
    </view>

    <view>
      <uni-grid :column="4" :showBorder="false" :square="false" @change="scenicChange">
        <uni-grid-item v-for="(item, index) in scenicList" :index="index" :key="index">
          <view style="display: flex; flex-direction: column; align-items: center; padding: 30rpx;">
            <image style=" width: 80rpx; height: 80rpx;" :src="item.url" />
            <text>{{ item.text }}</text>
          </view>
        </uni-grid-item>
      </uni-grid>
    </view>

    <view style="margin-bottom: 30rpx;">
      <uni-grid :column="2" :showBorder="false" :square="false" @change="packagesChange">
        <uni-grid-item v-for="(item, index) in packagesList" :index="index" :key="index">
          <view style="display: flex; flex-direction: row; align-items: center; ">
            <image style="width: 160rpx; height: 160rpx; margin-left: 40rpx;" :src="item.url" />
            <text style="margin-left: 20rpx;">{{ item.text }}</text>
          </view>
        </uni-grid-item>
      </uni-grid>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        searchValue: '',
        imageList: [{
          id: 3,
          image: [
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
        }, {
          id: 4,
          image: [
            "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg"
          ],
        }, {
          id: 7,
          image: [
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
        }, ],
        scenicList: [{
          url: '/static/image/古城.png',
          text: '古城',
        }, {
          url: '/static/image/城市.png',
          text: '城市',
        }, {
          url: '/static/image/海滨.png',
          text: '海滨',
        }, {
          url: '/static/image/山水.png',
          text: '山水',
        }],
        packagesList: [{
          url: '/static/image/河流.png',
          text: '河流',
          id: 1,
        },{
          url: '/static/image/公园.png',
          text: '公园',
          id: 2,
        },{
          url: '/static/image/松树.png',
          text: '松树',
          id: 3,
        },{
          url: '/static/image/峡谷.png',
          text: '峡谷',
          id: 4,
        },{
          url: '/static/image/山河.png',
          text: '山河',
          id: 5,
        },{
          url: '/static/image/小岛.png',
          text: '小岛',
          id: 6,
        },{
          url: '/static/image/树丛.png',
          text: '树丛',
          id: 7,
        },{
          url: '/static/image/冰川.png',
          text: '冰川',
          id: 8,
        }],
      }
    },
    onLoad() {
      // try {
      //   const value = uni.getStorageSync('storage_key');
      //   if (value) {
      //     console.log("onLoad", value);
      //   }
      // } catch (e) {
      //   // error  
      // }
      // try {
      //   const value = uni.getStorageSync('storage_key');
      //   if (value) {
      //     console.log("onShow", value);
      //   }
      // } catch (e) {
      //   // error
      // }
    },
    onShow() {
      uni.request({
        url: this.$baseUrl + '/travel/scenic/list',
        method: 'GET',
        success: res => {
          // console.log('success', res.data);
          // console.log('success', res.data.data);
          this.imageList = res.data.data.slice(0, 3);
          console.log('imageList', this.imageList);
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
      scenicChange(e) {
        let {
          index
        } = e.detail
        uni.showToast({
          title: `点击第${index+1}个宫格`,
          icon: 'none'
        })
        console.log('景点', index + 1, this.scenicList[index].text)
        uni.navigateTo({
          url: '/pages/home/scenic_list/scenic_list?name=' + this.scenicList[index].text
        });
      },
      packagesChange(e) {
        let {
          index
        } = e.detail
        uni.showToast({
          title: `点击第${index+1}个宫格`,
          icon: 'none'
        })
        console.log('套餐', index + 1, this.packagesList[index].text, this.packagesList[index].id);
        uni.navigateTo({
          url: '/pages/home/packages_info/packages_info?id=' + this.packagesList[index].id
        });
      },
      swiperChange(id) {
        console.log('景点', id)
        uni.navigateTo({
          url: '/pages/home/scenic_info/scenic_info?id=' + id
        });
      }
    }
  }
</script>

<style lang="scss">

</style>
