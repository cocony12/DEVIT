<template>
    <div>
        <v-layout wrap style="width:100%;">            
            <div id="pos" style="height:0px" />
            <v-list id="question" style="width:100%" dense :style="{'height' : questionHeight+'px'}">
                <v-list-item>
                    <v-list-item-content class="wrap-text">
                        {{item.boardTitle}} 
                    </v-list-item-content>
                </v-list-item>
                <v-list-item>
                    <v-list-item-avatar size="30" style="margin-right:10px;">
                            <v-img 
                                v-if="item.profile"
                                :src="'http://i3a101.p.ssafy.io/images/' + item.profile"
                            ></v-img>
                    </v-list-item-avatar >
                    <v-list-item-content>
                        <v-list-item-title>
                            {{item.nickName}}
                        </v-list-item-title>
                        <v-list-item-subtitle>
                            {{item.boardModified | moment('YYYY.MM.DD. HH:mm')}} 조회 {{item.boardCount}}
                        </v-list-item-subtitle>
                    </v-list-item-content>
                    <v-list-item-action v-if="item.isMine == 'Y'">
                        <v-menu bottom left>
                            <template v-slot:activator="{ on, attrs }">
                            <v-btn
                                :dark="darkOption"
                                icon
                                v-bind="attrs"
                                v-on="on"
                            >
                                <v-icon>mdi-dots-vertical</v-icon>
                            </v-btn>
                            </template>
                            <v-list style="margin:0px;padding:0px;" :dark="darkOption">
                                <v-list-item link @click="editMode=true">
                                    <v-list-item-title>수정하기</v-list-item-title>
                                </v-list-item>
                                <v-list-item link @click="removeBoard">
                                    <v-list-item-title>삭제하기</v-list-item-title>
                                </v-list-item>
                            </v-list>
                        </v-menu>
                    </v-list-item-action>
                </v-list-item>
                <v-list-item>                    
                    <v-divider />
                </v-list-item>
                <v-list-item>
                    <v-list-item-content>
                        <v-list-item-subtitle class="wrap-text" v-if="!editMode">
                            {{item.boardContent}}
                        </v-list-item-subtitle>
                        <div v-else>
                            <div>
                                <v-textarea v-model="item.boardContent" color="success" outlined></v-textarea>                            
                            </div>
                            <div style="float:right">
                                <v-btn outlined color="success" @click="updateBoard">저장</v-btn>
                                <v-btn outlined color="success" style="margin-left:5px;" @click="initBoard(); editMode=false">취소</v-btn>
                            </div>
                        </div>
                    </v-list-item-content>
                    
                </v-list-item>
                <v-list-item style="min-height:0px; margin-top:20px;">                    
                    <v-divider />
                </v-list-item>
                <v-list-item class="nomargin">
                    <v-list-item-subtitle class="nomargin">
                    답글 {{item.replyCount}}
                    </v-list-item-subtitle>
                </v-list-item>
                <template v-for="(replyItem,i) in reply">
                    <v-list-item  :key="`${i}_reply`" three-line v-if="editReplyIdx != i">
                        <v-list-item-avatar size="30" style="margin-right:10px;">
                            <v-img 
                                v-if="replyItem.profile"
                                :src="'http://i3a101.p.ssafy.io/images/' + replyItem.profile"
                            ></v-img>
                        </v-list-item-avatar >
                        <v-list-item-content>
                            <v-list-item-title style="margin-bottom:4px;">
                                <span style="font-weight:700; font-family:'Malgun Gothic';">{{replyItem.userName}}</span>
                            </v-list-item-title>
                            <v-list-item-subtitle class="wrap-text" v-html="replyItem.replyContent" style="font-weight:400; font-family:'Malgun Gothic';" :style="{'color': (darkOption ? '' : 'black') }">
                            </v-list-item-subtitle>
                            <v-list-item-subtitle style="color:#979797; font-size:12px; font-weight:400; font-family:'Malgun Gothic'; margin-top:7px;">
                                {{replyItem.replyModified | moment('YYYY.MM.DD. HH:mm')}} <template v-if="replyItem.isMine=='Y'">&middot; <span style="font-size:12px;cursor:pointer;" @click="editReplyIdx=i">수정</span>  <span style="font-size:12px;cursor:pointer;" @click="removeReply(replyItem)">삭제</span>  </template>
                            </v-list-item-subtitle> 
                        </v-list-item-content>                    
                    </v-list-item>
                    <v-list-item :key="`${i}_replyUpdate`"  v-else>
                        <v-list-item-content>
                            <div>
                                <div>
                                    <v-textarea v-model="replyItem.replyContent" color="success" outlined></v-textarea>                            
                                </div>
                                <div style="float:right">
                                    <v-btn outlined color="success" @click="updateReply(replyItem)">저장</v-btn>
                                    <v-btn outlined color="success" style="margin-left:5px;" @click="initBoard(); editReplyIdx=-1">취소</v-btn>
                                </div>
                            </div>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item :key="`${i}_divider`" style="min-height:0px;">
                        <v-divider />
                    </v-list-item>
                </template>
                <v-list-item>
                    <v-textarea dense outlined auto-grow placeholder="답글을 입력해보세요!" color="success" hide-details style="margin:10px 0px" v-model="replyContent" @focus="checkSession()"></v-textarea>                          
                </v-list-item>
                <v-list-item>
                    <v-list-item-content /> 
                    <v-list-item-action>
                        <v-btn color="success" outlined @click="writeReply">
                            댓글 입력
                        </v-btn>
                    </v-list-item-action>
                </v-list-item>
            </v-list>
        </v-layout>
    </div>
