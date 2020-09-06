<template>
  <div style="background-color:'red'">
        <v-container>
            <div style="margin-top:10px; z-index:9"></div>
            <v-tabs
                color="black"          
                background-color="transparent"
                slider-color="primary"
                slider-size=4
                style="position:sticky; top:5px; z-index:9; max-width:500px;"
                ref="menu"
                show-arrows                
                v-show="!$router.app.$store.state.smallMode"
            >
                <v-tab @click="goto('#introduce')"><span style="font-size:16px;" >소개</span></v-tab>
                <v-tab @click="goto('#curriculum')"><span style="font-size:16px">커리큘럼</span></v-tab>
                <v-tab @click="goto('#changes')"><span style="font-size:16px">변경 사항</span></v-tab>
                <v-tab @click="goto('#helped')"><span style="font-size:16px">공동 관리자</span></v-tab>
            </v-tabs>  
        <v-card tile flat>
                <v-layout wrap ref="main">
                    <v-flex xs12 sm12 md8 lg8 xl8 ref="left" style="margin-left:0px; padding:20px;">       
                        
                            <v-img 
                                v-if="item.thumbnailUrl"
                                :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                                lazy-src="@/assets/images/empty.png"
                                aspect-ratio="1.77"
                                position="left center"
                                contain
                            >
                                <template v-slot:placeholder>
                                    <v-row
                                    class="fill-height ma-0"
                                    align="center"
                                    justify="center"
                                    >
                                        <v-progress-circular indeterminate color="primary lighten-4"></v-progress-circular>
                                    </v-row>
                                </template>
                            </v-img>                            
                        

                        <div v-show="!option" style="width:100%;">
                            <h1> {{item.title}}</h1>        
                            <h4>조회수 {{item.viewCount | convertView}}</h4>
                            <h4>총 {{item.lectureCount}}강의&nbsp;<v-icon size="16" :color="item.userLikeYn ? 'pink' : 'gray'">mdi-heart</v-icon>{{item.likeCount | convertLike}}</h4>
                                        
                            #<v-chip                                            
                                :color="`primary lighten-4`"                                            
                                class="ma-1"
                                v-for="(tag,index) in item.tagName ? item.tagName.split(',') : ''"   
                                :key="index"
                                label
                                small
                                @click="move(`/search?keyword=${tag}`)"
                            >                                     
                                <span style="color:black">
                                    {{tag}}
                                </span>
                            </v-chip>
                            <div style="margin-top:5px;" /> 
                            <div style="float:left;">
                                <v-avatar
                                    class="profile"
                                    size=30
                                >
                                    <v-img 
                                        v-if="item.thumbnailUrl"
                                        :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                                    ></v-img>
                                </v-avatar>
                                <span style="margin-left:5px;font-size:16px; ">{{item.nickname}}</span>
                            </div>
                            <div style="float:right;">
                                <v-btn small class="mx-2" fab light color="white" @click="asynClickLike()" :loading="btnLoading">
                                    <v-icon :color="item.userLikeYn ? 'pink' : 'grey'">mdi-heart</v-icon>
                                </v-btn >
                                <v-btn small class="mx-2" fab color="white" @click="copyToClipboard">
                                    <v-icon class="grey--text">mdi-share-variant</v-icon>
                                </v-btn>
                            </div>
                            <div style="margin-bottom:20px; clear:both;" /> 
                            
                            <v-btn depressed dark color="primary" large block @click="move(`/lecture/player/undefined/${$route.params.id}?order=1`)">
                                <span style="font-size:20px;">학습하기</span>
                            </v-btn>
                        </div>

                        <div id="introduce" style="margin-top:40px;">
                            <h3 style="padding: 0px auto;">프로젝트 소개</h3>    
                            <v-divider style="margin-top:20px" v-show="item.content" />
                            <div class="content" v-html="parse(item.content)" />                                
                            <div v-if="!item.content" style="height:50px" />
                        </div>
                        <div id="curriculum" style="margin-top:40px;">
                            <h3>커리큘럼</h3>      
                            <v-divider style="margin-top:20px" v-show="chapter.length" />
                                <div class="content">
                                    <v-list style="font-size:14px">
                                        <v-list-item 
                                            v-for="(item,index) in chapter" :key="`${index}_contents`"
                                            link
                                            @click="move(`/lecture/player/index/${lectureId}?order=${index+1}`)"
                                        >
                                            <v-list-item-avatar>
                                                <v-icon :color="item.videoYn ? 'primary' : 'error darken-1'">
                                                    mdi-play-circle-outline
                                                </v-icon>
                                            </v-list-item-avatar>
                                            <v-list-item-content>    
                                                {{item.title}}
                                            </v-list-item-content>
                                            <v-list-item-action v-if="item.videoYn">
                                                {{item.playTime}}
                                            </v-list-item-action>
                                        </v-list-item>
                                    </v-list>
                                <div style="height:50px"/>
                            </div>                            
                        </div>
                        <div id="changes" style="margin-top:40px;">
                            <h3>변경 사항</h3>    
                            <div class="content">
                                <v-list style="padding:0px;">          
                                    <template v-for="(item,index) in history">
                                        <v-divider :key="`${index}_divider`"/>
                                        <v-list-item :key="`${index}_history`">
                                            <v-list-item-avatar>
                                                <v-img v-if="item.profile" :src="'http://i3a101.p.ssafy.io/images/' + item.profile"></v-img>
                                            </v-list-item-avatar>
                                            <v-list-item-content>
                                                <v-list-item-title>
                                                    <b>{{item.nickname}}</b>&nbsp;
                                                </v-list-item-title>
                                                <v-list-item-subtitle> <i>{{item.title}}에 {{item.reqType == 'wiki' ? '위키 문서 수정' : '메인 영상 업로드'}}</i> </v-list-item-subtitle>
                                            </v-list-item-content>
                                            <v-list-item-action style="margin-right:5px">
                                                {{item.modified | diffDate}}
                                            </v-list-item-action>  
                                        </v-list-item>
                                    </template>    
                                    <v-list-item v-if="!isExpand && expand_history.length">             
                                        <v-list-item-content>
                                            
                                        </v-list-item-content>
                                        <v-list-item-action @click="isExpand = true" style="cursor:pointer;">
                                            더보기
                                        </v-list-item-action>
                                    </v-list-item>
                                    <template v-for="(item,index) in expand_history" v-if="isExpand && expand_history.length">
                                        <v-divider :key="`${index}_divider2`"/>
                                        <v-list-item :key="`${index}_expand_history`">
                                            <v-list-item-avatar>
                                                <v-img v-if="item.profile" :src="'http://i3a101.p.ssafy.io/images/' + item.profile"></v-img>
                                            </v-list-item-avatar>
                                            <v-list-item-content>
                                                <v-list-item-title>
                                                    <b>{{item.nickname}}</b>&nbsp;
                                                </v-list-item-title>
                                                <v-list-item-subtitle> <i>{{item.title}}에 {{item.reqType == 'wiki' ? '위키 문서 수정' : '메인 영상 업로드'}}</i> </v-list-item-subtitle>
                                            </v-list-item-content>
                                            <v-list-item-action style="margin-right:5px">
                                                {{item.modified | diffDate}}
                                            </v-list-item-action>  
                                        </v-list-item>
                                    </template>   
                                    
                                    <!-- <v-divider :key="_divider"/> -->
                                </v-list>
                                <div style="height:50px"/>
                            </div>                              
                        </div>
                        <div id="helped" style="margin-top:40px;">
                            <h3>공동 관리자</h3>  
                            <div class="content">
                                <v-list style="padding:0px;">        
                                    <template v-for="(item,index) in admin">
                                        <v-divider :key="`${index}_divider`"/>
                                        <v-list-item :key="`${index}_memberList`">
                                            <v-list-item-avatar>
                                                <v-img v-if="item.profile" :src="'http://i3a101.p.ssafy.io/images/' + item.profile"></v-img>
                                            </v-list-item-avatar>
                                            <v-list-item-content>
                                                <v-list-item-title>
                                                    <b>{{item.nickname}}</b>&nbsp;
                                                     <span class="v-list-item__subtitle">{{item.email | idOfEmail}}</span></v-list-item-title>
                                                <v-list-item-subtitle > {{item.created | diffDate}}</v-list-item-subtitle>
                                            </v-list-item-content>

                                            <v-list-item-action style="margin-right:5px">
                                                {{item.role}}
                                            </v-list-item-action>  
                                        </v-list-item>                                        
                                    </template>
                                </v-list>
                            </div>      
                            <div style="height:300px" /> 
                        </div>
                        
                    </v-flex>                    
                    <v-flex v-show="option" style="border-left:1px solid #e2e2e2" md4 lg4 xl4> 
                        <div style="position: sticky; top:130px;">
                            <v-layout>
                                <v-spacer></v-spacer>
                                
                                <v-btn small class="mx-2" fab light color="white" @click="asynClickLike()" :loading="btnLoading">
                                <v-icon :color="item.userLikeYn ? 'pink' : 'grey'">mdi-heart</v-icon>
                                </v-btn >
                                <v-btn small class="mx-2" fab color="white" @click="copyToClipboard">
                                <v-icon class="grey--text">mdi-share-variant</v-icon>
                                </v-btn>
                            </v-layout>
                            <v-layout>
                                <div style="margin:30px; width:100%;">
                                        <h1> {{item.title}}</h1>        
                                        
                                        <h4>조회수 {{item.viewCount | convertView}}</h4>
                                        <h4>총 {{item.lectureCount}}강의 &nbsp;<v-icon size="16" :color="item.userLikeYn ? 'pink' : 'gray'">mdi-heart</v-icon>{{item.likeCount | convertLike}} </h4>
                                        
                                        #<v-chip                                            
                                            :color="`primary lighten-4`"                                            
                                            class="ma-1"
                                            v-for="(tag,index) in item.tagName ? item.tagName.split(',') : ''"   
                                            :key="index"
                                            label
                                            small
                                            @click="move(`/search?keyword=${tag}`)"
                                        >                                     
                                            <span style="color:black">
                                                {{tag}}
                                            </span>
                                        </v-chip>
                                        <div style="margin-top:5px;" /> 
                                        <v-avatar
                                                class="profile"
                                                size=30
                                            >
                                                <v-img 
                                                    v-if="item.profile"
                                                    :src="'http://i3a101.p.ssafy.io/images/' + item.profile"
                                                ></v-img>
                                        </v-avatar>
                                        <span style="margin-left:5px;font-size:16px">{{item.nickname}}</span>
                                        <div style="margin-top:20px;" /> 
                                        <v-btn depressed outlined dark color="primary" large block @click="move(`/lecture/player/index/${$route.params.id}?order=1`)"><span style="font-size:20px;">학습하기</span></v-btn>
                                        <v-btn v-if="item.manageYn" color="normal" depressed outlined large block @click="move(`/lecture/management/default/${$route.params.id}`)" style="margin-top:10px;"><span style="font-size:20px;">관리하기</span></v-btn>
                                </div>
                            </v-layout>
                        </div>
                    </v-flex>                    
                </v-layout>
        </v-card>
        <v-snackbar
            v-model="snackbar"
            timeout="1500"
        >
            주소를 클립보드로 복사했습니다.
            <template v-slot:action="{ attrs }">
                <v-btn
                color="blue"
                text
                v-bind="attrs"
                @click="snackbar = false"
                >
                    닫기
                </v-btn>
            </template>
        </v-snackbar>
      </v-container>
  </div>
