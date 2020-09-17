let baseURL = "http://localhost:8080/api/nic/id=";

function getData(nic) {
  if (!nic) {
    alert("Please enter the NIC !");
  } else {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", baseURL.concat(nic + ""), true);
    xmlhttp.onreadystatechange = function () {
      if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var data = JSON.parse(xmlhttp.responseText);

        switch (data.errorCode) {
          case 1:
            alert("Empty input ! [Error Code:01]");

            break;
          case 2:
            alert(
              "NIC should contain 10(Old NIC) or 12(New NIC) digits ! [Error Code:02]"
            );

            break;
          case 3:
            alert(
              "New NIC should be starts with 19XXXXXXXXXX or 20XXXXXXXXXX ! [Error Code:03]"
            );

            break;
          case 4:
            alert(
              "The last digit of old NIC should be V or X ! [Error Code:04]"
            );

            break;
          case 5:
            alert("Invalid number sequence ! [Error Code:05]");

            break;
          case 6:
            alert("NIC should contain numbers ! [Error Code:06]");

            break;
          case 10:
            {
              document.getElementById("gender").style.color = "black";
              document.getElementById("dob").style.color = "black";
              document.getElementById("gender").innerHTML =
                "Gender : " + data.gender;
              document.getElementById("dob").innerHTML =
                "Birth Date : " + data.birthDate;
              console.log(data);
            }
            break;
          default:
          // code block
        }

        // if (data.gender == null) {
        //   alert("Invalid NIC !")
        //   document.getElementById('gender').innerHTML = "Null";
        //   document.getElementById('dob').innerHTML = "Null";
        // } else {
        //   document.getElementById('gender').innerHTML = data.gender;
        //   document.getElementById('dob').innerHTML = data.birthDate;
        //   console.log(data);
        // }
      }
    };
    xmlhttp.send();
  }
}
