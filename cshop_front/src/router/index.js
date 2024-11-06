import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LayoutVue from '@/views/Layout.vue'

import OrderManageVue from '@/views/OrderManage/OrderManage.vue'
import ProductManageVue from '@/views/ProductManage/ProductManage.vue'

//定义路由关系
const routes = [
    {
        path: '/', component: LayoutVue, redirect: '/product/manage', children: [
            { path: '/order/manage', component: OrderManageVue },
            { path: '/product/manage', component: ProductManageVue },

        ]
    }
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

//导出路由
export default router
