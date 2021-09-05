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
                url: "/v1/questions/" + questionId,//v1/questions/15
                method: "get",
                success: function (r) {
                    console.log(r);
                    if (r.code == OK) {
                        questionApp.question = r.data;
                        addDuration(questionApp.question);
                    } else {
                        alert(r.message);
                    }
                }
            })
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
                        let answer = r.data;    //这个r.data就是新增的回答
                        //将这个问题的持续时间计算出来
                        addDuration(answer);
                        //将新增的方法插入到anwsers数组的后面
                        answersApp.answers.push(answer);
                        //回答已经显示,清空富文本编辑器中的内容
                        $("#summernote").summernote("reset");
                        postAnswerApp.message = r.message;
                        postAnswerApp.hasError = true;
                        //2秒中之后信息消失
                        setTimeout(function () {
                            postAnswerApp.hasError = false;
                        }, 2000);
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
                        answersApp.updateDuration();
                    } else {
                        answersApp.message = r.message;
                        answersApp.hasError = true;
                    }
                }
            })
        },
        updateDuration: function () {
            for (let i = 0; i < this.answers.length; i++) {
                addDuration(this.answers[i]);
            }
        }
    },
    created: function () {
        this.loadAnswers();
    }
})