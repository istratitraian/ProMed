
$.ajaxSetup({headers: {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')}});



//$('#clientCalendar').on('shown.bs.modal', function () {
//    $('#calendar').fullCalendar('render');
//    $('#calendar').fullCalendar('removeEvents');
//    $('#calendar').fullCalendar('refetchEvents');
//});

function clientCalendar(id) {

    $('#clientCalendar').show();
    var CAL = $('#calendar');
//    CAL.fullCalendar( 'destroy' );// destroy calendar
    CAL.fullCalendar('removeEventSources');
//    CAL.fullCalendar( 'unselect' );
//    $('#calendar').fullCalendar('option', 'scrollTime', new Date());
    CAL.fullCalendar('addEventSource', '/server/calendar/jsonclient/' + id);
    CAL.fullCalendar('render');
//    CAL.focus();

    console.log("clientCalnedar(" + id + ")");

}

$(document).ready(function () {

    var isMontSelected = false;

    $('#calendar').fullCalendar({

//        buttonIcons: {
//            prev: 'circle-triangle-w',
//            next: 'circle-triangle-e'
//        },

//        events: '/server/calendar/jsonclient/' + serverId, // /serverId
//        events: function (start, end, timezone, callback) {
//            $("#calendar").fullCalendar('removeEvents');
////            $("#calendar").fullCalendar({
////                defaultView: 'agendaWeek'
////            });
//            $.ajax({
//                url: '/server/calendar/jsonclient/' + serverId,
//                dataType: 'json',
//                data: {
//                    start: moment(start).format('YYYY-MM-DD'), //start.unix(),
//                    end: moment(end).format('YYYY-MM-DD') //end.unix()
//                },
//                success: function (doc) {
//                    var events = [];
////                        callback(events);
//
//                    for (var i in doc) {
//                        events.push({
//                            title: doc[i].title,
//                            start: doc[i].start,
//                            end: doc[i].end
//                        });
//                    }
//
//                    callback(events);
//                }
//            });
//        },

        eventRender: function (event, element, view) {
            if (event !== null) {
                element.css('background-color', '#090');
//                element.hide();
            }
        },

        eventClick: function (e, element) {


            var isEdited = false;
            var editBtn = $('#editBtn');
            editBtn.unbind();
            editBtn.attr('disabled', true);

            var title = $('#modalTitle');
            title.css('width', '100%').val("");//.val(event.title);
            var start = $('#modalStart');
            start.text(e.start.format('YYYY-MM-DD HH:mm'));
            var end = $('#modalEnd');
            end.text(e.end.format('YYYY-MM-DD HH:mm'));

            var firstName = $('#modalFirstName');
            firstName.css('width', '100%').val(e.firstName);
            var lastName = $('#modalLastName');
            lastName.css('width', '100%').val(e.lastName);
            var phone = $('#modalPhone');
            phone.css('width', '100%').val(e.phoneNumber);
            var email = $('#modalEmail');
            email.css('width', '100%').val(e.email);


            $('#fullCalClient').modal();


            editBtn.click(function () {
                if (isEdited) {

                    $.ajax({
                        type: 'post',
                        url: '/server/calendar/jsonclient/save/' + serverId, //'?_csrf=' + $('meta[name="_csrf"]').attr('content'),
                        data: JSON.stringify({title: title.val(),
                            start: e.start.format('YYYY-MM-DD HH:mm'), //moment(e.start).format("YYYY-MM-DD HH:mm"),
                            end: e.end.format('YYYY-MM-DD HH:mm'), //moment(e.end).format("YYYY-MM-DD HH:mm"),
                            firstName: firstName.val(),
                            lastName: lastName.val(),
                            phoneNumber: phone.val(),
                            email: email.val()
                        }),
                        contentType: "application/json",
                        dataType: 'text', //to get success use text
                        success: function () {
                            console.log(e.id + ", " + e.title + " CREATED : ");
                            $('#calendar').fullCalendar('removeEvents');
                            $('#calendar').fullCalendar('refetchEvents');
                        }
                    });

                }
            });



            function edit() {
                editBtn.attr('disabled', false);
                isEdited = true;
            }
            ;

            title.keyup(edit);
            start.keyup(edit);
            end.keyup(edit);
            firstName.keyup(edit);
            lastName.keyup(edit);
            phone.keyup(edit);
            email.keyup(edit);
            document.addEventListener('touchstart', edit, {passive: true});
            document.addEventListener('keyup', edit, {passive: true});
        },

        viewRender: function (view) {
            if (view.name === 'month') {
                isMontSelected = true;
            }
            else {
                isMontSelected = false;
            }
        },

        dayClick: function (date, mouseEvent, view) {
            if (isMontSelected) {
                $('#calendar').fullCalendar('gotoDate', date);
                $('#calendar').fullCalendar('changeView', 'agendaDay', date.format('YYYY-MM-DD'));
            }
        },
        scrollTime: new Date(),
        timeFormat: 'H(:mm)',
        allDaySlot: false,
        nowIndicator: true,
        lazyFetching: true, //default true 
        timezone: "local",
        height: 600,
//        aspectRatio: 2,
        handleWindowResize: true,
        contentHeight: 500,
        header: {
            left: 'prevYear title nextYear',
            center: 'today',
            right: 'prev,agendaDay,agendaFiveDay,agendaWeek,month,next'
        },
        footer: {
            left: '',
            center: '',
            right: ''
        },
        firstDay: 1, //Luni
        // weekends: false, // sat, sun remove
        // weekNumbers: false,
        hiddenDays: [0, 6],
        weekNumbersWithinDays: true,
        minTime: '09:00:00',
        maxTime: '18:00:00',
        businessHours: [
            {
                dow: [1, 2, 3, 4], // Mon, Tue, Wed,Thu
                start: '09:00',
                end: '18:00'
            },
            {
                dow: [5], // Friday
                start: '09:00',
                end: '16:00'
            }
        ],
        views: {
            year: {
                titleFormat: 'YYYY'
            },
            month: {// name of view
                titleFormat: 'MMMM YYYY'

            },
            day: {// name of view
                titleFormat: 'DD MMMM YYYY'
            },
            week: {// name of view
                titleFormat: 'MMMM YYYY',
                columnFormat: 'ddd D - M'
            },
            listYear: {
                titleFormat: 'YYYY'
            }
        },
        buttonText: {
            listYear: 'AN',
            listMonth: 'LUNA',
            listWeek: 'SAPTAMANA',
            agendaDay: 'Zi',
            agendaWeek: 'Saptamana',
            month: 'Luna',
            today: 'Azi'
//            prev: '&lt;',
//            next: '&gt;'
        },
        dragable: false,
        defaultDate: new Date(), //'2017-12-12',
        defaultView: "agendaWeek", //https://fullcalendar.io/docs/views/Available_Views/
        navLinks: true, // can click day/week names to navigate views
        selectable: false,
        selectHelper: false,
        editable: false,
        eventLimit: true, // allow "more" link when too many events
        slotDuration: "00:15",
        slotLabelFormat: [
            'HH:mm'
        ]
    });

});


//    $('#calendar').fullCalendar('refetchEvents');
//}