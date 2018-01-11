
$(document).ready(function () {

    var isMontSelected = false;
    $.ajaxSetup({headers: {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')}});


    function saveEvent(e) {

        $.ajax({
            type: 'post',
            url: 'http://localhost:8080/server/calendar/jsonrest/save', //'?_csrf=' + $('meta[name="_csrf"]').attr('content'),
            data: JSON.stringify({id: e.id, title: e.title,
                start: moment(e.start).format("YYYY-MM-DD HH:mm"),
                end: moment(e.end).format("YYYY-MM-DD HH:mm")
            }),
            contentType: "application/json",
            dataType: 'text',//to get success use text
            success: function (result, status, xhr) {
                console.log(e.title + " CREATED : ");
//                CALR.fullCalendar('removeEvents', event.id);
            }
        });
//                .done(function () {
//            console.log(e.title + " CREATED : ");
//        });

        CALR.fullCalendar('updateEvent', e);
        CALR.fullCalendar('removeEvents');
        CALR.fullCalendar('refetchEvents');
    }



    var CALR = $('#calendar').fullCalendar({
//        dragScroll: false,
        events: 'http://localhost:8080/server/calendar/jsonrest',

        eventRender: function (event, element, view) {
            if (event.allDay === 'true') {
                event.allDay = true;
//                element.css('background-color', 'red');
            }
            else {
                event.allDay = false;
            }

        },

        eventClick: function (event, element) {

            var isEdited = false;
            var editBtn = $('#editBtn');
            editBtn.attr('disabled', true);
            var delBtn = $('#delBtn');
            delBtn.unbind();
            editBtn.unbind();

            var title = $('#modalTitle');
            title.css('width', '100%').val(event.title);
            var start = $('#modalStart');
            start.css('width', '100%').val(event.start.format('YYYY-MM-DD HH:mm'));
            var end = $('#modalEnd');
            end.css('width', '100%').val(event.end.format('YYYY-MM-DD HH:mm'));


            $('#fullCalModal').modal();

            delBtn.click(function () {
                $.ajax({
                    url: "http://localhost:8080/server/calendar/jsonrest/delete",
                    type: "delete",
                    contentType: "application/json",
                    dataType: 'text',//to get success use text
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
                    saveEvent(event);
                    isEdited = false;
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

//            $("#calendar").fullCalendar('updateEvent', event);
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
//                $('#calendar').fullCalendar('changeView', 'agendaDay', moment(date, 'YYYY-MM-DD').format());
//                $('#calendar').fullCalendar('changeView', 'agendaDay', moment(time, 'YYYY-MM-DD', true).format());
            }
//                $.getJSON('http://localhost:8080/server/calendar/jsonrest/' + moment(time, 'MM/DD/YYYY', true).format(), function (data) {
//                    this.events = data;
////                    $('#calendar').fullCalendar('removeEvents');
////                    $('#calendar').fullCalendar('renderEvents', this.events, true);
//                });

        },

        eventResize: function (event, delta, revertFunc) {
            if (confirm("id = " + event.id + " resize MDiF!")) {
                saveEvent(event);
            }
            else
            {
                revertFunc();
            }
        },
        eventDrop: function (event, delta, revertFunc) {
            if (confirm("id = " + event.id + " drop MDiF!")) {
                saveEvent(event);
            }
            else
            {
                revertFunc();
            }
        },
        eventOverlap: function (stillEvent, movingEvent) {
            return stillEvent.allDay && movingEvent.allDay;
        }
        ,
//        select: function(start, end) {
//            $.getScript('/events/new', function () {
//                $('#event_date_range').val(moment(start).format("MM/DD/YYYY HH:mm") + ' - ' + moment(end).format("MM/DD/YYYY HH:mm"))
//                date_range_picker();
//                $('.start_hidden').val(moment(start).format('YYYY-MM-DD HH:mm'));
//                $('.end_hidden').val(moment(end).format('YYYY-MM-DD HH:mm'));
//            });
//            $('.modal').modal('hide');
//            CALR.fullCalendar('unselect');
//        },
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
        hiddenDays: [0],
        weekNumbersWithinDays: true,
        minTime: '08:00:00',
        maxTime: '21:00:00',
        businessHours: [
            {
                dow: [1, 2, 3, 4], // Mon, Tue, Wed,Thu
                start: '08:00',
                end: '18:00'
            },
            {
                dow: [5, 6], // Friday
                start: '08:00',
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
        defaultDate: new Date(), //'2017-12-12',
        defaultView: "agendaDay", //https://fullcalendar.io/docs/views/Available_Views/
        navLinks: true, // can click day/week names to navigate views
        selectable: true,
        selectHelper: true,
        editable: true,
        eventLimit: true, // allow "more" link when too many events
        slotDuration: "00:15",
        slotLabelFormat: [
            'HH:mm'
        ]
    });

});