/* This CSS file was last updated by Franz Dominno on 26 Mar 2015 */
function where(x) {
    if (x == "Bikolano" || x == "Tagalog" || x == "Ilocano") {
        console.log(x + " is used in Luzon");
        alert(x + " is used in Luzon");
    } else if (x == "Waray") {
        console.log(x + " is used in Visayas");
        alert(x + " is used in Visayas");
    } else if (x == "Tausug" || x == "Bisaya/Binisaya") {
        console.log(x + " is used in Mindanao");
        alert(x + " is used in Mindanao");
    } else if (x == "Hiligaynon/Ilonggo" || x == "Cebuano") {
        console.log(x + " is used in Visayas and Mindanao");
        alert(x + " is used in Visayas and Mindanao");
    }
}