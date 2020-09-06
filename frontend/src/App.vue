<template>
  <v-app id="frame">
    <Header></Header>
    <Navigation ></Navigation>
    <Content></Content>
  </v-app>
</template>

<script>
import Header from '@/components/common/Header.vue';
import Navigation from '@/components/common/Navigation.vue';
import Content from '@/components/common/Content.vue';
export default {
  name: 'App',

  components: {
    Header,
    Navigation,
    Content,
  },

  data() {
    return {

    }
  },

  created() {
      document.addEventListener('beforeunload', () =>{
          alert('zz')
      })
  },
  
  methods: {
    
  }

};
</script>
<style>
    @import url(//fonts.googleapis.com/earlyaccess/notosanskr.css);
    * {
        font-family: "Noto Sans KR", sans-serif;
    }

    @media only screen and (-webkit-min-device-pixel-ratio: 0) and (min-device-width: 1025px) {
         ::-webkit-scrollbar {
            width: 6px;
            height: 6px;
        }
        ::-webkit-scrollbar-button:start:decrement,
        ::-webkit-scrollbar-button:end:increment {
            display: block;
            height: 0px;
        }


        ::-webkit-scrollbar-button:vertical:increment {
            /* background-color: #fff;
             */
             height:0px;
        } 

        ::-webkit-scrollbar-track-piece {
            background-color: transparent;
            -webkit-border-radius: 3px;
        }

        ::-webkit-scrollbar-thumb:vertical {
            height: 50px;
            background-color:rgb(167, 164, 164);
            opacity: 0.5;
            
             /* background-image: linear-gradient(-45deg, rgba(0, 160, 255, 0.86), rgb(0, 72, 162)); */
        }

        ::-webkit-scrollbar-thumb:horizontal {
            width: 50px;
            background-color:transparent;
            -webkit-border-radius: 3px;
        } 
    }
    .v-application p{
        margin: 0px;
    } 

    blockquote {
        color: #7f8fa4;
        font-size: inherit;
        padding: 8px 24px;
        border-left: 3px solid #eaeaea;
    }

    hr {
        border-top: 1px solid #e1e1e1;
    }

    pre {
        font-family: "Menlo", "DejaVu Sans Mono", "Liberation Mono", "Consolas", "Ubuntu Mono", "Courier New", "andale mono", "lucida console", monospace;
        display: block;
        padding: 5px 12px 5px 12px;
        margin: 8px 0px ;
        font-size: 13px;
        word-break: break-all;
        word-wrap: break-word;
        color: #2e2e2e;
        background-color: #FAFAFA;
        border: 1px solid #e5e5e5;
        border-radius: 2px;
    }

    a {
        text-decoration:none
    }

    img {
        max-width:100%;
        height:auto;
    }

    ins{
        color: rgb(76, 175, 80);
        text-decoration: none;
    }

    .diff del {
        color:red;
    }

    .v-dialog {
        border-radius: 4px;
        margin: 24px;
        overflow-y: auto;
        pointer-events: auto;
        transition: 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
        width: 100%;
        z-index: inherit;
        box-shadow: 0px 11px 15px -7px rgba(0, 0, 0, 0.2), 0px 24px 38px 3px rgba(0, 0, 0, 0.14), 0px 9px 46px 8px rgba(0, 0, 0, 0.12);
    }

    iframe {
        width:100%;
        max-width:560px;        
        height:315px; 
    }

    #like .v-overlay__content {     
        height:100% !important;
        width:100% !important;
        position: relative;
    }
    /* #frame {
        background-color: #FAFAFA;    
    }

    .v-list, .v-card {
        background-color: #FAFAFA;    
    } */

    
</style>
