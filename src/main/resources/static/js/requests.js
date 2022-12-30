//sbumit post - subscriber
$("#form-subscriber").submit(function (evt)  {
    //its block the standard behavior of submit button function - that is refresh the page
    evt.preventDefault();
    var subscriber = {};
    subscriber.email = $("#email").val();
    $.ajax( {
        method: "POST",
        url: "/subscribe",
        data: subscriber,
        success: function() {
            //clear form
            $("#form-subscriber").each(function() {
                this.reset();
            });
            $("#email").text("");
            //show msm success
            $("#success-alert").addClass("container alert alert-success alert-dismissible fade show");
        },
        complete: function () {
            $("#success-alert").fadeTo(2000, 500).slideUp(500, function() {
            $("#success-alert").slideUp(500);
            });
        }
    });
});


//implement like
$('#like').click(function(){
    var id = $("#id").val();
      $.ajax({
        type: "POST",
        url: "/like",
        data: {
            'idArticle': id
          },
            success: function(response) {
                $("#likes").text(response);
            }
      });
});

//<!-- tooltip -->
$(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
});


//sbumit post - comment
$("#form-comment").submit(function (evt)  {
    //its block the standard behavior of submit button function - that is refresh the page
    //its block the standard behavior of submit button function - that is refresh the page
    evt.preventDefault();
    var comment = {};
    comment.review = $("#review").val();
    comment.author = $("#author").val();
    comment.article_id = $("#article_id").val();

    var comments = $("#comments");

    $.ajax( {
        method: "POST",
        url: "/post-comment",
        data: comment,

        beforeSend: function () {
            // remove the error messages
            $("span").closest('.error-span').remove();

            // remove as bordas vermelhas
            $("#author").removeClass("is-invalid");
            $("#review").removeClass("is-invalid");
        },

        success: function(response) {
            //clear form
            $("#form-comment").each(function() {
                this.reset();
            });
            $("#review").text("");
            $("#author").text("");

            var idArticle = $("#article_id").val();
            showCommentsListUpdated(idArticle);
        },

        statusCode: {
            422: function (xhr ) {
                console.log("status error: ", xhr.status);
                var errors = $.parseJSON(xhr.responseText);
                $.each(errors, function (key, val) {
                $("#" + key).addClass("is-invalid");
                $("#error-" + key)
                    .addClass("invalid-feedback")
                    .append("<span class='error-span'>" + val + "</span>")
                });
            }
        },

    });
});

// request to load or update comments seccion
function showCommentsListUpdated(idArticle){
    $.ajax({
        method: "GET",
        url: "/comments/ajax",
        data: {
            idArticle: idArticle
        },
        success: function (response) {
            console.log(response);
            $(".comment-area").fadeIn(250, function() {
                $(this).append(response);
            })
        }
    })
}

