<template>
  <div style="; margin-top:20px;">
        <v-card tile flat outlined >
                <v-layout wrap ref="main">
                    <v-flex xs12 sm12 md9 lg9 xl9 ref="left" style="margin-left:0px;">                         
                            <v-list style="padding:20px 100px;">
                                <v-list-item>
                                    <v-list-item-title>프로젝트명</v-list-item-title> 
                                </v-list-item>
                                <v-list-item>
                                    <v-list-item-title>
                                        <v-text-field
                                            v-model="title"
                                            counter="50"
                                            ref="title"
                                            id="title"
                                            @change="changeForm(true)"
                                        ></v-text-field>
                                    </v-list-item-title>
                                </v-list-item>
                            </v-list> 
                            
                            <v-list style="padding:20px 100px;">
                                <v-list-item>
                                    <v-list-item-title>대표 이미지</v-list-item-title> 
                                </v-list-item>
                                <v-list-item>
                                    <v-list-item-title>
                                    <div v-show="!thumbnailUrl" style="width:100%; border:2px dashed gray; cursor:pointer; margin-top:15px; overflow-y:visible;" :style="{'height': height+'px'}" @click="clickImg($event)" id="image" >
                                        <input type="file"
                                            v-show="false"				                                            
                                            accept="image/png, image/jpeg, image/bmp"		
                                            @change="changeImg"
                                            ref="file" 
                                            id="file"
                                        />
                                        <div v-show="!thumbnailUrl" style="
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
                                                <div style="font-size:14px;">클릭해서 대표 이미지를 선택하세요.</div>
                                            </div>
                                        </div>                                                                                
                                    </div>
                                    <v-img v-if="thumbnailUrl" :src="`http://i3a101.p.ssafy.io/images/${thumbnailUrl}`" min-width="100%" aspect-ratio="1.77" contain @click="clickImg($event)" style="cursor:pointer; border:2px dashed gray;" />

                                    </v-list-item-title> 
                                </v-list-item>
                            </v-list>

                            <v-list style="padding:20px 100px;">
                                <v-list-item>
                                    <v-list-item-title>
                                        <v-radio-group v-model="category" id="category" @change="changeForm(true); changeCategory();">
                                            <template v-slot:label>
                                                <div style="color:rgba(0, 0, 0, 0.87)">카테고리</div>
                                            </template>
                                            <p style="height:20px"/>
                                            <v-radio :value="0">
                                                <template v-slot:label>
                                                    <div><span style="color:#1976D2">기술 스택</span> <br>
                                                    <span class="text-caption">사람들과 함께 강의의 완성도를 높여보세요.<div class=""></div></span></div>
                                                </template>
                                            </v-radio>
                                            <v-radio :value="1">
                                                <template v-slot:label>
                                                    <div><span style="color:#1976D2">스터디</span> <br>
                                                    <span class="text-caption">같은 목표를 가진 사람들과 함께 만들어보세요.</span></div>
                                                </template>
                                            </v-radio>
                                        </v-radio-group>
                                    </v-list-item-title> 
                                </v-list-item>
                            </v-list>
                            <v-expand-transition>
                                <v-list style="padding:20px 100px;" v-show="category >= 0">
                                    <v-list-item>
                                        <v-list-item-title>
                                            <v-radio-group v-model="type" id="type" @change="changeForm(true)">
                                                <template v-slot:label>
                                                    <div style="color:rgba(0, 0, 0, 0.87)">{{['레벨','기술'][category  ]}}</div>
                                                </template>
                                                <p style="height:20px"/>
                                                <div v-if="category == 0">
                                                    <v-radio :value="1">
                                                        <template v-slot:label>
                                                            <div><span style="color:#1976D2">초급</span> <br>
                                                            <span class="text-caption">별다른 사전 지식 없이도 볼 수 있습니다.</span></div>
                                                        </template>
                                                    </v-radio>
                                                    <v-radio :value="2">
                                                        <template v-slot:label>
                                                            <div><span style="color:#1976D2">중급</span> <br>
                                                            <span class="text-caption">관련 분야에 대한 기본 지식이 필요합니다.</span></div>
                                                        </template>
                                                    </v-radio>
                                                    <v-radio :value="3">
                                                        <template v-slot:label>
                                                            <div> <span style="color:#1976D2">고급</span> <br>
                                                            <span class="text-caption">관련 분야에 대한 충분한 이해가 필요합니다</span></div>
                                                        </template>
                                                    </v-radio>
                                                </div>
                                                <div v-else>
                                                    <v-radio :value="101">
                                                        <template v-slot:label>
                                                            <div><span style="color:#1976D2">프로그래밍</span> <br>
                                                            <span class="text-caption">프로그래밍 언어에 대한 지식을 공유해보세요.</span></div>
                                                        </template>
                                                    </v-radio>
                                                    <v-radio :value="102">
                                                        <template v-slot:label>
                                                            <div><span style="color:#1976D2">알고리즘</span> <br>
                                                            <span class="text-caption"> 대회, 코딩 테스트 등의 같은 목표를 가진 사람들과 지식을 공유해보세요.  </span></div>
                                                        </template>
                                                    </v-radio>
                                                    <v-radio :value="103">
                                                        <template v-slot:label>
                                                            <div> <span style="color:#1976D2">자격증</span> <br>
                                                            <span class="text-caption">IT자격증에 대한 지식을 공유해보세요.</span></div>
                                                        </template>
                                                    </v-radio>
                                                    <v-radio :value="104">
                                                        <template v-slot:label>
                                                            <div> <span style="color:#1976D2">기타</span> <br>
                                                            <span class="text-caption">다양한 주제에 대해 지식을 공유해보세요.</span></div>
                                                        </template>
                                                    </v-radio>
                                                </div>
                                            </v-radio-group>
                                        </v-list-item-title> 
                                    </v-list-item>
                                </v-list>
                            </v-expand-transition>

                            <v-list style="padding:20px 100px;">
                                <v-list-item>
                                    <v-list-item-title>
                                        검색 키워드    
                                    </v-list-item-title>                                    
                                </v-list-item>
                                <v-list-item>
                                    <v-list-item-title>
                                        <v-combobox 
                                            multiple 
                                            counter=10 
                                            hint="키워드 작성 후 엔터키를 눌러 구분할 수 있습니다." 
                                            prefix="#" 
                                            append-icon=""
                                            v-model="tags"
                                            id="tags"
                                            ref="tags"
                                            @change="changeForm(true)"
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
                                    </v-list-item-title>                                    
                                </v-list-item>
                            </v-list>      
                            <v-list style="padding:20px 100px;">
                                <v-layout>
                                    <v-spacer></v-spacer>
                                    <v-btn  color="primary" outlined @click="ValidationForm">
                                        저장하기
                                    </v-btn>    
                                    <div style="margin-right:5px"></div>
                                </v-layout>
                                    
                                
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
                                        프로젝트 명은 모두가 함께 만들어갈 하나의 주제를 정하는 것입니다.
                                        <span class="primary--text"> 모르는 사람도 보고 바로 이해할 수 있는 키워드</span>를 사용하세요!
                                    </div>                                
                                </v-list-item-content>                                    
                            </v-list-item>
                            
                            <v-list-item>                                       
                                <v-list-item-content style="font-size:14px">                                
                                    <div>
                                        대표 이미지는 다른 사람에게 가장 먼저 보여지는 시각 정보입니다.
                                        <span class="primary--text"> 신뢰감을 줄 수 있는 깔끔한 이미지 </span>를 사용하여 좋은 인상을 남겨보세요!
                                    </div>                                
                                </v-list-item-content>                                    
                            </v-list-item>
                            <v-list-item>                                       
                                <v-list-item-content style="font-size:14px">                                
                                    <div>
                                        적절한 검색 키워드를 통해 
                                        <span class="primary--text"> 해당 키워드에 관심이 있는 사람들이 접근하기 쉽게 </span> 해주세요! 
                                        최대 10개까지 지정할 수 있습니다.                                         
                                    </div>                                
                                </v-list-item-content>                                    
                            </v-list-item>
                        </v-list>
                    </v-flex>
                </v-layout>
        </v-card>
        <v-snackbar
            v-model="snackbar"
            right
            timeout="2000"
            :color="color"        
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
export default {
    props: ['option','tab','curTab'],
    watch: {
        tags(val,prev){
            if(val.length > 10)
                val.pop()            
        },
        curTab(){
            if(this.tab == this.curTab){
                this.getDefaultPage();
            }
        },
        /*category() {
            this.type = this.category * 100 + 1;
        }*/
    },
    data() {
        return {  
            commonId: 0,
            lectureId: 0,
            title: '',
            thumbnailUrl:'',
            type: 1,
            tags: [],

            snackbar: false,
            msg: '',          
            color: 'success',
            
            height: 300,

            category: -1,
        }
    },
    created(){
        this.lectureId = this.$route.params.id;
        this.getDefaultPage();
    },
    mounted(){
        this.height = $('#image').width()/1.77;
    },
    
    methods: {
        changeCategory(){
            this.type = this.category * 100 + 1;
        },
        changeForm(flag){
            this.$router.app.$store.commit('setChange', flag);
        },
        clickImg(){
            $("#file").click();
        },
        changeImg(e){	
            var frm = new FormData();
            frm.append("file", document.getElementById("file").files[0]);
            
            axios.post('http://i3a101.p.ssafy.io:8080/api/v1/file/upload', frm, {
                headers: {
                    'accept': '*/*',
                    'X-AUTH-TOKEN': store.state.token,
                    'Content-Type': 'multipart/form-data'
                }
            }).then(({data}) => {
                this.thumbnailUrl = data.result;
                this.changeForm(true);
            })
            .catch((error) => {
                console.dir(error)
            }).finally(()=>{

            })
            this.$refs.file.value = ''
        },
        goto(target, msg, color){
            this.$vuetify.goTo(target, {
                duration: 300,
                offset: 150,
                easing: 'easeInOutCubic'
            })
            this.msg = msg;
            this.snackbar = true;
            this.color = color;
        },
        ValidationForm(){
            if(!this.title){
                this.goto('#title', '프로젝트명을 입력해주세요.', 'error darken-1')
                this.$refs.title.focus();                
            }
            else if(!this.thumbnailUrl){
                this.goto('#image', '대표 이미지를 선택해주세요.', 'error darken-1')                
            }
            else if(!this.type){
                this.goto('#type', '레벨을 설정해주세요.', 'error darken-1')                                
            }else if(!this.tags.length){
                this.goto('#tags', '검색 키워드를 입력해주세요.', 'error darken-1')          
                this.$refs.tags.focus();      
            }else{
                this.saveDefaultPage();
            }
        },
        getDefaultPage(){
            http.axios.get(`/api/v1/lectures/${this.lectureId}`).then(({data}) => {
                if(data.msg == 'success') {
                    this.title = data.result.title;
                    this.thumbnailUrl = data.result.thumbnailUrl;                    
                    this.type = data.result.type;
                    if(data.result.tagName != null) {
                        this.tags = data.result.tagName.split(',')
                    }
                    if(this.type < 100 && this.type > 0){
                        this.category = 0;
                    }else if(this.type > 100){
                        this.category = 1;
                    }
                    this.commonId = data.result.commonId;
                }
            })
        },
        saveDefaultPage(){
            http.axios.put('/api/v1/lectures/foundation', {  
                "commonId": this.commonId,
                "lectureId": this.lectureId,
                "title": this.title,
                "tags": this.tags,
                "thumbnailUrl": this.thumbnailUrl,                
                "type": this.type
            }).then(({data}) => {
                this.snackbar = true;
                this.msg = '저장되었습니다.';
                this.color = "success"
                this.changeForm(false);
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