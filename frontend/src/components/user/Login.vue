<template>
    <div id="app" style="background-color:#f2f2f2; max-width:350px; z-index:99" >
    <v-container>
        <v-layout row class="text-xs-center">
        <v-flex class="grey lighten-4">
            <v-container class="text-xs-center">
            <v-img src="@/assets/logo.png" max-width="250px" style="margin:0px auto"></v-img>
            <!--<v-card flat>-->
                <v-form>
                    <v-text-field autofocus prepend-icon="mdi-account" name="Email" label="Email" v-model="email"></v-text-field>
                    <v-text-field prepend-icon="mdi-lock" name="Password" label="Password" type="password" v-model="password" @keydown.enter="login"></v-text-field>                    
                    <v-card-actions>
                    <v-btn primary large block color="primary" @click="login" style="margin-bottom:18px">Login</v-btn>
                    </v-card-actions>                
                    <v-checkbox label="자동 로그인" dense style="margin:0px" @change="autoLogin"></v-checkbox>
                </v-form>

                <div class="find_info">
                    <div class="add-option">
                        <div class="wrap" style="margin-bottom:10px;">
                            <p>다른 서비스로 로그인</p>
                            <a class="_kakao _serviceIcon" title="kakao" href="#" @mouseover="switchButton" @click="forKakaoLogin"></a>
                            <a class="_naver _serviceIcon" title="naver" href="#" @mouseover="switchButton" @click="forKakaoLogin"></a>
                            <a class="_github _serviceIcon" title="github" href="#" @mouseover="switchButton" @click="forKakaoLogin"></a>
                        </div>
                        <a href="#" @click="findPW">비밀번호 찾기</a> <span class="bar" aria-hidden="true">|</span> <a href="#" @click="moveJoin">회원가입</a>     
                    </div>            
                </div>

                <!-- hover underline 추가예정 -->
            <!--</v-card>-->
            <v-snackbar v-model="errorSnackbar" timeout="1500" color="error">
                {{errorMsg}}
            </v-snackbar>
            </v-container>
        </v-flex>
        </v-layout>
    </v-container>
    </div>
    
</template>

<script>
import store from "@/store/index.js"
import http from "@/util/http_common.js"
import eventBus from "@/lib/EventBus.js"
import KaKaoLogin from '@/lib/oauth/KaKaoLogin.js'

export default {
    data() {
        return {
            email: '',
            password: '',
            errorMsg: "",
            errorSnackbar: false,
        }
    },
    created(){
        eventBus.$on('socialLogin', (email, nickname, password) => {
            this.$router.app.$store.commit('startLoading');
            http.axios.post('/api/v1/account/login', {
                email: email,
                password: password
            }).then(({data}) => {  
                if(data.msg == 'fail') {
                    http.axios.post("/api/v1/account/signup", {
                        nickname: nickname,
                        password: password,
                        email: email
                    }).then(() => {
                        http.axios.post('/api/v1/account/login', {
                            email: email,
                            password: password
                        }).then(({data}) => {
                            if(data.msg=="fail"){
                                this.errorMsg = `${email} 계정이 이미 사용중입니다.`;
                                this.errorSnackbar = true;
                                return;
                            }
                            store.commit('login', {token: data.result.token, email: email, profile: data.result.profile})
                            this.$emit('closeDialog');
                            location.reload(true);
                        })
                    })
                }else if(data.msg == 'success'){
                    store.commit('login', {token: data.result.token, email: email, profile: data.result.profile})
                    this.$emit('closeDialog');                
                    location.reload(true);
                }
            }).catch((error) => {
                console.log(error)                
            }).finally(() => {
                this.$router.app.$store.commit('endLoading');
            })
        })
    },
    methods:{
        switchButton(e) {
            var kakaoIcon = $('._kakao');            
            var className = e.target.classList;
            for(let i=0; i < e.target.classList.length; i++)
                if(e.target.classList[i] != '_serviceIcon')
                    className = e.target.classList[i];
            
            var targetIcon = $(`.${className}`);
            if(kakaoIcon.selector != targetIcon.selector) {
                kakaoIcon.addClass(className);
                kakaoIcon.removeClass('_kakao');

                targetIcon.addClass('_kakao');
                targetIcon.removeClass(className);
            }
        },
        forKakaoLogin(){            
            KaKaoLogin.loginWithKakao();
        },
        login(){
            this.$router.app.$store.commit('startLoading');
            http.axios.post("/api/v1/account/login", {
                email: this.email,
                password: this.password
            }).then(({data}) => {
                if(data.msg=="fail"){

                    this.errorMsg = data.result;
                    this.errorSnackbar = true;
                    return;
                }

                store.commit('login', {token: data.result.token, email: this.email, profile: data.result.profile})
                this.$emit('closeDialog');

                location.reload(true);
            }).finally(() => {
                this.$router.app.$store.commit('endLoading');
            })
        },
        autoLogin(e) {
            localStorage.setItem('autoLogin', e.toString())
        },
        moveJoin(){
            this.$router.push('/join')
            this.$emit('closeDialog');
        },
        findPW(){
            this.$router.push('/findpw')
            this.$emit('closeDialog');
        },
    }
}
</script>

<style scoped>
    a {
        font-weight: 300;
    }

    .find_info {
        font-size:14px;
        line-height:14px;
        padding-top:18px;
        text-align:center;
        color:#8e8e8e;
        border-top:1px solid #e4e4e5;
    }
    .bar{
        display: inline-block;
        width:1px;
        height:12px;
        text-indent: -999em;
        background: #e4e4e5;
        vertical-align: bottom;
    }
    .find_info .bar {
        margin: 0 3px;
    }

    ._kakao{
        background-color: #ffcd00;
        background-image: url(https://statics.goorm.io/images/social/logo/kakaoLogo.svg);
    }

    ._naver{
        background-color: #1dc800;
        background-image: url(https://statics.goorm.io/images/social/logo/naverLogo.svg);
    }
    ._github{
        background-color: white;
        background-image: url('/github.png');
        background-size: 40px
    }

     ._serviceIcon{
        background-repeat: no-repeat;
        background-position: center;
        width: 40px;
        height: 40px;
        border-radius: 3px;
        -webkit-box-shadow: 0 0 1px 0 rgba(0, 0, 0, 0.12), 0 1px 1px 0 rgba(0, 0, 0, 0.24);
        box-shadow: 0 0 1px 0 rgba(0, 0, 0, 0.12), 0 1px 1px 0 rgba(0, 0, 0, 0.24);
        display: -ms-inline-flexbox;
        display: inline-flex;
        -ms-flex-pack: center;
        justify-content: center;
        margin:10px;
     }
    .add-option{
        margin-bottom: 20px;
    }

</style>