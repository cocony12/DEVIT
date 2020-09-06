<template>
    <v-container fluid="fluid" style="padding:12px 0px;">
        <div style="width:100%">
            <v-btn
                v-if="isAdmin"
                @click="newBoard()"
                depressed="depressed"
                outlined="outlined"
                style="float:right; margin-right:12px;" 
                color="normal">글쓰기</v-btn>
        </div>
        <div v-if="items.length > 0">
            <v-data-iterator
                class="board_list"
                style="margin-top:30px;"
                :items="items"
                :items-per-page.sync="itemsPerPage"
                hide-default-footer="hide-default-footer"
                no-data-text="게시글이 존재하지 않습니다.">
                <template v-slot:default="props">
                    <v-col v-for="item in props.items" :key="item.boardId">
                        <v-card @click="goToDetail(item.boardId)" style="cursor:pointer">
                            <v-list-item>
                                <v-list-item-avatar size="40" style="margin-right:0px;">
                                    <v-img :src="'http://i3a101.p.ssafy.io/images/' + item.profile"></v-img>
                                </v-list-item-avatar>
                                <v-list-item-content>
                                    <v-list-item class="board_writer">{{ item.userName }}</v-list-item>
                                    <v-list-item class="board_info">
                                        {{item.boardModified ? item.boardModified : item.boardCreated | moment('YYYY-MM-DD HH:mm')}}
                                        <v-icon size="15" style="margin:0 5px;">mdi-eye</v-icon>
                                        {{item.boardCount}}
                                        <v-icon size="15" style="margin:0 5px;">mdi-comment-processing</v-icon>
                                        {{item.replyCount}}
                                    </v-list-item>
                                </v-list-item-content>
                            </v-list-item>

                            <v-divider></v-divider>

                            <v-list dense="dense">

                                <v-list-item class="board_title">
                                    {{ item.boardTitle }}
                                </v-list-item>
                                <v-list-item class="board_content">
                                    {{ item.boardContent }}
                                </v-list-item>
                                <v-list-item>
                                    <a href="#" class="more">더보기</a>
                                </v-list-item>

                            </v-list>
                        </v-card>
                    </v-col>

                </template>
            </v-data-iterator>

            <v-container>
                <v-row justify="center">
                    <v-col cols="8">
                        <v-pagination v-model="page" class="my-4" :length="pageCnt"></v-pagination>
                    </v-col>
                </v-row>
            </v-container>
        </div>
        <div v-else style="width:100%;">                    
                <v-row style="width:100%;">
                    <v-col cols="12">
                        <v-row align="start" justify="center">                                    
                            <v-icon style="font-size:150px; color:rgba(0, 0, 0, 0.54); margin:30px 0 20px 0"> mdi-emoticon-cry-outline </v-icon>                               
                        </v-row>
                        <v-row align="start" justify="center">       
                            <div style="font-size:20px; margin-bottom:20px;"> 게시글이 존재하지 않습니다 :( </div>    
                        </v-row>
                    </v-col>
                </v-row>            
        </div>

        <v-container>
            <v-row justify="center">

                <v-select
                    dense="dense"
                    outlined="outlined"
                    style="max-width:100px; margin-right:10px;"
                    v-model="searchselect"
                    :items="searchitems"></v-select>

                <v-card flat="flat" rounded="rounded">
                    <v-text-field
                        placeholder="검색어를 입력하세요"
                        v-model="searchTxt"
                        solo="solo"
                        dense="dense"
                        flat="flat"
                        outlined="outlined"
                        @keydown.enter="searchByPage"></v-text-field>
                </v-card>

                <v-btn dense="dense" icon="icon" @click="searchByPage" ref="searchBtn">
                    <v-icon ref="searchIcon">
                        mdi-magnify
                    </v-icon>
                </v-btn>
            </v-row>
        </v-container>
    </v-container>
</template>

<script>
    import eventBus from "@/lib/EventBus.js"
    import http from "@/util/http_common.js"

    export default {
        name: 'local-component',
        props: ['boardtype'],
        watch: {
            page() {
                this.searchByPage()
            }
        },
        data() {
            return {
                searchTxt: "",
                page: 1,
                pageCnt: 0,
                searchselect: '전체',
                searchitems: [
                    '전체', '제목', '내용', '작성자'
                ],
                itemsPerPageArray: [
                    10, 20, 50
                ],
                itemsPerPage: 6,
                items: [
                    {
                        // boardId: 2, userName: '홍길동', boardTitle: '글제목2', boardContent: '글내용2',
                        // boardCreated: '2020/07/06', boardModified: '2020/07/06', boardCount: '3,743',
                        // likes: '152'
                    }
                ],
                isAdmin: false
            }
        },
        mounted() {
            //boardtype별 조회

            http
                .axios
                .get(`/api/v1/users/user/isadmin`, {})
                .then(({data}) => {
                    this.isAdmin = (this.boardtype != 1 || data.result == 'Y');
                })
                .catch((error) => {
                    console.dir(error)
                });
                
            this.searchByPage();
        },
        methods: {
            searchByPage() {
                if (this.searchTxt != null && this.searchTxt != "") {
                    this.page = 1;
                }
                http
                    .axios
                    .get(
                        `/api/v1/board/list?page=${this.page}&type=${this.boardtype}&itemsperpage=${this.itemsPerPage}&searchselect=${this.searchselect}&searchtxt=${this.searchTxt}`,
                        {}
                    )
                    .then(({data}) => {
                        this.items = data.result;
                        this.pageCnt = data
                            .result[0]
                            .pageCnt;
                    })
                    .catch((error) => {
                        console.dir(error)
                    })
                },
            goToDetail(boardId) {
                this
                    .$router
                    .push({
                        path: '/board/detail',
                        query: {
                            "boardtype": this.boardtype,
                            "boardId": boardId
                        }
                    });

            },
            newBoard() {
                // eventBus.$emit('newBoard');
                if (this.$router.app.$store.state.token) {
                    this
                        .$router
                        .push({
                            path: '/board/edit',
                            query: {
                                "boardtype": this.boardtype,
                                "edittype": "new"
                            }
                        });
                } else {
                    eventBus.$emit('doLogin');
                }
            }
        }
    }
</script>

<style scoped="scoped">
    .board_list .v-list-item {
        min-height: 10px;
    }

    .board_list .board_writer {
        font-weight: bolder;
    }

    .board_list .board_info {
        font-size: 12px;
        color: #585858 !important;
    }

    .board_list .board_title {
        font-weight: bold;
    }

    .board_list .board_content {
        font-size: 13px;
        margin: 10px 5px;
    }

    .board_list .more {
        color: #888888 !important;
        text-decoration: none;
        font-size: 12px;
    }

    .v-card:hover {
        transform: translateY(-4px);
        box-shadow: 0 3px 8px 0 rgba(0,0,0,.08), 0 0 1px 0 rgba(0,0,0,.44);
        cursor:pointer;
    }
</style>