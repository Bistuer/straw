let app = new Vue({
    el:'#app',
    data:{
        inviteCode:'',
        phone:'',
        nickname:'',
        password:'',
        confirm:''
    },
    methods:{
        register:function () {
            console.log('Submit');
            let data = {
                inviteCode: this.inviteCode,
                phone: this.phone,
                nickname: this.nickname,
                password: this.password,
                confirm: this.confirm
            }
            console.log(data);
            if(data.password !== data.confirm){
                return;
            }
            $.ajax({
                url:"/register",
                method: "POST",
                data: data,
                success: function (r) {
                    console.log(r);
                    if(r.code == CREATED){
                        console.log("注册成功");
                        console.log(r.message);
                    }else{
                        console.log(r.message);
                    }
                }
            });
        }
    }
});