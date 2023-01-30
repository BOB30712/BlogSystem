import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'about',
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/readpage',
    name: 'readpage',
    component: () => import('../views/ReadPage.vue'),
    children: [
      {
        path: 'articlelist', // 所有文章列表
        component: () => import('../views/GetAllArticle.vue')
      },
      {
        path: 'articletarget/:id/:tname', // 所有相同標籤的文章ArticleByTarget
        name: 'readpage/articletarget',
        component: () => import('../views/GetAllArticle.vue')
      },
      {
        path: 'articleread/:id', // 閱讀文章內容
        name: 'readpage/articleread',
        component: () => import('../views/ReadArticle.vue')
      }
    ]
  },
  {
    path: '/article', // 撰寫文章
    name: 'article',
    component: () => import('../views/ArticleView.vue')
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
