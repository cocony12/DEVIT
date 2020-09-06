<template>
    <v-layout id="main" style="width:100%;">      
        <div id="editorFrame" :class="{focused : isFocus}">
            {{hideTab}}
            <ul v-show="hideTab==null" :class="{focused : isFocus}">
                <li>
                    <button :class="{active : isActive}" @click="isActive = true">
                        Write
                    </button>
                </li>
                <li>
                    <button :class="{active : !isActive}" @click="isActive = false;">
                        Preview
                    </button>
                </li>

                <li style="float:right;" v-show="isActive">
                    <v-tooltip bottom color="primary" open-on-hover open-on-focus open-on-click v-for="(item, i) in toolbar" :key="`${i}_toolbar`">
                        <template v-slot:activator="{ on, attrs }">
                            <v-icon 
                                v-bind="attrs"
                                v-on="on" 
                                class="toolbar" 
                                @click="clickToolbar(item.message)" 
                                :style="{'font-size' : item.size}"
                                light
                            >
                                {{item.icon}}
                            </v-icon>
                        </template>
                        <span>{{item.message}}</span>
                    </v-tooltip>

                </li>
            </ul>            
            <!-- <div id="editor" contenteditable v-show="isActive" @focusin="isFocus=true;" @focusout="isFocus=false;" @paste.prevent.self="checkPaste()" @input="onInput" @keypress="checkKeypress"></div> -->
            <div v-show="isActive && !isFullScreen">
                <textarea id="editor"  @focusin="isFocus=true;" @focusout="isFocus=false;" @paste.prevent.self="checkPaste()" @input="onInput" @keypress="checkKeypress" :value="value" :style="{'height' : (height ? height+'px' : 'auto')}" @change="changeText" />
                <v-divider  style="margin-bottom:10px" />
                <span style='font-size:14px; line-height: 16px;     font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen-Sans, Ubuntu, Cantarell, "Helvetica Neue", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";'> <a style="cursor:text">Markdown</a> and <a style="cursor:text">tags</a> are supported </span>
                

            </div>
            <div class="full_screen" v-show="isFullScreen">
                <div>
                    <textarea id="full_screen_editor" v-show="isActive" @paste.prevent.self="checkPaste()" @input="onInput" @keypress="checkKeypress" :value="value" placeholder="Write Here..."> </textarea>
                </div>
                <div style="position:fixed; top:10px; right:10px; z-index:2000; cursor:pointer;" @click="isFullScreen=false">
                    <v-icon style="font-size:48px;">mdi-close</v-icon>
                </div>
            </div>
            <div id="view" v-show="!isActive" :style="{'height' : (height ? height+'px' : 'auto')}" v-html="parseContent"> </div>
        </div>
    </v-layout>
</template>

