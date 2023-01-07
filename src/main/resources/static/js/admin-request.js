/*
$(function () {
       $('.enable-disable').on('click', function (event) {
        // evita o evento default para nao logo para o controller log q é clicado
        // ou seja encaminha a requisicao - o js fará isso via PUT
        event.preventDefault();

        var botaoReceber = $(event.currentTarget);

        // pega essa linha da url pra enviar pra o metodo
        // th:href="@{receber/{codigo}(codigo=${titulo.codigo})}">
        var url = botaoReceber.attr('href');

        $.ajax({
                type: "PUT",
                url: url,
                    success: function(response) {
                    var id = botaoReceber.data('codigo');
                    //const minhaImagemSVGPublished = document.querySelector('#minhaImagemSVGPublished-'+id);

                    const minhaImagemSVGNotPublished = document.querySelector('#minhaImagemSVGNotPublished-'+id);

                    // minhaImagemSVGPublished.setAttribute('style', 'display: none');
                     minhaImagemSVGNotPublished.setAttribute('style', 'display: none');



                   // if (response == true){
                   //     minhaImagemSVGPublished.setAttribute('style', 'display: none');
                   //     minhaImagemSVGNotPublished.setAttribute('style', 'display: block');
                   //}else {
                   //     minhaImagemSVGPublished.setAttribute('style', 'display: block');
                   //     minhaImagemSVGNotPublished.setAttribute('style', 'display: none');
                   // }

                    }
              });
    });
});
*/

//deleteArticle
$(function () {
       $('.delete-article').on('click', function (event) {
        event.preventDefault();
        var botaoReceber = $(event.currentTarget);
        var url = botaoReceber.attr('href');
        $.ajax({
                type: "GET",
                url: url,
                    success: function(response) {
                    $('#rowToDeleteAjax-'+response).closest('tr').remove();
                    }
              });
    });
});

