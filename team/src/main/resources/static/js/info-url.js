$.brandPrimary =  '#20a8d8';
$.brandSuccess =  '#4dbd74';
$.brandInfo =     '#63c2de';
$.brandWarning =  '#f8cb00';
$.brandDanger =   '#f86c6b';
$.brandBlue =     '#4286f4';
$.brandGreen =    '#a2d200';

var clickGraphicInstance;
var referrerGraphicInstance;
var platformGraphicInstance;
var browserGraphicInstance;

//convert Hex to RGBA
function convertHex(hex,opacity){
    hex = hex.replace('#','');
    var r = parseInt(hex.substring(0,2), 16);
    var g = parseInt(hex.substring(2,4), 16);
    var b = parseInt(hex.substring(4,6), 16);

    return 'rgba('+r+','+g+','+b+','+opacity/100+')';
}

function getRandomColor() {
    var letters = '0123456789ABCDEF';
    var color = '#';
    for (var i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

// .toLocaleDateString("es-ES", {year:'numeric', month: 'short', day:'numeric',hour:'numeric',minute:'numeric',second:'numeric'})

// Traffic Chart
function showClicksGraphic(clicksInfo, displayUnits, timeInterval, totalClicks) {
    Chart.defaults.global.elements.point.radius = 0;
    Chart.defaults.global.elements.point.hitRadius = 20;
    Chart.defaults.global.elements.point.hoverBorderWidth = 4;
    var data;
    if(clicksInfo) {
        data = clicksInfo.map(function(item) { return { x: new Date(item.time), y: item.counter }; })
    } else {
        data = []
    }

    var trafficData = {
        datasets: [{
            lineTension: 0, // Avoid bezier curves
            label: 'Clicks',
            backgroundColor: convertHex($.brandBlue,35),
            borderColor: $.brandBlue,
            pointBorderColor: $.brandBlue,
            pointBorderWidth: 0,
            pointHoverRadius: 5,
            pointHoverBackgroundColor: $.brandBlue,
            pointHoverBorderColor: $.brandBlue,
            pointHoverBorderWidth: 2,
            pointRadius: 0,
            pointHitRadius: 10,
            data: data
        }]
    };

    var trafficOptions = {
        responsive: true,
        maintainAspectRatio: false,
        legend: {
            display: false
        },
        scales: {
            xAxes: [{
                type: 'time',
                time: {
                    unit: displayUnits,
                    displayFormats: {
                        'minute': 'h:mm a', // 11:20 AM
                        'hour': 'MMM D, hA', // Sept 4, 5PM
                        'day': 'll', // Sep 4 2015
                        'month': 'MMM YYYY', // Sept 2015
                        'year': 'YYYY' // 2015
                    },
                    unitStepSize: timeInterval
                },
                display: true
            }],
            yAxes: [{
                ticks: {
                    beginAtZero: true,
                    max: (totalClicks+4)
                },
                display: true
            }]
        }
    };

    if($("#clicks-chart").length) {
        var ctx = $("#clicks-chart").get(0).getContext("2d");
        //var ctx = $('#main-chart');
        clickGraphicInstance = new Chart(ctx, {
            type: 'line',
            data: trafficData,
            options: trafficOptions
        });
    }

}

// Referrers chart
function showReferrersGraphic(referrersInfo) {
    var referrersData;
    // Check if there aren't click information yet
    if(referrersInfo.length === 1 && referrersInfo[0][1] === 0) {
        referrersData = {
            labels: ["No hay datos"],
            datasets: [{
                data: [1],
                backgroundColor: ['#000000']
            }]
        };
    }
    else {
        referrersData = {
            labels: referrersInfo.map(function(obj) { return obj[0] }),
            datasets: [{
                data: referrersInfo.map(function(obj) { return obj[1] }),
                backgroundColor: referrersInfo.map(function() { return getRandomColor() })
            }]
        };
    }

    var referrersOptions = {
        responsive: true,
        animation: {
          tanimateScale: true,
            animaeRotate: true
        }
    };

    if ($("#referrers-chart").length) {
        var doughnutChartCanvas = $("#referrers-chart").get(0).getContext("2d");
        referrerGraphicInstance = new Chart(doughnutChartCanvas, {
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
        platformGraphicInstance = new Chart(barChartCanvas, {
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
        browserGraphicInstance = new Chart(barChartCanvas, {
            type: 'bar',
            data: browsersData,
            options: browsersOptions
        });
    }

}

function updateData(message, displayUnits, timeInterval, totalClicks) {
    showClicksGraphic(message.clicksInfo, displayUnits, timeInterval, totalClicks);

    showMapClicksGraphic(message.countriesInfo);

    var platformsInfoList = message.platformsInfo.map(function(obj) {
        return [((obj.platform === null) ? 'desconocido' : obj.platform), obj.counter];
    });
    showPlatformsGraphic(platformsInfoList);

    var browsersInfoList = message.browsersInfo.map(function(obj) {
        return [((obj.browser === null) ? 'desconocido' : obj.browser), obj.counter];
    });
    showBrowsersGraphic(browsersInfoList);

    var referrersInfoList = message.referrersInfo.map(function(obj) {
        return [((obj.referrer === null) ? 'desconocido' : obj.referrer), obj.counter];
    });
    showReferrersGraphic(referrersInfoList);
}

function getInformationByType(hash, type, displayUnits, timeInterval) {
    $.getJSON('/urlInfo/' + hash + '/' + type)
        .done(function (msg) {
            $('#totalClicks').text(msg.urlInfo.totalClicks);
            updateData(msg, displayUnits, timeInterval, msg.urlInfo.totalClicks);
        })
        .fail(function () {
            alert('Error')
        });
}

function getInfoByTabSelected(tabIndex, hash) {
    // Destroys the previous data
    if(clickGraphicInstance) clickGraphicInstance.destroy();
    if(referrerGraphicInstance) referrerGraphicInstance.destroy();
    if(platformGraphicInstance) platformGraphicInstance.destroy();
    if(browserGraphicInstance) browserGraphicInstance.destroy();

    if(tabIndex === 1){
        // Show information of last two hours, within intervals of 15 minutes in xAxes
        getInformationByType(hash, 'lastHours', 'minute', 15);
    } else if(tabIndex === 2){
        // Show information of the current day, within intervals of 4 hours in xAxes
        getInformationByType(hash, 'day', 'hour', 4);
    } else if(tabIndex === 3){
        // Show information of the current week, within intervals of 1 day in xAxes
        getInformationByType(hash, 'week', 'day', 1);
    } else if(tabIndex === 4){
        // Show information of the current month, within intervals of 7 days in xAxes
        getInformationByType(hash, 'month', 'day', 7);
    } else if(tabIndex === 5){
        // Show information of the current year, within intervals of 1 month in xAxes
        getInformationByType(hash, 'year', 'month', 1);
    } else if(tabIndex === 6){
        // Show information of all the url life cycle, within intervals of 1 year in xAxes
        getInformationByType(hash, 'all', 'year', 1);
    }
}
