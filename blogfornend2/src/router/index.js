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
        path: 'articlelist',
        component: () => import('../views/GetAllArticle.vue')
      },
      {
        path: 'articleread/:id',
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
    path: '/getallarticle', // 所有文章列表
    name: 'getallarticle',
    component: () => import('../views/GetAllArticle.vue')
  },
  {
    path: '/readarticle/:id', // 閱讀文章內容
    name: 'readarticle',
    component: () => import('../views/ReadArticle.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
