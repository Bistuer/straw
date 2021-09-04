let questionApp = new Vue({
    el: "#questionApp",
    data: {
        question: {}
    },
    methods: {
        loadQuestion: function () {
            //获取浏览器地址栏中当前url中?之后的内容
            let questionId = location.search;
            console.log("questionId:" + questionId);
            //判断是不是获得了?之后的内容
            if (!questionId) {
                //如果没有?则终止
                alert("必须指定问题id");
                return;
            }
            //如果存在?之后的内容,则去掉?    ?354
            questionId = questionId.substring(1);
            //发送异步请求
            $.ajax({
                url: "/v1/questions/" + questionId, //v1/questions/15
                method: "get",
                success: function (r) {
                    console.log(r);
                    if (r.code == OK) {
                        questionApp.question = r.data;
                        questionApp.updateDuration();
                    } else {
                        alert(r.message);
                    }
                }
            })
        },
        updateDuration: function () {
            //获得问题中的创建时间属性(毫秒数)
            let createtime = new Date(this.question.createtime).getTime();
            //获得当前时间的毫秒数
            let now = new Date().getTime();
            //计算时间差(秒)
            let durtaion = (now - createtime) / 1000;
            if (durtaion < 60) {
                // 显示刚刚
                //duration这个名字可以随便起,只要保证和页面上取的一样就行
                this.question.duration = "刚刚";
            } else if (durtaion < 60 * 60) {
                // 显示XX分钟
                this.question.duration =
                    (durtaion / 60).toFixed(0) + "分钟前";
            } else if (durtaion < 60 * 60 * 24) {
                //显示XX小时
                this.question.duration =
                    (durtaion / 60 / 60).toFixed(0) + "小时前";
            } else {
                //显示XX天
                this.question.duration =
                    (durtaion / 60 / 60 / 24).toFixed(0) + "天前";
            }
        }
    },
    created: function () {
        this.loadQuestion();
    }
});

$(function () {
    $('#summernote').summernote({
        height: 300,
        lang: 'zh-CN',
        placeholder: '请输入问题的详细描述...',
        callbacks: {
            //在执行指定操作后自动调用下面的方法
            //onImageUpload方法就会在用户选中图片之后立即运行
            onImageUpload: function (files) {
                //参数是一个file数组取出第一个,因为我们只会选中一个
                let file = files[0];
                //构建表单
                let form = new FormData();
                form.append("imageFile", file);
                $.ajax({
                    url: "/upload/file",
                    method: "post",
                    data: form,//发送的是我们构建的表单中的数据
                    //下面有两个特殊参数,需要在文件上传时设置
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (r) {
                        if (r.code == OK) {
                            console.log(r);
                            //将刚刚上传成功的图片显示在summernote富文本编辑器中
                            var img = new Image();//实例化了一个img标签
                            img.src = r.message;//将img标签的src属性赋值为刚上传的图片
                            //summernote方法中提供了插入标签的功能
                            //支持使用"insertNode"表示要向富文本编辑器中添加标签内容
                            $("#summernote").summernote(
                                "insertNode", img
                            )
                        } else {
                            alert(r.message);
                        }
                    }
                });
            }
        }
    });
})

let postAnswerApp = new Vue({
    el: "#postAnswerApp",
    data: {
        message: "",
        hasError: false
    },
    methods: {
        postAnswer: function () {
            postAnswerApp.hasError = false;
            let questionId = location.search;
            if (!questionId) {
                this.message = "没有问题ID";
                this.hasError = true;
                return;
            }
            //去掉?
            questionId = questionId.substring(1);
            let content = $("#summernote").val();
            if (!content) {
                this.message = "请填写回复内容";
                this.hasError = true;
                return;
            }
            let data = {
                questionId: questionId,
                content: content
            }
            $.ajax({
                url: "/v1/answers",
                method: "post",
                data: data,
                success: function (r) {
                    if (r.code == CREATED) {
                        postAnswerApp.message = r.message;
                        postAnswerApp.hasError = true;
                    } else {
                        postAnswerApp.message = r.message;
                        postAnswerApp.hasError = true;
                    }
                }
            })
        }
    }
})

let answersApp = new Vue({
    el: "#answersApp",
    data: {
        message: "",
        hasError: false,
        answers: []
    },
    methods: {
        loadAnswers: function () {
            let questionId = location.search;
            if (!questionId) {
                this.message = "必须有问题ID";
                this.hasError = true;
                return;
            }
            questionId = questionId.substring(1);
            $.ajax({
                url: "/v1/answers/question/" + questionId,
                method: "get",
                success: function (r) {
                    if (r.code == OK) {
                        answersApp.answers = r.data;
                    } else {
                        answersApp.message = r.message;
                        answersApp.hasError = true;
                    }
                }
            })
        }
    },
    created: function () {
        this.loadAnswers();
    }
})

























