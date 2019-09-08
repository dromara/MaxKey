$(function() {

  $('#menu').metisMenu();

  $('#stopMm').on('click', function(event) {
    $('#menu').metisMenu('dispose');
    new Noty({
      text: 'metisMenu stopped',
      layout: 'topRight',
      type: 'error',
      theme: 'relax',
      progressBar: true,
      timeout: 2000
    }).show();
  });

  $('#startMm').on('click', function(event) {
    $('#menu').metisMenu();
    new Noty({
      text: 'metisMenu restarted',
      layout: 'topRight',
      type: 'success',
      theme: 'relax',
      progressBar: true,
      timeout: 2000
    }).show();
  });

  $('#menu1').metisMenu();
  $('#deleteElem').one('click', function(event) {
    $(this).removeClass('btn-danger').addClass('btn-success').html('Menu 1 removed').attr('disabled', 'disabled');
    $('#menu1').metisMenu('dispose');

    $('#menu1 #removable').remove();
    new Noty({
      text: 'Menu 1 removed',
      layout: 'topRight',
      type: 'information',
      theme: 'relax',
      progressBar: true,
      timeout: 2000
    }).show();

    $('#menu1').metisMenu();
  });

});
