let questionApp = new Vue({
    el: '#questionApp',
    data: {
        question: {}
    },
    methods: {
        loadQuestion: function () {
            //通過地址欄獲取問題id
            let questionId = location.search //查詢參數
            if (!questionId) {
                alert('必須指定問題ID')
                return;
            }
            //去除查詢參數
            questionId = questionId.substring(1);

            $.ajax({
                url: '/faq/v1/questions/' + questionId,
                method: "GET",
                success: function (r) {
                    console.log("得到問題對象:")
                    console.log(r)
                    if (r.code === OK) {
                        let q = r.data;
                        addDuration(q);
                        questionApp.question = q;
                    } else {
                        alert(r.message)
                    }
                }
            })
        },
        // updateDuration: function (question) {
        //     //創建問題時候的時間毫秒數
        //     let createtime = new Date(question.createTime).getTime();
        //     //當前時間毫秒數
        //     let now = new Date().getTime();
        //     let duration = now - createtime;
        //     if (duration < 1000 * 60) { //一分鐘以內
        //         question.duration = '剛剛'
        //     } else if (duration < 1000 * 60 * 60) {//一小時以內
        //         question.duration = (duration / 1000 / 60).toFixed(0) + "分鐘以前"
        //     } else if (duration < 1000 * 60 * 60 * 24) { //一天以內
        //         question.duration = (duration / 1000 / 60 / 60).toFixed(0) + "小時以前"
        //     } else {
        //         question.duration = (duration / 1000 / 60 / 60 / 24).toFixed(0) + "天以前"
        //     }
        // }
    },
    created: function () {
        this.loadQuestion();
    }
});

//提交答案功能(搬去共用)

//初始化Summernote
// $(document).ready(function () {
//     $('#summernote').summernote({
//         height: 300,
//         tabsize: 2,
//         lang: 'zh-CN',
//         placeholder: '请输入详细描述...',
//         callbacks:{
//             onImageUpload:function (files) {
//                 //Summernote選擇了圖片以後自動執行的方法
//                 let file=files[0];
//                 let form=new FormData();
//                 form.append("imageFile",file);
//                 console.log(form);
//                 $.ajax({
//                     url:'/upload/image',
//                     method:'POST',
//                     data:form,
//                     contentType:false,
//                     processData:false,
//                     success:function (r){
//                         console.log(r)
//                         if(r.code===OK){
//                             let img=new Image();
//                             img.src=r.message;//顯示照片的URL
//                             //在當前的Summernote的內容中插入照片
//                             $('#summernote').summernote('insertNode',img);
//                         }else{
//                             alert(r.message)
//                         }
//                     }
//                 })
//             }
//         }
//     });
// });

// //Vue 視圖模型
// let postAnswerApp = new Vue({
//     el: '#postAnswerApp',
//     data: {
//         message: {},
//         hasError: false
//     },
//     methods: {
//         postAnswer: function () {
//             let questionId = location.search;
//             if (!questionId) {
//                 postAnswerApp.message = '必須要有問題的ID';
//                 postAnswerApp.hasError = true;
//                 return;
//             }
//             //去除多餘的?
//             questionId = questionId.substring(1);
//             let content = $('#summernote').val();
//             console.log(content);
//             if (!content) {
//                 postAnswerApp.message = '必須要有回覆內容';
//                 postAnswerApp.hasError = true;
//                 return;
//             }
//             let form = {
//                 questionId: questionId,
//                 content: content
//             }
//             $.ajax({
//                 url: '/v1/answers',
//                 method: 'POST',
//                 data: form,
//                 success: function (r) {
//                     console.log(r)
//                     if (r.code === CREATED) {
//                         let answer=r.data;
//                         console.log(answer)
//                         addDuration(answer)
//                         answersApp.answers.push(answer)
//                         //清空summernote輸入區域清空
//                         $('#summernote').summernote('reset')
//                         setTimeout(function (){
//                             postAnswerApp.hasError = false;
//                         },1000);
//                         postAnswerApp.message = r.message;
//                         postAnswerApp.hasError = true;
//                     } else {
//                         postAnswerApp.message = r.message;
//                         postAnswerApp.hasError = true;
//                     }
//                 }
//             })
//         }
//     }
// });

