# travel-vue.js

### 环境准备

#### 1) 安装 nvm

nvm (node version manager)，切换 node.js 版本的工具，安装后 nvm 会自动添加环境变量，但可能需要手工添加 node.js 的 PATH 环境变量

选用【以管理员身份运行】cmd 程序来执行 nvm 命令

设置国内镜像地址

```
nvm node_mirror http://npm.taobao.org/mirrors/node/
nvm npm_mirror https://npm.taobao.org/mirrors/npm/
```

查看可用版本

```
nvm list available
```

安装 LTS（长期支持版）

```
nvm install 16.19.1
```

查看已安装版本

```
nvm list
```

切换到 16.19.1

```
nvm use 16.19.1
```



#### 2) 检查 npm

npm 是 js 的包管理器，就类似于 java 界的 maven，要确保它使用的是国内镜像

检查镜像

```
npm get registry
```

设置国内镜像

```
npm config set registry https://registry.npm.taobao.org/
```



### 安装依赖包
```
npm install
```

### 运行项目

端口：8088

```
npm run serve
```
