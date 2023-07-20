<template>
  <div>
    <div>
      <el-input placeholder="作者" prefix-icon="el-icon-search" v-model="params.username" clearable
                style="width: 240px;margin-right: 15px;"/>
      <el-input placeholder="标题" prefix-icon="el-icon-search" v-model="params.title" clearable
                style="width: 240px;margin-right: 15px;"/>
      <el-select v-model="params.status" placeholder="游计状态" style="margin-right: 15px;" clearable>
        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
      </el-select>
      <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
    </div>
    <div style="margin-top: 40px; margin-bottom: 10px;">
      <el-table :data="tableData.records" style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="scope">
            <el-descriptions direction="vertical" :column="1" border>
              <el-descriptions-item label="游记内容">
                <template>
                  <el-card>
                    <div slot="header" class="clearfix">
                      <div style="display: flex; justify-content: center">
                        <h2>{{ scope.row.title }}</h2>
                      </div>
                    </div>
                    <div style="margin: 20px" v-for="contentDetailed in scope.row.content"
                         :key="contentDetailed.id">
                      <h4>{{ contentDetailed.title }}</h4>
                      <div>&#8195;&#8195;{{ contentDetailed.content }}</div>
                    </div>
                  </el-card>
                </template>
              </el-descriptions-item>
              <el-descriptions-item label="景点图片">
                <el-image style="width: 200px; height: 200px;margin-left: 20px;" :preview-src-list="scope.row.image"
                          v-for="url in scope.row.image" :key="url" :src="url" fit="cover" lazy/>
              </el-descriptions-item>
            </el-descriptions>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="游记标题"/>
        <el-table-column prop="username" label="作者名称"/>
        <el-table-column prop="createdTime" label="创建时间"/>
        <el-table-column prop="updatedTime" label="更新时间"/>
        <el-table-column prop="image" label="游记图片">
          <template slot-scope="scope">
            <el-image style="width: 50px; height: 50px" :preview-src-list="scope.row.image"
                      v-for="url in scope.row.image.slice(0, 3)" :key="url" :src="url" fit="cover" lazy/>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag v-show="scope.row.status===1" type="warning">未审核</el-tag>
            <el-tag v-show="scope.row.status===2" type="success">已审核</el-tag>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="150">
          <template slot-scope="scope">
            <el-button v-if="scope.row.status===1" type="primary" size="mini" @click="handleEdit(scope.row)">通过
            </el-button>
            <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除
            </el-button>
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
import {info, page, update, deleteBatch} from '@/api/notes'

export default {
  data() {
    return {
      params: {
        username: '',
        title: '',
        status: '',
        current: 1,
        size: 5
      },
      tableData: {
        records: [],
        total: 3
      },
      options: [{
        value: '1',
        label: '未审核'
      }, {
        value: '2',
        label: '已审核'
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
      // 编辑接口对接
      this.formData.status = 2;
      const {data} = await update(this.formData);
      this.formData = data.data;
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