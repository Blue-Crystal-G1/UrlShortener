$("#shortener").submit(function(event) {
    event.preventDefault();
    $.post('/link', $(this).serialize())
        .done(function (msg) {
            var uri = msg.uri;
            showDialogSuccess(uri);
        })
        .fail(function () {
            showDialogError();
        });
    //
    // $.ajax({
    //     type : "POST",
    //     url : "/link",
    //     data : $(this).serialize(),
    //     success : function(msg) {
    //         var uri = msg.uri;
    //         showDialogSuccess(uri);
    //     },
    //     error : function() {
    //         showDialogError();
    //     }
    // });
});

function showDialogSuccess(result) {
    var $richText = $('<div></div>');
    $richText.append('<strong>Esta es tu URL acortada: </strong>');
    $richText.append('<a href="' + result + '">' + result + '</a>');

    BootstrapDialog.show({
        type: BootstrapDialog.TYPE_SUCCESS,
        title: "URL generada con éxito",
        message: $richText,
        closable: true,
        buttons: [{
            label: 'Cerrar',
            action: function (dialogItself) {
                dialogItself.close();
            }
        }]
    })
}

function showDialogError() {
    BootstrapDialog.show({
        type: BootstrapDialog.TYPE_DANGER,
        title: "Error al generar la URL",
        message: "La URL que has introducido no es válida.",
        closable: true,
        buttons: [{
            label: 'Cerrar',
            action: function (dialogItself) {
                dialogItself.close();
            }
        }]
    })
}
