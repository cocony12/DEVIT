import eventBus from '@/lib/EventBus.js'

export default {
    loginWithKakao() {
        try{
            Kakao.init('9e2c2cdb8b79cea2b27f5fec32a3cf91');
        }catch(e){
            // console.dir(e)
          }
        Kakao.Auth.login({
            success: function(authObj) {
                Kakao.API.request({
                  url: '/v2/user/me',
                  success: function(res) {
                    eventBus.$emit('socialLogin', res.kakao_account.email, res.kakao_account.profile.nickname, res.id);
                  },
                  fail: function(error) {
                    
                  },
                })
              },
            fail: function (error) {
                // console.log(error);
            },
        });
        
    }
}