<script>
import axios from "axios";
import parse from "@/lib/markdown/ParseMd.js";
export default {
    props: ['value', 'height', 'hideTab'],
    watch: {
        isActive(){ 
            if(!this.isActive){                
                this.parseContent = this.parse($('#editor').val());
            }
        },        
        hideTab(){
            
        },
        value()
        {
            // console.dir(this.parse(this.value))
        }
    },
    data() {
        return {
            isFocus: false,
            isActive: true,
            isFullScreen: false,
            parseContent: '',
            content: '',
            toolbar: [
                {
                    icon: 'mdi-format-header-1',
                    message: 'header',
                    size: '18px',           
                },
                {
                    icon: 'mdi-format-bold',
                    message: 'bold',
                    size: '18px',
                },
                {
                    icon: 'mdi-format-italic',
                    message: 'italic',
                    size: '18px',
                },
                {
                    icon: 'mdi-format-underline',
                    message: 'underline',
                    size: '18px',
                },
                {
                    icon: 'mdi-format-strikethrough-variant',
                    message: 'strikethrough',
                    size: '15px',
                },
                {
                    icon: 'mdi-minus',
                    message: 'divider',
                    size: '18px',
                },
                {
                    icon: 'mdi-format-quote-close',
                    message: 'blockquote',
                    size: '18px',
                },
                {
                    icon: 'mdi-code-tags',
                    message: 'code',
                    size: '18px',
                },
                {
                    icon: 'mdi-paperclip mdi-rotate-45',
                    message: 'link',
                    size: '18px',
                },
                {
                    icon: 'mdi-fullscreen',
                    message: 'fullscreen',
                    size: '18px',
                },
            ]

        }
    },
    methods: {
        parse,
        changeText(e){
            this.$router.app.$store.commit('setChange', true);            
        },
        clickToolbar(msg){
            var selectionText = "";
            if (document.getSelection) {
                selectionText = document.getSelection().toString();
            } else if (document.selection) {
                selectionText = document.selection.createRange().text;
            }

            switch(msg){
                case 'header':
                    selectionText = `#${selectionText}`
                    break;
                case 'bold':
                    selectionText = `__${selectionText}__`
                    break;
                case 'italic':
                    selectionText = `_${selectionText}_`
                    break;
                case 'underline':
                    selectionText = `___${selectionText}___`
                    break;
                case 'strikethrough':
                    selectionText = `~~${selectionText}~~`
                    break;
                case 'divider':
                    selectionText = `---`
                    break;
                case 'blockquote':
                    selectionText = `>${selectionText}`
                    break;
                case 'code':
                    selectionText = `\`\`\`\n${selectionText}\n\`\`\``
                    break;
                case 'link':
                    selectionText = `[${selectionText}](url)`
                    break;
                case 'fullscreen':
                    this.isFullScreen = true;
                    break;
            }            
            document.execCommand('insertText', false, selectionText)
        },
        onInput(e){            
            this.$emit('input', e.target.value);
        },
        checkKeypress(e) {
            // if(e.keyCode == 13){
            //     document.execCommand('insertHTML', false, '<br>');
            //     e.preventDefault();
            //     e.stopPropagation();

                
            // }
        },
        checkPaste(e){
            var items = (event.clipboardData || event.originalEvent.clipboardData).items;
            let flag = true;
            for (var index in items) {
                var item = items[index];
                if (item.kind === 'file') {
                    var blob = item.getAsFile();
                    this.imageTransfer(blob)
                    flag = false;
                }
            }
            if(flag) {
                let clipboardData = event.clipboardData || window.clipboardData;
                let pastedData = clipboardData.getData('Text');
                document.execCommand('insertText', false, pastedData)
            }
            return false;
        },

        imageTransfer(blob) {
            let name = blob.name;

            var frm = new FormData();
            frm.append("file", blob);            
            axios.post('http://i3a101.p.ssafy.io:8080/api/v1/file/upload', frm, {
                headers: {
                    'accept': '*/*',
                    'X-AUTH-TOKEN': this.$router.app.$store.state.token,
                    'Content-Type': 'multipart/form-data'
                }
            }).then(({data}) => {
                document.execCommand('insertText', false, `![${name}](http://i3a101.p.ssafy.io/images/${data.result})`);
            })
            .catch((error) => {
                console.dir(error)
            }).finally(()=>{

            })
        },
    }
}
</script>

<style scoped>
    

    .v-icon:hover{
        color:#1B69B6;
    }

    .v-icon.v-icon::after {
        border-radius: 50%;
        height: 0;
    }

    *{
        word-wrap: break-word;
        overflow-wrap: break-word;
        white-space: normal;
    }
    :focus {
        outline: none;
        /* #1B69B6 */
    }

    img {
        max-width: 100%; 
        height: auto;
    }
    

    #view{
        margin-top:15px;
        width:100%;
        height:90%;
        overflow-y:auto;
    }
    
    .v-application p{
        margin: 0px;
    }

    ul {
        list-style: none;
        padding-left: 0;
        border-bottom:1px solid #e5e5e5;
        width:100%; 
        height:33px;
    }

    .focused {        
        border-color:#80bdff;
    }

    li {
        display: inline; 
        height:32px;
    }
    
    .toolbar {
        font-size:18px;
    }

    button {
        height:32px;
        padding:0px 10px 11px 10px;
        font-size:14px;
        color:#707070;
    }

    button.active{
        border-bottom:2px solid #6666c4;
        color:#000000;
    }

    #editorFrame {
        width:98%;
        max-width:98%;
        color:#2e2e2e;
        background-color:#fff;
        border:1px solid #e5e5e5;
        outline: 0;
        padding:10px;
        margin:3px;
    }
    
    #editorFrame.focused {
        color:#2e2e2e;
        background-color:#fff;
        border-color:#80bdff;
        outline: 0;
        padding:10px;
        margin:3px;
        box-shadow: 0 0 0 0.2rem rgba(0,123,255,0.25);
    }

    #editor{
        line-height: 18px;
        margin-top:15px;
        font-size: 13px;
        width:100%;
        overflow-y:auto;
        resize: none;
    }

    .full_screen {
        background-color: #fff;
        position: fixed;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        z-index: 2000;        
    }

    #full_screen_editor{
        border: 0;
        box-shadow: none;
        border-radius: 0;
        color: #000;
        font-size: 20px;
        line-height: 26px;
        padding: 30px;
        display: block;
        outline: none;
        resize: none;
        height: 100vh;
        max-height: calc(100vh - 10px);
        max-width: 900px;
        width:67%;
        margin: 0 auto 10px;
    }
</style>