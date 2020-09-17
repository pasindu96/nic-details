let baseURL = "http://localhost:8080/api/nic/id=";

function getData(nic) {
  if (!nic) {
    Swal.fire({
      icon: "error",
      title: "Oops...",
      text: "Empty input !",
      footer: "<div>Error Code:01</div>",
    });
  } else {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", baseURL.concat(nic + ""), true);
    xmlhttp.onreadystatechange = function () {
      if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
        var data = JSON.parse(xmlhttp.responseText);

        switch (data.errorCode) {
          case 1:
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Empty input !",
              footer: "<div>Error Code:01</div>",
            });
            break;
          case 2:
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "NIC should contain 10(Old NIC) or 12(New NIC) digits !",
              footer: "<div>Error Code:02</div>",
            });
            break;
          case 3:
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "New NIC should start with 19XXXXXXXXXX or 20XXXXXXXXXX !",
              footer: "<div>Error Code:03</div>",
            });
            break;
          case 4:
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "The last digit of old NIC should be V or X !",
              footer: "<div>Error Code:04</div>",
            });
            break;
          case 5:
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "Invalid number sequence ! ",
              footer: "<div>Error Code:05</div>",
            });
            break;
          case 6:
            Swal.fire({
              icon: "error",
              title: "Oops...",
              text: "NIC should contain numbers !",
              footer: "<div>Error Code:06</div>",
            });
            break;
          case 10:
            {
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

if (!alertify.myAlert) {
  //define a new dialog
  alertify.dialog("myAlert", function () {
    return {
      main: function (message) {
        this.message = message;
      },
      setup: function () {
        return {
          buttons: [{ text: "cool!", key: 27 /*Esc*/ }],
          focus: { element: 0 },
        };
      },
      prepare: function () {
        this.setContent(this.message);
      },
    };
  });
}
