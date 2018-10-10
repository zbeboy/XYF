$(document).ready(function () {

    /*
     ajax url
    */
    var ajax_url = {
        reset_password: '/web/backstage/users/reset_password'
    };

    /*
     参数id
    */
    var paramId = {
        newPassword: '#newPassword',
        okPassword: '#okPassword'
    };

    /*
     参数
     */
    var param = {
        newPassword: $(paramId.newPassword).val().trim(),
        okPassword: $(paramId.okPassword).val().trim()
    };

    /**
     * 初始化参数
     */
    function initParam() {
        param.newPassword = $(paramId.newPassword).val().trim();
        param.okPassword = $(paramId.okPassword).val().trim();
    }

    /*
     检验正则
    */
    var valid_regex = {
        password_regex: /^[a-zA-Z0-9]\w{5,17}$/
    };

    /*
     错误消息id
     */
    var errorMsgId = {
        newPassword: '#new_password_error_msg',
        okPassword: '#ok_password_error_msg'
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

    $(paramId.newPassword).blur(function () {
        initParam();
        var newPassword = param.newPassword;
        if (!_.isEmpty(newPassword)) {
            if (valid_regex.password_regex.test(newPassword)) {
                validSuccessDom(paramId.newPassword, errorMsgId.newPassword);
            } else {
                validErrorDom(paramId.newPassword, errorMsgId.newPassword, '密码为数字字母6~18位');
            }
        }
    });

    $(paramId.okPassword).blur(function () {
        initParam();
        var okPassword = param.okPassword;
        if (!_.isEmpty(okPassword)) {
            var newPassword = param.newPassword;
            if (okPassword === newPassword) {
                validSuccessDom(paramId.okPassword, errorMsgId.okPassword);
            } else {
                validErrorDom(paramId.okPassword, errorMsgId.okPassword, '密码不一致');
            }
        }
    });

    $('#updatePassword').click(function () {
        updatePassword();
    });

    function updatePassword() {
        initParam();
        var msg;
        msg = Messenger().post({
            message: "确定更改密码吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        validPassword();
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

    function validPassword() {
        var newPassword = param.newPassword;
        if (!_.isEmpty(newPassword)) {
            if (valid_regex.password_regex.test(newPassword)) {
                var okPassword = param.okPassword;
                if (newPassword === okPassword) {
                    sendAjax();
                } else {
                    Messenger().post({
                        message: '请确认密码',
                        type: 'error',
                        showCloseButton: true
                    });
                }
            } else {
                Messenger().post({
                    message: '密码为数字字母6~18位',
                    type: 'error',
                    showCloseButton: true
                });
            }
        } else {
            Messenger().post({
                message: '请填写新密码',
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
            progressMessage: '正在更新密码....'
        }, {
            url: web_path + ajax_url.reset_password,
            type: 'put',
            data: $('#data_form').serialize(),
            success: function (data) {
                if (data.state) {
                    $(paramId.newPassword).val('');
                    $(paramId.okPassword).val('');
                    $('#passwordModal').modal('hide');
                }

                Messenger().post({
                    message: data.msg,
                    type: data.state ? 'info' : 'error',
                    showCloseButton: true
                });
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