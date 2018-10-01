$(document).ready(function () {

    /*
     ajax url
    */
    var ajax_url = {
        classifies: '/web/backstage/goods/datum/classifies',
        file_upload_url: '/web/backstage/goods/datum/upload',
        del_pic: '/web/backstage/goods/datum/del_pic',
        save: '/web/backstage/article/save',
        back: '/web/backstage/article'
    };

    /*
     参数id
    */
    var paramId = {
        classifyId: '#classifyId',
        articleTitle: '#articleTitle',
        articleBrief: '#articleBrief',
        goodsPic: '#goodsPic',
        goodsPicTemp: '#goodsPicTemp',
        articleSources: '#articleSources',
        articleSourcesName: '#articleSourcesName',
        articleSourcesLink: '#articleSourcesLink',
        articleSn: '#articleSn'
    };

    /*
    参数
    */
    var param = {
        menuId: $(paramId.menuId).val(),
        articleTitle: $(paramId.articleTitle).val(),
        articleBrief: $(paramId.articleBrief).val(),
        articleCover: $(paramId.articleCover).val(),
        articleContent: '',
        articleSources: $("input[name='articleSources']:checked").val(),
        articleSourcesName: $(paramId.articleSourcesName).val(),
        articleSourcesLink: $(paramId.articleSourcesLink).val(),
        articleSn: $(paramId.articleSn).val()
    };

    init();

    function init() {
        initClassify();
    }

    function initClassify() {
        $.get(web_path + ajax_url.classifies, function (data) {
            classifyData(data);
        });
    }

    /**
     * 类别数据
     * @param data 数据
     */
    function classifyData(data) {
        var template = Handlebars.compile($("#classify-template").html());
        $(paramId.classifyId).append(template(data));
    }

    // 上传组件
    $('#fileupload').fileupload({
        url: web_path + ajax_url.file_upload_url,
        dataType: 'json',
        maxFileSize: file_max_size,// 100MB
        acceptFileTypes: /([.\/])(jpg|jpeg|png|gif)$/i,
        formAcceptCharset: 'utf-8',
        maxNumberOfFiles: 1,
        messages: {
            maxNumberOfFiles: '最大支持上传1个文件',
            acceptFileTypes: '仅支持上传jpg,png,gif等类型文件',
            maxFileSize: '单文件上传仅允许100MB大小'
        },
        done: function (e, data) {
            $(paramId.goodsPicTemp).attr('src', web_path + "/" + data.result.picPath + "/" + data.result.info.newName);
            $(paramId.goodsPic).val("/" + data.result.picPath + "/" + data.result.info.newName);
            $('.fileinput-button').addClass('hidden');
            Messenger().post({
                message: data.result.msg,
                type: data.result.state ? 'success' : 'error',
                showCloseButton: true
            });
        }
    }).on('fileuploadadd', function (evt, data) {
        var isOk = true;
        var $this = $(this);
        var validation = data.process(function () {
            return $this.fileupload('process', data);
        });
        validation.fail(function (data) {
            isOk = false;
            Messenger().post({
                message: '上传失败: ' + data.files[0].error,
                type: 'error',
                showCloseButton: true
            });
        });
        return isOk;
    });

    var IMG = "data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iMzE5IiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDMxOSAyMDAiIHByZXNlcnZlQXNwZWN0UmF0aW89Im5vbmUiPjwhLS0KU291cmNlIFVSTDogaG9sZGVyLmpzLzEwMCV4MjAwCkNyZWF0ZWQgd2l0aCBIb2xkZXIuanMgMi42LjAuCkxlYXJuIG1vcmUgYXQgaHR0cDovL2hvbGRlcmpzLmNvbQooYykgMjAxMi0yMDE1IEl2YW4gTWFsb3BpbnNreSAtIGh0dHA6Ly9pbXNreS5jbwotLT48ZGVmcz48c3R5bGUgdHlwZT0idGV4dC9jc3MiPjwhW0NEQVRBWyNob2xkZXJfMTYyYmUyMDgyYWQgdGV4dCB7IGZpbGw6I0FBQUFBQTtmb250LXdlaWdodDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2ZvbnQtc2l6ZToxNnB0IH0gXV0+PC9zdHlsZT48L2RlZnM+PGcgaWQ9ImhvbGRlcl8xNjJiZTIwODJhZCI+PHJlY3Qgd2lkdGg9IjMxOSIgaGVpZ2h0PSIyMDAiIGZpbGw9IiNFRUVFRUUiLz48Zz48dGV4dCB4PSIxMTcuOTg0Mzc1IiB5PSIxMDcuMiI+MzE5eDIwMDwvdGV4dD48L2c+PC9nPjwvc3ZnPg==";

    $('#clearImg').click(function () {
        $(paramId.goodsPicTemp).attr('src', IMG);
        var goodsPic = $(paramId.goodsPic).val();
        if (!_.isEmpty(goodsPic)) {
            Messenger().run({
                progressMessage: '正在删除图片...'
            }, {
                url: web_path + ajax_url.del_pic,
                data: {goodsPic: goodsPic},
                success: function (data) {
                    if(data.state){
                        $(paramId.goodsPic).val('');
                        $('.fileinput-button').removeClass('hidden');
                    }
                    Messenger().post({
                        message: data.msg,
                        type: data.state ? 'info' : 'error',
                        showCloseButton: true
                    });
                },
                error: function (xhr) {
                    if ((xhr != null ? xhr.status : void 0) === 404) {
                        return "请求错误";
                    }
                    return true;
                }
            });
        }
    });
});