/*
顯示標籤列
 */
let tagsApp = new Vue({
    el: '#tagsApp',
    data: {
        tags: []
    },
    methods: {
        loadTags: function () {
            console.log("執行了loadTags")
            $.ajax({
                url: '/faq/v1/tags',
                method: 'GET',
                success: function (r) {
                    console.log(r)
                    if (r.code === OK) {
                        console.log("成功獲取tags");
                        tagsApp.tags = r.data;
                    }

                }
            })
        }
    },
    created: function () {
        //網頁加載完就執行
        this.loadTags();
    }
})