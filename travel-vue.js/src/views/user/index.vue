<template>
  <div>
    <div>
      <el-input placeholder="用户名" prefix-icon="el-icon-search" v-model="params.username" clearable
                style="width: 240px;margin-right: 15px;"/>
      <el-select v-model="params.status" placeholder="用户状态" style="margin-right: 15px;" clearable>
        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
    </div>
    <div style="margin-top: 40px; margin-bottom: 10px;">
      <el-table :data="tableData.records" style="width: 100%;">
        <el-table-column prop="createdTime" label="注册时间"/>
        <el-table-column prop="username" label="用户名"/>
        <el-table-column prop="phone" label="手机号码"/>
        <el-table-column prop="address" label="地址"/>
        <el-table-column prop="genderText" label="性别"/>
        <el-table-column fixed="right" label="状态" width="100">
          <template slot-scope="scope">
            <el-button plain v-if="!scope.row.status" type="warning" size="mini" @click="handleEdit(scope.row)">禁用
            </el-button>
            <el-button plain v-if="scope.row.status" type="success" size="mini" @click="handleEdit(scope.row)">启用
            </el-button>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
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
import {page, info, update, deleteBatch} from '@/api/user'

export default {
  data() {
    return {
      params: {
        username: '',
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
      console.log('分页查询', this.tableData);
    },
    async info(row) {
      // 查询数据详情
      const {data} = await info(row.id);
      this.formData = data.data;
      console.log('数据详情', this.formData);
    },
    async handleEdit(row) {
      // 查询数据详情
      await this.info(row);
      if (this.formData.status === 1) {
        //禁用
        this.formData.status = 0;
        const {data} = await update(this.formData);
        this.formData = data.data;
      } else if (this.formData.status === 0) {
        //启用
        this.formData.status = 1;
        const {data} = await update(this.formData);
        this.formData = data.data;
      }
      console.log('修改数据', this.formData);
      await this.search();
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
        console.log('取消删除')
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