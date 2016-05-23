/* This JavaScript file was last updated by Franz Dominno on 7 Apr 2015 */
function calc(h, w, s) {
    var result = 0;
    if (s == "M") {
        result = (w/(h*h))
    } else if (s == "E") {
        result = ((w/(h*h))*703)
    }
    
    if (result != 0) {
        bmi.result.value = result;
    } else {
        alert('Please review your input. You may not have chosen which system you are using.')
    }
    
}