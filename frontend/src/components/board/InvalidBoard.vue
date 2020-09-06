<template>
    <v-card tile flat style="margin-top:50px">
        <v-img src="@/assets/logo.png" style="width:200px; margin: 0px auto; cursor:pointer;" @click="move('/')"></v-img>
        <v-flex xs12 sm12 md9 lg6 xl4 style="margin:0 auto;">
        <v-card tile flat outlined style="width:100%; margin: 0px auto; margin-top:50px; padding:50px;">
            <div style="font-size:20px; text-align:center;">
                존재하지 않는 게시글입니다.
            </div>
            <v-btn color="normal" style="margin-top:50px; width:100%" depressed large @click="moveToList">
                목록으로
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
        moveToList(){
            var path = '/board?type='+this.$route.query.type;
            this.$router.push(path).catch(()=>{location.reload(true);});            
        },
    }
}
</script>

<style>

</style>