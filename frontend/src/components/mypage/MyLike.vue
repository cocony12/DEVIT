<template>
    <div style="margin:20px 50px; padding:10px 60px" id="like">
        <span style="font-size:20px; font-weight:600; color:#1976d2 !important">내 프로젝트</span>
        <carousel-3d ref="carousel" perspective="1" :space="400" :display="items.length > 5 ? 5 : items.length" controlsVisible v-if="items.length" width="400" height="340" style="background-color:#ffffff; margin:0; padding:20px; margin:10px;">
                <slide v-for="(item, i) in items" :index="i" :key="`${i}_slide`">
                    <v-hover>
                        <template v-slot:default="{ hover }">
                            <v-card
                                class="mx-auto"
                                width="400"
                            >
                                <v-img
                                    v-if="item.thumbnailUrl"
                                    :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                                    lazy-src="@/assets/images/empty.png"
                                    position="center center"
                                    height="224"
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
                                    <v-fade-transition>
                                        <v-overlay
                                            v-if="hover && $refs.carousel && $refs.carousel.currentIndex == i"
                                            absolute
                                            color="#000000"
                                        >
                                            <v-btn bottom icon style="position:absolute; right:50px; bottom:10px;" @click="move(`/lecture/management/default/${item.lectureId}`)">                        
                                                <i class="fas fa-cog fa-lg"></i>
                                            </v-btn>
                                            <v-btn icon style="position:absolute; right:10px; bottom:10px;" @click="remove(item.lectureId)">
                                                <i class="fas fa-trash fa-lg"></i>
                                            </v-btn>
                                        </v-overlay>
                                    </v-fade-transition>
                                </v-img>
        
                                <v-card-text style="height:120px;">                        
                                    <!-- <h3 style="margin-left:0px; color:#1e1e1e">  </h3>             -->
                                    <v-list-item style="padding:0">                
                                        <v-list-item-avatar style="margin-top:0px; margin-bottom:16px;">
                                            <v-img 
                                                v-if="item.profile"
                                                :src="'http://i3a101.p.ssafy.io/images/' + item.profile"
                                            ></v-img>
                                        </v-list-item-avatar>
                                        <v-list-item-content style="padding-top:0;">
                                            <v-list-item-title style="font-size:18px;">
                                                {{item.title}}
                                            </v-list-item-title>     
                                            <v-list-item-subtitle>
                                                <span style="font-size:13px">{{item.nickname}}</span>
                                            </v-list-item-subtitle>
                                            <v-list-item-subtitle>
                                                <span v-for="(tag,index) in item.tagName ? item.tagName.split(',') : ''" :key="i+'_'+index+'_tag'" style="font-size:12px;">
                                                    #{{tag}}                                                
                                                </span>
                                            </v-list-item-subtitle>                                       
                                        </v-list-item-content>
                                    </v-list-item>
                                </v-card-text>
                            </v-card>
                        </template>
                    </v-hover>
                </slide>
            </carousel-3d>
        <div style="width:100%; margin-top:100px;">
            <span style="font-size:20px; font-weight:600; color:#1976d2 !important">내가 좋아요한 프로젝트</span>
            <span style="float:right; font-size:14px; color:#585858; cursor:pointer" @click="goToLectureList">더보기</span>
        </div>
        <div v-if="letureItems.length > 0">
            <v-layout row="row" wrap="wrap">
                <!-- <div style="width:100%; margin:0 10px;">
                    <span style="font-size:20px; font-weight:600; color:#1976d2 !important">내가 좋아요한 프로젝트</span>
                    <span style="float:right; font-size:14px; color:#585858; cursor:pointer" @click="goToLectureList">더보기</span>
                </div> -->
                <v-flex
                    v-for="(item,i) in letureItems"
                    :key="`4${i}`"
                    xs12="xs12"
                    sm6="sm6"
                    md3="md3"
                    lg3="lg3"
                    xl3="xl3">
                    <v-card
                        tile="tile"
                        flat="flat"
                        style="margin-left:10px; margin-top:20px;cursor:pointer;">
                        <v-img
                            :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                            :lazy-src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                            aspect-ratio="1.7"
                            @click="move(`/lecture/detail/${item.lectureId}`)"></v-img>

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
        <div v-else>
            <v-container fluid style="width:100%;">         
                <v-row>
                    <v-col cols="12">
                        <v-row align="start" justify="center">                                    
                            <v-icon style="font-size:150px; color:rgba(0, 0, 0, 0.54); margin:30px 0 20px 0"> mdi-emoticon-cry-outline </v-icon>                               
                        </v-row>
                        <v-row align="start" justify="center">       
                            <div style="font-size:20px; margin-bottom:20px;"> 프로젝트가 존재하지 않습니다 :( </div>    
                        </v-row>
                    </v-col>
                </v-row>
            </v-container>
        </div>
        
        <div style="width:100%; margin-top:20px; padding-top:20px; border-top:1px #c8c8c8 solid;">
            <span style="font-size:20px; font-weight:600; color:#1976d2 !important">내가 좋아요한 동영상</span>
            <span style="float:right; font-size:14px; color:#585858; cursor:pointer" @click="goToVideoList">더보기</span>
        </div>
        <div v-if="videoItems.length > 0">
            <v-layout row="row" wrap="wrap">
                <v-flex
                    v-for="(item,i) in videoItems"
                    :key="`4${i}`"
                    xs12="xs12"
                    sm6="sm6"
                    md4="md3"
                    lg3="lg3"
                    xl2="xl3">
                    <v-card
                        tile="tile"
                        flat="flat"
                        style="margin-left:10px; margin-top:20px;cursor:pointer;">
                        <v-img
                            :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                            :lazy-src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                            aspect-ratio="1.7"
                            @click="move(`/lecture/player/undefined/${item.lectureId}?order=${item.order}&subId=${item.subId}&subHisId=${item.subHisId}`)"></v-img>

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
        <v-snackbar v-model="snackbar" timeout="1500" color="primary">
            {{msg}}
        </v-snackbar>
        <v-snackbar v-model="errorSnackbar" timeout="1500" color="error">
            {{errorMsg}}
        </v-snackbar>
    </div>
</template>

<script
    src="https://code.jquery.com/jquery-3.5.1.min.js"
    integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
    crossorigin="anonymous"></script>
<script>
    import http from "@/util/http_common.js"
    import store from "@/store/index.js"

    export default {
        data() {
            return {
                letureItems: [], 
                videoItems: [], 
                items: [], 
                level: this.$route.query.level, 
                page: 1, 
                loading: false,

                errorSnackbar: false,
                errorMsg: false,
                
                snackbar: false,
                msg: '',
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
        created(){
            if(!store.state.token) {
                this.$router.push('/')
            }
        },
        mounted() {
            this.getManagementProject();
            http
                .axios
                .get(`/api/v1/myLikeLecture?page=1&itemsperpage=4`)
                .then(({data}) => {
                    this.letureItems = data.result;
                })
            http
                .axios
                .get(`/api/v1/myLikeVideo?page=1&itemsperpage=4`)
                .then(({data}) => {
                    this.videoItems = data.result;
                })
        },
        beforeDestroy() {
        },
        methods: {
            getManagementProject() {
                http.axios.get(`/api/v1/myMngLecture?page=1&itemsperpage=100`)
                    .then(({data}) => {
                        this.items = [];
                        for(let i in data.result) {
                            if(data.result[i].thumbnailUrl)
                                this.items.push(data.result[i])                                
                        }
                    })                
            },
            move(url) {
                this
                    .$router
                    .push(url)
            },
            goToLectureList(){
                this.move('/mypage/like?search=project')
            },
            goToVideoList(){
                this.move('/mypage/like?search=video')
            },
            remove(lectureId) {
                if (confirm("삭제하시겠습니까?")) {
                    http
                        .axios
                        .put('/api/v1/deleteLecture', {"lectureId": lectureId})
                        .then(({data}) => {
                            if (data.msg == "noauth") {
                                this.errorMsg = "프로젝트 관리 권한이 없습니다.";
                                this.errorSnackbar = true;
                            } else if(data.msg == "hashistory"){                                
                                this.errorMsg = "강의에 요청 건이 존재하여 삭제할 수 없습니다.";
                                this.errorSnackbar = true;
                            }else {
                                this.msg = "삭제되었습니다.";
                                this.snackbar = true;
                                location.reload(true);
                            }
                        })
                        .catch((error) => {
                            console.dir(error)
                        })
                    }
            },
        }
    }
</script>

<style scoped>
    .carousel-3d-slide {
        border:none;        
        height:320px !important;
        box-shadow: 0 3px 1px -2px rgba(0,0,0,.2), 0 2px 2px 0 rgba(0,0,0,.14), 0 1px 5px 0 rgba(0,0,0,.12);
        border-radius: 4px;
    }

    
</style>