import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/login/LoginIndex.vue')
  },
  {
    path: '/home', // 首页
    name: 'home',
    component: () => import('../views/home/HomeIndex.vue'),
    meta: { breadcrumb: '首页' }, // 添加面包屑
    children: [
      {
        path: 'mp', // 模型广场
        name: 'mp',
        component: () => import('../views/home/mp/mpIndex.vue'),
        meta: { breadcrumb: '模型仓库' }
      },
      {
        path: 'pre-db', // 数据集
        name: 'pre-db',
        component: () => import('../views/home/db/pre-db.vue'),
        meta: { breadcrumb: '已预处理数据集' }
      },
      {
        path: 'pre', // 预处理任务列表
        name: 'pre',
        component: () => import('../views/home/task/preTask.vue'),
        meta: { breadcrumb: '预处理任务列表' },
      },
      {
        path: 'predisplay', // 预处理展示
        name: 'predisplay',
        component: () => import('../views/home/taskDisplay/preDisplay.vue'),
        meta: { breadcrumb: '预处理展示' }
      },
      {
        path: 'cltrain', // 克隆训练任务列表
        name: 'cltrain',
        component: () => import('../views/home/task/train/clTrainTask.vue'),
        meta: { breadcrumb: '训练任务列表' },
      },
      {
        path: 'cltraindisplay', // 克隆训练展示
        name: 'cltraindisplay',
        component: () => import('../views/home/taskDisplay/train/clTrainDisplay.vue'),
        meta: { breadcrumb: '训练过程展示' },
       
      },
      {
        path: 'ywtrain', // 异味训练任务列表
        name: 'ywtrain',
        component: () => import('../views/home/task/train/ywTrainTask.vue'),
        meta: { breadcrumb: '训练任务列表' },
      },
      {
        path: 'ywtraindisplay', // 异味训练展示
        name: 'ywtraindisplay',
        component: () => import('../views/home/taskDisplay/train/ywTrainDisplay.vue'),
        meta: { breadcrumb: '训练过程展示' }
      },
      {
        path: 'ldtrain', // 漏洞训练任务列表
        name: 'ldtrain',
        component: () => import('../views/home/task/train/ldTrainTask.vue'),
        meta: { breadcrumb: '训练任务列表' },
      },
      {
        path: 'ldtraindisplay', // 漏洞训练展示
        name: 'ldtraindisplay',
        component: () => import('../views/home/taskDisplay/train/ldTrainDisplay.vue'),
        meta: { breadcrumb: '训练过程展示' }
      },
      {
        path: 'cltest', // 克隆测试任务列表
        name: 'cltest',
        component: () => import('../views/home/task/test/clTestTask.vue'),
        meta: { breadcrumb: '测试任务列表' },
      },
      {
        path: 'cltestdisplay', // 克隆测试展示
        name: 'cltestdisplay',
        component: () => import('../views/home/taskDisplay/test/clTestDisplay.vue'),
        meta: { breadcrumb: '测试任务展示' }
      },
      {
        path: 'ywtest', // 异味测试任务列表
        name: 'ywtest',
        component: () => import('../views/home/task/test/ywTestTask.vue'),
        meta: { breadcrumb: '测试任务列表' },
      },
      {
        path: 'ywtestdisplay', // 异味测试展示
        name: 'ywtestdisplay',
        component: () => import('../views/home/taskDisplay/test/ywTestDisplay.vue'),
        meta: { breadcrumb: '测试任务展示' }
      },
      {
        path: 'ldtest', // 漏洞测试任务列表
        name: 'ldtest',
        component: () => import('../views/home/task/test/ldTestTask.vue'),
        meta: { breadcrumb: '测试任务列表' },
      },
      {
        path: 'ldtestdisplay', // 漏洞测试展示
        name: 'ldtestdisplay',
        component: () => import('../views/home/taskDisplay/test/ldTestDisplay.vue'),
        meta: { breadcrumb: '测试任务展示' }
      },
      {
        path: 'temporary', // 临时效果演示
        name: 'temporary',
        component: () => import('../views/home/temporary.vue'),
        meta: { breadcrumb: '临时效果演示' },
       
      },
    ]
  }
]

const router = new VueRouter({
  mode: 'hash', 
  base: process.env.BASE_URL,
  routes
})

export default router
