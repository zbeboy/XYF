$(document).ready(function() {

    /*
    参数
    */
    var param = {
        classifyName: ''
    };

    /*
    ajax url
    */
    function getAjaxUrl() {
        return {
            classifies: '/web/goods/classify/data',
            add: '/web/goods/classify/add',
            edit: '/web/goods/classify/edit'
        };
    }

    /*
    参数id
    */
    function getParamId() {
        return {
            classifyName: '#search_classify'
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
    }

    // 预编译模板
    var template = Handlebars.compile($("#operator_button").html());

    var tableElement = $('#dataTable');

    var myTable = tableElement.DataTable( {
        responsive: {
            details: {
                display: $.fn.dataTable.Responsive.display.modal( {
                    header: function ( row ) {
                        var data = row.data();
                        return '详情 '+data[0]+' '+data[1];
                    }
                } ),
                renderer: $.fn.dataTable.Responsive.renderer.tableAll( {
                    tableClass: 'table'
                } )
            }
        },
        autoWidth: false,
        searching: false,
        "processing": true, // 打开数据加载时的等待效果
        "serverSide": true,// 打开后台分页
        "aaSorting": [[0, 'desc']],// 排序
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
            {"data": "classifyId"},
            {"data": "classifyName"},
            {"data": null}
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

        }
    } );
} );