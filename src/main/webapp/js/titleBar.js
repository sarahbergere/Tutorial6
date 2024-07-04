//This allows me to choose which page has an arrow that goes back to the index page
$(document).ready(function() {
    let a = document.createElement("a");
    a.href = "index.jsf";
    let i = document.createElement('i');
    i.className = "fa-solid fa-chevron-left";
    i.style.color = "#ffffff";
    a.append(i);
    $(".header").prepend(a);
});
