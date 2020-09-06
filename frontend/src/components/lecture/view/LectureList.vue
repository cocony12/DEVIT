<template>
  <div style="margin:50px">
      
        <div v-if="items.length > 0">
    <v-layout row wrap>
      <v-flex v-for="(item,i) in items" :key="`4${i}`" xs12 sm6 md4 lg3 xl2 >        
            <v-card tile flat style="margin-left:10px; margin-top:20px;cursor:pointer;">
                <v-img 
                    v-if="item.thumbnailUrl"
                    :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                    lazy-src="@/assets/images/empty.png"
                    position="left center"
                    contain
                    aspect-ratio="1.77"
                    @click="move(`/lecture/detail/${item.lectureId}`)"
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
                
                    
                <!-- <v-card-actions> -->
                                    <v-list>
                                            <div @click="move(`/lecture/detail/${item.lectureId}`)">
                                            <v-list-item-title style="font-size: 1.17em; font-weight: bold;">
                                                {{item.title}}
                                            </v-list-item-title>
                                            <v-list-item-subtitle>
                                                조회수 {{item.viewCount | convertView}}&nbsp;<v-icon size="16" :color="item.userLikeYn ? 'pink' : 'gray'">mdi-heart</v-icon>{{item.likeCount | convertLike}}
                                            </v-list-item-subtitle>
                                            
                                            <v-list-item-subtitle>
                                                총 {{item.lectureCount}}강의
                                            </v-list-item-subtitle>
                                            </div>
                                            <v-list-item-subtitle>
                                                #
                                                <v-chip                                            
                                                    :color="`primary lighten-4`"                                            
                                                    class="ma-1"
                                                    v-for="(tag,index) in item.tagName ? item.tagName.split(',') : ''"   
                                                    :key="i+'_'+index+'_tag'"
                                                    small
                                                    label
                                                    @click="move(`/search?keyword=${tag}`)"
                                                >  
                                                    <span style="color:black">
                                                        {{tag}}
                                                    </span>
                                                </v-chip>                                                                                                   
                                            </v-list-item-subtitle>
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

                                    </v-list>   
                <!-- </v-card-actions> -->                
            </v-card>            
      </v-flex>
    </v-layout>
      </div>
        <div v-else>
            <v-container fluid style="width:100%;">         
                <v-row>
                    <v-col cols="12">
                        <v-row align="start" justify="center">                                    
                            <v-icon style="font-size:150px; color:rgba(0, 0, 0, 0.54); margin:30px 0 20px 0"> mdi-emoticon-cry-outline </v-icon>                               
                        </v-row>
                        <v-row align="start" justify="center">       
                            <div style="font-size:20px; margin-bottom:20px;"> 프로젝트가 존재하지 않습니다 :( </div>    
                        </v-row>
                    </v-col>
                </v-row>
            </v-container>
        </div>
  </div>
</template>

<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
<script>
import http from "@/util/http_common.js"

export default {
    data() {
        return {
            items: [

            ],
            level: this.$route.query.level,
            page: 1,
            offset: 0,
        }
    },
    filters: {
        convertView(num) {
            return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '회';
        },
        convertLike(num){
            return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        },
    },
    created(){        
        this.$router.app.$store.commit('startLoading')
        http.axios.get(`/api/v1/lectures?page=${this.page}&type=${this.level}`).then(({data}) => {
            this.page++;
            this.items = data.result;
        }).finally(() => {
            this.$router.app.$store.commit('endLoading')
        })

        document.addEventListener('scroll', this.handleScroll);
    },
    beforeDestroy(){
        document.removeEventListener('scroll', this.handleScroll);
    },
    methods: {
        handleScroll(){
            if($(document).scrollTop() + $(document)[0].scrollingElement.clientHeight + 10 >= $(document).height()){
                if(this.offset >= $(document).scrollTop()) {
                    this.offset = $(document).scrollTop();
                    return;
                }
                this.offset = $(document).scrollTop();
                if(!this.$router.app.$store.state.loading){
                    this.$router.app.$store.commit('startLoading', true)
                    http.axios.get(`/api/v1/lectures?page=${this.page}&type=${this.level}`)
                        .then(({data}) => {
                            this.page++;
                            
                            for(let i in data.result)
                                this.items.push(data.result[i]);
                        }).finally(() => {
                            this.$router.app.$store.commit('endLoading', false)
                        })
                }
            }          
        },
        move(url){            
            this.$router.push(url)
        }
    }
}
</script>

<style scoped>
    .v-card:hover {
        transform: translateY(-4px);
        box-shadow: 0 3px 8px 0 rgba(0,0,0,.08), 0 0 1px 0 rgba(0,0,0,.44);
        cursor:pointer;
    }

</style>