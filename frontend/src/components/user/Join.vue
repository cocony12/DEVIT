<template>
    <v-card tile flat style="margin-top:30px">
        <v-img src="@/assets/logo.png" style="width:200px; margin: 0px auto"></v-img>
        <v-flex xs12 sm9 md6 lg4 xl2 style="margin:0 auto;">
        <v-card tile flat outlined style="width:100%; margin: 0px auto; margin-top:30px; padding:30px 50px;">
            <table width="100%">
                <tr width="100%">
                    <td width="100%">
                        <v-text-field
                            placeholder="닉네임"
                            prepend-icon="mdi-account"
                            v-model="nickname"
                            :success-messages="!nickerror ? nick_text : false"
                            :error-messages="nickerror ? nick_text : false"
                            @blur="vaildNickname"
                            @focus="nickfocus"
                        >
                        </v-text-field>
                    </td>
                </tr>
                <tr>
                    <td>
                        <v-text-field
                            placeholder="이메일"
                            prepend-icon="mdi-email"
                            v-model="email"

                            :success-messages="!emailerror ? email_text : false"
                            :error-messages="emailerror ? email_text : false"

                            @blur="vaildEmail"
                            @focus="emailfocus"
                        ></v-text-field>
                    </td>
                </tr>
                <tr>
                    <td>
                        <v-text-field
                            placeholder="비밀번호"
                            prepend-icon="mdi-lock"
                            v-model="password"

                            :success-messages="!pwerror ? pw_text : false"
                            :error-messages="pwerror ? pw_text : false"

                            type="password"
                            @keyup="vaildPassword(); validRePassword()"
                        ></v-text-field>
                    </td>
                </tr>
                <tr>
                    <td>
                        <v-text-field
                            placeholder="비밀번호 재확인"
                            prepend-icon="mdi-lock"
                            v-model="re_password"
                            :success-messages="!repwerror ? repw_text : false"
                            :error-messages="repwerror ? repw_text : false"
                            type="password"
                            @keyup="validRePassword()"  
                        ></v-text-field>
                    </td>
                </tr>
            </table>

            <v-btn color="primary" style="margin-top:50px; width:100%" depressed large @click="signup" outlined
                :disabled="nickerror || emailerror || pwerror || repwerror ||  !nick_text || !email_text ||  !pw_text || !repw_text"
            >
                다음
            </v-btn>
        </v-card >     
        </v-flex>   
        <v-snackbar v-model="errorSnackbar" timeout="1500" color="error">
            {{errorMsg}}
        </v-snackbar>
    </v-card>
</template>

<script>
import http from "@/util/http_common.js"
import eventBus from "@/lib/EventBus.js"

export default {
    data() {
        return {
            nickname: '',
            email: '',
            password: '',
            re_password: '',
            code: '',    
            check: false,
            nickerror : false,
            emailerror : false,
            pwerror : false,
            repwerror : false,
            errorMsg: "",
            errorSnackbar: false,
            // isNicknameDupl: false,
            // isEmailDupl: false,
            // nick_text: '닉네임 중복 체크를 해주세요',
            // email_text: '아메일 인증을 해주세요',
            nick_text: '',
            email_text: '',
            pw_text: '',
            repw_text: '',
        }
    },

    methods: {
        signup(){
            if(this.nickname == ''){

                this.errorMsg = '닉네임을 입력해주세요.';
                this.errorSnackbar = true;
                return;

            }else if(this.email == ''){

                this.errorMsg = '이메일을 입력해주세요.';
                this.errorSnackbar = true;
                return;

            }else if(this.password == ''){

                this.errorMsg = '비밀번호를 입력해주세요.';
                this.errorSnackbar = true;
                return;

            }else if(this.re_password == ''){

                this.errorMsg = '비밀번호 재확인을 입력해주세요.';
                this.errorSnackbar = true;
                return;

            }
            /*http.axios.post("/api/v1/account/signup", {
                nickname: this.nickname,
                email: this.email,
                password: this.password,
            }).then(({data}) => {
                data;
                alert("회원가입이 완료되었습니다.");
                this.$router.push("/")
            }).catch((error) => {
                console.dir(error)
            })*/
            this.$router.app.$store.commit('startLoading')
            http.axios.post("/api/v1/mail/confirm/auth", {
                nickname: this.nickname,
                email: this.email,
                password: this.password,
            }).then(({data}) => {
                //data;
                //alert("회원가입이 완료되었습니다.");
                this.$router.push("/user/emailcertify")
            }).catch((error) => {
                console.dir(error)
                this.errorMsg = '메일 발송에 실패했습니다. 이메일을 확인해 주세요.';
                this.errorSnackbar = true;
            }).finally(() => {
                this.$router.app.$store.commit('endLoading')
            })
        },
        vaildPassword(){
            var str = this.password;
            var pattern1 = /[0-9]/; // 숫자 
            var pattern2 = /[a-zA-Z]/; // 문자 
            var pattern3 = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자 
            if(!pattern1.test(str) || !pattern2.test(str) || !pattern3.test(str) || str.length < 8) { 
                this.pwerror = true;
                this.pw_text = "비밀번호는 8자리 이상 문자, 숫자, 특수문자로 구성하여야 합니다."; 
                
            } else { 
                this.pwerror = false;
                this.pw_text = "사용 가능한 비밀번호입니다.";
            }

        },
        validRePassword(){
            if(this.re_password == '' ){
                this.repw_text = '';
                this.repwerror = false;
            }else if(this.password != this.re_password){
                
                this.repw_text = '비밀번호가 일치하지 않습니다.';
                this.repwerror = true;
            }else if(this.password == this.re_password){

                this.repw_text = '비밀번호가 일치합니다.';
                this.repwerror = false;
            }
        },
        vaildNickname(){
            if(this.nickname.length > 20){
                this.nickerror = true;
                this.nick_text = "20자 이하로 입력해주세요.";
                return;
            }

            http.axios.get(`/api/v1/users/user/${this.nickname}`)
            .then(({data}) => {
                if(data.msg == "success"){
                    this.nick_text = data.result;
                    this.nickerror = false;
                }else if(data.msg == "duplicate"){
                    //this.nick_text = data.result;
                    this.nick_text = "이미 사용중인 닉네임입니다.";
                    this.nickerror = true;
                }
            }).catch((error) => {
                this.nick_text = "사용할 수 없는 닉네임 입니다.";
                this.nickerror = true;
            })
        },
        vaildEmail(){
            const pattern = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            if(!pattern.test(this.email)){
                this.emailerror = true;
                this.email_text = "이메일 형식이 맞지 않습니다.";
                return;
            }
            this.email_text = '사용가능한 이메일입니다.'
            this.emailerror = false;
            http.axios.get(`/api/v1/users/email/${this.email}`)
            .then(({data}) => {
                this.email_text = data.result;
                if(data.msg == "success"){
                    this.emailerror = false;
                }else if(data.msg == "duplicate") {
                    this.emailerror = true;
                }
            }).catch((error) => {
                this.email_text = "통신에 문제가 생겼습니다.";
                this.emailerror = true;
            })
        },
        nickfocus(){
            this.nick_text = '';
        },
        emailfocus(){
            this.email_text = '';
        }
    }
}
</script>

<style scoped>
</style>