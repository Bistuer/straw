/*
显示当前用户的问题
 */
let questionsApp = new Vue({
    el: '#questionsApp',
    data: {
        questions: [],
        pageInfo: {},
        style:"fa-comments-o",
        navTitle:"我的问答"
    },
    methods: {
        loadQuestions: function (pageNum) {
            //判断pageNum是否是1,如果pageNum为空,默认页码为1
            if (!pageNum) {
                pageNum = 1;
            }
            $.ajax({
                url: '/faq/v1/questions/my',
                method: "GET",
                //前面的pageNum: 是QuestionController my(Integer pageNum)的pageNum
                data: {pageNum: pageNum},
                success: function (r) {
                    console.log("成功加载数据");
                    console.log(r);
                    if (r.code === OK) {
                        questionsApp.questions = r.data.list;
                        //调用计算持续时间的方法
                        questionsApp.updateDuration();
                        //调用显示所有按标签呈现的图片
                        questionsApp.updateTagImage();

                        questionsApp.pageInfo = r.data;
                    }
                }
            });
        },
        updateTagImage: function () {
            let questions = this.questions;
            for (let i = 0; i < questions.length; i++) {
                //获得当前问题对象的所有标签的集合(数组)
                let tags = questions[i].tags;
                //js代码中特有的写法,相当于判断tags非空 if(tags) => if(tags!=null)
                if (tags) {
                    let tagImage = '/img/tags/' + tags[0].id + '.jpg';
                    console.log(tagImage);
                    // questions[i].tagImage = tagImage; 中的tagImage是自己定义的
                    // 将这个文件路径保存到tagImage属性用，以便页面调用
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










