/* This JavaScript file was last updated by Franz Dominno on 21 Apr 2015 */
/*
function choose() {
    var arr = [1, 2, 3, 4];
    var num = Math.floor(Math.random() * (4 - 1 + 1)) + 1;
    if (num == 1) {
        return src="Muppets/kermit.jpg";
    } else if (num == 2) {
        return src="Muppets/misspiggy.jpg"
    } else if (num == 3) {
        return src="Muppets/oscar.jpg"
    } else if (num == 4) {
        return src="Muppets/cookiemonster.jpg"
    } else {
        return src="Muppets/planet.jpg"
    }
}
*/

/*
function pickGuide() {
    var arr = [1, 2, 3, 4];
    var num = Math.floor(Math.random() * (4 - 1 + 1)) + 1;
    if (num == 1) {
        document.getElementById("guidehere").innerHTML='<img class="yourguide" src="Muppets/kermit.jpg" alt="guide">';
        document.getElementById("say").innerHTML='<p>Your guide is Kermit</p>';
    } else if (num == 2) {
        document.getElementById("picked").innerHTML='<img class="yourguide" src="Muppets/misspiggy.jpg" alt="guide">';
        document.getElementById("say").innerHTML='<p>Your guide is Miss Piggy</p>';
    } else if (num == 3) {
        document.getElementById("picked").innerHTML='<img class="yourguide" src="Muppets/oscar.jpg" alt="guide">';
        document.getElementById("say").innerHTML='<p>Your guide is Oscar</p>';
    } else if (num == 4) {
        document.getElementById("picked").innerHTML='<img class="yourguide" src="Muppets/cookiemonster.jpg" alt="guide">';
        document.getElementById("say").innerHTML='<p>Your guide is Cookie Monster</p>';
    } else {
        document.getElementById("picked").innerHTML='<img src="Muppets/planet.jpg" alt="guide">';
    }
}
*/

function choose() {
    var arr = [1, 2, 3, 4];
    /*var pics;
    pics[0] = "Muppets/kermit.jpg";
    pics[1] = "Muppets/misspiggy.jpg";
    pics[2] = "Muppets/oscar.jpg";
    pics[3] = "Muppets/cookiemonster.jpg";
    var message;
    message[0] = "Your guide is Kermit";
    message[1] = "Your guide is Miss Piggy";
    message[2] = "Your guide is Oscar";
    message[3] = "Your guide is Cookie Monster";
    */
    var num = Math.floor(Math.random() * (4 - 1 + 1)) + 1;
    if (num == 1) {
        document.getElementById("guidehere").innerHTML='<div><img class="yourguide" src="Muppets/kermit.jpg" alt="guide"><p>Your guide is Kermit</p></div>';
        //document.getElementById("guidehere").innerHTML='<div><img src=pics[0] alt="guide"><p>message[0]</p></div>';
    } else if (num == 2) {
        document.getElementById("guidehere").innerHTML='<div><img class="yourguide" src="Muppets/misspiggy.jpg" alt="guide"><p>Your guide is Miss Piggy</p></div>';
        //document.getElementById("guidehere").innerHTML='<div><img src=pics[1] alt="guide"><p>message[1]</p></div>';
    } else if (num == 3) {
        document.getElementById("guidehere").innerHTML='<div><img class="yourguide" src="Muppets/oscar.jpg" alt="guide"><p>Your guide is Oscar</p></div>';
        //document.getElementById("guidehere").innerHTML='<div><img src=pics[2] alt="guide"><p>message[2]</p></div>';
    } else if (num == 4) {
        document.getElementById("guidehere").innerHTML='<div><img class="yourguide" src="Muppets/cookiemonster.jpg" alt="guide"><p>Your guide is Cookie Monster</p></div>';
        //document.getElementById("guidehere").innerHTML='<div><img src=pics[3] alt="guide"><p>message[3]</p></div>';
    }
}
