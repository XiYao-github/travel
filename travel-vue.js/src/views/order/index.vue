<template>
  <div>
    <div>
      <el-input placeholder="订单编号" prefix-icon="el-icon-search" v-model="params.num" clearable
                style="width: 240px;margin-right: 15px;"/>
      <el-select v-model="params.type" placeholder="订单类型" style="margin-right: 15px;" clearable>
        <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <el-select v-model="params.status" placeholder="订单状态" style="margin-right: 15px;" clearable>
        <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
    </div>
    <div style="margin-top: 40px; margin-bottom: 10px;">
      <el-table :data="tableData.records" style="width: 100%;">
        <el-table-column type="expand">
          <template slot-scope="scope">
            <el-timeline>
              <el-timeline-item v-for="statusDetailed in scope.row.statusList" :key="statusDetailed.status"
                                placement="top" type="primary" :timestamp="statusDetailed.desc">
                {{ statusDetailed.title }}
              </el-timeline-item>
            </el-timeline>
          </template>
        </el-table-column>
        <el-table-column prop="num" label="订单编号"/>
        <el-table-column prop="username" label="用户名称"/>
        <el-table-column prop="price" label="订单价格"/>
        <el-table-column prop="typeText" label="订单类型"/>
        <el-table-column prop="createdTime" label="创建时间"/>
        <el-table-column prop="updatedTime" label="更新时间"/>
        <el-table-column fixed="right" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag v-show="scope.row.status===0" type="info">{{ scope.row.statusText }}</el-tag>
            <el-tag v-show="scope.row.status===1" type="info">{{ scope.row.statusText }}</el-tag>
            <el-tag v-show="scope.row.status===2" type="primary">{{ scope.row.statusText }}</el-tag>
            <el-tag v-show="scope.row.status===3" type="warning">{{ scope.row.statusText }}</el-tag>
            <el-tag v-show="scope.row.status===4" type="info">{{ scope.row.statusText }}</el-tag>
            <el-tag v-show="scope.row.status===5" type="success">{{ scope.row.statusText }}</el-tag>
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
import {page, deleteBatch} from "@/api/order";

export default {
  data() {
    return {
      params: {
        num: '',
        type: '',
        status: '',
        current: 1,
        size: 5
      },
      tableData: {
        records: [],
        total: 3
      },
      typeOptions: [{
        value: '1',
        label: '景点'
      }, {
        value: '2',
        label: '套餐'
      }],
      statusOptions: [{
        value: '0',
        label: '交易取消'
      }, {
        value: '1',
        label: '买家下单'
      }, {
        value: '2',
        label: '卖家确认'
      }, {
        value: '3',
        label: '买家签收'
      }, {
        value: '4',
        label: '买家评价'
      }, {
        value: '5',
        label: '交易完成'
      }],
    }
  },
  mounted() {
    this.search();
  },
  methods: {
    async search() {
      const {data} = await page(this.params);
      console.log('分页查询', data.data);
      this.tableData = data.data;
      console.log('分页查询', this.tableData);
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