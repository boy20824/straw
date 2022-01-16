$(document).ready(function () {
    $('#summernote').summernote({
        height: 300,
        tabsize: 2,
        lang: 'zh-CN',
        placeholder: '请输入详细內容...',
        callbacks:{
            onImageUpload:function (files) {
                //Summernote選擇了圖片以後自動執行的方法
                let file=files[0];
                let form=new FormData();
                form.append("imageFile",file);
                console.log(form);
                $.ajax({
                    url:'/resource/v1/images',
                    method:'POST',
                    data:form,
                    contentType:false,
                    processData:false,
                    success:function (r){
                        console.log(r)
                        if(r.code===OK){
                            let img=new Image();
                            img.src=r.message;//顯示照片的URL
                            //在當前的Summernote的內容中插入照片
                            $('#summernote').summernote('insertNode',img);
                        }else{
                            alert(r.message)
                        }
                    }
                })
            }
        }
    });
});