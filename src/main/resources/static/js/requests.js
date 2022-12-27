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