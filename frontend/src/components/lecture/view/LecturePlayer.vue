<template>
    <div class="LecturePlayer">
    <v-layout wrap ref="main">
        <v-navigation-drawer
            fixed
            right
            mini-variant
            dark
            permanent
            style="background-image: linear-gradient(-45deg, rgba(0, 160, 255, 0.86), rgb(0, 72, 162)); min-height:100%; z-index:101"
            ref="nav"
            v-show="!$router.app.$store.state.smallMode"
        >
          <v-list
            nav
            dense
          >
            <v-list-item-group v-model="menu">
                <v-tooltip left color="primary" open-on-hover open-on-focus open-on-click>
                    <template v-slot:activator="{ on, attrs }">
                        <v-list-item 
                            v-bind="attrs"
                            v-on="on" 
                            link 
                            @click="menu == 0 && list? closeList() : openList(); tabs=0"
                        >
                            <v-list-item-icon>
                                <v-icon>mdi-format-list-bulleted</v-icon>
                            </v-list-item-icon>
                            <v-list-item-title></v-list-item-title>
                        </v-list-item>
                    </template>
                    <span>목차</span>
                </v-tooltip>

                <v-tooltip left color="primary" open-on-hover open-on-focus open-on-click>
                    <template v-slot:activator="{ on, attrs }">
                        <v-list-item 
                            v-bind="attrs"
                            v-on="on" 
                            link 
                            @click="menu == 1 ? closeList() : openList(); tabs=1"
                        >
                            <v-list-item-icon>
                                <v-icon>mdi-play-circle</v-icon>
                            </v-list-item-icon>
                            <v-list-item-title></v-list-item-title>
                        </v-list-item>
                    </template>
                    <span>관련 영상</span>
                </v-tooltip>

                <v-tooltip left color="primary" open-on-hover open-on-focus open-on-click>
                    <template v-slot:activator="{ on, attrs }">
                        <v-list-item 
                            v-bind="attrs"
                            v-on="on" 
                            link 
                            @click="menu == 2 ? closeList() : openList(); tabs=2"
                        >
                            <v-list-item-icon>
                                <v-icon>mdi-comment-processing</v-icon>
                            </v-list-item-icon>
                            <v-list-item-title></v-list-item-title>
                        </v-list-item>
                    </template>
                    <span>질문</span>
                </v-tooltip>

                

                <v-tooltip left color="primary" open-on-hover open-on-focus open-on-click>
                    <template v-slot:activator="{ on, attrs }">
                        <v-list-item 
                            v-bind="attrs"
                            v-on="on" 
                            link         
                            @click="menu == 3 ? closeList() : openList(); tabs=3"                    
                        >
                            <v-list-item-icon>
                                <i class="fas fa-cog fa-lg" style="margin-left:1px; margin-top:4px"></i>
                            </v-list-item-icon>
                            <v-list-item-title></v-list-item-title>
                        </v-list-item>
                    </template>
                    <span>설정</span>
                </v-tooltip>  
            </v-list-item-group>
            <v-tooltip left color="primary" open-on-hover open-on-focus open-on-click>
                    <template v-slot:activator="{ on, attrs }">
                        <v-list-item 
                            v-bind="attrs"
                            v-on="on" 
                            link             
                            @click="clickContribute()"
                        >
                             
                            <v-list-item-icon>
                                <v-icon>mdi-upload</v-icon>
                            </v-list-item-icon>
                            <v-list-item-title></v-list-item-title>
                        </v-list-item>
                    </template>
                    <span>기여하기</span>
                </v-tooltip>  
          </v-list>
        </v-navigation-drawer>
        <v-expand-x-transition>
            <v-card :width="listWidth" v-show="list" ref="list" tile flat outlined style="z-index:101; position:fixed; width:400px; top:0px; height:100%; " :style="{'right':navWidth+'px', 'border-color': (darkOption ? '#393939' : '#d2d2d2')}">
                <v-tabs v-model="tabs" hide-slider height=0>
                    <v-tab-item>
                        <v-list :dark="darkOption">
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-title>
                                        목차
                                    </v-list-item-title>                                                            
                                </v-list-item-content>
                                <v-list-item-action @click="closeList">
                                    <v-icon style="cursor:pointer">
                                        mdi-close
                                    </v-icon>
                                </v-list-item-action>
                            </v-list-item>
                        </v-list>
                        <index-list :darkOption="darkOption" :lectureId="lectureId" :order="order"> </index-list>
                        
                    </v-tab-item>
                    <v-tab-item>
                        <v-list :dark="darkOption">
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-title>
                                        관련 영상
                                    </v-list-item-title>                        
                                </v-list-item-content>
                                <v-list-item-action @click="closeList">
                                    <v-icon style="cursor:pointer">
                                        mdi-close
                                    </v-icon>
                                </v-list-item-action>
                            </v-list-item>
                            <v-list>
                                <v-list-item>                                
                                    <another-video :darkOption="darkOption" :lectureId="lectureId" :subId="subId" :tabs="tabs" :order="order"></another-video>
                                </v-list-item>
                            </v-list>
                            
                        </v-list>
                    </v-tab-item>
                    <v-tab-item>
                        <v-list :dark="darkOption">
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-title>
                                        질문 게시판
                                    </v-list-item-title>                        
                                </v-list-item-content>
                                <v-list-item-action @click="closeList">
                                    <v-icon style="cursor:pointer">
                                        mdi-close
                                    </v-icon>
                                </v-list-item-action>
                            </v-list-item>
                        </v-list>
                        <v-list :dark="darkOption">
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-title>
                                        <QuestionBoard :darkOption="darkOption" :lectureId="lectureId" :subId="subId" :refresh="boardRefresh" @show-detail="showDetail"> </QuestionBoard>             
                                    </v-list-item-title>                        
                                </v-list-item-content>
                            </v-list-item>                            
                        </v-list>
                    </v-tab-item>
                    <v-tab-item>
                        <v-list :dark="darkOption" style="height:1500px;">
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-title>
                                        설정
                                    </v-list-item-title>                                                            
                                </v-list-item-content>
                                <v-list-item-action @click="closeList">
                                    <v-icon style="cursor:pointer">
                                        mdi-close
                                    </v-icon>
                                </v-list-item-action>
                            </v-list-item>
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-subtitle>
                                        테마
                                    </v-list-item-subtitle>
                                    <v-list-item-subtitle>     
                                        <v-radio-group v-model="darkOption">
                                            <v-layout>
                                            <v-flex xs6 sm6 md6 lg6 xl6 style="padding:7px;">
                                                <v-card style="cursor:pointer" :style="{'border': (!darkOption ? '2px solid #00D27A' : 'none')}" link @click="darkOption=false">
                                                    <v-img src="@/assets/images/light.png" ></v-img>                                                
                                                </v-card>                                                
                                                <v-row justify="space-around" style="margin-top:10px;">                                                    
                                                    <v-radio
                                                        label="Light"
                                                        color="#00D27A"
                                                        :value="false"
                                                    ></v-radio>                                                    
                                                </v-row>
                                            </v-flex>

                                            <v-flex xs6 sm6 md6 lg6 xl6 style="padding:7px;">
                                                <v-card style="cursor:pointer" :style="{'border': (darkOption ? '2px solid #00D27A' : 'none')}" link @click="darkOption=true">
                                                    <v-img src="@/assets/images/dark.png" ></v-img>
                                                </v-card>
                                                <v-row justify="space-around" style="margin-top:10px;">                                                    
                                                    <v-radio
                                                        label="Dark"
                                                        color="#00D27A"
                                                        :value="true"
                                                    ></v-radio>                                                    
                                                </v-row>

                                            </v-flex>
                                            </v-layout>
                                        </v-radio-group>

                                    </v-list-item-subtitle>
                                </v-list-item-content>
                            </v-list-item>
                            <v-divider />
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-subtitle>
                                        자동 재생
                                    </v-list-item-subtitle>
                                </v-list-item-content>
                                <v-list-item-action>
                                    <v-switch v-model="autoPlay" color="#00D27A"></v-switch>
                                </v-list-item-action>
                            </v-list-item>   
                        </v-list>
                    </v-tab-item>                    
                    <v-tab-item>
                        <v-list :dark="darkOption">
                            <v-list-item>
                                <v-list-item-avatar @click="tabs=2">
                                    <v-icon style="cursor:pointer">
                                        mdi-arrow-left
                                    </v-icon>
                                </v-list-item-avatar>
                                <v-list-item-content>
                                    <v-list-item-title>
                                        질문 게시판
                                    </v-list-item-title>                        
                                </v-list-item-content>
                                <v-list-item-action @click="closeList">
                                    <v-icon style="cursor:pointer">
                                        mdi-close
                                    </v-icon>
                                </v-list-item-action>
                            </v-list-item>
                        </v-list>
                        <v-list :dark="darkOption">
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item-title>                                       
                                        <QuestionBoardDetail :darkOption="darkOption" :tabs="tabs" :boardId="boardId" @removeBoard="tabs=2"></QuestionBoardDetail> 
                                    </v-list-item-title>                        
                                </v-list-item-content>
                            </v-list-item>                            
                        </v-list>
                    </v-tab-item>                    
                </v-tabs>
            </v-card>
        </v-expand-x-transition>
        
        

            <div style="position:absolute; top:0px; left:0px; width:100%; height:100%; z-index:10; ">            
                <v-layout style="position:fixed; z-index:10000; bottom:0px; left:0px;" :style="{'width': videoWidth + 'px'}">               
                                <v-bottom-navigation
                                    absolute 
                                    :background-color="darkOption ? '#373838' : '#FFFFFF'"           
                                    style="z-index:10000; box-shadow:none;"                     
                                    :style="{'border' : (darkOption ? '1px solid #373838' : '1px solid #d4d4d4')}"
                                >
                                    <v-btn @click="move(`/lecture/detail/${$route.params.id}`)">
                                        <v-icon :color="darkOption ? '#d4d4d4' : 'primary'" large>mdi-exit-to-app mdi-rotate-180</v-icon>
                                    </v-btn>
                                    <v-tabs :background-color="darkOption ? '#373838' : '#FFFFFF'">                                
                                    </v-tabs>
                                    <v-btn v-show="order != 1" @click="prevLecture">
                                        <v-icon :color="darkOption ? '#d4d4d4' : 'primary'" style="font-size:40px;">mdi-skip-previous</v-icon>
                                    </v-btn>
                                    <v-btn v-show="order != sub.lectureCount" @click="nextLecture">
                                        <v-icon :color="darkOption ? '#d4d4d4' : 'primary'" style="font-size:40px;">mdi-skip-next</v-icon>
                                    </v-btn>
                                    
                                </v-bottom-navigation>
                            </v-layout>
                    <div :style="{'width' : videoWidth+'px'}">
                        <div v-show="sub.playerUrl">
                            <div id="videoFrame">
                                <video
                                    ref="video"
                                    id="livestation-player"
                                    class="video-js vjs-default-skin vjs-big-play-centered"
                                    controls
                                    data-setup='{}'
                                    style="width:100%;min-height:500px;"   
                                    :poster="`http://i3a101.p.ssafy.io/images/${sub.thumbnailUrl}`"
                                >
                                    <!-- <source src="http://i3a101.p.ssafy.io/images/example2.mp4"></source> -->
                                </video>
                            </div>        
                            <div v-show="!sub.playerUrl" style="padding:15px;">
                                등록된 영상이 없습니다.
                            </div>                            

                            <v-list :dark="darkOption">
                                <v-list-item>
                                    <v-list-item-content>
                                        <v-list-item-title>
                                            {{sub.title}}
                                        </v-list-item-title>
                                        <v-list-item-subtitle>
                                            조회수 {{sub.viewCount | convertNumber}} &middot; <v-icon small :color="sub.userLikeYn ? 'pink' : 'normal'">mdi-heart</v-icon> {{sub.likeCount | convertNumber}}
                                        </v-list-item-subtitle>
                                        <v-list-item-subtitle>
                                        #
                                            <v-chip                                            
                                                :color="`primary lighten-4`"                                            
                                                class="ma-1"
                                                v-for="(tag,index) in sub.tagName ? sub.tagName.split(',') : ''"   
                                                :key="index+'_tag'"
                                                small
                                                label
                                                @click="move(`/search?keyword=${tag}`)"
                                            >  
                                                <span style="color:black">
                                                    {{tag}}
                                                </span>
                                            </v-chip>                                                                                                   
                                        </v-list-item-subtitle>
                                        <v-list-item-subtitle>
                                            <v-avatar
                                                    class="profile"
                                                    size=20
                                                >
                                                    <v-img 
                                                        v-if="sub.profile"
                                                        :src="`http://i3a101.p.ssafy.io/images/${sub.profile}`"
                                                    ></v-img>
                                            </v-avatar>
                                            <span style="margin-left:5px;font-size:12px">{{sub.nickname}}</span>
                                        </v-list-item-subtitle>
                                    </v-list-item-content>
                                    <v-list-item-action>
                                        <v-btn small class="mx-2" fab light color="white" @click="asynClickLike()" :loading="btnLoading">
                                            <v-icon :color="sub.userLikeYn ? 'pink' : 'grey'" >mdi-heart</v-icon>
                                        </v-btn>
                                    </v-list-item-action>
                                    <v-list-item-action>
                                        <v-btn small class="mx-2" fab color="white" @click="copyToClipboard">
                                            <v-icon class="grey--text">mdi-share-variant</v-icon>
                                        </v-btn>
                                    </v-list-item-action>
                                </v-list-item>
                                <v-divider />
                                <div style="margin-top:50px" />                                                 
                            </v-list>
                        </div>


                        <v-list :style="{'background-color': (darkOption ? '#1e1e1e' : '#FFFFFF')}">
                            <v-list-item>
                                <div id="test" class="wiki-paragraph" v-html="parse(sub.wikiContentHtml)" v-if="sub.wikiContentHtml" style="width:100%" />
                                <div class="wiki-paragraph" v-else style="width:100%;" >
                                    <v-container fluid style="width:100%;">         
                                        <v-row>
                                            <v-col cols="12">
                                                <v-row
                                                    align="start"
                                                    justify="center"
                                                >                                    
                                                    <v-icon style="font-size:120px;"  :style="{'color': (darkOption ? '#d4d4d4' : 'rgba(0, 0, 0, 0.54)')}" > mdi-emoticon-cry-outline </v-icon>                                
                                                </v-row>
                                            </v-col>
                                        </v-row>
                                        <v-row>
                                            <v-col cols="12">
                                                <v-row
                                                    align="end"
                                                    justify="center"
                                                >       
                                                    <div style="font-size:20px"> 위키 문서가 비어있습니다 :( </div>    
                                                </v-row>
                                            </v-col>
                                        </v-row>
                                    </v-container>
                                </div>


                            </v-list-item>
                            <div style="height:100px;"></div>       
                        </v-list>
                        
                    </div>
            </div>
            
            <div style="position:fixed; top:0px; left:0px;width:100%; height:100%; z-index:9;" :style="{'background-color': (darkOption ? '#1e1e1e' : '#FFFFFF')}">
                
            </div>
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
            <v-snackbar
                v-model="isSuccess"
                right
                timeout="2500"
                color="success"        
            >
                요청이 완료되었습니다.
            </v-snackbar>
            <Contribute :dialog="dialog" :darkOption="darkOption" :lectureId="lectureId" :subId="subId" :wiki="sub.wikiContentHtml" @closeDialog="dialog=false;" style="z-index:9000;" @success="isSuccess=true;"></Contribute>
        </v-layout>
    </div>
</template>

<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<script>

import http from "@/util/http_common.js"
import IndexList from "@/components/lecture/view/IndexList.vue"
import AnotherVideo from "@/components/lecture/view/AnotherVideo.vue"
import QuestionBoard from "@/components/lecture/view/QuestionBoard.vue"
import QuestionBoardDetail from "@/components/lecture/view/QuestionBoardDetail.vue"
import Contribute from "@/components/lecture/view/Contribute.vue"
import parse from "@/lib/markdown/ParseMd.js";
import eventBus from "@/lib/EventBus.js";

export default {
    components: {
        IndexList,
        AnotherVideo,
        QuestionBoard,
        QuestionBoardDetail,
        Contribute,        
    },
    data(){
        return {
            tabName: ['index','another','question','setting','detail'],
            tabs: 0,
            menu: 0,
            
            lectureId: 0,
            subId: 0,
            subHisId: 0,
            boardId: 0,

            dialog:false,

            sub: {
                viewCount:0,
                likeCount:0,
                tagName:''
            },

            darkOption: false,
            theme:'',
            autoPlay: false,
            height:500,

            navWidth:0,
            listWidth:400,
            videoWidth:500,
            list: false,

            myPlayer: null,

            btnLoading: false,
            snackbar: false,
            isSuccess:false,

            boardRefresh: false,
        }
    },
    watch: {
        darkOption(){
            if(this.darkOption){
                $('.LecturePlayer .v-list-item__content').css('color', '#d4d4d4')
                $('.LecturePlayer .wiki-paragraph').css('color', '#d4d4d4')
            }else{            
                $('.LecturePlayer .v-list-item__content').css('color', '#000000DE')
                $('.LecturePlayer .wiki-paragraph').css('color', '#000000DE')
            }
            localStorage.setItem('darkOption', this.darkOption ? 'dark' : 'light');
        },
        autoPlay() {
            localStorage.setItem('autoPlay', this.autoPlay ? 'true' : 'false');
        },
        tabs() {
            if(this.tabs == 4) {
                if(this.subId)
                    history.pushState('', '', `/lecture/player/${this.tabName[this.tabs]}/${this.lectureId}?order=${this.order}&subId=${this.subId}&subHisId=${this.subHisId}&boardId=${this.boardId}`);
                else
                    history.pushState('', '', `/lecture/player/${this.tabName[this.tabs]}/${this.lectureId}?order=${this.order}&boardId=${this.boardId}`);

                    
            }else {
                if(this.subId)
                    history.pushState('', '', `/lecture/player/${this.tabName[this.tabs]}/${this.lectureId}?order=${this.order}&subId=${this.subId}&subHisId=${this.subHisId}`);
                else
                    history.pushState('', '', `/lecture/player/${this.tabName[this.tabs]}/${this.lectureId}?order=${this.order}`);

            }   
            
            if(this.tabs == 2){
                this.boardRefresh = !this.boardRefresh;
            }
        },
        sub() {
            var videoPlayer = document.getElementById('videoFrame');
            $('#videoFrame').html(
                `
                    <video
                        class="video-js vjs-default-skin vjs-big-play-centered"
                        controls
                        ${this.autoPlay ? 'autoplay' : ''}                        
                        data-setup='{}'
                        style="width:100%;min-height:500px;"   
                        poster="http://i3a101.p.ssafy.io/images/${this.sub.thumbnailUrl}"
                    >
                        <source src="http://i3a101.p.ssafy.io/images/${this.sub.playerUrl}"> </source>
                    </video>
                `
            )            
        }
    },
    filters: {
        convertNumber(num){
            return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        },
    },
    created(){    
        this.lectureId = this.$route.params.id;
        this.order = this.$route.query.order;
        this.tabs = this.tabName.indexOf(this.$route.params.tabName);        
        this.list = this.tabs != -1
        this.menu = this.tabs;
        if(this.menu == 4) {
            this.menu = 2;
            this.boardId = this.$route.query.boardId
        }
        this.subId = this.$route.query.subId;    
        this.subHisId = this.$route.query.subHisId;
        
        
        this.darkOption = localStorage.getItem('darkOption')        
        if(this.darkOption == null)
            this.darkOption = false;
        else
            this.darkOption= this.darkOption == 'dark';
        
        this.autoPlay = localStorage.getItem('autoPlay')
        if(this.autoPlay == null)
            this.autoPlay = true;
        else
            this.autoPlay= this.autoPlay == 'true';

        this.$emit('')
    },
    mounted() {
        
        if(this.subHisId) {
            this.loadSub();     
        }else {
            this.loadMain();
        }  

        if(this.darkOption){
            $('.LecturePlayer .v-list-item__content').css('color', '#d4d4d4')
            $('.LecturePlayer .wiki-paragraph').css('color', '#d4d4d4')
        }else{            
            $('.LecturePlayer .v-list-item__content').css('color', '#000000DE')
            $('.LecturePlayer .wiki-paragraph').css('color', '#000000DE')
        }
        if(this.tabs == -1)   {
            this.closeList();
        }
        
        this.handleResize()
        window.addEventListener('resize', this.handleResize)     
    },
    beforeDestroy(){
        window.removeEventListener('resize', this.handleResize);
    },
    methods: {
        parse,
        clickContribute() {
            if(this.$router.app.$store.state.token){    
                this.dialog=true
            }else{
                eventBus.$emit('doLogin');
            }
        },
        prevLecture(){
            this.move(`/lecture/player/${this.$route.params.tabName}/${this.lectureId}?order=${parseInt(this.order)-1}`)
        },
        nextLecture(){
            this.move(`/lecture/player/${this.$route.params.tabName}/${this.lectureId}?order=${parseInt(this.order)+1}`)
        },        
        loadMain() {
            http.axios.get(`/api/v1/lectures/sub/${this.lectureId}?order=${this.order}`).then(({data}) => {
                if(data.result) {                    
                    this.sub = data.result;
                    this.subId = this.sub.subId;
                    this.subHisId = this.sub.subHisId;     
                }else{
                    alert("존재하지 않는 강의 입니다.");
                    this.move(`/lecture/detail/${this.lectureId}`)                    
                }
            }).finally( () => {
                // var myPlayer = document.getElementById('livestation-player');
                // $('#livestation-player').html(`<source src="http://i3a101.p.ssafy.io/images/${this.sub.playerUrl}">`)
                // $('#livestation-player').attr('poster',`http://i3a101.p.ssafy.io/images/${this.sub.thumbnailUrl}`)
            })  
        },
        loadSub() {
            http.axios.get(`/api/v1/lectures/sub/history?lectureId=${this.lectureId}&subId=${this.subId}&subHisId=${this.subHisId}`).then(({data}) => {
                if(data.result) {                    
                    this.sub = data.result;
                }else{
                    alert("존재하지 않는 강의 입니다.");
                    this.move(`/lecture/detail/${this.lectureId}`)                    
                }
            }).finally( () => {
                // var myPlayer = document.getElementById('livestation-player');
                // $('#livestation-player').html(`<source src="http://i3a101.p.ssafy.io/images/${this.sub.playerUrl}">`)
                // $('#livestation-player').attr('poster',`http://i3a101.p.ssafy.io/images/${this.sub.thumbnailUrl}`)
            }) 
        },
        handleResize() {            
            this.navWidth = this.$router.app.$store.state.smallMode ? 0 : this.$refs.nav.miniVariantWidth;        
            this.videoWidth = window.innerWidth - this.listWidth - this.navWidth;
            if(this.$router.app.$store.state.smallMode){                
                this.videoWidth = window.innerWidth;
            }
            this.height = this.$refs.video.clientHeight;
        },   

        closeList(){
            this.navWidth = this.$router.app.$store.state.smallMode ? 0 : this.$refs.nav.miniVariantWidth;        
            this.listWidth = 0;
            this.videoWidth = document.body.scrollWidth - this.listWidth - this.navWidth;
            this.list = false;
            this.menu = -1;
            this.tabs = -1;
        },

        openList(){
            this.navWidth = this.$refs.nav.miniVariantWidth;
            this.listWidth = 400;
            this.videoWidth = document.body.scrollWidth - this.listWidth - this.navWidth;
            this.list = true;
        },

        move(url){
            this.$router.push(url)
        },
        showDetail(id){
            this.tabs=4;
            this.boardId = id;
        },
        clickLike(){
            http.axios.put(`/api/v1/lectures/like/history?lectureId=${this.lectureId}&subHisId=${this.sub.subHisId}&subId=${this.sub.subId}`).then(({data}) => {
                if(data.msg == 'success') {
                    this.sub.likeCount += this.sub.userLikeYn ? -1 : 1;
                    this.sub.userLikeYn = !this.sub.userLikeYn;    
                }
            }).finally(() => {                
                this.btnLoading = false;
            })
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
        copyToClipboard() {
            var url = document.createElement("textarea");
            document.body.appendChild(url);
            url.value = this.$router.app.$el.baseURI;
            url.select();
            document.execCommand('copy');
            document.body.removeChild(url);
            this.snackbar = true;
        },
    }
}
</script>

<style scoped>
</style>