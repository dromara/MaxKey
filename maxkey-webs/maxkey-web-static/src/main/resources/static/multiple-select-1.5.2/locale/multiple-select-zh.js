(function (global, factory) {
  typeof exports === 'object' && typeof module !== 'undefined' ? factory(require('jquery')) :
  typeof define === 'function' && define.amd ? define(['jquery'], factory) :
  (global = global || self, factory(global.jQuery));
}(this, function ($) { 'use strict';

  $ = $ && $.hasOwnProperty('default') ? $['default'] : $;

  /**
   * Multiple Select zh-CN translation
   * Author: Zhixin Wen<wenzhixin2010@gmail.com>
   */

  $.fn.multipleSelect.locales['zh-CN'] = {
    formatSelectAll: function formatSelectAll() {
      return '[全选]';
    },
    formatAllSelected: function formatAllSelected() {
      return '已选择所有记录';
    },
    formatCountSelected: function formatCountSelected(count, total) {
      return '已从' + total + '条记录中选择' + count + '条';
    },
    formatNoMatchesFound: function formatNoMatchesFound() {
      return '没有找到记录';
    }
  };
  $.extend($.fn.multipleSelect.defaults, $.fn.multipleSelect.locales['zh-CN']);

}));
