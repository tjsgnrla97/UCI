import Vue from 'vue'
import VueRouter from 'vue-router'
import homeView from '@/views/homepage/homeView.vue'
import AccountView from '@/views/authentication/AccountView.vue'
import MyProfileView from '@/views/homepage/MyProfileView.vue'
import VideoView from '@/views/videos/VideoView.vue'
import MainView from '@/views/mainpage/MainView.vue'
import HompageLayout from '@/layouts/homepage/Index'
import MaingPageLayout from '@/layouts/mainpage/Index'
import NotFoundView from '@/layouts/NotFound/Index'

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    component: HompageLayout,
    children: [
      {
        path: 'myprofile',
        name: 'MyProfileView',
        component: MyProfileView
      },
    ]
  },
  {
    path: '/video',
    name: 'VideoView',
    component: VideoView
  },
  {
    path: '/',
    component: MaingPageLayout,
    children: [
      {
        path: 'accountview',
        name: 'AccountView',
        component: AccountView
      }
    ]   
  },
  {
    path: '*',
    name: '404',
    component: NotFoundView,
  }
// {
//   path: '/',
//   component: MeetingPageLayout,
//   children: [
//     {
//       path: '/signup',
//       name: 'signup',
//       component: SignUpView
//     },
//     {
//       path: '/login',
//       name: 'LoginView',
//       component: LoginView
//     },
// ]
// },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
