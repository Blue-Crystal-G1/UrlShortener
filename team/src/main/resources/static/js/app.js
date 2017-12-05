//Main navigation
$.navigation = $('nav > ul.nav');

'use strict';

$(document).ready(function() {
    var numMenu = 0;
    // Add class .active to current link
    $.navigation.find('a').each(function(){

        var cUrl = String(window.location).split('?')[0];

        if (cUrl.substr(cUrl.length - 1) == '#') {
            cUrl = cUrl.slice(0,-1);
        }

        var hRef = $($(this))[0].href;
        if (hRef.substr(hRef.length - 1) == '#') {
            hRef = hRef.slice(0,-1);
        }

        if (hRef==cUrl) {
            //$(this).addClass('active');

            $(this).parents('li').addClass('active');
        }
        else {
            numMenu = numMenu + 1;
        }
    });
});


function IsJsonString(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}