<template>
  <div style="; margin-top:20px;">
        <v-card tile flat outlined >
                <v-layout wrap>
                    <v-flex xs12 sm12 md9 lg9 xl9 style="margin-left:0px;">                         
                            <v-list style="padding:20px 100px;">
                                <v-list-item>
                                    <v-list-item-title>소개</v-list-item-title> 
                                </v-list-item>
                                <v-list-item>
                                    <v-list-item-title>
                                        <div style="margin-top:15px;">
                                            <editor v-model="content" height="450"> </editor>
                                        </div>
                                    </v-list-item-title>
                                </v-list-item>
                                <v-list-item style="margin-top:20px;">
                                    <v-layout>
                                        <v-spacer></v-spacer>
                                        <v-btn  color="primary" outlined @click="ValidationForm">
                                            저장하기
                                        </v-btn>    
                                        <div style="margin-right:7px"></div>
                                    </v-layout>
                                </v-list-item>
                            </v-list> 
   
                            <v-list style="padding:20px 100px;">
                                
                            </v-list>      
                    </v-flex>                    
                    <v-flex v-show="option" style="border-left:1px solid #e2e2e2" md3 lg3 xl3> 
                        <v-list style="position: sticky; top:60px;" three-line>
                            <v-list-item >
                                <v-list-item-title>                                    
                                    가이드                                    
                                </v-list-item-title>                                
                            </v-list-item>
                            <v-list-item>                                       
                                <v-list-item-content style="font-size:14px">                                
                                    <div>
                                        소개는 
                                        <span class="primary--text"> 
                                            지식 공유자와 수강자에게 프로젝트의 방향성에 대해 소개
                                        </span>하는 공간입니다. 
                                        누구를 위해 무엇을 만들어나갈지 설명해주세요!
                                    </div>                                
                                </v-list-item-content>                                    
                            </v-list-item>                            
                        </v-list>
                    </v-flex>
                </v-layout>
        </v-card>
        <v-snackbar
            v-model="snackbar"
            timeout="1500"
            :color="color"        
            right                        
        >
            {{msg}}


        </v-snackbar>
  </div>
</template>

<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<script>
import http from "@/util/http_common.js"
import axios from "axios"
import store from "@/store/index.js"
import Editor from "@/components/common/Editor.vue"
import convertHTML from "@/lib/markdown/ConvertHTML.js";
import parse from "@/lib/markdown/ParseMd.js";

export default {
    components: {
        Editor,
    }, 
    props: ['option','tab','curTab'],
    watch: {
        curTab(){
            if(this.tab == this.curTab){
                this.getIntroPage();
            }
        },
        content() {
            
        }
    },
    data() {
        return {         
            commonId: 0,
            lectureId: 0,
            content: '123',
            
            snackbar: false,
            msg: '',            
            color:'success',
        }
    },
    created(){
        this.lectureId = this.$route.params.id;
        this.getIntroPage();
    },
    methods: {
        parse,
        convertHTML,
        ValidationForm(){
            if(!this.content){          
                this.msg = "내용을 입력해주세요!";
                this.snackbar = true;                         
                this.color = "error darken-1"
            }else{
                this.saveIntroPage();
            }
        },
        getIntroPage(){
            http.axios.get(`/api/v1/lectures/${this.lectureId}`).then(({data}) => {
                if(data.msg == 'success') {
                    this.content = data.result.content;
                    this.commonId = data.result.commonId;
                }
            })
        },
        saveIntroPage(){
            http.axios.put('/api/v1/lectures/content', {  
                "commonId": this.commonId,
                "lectureId": this.lectureId,
                "content": this.content,
            }).then(({data}) => {
                this.snackbar = true;
                this.msg = '저장되었습니다.';
                this.color = "success";
                this.$router.app.$store.commit('setChange', false);            
            })
        }
        
    }
}
</script>

<style scoped>
    * {
        font-weight: 400;
    }
    h1 {
        font-size: 26px;
        font-weight: 500;
        margin-bottom:30px;
    }
    
</style>