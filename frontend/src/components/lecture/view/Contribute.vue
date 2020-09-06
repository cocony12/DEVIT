<template>
    <v-dialog v-model="dialog" max-width="768px" persistent :dark="darkOption">       
        <v-tabs v-model="tabs" icons-and-text grow hide-slider color="success" :dark="darkOption">
            <v-tab>
                <span>영상</span>
                <v-icon>mdi-movie</v-icon>                
            </v-tab>
            <v-tab>
                <span>위키</span>
                <v-icon>mdi-wikipedia</v-icon>
            </v-tab>
            <v-tab-item> 
                <v-stepper v-model="step" vertical non-linear :dark="darkOption" style="border:none;">
                    <v-stepper-step :complete="playerUrl != ''" step="1">
                        영상 업로드
                    </v-stepper-step>
                
                    <v-stepper-content step="1">
                        <div style="width:100%; height:25em; border:2px dashed gray; cursor:pointer;" @click="clickVideo($event)" id="video">                            
                            <input type="file"
                                v-show="false"				                                          
                                accept="video/mp4,video/x-m4v,video/*"
                                @change="changeVideo"
                                ref="file" 
                                id="file"
                            />
                            <div v-show="playerUrl == ''" style="
                                width:100%;height:100%; display: -webkit-box;
                                display: flex;
                                -webkit-box-pack: center;
                                justify-content: center;
                                -webkit-box-align: center;
                                align-items: center;
                            ">                                            
                                <v-icon style="font-size:40px;" color='success'> mdi-movie-open</v-icon>
                                <div>                                        
                                    <div class="success--text" sytle="display:block;font-size:14px;">비디오 업로드</div>
                                    <div style="font-size:14px;" :style="{'color' : (darkOption ? '#d4d4d4' : '')}">클릭해서 비디오를 선택하세요.</div>
                                </div>
                            </div>                            
                            <video
                                ref="video"
                                id="livestation-player"
                                class="video-js vjs-default-skin vjs-big-play-centered"
                                controls
                                preload="auto"
                                data-setup='{}'
                                style="width:100%;min-height:500px;"   
                                v-show="playerUrl != ''"
                            >
                                <source :src="`http://i3a101.p.ssafy.io/images/${playerUrl}`"></source>
                            </video>   
                        </div>
                    </v-stepper-content>
                
                    <v-stepper-step :complete="thumbnailUrl != ''" step="2">썸네일 업로드</v-stepper-step>
                        
                    <v-stepper-content step="2">
                        <div style="width:100%; height:25em; cursor:pointer;" :style="{'border' : (!thumbnailUrl ? '2px dashed gray': '' )}" @click="clickImage($event)" id="image">                            
                            <input type="file"
                                v-show="false"				                                          
                                accept="image/png, image/jpeg, image/bmp"
                                @change="changeImage"
                                ref="file2" 
                                id="file2"
                            />
                            <div v-if="!thumbnailUrl" style="
                                width:100%;height:100%; display: -webkit-box;
                                display: flex;
                                -webkit-box-pack: center;
                                justify-content: center;
                                -webkit-box-align: center;
                                align-items: center;
                            ">                                            
                                <v-icon style="font-size:40px;" color='primary'> mdi-image</v-icon>
                                <div>                                        
                                    <div class="primary--text" sytle="display:block;font-size:14px;">이미지 업로드</div>
                                    <div style="font-size:14px;" :style="{'color' : (darkOption ? '#d4d4d4' : '')}">클릭해서 썸네일을 선택하세요.</div>
                                </div>
                            </div>
                            <v-img v-else :src="`http://i3a101.p.ssafy.io/images/${thumbnailUrl}`" min-height="100%" min-width="100%" aspect-ratio="1.7"/>
                        </div>
                    </v-stepper-content>
                    <v-stepper-step :complete="title != ''" step="3">제목 정하기</v-stepper-step>
                        
                    <v-stepper-content step="3">
                        <v-text-field outlined dense placeholder="제목을 정해보세요!" v-model="title" color="success">
                        </v-text-field>
                    </v-stepper-content>
                </v-stepper>
            </v-tab-item>       
            <v-tab-item :dark="darkOption" >            
                <v-card tile flat :dark="darkOption" style="border:none;">
                    <v-list :dark="darkOption" >
                        <v-list-item>
                            <v-list-item-content>
                                <editor v-model="content" :height="550"> </editor>
                                <!-- <v-textarea outlined auto-grow placeholder="텍스트 에디터 적용 예정입니다." :rows="10" v-model="content" /> -->
                            </v-list-item-content>
                        </v-list-item>         
                                      
                    </v-list>
                    
                </v-card>
            </v-tab-item> 
        </v-tabs>
        <v-list>
        <v-list-item style="margin-top:15px;">        
            <v-list-item-content>
                <v-btn outlined color="error" max-width="80" @click="$emit('closeDialog')">
                    취소
                </v-btn>
            </v-list-item-content>
            <v-list-item-action>
                <v-btn outlined color="success" @click="tabs ? requestWiki() : requestVideo()">
                    요청하기
                </v-btn>
            </v-list-item-action>
            <v-list-item-action>
                <v-btn outlined color="success" @click="initPreview()">
                    미리보기
                </v-btn>
            </v-list-item-action>
        </v-list-item> 
        </v-list>
       
        <v-snackbar
            v-model="isUpload"
            timeout="-1"
            right            
            tile
            color="white"
            light
            height="20"
            style="border:1px solid #d4d4d4"
        
        >
            <v-list-item>
                <v-list-item-content>
                    <v-list-item-title><v-icon color="red">
                        mdi-movie
                    </v-icon>{{msg}}

                    </v-list-item-title>
                </v-list-item-content>
                <v-list-item-action>

                <v-progress-circular
                    :value="percentCompleted"
                    size="20"
                    width="3"
                    color="primary"
                    v-show="step == 1"
                />
                <v-icon color="success" v-show="step == 2">
                    mdi-check-circle
                </v-icon>
                </v-list-item-action>
            </v-list-item>
        </v-snackbar>
        <v-snackbar
            v-model="snackbar"
            right
            timeout="1500"
            color="primary"        
        >
            {{msg}}
        </v-snackbar>
        <v-dialog v-model="preview" hide-overlay max-width="768"> 
            <div id="videoFrame2" v-show="!tabs">
                <video 
                    class="video-js vjs-default-skin vjs-big-play-centered"
                    controls                      
                    data-setup='{}'
                    style="position: relative; height: 0; overflow: hidden; width: 768px; height: auto;"                       
                >
                    <!-- <source :src="`http://i3a101.p.ssafy.io/images/${playerUrl}`"> </source> -->
                </video>
            </div>
            <div class="wiki-paragraph" v-show="tabs" v-html="parse(content)" style="background-color: #ffffff; padding:10px;">
                
            </div>
        </v-dialog>
    </v-dialog>
