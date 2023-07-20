<template>
  <el-container>
    <el-aside width="200px">
      <el-menu router :default-active="$route.path">
        <el-menu-item index="/home"><i class="el-icon-s-home"/>欢迎首页</el-menu-item>
        <el-menu-item index="/scenic"><i class="el-icon-ship"/>景区管理</el-menu-item>
        <el-menu-item index="/packages"><i class="el-icon-menu"/>套餐管理</el-menu-item>
        <el-menu-item index="/notes"><i class="el-icon-edit-outline"/>游记管理</el-menu-item>
        <el-menu-item index="/order"><i class="el-icon-date"/>订单管理</el-menu-item>
        <el-menu-item index="/user"><i class="el-icon-s-custom"/>用户管理</el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header>
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item v-for="item in breads" :key="item.title" :to="{ path: item.path }">
            {{ item.title }}
          </el-breadcrumb-item>
        </el-breadcrumb>

        <el-dropdown trigger="click">
          <el-avatar icon="el-icon-switch-button"/>
          <el-dropdown-menu slot="dropdown">
            <el-popover placement="top" width="160" v-model="visible">
              <p>是否退出管理系统？</p>
              <div style="text-align: right; margin: 0">
                <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                <el-button type="primary" size="mini" @click="logout()">确定</el-button>
              </div>
              <el-dropdown-item slot="reference">退出系统</el-dropdown-item>
            </el-popover>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      visible: false,
      breads: []
    };
  },
  created() {
    //一进入页面就调用面包屑方法
    this.liveBreads();
  },
  watch: {
    //监听路由发生的改变
    "$route.path"() {
      this.liveBreads();
    }
  },
  methods: {
    logout() {
      this.visible = false;
      console.log("退出登入");
      localStorage.clear()
      window.sessionStorage.clear()
      this.$router.push('/login')
    },
    liveBreads() {
      let temp = [];
      this.$route.matched.forEach(val => {
        if (val.meta.title) {
          temp.push({path: val.path, title: val.meta.title});
        }
      });
      this.breads = temp;
    },
  }
}
</script>

<style scoped>
.el-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.el-breadcrumb {
  margin: auto;
  width: 100%;
  padding: 10px;
}

.el-dropdown {
  margin: auto;
  padding: 10px;
}
</style>
  