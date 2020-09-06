<template>
  <div style="; margin-top:20px;">
        <v-card tile flat outlined >
                <v-layout wrap>
                    <v-flex xs12 sm12 md9 lg9 xl9 style="margin-left:0px;">                         
                            <v-list style="padding:20px 10px;">
                                <v-layout wrap>                                    
                                    <v-flex  md12 lg12 xl12> 
                                        <v-list-item>
                                            <v-list-item-title>목차</v-list-item-title> 
                                        </v-list-item> 
                                            <draggable v-model="chapter" @start="drag=true" @end="drag=false" :options="{group:'people'}">                                          
                                                    <template v-for="(item, index) in chapter">                                
                                                        <v-hover v-slot:default="{ hover }" :key="`${index}_hover1`">
                                                            <v-list class="nomargin nopadding">
                                                                <v-list-item class="" :key="`${index}_dragItem`">
                                                                    <v-list-item-content class="nomargin nopadding" style="margin-right:10px;">
                                                                        <v-card class="chapter" :key="`${index}_list`"  outlined>
                                                                            <v-list-item style="">                           
                                                                                <v-list-item-content>                                                                                    
                                                                                    <v-list-item-title>
                                                                                        {{item.title}} 
                                                                                        <div style="float:right">
                                                                                            <v-icon color="primary lighten-1" v-show="item.videoYn">
                                                                                                mdi-play-circle-outline
                                                                                            </v-icon>    
                                                                                            <v-icon style="margin-left:5px;" color="primary lighten-1" v-show="item.wikiYn">
                                                                                                mdi-script-text-outline
                                                                                            </v-icon>                                                                                     
                                                                                        </div>
                                                                                    </v-list-item-title>                                 
                                                                                    
                                                                                    <v-list-item-subtitle style="margin-top:3px;">
                                                                                        <span v-for="(tag,index) in item.tags" :key="`${index}_tag`">
                                                                                        #{{tag}}
                                                                                        </span>
                                                                                    </v-list-item-subtitle>   
                                                                                </v-list-item-content>
                                                                            </v-list-item>
                                                                        </v-card>
                                                                    </v-list-item-content>
                                                                    <v-list-item-action v-if="hover">                                                            
                                                                        <v-btn icon @click="openDialog(item)">
                                                                            <v-icon>
                                                                                mdi-pencil
                                                                            </v-icon>
                                                                        </v-btn>
                                                                    </v-list-item-action>
                                                                    <v-list-item-action v-else> <v-btn icon /> </v-list-item-action>
                                                                    <v-list-item-action v-if="hover">              
                                                                        <v-btn class="nomargin nopadding" icon @click="removeItem(index)">                                             
                                                                            <v-icon>
                                                                                mdi-trash-can
                                                                            </v-icon>
                                                                        </v-btn>
                                                                    </v-list-item-action>
                                                                    <v-list-item-action v-else> <v-btn icon /> </v-list-item-action>
                                                                </v-list-item>                                    
                                                                                                                     
                                                                <v-list-item style="min-height:30px;" @mouseenter="item.showAddChapter=true" @mouseleave="item.showAddChapter=false">                                                                    
                                                                    <template  v-if="item.showAddChapter">
                                                                        <v-icon small color="black" style="margin-right:5px;" @click="openDialog(null, index+1)">
                                                                            mdi-plus
                                                                        </v-icon>
                                                                        <v-list-item-content  style="border:2px dashed #d4d4d4; margin-right:10px;cursor:pointer" @click="openDialog(null, index+1)" />
                                                                        <v-list-item-action> <v-btn icon style="height:0px;"></v-btn> </v-list-item-action>
                                                                        <v-list-item-action> <v-btn icon style="height:0px;"></v-btn> </v-list-item-action>
                                                                    </template>                                                                                                                                
                                                                </v-list-item>                                                                                                
                                                            </v-list>
                                                        </v-hover>  
                                                    </template>
                                            </draggable>                                            
                                            <v-list-item style="min-height:10px; margin-top:10px;" >
                                                <v-list-item-content  style="border:2px dashed #d4d4d4; margin-right:10px;cursor:pointer" @click="openDialog(null, chapter.length)">
                                                    <table width=100%>
                                                        <tr>
                                                            <th>
                                                                <v-icon small color="black">
                                                                    mdi-plus
                                                                </v-icon>
                                                            </th>
                                                        </tr>
                                                    </table>
                                                </v-list-item-content>
                                                <v-list-item-action> <v-btn icon style="height:0px;"></v-btn> </v-list-item-action>
                                                <v-list-item-action> <v-btn icon style="height:0px;"></v-btn> </v-list-item-action>                                                                                    
                                            </v-list-item>
                                    </v-flex>
                                </v-layout>
                            </v-list> 
   
                            <v-list style="padding:0px 10px; margin-bottom:30px;">
                                <v-list-item style="min-height:10px;" >
                                <v-list-item-content style="margin-right:10px;">
                                    <v-layout>
                                    <v-spacer></v-spacer><v-spacer></v-spacer>
                                    <v-btn outlined color="primary" @click="updateChapter">
                                        저장하기
                                    </v-btn>    
                                </v-layout>
                                                </v-list-item-content>
                                                <v-list-item-action> <v-btn icon style="height:0px;"></v-btn> </v-list-item-action>
                                                <v-list-item-action> <v-btn icon style="height:0px;"></v-btn> </v-list-item-action>                                                                                    
                                            </v-list-item>
                                
                                
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
                                <v-list-item-content style="font-size:14px;">                                
                                    <div>
                                        하나의 목차에는 여러 사람들이 영상을 올리고, 위키 문서를 수정할 수 있습니다.
                                        <span class="primary--text">적절한 범위와 주제를 가지고 프로젝트가 어떤 방향으로 나아갈지 제시</span>해보세요! 
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
            color="primary"        
            right            
        >
            {{msg}}
        </v-snackbar>
        <v-dialog v-model="dialog" max-width="780">
            <v-card>
                <v-card-title>
                    <span class="primary--text"> 제목 </span>
                </v-card-title>
                <v-card-text>
                    <v-text-field v-model="title" dense ref="title" id="title"></v-text-field>
                </v-card-text>
                <v-card-title>
                    <span class="primary--text"> 검색 키워드 </span>
                </v-card-title>
                <v-card-text>
                    <v-combobox 
                        multiple 
                        counter=10 
                        hint="키워드 작성 후 엔터키를 눌러 구분할 수 있습니다." 
                        prefix="#" 
                        append-icon=""
                        v-model="tags"
                        dense
                        ref="tags"
                        id="tags"
                    >
                        <template v-slot:selection="{ attrs, item, parent, selected }">
                            <v-chip
                                v-bind="attrs"
                                :color="`primary lighten-4`"
                                :input-value="selected"
                                label
                                small
                            >
                                <span style="color:black">
                                    {{item}}
                                </span>
                            </v-chip>
                        </template>
                    </v-combobox>
                </v-card-text>
                <v-card-actions>
                    
                    <v-layout>
                        <v-spacer></v-spacer>
                        <v-btn depressed outlined color="primary" @click="saveChapter()">
                            저장하기
                        </v-btn>    
                        <div style="margin-right:5px"></div>
                        <v-btn depressed outlined  @click="dialog=false">
                            취소하기
                        </v-btn>    
                    </v-layout>   
                </v-card-actions>
            </v-card>
        </v-dialog>
  </div>
