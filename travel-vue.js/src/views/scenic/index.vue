<template>
  <div>
    <div style="display: flex; justify-content: space-between;">
      <div>
        <el-input placeholder="景点名称" prefix-icon="el-icon-search" v-model="params.name" clearable
                  style="width: 240px;margin-right: 15px;"/>
        <el-select v-model="params.status" placeholder="景点状态" style="margin-right: 15px;" clearable>
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
              <el-descriptions-item label="景点描述">
                <div style="margin: 20px">
                  &#8195;&#8195;{{ scope.row.description }}
                </div>
              </el-descriptions-item>
              <el-descriptions-item label="景点图片">
                <el-image style="width: 200px; height: 200px;margin-left: 20px;" :preview-src-list="scope.row.image"
                          v-for="url in scope.row.image" :key="url" :src="url" fit="cover" lazy/>
              </el-descriptions-item>
            </el-descriptions>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="景点名称"/>
        <el-table-column prop="address" label="景点地址"/>
        <el-table-column prop="price" label="景点价格"/>
        <el-table-column prop="createdTime" label="创建时间"/>
        <el-table-column prop="updatedTime" label="更新时间"/>
        <el-table-column prop="image" label="景点图片">
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
          <el-form-item label="景点名称" prop="name">
            <el-input v-model="formData.name"/>
          </el-form-item>
          <el-form-item label="景点地址" prop="address">
            <el-input v-model="formData.address"/>
          </el-form-item>
          <el-form-item label="景点价格" prop="price">
            <el-input v-model="formData.price"/>
          </el-form-item>
          <el-form-item label="景点状态" prop="status">
            <el-switch v-model="formData.status" :active-value="1" :inactive-value="0"
                       active-color="#409EFF" inactive-color="#C0CCDA" active-text="正常" inactive-text="禁用"/>
          </el-form-item>
          <el-form-item label="景点描述" prop="description">
            <el-input type="textarea" v-model="formData.description" :autosize="rows"/>
          </el-form-item>
          <el-form-item label="景区图片" prop="image">
            <UploadImage ref="upload" :upload-url="uploadUrl" :file-list="formData.image"
                         @upload-remove="handleRemove" @upload-success="handleSuccess"></UploadImage>
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
import {page, info, insert, update, deleteBatch} from '@/api/scenic'
import UploadImage from '@/components/image.vue'

export default {
  components: {
    UploadImage
  },
  data() {
    return {
      uploadUrl: '/travel/image/upload',
      urlList: [],
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
      formData: {}
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
      this.urlList = [];
      this.formData = {};
      if (row) {
        // 查询数据详情
        const {data} = await info(row.id);
        this.formData = data.data;
        this.urlList = this.formData.image;
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
    handleSuccess(urlList) {
      this.urlList = urlList;
      console.log('父组件成功', this.urlList);
    },
    handleRemove(urlList) {
      this.urlList = urlList;
      console.log('父组件删除', this.urlList);
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