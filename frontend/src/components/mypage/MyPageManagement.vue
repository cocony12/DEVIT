<template>
  <div style="background-color:'red'; margin-top:20px;">
        <v-container>            
            
            <v-layout wrap ref="main">
                <v-flex xs12 sm12 md11 lg11 xl11 ref="left"></v-flex>
                <v-flex xs12 sm12 md4 lg4 xl4 v-show="!option" style="text-align:center;">              
                    <h1 class="primary--text">{{items[tab].title}}</h1>
                </v-flex>                  
                
                
                <v-flex xs12 sm12 md12 lg12 xl12>                     
                    <h1 class="primary--text" v-show="option" style="float:left">{{items[tab].title}}</h1>   
                    <v-tabs v-model="tab" hide-slider right icons-and-text show-arrows color="normal">                         
                        <v-tab v-for="(item,i) in items" :key="`${i}_tab`" @click="changeTab(i)">
                            <span class="primary--text lighten-1">{{item.title}}</span>
                            <v-icon :color="tab==i ? 'primary' : ''" small>{{tab == i ? 'mdi-circle' : item.icon }}</v-icon>
                        </v-tab>
                    </v-tabs>           
                    <Info :option='option' :tab='0' :curTab="tab" v-if="tab==0"> </Info>
                    <Activity :option='option' :tab='1' :curTab="tab" v-else-if="tab==1"> </Activity>
                    <MyLike :option='option' :tab='2' :curTab="tab" v-else-if="tab==2 && !search"> </MyLike>
                    <MyLikeLecture :option='option' :tab='2' :curTab="tab" v-else-if="tab==2 && search == 'project'"> </MyLikeLecture>
                    <MyLikeVideo :option='option' :tab='2' :curTab="tab" v-else-if="tab==2 && search == 'video'"> </MyLikeVideo>
                    <MyReqList :option='option' :tab='3' :curTab="tab" v-else-if="tab==3"> </MyReqList>
                </v-flex>       

                
                
            </v-layout>     
        </v-container>        
  </div>
</template>

<script>
import http from "@/util/http_common.js"
import store from "@/store/index.js"
import Info from '@/components/mypage/Info.vue'
import Activity from '@/components/mypage/Activity.vue'
import MyLike from '@/components/mypage/MyLike.vue'
import MyLikeLecture from '@/components/mypage/MyLikeLecture.vue'
import MyLikeVideo from '@/components/mypage/MyLikeVideo.vue'
import MyReqList from '@/components/mypage/MyReqList.vue'

export default {
    components: {
        Info,
        Activity,
        MyLike,
        MyLikeLecture,
        MyLikeVideo,
        MyReqList,
    },
    data() {
        return {          
            lectureId: 0,
            tabName: ['info','activity','like','request'],                    
            tab: 0,  
            option: false,
            search: '',

            items: [
                {
                    title: '회원 정보',
                    icon: 'mdi-circle-outline',
                    link: 'default',
                },
                {
                    title: '활동 내역',
                    icon: 'mdi-circle-outline',
                    link: 'default',
                },
                {
                    title: '보관함',
                    icon: 'mdi-circle-outline',
                    link: 'intro',
                },
                {
                    title: '요청 목록',
                    icon: 'mdi-circle-outline',
                    link: 'request',
                },
            ]
        }
    },
    watch:{
        tab(val) {
            this.tab = this.tabName.indexOf(this.$route.params.tabName);
        }
    },
    created(){
        this.lectureId = this.$route.params.id;
        if(!store.state.token) {
            this.$router.push('/')
        }
        
        this.tab = this.tabName.indexOf(this.$route.params.tabName);
        if(this.tab == -1){
            alert('잘못된 접근입니다.')
        }        

        this.search = this.$route.query.search;
    },
    mounted() {
        window.addEventListener('resize', this.handleResize)
        this.option = this.$refs.main.clientWidth != this.$refs.left.clientWidth;
    },
    beforeDestroy(){
        window.removeEventListener('resize', this.handleResize)
    },
    methods: {  
        changeTab(e) {            
            this.$router.push(`/mypage/${this.tabName[e]}`)
            this.tab = this.tabName.indexOf(this.$route.params.tabName);
        },
        handleResize() {
            this.option = this.$refs.main.clientWidth != this.$refs.left.clientWidth;
        },
    }
}
</script>

<style scoped>
    * {
        font-weight: 400;
    }
    h1 {
        font-size: 26px;
        font-weight: 500;
        margin-top:15px;
        /* margin-bottom:30px; */
    }
</style>