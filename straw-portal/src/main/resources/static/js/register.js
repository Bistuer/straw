let app = new Vue({
    el: '#app',
    data: {
        inviteCode: '',
        phone: '',
        nickname: '',
        password: '',
        confirm: '',
        //这里message绑定的是register.html中<span v-text="message">邀请码错误！</span>
        message: '',
        //绑定 v-bind:class="{'d-block':hasError}">
        hasError: false
    },
    methods: {
        // <form action="/register" method="post" v-on:submit.prevent="register">
        register: function () {
            console.log('Submit');
            let data = {
                inviteCode: this.inviteCode,
                phone: this.phone,
                nickname: this.nickname,
                password: this.password,
                confirm: this.confirm
            }
            console.log(data);
            if (data.password !== data.confirm) {
                this.message = "两次密码输入不一致";
                this.hasError = true;
                return;
            }

            $.ajax({
                url: "/register",
                method: "POST",
                //这个data是上面 let data的data
                data: data,
                success: function (r) {
                    console.log(r);
                    if (r.code == CREATED) {
                        console.log("注册成功");
                        console.log(r.message);
                        //注册成功，可以直接跳转到页面;   location.search == "?register"看login.html
                        location.href="/login.html?register";
                    } else {
                        console.log(r.message);
                        //如果注册失败将信息显示在信息Div中
                        app.message = r.message;
                        app.hasError = true;
                    }
                }
            });

        }
    }
});