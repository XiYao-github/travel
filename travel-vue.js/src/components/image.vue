<template>
  <div>
    <el-upload
        :action="uploadUrl"
        :show-file-list="true"
        :file-list="fileUrlList"
        multiple
        ref="upload"
        list-type="picture-card"
        :on-change="handleChange"
        :on-success="handleSuccess"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
    >
      <div>
        <i class="el-icon-plus"/>
      </div>
    </el-upload>
    <el-dialog :visible.sync="dialogVisibleImage">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>

export default {
  props: {
    uploadUrl: {
      type: String,
    },
    fileList: {
      type: Array,
      default: function () {
        return []
      }
    }
  },
  data() {
    return {
      dialogImageUrl: '',
      dialogVisibleImage: false,
      fileUrlList: [],
      urlList: []
    };
  },
  created() {
    this.fileList.forEach(url => {
      this.fileUrlList.push({name: '', url: url})
      this.urlList.push(url)
    })
  },
  watch: {
    fileList(newVal, oldVal) {
      console.log("监听组件传值",newVal, oldVal)
      this.fileUrlList = [];
      this.urlList = [];
      this.fileList.forEach(url => {
        this.fileUrlList.push({name: '', url: url})
        this.urlList.push(url)
      })
    }
  },
  mounted() {
    console.log('urlList', this.fileUrlList)
    console.log('url', this.urlList)
  },
  methods: {
    handlePreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleRemove(file, fileList) {
      this.urlList.forEach(function (item, index, arr) {
        if (item === file.url) {
          arr.splice(index, 1);
        }
      });
      console.log(this.urlList)
      this.$emit('upload-remove', this.urlList)
      console.log('删除', file, fileList);
    },
    handleSuccess(response, file, fileList) {
      this.urlList.push(response.data)
      console.log(this.urlList)
      this.$emit('upload-success', this.urlList)
      console.log('成功', response, file, fileList);
    },
    handleChange(file, fileList) {
      console.log('改变', file, fileList);
    },
  }
}
</script>