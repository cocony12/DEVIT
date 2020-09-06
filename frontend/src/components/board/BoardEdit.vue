<template>
    <div id="app">
        <v-container>
            <v-container fluid="fluid">
                <template>
                    <v-col :key="boardId">
                        <div class="board_detail">
                            <v-list-item>
                                <v-spacer></v-spacer>
                                <v-hover v-slot:default="{ hover }">
                                    <v-btn
                                        outlined="outlined"
                                        depressed="depressed"
                                        text="text"
                                        small="small"
                                        @click="saveTemp()"
                                        style="margin-right:5px">   
                                        <font :color="hover ? '' : 'gray'" size="2">임시저장</font>
                                    </v-btn>
                                </v-hover>
                                <v-hover v-slot:default="{ hover }">
                                    <v-btn
                                        outlined="outlined"
                                        depressed="depressed"
                                        text="text"
                                        small="small"
                                        @click="saveboard()"
                                        style="margin-right:5px">
                                        <font :color="hover ? '' : 'gray'" size="2">확인</font>
                                    </v-btn>
                                </v-hover>
                            </v-list-item>
                            <v-list-item>
                                <v-list-item-content>
                                    <v-list-item>
                                        <span style="width:80px">게시판</span>
                                        <v-select
                                            readonly
                                            style="max-width:200px;"
                                            v-model="boardType"
                                            :items="boardtypeitems"
                                            item-text="name"></v-select>
                                    </v-list-item>
                                    <v-list-item>
                                        <span style="width:80px">제목</span>
                                        <v-text-field v-model="boardTitle" counter="50"></v-text-field>
                                    </v-list-item>
                                </v-list-item-content>
                            </v-list-item>

                            <v-list-item>
                                <v-list-item-title>
                                    <!-- <div>
                                        <v-textarea
                                            v-model="boardContent"
                                            no-resize="no-resize"
                                            outlined="outlined"
                                            rows="10"></v-textarea>
                                    </div> -->
                                    <div style="margin-top:15px;">
                                        <editor v-model="content" height="450"> </editor>
                                    </div>
                                </v-list-item-title>
                            </v-list-item>
                        </div>
                    </v-col>
                </template>
            </v-container>
            <v-snackbar v-model="snackbar" bottom="bottom" timeout="1500">
                {{ text }}
                <template v-slot:action="{ attrs }">
                    <v-btn text="text" v-bind="attrs" color="success" @click="snackbar = false">닫기</v-btn>
                </template>
            </v-snackbar>
            <v-snackbar v-model="errorSnackbar" timeout="1500" color="error">
                {{errorMsg}}
            </v-snackbar>
        </v-container>
    </div>
</template>

