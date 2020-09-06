<template>
    <div style="; margin-top:20px;">
        <v-card tile="tile" flat="flat">
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
                                        {{ item.boardContent }}
                                    </v-list-item>
                                    <v-list-item class="board_content">
                                        {{ item.boardTitle }}
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
            <div v-else>
                <v-container fluid style="width:100%;">         
                    <v-row>
                        <v-col cols="12">
                            <v-row align="start" justify="center">                                    
                                <v-icon style="font-size:150px; color:rgba(0, 0, 0, 0.54); margin:30px 0 20px 0"> mdi-emoticon-cry-outline </v-icon>                               
                            </v-row>
                            <v-row align="start" justify="center">       
                                <div style="font-size:20px; margin-bottom:20px;"> 댓글이 존재하지 않습니다 :( </div>    
                            </v-row>
                        </v-col>
                    </v-row>
                </v-container>
            </div>
        </v-card>
        <v-snackbar v-model="snackbar" timeout="1500" color="primary">
            {{msg}}
        </v-snackbar>
    </div>
</template>

<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<script>
    import http from "@/util/http_common.js"
    import axios from "axios"
    import store from "@/store/index.js"
    export default {
        props: [],
        watch: {
            page() {
                this.searchByPage()
            }
        },
        data() {
            return {page: 1, pageCnt: 0, itemsPerPage: 6, items: [{}]}
        },
        watch: {
            page() {
                this.searchByPage()
            }
        },
        created(){
            if(!store.state.token) {
                this.$router.push('/')
            }
        },
        mounted() {
            this.searchByPage();
        },
        methods: {
            searchByPage() {

                http
                    .axios
                    .get(`/api/v1/myreply?page=${this.page}&itemsperpage=${this.itemsPerPage}`, {})
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
            goToDetail(boardId, boardType, lectureId, subId) {
                if (!lectureId) {

                    this
                    .$router
                    .push({
                        path: '/board/detail',
                        query: {
                            "boardtype": boardType,
                            "boardId": boardId
                        }
                    });
                }
            }
        }
    }
</script>

<style scoped="scoped">
    .v-list-item__title {
        font-size: 14px;
    }
    .v-list-item__subtitle {
        margin-top: 10px;
        font-size: 12px;
    }

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