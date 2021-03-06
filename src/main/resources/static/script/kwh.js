function showRow() {
    let articleContentRowElement = document.getElementById("article-content-row");
    let articleContentViewElement = document.getElementById("article-content-view");

    articleContentViewElement.innerHTML = "";

    hide(articleContentViewElement)
    show(articleContentRowElement)
}

function convertToHtml(input) {
    let reader = new commonmark.Parser();
    let writer = new commonmark.HtmlRenderer();
    let parsed = reader.parse(input);
    return writer.render(parsed);
}

function showView() {
    let articleContentRowElement = document.getElementById("article-content-row");
    let articleContentViewElement = document.getElementById("article-content-view");

    articleContentViewElement.innerHTML = convertToHtml(articleContentRowElement.value);

    hide(articleContentRowElement)
    show(articleContentViewElement)
}

function hide(element) {
    element.style.visibility = "hidden";
    element.style.display = "none";
}

function show(element) {
    element.style.visibility = "visible";
    element.style.display = "block";
}