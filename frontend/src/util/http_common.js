import Vue from "vue"
import axios from "axios";
import store from "@/store/index.js"


// export default axios.create({
//   baseURL: "http://i3a101.p.ssafy.io:8080",
//          headers: {
//            // "X-AUTH-TOKEN": this.token ? this.token : '',
//            "Content-type": "application/json"
//          }
// })

export default new Vue({
  computed: {
    token() {
       return store.state.token;
    },
    axios() {
      return axios.create({        
        baseURL: "http://i3a101.p.ssafy.io:8080",
        headers: {
          "x-auth-token": this.token ? this.token : '',
          "Content-type": "application/json"
        }
      });
    }
  } 
}); 