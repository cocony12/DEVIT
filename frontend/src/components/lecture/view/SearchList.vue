<template>
  <div>
        <v-container>
            <div style="margin-top:10px;"></div>        
        <v-card tile flat>
                <v-layout wrap ref="main">
                    <v-flex xs12 sm12 md8 lg8 xl8 ref="left" style="margin-left:0px; padding:20px;">       
                        <v-list>
                            <h1 style="margin-left:10px;"><span class="primary--text">{{keyword.join(',')}}</span> 검색 결과</h1>
                            <div style="height:30px"> </div>
                            <div v-show="!type">
                                <h3 style="color:#3c569b;padding-bottom:13px;border-bottom: 3px solid #4a63a6;">위키 ({{totalWiki}}건) <span style="font-size:14px; color:#666666; float:right; height:30px; padding-top:10px;cursor:pointer;" @click="expand(1)">더보기</span></h3>
                                <v-container fluid v-for="(item, i) in wiki" :key="i + '_item1'">
                                    <v-row style="cursor:pointer;" @click="move(`/lecture/player/undefined/${item.lectureId}?order=${item.order}`)">
                                        <v-col :cols="4">
                                            <div style="margin: 0px auto;">
                                                <v-avatar
                                                    class="profile"
                                                    size="100%"
                                                    tile>
                                                    <v-img 
                                                        v-if="item.thumbnailUrl"
                                                        :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                                                        aspect-ratio="1.7"
                                                        :ref="'img'+i"
                                                        min-height="100"
                                                        min-width="170"
                                                        style="border-radius:5px;"
                                                    ></v-img>
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
                                <div style="margin-top:30px;" />
                                <h3 style="color:#3c569b;padding-bottom:13px;border-bottom: 3px solid #4a63a6;">프로젝트 ({{totalProject}}건) <span style="font-size:14px; color:#666666; float:right; height:30px; padding-top:10px;cursor:pointer;" @click="expand(2)">더보기</span></h3>
                                <v-container fluid v-for="(item, i) in project" :key="i + '_item2'">
                                    <v-row style="cursor:pointer;" @click="move(`/lecture/detail/${item.lectureId}`)">
                                        <v-col :cols="4">
                                            <div style="margin: 0px auto;">
                                                <v-avatar
                                                    class="profile"
                                                    size="100%"
                                                    tile>
                                                    <v-img 
                                                        v-if="item.thumbnailUrl"
                                                        :src="'http://i3a101.p.ssafy.io/images/' + item.thumbnailUrl"
                                                        aspect-ratio="1.7"
                                                        :ref="'img'+i"
                                                        min-height="100"
                                                        min-width="170"
                                                        style="border-radius:5px;"
                                                    ></v-img>
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
                                                        v-for="(tag,index) in item.tagName.split(',')"   
                                                        :key="i+'_'+index+'_tag2'"
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
                                    <v-divider :key="i + '_divider2'"></v-divider>
                                </v-container>
                            </div>
                            <search-detail-list v-if="type" :type="type"> </search-detail-list>
                        </v-list>
                    </v-flex>                    
                    <v-flex v-show="option" style="border-left:1px solid #e2e2e2" md4 lg4 xl4> 
                        <div style="position: sticky; top:130px; ; height:500px; overflow-y:auto;">
                            <v-container>
                                <span style="normal--text; margin-left:10px;"> 추천 키워드 </span>
                                <p/>
                                <v-chip                                            
                                    :color="`primary lighten-4`"                                            
                                    class="ma-1"
                                    v-for="(tag,i) in keyword"   
                                    :key="i+'_keyword3'"
                                    label
                                    @click.stop="removeKeyword(tag)"
                                    style="margin-left:10px;"
                                >  
                                    <span style="color:black">
                                        {{tag}}
                                    </span>
                                </v-chip>   
                                <div class="chips" v-for="(tag,i) in tags" :key="i+'_chips3'" @click="addKeyword(tag.tagName)">
                                    <span class="chips_title">
                                        {{tag.tagName}}
                                    </span>
                                    <span class="chips_number">
                                        {{tag.tagCount}}
                                    </span>
                                </div>                                
                            </v-container>
                        </div>
                    </v-flex>
                </v-layout>
        </v-card>
      </v-container>
  </div>
