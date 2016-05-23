/* This JavaScript file was last updated by Franz Dominno on 14 Apr 2015 */
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