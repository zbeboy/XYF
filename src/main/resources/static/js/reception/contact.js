$(document).ready(function () {
    /*
   参数
    */
    var param = {
        customerName: '',
        customerContact: '',
        content: ''
    };

    /*
     ajax url
    */
    var ajax_url = {
        save: '/data/contact/save'
    };

    /*
    参数id
    */
    var paramId = {
        customerName: '#customerName',
        customerContact: '#customerContact',
        content: '#content'
    };

    /*
    初始化参数
    */
    function initParam() {
        param.customerName = $(paramId.customerName).val();
        param.customerContact = $(paramId.customerContact).val();
        param.content = $(paramId.content).val();
    }

    /*
    错误消息id
    */
    var errorMsgId = {
        customerName: '#customer_name_error_msg',
        customerContact: '#customer_contact_error_msg',
        content: '#content_error_msg'
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

    /*
    保存数据
    */
    $('#send').click(function () {
        initParam();
        validCustomerName();
    });

    /**
     * 添加时检验并提交数据
     */
    function validCustomerName() {
        var customerName = param.customerName;
        if (!_.isEmpty(customerName) && _.inRange(customerName.length, 1, 20)) {
            validSuccessDom(paramId.customerName, errorMsgId.customerName);
            validCustomerContact();
        } else {
            validErrorDom(paramId.customerName, errorMsgId.customerName, "联系人姓名为1~20个字符")
        }
    }

    function validCustomerContact() {
        var customerContact = param.customerContact;
        if (!_.isEmpty(customerContact) && _.inRange(customerContact.length, 1, 100)) {
            validSuccessDom(paramId.customerContact, errorMsgId.customerContact);
            validContent();
        } else {
            validErrorDom(paramId.customerContact, errorMsgId.customerContact, "联系方式为1~100个字符")
        }
    }

    function validContent() {
        var content = param.content;
        if (!_.isEmpty(content) && _.inRange(content.length, 1, 500)) {
            validSuccessDom(paramId.content, errorMsgId.content);
            sendAjax();
        } else {
            validErrorDom(paramId.content, errorMsgId.content, "内容为1~500个字符")
        }
    }

    function sendAjax() {
        $.get(web_path + ajax_url.save, $('#contactForm').serialize(), function (data) {
            if (data.state) {
                $('#msg').addClass('text-success').text("您的问题已反馈成功，稍后我们会处理并联系您，请您耐心等候！");
                $('#contactForm').hide();
                $('footer').css('position', 'absolute');
            } else {
                $('#msg').addClass('text-danger').text(data.msg);
                $('footer').css('position', 'inherit');
            }
        });
    }

});