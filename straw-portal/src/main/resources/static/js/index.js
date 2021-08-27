/*
显示当前用户的问题
 */
let questionsApp = new Vue({
    el: '#questionsApp',
    data: {
        questions: []
    },
    methods: {
        loadQuestions: function () {
            $.ajax({
                url: '/v1/questions/my',
                method: "GET",
                success: function (r) {
                    console.log("成功加载数据");
                    console.log(r);
                    if (r.code === OK) {
                        questionsApp.questions = r.data;
                        //调用计算持续时间的方法
                        questionsApp.updateDuration();
                    }
                }
            });
        },
        updateTagImage: function () {
            let questions = this.questions;
            for (let i = 0; i < questions.length; i++) {
                let tags = questions[i].tags;
                if (tags) {
                    let tagImage = '/img/tags/' + tags[0].id + '.jpg';
                    console.log(tagImage);
                    questions[i].tagImage = tagImage;
                }
            }
        },
        updateDuration: function () {
            let questions = this.questions;
            for (let i = 0; i < questions.length; i++) {
                //获得问题中的创建时间属性(毫秒数)
                let createtime = new Date(questions[i].createtime).getTime();
                //当前时间毫秒数
                let now = new Date().getTime();
                //计算时间差(秒)
                let duration = (now - createtime) / 1000;
                if (duration < 60) { //一分钟以内
                    //显示刚刚
                    //duration这个名字可以随便起,只要保证和页面上取的一致就行
                    questions[i].duration = "刚刚";
                } else if (duration < 60 * 60) { //一小时以内
                    //显示XX分钟  toFixed()四舍五入，0表示保存0位小数
                    questions[i].duration =
                        (duration / 60).toFixed(0) + "分钟前";
                } else if (duration < 60 * 60 * 24) {
                    //显示XX小时
                    questions[i].duration =
                        (duration / 60 / 60).toFixed(0) + "小时以前";
                } else {
                    //显示几天前
                    questions[i].duration =
                        (duration / 60 / 60 / 24).toFixed(0) + "天以前";
                }
            }
        }
    },
    created: function () {
        console.log("执行了方法");
        this.loadQuestions(1);
    }
});










