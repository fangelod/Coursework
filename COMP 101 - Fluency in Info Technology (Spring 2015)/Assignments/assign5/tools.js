/* This JavaScript file was last updated by Franz Dominno on 18 Apr 2015 */
function concat(e) {
    math.field.value = math.field.value + e;
}

function clear() {
    math.field.value = "";
}

function showMonth() {
    var d = new Date();
    var month = d.getMonth();
    
    switch (month) {
        case 0:
            month = "January";
            break;
        case 1:
            month = "February";
            break;
        case 2:
            month = "March";
            break;
        case 3:
            month = "April";
            break;
        case 4:
            month = "May";
            break;
        case 5:
            month = "June";
            break;
        case 6:
            month = "July";
            break;
        case 7:
            month = "August";
            break;
        case 8:
            month = "September";
            break;
        case 9:
            month = "October";
            break;
        case 10:
            month = "November";
            break;
        case 11:
            month = "December";
            break;
    }
    
    return "The month is " + month;
}

function showDay(args) {
    var d = new Date();
    var day = d.getDay();
    
    switch(day) {
        case 0:
            day = "Sunday";
            break;
        case 1:
            day = "Monday";
            break;
        case 2:
            day = "Tuesday";
            break;
        case 3:
            day = "Wednesday";
            break;
        case 4:
            day = "Thursday";
            break;
        case 5:
            day = "Friday";
            break;
        case 6:
            day = "Saturday";
            break;
    }
    
    return "The day is " + day;
}

function showYear(args) {
    var d = new Date();
    var year = d.getFullYear();
    
    return "The year is " + year;
}

function showTime(m) {
    var d = new Date();
    var hours = d.getHours();
    var minutes = d.getMinutes();
    var noon;
    var time;
    
    if (minutes < 10) {
        minutes += "0";
    }
    
    time = hours + ":" + minutes;
    
    if (m == 12) {
        if (hours > 12) {
            hours -= 12;
            noon = " PM";
        } else if (hours == 0) {
            hours = 12;
            noon = " AM";
        } else if (hours == 12) {
            noon = " PM";
        } else {
            noon = " AM"
        }
        
        time = hours + ":" + minutes + noon;
    }
    
    return "The time is " + time;
}