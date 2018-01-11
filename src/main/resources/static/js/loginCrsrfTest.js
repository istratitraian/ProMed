
function loginTest() {
    var header = $("meta[name='_csrf_header']").attr("content");
    var csrf_token = $('meta[name="_csrf"]').attr('content');
    $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
        if (options.type.toLowerCase() === "post") {
            options.data = options.data || "";
            options.data += options.data ? "&" : "";
            options.data += "_csrf=" + encodeURIComponent(csrf_token);
        }
    });

    $.post('http://localhost:8080/login', {username: 'medic_11', password: 'pass'}, function (response) {
        console.log("login post SUCCESS :  csrf = " + csrf_token + ", header = " + header);
        location.reload();
    });
};

$("#loginTestId").click(loginTest);
$("#logioutTestId").click(function () {
    $.get('http://localhost:8080/logout', function (response) {
        console.log("logut get success : ");
        location.reload();
    });
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