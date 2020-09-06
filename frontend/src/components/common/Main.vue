<template>
    <div id="app">        
        <v-carousel
            cycle
            height="400"
            hide-delimiter-background
            interval="7000"
        >
            <v-carousel-item
            v-for="(title, i) in titles"
            :key="i"
            >
            <v-sheet
                :color="colors[i]"
                height="100%"
                style="background-color:rgba(135, 206, 235, 0.26);"
            >
                <v-row
                    justify="start"
                    align="center"             
                    style="max-width:1180px; margin:0px auto; height:400px;"   
                >
                    <v-col :cols="6">
                        <div style="color:#333333; font-size:32px; font-weight:bold; margin-bottom: 8px;">{{ title }}</div>
                        <div style="color:#333333; font-size:16px;" v-html="contents[i]"></div>
                    </v-col>
                    <v-col :cols="6">
                        <v-img :src="`/${images[i]}`" height="400" contain />
                    </v-col>
                </v-row>
            </v-sheet>
            </v-carousel-item>
        </v-carousel>
        <v-container fluid="fluid">
            
            <v-layout wrap style="max-width:1380px; margin:0px auto;">
                <v-container fluid="fluid" grid-list-xl="grid-list-xl" class="" v-if="letureItems.length > 0">
                    <div style="width:100%;">
                        <span style="font-size:20px; font-weight:600; color:#1976d2 !important">인기 프로젝트</span>
                    </div>
                    <div v-if="letureItems.length > 0">
                        <v-layout row="row" wrap="wrap" style="width:100%; margin:0;">
                            <v-flex
                                v-for="(item,i) in letureItems.slice(0, $router.app.$store.state.smallMode ? 2 : 4)"
                                :key="`4${i}_lectures`"
                                style="padding:0;"
                                xs12 sm6 md3 lg3 xl3
                            >
                                <v-card
                                    tile="tile"
                                    flat="flat"
                                    style="margin-left:10px; margin-top:20px;cursor:pointer;"
                                >
                                    <v-img
                                        v-if="item.thumbnailUrl"
                                        :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                                        lazy-src="@/assets/images/empty.png"
                                        aspect-ratio="1.77"
                                        @click="move(`/lecture/detail/${item.lectureId}`)"
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

                                    <v-list>
                                        <div @click="move(`/lecture/detail/${item.lectureId}`)">
                                            <v-list-item-title>
                                                <h3>{{item.title}}</h3>
                                            </v-list-item-title>
                                            <v-list-item-subtitle>
                                                조회수
                                                {{item.viewCount | convertView}}&nbsp;<v-icon size="16" :color="item.userLikeYn ? 'pink' : 'gray'">mdi-heart</v-icon>{{item.likeCount | convertLike}}
                                            </v-list-item-subtitle>

                                            <v-list-item-subtitle>
                                                총
                                                {{item.lectureCount}}강의
                                            </v-list-item-subtitle>
                                        </div>
                                        <v-list-item-subtitle>
                                            #
                                            <v-chip
                                                :color="`primary lighten-4`"
                                                class="ma-1"
                                                v-for="(tag,index) in item.tagName ? item.tagName.split(',') : ''"
                                                :key="i+'_'+index+'_tag'"
                                                small="small"
                                                label="label"
                                                @click="move(`/search?keyword=${tag}`)">
                                                <span style="color:black">
                                                    {{tag}}
                                                </span>
                                            </v-chip>
                                        </v-list-item-subtitle>
                                        <v-avatar class="profile" size="20">
                                            <v-img :src="'http://i3a101.p.ssafy.io/images/' + item.profile"></v-img>
                                        </v-avatar>
                                        <span style="margin-left:5px;font-size:12px">{{item.nickname}}</span>

                                    </v-list>
                                </v-card>

                            </v-flex>
                        </v-layout>
                    </div>                    
                    <div style="margin-top:100px;" />
                    <div v-if="videoItems.length > 0" >
                        <span style="font-size:20px; font-weight:600; color:#1976d2 !important;">핫이슈 동영상</span>
                    </div>
                    <div v-if="videoItems.length > 0">
                        <v-layout row="row" wrap="wrap" style="width:100%; margin:0;">
                            <v-flex
                                v-for="(item,i) in videoItems.slice(0, $router.app.$store.state.smallMode ? 2 : 4)"
                                :key="`4${i}_video`"
                                xs12 sm6 md3 lg3 xl3
                                style="padding:0"
                            >                        
                                <v-card
                                    tile="tile"
                                    flat="flat"
                                    style="margin-left:10px; margin-top:20px;cursor:pointer;"
                                >
                                    <v-img
                                        :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                                        lazy-src="@/assets/images/empty.png"
                                        aspect-ratio="1.77"
                                        @click="move(`/lecture/player/undefined/${item.lectureId}?order=${item.order}&subId=${item.subId}&subHisId=${item.subHisId}`)"
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

                                    <v-list>
                                        <div @click="move(`/lecture/player/undefined/${item.lectureId}?order=${item.order}&subId=${item.subId}&subHisId=${item.subHisId}`)">
                                            <v-list-item-title>
                                                <h3>{{item.title}}</h3>
                                            </v-list-item-title>
                                            <v-list-item-subtitle>
                                                조회수
                                                {{item.viewCount | convertView}}&nbsp;<v-icon size="16" :color="item.userLikeYn ? 'pink' : 'gray'">mdi-heart</v-icon>{{item.likeCount | convertLike}}
                                            </v-list-item-subtitle>
                                        </div>
                                        <v-avatar class="profile" size="20">
                                            <v-img :src="'http://i3a101.p.ssafy.io/images/' + item.profile"></v-img>
                                        </v-avatar>
                                        <span style="margin-left:5px;font-size:12px">{{item.nickname}}</span>

                                    </v-list>
                                </v-card>

                            </v-flex>
                        </v-layout>
                    </div>
                    <div v-else>
                        <v-container fluid style="width:100%;">         
                            <v-row>
                                <v-col cols="12">
                                    <v-row align="start" justify="center">                                    
                                        <v-icon style="font-size:150px; color:rgba(0, 0, 0, 0.54); margin:30px 0 20px 0"> mdi-emoticon-cry-outline </v-icon>                               
                                    </v-row>
                                    <v-row align="start" justify="center">       
                                        <div style="font-size:20px; margin-bottom:20px;"> 동영상이 존재하지 않습니다 :( </div>    
                                    </v-row>
                                </v-col>
                            </v-row>
                        </v-container>
                    </div>
                </v-container>
                



        <!-- <v-container fluid="fluid" grid-list-xl="grid-list-xl" class="devit_status">
            <v-layout row="row" justify-center="justify-center" style="margin:auto 0;">
                <v-flex class="status_wrap" xs12 sm12 md3 lg3 xl3>
                    <v-layout row="row" wrap="wrap">
                        <div class="status_img">
                            <v-img src="@/assets/images/members.png" max-width="30px"></v-img>
                        </div>
                        <v-flex>
                            <v-layout column="column" wrap="wrap">
                                <div class="status_title">총 회원</div>
                                <div class="status_content">{{this.totalUsers}}</div>
                            </v-layout>
                        </v-flex>
                    </v-layout>
                </v-flex>
                <span class="bar" aria-hidden="true" v-if="videoWidth>960">|</span>
                <v-flex class="status_wrap" xs12 sm12 md3 lg3 xl3>
                    <v-layout row="row" wrap="wrap">
                        <div class="status_img">
                            <v-img src="@/assets/images/elearning.png" max-width="30px"></v-img>
                        </div>
                        <v-flex>
                            <v-layout column="column" wrap="wrap">
                                <div class="status_title">강의에 기여한 회원</div>
                                <div class="status_content">{{this.totalDeviters}}</div>
                            </v-layout>
                        </v-flex>
                    </v-layout>
                </v-flex>
                <span class="bar" aria-hidden="true" v-if="videoWidth>960">|</span>
                <v-flex class="status_wrap" xs12 sm12 md3 lg3 xl3>
                    <v-layout row="row" wrap="wrap">
                        <div class="status_img">
                            <v-img src="@/assets/images/lecture.png" max-width="30px"></v-img>
                        </div>
                        <v-flex>
                            <v-layout column="column" wrap="wrap">
                                <div class="status_title">누적 강의 수</div>
                                <div class="status_content">{{this.totalLectures}}</div>
                            </v-layout>
                        </v-flex>
                    </v-layout>
                </v-flex>
            </v-layout>
        </v-container>
        <v-container fluid="fluid" class="devit_info">
            <v-layout justify-center="justify-center">
                <div style="width:30px; vertical-align:middle; margin:auto 2px">
                    <v-img src="@/assets/images/logo.png"></v-img>
                </div>
                <div class="info_title">데빗이 뭔가요?</div>
            </v-layout>
            <v-container>
                <v-layout row="row" wrap="wrap" justify-center="justify-center">
                    <v-flex class="info_wrap" xs12="xs12" sm6="sm6" md4="md4" lg3="lg3" xl2="xl2">
                        <v-layout column="column">
                            <v-flex class="info_img">
                                <v-img src="@/assets/images/participation.png" max-width="80px"></v-img>
                            </v-flex>
                            <v-flex class="info_sub_title">참여형 강의 플랫폼</v-flex>
                            <v-flex class="info_content">
                                누구나 만들고 수정할 수 있는 참여형 강의 플랫폼입니다.  다른 사람의 강의에 부족한 점이 있거나 잘못된 정보가 있다면 자유롭게 추가/수정하여
                                강의의 품질을 높여보세요.
                            </v-flex>
                        </v-layout>
                    </v-flex>
                    <v-flex class="info_wrap" xs12="xs12" sm6="sm6" md4="md4" lg3="lg3" xl2="xl2">
                        <v-layout column="column">
                            <v-flex class="info_img">
                                <v-img src="@/assets/images/choose.png" max-width="80px"></v-img>
                            </v-flex>
                            <v-flex class="info_sub_title">원하는 강의만 쏙쏙 골라 듣기</v-flex>
                            <v-flex class="info_content">
                                아직도 강의를 처음부터 순서대로 들으시나요? 필요한 챕터만 골라서 듣고 시간을 절약해보세요.
                            </v-flex>
                        </v-layout>
                    </v-flex>
                    <v-flex class="info_wrap" xs12="xs12" sm6="sm6" md4="md4" lg3="lg3" xl2="xl2">
                        <v-layout column="column">
                            <v-flex class="info_img">
                                <v-img src="@/assets/images/communication.png" max-width="80px"></v-img>
                            </v-flex>
                            <v-flex class="info_sub_title">개발자들의 자유로운 소통 공간</v-flex>
                            <v-flex class="info_content">
                                강의를 수강하며 이해가 되지 않는다면, 혹은 홀로 개발을 하다 막힌다면 바로 질문게시판에 질문을 올려보세요. 강의를 올린 사람 뿐만 아니라
                                다양한 분야의 베테랑 개발자님들께 답변을 받을 수 있답니다.
                            </v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-container> -->
        <!-- <v-container fluid="fluid" class="devit_rank">
            <v-layout row="row" wrap="wrap">
                <v-flex
                    class="rank_wrap_30"
                    xs12="xs12"
                    sm6="sm6"
                    md4="md4"
                    lg3="lg3"
                    xl2="xl2">
                    <div class="rank_title">활동 랭킹</div>
                </v-flex>
                <v-flex
                    class="rank_wrap_40"
                    xs12="xs12"
                    sm6="sm6"
                    md4="md4"
                    lg3="lg3"
                    xl2="xl2">
                    <v-layout justify-center="justify-center" row="row" wrap="wrap">
                        <v-flex class="rank_top_wrap">
                            <v-layout column="column">
                                <v-flex class="rank_img">
                                    <v-img src="@/assets/images/silver.png" max-width="60px"></v-img>
                                </v-flex>
                                <v-flex class="rank_top_ranker">
                                    <span class="rank">2위</span>
                                    <span class="ranker">{{this.items[1].nickname}}</span>
                                </v-flex>
                            </v-layout>
                        </v-flex>
                        <v-flex class="rank_top_wrap">
                            <v-layout column="column">
                                <v-flex class="rank_img">
                                    <v-img src="@/assets/images/gold.png" max-width="60px"></v-img>
                                </v-flex>
                                <v-flex class="rank_top_ranker">
                                    <span class="rank">1위</span>
                                    <span class="ranker">{{this.items[0].nickname}}</span>
                                </v-flex>
                            </v-layout>
                        </v-flex>
                        <v-flex class="rank_top_wrap">
                            <v-layout column="column">
                                <v-flex class="rank_img">
                                    <v-img src="@/assets/images/bronze.png" max-width="60px"></v-img>
                                </v-flex>
                                <v-flex class="rank_top_ranker">
                                    <span class="rank">3위</span>
                                    <span class="ranker">{{this.items[2].nickname}}</span>
                                </v-flex>
                            </v-layout>
                        </v-flex>
                    </v-layout>
                </v-flex>
                <v-flex
                    class="rank_wrap_30"
                    xs12="xs12"
                    sm6="sm6"
                    md4="md4"
                    lg3="lg3"
                    xl2="xl2">
                    <v-layout column="column" wrap="wrap">
                        <v-flex>
                            <span class="rank">4위</span>
                            <span class="ranker">{{this.items[3].nickname}}</span>
                        </v-flex>
                        <v-flex>
                            <span class="rank">5위</span>
                            <span class="ranker">{{this.items[4].nickname}}</span>
                        </v-flex>
                        <v-flex>
                            <span class="rank">6위</span>
                            <span class="ranker">{{this.items[5].nickname}}</span>
                        </v-flex>
                        <v-flex>
                            <span class="rank">7위</span>
                            <span class="ranker">{{this.items[6].nickname}}</span>
                        </v-flex>
                        <v-flex>
                            <span class="rank">8위</span>
                            <span class="ranker">{{this.items[7].nickname}}</span>
                        </v-flex>
                    </v-layout>
                </v-flex>
            </v-layout>
        </v-container> -->
        <!-- </v-flex>
            </v-flex> -->
            </v-layout>
        </v-container>

    </div>
</template>
<script>
    import http from "@/util/http_common.js";    

    export default {
        data() {
            return {
                colors: [
                    '#ffffff',
                    '#ffffff',
                    '#ffffff',
                ],
                titles: [
                    '참여형 강의 플랫폼',
                    '원하는 강의만 쏙쏙 골라 듣기',
                    '개발자들의 자유로운 소통 공간',
                ],
                contents: [
                    '누구나 만들고 수정할 수 있는 참여형 강의 플랫폼입니다. <br>다른 사람의 강의에 부족한 점이 있거나 잘못된 정보가 있다면 <br>자유롭게 추가/수정하여 강의의 품질을 높여보세요.',
                    '아직도 강의를 처음부터 순서대로 들으시나요?<br> 필요한 챕터만 골라서 듣고 시간을 절약해보세요.<br> 이해가 안된다구요? 걱정하지마세요! 보충 강의도 있답니다.',
                    '강의를 수강하며 이해가 되지 않는다면, 질문게시판에 질문을 올려보세요!<br> 다양한 분야의 베테랑 개발자님들께 답변을 받을 수 있답니다.'
                ],
                images: [
                    'main1.png',
                    'main2.png',
                    'main3.png',
                ],
                letureItems: [], videoItems: [], 
                totalUsers: "",
                totalDeviters: "",
                totalLectures: "",
                items: [
                    {
                        "nickname": ""
                    }, 
                    {
                        "nickname": ""
                    }, 
                    {
                        "nickname": ""
                    }, 
                    {
                        "nickname": ""
                    }, 
                    {
                        "nickname": ""
                    }, 
                    {
                        "nickname": ""
                    }, 
                    {
                        "nickname": ""
                    }, 
                    {
                        "nickname": ""
                    }, 
                ],
            navWidth:0,
            listWidth:400,
            videoWidth:500,
            list: false,
            }
        },
        filters: {
            convertView(num) {
                return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '회';
            },
            convertLike(num) {
                return num
                    .toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ',');
            }
        },
        created() {
                        
        },
        mounted(){
            this.getBestLectures();
            this.getBestVideos();
            this.getDevitStatus();
            this.getDevitRank();
        
            this.handleResize()
            window.addEventListener('resize', this.handleResize)     
        },
        beforeDestroy(){
            window.removeEventListener('resize', this.handleResize);
        },
        methods:{
            getBestLectures(){

            http
                .axios
                .get(`/api/v1/commons/main/bestLectures`)
                .then(({data}) => {
                    this.letureItems = data.result;
                })
            },
            getBestVideos(){
                http
                .axios
                .get(`/api/v1/commons/main/bestVideos`)
                .then(({data}) => {
                    this.videoItems = data.result;
                    console.dir(data)
                })
            },
            getDevitStatus(){

            http
                .axios
                .get(`/api/v1/commons/main/status`, {})
                .then(({data}) => {
                    this.totalUsers = data.result.totalUsers;
                    this.totalDeviters = data.result.totalDeviters;
                    this.totalLectures = data.result.totalLectures;
                })
                .catch((error) => {
                    console.dir(error)
                });
            },
            getDevitRank(){

                http
                .axios
                .get(`/api/v1/commons/main/rank`, {})
                .then(({data}) => {
                    this.items = data.result;
                })
                .catch((error) => {
                    console.dir(error)
                });
            },
            handleResize() {                
                this.videoWidth = window.innerWidth;
            },   
            move(url){            
                this.$router.push(url)
            }
        }
    }