<script>
    import http from "@/util/http_common.js"
    import store from "@/store/index.js" 
    import Editor from "@/components/common/Editor.vue"
    import parse from "@/lib/markdown/ParseMd.js";
    import convertHTML from "@/lib/markdown/ConvertHTML.js";

    export default {
        components: {
            Editor,
        },
        name: 'app',
        data() {
            return {
                boardtypeitems: [
                    {
                        type: 1,
                        name: '공지사항'
                    }, {
                        type: 2,
                        name: '자유 게시판'
                    }, {
                        type: 3,
                        name: '질문 게시판'
                    }, {
                        type: 4,
                        name: '신고 게시판'
                    }
                ],
                //item:{},
                boardType: null,
                boardId: null,
                userId: '',
                userName: '',
                boardTitle: '',
                boardContent: '',
                boardCreated: '',
                boardModified: '',
                boardCount: null,
                likes: null,
                comment_count: null,
                //boardType: '0',
                snackbar: false,
                text: "",
                errorSnackbar: false,
                errorMsg:"",
                content:""
            }
        },
        created(){
            if(!store.state.token) {
                this.$router.push('/')
            }
        },
        mounted() {
            this.boardType = this.boardtypeitems[(Number)(this.$route.query.boardtype)-1];
            var edittype = this.$route.query.edittype;
            if (edittype == "modify") {
                http
                    .axios
                    .get("/api/v1/board/" + this.$route.query.boardId, {
                    })
                    .then(({data}) => {
                        //this.item = data.result;
                        //this.boardType = data.result.boardType,
                        this.boardId = data.result.boardId,
                        this.boardTitle = data.result.boardTitle,
                        //this.boardContent = data.result.boardContent,         
                        this.content = data.result.boardContentHtml,                        
                        this.boardType = this.boardtypeitems[data.result.boardType-1];
                        // this.boardType = data.result.boardType,
                        
                    })
                    .catch((error) => {
                        console.dir(error)
                    })
                }else if(edittype == "temp"){                    
                    var item = this.$route.params.item;
                    this.boardTitle = item.boardTitle;
                    //this.boardContent = item.boardContent;
                    this.content = item.boardContent;
                    this.boardType = this.boardtypeitems[(Number)(item.boardType)-1];

                }
        },
        methods: {
            parse,
            convertHTML,
            saveTemp() {
                var date = new Date();
                var jsonData = {
                    boardTitle: this.boardTitle,
                    //boardContent: this.boardContent,
                    boardContent: this.content,
                    boardType: this.boardType.type,
                    createDate: date
                };
                var jsonArray = localStorage.getItem(store.state.email);
                if (jsonArray == null) {
                    jsonArray = [];
                } else {
                    jsonArray = JSON.parse(jsonArray);
                }
                jsonArray.push(jsonData);
                localStorage.setItem(store.state.email, JSON.stringify(jsonArray));
                this.$router.app.$store.commit('setChange', false);    
                this.text = "임시저장 되었습니다."
                this.snackbar = true;
            },
            saveboard() {
                var edittype = this.$route.query.edittype;
                //alert(edittype);
                if (edittype == "new" || edittype == "temp") {
                    http
                        .axios
                        .post("/api/v1/board", {
                            boardTitle: this.boardTitle,
                            //boardContent: this.boardContent,
                            boardContent: this.convertHTML(this.parse(this.content)),
                            boardContentHtml: this.content,
                            boardType: this.boardType.type,
                            boardCreated: "",
                            boardCount: "",
                            boardModified: ""
                        })
                        .then(({data}) => {

                            if(data.msg == "noauth"){

                                this.errorMsg = '글쓰기 권한이 없습니다.';
                                this.errorSnackbar = true;

                            }else{
                                this.$router.app.$store.commit('setChange', false);    
                                this
                                .$router
                                .push({
                                    name: 'BoardDetail',
                                    params:{
                                        "showMsg": true,
                                        "msgText": "작성이 완료되었습니다."
                                        },
                                    query: { 
                                        'boardtype':this.boardType.type,
                                        'boardId': data.result, //data.boardId
                                    }
                                });
                            }                            
                        })
                        .catch((error) => {
                            console.dir(error)
                        })

                    } else if (edittype == "modify") {

                    http
                        .axios
                        .put("/api/v1/board", {
                            boardId: this.$route.query.boardId,
                            boardTitle: this.boardTitle,
                            //boardContent: this.boardContent,
                            boardContent: this.convertHTML(this.parse(this.content)),
                            boardContentHtml: this.content,
                            boardType: this.boardType.type,
                            //boardCreated: "",
                            boardCount: "",
                            boardModified: ""
                        })
                        .then(({data}) => {   
                            if(data.msg == "noauth"){

                                this.errorMsg = '수정 권한이 없습니다.';
                                this.errorSnackbar = true;
                                
                            }else{                       
                                this.$router.app.$store.commit('setChange', false);    
                                this
                                    .$router
                                    .push({path:'/board/detail', 
                                        params:{
                                            "showMsg": true,
                                            "msgText": "수정이 완료되었습니다."
                                            },
                                            query:{
                                        "boardtype": this.boardType.type,
                                        "boardId": data.result,/*data.boardId*/
                                    }});
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

<style>
    .board_detail {
        padding: 20px;
        min-height: 250px;
    }
</style>