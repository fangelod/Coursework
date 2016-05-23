var grid_size = 20;
var once_alive = [];
var speed = 0;
var game = setInterval(main_loop, speed);
var stopped = true;
var outer_referencing = 0;
var r = 1;
var l = 2;
var o = 3;
var gmin = 3;
var gmax = 3;

$(document).ready(function () {
    $('#grid').height($('#grid').width());
    
    // Start button
    $("#start").on('click', function (e) {
        e.preventDefault();
        
        game = setInterval(main_loop, speed);
        stopped = false;
    });
    
    // Stop button
    $("#stop").on('click', function (e) {
        e.preventDefault();
        
        clearInterval(game);
        stopped = true;
    });

    // Step button
    $("#step").on('click', function (e) {
        e.preventDefault();
        
        if (stopped) {
            main_loop();
        }
    });
    
    // Randomize button
    $("#randomize").on('click', function (e) {
        e.preventDefault();
        
        $("td").css("background", "white");
        once_alive = [];
        
        // How many cells are to be alive
        var random_amount = randomInt(1,(grid_size * grid_size));
        var random_list = [];
        
        for (i = 0; i < random_amount; i++) {
            var random_row = randomInt(1, grid_size);
            var random_col = randomInt(1, grid_size);
            var random_id = '#R';
            
            if (random_row < 10 && random_col < 10) {
                random_id += '00' + random_row + 'C00' + random_col;
            } else if (random_row < 10 && random_col > 9) {
                if (random_col < 100) {
                    random_id += '00' + random_row + 'C0' + random_col;
                } else {
                    random_id += '00' + random_row + 'C' + random_col;
                }
            } else if (random_row > 9 && random_col < 10) {
                if (random_row < 100) {
                    random_id += '0' + random_row + 'C00' + random_col;
                } else {
                    random_id += random_row + 'C00' + random_col;
                }
            } else if (random_row > 9 && random_col > 9) {
                if (random_row < 100 && random_col < 100) {
                    random_id += '0' + random_row + 'C0' + random_col;
                } else if (random_row < 100 && random_col > 99) {
                    random_id += '0' + random_row + 'C' + random_col;
                } else if (random_row > 99 && random_col < 100) {
                    random_id += random_row + 'C0' + random_col;
                } else if (random_row > 99 && random_col > 99) {
                    random_id += random_row + 'C' + random_col;
                }
            }
            //random_list.push(random_id);
            $(random_id).css("background", "rgb(153, 186, 221)");
            once_alive.push(random_id);
        }
    });

    // Reset button
    $("#reset").on('click', function (e) {
        e.preventDefault();
        
        // Change all cells to white
        $("td").css("background", "white");
        
        // Remove all cells from once_alive array
        once_alive = [];
    });
    
    // Update button
    $("#setting").on('click', function(e) {
        e.preventDefault();
        
        // Change grid size
        grid_size = $("#size").val();
        
        if (grid_size >= 20 && grid_size <= 200) {
            $("#grid").replaceWith(makeNewTable());
            $('#grid').css("width", "50%");
            $('#grid').height($('#grid').width());
            
            // Reset once_alive array
            once_alive = [];
        }
        
        // Change speed
        $("#insert").replaceWith('<label id="insert">' + $("#speed").val() +'</label>');
        speed = $("#speed").val();
            
        // Change neighbor reference
        //alert($("#reference").val());
        if ($("#reference").val() == "Always Dead") {
            outer_referencing = 0;
        } else if ($("#reference").val() == "Always Alive") {
            outer_referencing = 1;
        } else if ($("#reference").val() == "Toroidal"){
            outer_referencing = 2;
        }
        
        // Parameter Check
        var error_occured = false;
        var error_message = "Invalid Parameters!\n";
        var set_max = 440;
        
        // r check
        if ($("#r").val() < 1) {
            error_occured = true;
            error_message += "r must be >= 1\n";
        } else if ($("#r").val() > 10) {
            error_occured = true;
            error_message += "r must be <= 10\n";
        } else {
            r = $("#r").val();
            set_max = (4*r*r + 4*r);
        }
        
        // l check
        if ($("#l").val() <= 0) {
            error_occured = true;
            error_message += "l must be > 0\n";
        } else if ($("#l").val() > $("#o").val()) {
            error_occured = true;
            error_message += "l must be <= o\n";
        } else if ($("#l").val() >= set_max) {
            error_occured = true;
            error_message += "l must be < " + set_max + "\n";
        }
        
        // o check
        if ($("#o").val() <= 0) {
            error_occured = true;
            error_message += "o must be > 0\n";
        } else if ($("#o").val() >= set_max) {
            error_occured = true;
            error_message += "o must be < " + set_max + "\n";
        }
        
        // gmin check
        if ($("#gmin").val() <= 0) {
            error_occured = true;
            error_message += "gmin must be > 0\n";
        } else if ($("#gmin").val() > $("#gmax").val()) {
            error_occured = true;
            error_message += "gmin must be <= gmax\n";
        } else if ($("#gmin").val() >= set_max) {
            error_occured = true;
            error_message += "gmin must be < " + set_max + "\n";
        }
        
        // gmax check
        if ($("#gmax").val() <= 0) {
            error_occured = true;
            error_message += "gmax must be > 0\n";
        } else if ($("#gmax").val() >= set_max) {
            error_occured = true;
            error_message += "gmax must be < " + set_max + "\n";
        }
        
        if (error_occured) {
            r = 1;
            l = 2;
            o = 3;
            gmin = 3;
            gmax = 3;
            
            error_message += "\nDefault parameters used!";
            alert(error_message);
        } else {
            l = $("#l").val();
            o = $("#o").val();
            gmin = $("#gmin").val();
            gmax = $("#gmax").val();
        }
    });
    
    // Clicking on cells
    $("#grid_holder").on('click', "td", function(e) {
        e.preventDefault();
        
        var was_alive = false;
        var id_clicked = $(this).attr('id');
        
        for (i = 0; i < once_alive.length; i++) {
            if (id_clicked == once_alive[i]) {
                was_alive = true;
            }
        }
        
        if (e.shiftKey) {
            // Force alive
            $(this).css("background", "rgb(153,186,221)");
            
            if (!was_alive) {
                once_alive.push(id_clicked);
            }
        } else if (e.ctrlKey || e.altKey) {
            // Force dead
                // if currently carolina blue force to gray
                // if currently gray, stay as gray
                // if currently white, stay as white
            if ($(this).css("background").indexOf('rgb(153, 186, 221)') != -1) {
                $(this).css("background", "gray");
            }
        } else {
            if (was_alive) {
                if ($(this).css("background").indexOf('rgb(153, 186, 221)') != -1) {
                    // If background is carolina blue, change to gray
                    $(this).css("background", "gray");
                } else {
                    // Change background to carolina blue
                    $(this).css("background", "rgb(153,186,221)");
                }
            } else {
                if ($(this).css("background").indexOf('rgb(153, 186, 221)') != -1) {
                    $(this).css("background", "gray");
                } else {
                    $(this).css("background", "rgb(153,186,221)");
                    // Add the id of the cell clicked to the array of cells that were once alive
                    once_alive.push(id_clicked);
                }
            }
        }
    });
});


