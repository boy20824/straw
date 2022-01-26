let searchApp =new Vue({
    el:"#searchApp",
    data:{
        key:""
    },
    methods:{
        search:function () {
            //利用URL地址欄,傳遞搜索關鍵字,地址欄必須編碼
            location.href="/search.html?key="+encodeURI(this.key)
        }
    }
})