</template>

<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<script>
import http from "@/util/http_common.js"
import axios from "axios"
import store from "@/store/index.js"
import draggable from 'vuedraggable'

export default {
    components :{
        draggable,
    },
    props: ['option'],
    data() {
        return {         
            lectureId: 0,
            content: '',
            snackbar: false,
            msg: '',            
            chapter: [
            ],
            removeChapter: [],

            dialog: false,
            curItem: null,
            curIndex: -1,
            title: '',
            tags: [],
        }
    },
    created(){
        this.lectureId = this.$route.params.id;
        this.getIndexList();
    },
    methods: {
        goto(target, msg){
            this.$vuetify.goTo(target, {
                duration: 300,
                offset: 150,
                easing: 'easeInOutCubic'
            })
            this.msg = msg;
            this.snackbar = true;
        },
        openDialog(item, index){
            this.curIndex = -1;
            if(item == null){
                item = {
                    "commonId": 0,
                    "order": 0,
                    "subId": 0,
                    "title": '',
                    "tags": [],
                    "videoYn": false,
                    "wikiYn": false,
                }
                this.curIndex = index;
            }

            this.curItem = item;
            this.title = item.title;
            this.tags = item.tags;
            this.dialog = true;
        },
        removeItem(index){
            if(this.chapter[index].order != 0)
                this.removeChapter.push(this.chapter[index])
            this.chapter.splice(index, 1)
        },
        updateChapter() {
            let request = []
            for(let i in this.chapter) {
                if(parseInt(this.chapter[i].order) != parseInt(i)+1){
                    request.push({
                        "commonId": this.chapter[i].commonId,
                        "lectureId": this.lectureId,
                        "order": parseInt(i)+1,
                        "subId": this.chapter[i].subId,
                        "tags": this.chapter[i].tags,
                        "title": this.chapter[i].title,
                    })
                }
            }
            for(let i in this.removeChapter) {
                request.push({
                    "commonId": this.removeChapter[i].commonId,
                    "lectureId": this.lectureId,
                    "order": 0,
                    "subId": this.removeChapter[i].subId,
                    "tags": this.removeChapter[i].tags,
                    "title": this.removeChapter[i].title,
                })
            }                        
            
            this.removeChapter = []
            
            http.axios.post('/api/v1/lectures/sub', request).then(({data}) => {                
                this.$router.app.$store.commit('setChange', false); 
            }).finally(() => {
                this.getIndexList();
                this.snackbar = true;
                this.msg = '저장되었습니다.';
            })
        },
        getIndexList() {
            http.axios.get(`/api/v1/lectures/subs/${this.lectureId}`).then(({data}) => {
                this.chapter = [];
                for(let i in data.result){
                    this.chapter.push({
                        "commonId": data.result[i].commonId,
                        "order": parseInt(data.result[i].order),
                        "subId": data.result[i].subId,
                        "tags": data.result[i].tags ? data.result[i].tags.split(',') : [],
                        "title": data.result[i].title,
                        "videoYn": data.result[i].videoYn,
                        "wikiYn": data.result[i].wikiYn,
                    })
                }
            })
        },
        saveChapter(){
            if(!this.title){
                this.goto('#title', '목차명을 입력해주세요.')
                this.$refs.title.focus();   
            }
            else if(!this.tags.length) {
                this.goto('#tags', '검색 키워드를 입력해주세요.')
                this.$refs.tags.focus();   
            }else{
                this.curItem.title=this.title;
                this.curItem.tags=this.tags; 
                this.curItem.order=0; 
                this.dialog=false;
                if(this.curIndex != -1)
                    this.chapter.splice(this.curIndex, 0, this.curItem);
                this.$router.app.$store.commit('setChange', true); 
            }
        }
    }
}
</script>