</template>

<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<script>
import eventBus from "@/lib/EventBus.js"
import http from "@/util/http_common.js"
import parse from "@/lib/markdown/ParseMd.js";

export default {
    watch: {
        items(val,prev){
            if(val.length > 10)
                val.pop()            
        },
    },
    filters: {
        idOfEmail(val) {
            let id = val.substring(0,val.indexOf('@'))
            return '@' + (id ? id : val)
        },
        diffDate(val) {
            let diff = (new Date() - new Date(val)) / 1000;
            if(diff < 60)
                return '방금 전'
            diff /= 60;
            if(diff < 60)
                return parseInt(diff) + '분 전'

            diff /= 60;
            if(diff < 24)
                return parseInt(diff) + '시간 전'

            diff /= 24;
            if(diff < 7)
                return parseInt(diff) + '일 전'
            if (diff < 30)
                return parseInt(diff/7) + '주 전'
            if (diff < 365)
                return parseInt(diff/30) + '달 전'
            return parseInt(diff/365) + '년 전'
            return val
        },

        convertView(num) {
            // if(num < 1000){
            //     return num + '회'
            // }

            // if(num >= 100000000){
            //     num /= 100000000;
            //     return parseFloat(num).toFixed(2) + '억회'
            // }
            // if(num >= 10000){
            //     num /= 10000;
            //     return parseFloat(num).toFixed(0) + '만회'
            // }
            // if(num >= 1000){
            //     num /= 1000;
            //     return parseFloat(num).toFixed(1) + '천회'
            // }
            return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '회';
        },
        convertLike(num){
            return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        },
    },
    data() {
        return {            
            lectureId: 0,

            item:{
                "title": '',
                "thumbnailUrl": '',
                "nickname": "",
                "lectureCount": 0,
                "viewCount": 0,
                "likeCount": 0,
                "tagName": '',
                "userLikeYn": false,
            },
            option: true,
            snackbar: false,

            menuHeight: 0,

            btnLoading: false,

            chapter: [],

            history: [],
            expand_history: [],
            isExpand: false,

            admin: [],
        }
    },
    created(){               
        this.lectureId = this.$route.params.id;

        this.$router.app.$store.commit('startLoading')
        http.axios.get(`/api/v1/lectures/${this.$route.params.id}`).then(({data}) => {
            this.item = data.result;
        }).catch((error) => {
            
        }).finally(() => {
            this.$router.app.$store.commit('endLoading')
        })
        this.getIndexList();
        this.getHistoryList();
        this.getAdminList();
    },
    mounted() {        
        this.menuHeight = this.$refs.menu.$el.offsetTop;        
        this.option = this.$refs.main.clientWidth != this.$refs.left.clientWidth;
        window.addEventListener('resize', this.handleResize)
        document.addEventListener('scroll', this.handleScroll);
    },
    beforeDestroy(){
        window.removeEventListener('resize', this.handleResize);
        document.removeEventListener('scroll', this.handleScroll);
        eventBus.$emit("displayLogo", true);
    },
    methods: {
        parse,
        handleResize() {
            this.option = this.$refs.main.clientWidth != this.$refs.left.clientWidth;
        },        
        handleScroll(){
            eventBus.$emit("displayLogo", window.scrollY < 40);
        },
        copyToClipboard() {
            var url = document.createElement("textarea");
            document.body.appendChild(url);
            url.value = this.$router.app.$el.baseURI;
            url.select();
            document.execCommand('copy');
            document.body.removeChild(url);
            this.snackbar = true;
        },
        asynClickLike(){
            var clickLike = this.clickLike;            
            if(this.$router.app.$store.state.token){        
                this.btnLoading = true;        
                setTimeout(() => {
                    clickLike();
                }, 300);
            }else{
                eventBus.$emit('doLogin');
            }
        },
        clickLike(){
            http.axios.put(`/api/v1/lectures/like?lectureId=${this.item.lectureId}`).then(({data}) => {
                if(data.msg == 'success') {
                    this.item.likeCount += this.item.userLikeYn ? -1 : 1;
                    this.item.userLikeYn = !this.item.userLikeYn;    
                }
            }).finally(() => {                
                this.btnLoading = false;
            })
        },
        goto(target){
            this.$vuetify.goTo(target, {
                duration: 300,
                offset: 100,
                easing: 'easeInOutCubic'
            })
        },
        move(url){
            this.$router.push(url);
        },
        getIndexList() {
            this.$router.app.$store.commit('startLoading')
            http.axios.get(`/api/v1/lectures/subs/${this.lectureId}`).then(({data}) => {
                this.chapter = [];
                for(let i in data.result){                    
                    this.chapter.push({
                        "commonId": data.result[i].commonId,
                        "order": parseInt(data.result[i].order),
                        "subId": data.result[i].subId,
                        "tags": data.result[i].tags ? data.result[i].tags.split(',') : [],
                        "title": data.result[i].title,
                        "videoYn": data.result[i].videoYn,
                        "wikiYn": data.result[i].wikiYn,
                        "playTime": data.result[i].playTime,                        
                    })
                }
            }).finally(() => {
                this.$router.app.$store.commit('endLoading')
            })
        },
        getHistoryList() {
            this.$router.app.$store.commit('startLoading')
            http.axios.get(`/api/v1/lectures/historys/${this.lectureId}`).then(({data}) => {
                this.history = [];
                this.expand_history = [];
                for(let i in data.result){         
                    if(data.result[i].acceptYn){
                        if(this.history.length < 5)
                            this.history.push(data.result[i])
                        else
                            this.expand_history.push(data.result[i])
                    }
                }
            }).finally(() => {
                this.$router.app.$store.commit('endLoading')
            })
        },
        getAdminList(){
            this.$router.app.$store.commit('startLoading')
            http.axios.get(`/api/v1/lectures/auth/${this.lectureId}`).then(({data}) => {
                this.admin = data.result;
            }).finally(() => {
                this.$router.app.$store.commit('endLoading')
            });
        }
        
    }
}
</script>

<style scoped>
    * {
        font-weight: 400;
    }
    h1 {
        font-size: 24px;
        font-weight: 400;
        margin-bottom:20px;
    }
    div.content {
        font-size:16px;
        margin-top:20px;
    }
</style>