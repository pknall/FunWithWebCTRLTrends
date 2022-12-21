function getTrends() {
    alert("I've been pressed");
    var xhr = new XMLHttpRequest();
    var startDate = document.getElementById("startDate");
    var endDate = document.getElementById("endDate");
    var trendId = document.getElementById("trendList");
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var result = xhr.responseText;
            document.getElementById("trendData").value = result;
        }
    }
    xhr.open("POST","trendservice/" + startDate + "/" + endDate + "/" + trendId);
    xhr.send(null);
}