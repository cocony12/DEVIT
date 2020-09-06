<template>
    <v-card tile flat style="margin-top:50px">
        <v-img src="@/assets/logo.png" style="width:200px; margin: 0px auto; cursor:pointer;" @click="move('/')"></v-img>
        <v-flex xs12 sm12 md9 lg6 xl4 style="margin:0 auto;">
        <v-card tile flat outlined style="width:100%; margin: 0px auto; margin-top:50px; padding:50px;">
            <div style="font-size:20px; text-align:center;">
                변경된 비밀번호를 이메일로 전송했습니다. 
                <br>
                전송된 이메일을 확인하여 새로운 비밀번호로 로그인해주세요.
            </div>
            <v-btn color="normal" style="margin-top:50px; width:100%" depressed large @click="move('/')">
                메인으로
            </v-btn>
        </v-card >   
        </v-flex>     
    </v-card>
</template>

<script>
import http from "@/util/http_common.js"

export default {
    data() {
        return {
        }
    },

    methods: {
        move(path){
            this.$router.push(path).catch(()=>{location.reload(true);});            
        },
    }
}
</script>

<style>

</style>