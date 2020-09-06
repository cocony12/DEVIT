<template>
    <v-card tile flat style="margin-top:50px">
        <!-- <v-img src="@/assets/logo.png" style="width:200px; margin: 0px auto; cursor:pointer;" @click="move('/')"></v-img> -->
        <v-flex xs12 sm12 md9 lg6 xl4 style="margin:0 auto;">
             <div style="width:100%; margin:30px auto 10px auto;">
                <span style="font-size:26px; font-weight:600; color:#1976d2 !important;">비밀번호 찾기</span>
            </div>
            <v-card tile flat outlined style="width:100%; margin: 0px auto; padding:50px;">
                <div style="font-size:20px; text-align:center; width:100%;">
                    <v-layout>
                        <v-spacer/>
                        <v-flex>
                            <v-text-field
                                placeholder="이메일을 입력해 주세요"
                                prepend-icon="mdi-email"
                                v-model="email"
                            ></v-text-field>
                        </v-flex>
                        <v-flex class="pt-5">
                            <v-btn color="primary" @click="sendPWFindEmail" small style="margin-left:10px; float:left;">
                                비밀번호 찾기
                            </v-btn>
                        </v-flex> 
                    </v-layout>
                <div style="margin-top:10px;">
                <span style="font-size:12px;">아직 회원이 아니세요?</span>
                <v-btn color="normal" style="margin-left:10px; font-size:11px;" small depressed @click="move('/join')">
                    회원가입하기
                </v-btn>
                </div>
                </div>
            </v-card >   
        </v-flex>     
        <v-snackbar v-model="errorSnackbar" timeout="1500" color="error">
            {{errorMsg}}
        </v-snackbar>
    </v-card>
</template>

<script>
import http from "@/util/http_common.js"

export default {
    data() {
        return {
            email:'',
            errorSnackbar:false,
            errorMsg:''
        }
    },

    methods: {
        move(path){
            this.$router.push(path).catch(()=>{location.reload(true);});            
        },
        sendPWFindEmail(){
            
            this.$router.app.$store.commit('startLoading')
            http.axios.post("/api/v1/mail/password", {
                email: this.email,
            }).then(({data}) => {
                if(data.msg == 'notexist'){
                    this.errorMsg = data.result;
                    this.errorSnackbar = true;
                }else{

                    this.move('/user/emailcertify');
                }
            }).catch((error) => {
                console.dir(error)
                this.errorMsg = '메일 발송에 실패했습니다. 이메일을 확인해 주세요.';
                this.errorSnackbar = true;
            }).finally(() => {
                this.$router.app.$store.commit('endLoading')
            })
        }
    }
}
</script>

<style>

</style>