$(document).ready(function () {

    // 导航
    $('#collapseGoods').collapse('show');

    /*
     ajax url
    */
    var ajax_url = {
        classifies: '/web/backstage/goods/datum/classifies',
        file_upload_url: '/web/backstage/goods/datum/upload',
        del_pic: '/web/backstage/goods/datum/del_pic',
        valid: '/web/backstage/goods/datum/valid',
        save: '/web/backstage/goods/datum/save',
        back: '/web/backstage/goods/datum/list'
    };

    /*
     参数id
    */
    var paramId = {
        classifyId: '#classifyId',
        goodsName: '#goodsName',
        goodsRecommend: '#goodsRecommend',
        goodsItem: '#goodsItem',
        goodsPrice: '#goodsPrice',
        goodsSerial: '#goodsSerial',
        goodsIsStick: '#goodsIsStick',
        goodsIsDel: '#goodsIsDel',
        goodsPic: '#goodsPic',
        goodsPicTemp: '#goodsPicTemp',
        goodsBrief: '#goodsBrief'
    };

    /*
    参数
    */
    var param = {
        classifyId: $(paramId.classifyId).val(),
        goodsName: $(paramId.goodsName).val(),
        goodsRecommend: $(paramId.goodsRecommend).val(),
        goodsItem:$(paramId.goodsItem).val(),
        goodsPrice: $(paramId.goodsPrice).val(),
        goodsSerial: $(paramId.goodsSerial).val(),
        goodsIsStick: $("input[name='goodsIsStick']:checked").val(),
        goodsIsDel: $("input[name='goodsIsDel']:checked").val(),
        goodsPic: $(paramId.goodsPic).val(),
        goodsBrief: $(paramId.goodsBrief).val()
    };

    /*
     初始化参数
     */
    function initParam() {
        param.classifyId = $(paramId.classifyId).val();
        param.goodsName = $(paramId.goodsName).val();
        param.goodsRecommend = $(paramId.goodsRecommend).val();
        param.goodsItem = $(paramId.goodsItem).val();
        param.goodsPrice = $(paramId.goodsPrice).val();
        param.goodsSerial = $(paramId.goodsSerial).val();
        var isStick = $('input[name="goodsIsStick"]:checked').val();
        param.goodsIsStick = _.isUndefined(isStick) ? 0 : isStick;
        var isDel = $('input[name="goodsIsDel"]:checked').val();
        param.goodsIsDel = _.isUndefined(isDel) ? 0 : isDel;
        param.goodsPic = $(paramId.goodsPic).val();
        param.goodsBrief = $(paramId.goodsBrief).val();
    }

    /*
     错误消息id
     */
    var errorMsgId = {
        goodsName: '#goods_name_error_msg',
        goodsRecommend: '#goods_recommend_error_msg'
    };

    /**
     * 检验成功
     * @param validId
     * @param errorMsgId
     */
    function validSuccessDom(validId, errorMsgId) {
        $(validId).removeClass('is-invalid');
        $(errorMsgId).text('');
    }

    /**
     * 检验失败
     * @param validId
     * @param errorMsgId
     * @param msg
     */
    function validErrorDom(validId, errorMsgId, msg) {
        $(validId).addClass('is-invalid');
        $(errorMsgId).text(msg);
    }

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

    $(paramId.goodsName).blur(function () {
        initParam();
        var goodsName = param.goodsName;
        if (!_.isEmpty(goodsName) && _.inRange(goodsName.length, 1, 100)) {
            Messenger().run({
                progressMessage: '正在校验数据...'
            }, {
                url: web_path + ajax_url.valid,
                data: {
                    type: 0,
                    goodsName: goodsName
                },
                success: function (data) {
                    if (data.state) {
                        validSuccessDom(paramId.goodsName, errorMsgId.goodsName);
                    } else {
                        validErrorDom(paramId.goodsName, errorMsgId.goodsName, data.msg);
                    }
                },
                error: function (xhr) {
                    if ((xhr != null ? xhr.status : void 0) === 404) {
                        return "请求错误";
                    }
                    return true;
                }
            });
        } else {
            validErrorDom(paramId.goodsName, errorMsgId.goodsName, "商品名1~100个字符")
        }
    });

    $(paramId.goodsRecommend).blur(function () {
        initParam();
        var goodsRecommend = param.goodsRecommend;
        if (_.toSafeInteger(goodsRecommend) <= 5) {
            validSuccessDom(paramId.goodsRecommend, errorMsgId.goodsRecommend);
        } else {
            validErrorDom(paramId.goodsRecommend, errorMsgId.goodsRecommend, "商品推荐度最大5")
        }
    });

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
                    if (data.state) {
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

    /*
    保存数据
    */
    $('#save').click(function () {
        add();
    });

    /*
    返回
     */
    $('#back').click(function () {
        window.location.href = web_path + ajax_url.back;
    });

    /*
    添加询问
    */
    function add() {
        initParam();
        var goodsName = param.goodsName;
        var msg;
        msg = Messenger().post({
            message: "确定添加商品 '" + goodsName + "'  吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        validGoodsName();
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

    /**
     * 添加时检验并提交数据
     */
    function validGoodsName() {
        var goodsName = param.goodsName;
        if (!_.isEmpty(goodsName) && _.inRange(goodsName.length, 1, 100)) {
            Messenger().run({
                progressMessage: '正在校验数据...'
            }, {
                url: web_path + ajax_url.valid,
                data: {
                    type: 0,
                    goodsName: goodsName
                },
                success: function (data) {
                    if (data.state) {
                        validGoodsRecommend();
                    } else {
                        Messenger().post({
                            message: data.msg,
                            type: 'error',
                            showCloseButton: true
                        });
                    }
                },
                error: function (xhr) {
                    if ((xhr != null ? xhr.status : void 0) === 404) {
                        return "请求错误";
                    }
                    return true;
                }
            });
        } else {
            Messenger().post({
                message: '商品名1~100个字符',
                type: 'error',
                showCloseButton: true
            });
        }
    }

    function validGoodsRecommend() {
        var goodsRecommend = param.goodsRecommend;
        if (_.toSafeInteger(goodsRecommend) <= 5) {
            validGoodsPic();
        } else {
            Messenger().post({
                message: '商品推荐度最大5',
                type: 'error',
                showCloseButton: true
            });
        }
    }

    function validGoodsPic() {
        var goodsPic = param.goodsPic;
        if (!_.isEmpty(goodsPic)) {
            sendAjax();
        } else {
            Messenger().post({
                message: '请上传商品图片',
                type: 'error',
                showCloseButton: true
            });
        }
    }

    /**
     * 发送数据到后台
     */
    function sendAjax() {
        Messenger().run({
            progressMessage: '正在保存数据....'
        }, {
            url: web_path + ajax_url.save,
            type: 'post',
            data: param,
            success: function (data) {
                if (data.state) {
                    window.location.href = web_path + ajax_url.back;
                } else {
                    Messenger().post({
                        message: data.msg,
                        type: 'error',
                        showCloseButton: true
                    });
                }
            },
            error: function (xhr) {
                if ((xhr != null ? xhr.status : void 0) === 404) {
                    return "请求失败";
                }
                return true;
            }
        });
    }

});