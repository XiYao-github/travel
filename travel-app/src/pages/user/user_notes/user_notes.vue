<template>
  <view>
    <view>
      <uni-list>
        <uni-card style="border-radius: 20rpx; " :is-shadow="true" :border="true" margin="10rpx" spacing="0rpx"
          padding="0rpx" v-for="(item, index) in notesList" :key="index">
          <uni-list-item direction="column" :to="'/pages/user/user_notes/user_notes_info/user_notes_info?id='+item.id">
            <template v-slot:header>
              <uni-title type="h3" align="center" :title="item.title" />
            </template>
            <template v-slot:body>
              <view>
                <image style="width: 100%; height: 200px; " :src="item.image[0]" mode="aspectFill" />
              </view>
            </template>
            <template v-slot:footer>
              <view>
                <view style="display: flex; flex-direction: row; align-items: center; justify-content: space-between;">
                  <uni-title type="h5" :title="item.username+' '+item.createdTime" />
                  <text v-show="item.status===1" style="margin-right: 10rpx; font-size: 28rpx; color: coral;">
                    {{item.statusText}}
                  </text>
                  <text v-show="item.status===2" style="margin-right: 10rpx; font-size: 28rpx; color: deepskyblue;">
                    {{item.statusText}}
                  </text>
                </view>
              </view>
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
        user: {},
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
      console.log('用户游记：', this.user.id);
      uni.request({
        url: this.$baseUrl + '/travel/notes/list',
        method: 'GET',
        data: {
          userId: this.user.id
        },
        success: res => {
          // console.log('success', res.data);
          // console.log('success', res.data.data);
          this.notesList = res.data.data;
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
