$(document).ready(function () {

    /*
    参数
    */
    var param = {
        goodsName: ''
    };

    /*
    ajax url
    */
    function getAjaxUrl() {
        return {
            goods: '/web/goods/datum/data',
            add: '/web/goods/datum/add',
            edit: '/web/goods/datum/edit',
            tableTime: '/web/table/GOODS'
        };
    }

    /*
    参数id
    */
    function getParamId() {
        return {
            goodsName: '#search_goods'
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
        param.goodsName = $(getParamId().goodsName).val();
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
        "aaSorting": [[1, 'desc']],// 排序
        "ajax": {
            "url": web_path + getAjaxUrl().goods,
            "dataSrc": "data",
            "data": function (d) {
                // 添加额外的参数传给服务器
                var searchParam = getParam();
                d.extra_search = JSON.stringify(searchParam);
            }
        },
        "columns": [
            {"data": null},
            {"data": "goodsName"},
            {"data": "goodsPrice"},
            {"data": "goodsRecommend"},
            {"data": "classifyName"},
            {"data": "goodsSerial"},
            {"data": "goodsIsDel"},
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
                targets: 7,
                orderable: false,
                render: function (a, b, c, d) {

                    var context = null;

                    if (c.goodsIsDel === 0 || c.goodsIsDel == null) {
                        context =
                            {
                                func: [
                                    {
                                        "name": "编辑",
                                        "css": "edit",
                                        "type": "primary",
                                        "id": c.goods_id,
                                        "classify": c.goods_name
                                    },
                                    {
                                        "name": "删除",
                                        "css": "del",
                                        "type": "danger",
                                        "id": c.goods_id,
                                        "classify": c.goods_name
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
                                        "id": c.goods_id,
                                        "classify": c.goods_name
                                    },
                                    {
                                        "name": "恢复",
                                        "css": "recovery",
                                        "type": "warning",
                                        "id": c.goods_id,
                                        "classify": c.goods_name
                                    }
                                ]
                            };
                    }

                    return template(context);
                }
            },
            {
                targets: 6,
                render: function (a, b, c, d) {
                    if (c.goodsIsDel === 0 || c.goodsIsDel == null) {
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
                goods_del($(this).attr('data-id'), $(this).attr('data-goods'));
            });

            tableElement.delegate('.recovery', "click", function () {
                goods_recovery($(this).attr('data-id'), $(this).attr('data-goods'));
            });
        }
    });

    var html = '<div class="input-group">' +
        '<input type="text" id="search_goods" class="form-control form-control-sm" placeholder="商品名" />' +
        '<div class="input-group-append">' +
        '<button type="button" id="search" class="btn btn-outline btn-default btn-sm"><i class="fa fa-search"></i>搜索</button>' +
        '<button type="button" id="reset_search" class="btn btn-outline btn-default btn-sm"><i class="fa fa-repeat"></i>重置</button>' +
        '</div>' +
        '</div>';
    $('#mytoolbox').append(html);

    var global_button = '<button type="button" id="goods_add" class="btn btn-outline btn-primary btn-sm"><i class="fa fa-plus"></i>添加</button>' +
        '  <button type="button" id="goods_dels" class="btn btn-outline btn-danger btn-sm"><i class="fa fa-trash-o"></i>批量删除</button>' +
        '  <button type="button" id="goods_recoveries" class="btn btn-outline btn-warning btn-sm"><i class="fa fa-reply-all"></i>批量恢复</button>' +
        '  <button type="button" id="refresh" class="btn btn-outline btn-default btn-sm"><i class="fa fa-refresh"></i>刷新</button>';
    $('#global_button').append(global_button);

    /*
    清空参数
    */
    function cleanParam() {
        $(getParamId().goodsName).val('');
    }

    $(getParamId().goodsName).keyup(function (event) {
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
    $('#goods_add').click(function () {
        window.location.href = web_path + getAjaxUrl().add;
    });

    /*
   批量注销
   */
    $('#goods_dels').click(function () {
        var goodsIds = [];
        var ids = $('input[name="check"]:checked');
        for (var i = 0; i < ids.length; i++) {
            goodsIds.push($(ids[i]).val());
        }

        if (goodsIds.length > 0) {
            var msg;
            msg = Messenger().post({
                message: "确定删除选中的商品吗?",
                actions: {
                    retry: {
                        label: '确定',
                        phrase: 'Retrying TIME',
                        action: function () {
                            msg.cancel();
                            dels(goodsIds);
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
            Messenger().post("未发现有选中的商品!");
        }
    });

    /*
     批量恢复
     */
    $('#goods_recoveries').click(function () {
        var goodsIds = [];
        var ids = $('input[name="check"]:checked');
        for (var i = 0; i < ids.length; i++) {
            goodsIds.push($(ids[i]).val());
        }

        if (goodsIds.length > 0) {
            var msg;
            msg = Messenger().post({
                message: "确定恢复选中的商品吗?",
                actions: {
                    retry: {
                        label: '确定',
                        phrase: 'Retrying TIME',
                        action: function () {
                            msg.cancel();
                            recoveries(goodsIds);
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
            Messenger().post("未发现有选中的商品!");
        }
    });

    /*
     编辑
     */
    function edit(goodsId) {
        window.location.href = web_path + getAjaxUrl().edit + '/' + goodsId;
    }

    /*
     删除
    */
    function goods_del(goodsId, goodsName) {
        var msg;
        msg = Messenger().post({
            message: "确定删除商品 '" + goodsName + "' 吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        del(goodsId);
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
    function goods_recovery(goodsId, goodsName) {
        var msg;
        msg = Messenger().post({
            message: "确定恢复商品 '" + goodsName + "' 吗?",
            actions: {
                retry: {
                    label: '确定',
                    phrase: 'Retrying TIME',
                    action: function () {
                        msg.cancel();
                        recovery(goodsId);
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

    function del(goodsId) {
        sendUpdateDelAjax(goodsId, '删除', 1);
    }

    function recovery(goodsId) {
        sendUpdateDelAjax(goodsId, '恢复', 0);
    }

    function dels(goodsIds) {
        sendUpdateDelAjax(goodsIds.join(","), '批量注销', 1);
    }

    function recoveries(goodsIds) {
        sendUpdateDelAjax(goodsIds.join(","), '批量恢复', 0);
    }

    /**
     * 注销或恢复ajax
     * @param goodsId
     * @param message
     * @param goodsIsDel
     */
    function sendUpdateDelAjax(goodsId, message, goodsIsDel) {
        Messenger().run({
            progressMessage: '正在' + message + '商品....'
        }, {
            url: web_path + getAjaxUrl().state,
            type: 'put',
            data: {goodsIds: goodsId, goodsIsDel: goodsIsDel},
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