</template>

<script>
import http from "@/util/http_common.js";
import axios from "axios";
import Editor from "@/components/common/Editor.vue"
import parse from "@/lib/markdown/ParseMd.js";
import convertHTML from "@/lib/markdown/ConvertHTML.js";

export default {
    components: {
        Editor,
    },
    props:['darkOption', 'dialog', 'lectureId', 'subId', 'wiki'],
    watch: {
        dialog() {
            if(this.dialog == false)
                this.$emit('closeDialog')
        },
        wiki() {
            this.content = this.wiki;
        }
    },
    data(){
        return {
            preview: false,

            tabs: 0,
            step: 1,

            playTime: '',
            playerUrl: '',
            thumbnailUrl: '',
            
            isUpload: false,
            percentCompleted: 0,

            title: '',
            content: '',

            snackbar: false,
            msg: '',
        }
    },
    methods: {
        parse,
        convertHTML,        
        initPreview() {
            this.preview=true;
            setTimeout(() => {
            $('#videoFrame2').html(
                `
                    <video
                        class="video-js vjs-default-skin vjs-big-play-centered"
                        controls                      
                        data-setup='{}'
                        style="position: relative; height: 0; overflow: hidden; width: 768px; height: auto;"   
                        poster="http://i3a101.p.ssafy.io/images/${this.thumbnailUrl}"
                    >
                        <source src="http://i3a101.p.ssafy.io/images/${this.playerUrl}"> </source>
                    </video>                    
                `            
            )}, 100);
        },
        clickVideo(){
            $("#file").click();
        },
        changeVideo(e){	
            var frm = new FormData();
            frm.append("file", document.getElementById("file").files[0]);

            var fileValue = this.$refs.file.value.split("\\");
            var fileName = fileValue[fileValue.length-1];
            this.msg = fileName;

            let config = {
                onUploadProgress: this.updatePercentCompleted
            };

            this.isUpload = true;
            axios.post('http://i3a101.p.ssafy.io:8080/api/v1/file/upload', frm, config, {
                headers: {
                    'accept': '*/*',
                    'X-AUTH-TOKEN': this.$router.app.$store.state.token,
                    'Content-Type': 'multipart/form-data'
                }
            }).then(({data}) => {
                var setPlayTime = this.setPlayTime;
                this.playerUrl = data.result;
                var myPlayer = videojs('livestation-player');  
                myPlayer.src({src: `http://i3a101.p.ssafy.io/images/${this.playerUrl}`});
                myPlayer.ready(function() {
                    var key = setInterval(() => {
                        if(myPlayer.duration()){
                            setPlayTime(myPlayer.duration())
                            clearInterval(key);
                        }else{
                        }
                    }, 100)
                });
                
            })
            .catch((error) => {
                console.dir(error)
            }).finally(()=>{
                setTimeout(this.exitUpload, 1000)
            })
            this.$refs.file.value = ''
        },

        clickImage(){
            $("#file2").click();
        },
        changeImage(e){	
            var frm = new FormData();
            frm.append("file", document.getElementById("file2").files[0]);

            axios.post('http://i3a101.p.ssafy.io:8080/api/v1/file/upload', frm, {
                headers: {
                    'accept': '*/*',
                    'X-AUTH-TOKEN': this.$router.app.$store.state.token,
                    'Content-Type': 'multipart/form-data'
                }
            }).then(({data}) => {
                this.thumbnailUrl = data.result;
                this.snackbar = true;
                this.msg = '이미지가 등록되었습니다.'
                this.step = 3;
            })
            .catch((error) => {
                console.dir(error)
            }).finally(()=>{
            })
            this.$refs.file2.value = ''
        },

        requestWiki(){
            http.axios.post('/api/v1/lectures/sub/history', {
                "lectureId": this.lectureId,
                "subId": this.subId,
                "reqType": "wiki",
                "wikiContent": this.convertHTML(this.parse(this.content)),
                "wikiContentHtml": this.content,
            }).then(({data}) => {
                this.$emit('closeDialog');
                this.snackbar = true;
                this.msg = '정상적으로 요청되었습니다.';
                this.$router.app.$store.commit('setChange', false);
                this.$emit('success');
            })
        },
        updatePercentCompleted(progressEvent){
            this.percentCompleted = Math.round( (progressEvent.loaded * 100) / progressEvent.total )
        },
        requestVideo() {
            if(this.playerUrl == '') {
                this.snackbar = true;
                this.msg = '동영상을 올려주세요!'
                this.step = 1;
            }else if(this.thumbnailUrl == '') {
                this.snackbar = true;
                this.msg = '썸네일을 올려주세요!'
                this.step = 2;
            }else if(this.title == ''){
                this.snackbar = true;
                this.msg = '제목을 입력해주세요!'
                this.step = 3;
            }else{
                http.axios.post('/api/v1/lectures/sub/history', {
                    "lectureId": this.lectureId,
                    "subId": this.subId,
                    "reqType": "video",
                    "playTime": this.playTime,
                    "playerUrl": this.playerUrl,
                    "thumbnailUrl": this.thumbnailUrl,
                    "title": this.title,
                }).then(({data}) => {
                    this.$emit('closeDialog');
                    this.snackbar = true;
                    this.msg = '정상적으로 요청되었습니다.';
                    this.$router.app.$store.commit('setChange', false);
                    this.$emit('success');
                })
            }
        },
        exitUpload(){
            this.step = 2;
            setTimeout(this.exitUploadDelay, 2000)
        },
        exitUploadDelay() {
            this.isUpload = false;
            this.percentCompleted = 0;
        },
        setPlayTime(duration){
            duration = parseInt(duration);
            this.playTime = parseInt(duration / 60) + ":" + (duration % 60);
        }
    }
}
</script>

<style scoped>
    .nomargin{
        margin:0px;
    }
    .v-stepper {
        border-radius: 0px;
        border-top:1px solid #d4d4d4;
        border-bottom:1px solid #d4d4d4;
        box-shadow: none;
    }
    .v-card {
        border-radius: 0px;
        border-top:1px solid #d4d4d4;
        border-bottom:1px solid #d4d4d4;
    }
</style>