</template>

<script>
import http from "@/util/http_common.js"
import eventBus from "@/lib/EventBus.js"

export default {
    props: ['darkOption', 'tabs', 'boardId' , 'refresh'],
    data() {
        return {
            item: {},
            reply: {},
            replyContent: '',
            questionHeight: 768,

            editMode: false,
            editReplyIdx : -1,
        }
    },
    watch: {
        boardId() {
            this.initBoard();
        },
        tabs() {
            this.editMode = false;
        }
    },
    created(){
        this.initBoard()       
    },
    mounted() {
        this.detailBoardResize()
        window.addEventListener('resize', this.detailBoardResize)
    },
    beforeDestroy(){
        window.removeEventListener('resize', this.detailBoardResize)
    },
    methods:{
        checkSession() {
            if(this.$router.app.$store.state.token){

            }else{
                eventBus.$emit('doLogin');
            }
        },
        detailBoardResize() {      
            if(this.tabs == 4)      
                this.questionHeight = $('body').height()-$('#pos').offset().top
        },
        writeReply() {
            http.axios.post(`/api/v1/reply`,{
                "boardId": this.boardId,
                "parentReplyId": 0,
                "replyContent": this.replyContent,
            }).then(({data}) => {
                this.reply = data.result
            }).finally(() => {
                this.replyContent = '';
                this.initBoard();
            })
        },
        initBoard(){
            http.axios.get(`/api/v1/board/${this.boardId}`).then(({data}) => {
                this.item = data.result
            })
            http.axios.get(`/api/v1/reply/${this.boardId}`).then(({data}) => {
                this.reply = data.result
            }) 
        },
        removeBoard() {
            http.axios.delete(`/api/v1/board/${this.boardId}`).then(({data}) => {
                this.$emit('removeBoard');
            })
        },
        updateBoard() {
            http.axios.put(`/api/v1/board`, {                
                "boardContent": this.item.boardContent,
                "boardId": this.item.boardId,
                "boardTitle": this.item.boardTitle,
                "boardType": this.item.boardType   
            }).then(({data}) => {
                this.initBoard();
                this.editMode = false;
            })
        },

        removeReply(item) {
            http.axios.delete(`/api/v1/reply/${item.boardReplyId}`).then(({data}) => {
                this.initBoard();
            })
        },

        updateReply(item) {
            http.axios.put(`/api/v1/reply`, {                
                "boardId": item.boardId,
                "boardReplyId": item.boardReplyId,
                "replyContent": item.replyContent,
            }).then(({data}) => {
                this.initBoard();
                this.editReplyIdx = -1;
            })
        },
    }
}
</script>

<style scoped>
.wrap-text {
  -webkit-line-clamp: unset !important;
  white-space: normal;  
}

#question {
  height: 650px;
  overflow-y: auto;
  padding-right:15px;
}

    .nomargin{
        margin:0px;

    }
    .nopadding{
        padding:0px;         
    }

</style>