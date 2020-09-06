<template>
    <div id="app">
        <v-container>
            <v-tabs v-model="tab" color="#1976d2" right>                
                <!-- <v-tab
                    v-for="i in tabs"
                    :key="i"
                    @click="changeTab(i)"
                > -->
                <v-tab v-for="(item,i) in items" :key="`${i}_tab`">
                    <!-- {{title[i-1]}} -->
                    <span>{{item.title}}</span>
                </v-tab>
                <!-- <v-tab-item
                    v-for="i in tabs"
                    :key="i+'item'"
                >
                    <board-component :boardtype="i"></board-component>
                </v-tab-item> -->
                <v-tab-item>
                    <Board :option='option' :tab='0' :curTab="tab"> </Board>
                </v-tab-item>
                <v-tab-item>
                    <Reply :option='option' :tab='1' :curTab="tab"> </Reply>
                </v-tab-item>
            </v-tabs>
        </v-container>
    </div>
</template>

<script>
    //import http from "@/util/http_common.js"
    import eventBus from "@/lib/EventBus.js"
    //mport BoardComponent from "@/components/board/board-component.vue"
    import store from "@/store/index.js"
    import Board from "@/components/mypage/Board.vue"
    import Reply from "@/components/mypage/Reply.vue"

    export default {
        components: {
        Board,
        Reply,
    },
    data() {
        return {          
            tabName: ['board','reply'],
            tab: 0,  
            option: false,
            items: [
                {
                    title: '내가 쓴 게시글',
                    icon: 'mdi-circle-outline',
                },
                {
                    title: '내가 쓴 댓글',
                    icon: 'mdi-circle-outline',
                },
            ]
        }
    },
    /*watch:{
        tab() {
            history.pushState('', '', `/lecture/management/${this.tabName[this.tab]}/${this.$route.params.id}`);
        }
    },*/
    created(){
        if(!store.state.token) {
            this.$router.push('/')
        }     
    },
    mounted() {
        //window.addEventListener('resize', this.handleResize)
        //this.option = this.$refs.main.clientWidth != this.$refs.left.clientWidth;
    },
    beforeDestroy(){
        //window.removeEventListener('resize', this.handleResize)
    },
    methods: {       
        /*handleResize() {
            this.option = this.$refs.main.clientWidth != this.$refs.left.clientWidth;
        },*/
    }
    }

</script>

<style></style>