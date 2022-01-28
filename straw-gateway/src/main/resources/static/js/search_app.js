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
    },
    created:function () {
    let key=location.search
        //檢查地址上欄上是否?key=?
        if(key && key.startsWith("?key=")){
            //擷取key=後面的訊息
            key=decodeURI(key.substring("?key=".length));
            this.key=key;
        }
    }
})