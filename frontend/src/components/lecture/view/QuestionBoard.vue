<template>
    <v-layout wrap ref="main">
        <v-spacer></v-spacer>
        <v-btn v-if="!isWrite" light color="success" outlined @click="clickQuestionButton()">
            질문하기
        </v-btn>
        <v-list v-else style="width:100%;" :dark="darkOption" >
            <v-list-item>
                <v-text-field dense outlined placeholder="제목을 입력하세요." color="success" hide-details style="margin-bottom:10px" v-model="title"></v-text-field>                
            </v-list-item>
            <v-list-item>                
                <v-textarea dense outlined auto-grow placeholder="" color="success" hide-details style="margin-bottom:4px" v-model="content"></v-textarea>                                
                
            </v-list-item>
            <v-list-item>
                <v-list-item-content>
                </v-list-item-content>
                <v-list-item-action style="margin-top:0px;margin-bottom:0px;">
                    <v-btn light color="success" outlined @click="writeBoard">
                        질문
                    </v-btn>                    
                </v-list-item-action>
                <v-list-item-action style="margin-top:0px;margin-bottom:0px;">
                    <v-btn light color="success" outlined @click="isWrite=false ">
                        취소
                    </v-btn>                    
                </v-list-item-action>
            </v-list-item>
        </v-list>
        <div id="question" ref="question" :style="{'height': questionHeight+'px'}" style="width:100%; overflow-y:auto; margin-top:20px;">
            <v-list v-if="true" link>
                <div class="boardContent" style="width:100%; border:1px solid #4CAF50; border-radius:5px; margin:10px 0px;" v-for="(item,i) in items" :key="i+'_div'" @click="$emit('show-detail', item.boardId)">
                    <v-list-item style="width:100%;">
                        <v-list-item-content style="padding-bottom:0px;">                            
                            <div class="v-list-item__title">{{item.boardTitle}}</div>
                            <div class="v-list-item__subtitle">{{item.boardContent}}</div>
                        </v-list-item-content>
                    </v-list-item>
                    <v-list-item>
                        <v-list-item-content style="padding-top:8px;">
                            <v-list-item-subtitle>                          
                                <v-avatar
                                    class="profile"
                                    size=15
                                >
                                    <v-img 
                                        v-if="item.profile"
                                        :src="'http://i3a101.p.ssafy.io/images/' + item.profile"
                                    ></v-img>
                                </v-avatar>
                                <span style="margin-left:3px; font-size:12px;">{{item.nickName}}</span> &nbsp;&nbsp;
                                <span style="font-size:12px;"><v-icon small>mdi-comment-text</v-icon> {{item.replyCount}}</span>&nbsp;
                                <span style="font-size:12px;">조회 {{item.boardCount}}</span>
                                 
                            </v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item>
                </div>
            </v-list>
            <div
                v-else 
                style="
                    width:100%;height:100%; display: -webkit-box;
                    display: flex;
                    -webkit-box-pack: center;
                    justify-content: center;
                    -webkit-box-align: center;
                    align-items: center;
                    font-size:14px;
                    font-weight:400;
                "
            > 
                첫번째 질문을 남겨보세요!
            </div>  
        </div>           
    </v-layout>
</template>

<script>
import http from "@/util/http_common.js"
import Editor from "@/components/common/Editor.vue"
import eventBus from "@/lib/EventBus.js"
export default {
    components: {
        Editor,
    },
    props: ['darkOption', 'lectureId', 'subId','refresh'],
    data(){
        return {            
            questionHeight: 768,
            dialog:true,
            isWrite:false,

            title: '',
            content: '',

            items: []
        }
    },
    watch: {
        subId() {
            this.initBoard()
        },
        refresh() {
            this.initBoard();
            this.questionBoardResize();
        }
    },
    created() {
        
    },
    mounted(){
        this.initBoard();
        this.questionBoardResize()
        window.addEventListener('resize', this.questionBoardResize())
    },
    beforeDestroy(){
        window.removeEventListener('resize', this.questionBoardResize())
    },
    methods:{
        clickQuestionButton() {
            if(this.$router.app.$store.state.token){        
                this.isWrite=true;
                this.title=''; 
                this.content='';
            }else{
                eventBus.$emit('doLogin');
            }
        },
        questionBoardResize() {
            this.questionHeight = $('body').height()-$('#question').offset().top
        },
        initBoard() {
            http.axios.get(`/api/v1/board/lecture?lectureId=${this.lectureId}&subId=${this.subId}`).then(({data}) => {
                
                this.items = data.result;
            })
        },
        writeBoard(){
            http.axios.post("/api/v1/board/lecture", {
                "boardTitle": this.title,
                "boardContent": this.content,
                "lectureId": this.lectureId,
                "subId": this.subId
            }).then(({data}) => {

            }).finally(() => {
                this.initBoard();
                this.title = '',
                this.content = '',
                this.isWrite = false
            })
        }        
    }
}
</script>

<style scoped>
    * {
        /* font-weight: 500; */
        
    }

    .boardContent:hover {
        transform: translateY(-4px);
        box-shadow: 0 3px 8px 0 rgba(0,0,0,.08), 0 0 1px 0 rgba(0,0,0,.44);
        cursor:pointer;
    }

    /* .v-text-field__details{
        display: none !important;
        margin-bottom: 100px !important;
        height:0px !important;        
    } */
</style>