let answersApp = new Vue({
    el: '#answersApp',
    data: {
        answers: []
    },
    methods: {
        loadAnswers: function () {
            //當前問題的ID
            let questionId = location.search;
            if (!questionId) {
                return;
                alert('必須有問題ID');
            }
            questionId = questionId.substring(1);
            //利用Ajax從服務器讀取Answers數據
            $.ajax({
                // /v1/answers/question/157
                url: '/faq/v1/answers/question/' + questionId,
                method: 'GET',
                success: function (r) {
                    console.log(r)
                    if (r.code === OK) {
                        answersApp.answers = r.data;
                        answersApp.updateDuration();
                    } else {
                        alert(r.message)
                    }
                }

            })
        },
        updateDuration: function () {
            for (let i = 0; i < this.answers.length; i++) {
                addDuration(this.answers[i]);
            }
        },
        postComment:function (answerId,index){
            if(!answerId){
                return;
            }
            let textarea =$('#addComment'+answerId+' textarea');
            let content=textarea.val();
            if(!content){
                return;
            }
            let form={
                answerId:answerId,
                content:content
            }
            $.ajax({
                url:'/faq/v1/comments',
                method:'POST',
                data:form,
                success:function (r){
                    console.log(r)
                    if(r.code==CREATED){
                        let comment=r.data
                        //找到當前問題的全部評論列表,將新評論插入到列表最後
                        let answers=answersApp.answers;
                        answers[index].comments.push(comment);
                        // for(let i=0;i<answers.length;i++){
                        //     if(answers[i].id==answerId){
                        //         answers[i].comments.push(comment);
                        //         break;
                        //     }
                        // }
                        textarea.val("");
                        console.log(r.message)
                    }else{
                        console.log(r.message)
                    }
                }

            })
        },
        removeComment:function (commentId,index,comments){
            console.log("comment= "+commentId);
            if(!commentId){
                return;
            }
            $.ajax({
                url:'/faq/v1/comments/'+commentId+'/delete',
                method:'GET',
                success:function (r){
                    console.log(r)
                    if(r.code===GONE){
                        //從評論數組中刪除,已經被刪除的評論
                       comments.splice(index,1);
                    }else{
                        console.log(r.message)
                    }
                }
            })

        },
        updateComment:function (commentId,answerId,index,comments){
            if(!commentId){
                return;
            }
            if(!answerId){
                return;
            }
            let textarea=$('#editComment'+commentId+' textarea');
            let content=textarea.val();
            if(!content){
                return;
            }
            let form={
                answerId:answerId,
                content:content
            }
            $.ajax({
                url:'/faq/v1/comments/'+commentId+'/update',
                method:'POST',
                data:form,
                success:function (r){
                    console.log(r)
                    if(r.code===OK){
                        //更新comments數組中的數據,自動刷新頁面
                        Vue.set(comments,index,r.data);
                        $('#editComment'+commentId).collapse("hide")
                    }else{
                        alert(r.message)
                    }
                }
            })

        },
        answerSolved:function (answerId,answer){
            if(answer.acceptedStatus===1){
                alert("已經解決")
                return;
            }
            $.ajax({
                url:'/faq/v1/answers/'+answerId+'/solved',
                method:'GET',
                success:function (r){
                    console.log(r);
                    if(r.code===ACCEPTED){
                        alert('問題已解決')
                        answer.acceptedStatus=1;
                    }else{
                        alert(r.message)
                    }
                }
            })
        }
    },
    created: function () {
        this.loadAnswers();
    }
})