//Main navigation
$.navigation = $('nav > ul.nav');

'use strict';

$(document).ready(function() {
    // Add class .active to current link
    $.navigation.find('a').each(function(){

        var cUrl = String(window.location).split('?')[0];

        if (cUrl.substr(cUrl.length - 1) === '#') {
            cUrl = cUrl.slice(0,-1);
        }

        var hRef = $($(this))[0].href;
        if (hRef.substr(hRef.length - 1) === '#') {
            hRef = hRef.slice(0,-1);
        }

        if (hRef===cUrl) {
            $(this).parents('li').addClass('active');
        }
        else {
            $(this).parents('li').removeClass('active');
        }
    });
});

// Serialize form to JavaScript object
(function ($) {
    $.fn.serializeFormJSON = function () {

        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
})(jQuery);