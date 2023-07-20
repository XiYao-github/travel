<template>
  <view>
    <view>
      <uni-notice-bar showIcon showClose scrollable :text="noticeText" />
    </view>

    <!--    <view>
      <uni-search-bar bgColor="#ffffff" radius="5" placeholder="搜索旅游景区..." clearButton="auto" cancelButton="none"
        @confirm="search" />
    </view> -->

    <view>
      <swiper style="height: 400rpx;" indicator-dots autoplay circular>
        <swiper-item v-for="(item, index) in imageList" :key="index">
          <image style="width: 100%; height: 100%;" :src="item.image[0]" mode="aspectFill"
            @click="swiperChange(item.id)" />
        </swiper-item>
      </swiper>
    </view>

    <view>
      <uni-list>
        <uni-card style="border-radius: 20rpx; " :is-shadow="true" :border="true" margin="10rpx" spacing="0rpx"
          padding="0rpx" v-for="(item, index) in notesList" :key="index">
          <uni-list-item direction="column" :to="'/pages/notes/notes_info/notes_info?id='+item.id">
            <template v-slot:header>
              <uni-title type="h3" align="center" :title="item.title" />
            </template>
            <template v-slot:body>
              <view>
                <image style="width: 100%; height: 200px; " :src="item.image[0]" mode="aspectFill" />
              </view>
            </template>
            <template v-slot:footer>
              <uni-title type="h5" :title="item.username+' '+item.createdTime" />
            </template>
          </uni-list-item>
        </uni-card>
      </uni-list>
    </view>

  </view>
</template>

<script>
  export default {
    data() {
      return {
        searchValue: '',
        noticeText: '适合五一的旅行地有八达岭长城、杭州西湖、桐乡乌镇、四川九寨沟、峨眉山、张家界武陵源、桂林山水、安徽黄山、云南丽江古城、厦门鼓浪屿',
        imageList: [{
          id: 1,
          image: [
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
        }, {
          id: 14,
          image: [
            "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg"
          ],
        }, {
          id: 15,
          image: [
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
        }, ],
        notesList: [{
          id: '2',
          title: '滕王阁位于沿江北路',
          image: [
            "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
          username: '张三',
          createdTime: '1999-7-5 12:34:56',
        }, {
          id: '3',
          title: '滕王阁位于沿江北路',
          image: [
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
          username: '张三',
          createdTime: '1999-7-5 12:34:56',
        }, {
          id: '4',
          title: '滕王阁位于沿江北路',
          image: [
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ],
          username: '张三',
          createdTime: '1999-7-5 12:34:56',
        }],
      }
    },
    onLoad() {},
    onShow() {
      // console.log('游记：', this.searchValue);
      uni.request({
        url: this.$baseUrl + '/travel/notes/list',
        method: 'GET',
        data: {
          status: 2
        },
        success: res => {
          // console.log('success', res.data);
          // console.log('success', res.data.data);
          this.notesList = res.data.data;
          this.imageList = res.data.data.slice(0, 3);
          console.log('notesList', this.notesList);
        },
        fail: err => {
          console.log('fail', err);
        },
      });
    },
    methods: {
      swiperChange(id) {
        console.log(id)
        uni.navigateTo({
          url: '/pages/notes/notes_info/notes_info?id=' + id
        });
      }
    }
  }
</script>

<style lang="scss">

</style>