$(window).resize(function() {
    $('#grid').height($('#grid').width());
});


// Helper functions
function makeNewTable() {
    grid_size = $('#size').val();
    
    var rows_array = new Array(grid_size);
    
    for (i = 0; i < grid_size; i++) {
        var row = "<tr>";
        
        for (j = 0; j < grid_size; j++) {
            if ((i+1) < 10 && (j+1) < 10) {
                row += '<td id="R00' + (i+1) + 'C00' + (j+1) + '"><div></div></td>';
            } else if ((i+1) < 10 && (j+1) > 9) {
                if ((j+1) < 100) {
                    row += '<td id="R00' + (i+1) + 'C0' + (j+1) + '"><div></div></td>';
                } else {
                    row += '<td id="R00' + (i+1) + 'C' + (j+1) + '"><div></div></td>';
                }
            } else if ((i+1) > 9 && (j+1) < 10) {
                if ((i+1) < 100) {
                    row += '<td id="R0' + (i+1) + 'C00' + (j+1) + '"><div></div></td>';
                } else {
                    row += '<td id="R' + (i+1) + 'C00' + (j+1) + '"><div></div></td>';
                }
            } else if ((i+1) > 9 && (j+1) > 9) {
                if ((i+1) < 100 && (j+1) < 100) {
                    row += '<td id="R0' + (i+1) + 'C0' + (j+1) + '"><div></div></td>';
                } else if ((i+1) < 100 && (j+1) > 99) {
                    row += '<td id="R0' + (i+1) + 'C' + (j+1) + '"><div></div></td>';
                } else if ((i+1) > 99 && (j+1) < 100) {
                    row += '<td id="R' + (i+1) + 'C0' + (j+1) + '"><div></div></td>';
                } else if ((i+1) > 99 && (j+1) > 99) {
                    row += '<td id="R' + (i+1) + 'C' + (j+1) + '"><div></div></td>';
                }
            }
        }
        
        if (i == (grid_size - 1)) {
            row += "</tr>";
        }
        rows_array.push(row);
    }
    
    var fixTable = '<table id="grid">';
    for (i = 0; i < grid_size; i++) {
        fixTable += rows_array[i];
        if (i == (grid_size - 1)) {
            fixTable += "</table>"
        }
    }
    
    return fixTable;
}

