<template>
    <div style="margin:50px">
        <v-container justify-center="justify-center">

            <div style="width:100%; margin:0 auto;">
                <span style="font-size:26px; font-weight:600; color:#1976d2 !important;">내가 좋아요한 동영상</span>
            </div>
            <div v-if="items.length > 0">
            <v-layout row="row" wrap="wrap">
                
                    <v-flex
                    v-for="(item,i) in items"
                    :key="`4${i}`"
                    xs12="xs12"
                    sm6="sm6"
                    md4="md4"
                    lg3="lg3"
                    xl2="xl2">
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
        </v-container>
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
            return {items: [], level: this.$route.query.level, page: 1, itemsperpage: 20, loading: false}
        },
        filters: {
            convertView(num) {
                if (num < 1000) {
                    return num + '회'
                }

                if (num >= 100000000) {
                    num /= 100000000;
                    return parseFloat(num).toFixed(2) + '억회'
                }
                if (num >= 10000) {
                    num /= 10000;
                    return parseFloat(num).toFixed(0) + '만회'
                }
                if (num >= 1000) {
                    num /= 1000;
                    return parseFloat(num).toFixed(1) + '천회'
                }
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
            this.loading = true;
            http
                .axios
                .get(`/api/v1/myLikeVideo?page=${this.page}&itemsperpage=${this.itemsperpage}`)
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
                            .get(`/api/v1/myLikeVideo?page=${this.page}&itemsperpage=${this.itemsperpage}`)
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
            }
        }
    }
</script>

<style></style>