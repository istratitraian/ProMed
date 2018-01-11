var CALENDAR = function () {
    var wrap, label, labelOut = {}, yearT = {}, monthT = {},
            months = [
                'Ianuarie', 'Februarie', 'Martie',
                'Aprilie', 'Mai', 'Iunie', 'Iulie',
                'August', 'Septembrie', 'Octombrie',
                'Noembrie', 'Decembrie'
//			"January", "February", "March",
//			"April", "May", "June", "July", 
//			"August", "September", "October",
//			"November", "December"
            ];

    function init(newWrap) {
        wrap = $(newWrap || "#cal");
        label = wrap.find("#label");
        wrap.find("#prev").bind("click.calendar", function () {
            switchMonth(false);
        });
        wrap.find("#next").bind("click.calendar", function () {
            switchMonth(true);
        });
        label.bind("click", function () {
            switchMonth(null,
                    new Date().getMonth(),
                    new Date().getFullYear());
        });
        label.click();
    }

    function switchMonth(next, month, year) {

        var curr = label.text().trim().split(" "),
                calendar, tempYear = parseInt(curr[1], 10);


        if (next !== null) {
            if (next) {
                if (curr[0] === "Decembrie") {
                    month = 0;
                    year = tempYear + 1;
                } else {
                    month = months.indexOf(curr[0]) + 1;
                    year = tempYear;
                }
            } else {
                if (curr[0] === "Ianuarie") {
                    month = 11;
                    year = tempYear - 1;
                } else {
                    month = months.indexOf(curr[0]) - 1;
                    year = tempYear;
                }
            }
        }


        calendar = createCal(year, month);

        $("#cal-frame", wrap)
                .find(".curr")
                .removeClass("curr")
                .addClass("temp")
                .end()
                .prepend(calendar.calendar())
                .find(".temp")
                .fadeOut("slow", function () {
                    $(this).remove();
                });

        $('#label').text(labelOut.text = calendar.label);


    }

    function createCal(year, month) {


        var day = 1,
                i, j, haveDays = true,
                startDay = new Date(year, month, day).getDay(),
                daysInMonths = [31,
                    (((year % 4 === 0) && (year % 100 !== 0)) || (year % 400 === 0)) ? 29 : 28,
                    31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
                calendar = [];

        if (createCal.cache[year]) {
            if (createCal.cache[year][month]) {
                return createCal.cache[year][month];
            }
        } else {
            createCal.cache[year] = {};
        }



        i = 0;
        while (haveDays) {
            calendar[i] = [];
            var td = "";
            for (j = 0; j < 7; j++) {
                if (i === 0) {
                    if (j === startDay) {
                        calendar[i][j] = day++;
                        startDay++;
                        td += "<td onClick='clickDay(" + calendar[i][j] + ")'>" +
                                calendar[i][j] + "</td>";
                    } else {
                        td += "<td></td>";
                    }
                } else if (day <= daysInMonths[month]) {
                    calendar[i][j] = day++;
                    td += "<td onClick='calendarPicker.clickDay(" + calendar[i][j] + ")'>" +
                            calendar[i][j] + "</td>";
                } else {
                    calendar[i][j] = "";
                    haveDays = false;
                    td += "<td></td>";
                }
                if (day > daysInMonths[month]) {
                    haveDays = false;
                }
            }
            calendar[i] = "<tr>" + td + "</tr>";
            i++;
        }



        calendar = $("<table>" + calendar.join("") + "</table>").addClass("curr");

        $("td:empty", calendar).addClass("nil");
        if (month === new Date().getMonth()) {
            $('td', calendar).filter(function () {
                return $(this).text() === new Date().getDate().toString();
            }).addClass("today");
        }


        yearT.text = year;
        monthT.text = months[month];

        createCal.cache[year][month] = {
            calendar: function () {
                return calendar.clone();
            },
            label: months[month] + " " + year
        };


        return createCal.cache[year][month];
    }


    function clickDay(day) {
        $('#selectedDate').text(labelOut.text = (day + " " + labelOut.text));
        console.log(day + "/" + monthT.text + "/" + yearT.text);
    }



    createCal.cache = {};
    return {
        init: init,
        switchMonth: switchMonth,
        createCal: createCal,
        clickDay: clickDay,
        year: yearT,
        month: monthT,
        label: labelOut
    };

};
