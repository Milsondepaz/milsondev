var hash_tags = "Programming,Java,Spring,SpringBoot,RestAPI,microservice,Code"; //only for tweeter
var params = "menubar=no,toolbar=no,status=no,width=570,height=570"; // for window


var facebook = document.querySelector('.facebook');
var twitter = document.querySelector('.twitter');
var copyLink = document.querySelector('.copyLink');
var whatsapp = document.querySelector('.whatsapp');
var linkedin = document.querySelector('.linkedin');


facebook.addEventListener('click', function(evt) {
    //its block the standard behavior of submit button function - that is refresh the page
    evt.preventDefault();
    var url = $("#url").val();
    var title = $("#title").val();
    let shareUrl = `http://www.facebook.com/sharer/sharer.php?u=${url}`;
    window.open(shareUrl,"NewWindow" , params);
});

twitter.addEventListener('click', function(evt) {
    //its block the standard behavior of submit button function - that is refresh the page
    evt.preventDefault();
    var url = $("#url").val();
    var title = $("#title").val();
    let shareUrl = `https://twitter.com/intent/tweet?url=${url}&text=${title}&hashtags=${hash_tags}`;
    window.open(shareUrl,"NewWindow" , params);
});

linkedin.addEventListener('click', function(evt) {
    //its block the standard behavior of submit button function - that is refresh the page
    evt.preventDefault();
    var url = $("#url").val();
    var title = $("#title").val();
    let shareUrl = `https://www.linkedin.com/sharing/share-offsite/?url=${url}&title=${title}`;
    window.open(shareUrl,"NewWindow" , params);
});

whatsapp.addEventListener('click', function(evt) {
    //its block the standard behavior of submit button function - that is refresh the page
    evt.preventDefault();
    var url = $("#url").val();
    var title = $("#title").val();
    let shareUrl = `https://api.whatsapp.com/send?&text=${url}`;
    window.open(shareUrl,"NewWindow" , params);
});

copyLink.addEventListener('click', function(evt) {
    //its block the standard behavior of submit button function - that is refresh the page
    evt.preventDefault();
    var url = $("#url").val();
    var title = $("#title").val();
var input = document.createElement('input');
    input.setAttribute('value', url);
    document.body.appendChild(input);
    input.select();
    var result = document.execCommand('copy');
    document.body.removeChild(input);
    return result;
});