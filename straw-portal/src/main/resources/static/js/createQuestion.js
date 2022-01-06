// $(document).ready(function () {
//     $('#summernote').summernote({
//         height: 300,
//         tabsize: 2,
//         lang: 'zh-CN',
//         placeholder: '请输入问题的详细描述...',
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


Vue.component('v-select', VueSelect.VueSelect);
let createQuestionApp = new Vue({
    el: '#createQuestionApp',
    data: {
        title: '',
        selectedTags: [],
        tags: [],
        selectedTeachers: [],
        teachers: []
    },
    methods: {
        createQuestion: function () {
            let content = $('#summernote').val();
            console.log(content);
            //data 对象，与服务器端QuestionVo的属性对应
            let data = {
                title: createQuestionApp.title,
                tagNames: this.selectedTags,
                teacherNicknames: this.selectedTeachers,
                content: content
            };
            console.log(data);
            $.ajax({
                url: '/v1/questions',
                traditional: true,  //采用传统数组编码方式，SpringMVC才能接收
                method: 'POST',
                data: data,
                success: function (r) {
                    console.log(r);
                    if (r.code === CREATED) {
                        console.log(r.message);
                        location.href='../index.html'
                    } else {
                        console.log(r.message);
                    }
                }
            });
        },
        loadTags: function () {
            console.log('開始加載標籤列表下拉框')
            $.ajax({
                url: '/v1/tags',
                method: 'GET',
                success: function (r) {
                    console.log(r)
                    if (r.code === OK) {
                        let tagName = [];
                        let tags = r.data;
                        for (let i = 0; i < tags.length; i++) {
                            tagName.push(tags[i].name);
                        }
                        createQuestionApp.tags = tagName
                    } else {
                        console.log('失敗');
                        console.log(r.message);
                    }
                }
            })
        },
        loadTeachers: function () {
            console.log("loadTeachers");
            $.ajax({
                url: '/v1/users/master',
                method: 'GET',
                success: function (r) {
                    console.log(r);
                    if (r.code == OK) {
                        let list = r.data;
                        let teachers = [];
                        for (let i = 0; i < list.length; i++) {
                            teachers.push(list[i].nickName);
                        }
                        createQuestionApp.teachers = teachers;
                    }else{
                        console.log('失敗')
                        console.log(r.message)
                    }
                }
            });
        }
    },
    created: function () {
        this.loadTags();
        this.loadTeachers();
    }
});