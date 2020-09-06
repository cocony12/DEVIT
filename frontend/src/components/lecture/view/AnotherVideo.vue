<template>
    <v-list ref="another" class="LecturePlayer" style="width:100%; overflow-y:auto; font-size:14px" :dark="darkOption" :style="{'height': anotherHeight+'px'}">
        <div fluid v-for="(item, i) in items" :key="i + '_item'" style="width:100%">
            <v-row style="cursor:pointer;" @click="move(`/lecture/player/undefined/${item.lectureId}?order=${order}&subId=${item.subId}&subHisId=${item.subHisId}`)">
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
                            ></v-img>
                        </v-avatar>
                    </div>
                </v-col>
                <v-col :cols="8">
                    <v-row>
                        <v-card
                            tile
                            flat
                            :ref="'content'+i"
                        >
                            <v-list-item-title>
                                {{item.title}}
                            </v-list-item-title>
                            <v-list-item-subtitle>
                                조회수 {{item.viewCount | convertView}}&middot;<v-icon size="16" :color="item.userLikeYn ? 'pink' : 'gray'">mdi-heart</v-icon>{{item.likeCount | convertLike}}
                            </v-list-item-subtitle>                            
                        </v-card>
                    </v-row>
                    <v-row>
                        <span class="nickname" style="font-size:12px">{{item.nickname}}</span>
                    </v-row>
                </v-col>
            </v-row>
        </div>
        <div style="height:50px" />
    </v-list>
</template>

<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<script>
    import http from "@/util/http_common.js"
    export default {
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
        props: ['darkOption', 'lectureId', 'subId', 'order', 'tabs'],
        data() {
            return {                
                anotherHeight: 768,

                items:[],
                keyword: '',
                height: 20,
                loading: false,
                page: 1,
                tags: [],
                scrollPos:0,
            }
        },
        watch: {
            darkOption(){
                if(this.darkOption){
                    $('.LecturePlayer .v-list-item__title').css('color', '#d4d4d4')
                    $('.LecturePlayer .nickname').css('color', '#d4d4d4')
                }else{            
                    $('.LecturePlayer .v-list-item__title').css('color', '#000000DE')
                    $('.LecturePlayer .nickname').css('color', '#000000DE')
                }                
            },
            subId () {
                this.getList();
            },
            tabs() {
                this.getList();
            }

        },
        mounted() {
            if(this.darkOption){
                $('.LecturePlayer .v-list-item__title').css('color', '#d4d4d4')
                $('.LecturePlayer .nickname').css('color', '#d4d4d4')
            }else{            
                $('.LecturePlayer .v-list-item__title').css('color', '#000000DE')
                $('.LecturePlayer .nickname').css('color', '#000000DE')
            }
            this.handleResize2()
            window.addEventListener('resize', this.handleResize2);
        },
        created(){            
            this.getList();
        },
        beforeDestroy(){
            window.removeEventListener('resize', this.handleResize2);
        },
        methods: {    
            handleResize2() {
                this.anotherHeight = $('body').prop("clientHeight")-this.$refs.another.$el.offsetTop
            },   
            move(url){
                this.$router.push(url).then(() => {}).catch(()=>{location.reload(true);});
            },

            getList() {
                http.axios.get(`/api/v1/lectures/sub/history/${this.subId}`).then(({data}) => {
                    this.items = data.result;
                })
            }
        }
    }
</script>

<style scoped>
* {
    font-weight: 400;
}
</style>