$(document).ready(function () {

    /*
   ajax url
   */
    function getAjaxUrl() {
        return {
            banners: '/web/backstage/goods/banner/data',
            file_upload_url: '/web/backstage/goods/banner/upload'
        };
    }

    init();

    function init() {
        initBanner();
    }

    function initBanner() {
        $.get(web_path + getAjaxUrl().banners, function (data) {
            $('#datas').empty();
            bannerData(data);
        });
    }

    /**
     * 数据
     * @param data 数据
     */
    function bannerData(data) {
        var template = Handlebars.compile($("#banner-template").html());

        Handlebars.registerHelper('imgSrc', function () {
            return new Handlebars.SafeString(Handlebars.escapeExpression(web_path + this.bannerUrl));
        });

        Handlebars.registerHelper('imgHideCss', function () {
            var css = 'eye';
            if (this.bannerIsHide !== 1) {
                css = 'eye-slash';
            }
            return new Handlebars.SafeString(Handlebars.escapeExpression(css));
        });

        Handlebars.registerHelper('imgHideClass', function () {
            var hideClass = 'showImg';
            if (this.bannerIsHide !== 1) {
                hideClass = 'hideImg';
            }
            return new Handlebars.SafeString(Handlebars.escapeExpression(hideClass));
        });

        Handlebars.registerHelper('imgHide', function () {
            var hideVal = '0';
            if (this.bannerIsHide !== 1) {
                hideVal = '1';
            }
            return new Handlebars.SafeString(Handlebars.escapeExpression(hideVal));
        });

        Handlebars.registerHelper('imgHideText', function () {
            var hideText = '显示';
            if (this.bannerIsHide !== 1) {
                hideText = '隐藏';
            }
            return new Handlebars.SafeString(Handlebars.escapeExpression(hideText));
        });

        $('#datas').prepend(template(data));
    }

    // 上传组件
    $('#fileupload').fileupload({
        url: web_path + getAjaxUrl().file_upload_url,
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
            if (data.result.state) {
                initBanner();
            }
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

});