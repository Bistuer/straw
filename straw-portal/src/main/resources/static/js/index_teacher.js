
/*
显示登录讲师的问题列表
 */
let questionsApp = new Vue({
    el:'#questionsApp',
    data: {
        questions:[],
        pageInfo:{},
        style:"fa-tasks",
        navTitle:"我的任务"
    },
    methods: {
        loadQuestions:function (pageNum) {
            if(!pageNum){ //如果pageNum为空,默认页码为1
                pageNum=1;
            }
            $.ajax({
                url: '/v1/questions/teacher',
                method: "GET",
                data:{pageNum:pageNum},
                success: function (r) {
                    console.log("成功加载数据");
                    console.log(r);
                    if(r.code === OK){
                        questionsApp.questions = r.data.list;
                        //调用计算持续时间的方法
                        questionsApp.updateDuration();
                        //调用显示所有按标签呈现的图片
                        questionsApp.updateTagImage();
                        questionsApp.pageInfo=r.data;
                    }
                }
            });
        },
        updateTagImage:function(){
            let questions = this.questions;
            for(let i=0; i<questions.length; i++){
                //获得当前问题对象的所有标签的集合(数组)
               let tags = questions[i].tags;
               //js代码中特有的写法if(tags)
               //相当于判断tags非空
               if(tags){
                   //获取当前问题的第一个标签对应的图片文件路径
                   let tagImage = 'img/tags/'+tags[0].id+'.jpg';
                   console.log(tagImage);
                   //将这个文件路径保存到tagImage属性用,以便页面调用
                   questions[i].tagImage = tagImage;
               }
            }
        },
        updateDuration:function () {
            let questions=this.questions;
            for(let i=0;i<questions.length;i++){
                //获得问题中的创建时间属性(毫秒数)
                let createtime=new Date(questions[i].createtime).getTime();
                //获得当前时间的毫秒数
                let now=new Date().getTime();
                //计算时间差(秒)
                let durtaion=(now-createtime)/1000;
                if(durtaion<60){
                    // 显示刚刚
                    //duration这个名字可以随便起,只要保证和页面上取的一样就行
                    questions[i].duration="刚刚";
                }else if(durtaion<60*60){
                    // 显示XX分钟
                    questions[i].duration=
                        (durtaion/60).toFixed(0)+"分钟前";
                }else if (durtaion<60*60*24){
                    //显示XX小时
                    questions[i].duration=
                        (durtaion/60/60).toFixed(0)+"小时前";
                }else{
                    //显示XX天
                    questions[i].duration=
                        (durtaion/60/60/24).toFixed(0)+"天前";
                }

            }

        }
    },
    created:function () {
        console.log("执行了方法");
        this.loadQuestions(1);
    }
});










