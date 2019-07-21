$(function() {
  $('#menu')
    .metisMenu()
    .on('shown.metisMenu', function(event) {
      Jump("#menu")
    });
});
