<template>
    <div style="margin:40px">
        <v-container justify-center="justify-center">
            <v-layout row="row" wrap="wrap">
                <v-container fluid v-show="!items.length" >         
                        <v-row>
                            <v-col cols="12">
                                <v-row
                                    align="end"
                                    justify="center"
                                style="height: 300px;"
                                >                                    
                                    <v-icon style="font-size:120px;"> mdi-emoticon-cry-outline </v-icon>                                
                                </v-row>
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col cols="12">
                                <v-row
                                    align="end"
                                    justify="center"
                                >       
                                    <div style="font-size:20px"> 요청 목록이 비어있습니다 :( </div>    
                                </v-row>
                            </v-col>
                        </v-row>
                    </v-container>
                    
                    <v-flex xs12 sm12 md8 lg8 xl8 style="margin-left:0px;" v-if="items.length ">                         
                            <v-list style="padding:10px 0px;" dense>
                                <template v-for="(item,index) in items">
                                    <v-divider :key="`${index}_divider`"/>
                                    <div class="day_header" :key="`${index}_listHeader`" v-if="!item.subHisId">
                                        <span class="day">{{item}}</span>
                                    </div>
                                    <v-list-item :key="`${index}_memberList`" link @click="curItem=item;initDetail(item)" v-else>
                                        <v-list-item-avatar size="30">
                                            <v-icon color="" v-if="item.reqType == 'video'">
                                                mdi-play-circle-outline
                                            </v-icon>
                                            <v-icon color="" v-else-if="item.reqType == 'wiki'">
                                                mdi-script-text-outline
                                            </v-icon>
                                        </v-list-item-avatar>
                                        <v-list-item-avatar size="30">
                                            <v-img v-if="item.profile" :src="'http://i3a101.p.ssafy.io/images/' + item.profile"></v-img>
                                            
                                        </v-list-item-avatar>
                                        <v-list-item-content style="margin-left:10px;">
                                            <v-list-item-title>
                                                <b>{{item.subTitle}}</b>&nbsp;      
                                            </v-list-item-title>                                               
                                            <v-list-item-subtitle >
                                                <span> {{item.nickname}} <span style="font-size:10px"> {{item.created | diffDate}} </span></span>
                                            </v-list-item-subtitle>                                                
                                        </v-list-item-content>
                                        
                                        <v-list-item-action>
                                            <v-icon color="green" v-if="item.mainId">
                                                mdi-check-circle-outline
                                            </v-icon>
                                            <!-- <v-icon color="red" v-else-if="item.acceptYn=='N'">
                                                mdi-close-circle-outline
                                            </v-icon> -->
                                        </v-list-item-action>
                                    </v-list-item>                                    
                                </template>
                            </v-list>    
                    </v-flex>                    
                    <v-flex v-show="option && curItem.reqType"  md4 lg4 xl4> 
                        <v-list style="position: sticky; top:60px;" dense>
                            <div id="pos" style="height:0px" />
                            <div style="
                                    width:100%; 
                                    overflow-y:auto;
                                    padding:30px 50px;
                                    padding-right:10px;
                                " 
                                :style="{'height' : height + 'px'}"
                                v-if="curItem"
                            >           
                                <div v-if="curItem.reqType == 'video'">
                                    {{curItem.subTitle}} <br>
                                    <v-avatar size=20>
                                        <v-img v-if="curItem.profile" :src="'http://i3a101.p.ssafy.io/images/' + curItem.profile"></v-img>
                                    </v-avatar> 
                                    <span> {{curItem.nickname}} {{curItem.created | diffDate}} </span>
                                </div>
                                <div v-show="curItem.reqType != 'video'">                                 
                                    {{curItem.subTitle}}<br>
                                    <v-avatar size=20>
                                        <v-img v-if="curItem.profile" :src="'http://i3a101.p.ssafy.io/images/' + curItem.profile"></v-img>
                                    </v-avatar> 
                                    <span> {{curItem.nickname}} {{curItem.created | diffDate}} </span><p />
                                    <v-list-item-content>
                                    <div id="oneResult">
                                        <span class="diff"> </span>                                        
                                    </div>
                                    </v-list-item-content>
                                </div>
                                <div  style="
                                    width:100%; display: -webkit-box;
                                    display: flex;
                                    -webkit-box-pack: center;
                                    justify-content: center;
                                    -webkit-box-align: center;
                                    align-items: center;
                                    margin:30px 0px;
                                ">          
                                        <div id="videoFrame4" v-show="curItem.reqType=='video'">                                            
                                            <!-- <video
                                                class="video-js vjs-default-skin vjs-big-play-centered"
                                                controls                      
                                                data-setup='{}'
                                                style="position: relative; height: 0; overflow: hidden; width: 100%; height: auto;"                       
                                                :poster="`${curItem.thumbnailUrl} ? http://i3a101.p.ssafy.io/images/${curItem.thumbnailUrl} : ''`"
                                            >
                                                <source :src="`${curItem.playerUrl} ? http://i3a101.p.ssafy.io/images/${curItem.playerUrl} : ''`"> </source>
                                            </video> -->
                                        </div>
                                </div>     
                                <!-- <v-btn color="primary" block depressed style="margin:10px 0px;" @click="requestProcess(curItem,'Y')">적용하기</v-btn>   -->
                                <!-- <v-btn color="primary" block depressed style="margin:10px 0px;" @click="requestProcess(curItem,'N')">거절</v-btn>   -->
                                <v-btn color="primary" block depressed style="margin:10px 0px;" @click="initPreview(curItem)">상세보기</v-btn>  
                                <v-dialog v-model="preview" hide-overlay max-width="768"> 
                                    <div id="zz" />
                            
                                    <div id="videoFrame3" v-show="curItem.reqType=='video'">
                                        <!-- <video
                                            class="video-js vjs-default-skin vjs-big-play-centered"
                                            controls                      
                                            data-setup='{}'
                                            style="position: relative; height: 0; overflow: hidden; width: 768px; height: auto; max-height:500px"                       
                                            :poster="`${curItem.thumbnailUrl} ? http://i3a101.p.ssafy.io/images/${curItem.thumbnailUrl} : ''`"
                                        >
                                            <source :src="`${curItem.playerUrl} ? http://i3a101.p.ssafy.io/images/${curItem.playerUrl} : ''`"> </source>
                                        </video> -->
                                    </div>
                                    <div class="wiki-paragraph" v-show="curItem.reqType=='wiki'" style="background-color: #ffffff; padding:10px;">
                                        <div v-if="curItem.wikiContentHtml" v-html="parse(curItem.wikiContentHtml)" style="min-height:300px"/>
                                        <div v-else>
                                            <v-container fluid style="width:100%;">         
                                                <v-row>
                                                    <v-col cols="12">
                                                        <v-row
                                                            align="start"
                                                            justify="center"
                                                        >                                    
                                                            <v-icon style="font-size:120px; color:rgba(0, 0, 0, 0.54)"> mdi-emoticon-cry-outline </v-icon>                                
                                                        </v-row>
                                                    </v-col>
                                                </v-row>
                                                <v-row>
                                                    <v-col cols="12">
                                                        <v-row
                                                            align="end"
                                                            justify="center"
                                                        >       
                                                            <div style="font-size:20px"> 위키 문서가 비어있습니다 :( </div>    
                                                        </v-row>
                                                    </v-col>
                                                </v-row>
                                            </v-container>
                                        </div>
                                    </div>
                                </v-dialog>
                            </div>
                        </v-list>
                    </v-flex>
            </v-layout>
        </v-container>
    </div>
</template>

<script
    src="https://code.jquery.com/jquery-3.5.1.min.js"
    integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
    crossorigin="anonymous"></script>
<script>
    import http from "@/util/http_common.js"
    import axios from "axios"
    import store from "@/store/index.js"
    import parse from "@/lib/markdown/ParseMd.js";

    export default {
        //props: ['option'],
        watch: {
            curItem() {
                if(this.curItem.reqType == 'wiki') {               
                    $("#oneResult").prettyTextDiff({
                        originalContent: this.curItem.indexWikiContentHtml ? this.curItem.indexWikiContentHtml : ' ',
                        changedContent: this.curItem.wikiContentHtml ? this.curItem.wikiContentHtml : ' ',
                        diffContainer: ".diff"                    
                    });
                    
                    $('#videoFrame3').html(' ')
                    $('#videoFrame4').html(' ')
                }else{
                }
            },
            preview() {

            }
        },
        data() {
            return {
                items: [], 
                level: this.$route.query.level, 
                page: 1, 
                itemsperpage: 20, 
                loading: false,
                curItem: {

                },
                option:true,
                prevCreated: '',
                height:500,
                preview: false,
                offset:0,
                }
                
        },
        filters: {
            idOfEmail(val) {
                let id = val.substring(0,val.indexOf('@'))
                return '@' + (id ? id : val)
            },
            diffDate(val) {
                let diff = (new Date() - new Date(val)) / 1000;
                if(diff < 60)
                    return '방금 전'
                diff /= 60;
                if(diff < 60)
                    return parseInt(diff) + '분 전'

                diff /= 60;
                if(diff < 24)
                    return parseInt(diff) + '시간 전'

                diff /= 24;
                if(diff < 7)
                    return parseInt(diff) + '일 전'
                if (diff < 30)
                    return parseInt(diff/7) + '주 전'
                if (diff < 365)
                    return parseInt(diff/30) + '달 전'
                return parseInt(diff/365) + '년 전'
                return val
            }
        },
        created(){
            if(this.$router.app.$store.state.token){
                this.initRequestList();
            }else{
                eventBus.$emit('doLogin');
            }
        },
        mounted() {
            this.handleRequestResize()
            window.addEventListener('resize', this.handleRequestResize);
            document.addEventListener('scroll', this.requestScroll);
        },
        beforeDestroy() {
            window.removeEventListener('resize', this.handleRequestResize);
            document.removeEventListener('scroll', this.requestScroll);
        },
        methods: {
                
            parse,
            initRequestList() {
                this.$router.app.$store.commit('startLoading')
                http.axios.get(`/api/v1/myReqList?page=${this.page}&itemsperpage=${this.itemsperpage}`)
                    .then(({data}) => {
                        for(let i in data.result){
                            if(this.prevCreated != this.$moment(data.result[i].created).format('DD MMMM, YYYY')){
                                this.prevCreated = this.$moment(data.result[i].created).format('DD MMMM, YYYY');
                                this.items.push(this.prevCreated);
                            }
                                
                            this.items.push(data.result[i]);
                        }
                        if(data.result.length) {
                            this.page++;
                        }
                    }).finally(() => {
                        this.$router.app.$store.commit('endLoading')
                    })
            },
        initPreview(item) {
            this.preview=true;            
            if(item.reqType == 'video') {
                setTimeout(() => {
                    $('#videoFrame3').html(
                        `
                            <video
                                class="video-js vjs-default-skin vjs-big-play-centered"
                                controls                      
                                data-setup='{}'
                                style="position: relative; height: 0; overflow: hidden; width: 100%; height: auto; max-height:500px;"   
                                poster="http://i3a101.p.ssafy.io/images/${item.thumbnailUrl}"
                            >
                                <source src="http://i3a101.p.ssafy.io/images/${item.playerUrl}"> </source>
                            </video>
                        `
                    )
                }, 100)
            }
        },
        initDetail(item) {
            if(item.reqType == 'video') {
                $('#videoFrame4').html(                `
                        <video
                            class="video-js vjs-default-skin vjs-big-play-centered"
                            controls                      
                            data-setup='{}'
                            style="position: relative; height: 0; overflow: hidden; width: 100%; height: auto;"   
                            poster="http://i3a101.p.ssafy.io/images/${item.thumbnailUrl}"
                        >
                            <source src="http://i3a101.p.ssafy.io/images/${item.playerUrl}"> </source>
                        </video>
                    `
                )
            }
        },
            /*requestProcess(item, type){
                http.axios.put(`/api/v1/lectures/historys?subId=${item.subId}&subHisId=${item.subHisId}&type=${type}&reqType=${item.reqType}`).then(({data}) => {
                    console.dir(data)
                }).finally(() => {
                    this.items = []
                    this.page = 1;
                    this.initRequestList();
                })
            },*/
            requestScroll() {
                if($(document).scrollTop() < this.offset){                
                    this.offset = $(document).scrollTop();
                    return;
                }
                this.offset = $(document).scrollTop();

                if($(document).scrollTop() + $(document)[0].scrollingElement.clientHeight + 10 >= $(document).height()){
                    if(!this.$router.app.$store.state.loading)
                        this.initRequestList();
                }          
            
            },
            handleRequestResize() {            
                this.height = $('body').height() - 260;
            },
            move(url) {
                this
                    .$router
                    .push(url)
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

    .v-text-field{        
      font-size:16px;
      width: 120px;
      height:40px;
      padding:0px auto;
    }

    b{
        font-size:13px;
    }

    span{
        font-size:11px;
    }
    
    .day_header{
        padding: 5px 10px;
        background-color: #fafafa;
        font-size: 14px;
    }

    .day{ 
        margin: 0;
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen-Sans, Ubuntu, Cantarell, "Helvetica Neue", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
        font-size: 14px;
        font-weight: 400;

        line-height: 1.5;
        color: #212529;
        text-align: left;
    }
</style>