<template>
    <div>
        <h3 style="color:#3c569b;padding-bottom:13px;border-bottom: 3px solid #4a63a6;">{{type==1 ? '위키' : '프로젝트'}} ({{total}}건)</h3>
        <v-container fluid v-for="(item, i) in items" :key="i + '_item1'">
            <v-row style="cursor:pointer;" @click="move(type == 1 ? `/lecture/player/undefined/${item.lectureId}?order=${item.order}` : `/lecture/detail/${item.lectureId}`)">
                <v-col :cols="4">
                    <div style="margin: 0px auto;">
                        <v-avatar
                            class="profile"
                            size="100%"
                            tile
                        >
                            <v-img 
                                v-if="item.thumbnailUrl"
                                :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                                aspect-ratio="1.7"
                                :ref="'img'+i"
                                min-height="100"
                                min-width="170"
                                style="border-radius:5px;"
                            >

                                <template v-slot:placeholder>
                                    <v-row
                                    class="fill-height ma-0"
                                    align="center"
                                    justify="center"
                                    >
                                        <v-progress-circular indeterminate color="primary lighten-4"></v-progress-circular>
                                    </v-row>
                                </template>
                            
                            </v-img>
                        </v-avatar>
                    </div>
                </v-col>
                <v-col :cols="7">
                    <v-row>
                        <v-card
                            tile
                            flat
                            :ref="'content'+i"
                        >
                            <v-list-item-title>
                                <h3>{{item.title}}</h3>
                            </v-list-item-title>

                            <v-list-item-subtitle>
                                총 {{item.lectureCount}}강의 &middot; 조회수 {{item.viewCount | convertView}}&nbsp;<v-icon size="16" :color="item.userLikeYn ? 'pink' : 'gray'">mdi-heart</v-icon>{{item.likeCount | convertLike}}
                            </v-list-item-subtitle>
                            <v-list-item-subtitle>
                                #
                                <v-chip                                            
                                    :color="`primary lighten-4`"                                            
                                    class="ma-1"
                                    v-for="(tag,index) in item.tagName ? item.tagName.split(',') : ''"   
                                    :key="i+'_'+index+'_tag1'"
                                    small
                                    label
                                    @click.stop="clickTag(tag)"
                                >  
                                    <span style="color:black">
                                        {{tag}}
                                    </span>
                                </v-chip>                                                                                                   
                            </v-list-item-subtitle>
                        </v-card>  
                    </v-row>
                    <v-card :height="height" tile flat >
                                            
                    </v-card>
                    <v-row>  
                        <v-avatar
                            class="profile"
                            size=20
                        >
                            <v-img 
                                v-if="item.profile"
                                :src="'http://i3a101.p.ssafy.io/images/' + item.profile"
                            ></v-img>
                        </v-avatar>
                        <span style="margin-left:5px;font-size:12px">{{item.nickname}}</span>
                    </v-row>
                </v-col>
            </v-row>
            <v-divider :key="i + '_divider1'"></v-divider>
        </v-container>
    </div>
</template>

<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<script>
import http from "@/util/http_common.js"
import eventBus from "@/lib/EventBus.js"

export default {
    props:['type'],
    filters: {
        convertView(num) {
            if(num < 1000){
                return num + '회'
            }

            if(num >= 100000000){
                num /= 100000000;
                return parseFloat(num).toFixed(2) + '억회'
            }
            if(num >= 10000){
                num /= 10000;
                return parseFloat(num).toFixed(0) + '만회'
            }
            if(num >= 1000){
                num /= 1000;
                return parseFloat(num).toFixed(1) + '천회'
            }
        },
        convertLike(num){
            return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        }
    },
    data() {
        return {            
            items: [],
            total: 0,
            scrollPos: 0,
            height:20,
        }
    },
    created(){
        this.keyword = this.$route.query.keyword;   
        if(typeof this.keyword == 'string') {
            this.keyword = [this.keyword]
        }
        this.page=1;
        this.search(this.type);

        document.addEventListener('scroll', this.handleScroll2);
    },
    mounted() {
        setTimeout(this.handleResize2, 100);
        window.addEventListener('resize', this.handleResize2)
        if(this.$refs.img0){
                this.height = this.$refs.img0[0].$el.offsetHeight - this.$refs.content0[0].$el.offsetHeight-25
                if(this.height < 0)
                    this.height = 0;
            }
    },
    beforeDestroy(){
        document.removeEventListener('scroll', this.handleScroll2);
        document.removeEventListener('resize', this.handleResize2);
    },
    methods: {
        handleScroll2(e){
            if($(document).scrollTop() < this.scrollPos){                
                this.scrollPos = $(document).scrollTop();
                return;
            }
            this.scrollPos = $(document).scrollTop();

            if($(document).scrollTop() + $(document)[0].scrollingElement.clientHeight + 100 >= $(document).height()){
                if(!this.$router.app.$store.state.loading)
                    this.search(this.type);
            }          
        },
        handleResize2() {
            if(this.$refs.main && this.$refs.left)  
                this.option = this.$refs.main.clientWidth != this.$refs.left.clientWidth;
            if(this.$refs.img0[0]){
                this.height = this.$refs.img0[0].$el.offsetHeight - this.$refs.content0[0].$el.offsetHeight-25
                if(this.height < 0)
                    this.height = 0;
            }
        },    
        goto(target){
            this.$vuetify.goTo(target, {
                duration: 300,
                offset: 100,
                easing: 'easeInOutCubic'
            })
        },    
        search(type){
            this.$router.app.$store.commit('startLoading')
            let request = '';
            if(type == 1) {
                request = `/api/v1/commons/search/indexs?startPage=${this.page}`
            }else if(type == 2) {
                request = `/api/v1/commons/search/lectures?startPage=${this.page}`
            }
            
            for(let i in this.keyword){
                request += `&searchText=${this.keyword[i]}`
            }

            http.axios.get(request).then(({data}) => {
                if(data.result[0]) {
                    this.page++;
                    for(let i in data.result)
                        this.items.push(data.result[i])
                    this.total = data.result[0].totalCount;
                }else{
                    // console.dir
                }
            }).finally(() => {
                this.$router.app.$store.commit('endLoading')
            })
        },
        move(url){
            this.$router.push(url).catch(()=>{location.reload(true);});            
        },
        clickTag(tag) {
            this.$router.push(`/search?keyword=${tag}`).catch(() => {location.reload(true)})
        },
    }
}
</script>

<style scoped>
    * {
        font-weight: 400;
    }
    h1 {
        font-size: 24px;
        font-weight: 400;
        margin-bottom:20px;
    }
    div.content {
        font-size:16px;
        margin-top:20px;
    }

    div.chips{
        display: inline-block;
        background-color:#ededed; 
        border-radius: 5px;
        padding: 5px 0px;
        padding-left: 5px;
        margin:10px;
        font-size:12px;
        opacity: 0.6;
    }

    .chips_title{
        padding: 5px;
    }

    .chips_number{
        height:100%;
        width:100%;
        background-color:#cdcdcd;
        border-top-right-radius: 5px;
        border-bottom-right-radius: 5px;
        padding: 5px;
    }

    div.chips:hover{
        opacity: 1.0;
        cursor:pointer
    }
</style>