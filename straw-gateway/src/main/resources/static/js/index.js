/*
    顯示所有問題
 */
let questionsApp = new Vue({
    el: "#questionsApp",
    data: {
        questions: [],
        pageInfo: {}
    },
    methods: {
        loadQuestions: function (pageNum) {
            if (!pageNum) {
                pageNum = 1;
            }
            $.ajax({
                url: '/v1/questions/my',
                method: 'GET',
                data: {
                    pageNum: pageNum
                },
                success: function (r) {
                    console.log("成功加載")
                    console.log(r);
                    if (r.code === OK) {
                        questionsApp.questions = r.data.list;
                        questionsApp.pageInfo = r.data;
                        //調用方法,為每個Questions計算持續時間
                        questionsApp.updateDuration();
                        //調用方法,為每個Questions新增圖片屬性
                        questionsApp.updateTagImage();
                    }
                }
            })
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
                //創建問題時候的時間毫秒數
                addDuration(questions[i])
            }

        }

    },
    created: function () {
        console.log("執行了方法")
        this.loadQuestions(1);
    }
})