//BaseURL of the API
var baseURL = "http://localhost:8080/api/nic/";

//Function to clear the input fields
function clearData() {
    $('#nic').val("");
    $("#nic").focus();
    document.getElementById('gender').innerHTML = "";
    document.getElementById('dob').innerHTML = "";
    hideAllRecordDiv();

}

//Function to hide the records table div
function hideAllRecordDiv() {
    var x = document.getElementById("tblDiv");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

//Function to show the record table div
function showAllRecordsDiv() {
    var x = document.getElementById("tblDiv");
    if (x.style.display === "block") {
        x.style.display = "none";
    } else {
        x.style.display = "block";
    }
}

//Function to edit the data
function editRecord(nic, gender, birthday) {

    if (gender || gender.trim()) {
        if (birthday || birthday.trim()) {
            const json = {
                "nic": nic,
                "gender": gender,
                "birthDay": birthday,
            };
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.open("POST", baseURL.concat("update"), true);
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                    var data = JSON.parse(xmlhttp.responseText);
                    if (data)
                        popUpSuccess("Record updated successfully !");
                    showAllRecordsDiv();
                    viewAllData();
                }
            }
            xmlhttp.setRequestHeader('Content-Type', 'application/json');
            xmlhttp.send(JSON.stringify(json));
        } else {
            popUpError("A field should contain a value !");
        }
    } else {
        popUpError("A field should contain a value !");
    }
    // var xmlhttp=new XMLHttpRequest();
    // xmlhttp.open("POST",)
}

//Function to delete a record
function deleteRecord(nic) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", baseURL.concat("delete/nic=" + nic), true);
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            //     console.log(records);
            var data = JSON.parse(xmlhttp.responseText);
            if (data)
                popUpError("Record Deleted Successfully !");
            console.log(data);
            var table = document.getElementById('myTable')
            table.innerHTML = "";
            hideAllRecordDiv();
            viewAllData();
        };
    };
    xmlhttp.send();
}

//Function to show all the records
function viewAllData() {
    showAllRecordsDiv();
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", baseURL.concat("view"), true);
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            var records = JSON.parse(xmlhttp.responseText);
            var records = JSON.parse(xmlhttp.responseText);
            var table = document.getElementById('myTable');
            table.innerHTML = "";
            // var table = document.getElementById('myTable')

            for (var i = 0; i < records.length; i++) {
                var row = `<tr>
                    <td>${records[i].nic}</td>
                    <td><input class="editData" id="editGender" type="text" style='width: 60px' value="${records[i].gender}" required></td>
                    <td><input class="editData" id="editDob" type="text" style='width: 180px' value="${records[i].birthDay}" required></td>
                    <td><a href="#" data-toggle="tooltip" title="Edit the data and click this button !"onClick="editRecord('${records[i].nic}',document.getElementById('editGender').value,document.getElementById('editDob').value)"><i class="fa fa-edit" style="font-size:24px;color:black"></i></a></td>
                    <td><a href="#" onClick="deleteRecord('${records[i].nic}')"><i class="fa fa-trash" style="font-size:24px;color:red"></a></i></td>
                  </tr>`
                table.innerHTML += row
            }
            console.log(records);
        }
    };
    xmlhttp.send();
}

//Function to pass the nic to backend api and get the data from the server
function getData(nic) {
    //   var table = document.getElementById('myTable')
    //   table.innerHTML = "";

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", baseURL.concat("id=" + nic), true);
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            var data = JSON.parse(xmlhttp.responseText);

            switch (data.errorCode) {
                case 1:
                    popUpError("Empty input ! - ERROR 1");
                    break;
                case 2:
                    popUpError("NIC should contain 10(Old NIC) or 12(New NIC) digits ! - ERROR 2");
                    break;
                case 3:
                    popUpError("New NIC should be starts with 19XXXXXXXXXX or 20XXXXXXXXXX ! - ERROR 3");

                    break;
                case 4:
                    popUpError("The last digit of old NIC should be V or X ! - ERROR 4");
                    break;
                case 5:
                    popUpError("Invalid number sequence ! - ERROR 5");
                    break;
                case 6:
                    popUpError("NIC should contain numbers only ! - ERROR 6");
                    break;
                case 10: {
                    document.getElementById('gender').innerHTML = "Gender  : " + data.gender;
                    document.getElementById('dob').innerHTML = "Birthday : " + data.birthDate;
                    console.log(data);
                }
                    break;
                default:
            }
        }
    };
    xmlhttp.send();
}

function invalidNICData() {
    // document.getElementById('gender').style.color = 'red';
    // document.getElementById('gender').innerHTML = "Invalid NIC";
    // document.getElementById('dob').style.color = 'red';
    // document.getElementById('dob').innerHTML = "Invalid NIC";
}

//Function to popup an error
function popUpError(errorMessage) {
    $(function () {
        $('.popup_box').fadeIn("slow");
        $('.popup_box').css("display", "block");
        document.getElementById('error').innerHTML = errorMessage;
        document.getElementById('gender').innerHTML = "";
        document.getElementById('dob').innerHTML = "";

    });
}

$(document).ready(function () {
    $('.btnok').click(function () {
        $('.popup_box').css("display", "none");
        $('#nic').val("");
        $("#nic").focus();
    });
});

//Function to popup a success message
function popUpSuccess(successMessage) {
    $(function () {
        $('.popup_box_success').fadeIn("slow");
        $('.popup_box_success').css("display", "block");
        document.getElementById('success').innerHTML = successMessage;
    });
}

$(document).ready(function () {
    $('.btnSuccess').click(function () {
        $('.popup_box_success').css("display", "none");
    });
});

