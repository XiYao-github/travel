<template>
  <view>
    <uni-card style="border-radius: 20rpx; " :is-shadow="true" :border="false" margin="15rpx">
      <view class="view_icons">
        <view style="width: 200rpx;">
          <uni-file-picker :imageStyles="imageStyles" v-model="imageValue" fileMediatype="image" :limit="1"
            @select="select" @delete="deleteImage" :del-icon="false" mode="grid" disable-preview return-type="object" />
        </view>
      </view>
      <uni-forms ref="userForm" :rules="rules" :modelValue="user">
        <uni-forms-item label="用户名" name="username">
          <uni-easyinput type="text" v-model="user.username" placeholder="请输入用户名" />
        </uni-forms-item>
        <uni-forms-item label="手机号" name="phone">
          <uni-easyinput type="text" v-model="user.phone" placeholder="请输入手机号码" />
          <!--<uni-easyinput disabled type="text" v-model="user.phone" placeholder="请输入手机号码" /> -->
        </uni-forms-item>
        <uni-forms-item label="地址" name="address">
          <uni-easyinput type="text" v-model="user.address" placeholder="请输入详细地址" />
        </uni-forms-item>
        <uni-forms-item label="生日" name="birthday">
          <uni-datetime-picker type="date" v-model="user.birthday" />
        </uni-forms-item>
        <uni-forms-item label="性别" name="gender">
          <uni-data-checkbox mode="tag" v-model="user.gender" :localdata="sex" />
        </uni-forms-item>
      </uni-forms>
      <button style="border-radius: 50rpx; margin-top: 30rpx;" type="primary" @click="submit('userForm')">保存</button>
    </uni-card>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        rules: {
          username: {
            rules: [{
              required: true,
              errorMessage: '请填写姓名',
            }, {
              minLength: 2,
              maxLength: 6,
              errorMessage: '长度在 {minLength} 到 {maxLength} 个字符',
            }],
          },
        },
        user: {
          id: 1,
          username: '张三',
          phone: '17720202177',
          address: '江西',
          birthday: '1999-02-04',
          gender: 1,
          image: ["https://fuss10.elemecdn.com/0/6f/e35ff375812e6b0020b6b4e8f9583jpeg.jpeg"],
        },
        imageValue: [], //回显图片集合
        imageList: [], //本地图片路径
        imageStyles: {
          width: 100,
          height: 100,
          border: {
            radius: '50%'
          }
        },
        sex: [{
          text: '男',
          value: 1
        }, {
          text: '女',
          value: 2
        }, {
          text: '未知',
          value: 0
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
      console.log("头像", this.user.image);
      this.user.image.forEach(url => {
        this.imageValue.push({
          name: '',
          extname: 'png',
          url: url
        })
      });
      console.log("回显头像", this.imageValue);
    },
    methods: {
      // 获取上传状态
      select(e) {
        this.imageList = e.tempFilePaths;
        console.log('选择文件：', e, this.imageList)
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
      submit(ref) {
        this.$refs[ref].validate().then(res => {
          this.upload().then(imageUrls => {
            console.log('图片路径', imageUrls);
            if (imageUrls[0]) {
              this.user.image = imageUrls;
            }
            console.log('表单属性', this.user);
            uni.request({
              url: this.$baseUrl + '/travel/user',
              method: 'PUT',
              data: this.user,
              success: res => {
                console.log('success', res);
                console.log('success', res.data);
                console.log('success', res.data.data);
                uni.setStorageSync('userInfo', res.data.data);
                uni.showToast({
                  title: '更新成功',
                  icon: 'none',
                });
              },
              fail: err => {
                console.log('fail', err);
              },
            });
          }).catch(err => {
            console.log('err', err);
          })
        }).catch(err => {
          console.log('err', err);
        })
      },
    }
  }
</script>

<style lang="scss">
  .view_icons {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    margin-top: 30rpx;
    margin-bottom: 60rpx;
  }

  .image_icons {
    width: 200rpx;
    height: 200rpx;
    border-radius: 50%;
  }
</style>
