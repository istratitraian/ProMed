
function loginTest() {

    var CSRF_HEADER = 'X-XSRF-TOKEN';
//
//    var setCSRFToken = function (securityToken) {
//        jQuery.ajaxPrefilter(function (options, _, xhr) {
//            if (!xhr.crossDomain)
//                xhr.setRequestHeader(CSRF_HEADER, securityToken);
//        });
//    };
//
//    setCSRFToken($('meta[name="_csrf.token"]').attr('content'));

//    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

//
//    $(document).ajaxSend(function (e, xhr, options) {
//        xhr.setRequestHeader(header, token);
//    });
//
//
//    function getCookie(name) {
//        var cookieValue = null;
//        if (document.cookie && document.cookie !== '') {
//            var cookies = document.cookie.split(';');
//            for (var i = 0; i < cookies.length; i++) {
//                var cookie = jQuery.trim(cookies[i]);
//                // Does this cookie string begin with the name we want?
//                if (cookie.substring(0, name.length + 1) === (name + '=')) {
//                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
//                    break;
//                }
//            }
//        }
//        return cookieValue;
//    }
//
//    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
//        if (options['type'].toLowerCase() === "post") {
//            jqXHR.setRequestHeader('X-XSRF-TOKEN', getCookie('_csrf'));
//        }
//    });




    var csrf_token = $('meta[name="_csrf"]').attr('content');
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
        if (options.type.toLowerCase() === "post") {
            options.data = options.data || "";
            options.data += options.data ? "&" : "";
            options.data += "_csrf=" + encodeURIComponent(csrf_token);
        }
    });


    $.post('http://localhost:8080/login', {username: 'medic_11', password: 'pass'}, function (response) {
        console.log("login post SUCCESS :  csrf = " + csrf_token +  ", header = " + header);
         location.reload();
    });




// this works TO
//    var request = $.ajax({
//        url: 'http://localhost:8080/login',
//        method: "POST",
//        data: {username: 'medic_11',
//            password: 'pass'
//
//        },
//        headers:
//                {
//                    'X-XSRF-TOKEN': $('meta[name="_csrf.token"]').attr('content')
//                },
//        datatype: "json"
//    });
//
//    request.done(function (msg) {
//        console.log("request.done : " + msg);
//    });
//
//    request.fail(function (jqXHR, textStatus) {
//        console.warn("Request failed: " + textStatus);
//    });


}
;


$("#loginTestId").click(loginTest);
$("#logioutTestId").click(function () {
    $.get('http://localhost:8080/logout', function (response) {
        console.log("logut get success : ");
         location.reload();
    });
});
