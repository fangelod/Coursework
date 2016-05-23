/* This JavaScript file was last updated by Franz Dominno on 25 Apr 2015 */
document.addEventListener('DOMContentLoaded',domloaded,false);
function domloaded() {
    var canvas = document.getElementById("c");
    var r = canvas.getContext("2d");
    r.fillStyle = "Red";
    r.fillRect(0,0,600,100);
    
    r.fillStyle = "White";
    r.fillRect(0,100,600,100);
    
    r.fillStyle = "Red";
    r.fillRect(0,200,600,100);
}

function fact() {
    var arr = ["Austria has a population of about 8.5 million people", "Austria was founded in 1804", "Austria spans 83,879 square kilometers or 32,385.86 square miles", "Austria is ranked 28 in the world in terms of GDP", "The official language in Austria is German"];
    var num = Math.floor(Math.random() * (4 - 0 + 1)) + 0;
    document.getElementById("inform").innerHTML='<p>'+arr[num]+'</p>';
}

