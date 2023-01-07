// remove caracteres especiais do input title
document.getElementById('title').addEventListener('input', function(event) {
    var regex = /^[a-zA-Z0-9\s]*$/;
    if (!regex.test(event.target.value)) {
      event.target.value = event.target.value.substring(0, event.target.value.length - 1);
    }
});