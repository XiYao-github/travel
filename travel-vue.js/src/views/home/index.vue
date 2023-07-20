<template>
  <div style="width: 100%">
    <div class="div_button">
      <el-button round icon="el-icon-s-home">欢迎首页</el-button>
      <el-button type="primary" round icon="el-icon-ship">景区管理</el-button>
      <el-button type="info" round icon="el-icon-menu">套餐管理</el-button>
    </div>
    <div class="div_button">
      <el-button type="success" round icon="el-icon-edit-outline">游记管理</el-button>
      <el-button type="warning" round icon="el-icon-date">订单管理</el-button>
      <el-button type="danger" round icon="el-icon-s-custom">用户管理</el-button>
    </div>
  </div>
</template>

<style scoped>
.div_button {
  width: 100%;
  display: flex;
  justify-content: center;
}

.el-button {
  padding: 10px;
  margin: 10px;
  width: 30%;
  height: 100px;
}
</style>