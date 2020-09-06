<template>
    <div style="margin:50px">
        <v-container justify-center="justify-center">
            
            <v-layout row="row" wrap="wrap">
                <!-- {{items[0]}}  -->
                
                <div style="width:100%; margin:0 auto;">
                    <div style="width:100%; margin-bottom:30px;"></div>

                    <v-list>
                        <div style="border-bottom:1px solid #c8c8c8; border-top:1px solid #c8c8c8;">
                            <v-list-item style="width:100%;">
                                <v-list-item-content>
                                    <div style="">이미지</div>
                                </v-list-item-content>
                                <v-list-item-content>
                                    <div style="">프로젝트 명</div>
                                </v-list-item-content>
                                <v-list-item-content>
                                    <div style="text-align:right;">강의수</div>
                                </v-list-item-content>
                                <v-list-item-content>
                                    <div style="text-align:right;">조회수</div>
                                </v-list-item-content>
                                <v-list-item-content>
                                    <div style=""></div>
                                </v-list-item-content>
                            </v-list-item>
                        </div>
                        <div
                            class=""
                            style="width:100%; border-bottom:1px solid #c8c8c8; cursor:pointer;"
                            v-for="item in items"
                            :key="item.lectureId">
                            <v-list-item style="width:100%;">
                                <v-list-item-content>
                                    <div>
                                        <v-img
                                            max-width="170"
                                            max-height="100"
                                            :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                                            :lazy-src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                                            aspect-ratio="1.7"
                                            @click="move(`/lecture/detail/${item.lectureId}`)"></v-img>
                                    </div>
                                </v-list-item-content>
                                <v-list-item-content @click="move(`/lecture/detail/${item.lectureId}`)">
                                    <!-- <div>{{item.title}}</div> -->
                                    <v-list-item-title v-text="item.title"></v-list-item-title>
                                    <v-list-item-subtitle v-text="item.content"></v-list-item-subtitle>
                                </v-list-item-content>
                                <v-list-item-content @click="move(`/lecture/detail/${item.lectureId}`)">
                                    <div style="text-align:right; width:50px;">{{item.lectureCount}}</div>
                                </v-list-item-content>
                                <v-list-item-content @click="move(`/lecture/detail/${item.lectureId}`)">
                                    <div style="text-align:right; width:50px;">{{item.viewCount}}</div>
                                </v-list-item-content>
                                <v-list-item-content>
                                    <v-btn
                                        depressed="depressed"
                                        small="small"
                                        text="text"
                                        color="success"
                                        @click="move(`/lecture/management/default/${item.lectureId}`)">관리하기</v-btn>
                                    <v-btn
                                        depressed="depressed"
                                        small="small"
                                        text="text"
                                        color="error"
                                        @click="remove(`${item.lectureId}`)"
                                        v-if="item.isOwner=='Y'">삭제하기</v-btn>
                                </v-list-item-content>
                            </v-list-item>
                        </div>
                    </v-list>
                </div>

            </v-layout>
        </v-container>
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
                items: [],
                level: this.$route.query.level,
                page: 1,
                itemsperpage: 10,
                loading: false,
                errorMsg: "",
                errorSnackbar: false,
                msg: "",
                snackbar: false
            }
        },
        created() {
            if (!store.state.token) {
                this
                    .$router
                    .push('/')
            }
        },
        mounted() {
            this.loading = true;
            http
                .axios
                .get(
                    `/api/v1/myMngLecture?page=${this.page}&itemsperpage=${this.itemsperpage}`
                )
                .then(({data}) => {
                    this.page++;
                    this.items = data.result;
                })
                . finally(() => {
                    this.loading = false;
                })

            document.addEventListener('scroll', this.handleScroll);
        },
        beforeDestroy() {
            document.removeEventListener('scroll', this.handleScroll);
        },
        methods: {
            handleScroll() {
                if ($(document).scrollTop() + $(document)[0].scrollingElement.clientHeight + 100 >= $(
                    document
                ).height()) {
                    if (!this.loading) {
                        this.loading = true;
                        http
                            .axios
                            .get(
                                `/api/v1/myMngLecture?page=${this.page}&itemsperpage=${this.itemsperpage}`
                            )
                            .then(({data}) => {
                                this.page++;

                                for (let i in data.result) 
                                    this
                                        .items
                                        .push(data.result[i]);
                                }
                            )
                            . finally(() => {
                                this.loading = false;
                            })
                    }
                }
            },
            move(url) {
                this
                    .$router
                    .push(url)
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

                                this.page = 1;
                                this.loading = true;
                                http
                                    .axios
                                    .get(
                                        `/api/v1/myMngLecture?page=${this.page}&itemsperpage=${this.itemsperpage}`
                                    )
                                    .then(({data}) => {
                                        this.page++;
                                        this.items = data.result;
                                    })
                                    . finally(() => {
                                        this.loading = false;
                                    })

                                document.addEventListener('scroll', this.handleScroll);
                            }
                        })
                        .catch((error) => {
                            console.dir(error)
                        })
                    }
            }
        }
    }
</script>

<style></style>