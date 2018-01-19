
/* global moment */

$(document).ready(function () {

    var isMontSelected = false;
    $.ajaxSetup({headers: {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')}});


    function saveEvent(e) {

        $.ajax({
            type: 'post',
            url: 'http://localhost:8080/server/calendar/jsonclient/save/13', //'?_csrf=' + $('meta[name="_csrf"]').attr('content'),
            data: JSON.stringify({id: e.id, title: e.title,
                start: moment(e.start).format("YYYY-MM-DD HH:mm"),
                end: moment(e.end).format("YYYY-MM-DD HH:mm"),
                firstName: e.firstName,
                lastName: e.lastName,
                phoneNumber: e.phone,
                email: e.email
            }),
            contentType: "application/json",
            dataType: 'text', //to get success use text
            success: function (result, status, xhr) {
                console.log(e.id + ", " + e.title + " CREATED : ");
                CALR.fullCalendar('updateEvent', e);
                CALR.fullCalendar('removeEvents');
                CALR.fullCalendar('refetchEvents');
            }
        });

//         location.reload();
    }



    var CALR = $('#calendar').fullCalendar({
//        dragScroll: false,
        events: 'http://localhost:8080/server/calendar/jsonclient/13', // /serverId

        eventRender: function (event, element, view) {
            if (event !== null) {
                element.css('background-color', 'red');
//                element.hide();
//                element.disable(true);
//                element.attr('disabled','true');
            }
        },

        eventClick: function (event, element) {

            var isEdited = false;
            var editBtn = $('#editBtn');
            editBtn.unbind();
            editBtn.attr('disabled', true);
            var delBtn = $('#delBtn');
            delBtn.unbind();

            var title = $('#modalTitle');
            title.css('width', '100%').val(event.title);
            var start = $('#modalStart');
            start.css('width', '100%').attr("readonly", '').val(event.start.format('YYYY-MM-DD HH:mm'));
            var end = $('#modalEnd');
            end.css('width', '100%').attr("readonly", '').val(event.end.format('YYYY-MM-DD HH:mm'));

            var firstName = $('#modalFirstName');
            firstName.css('width', '100%').val(event.firstName);
            var lastName = $('#modalLastName');
            lastName.css('width', '100%').val(event.lastName);
            var phone = $('#modalPhone');
            phone.css('width', '100%').val(event.phoneNumber);
            var email = $('#modalEmail');
            email.css('width', '100%').val(event.email);


            $('#fullCalClient').modal();

            delBtn.click(function () {
                $.ajax({
                    url: "http://localhost:8080/server/calendar/jsonrest/delete",
                    type: "delete",
                    contentType: "application/json",
                    dataType: 'text', //to get success use text
                    data: JSON.stringify({id: event.id}),
                    success: function (result, status, xhr) {
                        console.log(event.id + " DELETED : ");
                        CALR.fullCalendar('removeEvents', event.id);
                    }
                });
                isEdited = false;
            });


            editBtn.click(function () {
                if (isEdited) {
                    event.title = title.val();
                    event.start = start.val();
                    event.end = end.val();
                    event.firstName = firstName.val();
                    event.lastName = lastName.val();
                    event.phoneNumber = phone.val();
                    event.email = email.val();
                    saveEvent(event);
                    isEdited = false;
                }
            });



            function edit() {
                editBtn.attr('disabled', false);
                isEdited = true;
            };

            title.keyup(edit);
            start.keyup(edit);
            end.keyup(edit);
            firstName.keyup(edit);
            lastName.keyup(edit);
            phone.keyup(edit);
            email.keyup(edit);

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
        select: function (start, end) {
            if (!isMontSelected) {
                var title = prompt('Event Title:');
                var ev;
                if (title) {
                    ev = {
                        title: title,
                        start: start,
                        end: end
                    };
                    saveEvent(ev);
//                    CALR.fullCalendar('renderEvent', ev, false); // stick? = true
                }
                $('#calendar').fullCalendar('unselect');
            }
        },
        allDaySlot: false,
        nowIndicator: true,
        lazyFetching: true, //default true 
        timezone: "local",
        height: 900,
        handleWindowResize: true,
        //contentHeight: 500,
        header: {
            left: 'prevYear title nextYear',
            center: 'today',
            right: 'prev,agendaDay,agendaFiveDay,agendaWeek,month,next'
        },
        footer: {
            left: 'prev,today,next',
            center: 'title',
            right: 'basicWeek,basicDay,listWeek,listMonth,listYear'
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
                titleFormat: 'MMMM YYYY'
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