<style scoped>
    * {
        font-weight: 400;       
        opacity: 1; 
    }
    h1 {
        font-size: 26px;
        font-weight: 500;
        margin-bottom:30px;
        
    }
    .v-list.chapterList {
        height:400px;
        overflow-y:auto;
        margin:10px;
        margin-right:20px;
        background-color:#f2f2f2;

        padding:5px 5px; 
        border-color:f2f2f2;
    }
    .nomargin{
        margin:0px;

    }
    .nopadding{
        padding:0px;         
    }

    .v-list-item__title{
        font-family:-apple-system, "Noto Sans KR",BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen-Sans, Ubuntu, Cantarell, "Helvetica Neue", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
        font-size:16px;
        font-weight: 500;
    }
    .v-list-item__subtitle{
        font-family:-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen-Sans, Ubuntu, Cantarell, "Helvetica Neue", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
        font-size:12px;
        font-weight: 500;
    }
   
    .chapterContent{
        /* font-family:-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen-Sans, Ubuntu, Cantarell, "Helvetica Neue", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji"; */
        /* font-size:12px; */
    } 

    .chapter {
        margin: 5px 0px; 
        border:3px solid rgba(0, 0, 0, 0.34); 
        cursor:pointer;
    }

    .chapter:hover {
        transform: translateY(-4px);
        box-shadow: 0 3px 8px 0 rgba(0,0,0,.08), 0 0 1px 0 rgba(0,0,0,.44);
    }
</style>