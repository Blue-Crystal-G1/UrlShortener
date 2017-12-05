
$.brandPrimary =  '#20a8d8';
$.brandSuccess =  '#4dbd74';
$.brandInfo =     '#63c2de';
$.brandWarning =  '#f8cb00';
$.brandDanger =   '#f86c6b';
$.brandBlue =     '#4286f4';
$.brandGreen =    '#a2d200';


//convert Hex to RGBA
function convertHex(hex,opacity){
    hex = hex.replace('#','');
    var r = parseInt(hex.substring(0,2), 16);
    var g = parseInt(hex.substring(2,4), 16);
    var b = parseInt(hex.substring(4,6), 16);

    var result = 'rgba('+r+','+g+','+b+','+opacity/100+')';
    return result;
}

function getRandomColor() {
    var letters = '0123456789ABCDEF';
    var color = '#';
    for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

// Traffic Chart
var trafficData = {
labels: ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado', 'Domingo'],
datasets: [{
    label: 'Clicks',
    backgroundColor: convertHex($.brandBlue,30),
    borderColor: $.brandBlue,
    pointHoverBackgroundColor: convertHex($.brandBlue,30),
    borderWidth: 1,
    data: [1, 5, 6, 3, 19, 7, 2]
}]
};

var trafficOptions = {
maintainAspectRatio: false,
legend: {
  display: false
},
scales: {
  xAxes: [{
    gridLines: {
      drawOnChartArea: true
    }
  }],
  yAxes: [{
    ticks: {
      beginAtZero: true
    }
  }]
},
elements: {
  point: {
    radius: 3,
    hitRadius: 6,
    hoverRadius: 6,
    hoverBorderWidth: 3,
  }
},
};

if($("#clicks-chart").length) {
    var ctx = $("#clicks-chart").get(0).getContext("2d");
    //var ctx = $('#main-chart');
    var mainChart = new Chart(ctx, {
        type: 'line',
        data: trafficData,
        options: trafficOptions
    });
}

function showReferrersGraphic(referrersInfo) {

    // Referrers chart
    var referrersData = {
        labels: referrersInfo.map(function(obj) { return obj[0] }),
        datasets: [{
            data: referrersInfo.map(function(obj) { return obj[1] }),
            backgroundColor: referrersInfo.map(function() { return getRandomColor() })
        }]
    };

    var referrersOptions = {
        responsive: true,
        animation: {
          tanimateScale: true,
            animaeRotate: true
        }
    };

    if ($("#referrers-chart").length) {
        var doughnutChartCanvas = $("#referrers-chart").get(0).getContext("2d");
        var doughnutChart = new Chart(doughnutChartCanvas, {
            type: 'doughnut',
            data: referrersData,
            options: referrersOptions
        });
    }

}

// Platforms Chart
function showPlatformsGraphic(platformsInfo) {

    var platformsData = {
        labels: platformsInfo.map(function(obj) { return obj[0] }),
        datasets: [{
            label: 'Clicks',
            data: platformsInfo.map(function(obj) { return obj[1] }),
            backgroundColor: platformsInfo.map(function() { return convertHex($.brandBlue,30); }),
            borderColor: platformsInfo.map(function() { return convertHex($.brandBlue,100); }),
            borderWidth: 1
        }]
    };

    var platformsOptions = {
        legend: {
            display: false
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    };

    if ($("#platforms-chart").length) {
        var barChartCanvas = $("#platforms-chart").get(0).getContext("2d");
        // This will get the first returned node in the jQuery collection.
        var barChart = new Chart(barChartCanvas, {
            type: 'bar',
            data: platformsData,
            options: platformsOptions
        });
    }

}

// Browsers Chart
function showBrowsersGraphic(browsersInfo) {

    var browsersData = {
        labels: browsersInfo.map(function (obj) {return obj[0]}),
        datasets: [{
            label: 'Clicks',
            data: browsersInfo.map(function (obj) {return obj[1]}),
            backgroundColor: browsersInfo.map(function() { return convertHex($.brandBlue,30); }),
            borderColor: browsersInfo.map(function() { return convertHex($.brandBlue,100); }),
            borderWidth: 1
        }]
    };

    var browsersOptions = {
        legend: {
            display: false
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    };

    if ($("#browsers-chart").length) {
        var barChartCanvas = $("#browsers-chart").get(0).getContext("2d");
        var barChart = new Chart(barChartCanvas, {
            type: 'bar',
            data: browsersData,
            options: browsersOptions
        });
    }

}
