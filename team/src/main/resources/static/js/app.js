//Main navigation
$.navigation = $('nav > ul.nav');

'use strict';

$(document).ready(function() {
    // $("#shortener").submit(function(event) {
    //     event.preventDefault();
    //     $.ajax({
    //         type : "POST",
    //         url : "/link",
    //         data : $(this).serialize(),
    //         success : function(msg) {
    //             $("#result").html(
    //                 "<div class='alert alert-success lead'><a target='_blank' href='"
    //                 + msg.uri
    //                 + "'>"
    //                 + msg.uri
    //                 + "</a></div>");
    //         },
    //         error : function() {
    //             $("#result").html(
    //                     "<div class='alert alert-danger lead'>ERROR</div>");
    //         }
    //     });
    // });

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