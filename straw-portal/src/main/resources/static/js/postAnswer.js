//Vue 視圖模型
let postAnswerApp = new Vue({
    el: '#postAnswerApp',
    data: {
        message: {},
        hasError: false
    },
    methods: {
        postAnswer: function () {
            let questionId = location.search;
            if (!questionId) {
                postAnswerApp.message = '必須要有問題的ID';
                postAnswerApp.hasError = true;
                return;
            }
            //去除多餘的?
            questionId = questionId.substring(1);
            let content = $('#summernote').val();
            console.log(content);
            if (!content) {
                postAnswerApp.message = '必須要有回覆內容';
                postAnswerApp.hasError = true;
                return;
            }
            let form = {
                questionId: questionId,
                content: content
            }
            $.ajax({
                url: '/v1/answers',
                method: 'POST',
                data: form,
                success: function (r) {
                    console.log(r)
                    if (r.code === CREATED) {
                        let answer=r.data;
                        console.log(answer)
                        addDuration(answer)
                        answersApp.answers.push(answer)
                        //清空summernote輸入區域清空
                        $('#summernote').summernote('reset')
                        setTimeout(function (){
                            postAnswerApp.hasError = false;
                        },1000);
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
});