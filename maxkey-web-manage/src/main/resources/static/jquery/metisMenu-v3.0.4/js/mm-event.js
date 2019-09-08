$(function() {

  $('#menu').metisMenu().on('show.metisMenu', function(event) {
    new Noty({
      text: $(event.target).parent('li').children('a').html() + ' opening ...',
      layout: 'topRight',
      type: 'information',
      theme: 'relax',

      timeout: 350
    }).show();
  }).on('shown.metisMenu', function(event) {
    new Noty({
      text: $(event.target).parent('li').children('a').html() + ' opened',
      layout: 'topRight',
      type: 'success',
      theme: 'relax',
      timeout: 350
    }).show();
  }).on('hide.metisMenu', function(event) {
    new Noty({
      text: $(event.target).parent('li').children('a').html() + ' collapsing ...',
      layout: 'topRight',
      type: 'warning',
      theme: 'relax',
      timeout: 350
    }).show();
  }).on('hidden.metisMenu', function(event) {
    new Noty({
      text: $(event.target).parent('li').children('a').html() + ' collapsed',
      layout: 'topRight',
      type: 'error',
      theme: 'relax',
      timeout: 350
    }).show();
  });

});
