$(document).ready(function () {

    $("#login-form").submit(function (event) {

        //stop submit the form event. Do this manually using ajax post function
        event.preventDefault();

        var email = $("#email").val();

        $("#btn-login").prop("disabled", true);
        
        $.ajax({
          type: "GET",
          url: "/subscribe",
          data: {
            email
          },
          headers: {
            "X-Custom-Header": "value"
          },

          success: function(response) {
           console.log(response);

          }
        });
        
    });

});
