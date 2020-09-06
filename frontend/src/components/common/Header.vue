<template>
    <div>
        <v-app-bar
            color="white"
            
            fixed
            ref="header"
        >
            <div ref="empty"></div>
            <v-slide-x-transition>
                <v-card  v-show="logo && !$router.app.$store.state.smallMode" flat tile>
                    <v-img src="@/assets/logo.png" style="cursor:pointer" max-width="150px" @click="move('/')"></v-img>
                </v-card>
            </v-slide-x-transition>
            <v-spacer></v-spacer>
            <v-expand-x-transition>
                 <v-card 
                    v-show="search"
                    dense
                    flat
                    rounded
                    height="70%"
                 >
                    <v-text-field
                        placeholder="검색"
                        v-model="keyword"
                        solo
                        dense
                        flat
                        outlined
                        @keydown.enter="keywordSearch"
                        autofocus
                        @blur="hideSearch($event)"
                    >
                    
                    </v-text-field>
                 </v-card>
            </v-expand-x-transition>
            <v-btn icon @click="keywordSearch" ref="searchBtn">
                <v-icon  ref="searchIcon"> mdi-magnify </v-icon>
            </v-btn>
            <span style="margin-left:50px;" v-show="!$router.app.$store.state.smallMode"></span>
            
            
            <div v-show="token">
                <v-hover
                    v-slot:default="{ hover }"                    
                >
                    <v-btn depressed text small @click="$router.app.$store.state.token ? createProject() : dialog = true" v-show="!$router.app.$store.state.smallMode">                        
                        <font :color="hover ? 'primary' : 'gray'" size="2">프로젝트 생성</font>                        
                    </v-btn>
                </v-hover>

                <v-menu left bottom offsetY v-if="!$router.app.$store.state.smallMode">
                    <template v-slot:activator="{ on, attrs }">
                        <v-badge
                            color="error"
                            :content="totalNotice > 9 ? '9+' : `${totalNotice}`"
                            offset-x="24"
                            offset-y="24"
                        >
                            <v-btn 
                                icon
                                v-bind="attrs"
                                v-on="on"
                                @click="getNoticeList"
                            >
                                <v-icon color="primary"> mdi-bell-outline </v-icon>
                            </v-btn>
                        </v-badge>
                    </template>
                    <v-list class="notice_list" outlined dense>
                        <v-list-item>
                            <v-list-item-content>
                                <v-list-item-title>전체 {{notices.length}}</v-list-item-title>
                            </v-list-item-content>
                            <v-list-item-action>
                                <v-list-item-title style="font-size:12px; cursor:pointer;" @click.stop="removeReadNotice">읽은 알림 삭제</v-list-item-title>
                            </v-list-item-action>
                            <v-list-item-action>
                                <v-list-item-title style="font-size:12px; cursor:pointer;" @click.stop="removeAllNotice">전체 삭제</v-list-item-title>
                            </v-list-item-action>
                            
                        </v-list-item>
                        <template v-for="(notice,i) in notices">
                            <v-divider :key="`${i}_divider`" />
                            <v-list-item :key="`${i}_notice`" link @click="readNotice(notice)" style="padding-left:0px;">                                
                                
                                <v-icon :color="notice.readYn == 'N' ? 'primary' : 'transparent'" style="margin:7px;">
                                    mdi-circle-medium
                                </v-icon>
                                
                                
                                <v-list-item-avatar style="margin-right:10px;">                                    
                                    <v-img
                                        :src="'http://i3a101.p.ssafy.io/images/' + notice.profile"
                                    >
                                    </v-img>
                                </v-list-item-avatar>
                                <v-list-item-content v-if="notice.noticeType == 1">
                                    <v-list-item-title> 새로운 전체 공지가 있습니다. </v-list-item-title>
                                    <v-list-item-subtitle> {{notice.boardTitle}} </v-list-item-subtitle>
                                    <v-list-item-subtitle> {{notice.createUserNickname}} <span style="font-size:12px;">{{notice.createdDate | diffDate}}</span> </v-list-item-subtitle>
                                </v-list-item-content>
                                <v-list-item-content v-else-if="notice.noticeType == 2">
                                    <v-list-item-title> {{notice.boardTitle}} </v-list-item-title>
                                    <v-list-item-subtitle> {{notice.replyContent}} </v-list-item-subtitle>
                                    <v-list-item-subtitle> {{notice.createUserNickname}} <span style="font-size:12px;">{{notice.createdDate | diffDate}}</span> </v-list-item-subtitle>
                                </v-list-item-content>
                                <v-list-item-content v-else-if="notice.noticeType == 3">
                                    <v-list-item-title> {{notice.lectureTitle}} </v-list-item-title>
                                    <v-list-item-subtitle> {{notice.subIndexTitle}}에 {{notice.reqType == 'wiki' ? '위키 수정 요청' : '강의 영상 업로드'}} </v-list-item-subtitle>
                                    <v-list-item-subtitle> {{notice.createUserNickname}} <span style="font-size:12px;">{{notice.createdDate | diffDate}}</span> </v-list-item-subtitle>
                                </v-list-item-content>
                                <v-list-item-content v-else-if="notice.noticeType == 4">
                                    <v-list-item-title> {{notice.lectureTitle}} </v-list-item-title>
                                    <v-list-item-subtitle> {{notice.subIndexTitle}}에 {{notice.reqType == 'wiki' ? '위키 수정 요청 수락' : '강의 영상 적용'}} </v-list-item-subtitle>
                                    <v-list-item-subtitle> <span style="font-size:12px;">{{notice.createdDate | diffDate}}</span> </v-list-item-subtitle>
                                </v-list-item-content>
                                <v-list-item-action @click.stop="removeNotice(notice)">
                                    <v-icon>
                                        mdi-close
                                    </v-icon>
                                </v-list-item-action>
                            </v-list-item>
                        </template>
                        <template v-if="!notices.length">                        
                            <v-divider />
                            <v-container fluid >         
                                <v-row>
                                    <v-col cols="12">
                                        <v-row
                                            align="end"
                                            justify="center"
                                        >                                    
                                            <v-icon style="font-size:120px;"> mdi-bell-off-outline </v-icon>                                
                                        </v-row>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12">
                                        <v-row
                                            align="end"
                                            justify="center"
                                        >       
                                            <div style="font-size:20px"> 새로운 알림이 없습니다 </div>    
                                        </v-row>
                                    </v-col>
                                </v-row>
                                <v-row>
                                    <v-col cols="12">
                                        <v-row
                                            align="end"
                                            justify="center"
                                        >       
                                            <div style="font-size:12px"> 나의 활동 소식과 새 소식을</div>
        
                                        </v-row>
                                        <v-row
                                            align="end"
                                            justify="center"
                                        >       
                                            <div style="font-size:12px"> 한번에 받아 보세요. </div>        
                                        </v-row>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </template>

                    </v-list>
                </v-menu>

                
                 
                <v-menu
                    left
                    bottom
                    offset-y
                    flat
                >
                    <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            icon
                            v-bind="attrs"
                            v-on="on"
                        >
                            <v-avatar size=30>
                                <v-img :src="`http://i3a101.p.ssafy.io/images/${$router.app.$store.state.profile}`" />
                            </v-avatar>
                        </v-btn>
                    </template>
            
                    <v-list flat tile outlined style="padding:0px;">
                        <v-list-item @click="userModify">
                            <v-list-item-icon>
                                <v-icon>mdi-account</v-icon>
                            </v-list-item-icon>
                            <v-list-item-content>
                                <v-list-item-title>마이 페이지</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                        <v-list-item @click="logout">
                            <v-list-item-icon>
                                <v-icon>mdi-power</v-icon>
                            </v-list-item-icon>
                            <v-list-item-content>
                                <v-list-item-title>로그아웃</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                        <v-list-item @click="tempDialog=true">
                            <v-list-item-icon>
                                <v-icon>mdi-clipboard-text</v-icon>
                            </v-list-item-icon>
                            <v-list-item-content>
                                <v-list-item-title>임시작성 목록</v-list-item-title>
                            </v-list-item-content>
                        </v-list-item>
                        
                    </v-list>
                </v-menu> 
            </div>
            <div v-show="!token">                
                    <v-btn depressed outlined @click.stop="dialog = true">로그인</v-btn>

                    <v-dialog
                        v-model="dialog"
                        max-width="350"
                        style="z-index:10000;"
                    >
                    <v-card tile>
                        <Login @closeDialog="dialog = false"></Login>
                    </v-card>
                    </v-dialog>                    

                    <span style="margin-left:10px;" v-show="!$router.app.$store.state.smallMode"></span>                
                    <v-btn depressed color="primary" outlined @click="move('/join')" v-show="!$router.app.$store.state.smallMode">회원가입</v-btn>                
            </div>            
        </v-app-bar>
        <v-snackbar 
            v-model="snackbar"
            timeout="2000"
            right
            color="primary"
        >
            {{msg}}
        </v-snackbar>
        <temp-board :dialog="tempDialog" @closeTempBoard="tempDialog=false"></temp-board>
    </div>
