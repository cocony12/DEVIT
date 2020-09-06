import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '@/components/common/Main.vue'
import Join from '@/components/user/Join.vue'
import FindPassword from '@/components/user/FindPassword.vue'
import ChangedPassword from '@/components/user/ChangedPassword.vue'
import UserModify from '@/components/user/Modify.vue'
import EmailCertify from '@/components/user/EmailCertify.vue'

import Board from '@/components/board/Board.vue'
import BoardDetail from '@/components/board/BoardDetail.vue'
import BoardEdit from '@/components/board/BoardEdit.vue'
import TempBoard from "@/components/board/TempBoard.vue";
import InvalidBoard from '@/components/board/InvalidBoard.vue'

import LectureList from '@/components/lecture/view/LectureList.vue'
import LectureManagement from '@/components/lecture/manager/LectureManagement.vue'
import LectureDetail from '@/components/lecture/view/LectureDetail.vue'

import LecturePlayer from '@/components/lecture/view/LecturePlayer.vue'

import SearchList from '@/components/lecture/view/SearchList.vue'

import MyPage from '@/components/mypage/MyPageManagement.vue'




Vue.use(VueRouter)


const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/join',
    name: 'Join',
    component: Join
  },  
  {
    path: '/findpw',
    name: 'FindPassword',
    component: FindPassword
  },
  {
    path: '/changedpw',
    name: 'ChangedPassword',
    component: ChangedPassword
  },
  {
    path: '/user/modify',
    name: 'UserModify',
    component: UserModify
  },
  {
    path: '/user/emailcertify',
    name: 'EmailCertify',
    component: EmailCertify
  },
  {
    path: '/board',
    name: 'Board',
    component: Board
  },
  {
    path: '/board/detail',
    name: 'BoardDetail',
    component: BoardDetail
  },
  {
    path: '/board/edit',
    name: 'BoardEdit',
    component: BoardEdit
  },
  {
    path: '/board/invalid',
    name: 'InvalidBoard',
    component: InvalidBoard
  },
  {
    path: '/lecture',
    name: 'Lecture',
    component: LectureList
  },
  {
    path: '/lecture/management/:tabName/:id',
    name: 'LectureManagement',
    component: LectureManagement
  },
  {
    path: '/lecture/detail/:id',
    name: 'LectureDetail',
    component: LectureDetail
  },

  {
    path: '/search',
    name: 'Search',
    component: SearchList
  },

  {
    path: '/tempboard',
    name: 'TempBoard',
    component: TempBoard
  },

  {
    path: '/lecture/player/:tabName/:id',
    name: 'LecturePlayer',
    component: LecturePlayer
  },
 
  {
    path: '/mypage/:tabName',
    name: 'MyPage',
    component: MyPage
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
