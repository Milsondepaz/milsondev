//var text = encodeURIComponent("Follow JavaScript Jeep form Amazing JavaScript Tutorial");
//var url = /*[[${article.url}]]*/
//var hash_tags = "JS,JavaScript,100DaysOfCode,Programming"; //only for tweeter

var params = "menubar=no,toolbar=no,status=no,width=570,height=570"; // for window
var url = "https://medium.com/@jagathishsaravanan/";
var linkOfArticle = "";

//var facebook = document.querySelector('.facebook');
var twitter = document.querySelector('.twitter');
var copyLink = document.querySelector('.copyLink');
var whatsapp = document.querySelector('.whatsapp');
var linkedin = document.querySelector('.linkedin');


//facebook.addEventListener('click', function(ev) {
//    let shareUrl = `http://www.facebook.com/sharer/sharer.php?u=${url}`;
//    window.open(shareUrl,"NewWindow" , params);
//});

twitter.addEventListener('click', function(ev) {
  let shareUrl = `https://twitter.com/intent/tweet?url=${url}&text=${text}&hashtags=${hash_tags}`;
   window.open(shareUrl,"NewWindow" , params);
});

linkedin.addEventListener('click', function(ev) {
  let shareUrl = `https://www.linkedin.com/sharing/share-offsite/?url=${url}&title=${text}`;
   window.open(shareUrl,"NewWindow" , params);
});

whatsapp.addEventListener('click', function(ev) {
  let shareUrl = `https://api.whatsapp.com/send?&text=${url}`;
   window.open(shareUrl,"NewWindow" , params);
});

copyLink.addEventListener('click', function(ev) {
var text = "Hello World 3";
var input = document.createElement('input');
    input.setAttribute('value', text);
    document.body.appendChild(input);
    input.select();
    var result = document.execCommand('copy');
    document.body.removeChild(input);
    return result;
});