function randomInt(min, max) {
    return Math.floor(Math.random()*(max-min+1)+min);
}

var main_loop = function() {
    var curr_alive = [];
    var curr_dead = [];
    
    
    // Find which cells are currently alive
    for (i = 1; i < grid_size + 1; i++) {
        for (j = 1; j < grid_size + 1; j++) {
            var cell = '#R';
            
            if (i < 10 && j < 10) {
                cell += '00' + i + 'C00' + j;
            } else if (i < 10 && j > 9) {
                if (j < 100) {
                    cell += '00' + i + 'C0' + j;
                } else {
                    cell += '00' + i + 'C' + j;
                }
            } else if (i > 9 && j < 10) {
                if (i < 100) {
                    cell += '0' + i + 'C00' + j;
                } else {
                    cell += i + 'C00' + j;
                }
            } else if (i > 9 && j > 9) {
                if (i < 100 && j < 100) {
                    cell += '0' + i + 'C0' + j;
                } else if (i < 100 && j > 99) {
                    cell += '0' + i + 'C' + j;
                } else if (i > 99 && j < 100) {
                    cell += i + 'C0' + j;
                } else if (i > 99 && j > 99) {
                    cell += i + 'C' + j;
                }
            }
            
            if ($(cell).css("background").indexOf('rgb(153, 186, 221)') != -1) {
                //alert($(cell).attr('id') + ", " + $(cell).index());
                curr_alive.push($("#grid_holder").find(cell).attr('id'));
            } else {
                curr_dead.push($("#grid_holder").find(cell).attr('id'));
            }
        }
    }
    
    // Determine how many neighbors each alive cell has
    var alive_num_neighbors = new Array(curr_alive.length);
    for (i = 0; i < curr_alive.length; i++) {
        var a = curr_alive[i].substring(1, 4);
        var b = curr_alive[i].substring(5, 8);
        var num = 0;
        
        for (j = parseInt(a) - r; j < parseInt(a) + r; j++) {
            for (k = parseInt(b) - r; k < parseInt(b) + r; k++) {
                var cell = '#R';
                
                if (outer_referencing == 0) {
                    // Always Dead
                    if (j < 1 || j > grid_size) {
                        // ignore
                    } else if (k < 1 || k > grid_size) {
                        // ignore
                    } else {
                        // normal
                        if (j < 10 && k < 10) {
                            cell += '00' + j + 'C00' + k;
                        } else if (j < 10 && k > 9) {
                            if (k < 100) {
                                cell += '00' + j + 'C0' + k;
                            } else {
                                cell += '00' + j + 'C' + k;
                            }
                        } else if (j > 9 && k < 10) {
                            if (j < 100) {
                                cell += '0' + j + 'C00' + k;
                            } else {
                                cell += j + 'C00' + k;
                            }
                        } else if (j > 9 && k > 9) {
                            if (j < 100 && k < 100) {
                                cell += '0' + j + 'C0' + k;
                            } else if (j < 100 && k > 99) {
                                cell += '0' + j + 'C' + k;
                            } else if (j > 99 && k < 100) {
                                cell += j + 'C0' + k;
                            } else if (j > 99 && k > 99) {
                                cell += j + 'C' + k;
                            }
                        }
                        
                        if (cell != curr_alive[i] && $("#grid_holder").find(cell).css("background").indexOf('rgb(153, 186, 221)') != -1) {
                            num++;
                        }
                    }   
                } else if (outer_referencing == 1) {
                    // Always Alive
                    if (j < 1 || j > grid_size) {
                        // Add 1 automatically
                        num++;
                    } else if (k < 1 || k > grid_size) {
                        // Add 1 automatically
                        num++;
                    } else {
                        // normal
                        if (j < 10 && k < 10) {
                            cell += '00' + j + 'C00' + k;
                        } else if (j < 10 && k > 9) {
                            if (k < 100) {
                                cell += '00' + j + 'C0' + k;
                            } else {
                                cell += '00' + j + 'C' + k;
                            }
                        } else if (j > 9 && k < 10) {
                            if (j < 100) {
                                cell += '0' + j + 'C00' + k;
                            } else {
                                cell += j + 'C00' + k;
                            }
                        } else if (j > 9 && k > 9) {
                            if (j < 100 && k < 100) {
                                cell += '0' + j + 'C0' + k;
                            } else if (j < 100 && k > 99) {
                                cell += '0' + j + 'C' + k;
                            } else if (j > 99 && k < 100) {
                                cell += j + 'C0' + k;
                            } else if (j > 99 && k > 99) {
                                cell += j + 'C' + k;
                            }
                        }
                        
                        if (cell != curr_alive[i] && $("#grid_holder").find(cell).css("background").indexOf('rgb(153, 186, 221)') != -1) {
                            num++;
                        }
                    }   
                } else if (outer_referencing == 2) {
                    // Toroidal
                    // Fix j (i.e. row) if out of bounds
                    if (j < 1) {
                        j += grid_size;
                    } else if (j > grid_size) {
                        j -= grid_size;
                    }
                    
                    // Fix k (i.e. col) if out of bounds
                    if (k < 1) {
                        k += grid_size;
                    } else if (k > grid_size) {
                        k -= grid_size;
                    }
                    
                    if (j < 10 && k < 10) {
                        cell += '00' + j + 'C00' + k;
                    } else if (j < 10 && k > 9) {
                        if (k < 100) {
                            cell += '00' + j + 'C0' + k;
                        } else {
                            cell += '00' + j + 'C' + k;
                        }
                    } else if (j > 9 && k < 10) {
                        if (j < 100) {
                            cell += '0' + j + 'C00' + k;
                        } else {
                            cell += j + 'C00' + k;
                        }
                    } else if (j > 9 && k > 9) {
                        if (j < 100 && k < 100) {
                            cell += '0' + j + 'C0' + k;
                        } else if (j < 100 && k > 99) {
                            cell += '0' + j + 'C' + k;
                        } else if (j > 99 && k < 100) {
                            cell += j + 'C0' + k;
                        } else if (j > 99 && k > 99) {
                            cell += j + 'C' + k;
                        }
                    }
                    
                    if (cell != curr_alive[i] && $("#grid_holder").find(cell).css("background").indexOf('rgb(153, 186, 221)') != -1) {
                        num++;
                    }
                }
                
                
            }
        }
        
        alive_num_neighbors[i] = num;
    }
    
    var dead_num_neighbors = new Array(curr_dead.length);
    for (i = 0; i < curr_dead.length; i++) {
        var a = curr_dead[i].substring(1, 4);
        var b = curr_dead[i].substring(5, 8);
        var num = 0;
        
        for (j = parseInt(a) - r; j < parseInt(a) + r; j++) {
            for (k = parseInt(b) - r; k < parseInt(b) + r; k++) {
                var cell = '#R';
                
                if (outer_referencing == 0) {
                    // Always Dead
                    if (j < 1 || j > grid_size) {
                        // ignore
                    } else if (k < 1 || k > grid_size) {
                        // ignore
                    } else {
                        // normal
                        if (j < 10 && k < 10) {
                            cell += '00' + j + 'C00' + k;
                        } else if (j < 10 && k > 9) {
                            if (k < 100) {
                                cell += '00' + j + 'C0' + k;
                            } else {
                                cell += '00' + j + 'C' + k;
                            }
                        } else if (j > 9 && k < 10) {
                            if (j < 100) {
                                cell += '0' + j + 'C00' + k;
                            } else {
                                cell += j + 'C00' + k;
                            }
                        } else if (j > 9 && k > 9) {
                            if (j < 100 && k < 100) {
                                cell += '0' + j + 'C0' + k;
                            } else if (j < 100 && k > 99) {
                                cell += '0' + j + 'C' + k;
                            } else if (j > 99 && k < 100) {
                                cell += j + 'C0' + k;
                            } else if (j > 99 && k > 99) {
                                cell += j + 'C' + k;
                            }
                        }
                        
                        if (cell != curr_alive[i] && $("#grid_holder").find(cell).css("background").indexOf('rgb(153, 186, 221)') != -1) {
                            num++;
                        }
                    }   
                } else if (outer_referencing == 1) {
                    // Always Alive
                    if (j < 1 || j > grid_size) {
                        // Add 1 automatically
                        num++;
                    } else if (k < 1 || k > grid_size) {
                        // Add 1 automatically
                        num++;
                    } else {
                        // normal
                        if (j < 10 && k < 10) {
                            cell += '00' + j + 'C00' + k;
                        } else if (j < 10 && k > 9) {
                            if (k < 100) {
                                cell += '00' + j + 'C0' + k;
                            } else {
                                cell += '00' + j + 'C' + k;
                            }
                        } else if (j > 9 && k < 10) {
                            if (j < 100) {
                                cell += '0' + j + 'C00' + k;
                            } else {
                                cell += j + 'C00' + k;
                            }
                        } else if (j > 9 && k > 9) {
                            if (j < 100 && k < 100) {
                                cell += '0' + j + 'C0' + k;
                            } else if (j < 100 && k > 99) {
                                cell += '0' + j + 'C' + k;
                            } else if (j > 99 && k < 100) {
                                cell += j + 'C0' + k;
                            } else if (j > 99 && k > 99) {
                                cell += j + 'C' + k;
                            }
                        }
                        
                        if (cell != curr_alive[i] && $("#grid_holder").find(cell).css("background").indexOf('rgb(153, 186, 221)') != -1) {
                            num++;
                        }
                    }   
                } else if (outer_referencing == 2) {
                    // Toroidal
                    // Fix j (i.e. row) if out of bounds
                    if (j < 1) {
                        j += grid_size;
                    } else if (j > grid_size) {
                        j -= grid_size;
                    }
                    
                    // Fix k (i.e. col) if out of bounds
                    if (k < 1) {
                        k += grid_size;
                    } else if (k > grid_size) {
                        k -= grid_size;
                    }
                    
                    if (j < 10 && k < 10) {
                        cell += '00' + j + 'C00' + k;
                    } else if (j < 10 && k > 9) {
                        if (k < 100) {
                            cell += '00' + j + 'C0' + k;
                        } else {
                            cell += '00' + j + 'C' + k;
                        }
                    } else if (j > 9 && k < 10) {
                        if (j < 100) {
                            cell += '0' + j + 'C00' + k;
                        } else {
                            cell += j + 'C00' + k;
                        }
                    } else if (j > 9 && k > 9) {
                        if (j < 100 && k < 100) {
                            cell += '0' + j + 'C0' + k;
                        } else if (j < 100 && k > 99) {
                            cell += '0' + j + 'C' + k;
                        } else if (j > 99 && k < 100) {
                            cell += j + 'C0' + k;
                        } else if (j > 99 && k > 99) {
                            cell += j + 'C' + k;
                        }
                    }
                    
                    if (cell != curr_alive[i] && $("#grid_holder").find(cell).css("background").indexOf('rgb(153, 186, 221)') != -1) {
                        num++;
                    }
                } 
            }
        }
        
        dead_num_neighbors[i] = num;
    }
    
    // Flip states
        // Alive to dead
    for (i = 0; i < curr_alive.length; i++) {
        if (alive_num_neighbors[i] < l || alive_num_neighbors[i] > o) {
            $("#grid_holder").find('#' + curr_alive[i]).css("background", "gray");
        }
    }
        
        // Dead to alive
    for (i = 0; i < curr_dead.length; i++) {
        if (dead_num_neighbors[i] >= gmin && dead_num_neighbors[i] <= gmax) {
            $("#grid_holder").find('#' + curr_dead[i]).css("background", "rgb(153, 186, 221)");
        }
    }

    /*
    // Print curr_alive array
    var curr_alive_msg = "";
    for (i = 0; i < curr_alive.length; i++) {
        curr_alive_msg += curr_alive[i] + "\n";
    }
    alert(curr_alive_msg);
    
    var curr_alive_num_msg = "";
    for (i = 0; i < curr_alive.length; i++) {
        curr_alive_num_msg += alive_num_neighbors[i] + "\n";
    }
    alert(curr_alive_num_msg);
    
    // Print curr_dead array
    var curr_dead_msg = "";
    for (i = 0; i < curr_dead.length; i++) {
        curr_dead_msg += curr_dead[i] + "\n";
    }
    alert(curr_dead_msg);
    
    var curr_dead_num_msg = "";
    for (i = 0; i < curr_dead.length; i++) {
        curr_dead_num_msg += dead_num_neighbors[i] + "\n";
    }
    alert(curr_dead_num_msg);
    */
}