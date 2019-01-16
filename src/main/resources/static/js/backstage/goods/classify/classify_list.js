$(document).ready(function () {

    // 导航
    $('#collapseGoods').collapse('show');

    /*
    参数
    */
    var param = {
        classifyName: '',
        dataClassifyName: '',
        dataClassifyId: '',
        dataClassifyIsDel: ''
    };

    /*
    ajax url
    */
    function getAjaxUrl() {
        return {
            classifies: '/web/backstage/goods/classify/data',
            valid: '/web/backstage/goods/classify/valid',
            add: '/web/backstage/goods/classify/add',
            edit: '/web/backstage/goods/classify/edit',
            query: '/web/backstage/goods/classify/query',
            state: '/web/backstage/goods/classify/state',
            tableTime: '/web/backstage/table/CLASSIFY'
        };
    }

    /*
    参数id
    */
    function getParamId() {
        return {
            classifyName: '#search_classify',
            dataClassifyName: '#classifyName',
            dataClassifyId: '#classifyId',
            dataClassifyIsDel: '#classifyIsDel'
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
        param.classifyName = $(getParamId().classifyName).val();
        param.dataClassifyName = $(getParamId().dataClassifyName).val();
        param.dataClassifyId = $(getParamId().dataClassifyId).val();
        var isDel = $('input[name="classifyIsDel"]:checked').val();
        param.dataClassifyIsDel = _.isUndefined(isDel) ? 0 : isDel;
    }

    /*
    错误消息id
    */
    var errorMsgId = {
        dataClassifyName: '#classify_name_error_msg'
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
            "url": web_path + getAjaxUrl().classifies,
            "dataSrc": "data",
            "data": function (d) {
                // 添加额外的参数传给服务器
                var searchParam = getParam();
                d.extra_search = JSON.stringify(searchParam);
            }
        },
        "columns": [
            {"data": null},
            {"data": "classifyId"},
            {"data": "classifyName"},
            {"data": "classifyIsDel"},
            {"data": null}
        ],
        columnDefs: [
            {
                targets: 0,
                orderable: false,
                render: function (a, b, c, d) {
                    return '<input type="checkbox" value="' + c.classifyId + '" name="check"/>';
                }
            },
            {
                targets: 4,
                orderable: false,
                render: function (a, b, c, d) {

                    var context = null;

                    if (c.classifyIsDel === 0 || c.classifyIsDel == null) {
                        context =
                            {
                                func: [
                                    {
                                        "name": "编辑",
                                        "css": "edit",
                                        "type": "primary",
                                        "id": c.classifyId,
                                        "classify": c.classifyName
                                    },
                                    {
                                        "name": "删除",
                                        "css": "del",
                                        "type": "danger",
                                        "id": c.classifyId,
                                        "classify": c.classifyName
                                    }
                                ]
                            };
                    } else {
                        context =
                            {
                                func: [
                                    {
                                        "name": "编辑",
                                        "css": "edit",
                                        "type": "primary",
                                        "id": c.classifyId,
                                        "classify": c.classifyName
                                    },
                                    {
                                        "name": "恢复",
                                        "css": "recovery",
                                        "type": "warning",
                                        "id": c.classifyId,
                                        "classify": c.classifyName
                                    }
                                ]
                            };
                    }

                    return template(context);
                }
            },
            {
                targets: 3,
                render: function (a, b, c, d) {
                    if (c.classifyIsDel === 0 || c.classifyIsDel == null) {
                        return "<span class='text-info'>正常</span>";
                    } else {
                        return "<span class='text-danger'>已删除</span>";
                    }
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
            tableElement.delegate('.edit', "click", function () {
                edit($(this).attr('data-id'));
            });

            tableElement.delegate('.del', "click", function () {
                classify_del($(this).attr('data-id'), $(this).attr('data-classify'));
            });

            tableElement.delegate('.recovery', "click", function () {
                classify_recovery($(this).attr('data-id'), $(this).attr('data-classify'));
            });
        }
    });

    var html = '<div class="input-group">' +
        '<input type="text" id="search_classify" class="form-control form-control-sm" placeholder="类别" />' +
        '<div class="input-group-append">' +
        '<button type="button" id="search" class="btn btn-outline btn-default btn-sm"><i class="fa fa-search"></i>搜索</button>' +
        '<button type="button" id="reset_search" class="btn btn-outline btn-default btn-sm"><i class="fa fa-repeat"></i>重置</button>' +
        '</div>' +
        '</div>';
    $('#mytoolbox').append(html);

    var global_button = '<button type="button" id="classify_add" class="btn btn-outline btn-primary btn-sm"><i class="fa fa-plus"></i>添加</button>' +
        '  <button type="button" id="classify_dels" class="btn btn-outline btn-danger btn-sm"><i class="fa fa-trash-o"></i>批量删除</button>' +
        '  <button type="button" id="classify_recoveries" class="btn btn-outline btn-warning btn-sm"><i class="fa fa-reply-all"></i>批量恢复</button>' +
        '  <button type="button" id="refresh" class="btn btn-outline btn-default btn-sm"><i class="fa fa-refresh"></i>刷新</button>';
    $('#global_button').append(global_button);

    /*
    清空参数
    */
    function cleanParam() {
        $(getParamId().classifyName).val('');
    }

    $(getParamId().classifyName).keyup(function (event) {
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
    添加
    */
    $('#classify_add').click(function () {
        $('#modalLabel').text('添加类别');
        $('#modalType').val('add');
        $('#modal').modal('show');
    });

    /*
   批量注销
   */
    $('#classify_dels').click(function () {
        var classifyIds = [];
        var ids = $('input[name="check"]:checked');
        for (var i = 0; i < ids.length; i++) {
            classifyIds.push($(ids[i]).val());
        }

        if (classifyIds.length > 0) {
            var msg;
            msg = Messenger().post({
                message: "确定删除选中的类别吗?",
                actions: {
                    retry: {
                        label: '确定',
                        phrase: 'Retrying TIME',
                        action: function () {
                            msg.cancel();
                            dels(classifyIds);
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
            Messenger().post("未发现有选中的类别!");
        }
    });

    /*
     批量恢复
     */
    $('#classify_recoveries').click(function () {
        var classifyIds = [];
        var ids = $('input[name="check"]:checked');
        for (var i = 0; i < ids.length; i++) {
            classifyIds.push($(ids[i]).val());
        }

        if (classifyIds.length > 0) {
            var msg;
            msg = Messenger().post({
                message: "确定恢复选中的类别吗?",
                actions: {
                    retry: {
                        label: '确定',
                        phrase: 'Retrying TIME',
                        action: function () {
                            msg.cancel();
                            recoveries(classifyIds);
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
            Messenger().post("未发现有选中的类别!");
        }
    });

    /*
     编辑
     */
    function edit(classifyId) {
        Messenger().run({
            progressMessage: '正在查询数据...'
        }, {
            url: web_path + getAjaxUrl().query + '/' + classifyId,
            success: function (data) {
                $('#modalLabel').text('编辑类别');
                $('#modalType').val('edit');
                $(getParamId().dataClassifyName).val(data.classifyName);
                $(getParamId().dataClassifyId).val(classifyId);
                $(getParamId().dataClassifyIsDel).prop('checked', data.classifyIsDel);
                $('#modal').modal('show');
            },
            error: function (xhr) {
                if ((xhr != null ? xhr.status : void 0) === 404) {
                    return "请求错误";
                }
                return true;
            }
        });
    }

    $('#modal').on('shown.bs.modal', function () {
        $(getParamId().dataClassifyName).trigger('focus');
    });

    /**
     * 保存数据
     */
    $('#save').click(function () {
        initParam();
        validClassifyName();
    });

    /**
     * 校验类别名
     */
    function validClassifyName() {
        var dataClassifyName = getParam().dataClassifyName;
        if (_.inRange(dataClassifyName.length, 1, 30)) {
            var data = "";
            if ($('#modalType').val() === 'add') {
                data = {
                    type: 0,
                    classifyName: dataClassifyName
                };
            } else {
                data = {
                    type: 1,
                    classifyName: dataClassifyName,
                    classifyId: getParam().dataClassifyId
                };
            }
            Messenger().run({
                progressMessage: '正在校验数据...'
            }, {
                url: web_path + getAjaxUrl().valid,
                data: data,
                success: function (data) {
                    if (data.state) {
                        validSuccessDom(getParamId().dataClassifyName, errorMsgId.dataClassifyName);
                        sendAjax();
                    } else {
                        validErrorDom(getParamId().dataClassifyName, errorMsgId.dataClassifyName, data.msg);
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
            validErrorDom(getParamId().dataClassifyName, errorMsgId.dataClassifyName, '类别1~30个字符');
        }
    }

    /**
     * 发送数据到后台
     */
    function sendAjax() {
        var url = "";
        var type = "";
        if ($('#modalType').val() === 'add') {
            url = getAjaxUrl().add;
            type = "post";
        } else {
            url = getAjaxUrl().edit;
            type = "put";
        }
        Messenger().run({
            progressMessage: '正在保存数据...'
        }, {
            url: web_path + url,
            type: type,
            data: $('#dataForm').serialize(),
            success: function (data) {
                if (data.state) {
                    $('#modal').modal('hide');
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
                    return "请求错误";
                }
                return true;
            }
        });
    }


    /*
     删除
    */
    function classify_del(classifyId, classifyName) {
        var msg;
        msg = Messenger().post({
            message: "确定删除类别 '" + classifyName + "' 吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        del(classifyId);
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
    function classify_recovery(classifyId, classifyName) {
        var msg;
        msg = Messenger().post({
            message: "确定恢复类别 '" + classifyName + "' 吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        recovery(classifyId);
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

    function del(classifyId) {
        sendUpdateDelAjax(classifyId, '删除', 1);
    }

    function recovery(classifyId) {
        sendUpdateDelAjax(classifyId, '恢复', 0);
    }

    function dels(classifyIds) {
        sendUpdateDelAjax(classifyIds.join(","), '批量注销', 1);
    }

    function recoveries(classifyIds) {
        sendUpdateDelAjax(classifyIds.join(","), '批量恢复', 0);
    }

    /**
     * 注销或恢复ajax
     * @param classifyId
     * @param message
     * @param classifyIsDel
     */
    function sendUpdateDelAjax(classifyId, message, classifyIsDel) {
        Messenger().run({
            progressMessage: '正在' + message + '类别....'
        }, {
            url: web_path + getAjaxUrl().state,
            type: 'put',
            data: {classifyIds: classifyId, classifyIsDel: classifyIsDel},
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