</template>

<script>
import http from "@/util/http_common.js"
import eventBus from "@/lib/EventBus.js"
import store from "@/store/index.js"
import Login from "@/components/user/Login.vue"
import TempBoard from "@/components/board/TempBoard.vue"

export default {
    components: {
       Login,
       TempBoard
    },
    created(){
        
        eventBus.$on('setNotice', (cnt) => {
            this.totalNotice = cnt;
        });

        eventBus.$on("modifyNavForHeader", (width) => {
            if(!this.$router.app.$store.state.smallMode) {
                this.$refs.empty.style.marginLeft = width + 20 + "px";
            }else{
                this.$refs.empty.style.marginLeft = 56 + "px";
            }
        });

        eventBus.$on("displayLogo", (logo) => {
            this.logo = logo;
        });

        eventBus.$on("doLogin", () => {
            this.dialog = true;
        })

        this.handleHeader();
        window.addEventListener('resize', this.handleHeader)
    },
    mounted(){
        eventBus.$emit("updateHeader", this.$refs.header.computedHeight);
    },
    beforeDestroy() {
        window.removeEventListener('resize', this.handleHeader)
    },
    computed:{
        token() {
            return store.state.token;
        }
    },
    filters: {
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
        }
    },
    data() {
        return {
            search: false,
            keyword: '',
            dialog: false,
            logo:true,
            tempDialog: false,
            snackbar: false,
            msg: '',

            totalNotice: 0,
            notices: [],

        }
    },
    methods: {        
        handleHeader() {
            this.$router.app.$store.commit('setDisplayMode', window.innerWidth < 960);
        },
        keywordSearch(){
            if(this.search){
                this.$router.push(`/search?keyword=${this.keyword}`).catch(()=>{location.reload(true);});
                /***                
                검색창에 입력된 키워드로 검색하는 로직 작성                 
                ***/
            }else{
                this.search = true;
            }
        },
        hideSearch(event){
            if(!event.relatedTarget){
                this.search = false;
                this.keyword = ''
            }
        },
        createProject(){                        
            http.axios.post('/api/v1/lectures').then(({data}) => {
                if(data.result.lectureId){
                    this.snackbar = true;
                    this.msg = "프로젝트가 생성되었습니다."
                    this.move(`/lecture/management/default/${data.result.lectureId}`);
                }
            }).catch(({data}) => {
                alert("프로젝트 생성에 실패하였습니다.");
            })
            
        },
        moveJoin(){
            this.$router.push('/join').catch(()=>{location.reload(true);});    
        },
        moveMain(){
            this.$router.push('/').catch(()=>{location.reload(true);});            
        },
        move(path){
            this.$router.push(path).catch(()=>{location.reload(true);});            
        },
        logout(){
            store.commit('logout')
            location.reload(true);
        },
        userModify(){
            if (this.$router.app.$store.state.token) {
                this.$router.push('/mypage/info').catch(()=>{location.reload(true);});   
            } else {
                eventBus.$emit('doLogin');
            }
        },

        getNoticeList() {
            http.axios.get('/api/v1/notice/getList').then(({data}) => {
                this.notices = data.result;
                this.totalNotice = 0;
            })
        },

        readNotice(notice) {
            http.axios.get(`/api/v1/notice/${notice.noticeId}`).then(({data}) => {
                if(data.result.noticeType == 1) {
                    this.move(`/board/detail?boardtype=1&boardId=${data.result.boardId}`)
                }else if(data.result.noticeType == 2) {
                    this.move(`/board/detail?boardtype=${data.result.boardType}&boardId=${data.result.boardId}`)
                }else if(data.result.noticeType == 3) {
                    this.move(`/lecture/management/request/${data.result.lectureId}`)
                }else if(data.result.noticeType == 4) {
                    this.move(`/lecture/player/undefined/${data.result.lectureId}?&order=${data.result.order}&subId=${data.result.subId}&subHisId=${data.result.subHisId}`)
                }

                this.getNoticeList();
            })
        },

        removeNotice(notice) {
            http.axios.delete(`/api/v1/notice/each?notice_id=${notice.noticeId}`).then(({data}) => {
                // noticeType
                this.getNoticeList();
            })
        },

        removeReadNotice() {
            http.axios.delete(`/api/v1/notice/read`).then(({data}) => {
                this.getNoticeList();
            })
        },

        removeAllNotice() {
            http.axios.delete(`/api/v1/notice/all`).then(({data}) => {
                this.getNoticeList();
            })
        }

    }
}
</script>

<style scoped>
    
    .v-menu__content  {
        box-shadow: none;
        overflow-y: visible;
        overflow-x: visible;
        contain: none;
    }

    .notice_list {
        border-radius:3px; 
        width: 400px;
        min-height:200px;
        max-height:450px;
        overflow-y:auto;
        padding:0px;
    }
</style>