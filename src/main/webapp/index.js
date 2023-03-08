function makeAPICall() {
    jQuery(document).ready(function () {
        jQuery.ajax({
            url: 'http://localhost:8080/api/v1/domainInfo?domainName=' +
                document.getElementById("getDomainName").value,
            type: "GET",
            success: function (result) {
                const answer = JSON.parse(result)
                let table = document.querySelector("table");
                let rows = "";
                if (answer.hasOwnProperty("registrarName")) {
                    for (let key in answer) {
                        rows += "<tr><td>" + key + ": </td><td>" + answer[key] + "</td></tr>";
                    }
                } else {
                    for (let price in answer) {
                        if (answer[price].hasOwnProperty("Currency") &&
                            answer[price].hasOwnProperty("RegularPrice") &&
                            answer[price].hasOwnProperty("Duration") &&
                            answer[price].hasOwnProperty("DurationType")) {
                            rows += "<tr><td>" + answer[price]["Duration"] + " " + answer[price]["DurationType"] + " for " +
                                answer[price]["RegularPrice"] + " " + answer[price]["Currency"] + "</td></tr>";
                        }
                    }
                }
                table.innerHTML = rows;
            },
            error: function (xhr, status, error) {
                console.log(error);
                document.querySelector("table").innerHTML = "";
                let errorInfo = JSON.parse(xhr.responseText);
                document.getElementById("errorInfo").innerHTML = errorInfo.message;
            }

        })
    })
}