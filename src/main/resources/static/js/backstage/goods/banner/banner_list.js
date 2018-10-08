$(document).ready(function () {

    // 导航
    $('#collapseGoods').collapse('show');

    /*
   ajax url
   */
    function getAjaxUrl() {
        return {
            banners: '/web/backstage/goods/banner/data',
            file_upload_url: '/web/backstage/goods/banner/upload',
            hide_url: '/web/backstage/goods/banner/hide',
            serial_url: '/web/backstage/goods/banner/serial',
            del: '/web/backstage/goods/banner/delete'
        };
    }

    init();

    function init() {
        initBanner();
    }

    var datas = $('#datas');

    function initBanner() {
        $.get(web_path + getAjaxUrl().banners, function (data) {
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

        datas.html(template(data));
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

    datas.delegate('.showImg', "click", function () {
        hideAsk($(this).attr('data-id'), 0, "显示");
    });

    datas.delegate('.hideImg', "click", function () {
        hideAsk($(this).attr('data-id'), 1, "隐藏");
    });

    datas.delegate('.serial', "click", function () {
        $('#bannerSerial').val($(this).attr('data-serial'));
        $('#bannerId').val($(this).attr('data-id'));
        $('#serialModal').modal('show');
    });

    $('#serialModal').on('shown.bs.modal', function () {
        $('#bannerSerial').trigger('focus');
    });

    datas.delegate('.del', "click", function () {
        delAsk($(this).attr('data-id'));
    });

    function hideAsk(bannerId, hide, message) {
        var msg;
        msg = Messenger().post({
            message: "确定" + message + "该banner吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        sendHideAjax(bannerId, hide);
                    }
                },
                cancel: {
                    label: '取消',
                    action: function () {
                        return msg.cancel();
                    }
                }
            }
        });
    }

    function delAsk(bannerId) {
        var msg;
        msg = Messenger().post({
            message: "确定要删除该banner吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        sendDelAjax(bannerId);
                    }
                },
                cancel: {
                    label: '取消',
                    action: function () {
                        return msg.cancel();
                    }
                }
            }
        });
    }

    function sendHideAjax(bannerId, hide) {
        Messenger().run({
            progressMessage: '正在更新数据...'
        }, {
            url: web_path + getAjaxUrl().hide_url,
            type: 'put',
            data: {bannerId: bannerId, bannerIsHide: hide},
            success: function (data) {
                if (data.state) {
                    initBanner();
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

    function sendDelAjax(bannerId) {
        Messenger().run({
            progressMessage: '正在删除数据...'
        }, {
            url: web_path + getAjaxUrl().del + '/' + bannerId,
            type: 'delete',
            success: function (data) {
                if (data.state) {
                    initBanner();
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

    $('#updateSerial').click(function () {
        Messenger().run({
            progressMessage: '正在更新数据...'
        }, {
            url: web_path + getAjaxUrl().serial_url,
            type: 'put',
            data: $('#serialForm').serialize(),
            success: function (data) {
                if (data.state) {
                    initBanner();
                    $('#serialModal').modal('hide');
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
    });

});