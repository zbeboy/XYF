$(document).ready(function () {
    /*
    参数
    */
    var param = {
        customerName: '',
        customerContact: '',
        hasDeal: ''
    };

    /*
    ajax url
    */
    function getAjaxUrl() {
        return {
            feedbacks: '/web/backstage/feedback/data',
            state: '/web/backstage/feedback/state',
            remark: '/web/backstage/feedback/remark',
            del: '/web/backstage/feedback/delete',
            tableTime: '/web/backstage/table/FEEDBACK'
        };
    }

    /*
    参数id
    */
    function getParamId() {
        return {
            customerName: '#search_customer_name',
            customerContact: '#search_customer_contact',
            hasDeal: '#search_has_deal'
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
        param.customerName = $(getParamId().customerName).val();
        param.customerContact = $(getParamId().customerContact).val();
        param.hasDeal = $(getParamId().hasDeal).val();
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
        responsive: {
            details: {
                display: $.fn.dataTable.Responsive.display.modal({
                    header: function (row) {
                        var data = row.data();
                        return '详情 ' + data[0] + ' ' + data[1];
                    }
                }),
                renderer: $.fn.dataTable.Responsive.renderer.tableAll({
                    tableClass: 'table'
                })
            }
        },
        drawCallback: function (oSettings) {
            $('#checkall').prop('checked', false);
            // 调用全选插件
            $.fn.check({checkall_name: "checkall", checkbox_name: "check"});
        },
        autoWidth: false,
        searching: false,
        "processing": true, // 打开数据加载时的等待效果
        "serverSide": true,// 打开后台分页
        "aaSorting": [[4, 'desc']],// 排序
        "ajax": {
            "url": web_path + getAjaxUrl().feedbacks,
            "dataSrc": "data",
            "data": function (d) {
                // 添加额外的参数传给服务器
                var searchParam = getParam();
                d.extra_search = JSON.stringify(searchParam);
            }
        },
        "columns": [
            {"data": null},
            {"data": "customerName"},
            {"data": "customerContact"},
            {"data": "content"},
            {"data": "createDateStr"},
            {"data": "hasDeal"},
            {"data": "remark"},
            {"data": null}
        ],
        columnDefs: [
            {
                targets: 0,
                orderable: false,
                render: function (a, b, c, d) {
                    return '<input type="checkbox" value="' + c.feedbackId + '" name="check"/>';
                }
            },
            {
                targets: 3,
                orderable: false
            },
            {
                targets: 6,
                orderable: false
            },
            {
                targets: 7,
                orderable: false,
                render: function (a, b, c, d) {

                    var context = null;

                    if (c.hasDeal === 0 || c.hasDeal == null) {
                        context =
                            {
                                func: [
                                    {
                                        "name": "已处理",
                                        "css": "hasDeal",
                                        "type": "primary",
                                        "id": c.feedbackId,
                                        "remark": c.remark
                                    },
                                    {
                                        "name": "备注",
                                        "css": "remark",
                                        "type": "default",
                                        "id": c.feedbackId,
                                        "remark": c.remark
                                    },
                                    {
                                        "name": "删除",
                                        "css": "del",
                                        "type": "danger",
                                        "id": c.feedbackId,
                                        "remark": c.remark
                                    }
                                ]
                            };
                    } else {
                        context =
                            {
                                func: [
                                    {
                                        "name": "未处理",
                                        "css": "noDeal",
                                        "type": "primary",
                                        "id": c.feedbackId,
                                        "remark": c.remark
                                    },
                                    {
                                        "name": "备注",
                                        "css": "remark",
                                        "type": "default",
                                        "id": c.feedbackId,
                                        "remark": c.remark
                                    },
                                    {
                                        "name": "删除",
                                        "css": "del",
                                        "type": "danger",
                                        "id": c.feedbackId,
                                        "remark": c.remark
                                    }
                                ]
                            };
                    }

                    return template(context);
                }
            },
            {
                targets: 5,
                render: function (a, b, c, d) {
                    if (c.hasDeal === 0 || c.hasDeal == null) {
                        return "<span class='text-info'>未处理</span>";
                    } else {
                        return "<span class='text-danger'>已处理</span>";
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
        "dom": "<'row'<'col-sm-2'l><'#global_button.col-sm-4'>r>" +
            "t" +
            "<'row'<'col-sm-5'i><'col-sm-7'p>>",
        initComplete: function () {
            tableElement.delegate('.hasDeal', "click", function () {
                deal($(this).attr('data-id'), 1);
            });

            tableElement.delegate('.noDeal', "click", function () {
                deal($(this).attr('data-id'), 0);
            });

            tableElement.delegate('.remark', "click", function () {
                remark($(this).attr('data-id'), $(this).attr('data-remark'));
            });

            tableElement.delegate('.del', "click", function () {
                feedback_del($(this).attr('data-id'));
            });
        }
    });

    var global_button = '<button type="button" id="feedback_dels" class="btn btn-outline btn-danger btn-sm"><i class="fa fa-trash-o"></i>批量删除</button>' +
        '  <button type="button" id="refresh" class="btn btn-outline btn-default btn-sm"><i class="fa fa-refresh"></i>刷新</button>';
    $('#global_button').append(global_button);

    /*
    清空参数
    */
    function cleanParam() {
        $(getParamId().customerName).val('');
        $(getParamId().customerContact).val('');
        $(getParamId().hasDeal).val('');
    }

    $(getParamId().customerName).keyup(function (event) {
        if (event.keyCode === 13) {
            initParam();
            myTable.ajax.reload();
        }
    });

    $(getParamId().customerContact).keyup(function (event) {
        if (event.keyCode === 13) {
            initParam();
            myTable.ajax.reload();
        }
    });

    $(getParamId().hasDeal).change(function () {
        initParam();
        myTable.ajax.reload();
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

    function deal(feedbackId, state) {
        Messenger().run({
            progressMessage: '正在处理数据...'
        }, {
            url: web_path + getAjaxUrl().state,
            type: 'put',
            data: {feedbackId: feedbackId, hasDeal: state},
            success: function (data) {
                if (data.state) {
                    myTable.ajax.reload();
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

    function remark(feedbackId, remark) {
        $('#remark').val(remark);
        $('#feedbackId').val(feedbackId);
        $('#remarkModal').modal('show');
    }

    $('#remarkModal').on('shown.bs.modal', function () {
        $('#remark').trigger('focus');
    });

    $('#remarkSave').click(function () {
        Messenger().run({
            progressMessage: '正在更新数据...'
        }, {
            url: web_path + getAjaxUrl().remark,
            type: 'put',
            data: $('#remarkForm').serialize(),
            success: function (data) {
                if (data.state) {
                    myTable.ajax.reload();
                    $('#remarkModal').modal('hide');
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

    /*
   批量删除
   */
    $('#feedback_dels').click(function () {
        var feedbackIds = [];
        var ids = $('input[name="check"]:checked');
        for (var i = 0; i < ids.length; i++) {
            feedbackIds.push($(ids[i]).val());
        }

        if (feedbackIds.length > 0) {
            var msg;
            msg = Messenger().post({
                message: "确定删除选中的条目吗?",
                actions: {
                    retry: {
                        label: '确定',
                        phrase: 'Retrying TIME',
                        action: function () {
                            msg.cancel();
                            dels(feedbackIds);
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
            Messenger().post("未发现有选中的条目!");
        }
    });

    /*
     删除
    */
    function feedback_del(feedbackId) {
        var msg;
        msg = Messenger().post({
            message: "确定删除该项吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        del(feedbackId);
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

    function del(feedbackId) {
        sendDelAjax(feedbackId);
    }

    function dels(feedbackIds) {
        sendDelAjax(feedbackIds.join(","));
    }

    /**
     * 删除ajax
     * @param feedbackIds
     */
    function sendDelAjax(feedbackIds) {
        Messenger().run({
            progressMessage: '正在删除条目....'
        }, {
            url: web_path + getAjaxUrl().del + '/' + feedbackIds,
            type: 'delete',
            success: function (data) {
                if (data.state) {
                    myTable.ajax.reload();
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