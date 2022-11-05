document-addEventListener('DOMContentLoaded', () => {

    const codeBlock = document.getElementById('code');
    const copyButton = document.getElementById('copy-button');
    const copySuccess = document.getElementById('copy-success');

    const copyLink = document.getElementById('copy-link');

    const copyTextHandler = () => {
        const text = codeBlock.innerText;

        // first version -- document.exeCommand('copy')
        var element = document.createElement('textarea');
        document.body.appendChild(element);
        element.value = text;
        element.select();
        document.execCommand('copy');
        document.body.removeChild(element);

        copySuccess.classList.add('show-message');

        var showtext = [[${article.url}]];
        console.log(showtext);

        setTimeout(() => {
                    copySuccess.classList.remove('show-message');
                     }, 2000);
        };

        copyButton.addEventListener('click', copyTextHandler);

});