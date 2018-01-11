var serviceUserApp = angular.module('serviceUserApp', []);

serviceUserApp.directive('workProgram', function () {
    return {
        restrict: 'E',
        templateUrl: '/static/ngdirectives/workProgram.html',
        replace: true,
//        transclude: true,
        scope: {
            program: '='
        }
    };
});
serviceUserApp.directive("calendar", function () {
    return {
        restrict: "E",
        templateUrl: '/static/ngdirectives/calendar1.html',
        scope: {
            selected: "="
        },
        link: function (scope) {

            scope.selected = moment();//_removeTime(scope.selected || mom);
            scope.month = scope.selected.clone();
            var start = scope.selected.clone();

            start.date(1);
            _removeTime(start.day(0));
            _buildMonth(scope, start, scope.month);

            scope.select = function (day) {
                scope.selected = day.date;
                setCalendar(day.date); /// defined in servUserCalendarCtrl $window.setCalendar = $scope.setCalendar = 
            };

            scope.next = function () {
                var next = scope.month.clone();
                _removeTime(next.month(next.month() + 1).date(1));
                scope.month.month(scope.month.month() + 1);
                _buildMonth(scope, next, scope.month);
            };
            scope.previous = function () {
                var previous = scope.month.clone();
                _removeTime(previous.month(previous.month() - 1).date(1));
                scope.month.month(scope.month.month() - 1);
                _buildMonth(scope, previous, scope.month);
            };
        }
    };
    function _removeTime(date) {
        return date.day(0).hour(0).minute(0).second(0).millisecond(0);
    }

    function _buildMonth(scope, start, month) {
        scope.weeks = [];
        var done = false, date = start.clone(), monthIndex = date.month(), count = 0;
        while (!done) {
            scope.weeks.push({days: _buildWeek(date.clone(), month)});
            date.add(1, "w");
            done = count++ > 2 && monthIndex !== date.month();
            monthIndex = date.month();
        }
    }

    function _buildWeek(date, month) {
        var days = [];
        for (var i = 0; i < 7; i++) {
            days.push({
//                name: date.format("dd").substring(0, 1),
                number: date.date(),
                isCurrentMonth: date.month() === month.month(),
                isToday: date.isSame(new Date(), "day"),
                date: date
            });
            date = date.clone();
            date.add(1, "d");
        }
        return days;
    }
});
serviceUserApp.controller('servUserCalendarCtrl', ['$scope', '$http', '$window',
    function ($scope, $http, $window) {
        $scope.workProgramObj;
        $window.setCalendar = $scope.setCalendar = function (day) {
            $http.get('/medic/calendar/' + day)
                    .then(function (response) {
//                    $scope.calendarObj = response;//obj, "status":200,"config":{"method":"GET","transformRequest":[null],"transformResponse":[null],"jsonpCallbackParam":"callback","url":"/medic/calendar","headers":{"Accept":"application/json, text/plain, */*","X-XSRF-TOKEN":"25680870-f51a-4d1d-af5c-4563987e0eb1"}},"statusText":"","xhrStatus":"complete"}
                        $scope.workProgramObj = response.data;
                    },
                            function () {
                                alert("loadCalendar ERROR");
                            }
                    );
        };
        $scope.setCalendar(new Date().getTime());


        $window.dragLeave = function (ev) {
            if (ev !== undefined) {
                if (ev.target.className === "drag-drop-container") {
                    ev.target.style.border = "";
                }
                ev.target.style.backgroundColor = "";
            }
        };

        var isEmpty = false;
        var draggedId = "";

        $window.onDragEnter = function (ev) {
            if (ev.target.id.startsWith("dragDiv")) {
                isEmpty = true;
                if (ev.target.className === "drag-drop-container") {
                    ev.target.style.border = "3px dotted red";
                }
                ev.target.style.backgroundColor = "";

            }
            else if (ev.target.id.startsWith("dragE")) {
                if (draggedId !== ev.target.id) {
                    ev.target.style.backgroundColor = "red";
                }
                isEmpty = false;
            }
        };

        $window.allowDrop = function (ev) {

            ev.preventDefault();
        };

        $window.drag = function (ev) {
            ev.dataTransfer.setData("Text",
                    (draggedId = ev.target.id));
        };

        $window.drop = function (ev) {
            var data = ev.dataTransfer.getData("Text");
            while (ev.target.firstChild) {
                ev.target.removeChild(ev.target.firstChild);

            }
            if (isEmpty) {
                ev.target.appendChild(document.getElementById(data));
                allowDrop(ev);
                dragLeave(ev);
            }
            else {
//           
                ev.target.style.backgroundColor = "";
            }
        };



    }]);