</script>
<style scoped="scoped">

    .devit_status {
        /*background-color:#f2f2f2;*/
        min-height: 100px;
        /*vertical-align:middle;*/
        margin: auto;
    }
    .devit_status .status_wrap {
        margin: auto 0;
    }
    .devit_status .status_wrap .status_img {
        margin: auto 20px;
    }
    .devit_status .status_wrap .status_title {
        font-size: 12px;
        color: #4294BF;
        font-weight: bolder;
    }
    .devit_status .status_wrap .status_content {
        font-size: 18px;
        font-weight: bold;
    }
    .devit_status .bar {
        display: inline-block;
        width: 1px;
        height: 60px;
        text-indent: -999em;
        background: #e4e4e5;
        vertical-align: middle;
        margin: 0 2px;
    }

    .devit_info {
        /*background-color:#f2f2f2;*/
        margin: 20px 0;
    }

    .devit_info .info_title {
        font-size: 30px;
        font-weight: bolder;
        color: #274259;
    }

    .devit_info .info_wrap {
        width: 33%;
        max-width: 300px;
        margin: 2px 20px;
    }

    .devit_info .info_wrap .info_img {
        height: 100px;
        margin: auto;
    }
    .devit_info .info_wrap .info_sub_title {
        font-weight: bold;
        font-size: 16px;
        margin: 10px auto 20px;
    }
    .devit_info .info_wrap .info_content {
        margin: auto;
        font-size: 14px;
    }

    .devit_rank {
        /*background-color:#f2f2f2;*/
        margin: 20px 0;
    }

    .devit_rank .rank_title {
        color: #274259;
        font-size: 30px;
        font-weight: bolder;
        text-align: right;
        margin: auto 0;
    }
    .devit_rank .rank_wrap_40 {
        width: 40%;
        padding: 0 10px;
        margin: auto;
    }
    .devit_rank .rank_wrap_30 {
        width: 30%;
        padding: 0 30px;
        margin: auto;
    }

    .devit_rank .rank_top_wrap {
        width: 33%;
    }

    .devit_rank .rank_top_ranker {
        text-align: center;
    }
    .devit_rank .rank_img {
        /*width:100%;*/
        margin: auto;
    }

    .devit_rank .rank {
        color: #4294BF;
        font-weight: bolder;
        font-size: 11px;
        margin: 0 3px;
    }

    .devit_rank .ranker {
        color: #274259;
        font-weight: bolder;
        font-size: 11px;
    }
</style>