$(document).ready(function () {

    /*
    参数
    */
    var param = {
        username: '',
        realName: ''
    };

    /*
    ajax url
    */
    function getAjaxUrl() {
        return {
            users: '/web/backstage/users/data',
            del: '/web/backstage/users/disabled',
            lock: '/web/backstage/users/lock',
            reset: '/web/backstage/users/reset',
            tableTime: '/web/backstage/table/USERS'
        };
    }

    /*
    参数id
    */
    function getParamId() {
        return {
            username: '#search_username',
            realName: '#search_real_name'
        };
    }

    /*
     得到参数
     */
    function getParam() {
        return param;
    }

    /*
     初始化参数
     */
    function initParam() {
        param.username = $(getParamId().username).val();
        param.realName = $(getParamId().realName).val();
    }

    /*
    错误消息id
    */
    var errorMsgId = {
        dataClassifyName: '#classify_name_error_msg'
    };

    init();

    function init() {
        initTableTime();
    }

    function initTableTime() {
        Messenger().run({
            progressMessage: '正在初始化表...'
        }, {
            url: web_path + getAjaxUrl().tableTime,
            success: function (data) {
                if (data.dealTimeStr) {
                    moment.locale('zh-cn');
                    $('#tableTime').text('更新于 ' + moment(data.dealTimeStr, 'YYYY-MM-DD hh:mm:ss').calendar() + ' ' + data.dealTimeStr);
                }
            },
            error: function (xhr) {
                if ((xhr != null ? xhr.status : void 0) === 404) {
                    return "请求错误";
                }
                return true;
            }
        });
    }

    // 预编译模板
    var template = Handlebars.compile($("#operator_button").html());

    var tableElement = $('#dataTable');

    var myTable = tableElement.DataTable({
        drawCallback: function (oSettings) {
            $('#checkall').prop('checked', false);
            // 调用全选插件
            $.fn.check({checkall_name: "checkall", checkbox_name: "check"});
        },
        autoWidth: false,
        searching: false,
        "processing": true, // 打开数据加载时的等待效果
        "serverSide": true,// 打开后台分页
        "aaSorting": [[1, 'desc']],// 排序
        "ajax": {
            "url": web_path + getAjaxUrl().users,
            "dataSrc": "data",
            "data": function (d) {
                // 添加额外的参数传给服务器
                var searchParam = getParam();
                d.extra_search = JSON.stringify(searchParam);
            }
        },
        "columns": [
            {"data": null},
            {"data": "username"},
            {"data": "realName"},
            {"data": "sex"},
            {"data": "contact"},
            {"data": "address"},
            {"data": "disabled"},
            {"data": "accountExpired"},
            {"data": "accountLocked"},
            {"data": null}
        ],
        columnDefs: [
            {
                targets: 0,
                orderable: false,
                render: function (a, b, c, d) {
                    return '<input type="checkbox" value="' + c.username + '" name="check"/>';
                }
            },
            {
                targets: 6,
                render: function (a, b, c, d) {
                    if (c.disabled === 0 || c.disabled == null) {
                        return "<span class='text-info'>正常</span>";
                    } else {
                        return "<span class='text-danger'>已注销</span>";
                    }
                }
            },
            {
                targets: 7,
                render: function (a, b, c, d) {
                    if (c.accountExpired === 0 || c.accountExpired == null) {
                        return "<span class='text-info'>正常</span>";
                    } else {
                        return "<span class='text-danger'>已过期</span>";
                    }
                }
            },
            {
                targets: 8,
                render: function (a, b, c, d) {
                    if (c.accountLocked === 0 || c.accountLocked == null) {
                        return "<span class='text-info'>正常</span>";
                    } else {
                        return "<span class='text-danger'>已锁</span>";
                    }
                }
            },
            {
                targets: 9,
                orderable: false,
                render: function (a, b, c, d) {

                    var context =
                        {
                            func: [
                                {
                                    "name": "重置密码",
                                    "css": "reset_password",
                                    "type": "primary",
                                    "id": c.username,
                                    "user": c.realName
                                },
                                {
                                    "name": c.disabled === 1 ? "恢复" : "注销",
                                    "css": c.disabled === 1 ? "recovery" : "del",
                                    "type": c.disabled === 1 ? "warning" : "danger",
                                    "id": c.username,
                                    "user": c.realName
                                },
                                {
                                    "name": c.accountLocked === 1 ? "解锁" : "锁定",
                                    "css": c.accountLocked === 1 ? "unlock" : "lock",
                                    "type": c.accountLocked === 1 ? "warning" : "danger",
                                    "id": c.username,
                                    "user": c.realName
                                }
                            ]
                        };

                    return template(context);
                }
            }

        ],
        "language": {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        },
        "dom": "<'row'<'col-sm-2'l><'#global_button.col-sm-4'><'col-sm-6'<'#mytoolbox'>>r>" +
            "t" +
            "<'row'<'col-sm-5'i><'col-sm-7'p>>",
        initComplete: function () {
            tableElement.delegate('.reset_password', "click", function () {
                reset_password($(this).attr('data-id'), $(this).attr('data-user'));
            });

            tableElement.delegate('.del', "click", function () {
                user_del($(this).attr('data-id'), $(this).attr('data-user'));
            });

            tableElement.delegate('.recovery', "click", function () {
                user_recovery($(this).attr('data-id'), $(this).attr('data-user'));
            });

            tableElement.delegate('.unlock', "click", function () {
                user_unlocked($(this).attr('data-id'), $(this).attr('data-user'));
            });

            tableElement.delegate('.lock', "click", function () {
                user_locked($(this).attr('data-id'), $(this).attr('data-user'));
            });
        }
    });

    var html = '<div class="input-group">' +
        '<input type="text" id="search_username" class="form-control form-control-sm" placeholder="账号" />' +
        '<input type="text" id="search_real_name" class="form-control form-control-sm" placeholder="姓名" />' +
        '<div class="input-group-append">' +
        '<button type="button" id="search" class="btn btn-outline btn-default btn-sm"><i class="fa fa-search"></i>搜索</button>' +
        '<button type="button" id="reset_search" class="btn btn-outline btn-default btn-sm"><i class="fa fa-repeat"></i>重置</button>' +
        '</div>' +
        '</div>';
    $('#mytoolbox').append(html);

    var global_button = '<button type="button" id="user_dels" class="btn btn-outline btn-danger btn-sm"><i class="fa fa-trash-o"></i>批量注销</button>' +
        '  <button type="button" id="user_recoveries" class="btn btn-outline btn-warning btn-sm"><i class="fa fa-reply-all"></i>批量恢复</button>' +
        '  <button type="button" id="refresh" class="btn btn-outline btn-default btn-sm"><i class="fa fa-refresh"></i>刷新</button>';
    $('#global_button').append(global_button);

    /*
    清空参数
    */
    function cleanParam() {
        $(getParamId().username).val('');
        $(getParamId().realName).val('');
    }

    $(getParamId().username).keyup(function (event) {
        if (event.keyCode === 13) {
            initParam();
            myTable.ajax.reload();
        }
    });

    $(getParamId().realName).keyup(function (event) {
        if (event.keyCode === 13) {
            initParam();
            myTable.ajax.reload();
        }
    });

    $('#search').click(function () {
        initParam();
        myTable.ajax.reload();
    });

    $('#reset_search').click(function () {
        cleanParam();
        initParam();
        myTable.ajax.reload();
    });

    $('#refresh').click(function () {
        myTable.ajax.reload();
    });

    /*
   批量注销
   */
    $('#user_dels').click(function () {
        var usersIds = [];
        var ids = $('input[name="check"]:checked');
        for (var i = 0; i < ids.length; i++) {
            usersIds.push($(ids[i]).val());
        }

        if (usersIds.length > 0) {
            var msg;
            msg = Messenger().post({
                message: "确定注销选中的用户吗?",
                actions: {
                    retry: {
                        label: '确定',
                        phrase: 'Retrying TIME',
                        action: function () {
                            msg.cancel();
                            dels(usersIds);
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
        } else {
            Messenger().post("未发现有选中的用户!");
        }
    });

    /*
     批量恢复
     */
    $('#user_recoveries').click(function () {
        var userIds = [];
        var ids = $('input[name="check"]:checked');
        for (var i = 0; i < ids.length; i++) {
            userIds.push($(ids[i]).val());
        }

        if (userIds.length > 0) {
            var msg;
            msg = Messenger().post({
                message: "确定恢复选中的用户吗?",
                actions: {
                    retry: {
                        label: '确定',
                        phrase: 'Retrying TIME',
                        action: function () {
                            msg.cancel();
                            recoveries(userIds);
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
        } else {
            Messenger().post("未发现有选中的用户!");
        }
    });

    /*
   重置
    */
    function reset_password(userId, realName) {
        var msg;
        msg = Messenger().post({
            message: "确定重置用户 '" + realName + "' 吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        reset(userId);
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

    /*
     注销
    */
    function user_del(userId, realName) {
        var msg;
        msg = Messenger().post({
            message: "确定注销用户 '" + realName + "' 吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        del(userId);
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

    /*
     恢复
     */
    function user_recovery(userId, realName) {
        var msg;
        msg = Messenger().post({
            message: "确定恢复用户 '" + realName + "' 吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        recovery(userId);
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

    /*
    锁定
    */
    function user_locked(userId, realName) {
        var msg;
        msg = Messenger().post({
            message: "确定锁定用户 '" + realName + "' 吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        locked(userId);
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

    /*
    解锁
    */
    function user_unlocked(userId, realName) {
        var msg;
        msg = Messenger().post({
            message: "确定解锁用户 '" + realName + "' 吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        unlocked(userId);
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

    function reset(userId) {
        sendUpdateResetAjax(userId);
    }

    function del(userId) {
        sendUpdateDelAjax(userId, '注销', 1);
    }

    function recovery(userId) {
        sendUpdateDelAjax(userId, '恢复', 0);
    }

    function locked(userId) {
        sendUpdateLockedAjax(userId, '锁定', 1);
    }

    function unlocked(userId) {
        sendUpdateLockedAjax(userId, '解锁', 0);
    }

    function dels(userIds) {
        sendUpdateDelAjax(userIds.join(","), '批量注销', 1);
    }

    function recoveries(userIds) {
        sendUpdateDelAjax(userIds.join(","), '批量恢复', 0);
    }

    /**
     * 重置ajax
     * @param userId
     */
    function sendUpdateResetAjax(userId) {
        Messenger().run({
            progressMessage: '正在重置用户....'
        }, {
            url: web_path + getAjaxUrl().reset + '/' + userId,
            type: 'put',
            success: function (data) {
                if (data.state) {
                    myTable.ajax.reload();
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

    /**
     * 注销或恢复ajax
     * @param userId
     * @param message
     * @param disabled
     */
    function sendUpdateDelAjax(userId, message, disabled) {
        Messenger().run({
            progressMessage: '正在' + message + '用户....'
        }, {
            url: web_path + getAjaxUrl().del,
            type: 'put',
            data: {userIds: userId, disabled: disabled},
            success: function (data) {
                if (data.state) {
                    myTable.ajax.reload();
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

    /**
     * 锁定或解锁ajax
     * @param userId
     * @param message
     * @param accountLocked
     */
    function sendUpdateLockedAjax(userId, message, accountLocked) {
        Messenger().run({
            progressMessage: '正在' + message + '用户....'
        }, {
            url: web_path + getAjaxUrl().lock,
            type: 'put',
            data: {userIds: userId, accountLocked: accountLocked},
            success: function (data) {
                if (data.state) {
                    myTable.ajax.reload();
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