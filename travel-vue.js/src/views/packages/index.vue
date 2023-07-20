<template>
  <div>
    <div style="display: flex; justify-content: space-between;">
      <div>
        <el-input placeholder="套餐名称" prefix-icon="el-icon-search" v-model="params.name" clearable
                  style="width: 240px;margin-right: 15px;"/>
        <el-select v-model="params.status" placeholder="套餐状态" style="margin-right: 15px;" clearable>
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
        <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
      </div>
      <div>
        <el-button type="success" style="margin-right: 15px;" @click="saveOrUpdate()">新增</el-button>
      </div>
    </div>
    <div style="margin-top: 40px; margin-bottom: 10px;">
      <el-table :data="tableData.records" style="width: 100%;">
        <el-table-column fixed type="expand">
          <template slot-scope="scope">
            <el-descriptions direction="vertical" :column="1" border>
              <el-descriptions-item label="套餐描述">
                <div style="margin: 20px">
                  &#8195;&#8195;{{ scope.row.description }}
                </div>
              </el-descriptions-item>
              <el-descriptions-item label="套餐景点">
                <el-table :data="scope.row.scenicList" style="width: 100%;">
                  <el-table-column prop="name" label="景点名称"/>
                  <el-table-column prop="address" label="景点地址"/>
                  <el-table-column prop="price" label="景点价格"/>
                  <el-table-column prop="image" label="景点图片">
                    <template slot-scope="scope">
                      <el-image style="width: 50px; height: 50px" :preview-src-list="scope.row.image"
                                v-for="url in scope.row.image.slice(0, 3)" :key="url" :src="url" fit="cover" lazy/>
                    </template>
                  </el-table-column>
                </el-table>
              </el-descriptions-item>
              <el-descriptions-item label="套餐图片">
                <el-image style="width: 200px; height: 200px;margin-left: 20px;" :preview-src-list="scope.row.image"
                          v-for="url in scope.row.image" :key="url" :src="url" fit="cover" lazy/>
              </el-descriptions-item>
            </el-descriptions>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="套餐名称"/>
        <el-table-column prop="address" label="套餐区域"/>
        <el-table-column prop="price" label="套餐价格"/>
        <el-table-column prop="createdTime" label="创建时间"/>
        <el-table-column prop="updatedTime" label="更新时间"/>
        <el-table-column prop="image" label="套餐图片">
          <template slot-scope="scope">
            <el-image style="width: 50px; height: 50px" :preview-src-list="scope.row.image"
                      v-for="url in scope.row.image.slice(0, 3)" :key="url" :src="url" fit="cover" lazy/>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag v-show="scope.row.status" type="success">正常</el-tag>
            <el-tag v-show="!scope.row.status" type="warning">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="saveOrUpdate(scope.row)">编辑</el-button>
            <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div>
      <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="40%" center>
        <el-form label-position="right" label-width="80px" :model="formData">
          <el-form-item label="套餐名称" prop="name">
            <el-input v-model="formData.name"/>
          </el-form-item>
          <el-form-item label="套餐区域" prop="address">
            <el-input v-model="formData.address"/>
          </el-form-item>
          <el-form-item label="套餐价格" prop="price">
            <el-input v-model="formData.price"/>
          </el-form-item>
          <el-form-item label="套餐状态" prop="status">
            <el-switch v-model="formData.status" :active-value="1" :inactive-value="0"
                       active-color="#409EFF" inactive-color="#C0CCDA" active-text="正常" inactive-text="禁用"/>
          </el-form-item>
          <el-form-item label="套餐描述" prop="description">
            <el-input type="textarea" v-model="formData.description" :autosize="rows"/>
          </el-form-item>
          <el-form-item label="套餐景点" prop="scenicList">
            <el-select v-model="formData.scenicIdList" multiple placeholder="请选择">
              <el-option v-for="item in scenicOptions" :key="item.id" :label="item.name" :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="套餐图片" prop="image">
            <template>
              <div>
                <el-upload
                    :action="uploadUrl" :show-file-list="true" :file-list="fileList"
                    multiple ref="upload" list-type="picture-card" :on-change="handleChange"
                    :on-success="handleSuccess" :on-preview="handlePreview" :on-remove="handleRemove">
                  <div>
                    <i class="el-icon-plus"/>
                  </div>
                </el-upload>
                <el-dialog :visible.sync="dialogVisibleImage">
                  <img width="100%" :src="dialogImageUrl" alt="">
                </el-dialog>
              </div>
            </template>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm()">确定</el-button>
            <el-button @click="dialogVisible = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
    <div>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="params.current" :page-sizes="[5,10,15,20]" :page-size="params.size"
                     layout="prev, pager, next, jumper, ->, sizes, total" :total="tableData.total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import {page, info, insert, update, deleteBatch} from '@/api/packages'
import {list} from '@/api/scenic'

export default {
  data() {
    return {
      uploadUrl: '/travel/image/upload',
      fileList: [],
      urlList: [],
      dialogImageUrl: '',
      dialogVisibleImage: false,
      rows: {
        minRows: 2,
        maxRows: 6
      },
      params: {
        name: '',
        status: '',
        current: 1,
        size: 5
      },
      tableData: {
        records: [],
        total: 3
      },
      options: [{
        value: '0',
        label: '禁用'
      }, {
        value: '1',
        label: '正常'
      }],
      dialogVisible: false,
      dialogTitle: '',
      modifyState: false,
      formData: {},
      scenicOptions: [],
    }
  },
  mounted() {
    this.search();
  },
  methods: {
    async search() {
      const {data} = await page(this.params);
      this.tableData = data.data;
      console.log('分页查询数据', this.tableData);
    },
    async saveOrUpdate(row) {
      const {data} = await list();
      this.scenicOptions = data.data;
      console.log('查询景点列表',this.scenicOptions);
      this.fileList = [];
      this.urlList = [];
      this.formData = {};
      if (row) {
        // 查询数据详情
        const {data} = await info(row.id);
        this.formData = data.data;
        this.formData.image.forEach(url => {
          this.fileList.push({name: '', url: url})
          this.urlList.push(url)
        })
        console.log('数据详情', this.formData);
        this.modifyState = true;
        this.dialogVisible = true;
        this.dialogTitle = '编辑';
      } else {
        this.modifyState = false;
        this.dialogVisible = true;
        this.dialogTitle = '新增';
      }
    },
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
      console.log('删除', file, fileList);
    },
    handleSuccess(response, file, fileList) {
      this.urlList.push(response.data)
      console.log(this.urlList)
      console.log('成功', response, file, fileList);
    },
    handleChange(file, fileList) {
      console.log(this.urlList)
      console.log('改变', file, fileList);
    },
    async submitForm() {
      this.formData.image = this.urlList;
      if (this.modifyState) {
        // 编辑接口对接
        const {data} = await update(this.formData);
        this.formData = data.data;
        console.log('修改数据', this.formData);
        this.dialogVisible = false;
        await this.search();
      } else {
        // 新增接口对接
        const {data} = await insert(this.formData);
        this.formData = data.data;
        console.log('新增数据', this.formData);
        this.dialogVisible = false;
        await this.search();
      }
    },
    async handleDelete(row) {
      try {
        await this.$confirm('是否确定删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await deleteBatch([row.id]);
        console.log('删除数据：' + [row.id]);
        await this.handleCurrentChange(1);
      } catch (e) {
        console.log('取消删除');
      }
    },
    async handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.params.size = val;
      await this.search();
    },
    async handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.params.current = val;
      await this.search();
    }
  }
}
</script>

<style scoped>

</style>