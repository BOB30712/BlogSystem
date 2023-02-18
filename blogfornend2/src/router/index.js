import { createRouter, createWebHashHistory } from 'vue-router'
import ReadPage from '../views/ReadPage.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: ReadPage
  },
  {
    path: '/test',
    component: () => import('../views/TestView.vue')
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/readpage', // 進入前臺
    name: 'readpage',
    component: () => import('../views/ReadPage.vue'),
    children: [
      {
        path: 'articlelist', // 所有文章列表
        component: () => import('../views/GetAllArticle.vue')
      },
      {
        path: 'articletarget/:id/:tname', // 所有相同標籤的文章
        name: 'readpage/articletarget',
        component: () => import('../views/GetAllArticle.vue')
      },
      {
        path: 'articleread/:id', // 閱讀文章內容
        name: 'readpage/articleread',
        component: () => import('../views/ReadArticle.vue')
      },
      {
        path: 'blogarchive', // 網誌存檔
        component: () => import('../views/BlogArchive.vue')
      }
    ]
  },
  {
    path: '/login', // 登入畫面
    name: 'login',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/managepage', // 進入後臺
    name: 'managepage',
    component: () => import('../views/ManagePage.vue'),
    children: [
      {
        path: 'article', // 撰寫文章
        component: () => import('../views/ArticleView.vue')
      },
      {
        path: 'managearticle', // 文章列表
        component: () => import('../views/ManageArticle.vue')
      },
      {
        path: 'adminview', // 管理員列表
        component: () => import('../views/AdminView.vue')
      }
    ]
  },
  {
    path: '/success',
    name: 'success',
    component: () => import('../views/SuccessView.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
