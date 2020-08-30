(function (global, factory) {
  typeof exports === 'object' && typeof module !== 'undefined' ? factory(require('jquery')) :
  typeof define === 'function' && define.amd ? define(['jquery'], factory) :
  (global = global || self, factory(global.jQuery));
}(this, function ($) { 'use strict';

  $ = $ && $.hasOwnProperty('default') ? $['default'] : $;

  /**
   * Multiple Select es-ES translation
   * Author: Zhixin Wen<wenzhixin2010@gmail.com>
   */

  $.fn.multipleSelect.locales['es-ES'] = {
    formatSelectAll: function formatSelectAll() {
      return '[Seleccionar todo]';
    },
    formatAllSelected: function formatAllSelected() {
      return 'Todos seleccionados';
    },
    formatCountSelected: function formatCountSelected(count, total) {
      return count + ' de ' + total + ' seleccionado';
    },
    formatNoMatchesFound: function formatNoMatchesFound() {
      return 'No se encontraron coincidencias';
    }
  };
  $.extend($.fn.multipleSelect.defaults, $.fn.multipleSelect.locales['es-ES']);

}));
