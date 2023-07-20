<template>
  <view>
    <uni-card style="border-radius: 20rpx; " :is-shadow="false" :border="false" margin="10rpx" spacing="0rpx">
      <uni-forms ref="dynamicForm" :rules="dynamicRules" :modelValue="dynamicFormData" label-position="top">
        <uni-forms-item name="title">
          <view slot="label"
            style="padding: 10rpx; display: flex; flex-direction: row; justify-content: space-between;">
            <view style="display: flex; flex-direction: row; ">
              <text style="color:red;">*</text>
              <text>标题</text>
            </view>
            <view style="display: flex; flex-direction: row;">
              <text>添加段落</text>
              <uni-icons @click="add" type="plus" color="#55aa7f" size="20" />
            </view>
          </view>
          <view>
            <uni-easyinput v-model="dynamicFormData.title" placeholder="请输入游记标题" />
          </view>
        </uni-forms-item>

        <uni-card :is-shadow="false" :border="false" margin="0rpx" spacing="0rpx" padding="0rpx"
          v-for="(item,index) in dynamicFormData.content" :key="item.id">
          <uni-forms-item :rules="dynamicFormData.content[index].titleRules" :name="['content',index,'title']">
            <view slot="label"
              style="padding: 10rpx; display: flex; flex-direction: row; justify-content: space-between;">
              <view style="display: flex; flex-direction: row;">
                <text style="color:red;">*</text>
                <text>段落</text>
              </view>
              <view style="display: flex; flex-direction: row;">
                <text>添加</text>
                <uni-icons @click="add" type="plus" color="#55aa7f" size="20" />
                <text>删除</text>
                <uni-icons @click="del(item.id)" type="minus" color="#ff557f" size="20" />
              </view>
            </view>
            <view>
              <uni-easyinput v-model="dynamicFormData.content[index].title" placeholder="请输入段落标题" />
            </view>
          </uni-forms-item>
          <uni-forms-item :rules="dynamicFormData.content[index].contentRules" :name="['content',index,'content']">
            <view slot="label" style="height: 0rpx;" />
            <view>
              <uni-easyinput type="textarea" autoHeight maxlength="-1" v-model="dynamicFormData.content[index].content"
                placeholder="请输入段落内容" />
            </view>
          </uni-forms-item>
        </uni-card>

        <uni-file-picker v-model="imageValue" fileMediatype="image" mode="grid" :limit="3" @select="select"
          @delete="deleteImage" title="游记图片" />
        <button style="border-radius: 50rpx; margin-top: 30rpx;" type="primary"
          @click="submit('dynamicForm')">保存</button>
      </uni-forms>
    </uni-card>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        user: {},
        // 数据源
        dynamicFormData: {
          title: '',
          content: [],
          image: []
        },
        // 规则
        dynamicRules: {
          title: {
            rules: [{
              required: true,
              errorMessage: '标题不能为空'
            }]
          }
        },
        imageValue: [], //回显图片集合
        imageList: [], //本地图片路径
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
      };
      this.add();
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
      // 新增表单域
      add() {
        this.dynamicFormData.content.push({
          title: '',
          content: '',
          titleRules: [{
            required: true,
            errorMessage: '段落标题不能为空'
          }],
          contentRules: [{
            required: true,
            errorMessage: '段落内容不能为空'
          }],
          id: Date.now() % 100000,
        })
        console.log(this.dynamicFormData.content);
      },
      // 删除表单域
      del(id) {
        console.log(this.dynamicFormData.content);
        let index = this.dynamicFormData.content.findIndex(v => v.id === id)
        this.dynamicFormData.content.splice(index, 1)
      },
      submit(ref) {
        this.$refs[ref].validate().then(res => {
          this.upload().then(imageUrls => {
            console.log('图片路径', imageUrls);
            this.dynamicFormData.image = imageUrls;
            this.dynamicFormData.userId = this.user.id;
            console.log('表单属性', this.dynamicFormData);
            uni.request({
              url: this.$baseUrl + '/travel/notes',
              method: 'POST',
              data: this.dynamicFormData,
              success: res => {
                console.log('success', res);
                uni.showToast({
                  title: `提交成功`
                });
                uni.redirectTo({
                  url: '/pages/user/user_notes/user_notes'
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

</style>
