<template>
    <div id="app">
        <v-container>
            <v-tabs v-model="tab" color="#1976d2" right>                
                <v-tab
                    v-for="i in tabs"
                    :key="i"
                    @click="changeTab(i)"
                >
                    {{title[i-1]}}
                </v-tab>
                <v-tab-item
                    v-for="i in tabs"
                    :key="i+'item'"
                >
                    <board-component :boardtype="i"></board-component>
                </v-tab-item>
                <!-- <v-tab :key="1" >공지사항</v-tab>
                <v-tab :key="2" @click="changeTab(2)">자유 게시판</v-tab>
                <v-tab :key="3" @click="changeTab(3)">질문 게시판</v-tab>
                <v-tab :key="4" @click="changeTab(4)">신고 게시판</v-tab> -->

                <!-- <v-tab-item>
                    <board-component :boardtype="1"></board-component>
                </v-tab-item>
                <v-tab-item>
                    <board-component :boardtype="2"></board-component>
                </v-tab-item>
                <v-tab-item>
                    <board-component :boardtype="3"></board-component>
                </v-tab-item>
                <v-tab-item>
                    <board-component :boardtype="4"></board-component>
                </v-tab-item> -->
            </v-tabs>
        </v-container>
    </div>
</template>

<script>
    //import http from "@/util/http_common.js"
    import eventBus from "@/lib/EventBus.js"
    import BoardComponent from "@/components/board/board-component.vue"

    export default {
        name: 'app',
        components: {
            BoardComponent
        },
        data() {
            return {
                tab: null,
                tabs: 4,
                title: ["공지사항", "자유 게시판", "질문 게시판", "신고 게시판"]
            }
        },
        mounted(){
            //alert(this.$route.query.type); 
            
            //this.tab = 3;
        },
        created() {
            var router = this.$router;
            var route = this.$route;
            this.tab = (Number)(this.$route.query.type)-1;
            eventBus.$on('showDetail', function (boardId) {
                //alert("Board showDetail "+boardId+", type = "+route.query.type);
                //router.push({ name:'BoardDetail', params:{ 'boardId' : boardId , 'boardType' : route.query.type}});
                router.push({ name:'BoardDetail', params:{ 'boardId' : boardId , 'boardType' : route.query.type}, query:{'boardId' : boardId}});
            });

            eventBus.$on('newBoard', function () {
                router.push({ name:'BoardEdit', params:{ 'edittype' : "new" , 'boardType' : route.query.type}});
                
            });
        },
        methods: {
            changeTab(type){
                history.pushState('', '', `/board?type=${type}`);
            }
        }
    }

</script>

<style></style>