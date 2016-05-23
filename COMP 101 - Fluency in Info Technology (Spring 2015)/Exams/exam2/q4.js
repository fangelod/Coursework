function whatyear(gender, year) {
    var subject;
    var rank;
    var statement;
    if (gender == "M") {
        subject = "He";
    } else if (gender == "F") {
        subject = "She";
    }
    
    if (year == 1) {
        rank = "freshman.";
    } else if (year == 2) {
        rank = "sophomore.";
    } else if (year == 3) {
        rank = "junior.";
    } else if (year == 4) {
        rank = "senior.";
    }
    
    statement = subject + " is a " + rank;
    return statement;
}