import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
    if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
    return originalPush.call(this, location).catch(err => err)
}

const routes = [
    {
        path: '/index',
        name: '首页',
        redirect: '/home',
        // redirect: '/scenic',
        meta: {title: '首页', needLogin: true},
        component: () => import('@/views'),
        children: [
            {
                path: '/home',
                name: '首页',
                meta: {needLogin: true},
                component: () => import('@/views/home')
            },
            {
                path: '/scenic',
                name: '景区',
                meta: {title: '景区管理', needLogin: true},
                component: () => import('@/views/scenic')
            },
            {
                path: '/packages',
                name: '套餐',
                meta: {title: '套餐管理', needLogin: true},
                component: () => import('@/views/packages')
            },
            {
                path: '/notes',
                name: '游记',
                meta: {title: '游记管理', needLogin: true},
                component: () => import('@/views/notes')
            },
            {
                path: '/order',
                name: '订单',
                meta: {title: '订单管理', needLogin: true},
                component: () => import('@/views/order')
            },
            {
                path: '/user',
                name: '用户',
                meta: {title: '用户管理', needLogin: true},
                component: () => import('@/views/user')
            }
        ]
    },
    {
        path: '/login',
        name: '登录',
        component: () => import('@/views/login')
    },
    {
        path: '*',
        redirect: '/login'
    }
]

const router = new VueRouter({
    routes
})

export default router
