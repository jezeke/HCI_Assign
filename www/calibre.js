function fileMenu() {
  alert("This would open a file prompt with the OS.");
}

function notImplemented() {
  alert("This feature is not implemented.");
}

$(function()  {
  $("#search").keyup(function (e) {
    if (e.which == 13) {
      alert("Not implemented. \n\nTyping in the search bar would filter the displayed books. \n\nSelecting a book or changing view would clear the search.")
    }
  });
});

function rightScroll() {
  return $("#nav-tab").scrollLeft($("#nav-tab").scrollLeft() - 100);
}

function leftScroll() {
  return $("#nav-tab").scrollLeft($("#nav-tab").scrollLeft() + 100);
}
