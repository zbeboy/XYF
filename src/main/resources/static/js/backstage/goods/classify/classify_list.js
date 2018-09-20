$(document).ready(function () {

    /*
    参数
    */
    var param = {
        classifyName: '',
        dataClassifyName: '',
        dataClassifyId: ''
    };

    /*
    ajax url
    */
    function getAjaxUrl() {
        return {
            classifies: '/web/goods/classify/data',
            valid: '/web/goods/classify/valid',
            add: '/web/goods/classify/add',
            edit: '/web/goods/classify/edit'
        };
    }

    /*
    参数id
    */
    function getParamId() {
        return {
            classifyName: '#search_classify',
            dataClassifyName: '#classifyName',
            dataClassifyId: '#dataClassifyId'
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
    添加
    */
    $('#classify_add').click(function () {
        $('#addModalLabel').text('添加类别');
        $('#addModal').modal('show');
    });

    /**
     * 保存数据
     */
    $('#save').click(function () {
        initParam();
        validClassifyName(getAjaxUrl().add)
    });

    /**
     * 校验类别名
     */
    function validClassifyName(url) {
        var dataClassifyName = getParam().dataClassifyName;
        if (_.inRange(dataClassifyName.length, 1, 30)) {
            Messenger().run({
                progressMessage: '正在校验数据...'
            }, {
                url: web_path + getAjaxUrl().valid,
                data: {
                    type: 0,
                    classifyName: dataClassifyName
                },
                success: function (data) {
                    if (data.state) {
                        validSuccessDom(getParamId().dataClassifyName, errorMsgId.dataClassifyName);
                        sendAjax(url);
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
     * @param url
     */
    function sendAjax(url) {
        Messenger().run({
            progressMessage: '正在保存数据...'
        }, {
            url: web_path + url,
            type: 'post',
            data: $('#dataForm').serialize(),
            success: function (data) {
                if (data.state) {
                    $('#addModal').modal('hide');
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

});