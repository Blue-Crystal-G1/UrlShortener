$(document).ready(function() {
    $("#shortList").load('/urlInfo')

    $("#shortener").submit(function(event) {
        event.preventDefault();
        $.post('/link', $(this).serialize())
            .done(function (msg) {
                // Show popup with shorted url
                showDialogSuccess(msg.hash, msg.uri);
                // Update table with shorted urls
                $("#shortList").load('/urlInfo');
            })
            .fail(function () {
                showDialogError();
            });
    });
});

function showDialogSuccess(hash, uri) {
    var $richText = $('<div></div>');
    $richText.append('<strong>Esta es tu URL acortada: </strong>');
    $richText.append('<a href="/advertising/' + hash + '">' + uri + '</a>');
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
