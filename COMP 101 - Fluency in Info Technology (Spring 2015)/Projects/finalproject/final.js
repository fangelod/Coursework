/* This JavaScript file was last updated by Franz Dominno on 30 Apr 2015 */
var i = 0;
var labels = ["Journey Into Asia (2010)", "Journey Into Asia (2011)", "Journey Into Asia (2012)", "Journey Into Asia (2013)", "Journey Into Asia (2014)"];
var videos = ["https://www.youtube.com/embed/ng7Gw-P0e44", "https://www.youtube.com/embed/NytO82OB2xo", "https://www.youtube.com/embed/9zwfugXsctw", "https://www.youtube.com/embed/EiYXJcwLAdQ", "https://www.youtube.com/embed/XFJVKBEINRo"];
function roll(way) {
    if (i + way >= 0) {
        i = (i + way) % 5;
    }
    else {
        i = (i + way + 5) % 5;
    }
    document.getElementById("perf").src=videos[i];
    document.getElementById("perflabel").innerHTML='<h3>'+labels[i]+'</h3>';
}