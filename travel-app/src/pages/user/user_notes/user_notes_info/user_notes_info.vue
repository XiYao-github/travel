<template>
  <view>
    <uni-card style="border-radius: 20rpx; " :is-shadow="false" :border="false" margin="10rpx" spacing="0rpx"
      padding="0rpx">
      <uni-title style="margin-top:20rpx;" type="h1" align="center" :title="notes.title" />
      <uni-card style="border-radius: 20rpx; " :is-shadow="false" :border="false" margin="10rpx" spacing="0rpx"
        v-for="(item,index) in notes.content" :key="index">
        <uni-title type="h3" :title="item.title" />
        <text>{{item.content}}</text>
        <image v-show="notes.image.length>index" mode="aspectFill" :src="notes.image[index]"
          style="width: 100%; height: 200px; " />
      </uni-card>
    </uni-card>

    <view v-show="notes.status===2">
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
            <uni-rate size="20" :value="item.score" />
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
  </view>
</template>

<script>
  export default {
    data() {
      return {
        notesId: 1,
        notes: {
          id: 3,
          title: 'uni-app介绍',
          content: [{
            title: '框架介绍',
            content: 'uni-app是一个使用Vue.js开发所有前端应用的框架，开发者编写一套代码，可发布到iOS、Android、H5、以及各种小程序（微信/支付宝/百度/头条/QQ/钉钉）等多个平台。即使不跨端，uni-app同时也是更好的小程序开发框架。DCloud公司拥有370万开发者用户，旗下uni-app有5万+案例、900款插件、50+微信/qq群，并且被阿里小程序工具内置，开发者可以放心选择。',
          }, {
            title: '框架介绍',
            content: 'uni-app是一个使用Vue.js开发所有前端应用的框架，开发者编写一套代码，可发布到iOS、Android、H5、以及各种小程序（微信/支付宝/百度/头条/QQ/钉钉）等多个平台。即使不跨端，uni-app同时也是更好的小程序开发框架。DCloud公司拥有370万开发者用户，旗下uni-app有5万+案例、900款插件、50+微信/qq群，并且被阿里小程序工具内置，开发者可以放心选择。',
          }, {
            title: '框架介绍',
            content: 'uni-app是一个使用Vue.js开发所有前端应用的框架，开发者编写一套代码，可发布到iOS、Android、H5、以及各种小程序（微信/支付宝/百度/头条/QQ/钉钉）等多个平台。即使不跨端，uni-app同时也是更好的小程序开发框架。DCloud公司拥有370万开发者用户，旗下uni-app有5万+案例、900款插件、50+微信/qq群，并且被阿里小程序工具内置，开发者可以放心选择。',
          }, ],
          image: [
            "https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg",
            "https://fuss10.elemecdn.com/9/bb/e27858e973f5d7d3904835f46abbdjpeg.jpeg"
          ]
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
        commentData: {},
        imageValue: [], //回显图片集合
        imageList: [], //本地图片路径
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
      console.log("游记", option.id);
      if (option.id) {
        this.notesId = option.id;
      }
    },
    onShow() {
      // console.log('游记：', this.searchValue);
      uni.request({
        url: this.$baseUrl + '/travel/notes/info',
        method: 'GET',
        data: {
          id: this.notesId
        },
        success: res => {
          // console.log('success', res.data);
          // console.log('success', res.data.data);
          this.notes = res.data.data;
          console.log('notes', this.notes);
        },
        fail: err => {
          console.log('fail', err);
        },
      });
      this.selectComment();
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
          this.commentData.itemId = this.notesId;
          this.commentData.type = 3
          console.log('表单属性', this.commentData);
          uni.request({
            url: this.$baseUrl + '/travel/comment',
            method: 'POST',
            data: this.commentData,
            success: res => {
              console.log('success', res);
              this.selectComment();
              this.del();
              uni.showToast({
                title: `评论成功`,
                icon: 'none',
              })
            },
            fail: err => {
              console.log('fail', err);
            },
          });
        }).catch(err => {
          console.log('err', err);
        })
      },
      selectComment() {
        uni.request({
          url: this.$baseUrl + '/travel/comment/list',
          method: 'GET',
          data: {
            itemId: this.notesId,
            type: 3
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
      },
      selectChange(e) {
        console.log("评分:", e);
        uni.request({
          url: this.$baseUrl + '/travel/comment/list',
          method: 'GET',
          data: {
            itemId: this.notesId,
            score: e,
            type: 3,
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
      add() {
        console.log(this.showForm);
        this.showForm = 1;
      },
      del() {
        console.log(this.showForm);
        this.showForm = 0;
        this.commentData = {};
        this.imageValue = []; //回显图片集合
        this.imageList = []; //本地图片路径
      },
    }
  }
</script>

<style lang="scss">

</style>
