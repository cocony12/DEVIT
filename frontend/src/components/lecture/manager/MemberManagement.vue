<template>
  <div style="; margin-top:20px;">
        <v-card tile flat >
                <v-layout wrap>
                    <v-flex xs12 sm12 md9 lg9 xl9 style="margin-left:0px;">                         
                            <v-list style="padding:20px 20px;">
                                <v-list outlined style="padding-top:0px;" v-show="isOwner">                                        
                                    <v-list-item style="background-color:#fbfbfb">
                                        <v-list-item-title>
                                            <v-list-item-title>멤버 초대</v-list-item-title> 
                                        </v-list-item-title>    
                                    </v-list-item>
                                    <v-divider />
                                    
                                    <div style="margin-top:30px" />
                                    <v-list-item>
                                        <v-list-item-content>
                                            <v-list-item-subtitle>
                                                초대할 멤버를 선택하세요.
                                            </v-list-item-subtitle>
                                            <v-list-item-subtitle>
                                            <v-combobox
                                                v-model="inviteMember"                                            
                                                :items="items"
                                                :search-input.sync="search"
                                                multiple
                                                outlined
                                                small-chips                                            
                                                no-filter
                                                auto-select-first
                                                hide-selected
                                                @keydown.enter.stop.capture="selectMember"
                                                counter=5 
                                                hint="초대할 멤버를 선택하세요."
                                                
                                                dense
                                            >                                            
                                                <template v-slot:selection="{ attrs, item, parent, selected }">
                                                    <v-chip
                                                            v-bind="attrs"
                                                            :color="`primary lighten-4`"
                                                            :input-value="selected"
                                                            label
                                                        >
                                                        <span style="color:black">
                                                            <v-avatar
                                                                class="profile"                                                    
                                                            >
                                                                <v-img v-if="item.profile" :src="'http://i3a101.p.ssafy.io/images/' + item.profile"></v-img>
                                                            </v-avatar>
                                                            {{item.text }}
                                                            <v-icon small right @click="parent.selectItem(item)">mdi-close</v-icon>
                                                        </span>
                                                    </v-chip>
                                                </template>
                                                <template v-slot:item="{ index, item }">                                                
                                                    <v-list-item-avatar>
                                                        <v-img v-if="item.profile" :src="'http://i3a101.p.ssafy.io/images/' + item.profile"></v-img>
                                                    </v-list-item-avatar>
                                                    <v-list-item-content>
                                                        <v-list-item-title v-html="item.text"></v-list-item-title>
                                                        <v-list-item-subtitle v-html="item.email"></v-list-item-subtitle>
                                                    </v-list-item-content>
                                                </template>
                                            </v-combobox>
                                            </v-list-item-subtitle>
                                        </v-list-item-content>
                                    </v-list-item>
                                    <v-list-item>
                                        <v-list-item-content>
                                            <v-list-item-subtitle>
                                                초대할 멤버의 권한을 부여해주세요.
                                            </v-list-item-subtitle>
                                            <v-list-item-subtitle>
                                            <v-combobox      
                                                outlined
                                                dense
                                                v-model="selectPermission"
                                                :items="permissions"
                                                readonly
                                            >                 
                                            </v-combobox>
                                            </v-list-item-subtitle>
                                        </v-list-item-content>
                                    </v-list-item>
                                    <v-list-item>
                                        <v-spacer />
                                        <v-btn outlined color="primary" @click="invite()">
                                            초대하기
                                        </v-btn>    
                                    </v-list-item>   
                                </v-list>

                                <div style="margin-top:30px" />
                                <v-list outlined style="padding:0px;">                                        
                                    <v-list-item style="background-color:#fbfbfb">
                                        <v-list-item-title>멤버 목록</v-list-item-title> 
                                    </v-list-item>
                                    <template v-for="(item,index) in memberList">
                                        <v-divider :key="`${index}_divider`"/>
                                        <v-list-item :key="`${index}_memberList`">
                                            <v-list-item-avatar>
                                                <v-img v-if="item.profile" :src="'http://i3a101.p.ssafy.io/images/' + item.profile"></v-img>
                                            </v-list-item-avatar>
                                            <v-list-item-content>
                                                <v-list-item-title>
                                                    <b>{{item.nickname}}</b>&nbsp;
                                                     <span class="v-list-item__subtitle">{{item.email | idOfEmail}}</span></v-list-item-title>
                                                <v-list-item-subtitle > {{item.created | diffDate}}</v-list-item-subtitle>
                                            </v-list-item-content>

                                            <v-list-item-action style="margin-right:5px">
                                                <table width="100%">
                                                    <tr width="100%">
                                                        <th width="100px">
                                                            {{item.role}}
                                                        </th>
                                                        <th width="40px;">
                                                                <v-icon style="font-weight:700;" color="error" v-show="!item.isMe && isOwner" @click="changeOwner(item)">
                                                                    mdi-crown
                                                                </v-icon>
                                                        </th>
                                                        <td width="30px;">
                                                                <v-icon color="error" v-show="!item.isMe && isOwner" @click="exitProject(item)">
                                                                    mdi-trash-can-outline
                                                                </v-icon>
                                                            
                                                                <v-icon color="red" v-show="item.isMe" @click="exitProject(item)">
                                                                    mdi-exit-to-app
                                                                </v-icon>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </v-list-item-action>  
                                        </v-list-item>
                                        
                                    </template>
                                </v-list>
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
                                        프로젝트를 함께 완성시켜나갈 멤버를 초대해보세요!
                                        초대된 멤버는 <span class="error--text">권한 부여</span>를 제외한
                                        <span class="primary--text">기본 정보 수정</span>과 <span class="primary--text">목차 관리 </span>, 그리고  
                                        <span class="primary--text">여러 참여자들의 요청을 프로젝트에 반영</span>시킬 수 있습니다.
                                        
                                    </div>                                
                                </v-list-item-content>                                    
                            </v-list-item>                            
                        </v-list>
                    </v-flex>
                </v-layout>
        </v-card>
        <v-snackbar
            v-model="snackbar"
            timeout="2000"
            color="primary"        
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
export default {
    props: ['option'],
    data() {
        return {         
            lectureId: 0,
            userId:0,   
            authId:0,

            items: [],
            inviteMember: [
            ],
            selectPermission: 'Maintainer',
            permissions: ['Maintainer'],
            search: null,
            isOwner: false,

            memberList: [],
            snackbar: false,
            msg: '',      
        }
    },
    watch: {
        inviteMember () {
            this.search = '';
            if( this.inviteMember.length > 5) {
                 this.inviteMember.pop();
            }
        },        
        search(){
            this.keywordSearch();
        },
    },
    created(){
        this.lectureId = this.$route.params.id;
        this.keywordSearch();        
        this.memberSearch();
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
        },
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
        selectMember(){
            this.search = ''
            if(this.items[0]){
                this.inviteMember.push(this.items[0])          
                this.inviteMember.pop()      
            }            
        },
        keywordSearch(){     
            http.axios.get(`/api/v1/users/${this.lectureId}?search=${this.search?this.search:""}`)
                .then(({data}) => {
                    this.items = data.result
                    for(let i in data.result){
                        this.items[i].text = this.items[i].nickname
                    }
                })
        },
        memberSearch(){
            http.axios.get(`/api/v1/lectures/auth/${this.lectureId}`)
                .then(({data}) => {
                    this.memberList = data.result;
                    for(let i in this.memberList){
                        this.memberList[i].isMe = false;
                        if(this.memberList[i].email == this.$router.app.$store.state.email){
                            this.memberList[i].isMe = true;
                            this.isOwner = this.memberList[i].role == 'owner' || this.memberList[i].role == 'Owner'
                            this.userId = this.memberList[i].userId
                            this.authId = this.memberList[i].authId
                        }
                    }
                })
        },        
        invite(){
            var request = []
            for(let i in this.inviteMember){
                request.push({
                    "authId":0,
                    "lectureId":this.lectureId,
                    "lectureRole": this.selectPermission,
                    "userId": this.inviteMember[i].userId,                      
                })
            }
            http.axios.put(`/api/v1/lectures/auth`, request).then(({data}) => {
            })

            this.inviteMember = []
            this.memberSearch()
        },
        exitProject(item){
            if(item.email == this.$router.app.$store.state.email && this.isOwner){
                this.snackbar = true;
                this.msg = 'Owner 권한을 위임한 후에 시도하시길 바랍니다.'
                return;
            }

            var exitMsg = '이 프로젝트 관리자 목록에서 나가시겠습니까?';
            if(this.isOwner){
                exitMsg = '이 멤버를 관리자 목록에서 삭제하시겠습니까?';
            }
            if(confirm(exitMsg)){
                http.axios.delete(`/api/v1/lectures/auth/${item.authId}`, {
                    "userId": item.userId,
                }).then(({data}) => {
                }).finally(() => {                
                    if(item.email == this.$router.app.$store.state.email)
                        this.$router.push('/lecture/detail/${this.lectureId}')
                    else
                        this.memberSearch()                
                })

            }

        },
        changeOwner(item){
            if(confirm('Owner 권한을 위임하시겠습니까?')){

                http.axios.put(`/api/v1/lectures/auth`, [
                    {
                        "authId":item.authId,
                        "lectureId":this.lectureId,
                        "lectureRole": "Owner",
                        "userId": item.userId,     
                    },
                    {
                        "authId":this.authId,
                        "lectureId":this.lectureId,
                        "lectureRole": "Maintainer",
                        "userId": this.userId   
                    }
                ]).then(({data}) => {
                }).catch((error) => {
                    console.dir(error)
                }).finally(() => {
                    this.memberSearch()
                })
            }


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
    th {
        font-family:-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen-Sans, Ubuntu, Cantarell, "Helvetica Neue", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
        font-size: 14px;
        font-weight: 500
    }
    .v-list-item__title{
        font-size:14px;
    }
    .v-list-item__subtitle{
        font-size:12px;
    }
    .v-text-field{
        margin-top:5px;
        font-size:12px;
        font-family:-apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen-Sans, Ubuntu, Cantarell, "Helvetica Neue", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
    }

     .v-input__control{
        min-height: 30px !important;
        }
</style>