</template>

<script src="https://code.jquery.com/jquery-2.2.1.min.js"></script>
<script>
import http from "@/util/http_common.js"
import SearchDetailList from "@/components/lecture/view/SearchDetailList.vue"
export default {
    components :{
        SearchDetailList,
    },
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
            project:[],
            wiki:[],
            option: true,
            keyword: [],
            height: 0,
            page: 1,
            tags: [],
            scrollPos:0,
            type:0,

            totalProject: 0,
            totalWiki: 0,
        }
    },
    created(){
        this.keyword = this.$route.query.keyword;   
        if(typeof this.keyword == 'string') {
            this.keyword = [this.keyword]
        }
        this.type = this.$route.query.type;
        this.tagSearch();
        this.projectSearch();
        this.wikiSearch();
    },
    mounted() {
        setTimeout(this.handleResize, 100);
        window.addEventListener('resize', this.handleResize)
        this.option = this.$refs.main.clientWidth != this.$refs.left.clientWidth;
        if(this.$refs.img0){
                this.height = this.$refs.img0[0].$el.offsetHeight - this.$refs.content0[0].$el.offsetHeight-25
                if(this.height < 0)
                    this.height = 0;
            }
    },
    beforeDestroy(){
        document.removeEventListener('resize', this.handleResize);
    },
    methods: {
        handleResize() {            
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
        tagSearch() {
            http.axios.get('/api/v1/lectures/tags').then(({data}) => {
                this.tags = []
                for(let i in data.result)
                {
                    if(this.keyword.indexOf(data.result[i].tagName) == -1)
                        this.tags.push(data.result[i])
                }
            })
        },
        projectSearch(){
            let request = `/api/v1/commons/search/lectures?startPage=${this.page}`
            for(let i in this.keyword){
                request += `&searchText=${this.keyword[i]}`
            }
            http.axios.get(request).then(({data}) => {
                this.project = [];

                if(data.result[0]) {
                    this.project = data.result.slice(0,5);
                    this.totalProject = data.result[0].totalCount;
                }
            })
        },
        wikiSearch(){
            let request = `/api/v1/commons/search/indexs?startPage=${this.page}`
            for(let i in this.keyword){
                request += `&searchText=${this.keyword[i]}`
            }
            http.axios.get(request).then(({data}) => {
                this.wiki = [];
                if(data.result[0]) {
                    this.wiki = data.result.slice(0,5);
                    this.totalWiki = data.result[0].totalCount;
                }
            })
        },
        move(url){
            this.$router.push(url).catch(()=>{location.reload(true);});            
        },

        addKeyword(keyword) {
            this.$router.push(`${this.$route.fullPath}&keyword=${keyword}`)
        },
        removeKeyword(keyword) {
            if(this.keyword.length == 1)
                return;
            let request = this.$route.path + '?'
            for(let key in this.$route.query) {
                for(let i in this.$route.query[key]) {
                    if(this.$route.query[key][i] != keyword || key != 'keyword')
                        request += `&${key}=${this.$route.query[key][i]}`;
                }
            }
            this.$router.push(request)
        },
        clickTag(tag) {
            this.$router.push(`/search?keyword=${tag}`).catch(() => {location.reload(true)})
        },
        expand(type){
            this.$router.push(`${this.$route.fullPath}&type=${type}`)
        }
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
        border-radius: 3px;
        padding: 5px 0px;
        padding-left: 5px;
        margin:4px;
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
        border-top-right-radius: 3px;
        border-bottom-right-radius: 3px;
        padding: 5px;
    }

    div.chips:hover{
        opacity: 1.0;
        cursor:pointer
    }
</style>