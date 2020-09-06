<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" scrollable max-width="400px" overlay-color="white" overlay-opacity="0.1" persistent no-click-animation style="box-shadow: none">      
      <v-card tile outlined >
            <div style="position:absolute; top:5px; right:8px; cursor:pointer;" @click="$emit('closeTempBoard')">              
                <v-icon size="20">mdi-window-close</v-icon>
            </div>
            <div style="margin: 0px 20px; margin-top:20px;">
                
                <span style="font-size:20px; font-family: 'Nanum Gothic';">임시저장 목록</span>                
                <div >
                    <v-row>
                        <v-col :cols="9">
                            <span class="primary--text" style="font-size:0.7em">총 {{items.length}}개</span>
                        </v-col>
                        <v-col :cols="3">
                            <table style="border:1px solid #DDDDDD; font-size:0.7em; cursor:pointer;" @click="removeItemAll">
                                <div style="display:block;margin:3px 1px;">
                                    <v-icon left color="grey" size="15" style="margin-right:0px;">mdi-trash-can-outline</v-icon> <span style="font-color:'grey'; font-family: 'Nanum Gothic'; -ms-user-select: none; -moz-user-select: -moz-none; -webkit-user-select: none; -khtml-user-select: none; user-select:none;">전체 삭제</span>
                                </div>
                            </table>                    
                        </v-col>
                    </v-row>
                </div>              
          </div>
        <v-divider></v-divider>
        <v-card-text style="height: 400px; padding:0px;">            
            <div v-for="(item,idx) in items" :key="idx+'_tempboard'">
                <v-list two-line >
                    <v-list-item dense link @click="writeBoard(item);$emit('closeTempBoard');">
                        <v-list-item-content>
                            <v-list-item-title>{{item.boardTitle}}</v-list-item-title>
                            <v-list-item-subtitle>{{item.createDate | moment('YYYY-MM-DD HH:mm')}}</v-list-item-subtitle>
                        </v-list-item-content>
                        <v-list-item-action @click.stop="removeItem(idx)">
                            <v-btn icon>
                                <v-icon color="grey lighten-1">mdi-trash-can-outline</v-icon>
                            </v-btn>
                        </v-list-item-action>
                    </v-list-item>                
                </v-list>
                <v-divider></v-divider>            
            </div>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
  import store from "@/store/index.js"

  export default {
    props: ["dialog"],
    data () {
      return {
        items: [],
      }
    },
    watch:{
        dialog() {
            if(this.dialog){
                this.items = localStorage.getItem(store.state.email)
                if(this.items){
                    this.items = JSON.parse(this.items);
                    this.items.sort((a,b) => {                        
                        var dateA = a.createDate;
                        var dateB = b.createDate;
                        return dateA < dateB ? 1 : -1;
                    })
                    
                }else{
                    this.items = []                    
                }
            }       
        }
    },
    methods: {
        removeItem(i){
            this.items.splice(i,1);
            localStorage.setItem(store.state.email, JSON.stringify(this.items));
        },
        removeItemAll(){
            this.items = []
            localStorage.removeItem(store.state.email);
        },
        writeBoard(item){

            this.$router.push({'name': 'BoardEdit', params: {item}, query: {edittype:'temp'}})

        }
    }
  }
</script>

<style>
    .v-dialog {
        box-shadow: none;
    }
    @import url('https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700,800');
</style>    