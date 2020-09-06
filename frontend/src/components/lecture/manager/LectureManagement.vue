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
                        <v-tab key="detail_tab" @click="$router.push(`/lecture/detail/${lectureId}`)">
                            <span class="primary--text lighten-1">강의 페이지</span>                            
                            <v-icon small>mdi-circle-outline</v-icon>
                        </v-tab>
                    </v-tabs>           
                    <Default :option='option' :tab='0' :curTab="tab" v-if="tab==0"> </Default>
                    <Intro :option='option' :tab='1' :curTab="tab" v-else-if="tab==1"> </Intro>
                    <MemberManagement :option='option' :tab='2' :curTab="tab" v-else-if="tab==2"> </MemberManagement>
                    <RequestList :option='option' :tab='3' :curTab="tab" v-else-if="tab==3"> </RequestList>
                    <ChapterManagement :option='option' :tab='4' :curTab="tab" v-else-if="tab==4"> </ChapterManagement>
                </v-flex>       

                
                
            </v-layout>     
        </v-container>        
  </div>
</template>

<script>
import http from "@/util/http_common.js"
import store from "@/store/index.js"
import Default from "@/components/lecture/manager/Default.vue"
import Intro from "@/components/lecture/manager/Intro.vue"
import MemberManagement from "@/components/lecture/manager/MemberManagement.vue"
import ChapterManagement from "@/components/lecture/manager/ChapterManagement.vue"
import RequestList from "@/components/lecture/manager/RequestList.vue"
export default {
    components: {
        Default,
        Intro,
        MemberManagement,
        RequestList,
        ChapterManagement,
    },
    data() {
        return {          
            lectureId: 0,
            tabName: ['default','intro','member','request','index'],
            tab: 0,  
            option: false,

            items: [
                {
                    title: '기본 정보',
                    icon: 'mdi-circle-outline',
                    link: 'default',
                },
                {
                    title: '강의 설명',
                    icon: 'mdi-circle-outline',
                    link: 'intro',
                },
                {
                    title: '멤버 관리',
                    icon: 'mdi-circle-outline',
                    link: 'member',
                },
                {
                    title: '요청 목록',
                    icon: 'mdi-circle-outline',
                    link: 'request',
                },
                {
                    title: '목차 관리',
                    icon: 'mdi-circle-outline',
                    link: 'index',
                },
            ]
        }
    },
    watch:{
        tab(val) {
            this.tab = this.tabName.indexOf(this.$route.params.tabName);

            

            // history.pushState('', '', `/lecture/management/${this.tabName[this.tab]}/${this.$route.params.id}`);
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

        http.axios.get(`/api/v1/lectures/${this.lectureId}`).then(({data}) => {
            if(!data.result.manageYn) {
                this.$router.push('/')
                alert('권한이 없습니다!')                
            }
        }).catch((error) => {
            
        }).finally(() => {
            
        })
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
            this.$router.push(`/lecture/management/${this.tabName[e]}/${this.$route.params.id}`)
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