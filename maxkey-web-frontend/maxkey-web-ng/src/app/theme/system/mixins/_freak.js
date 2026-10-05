module.exports = {
  install: function (less, pluginManager, functions) {
    functions.add('genComment', function (value, ...comments) {
      return (
        '/* AUTOGENERATE: ' +
        comments
          .map(c => c.value)
          .join('|SPLIT|')
          .replace(/(\{0\})/g, '"' + value.value + '"') +
        '*/'
      );
    });
    functions.add('genCommentColor', function (color, ...comments) {
      var imgColor = ' ![Color](https://dummyimage.com/20x20/' + color.value.substring(1) + '/fff.png&text=+)';
      return (
        '/* AUTOGENERATE: ' +
        comments
          .map(c => c.value + imgColor)
          .join('|SPLIT|')
          .replace(/(\{0\})/g, '"' + color.value + '"') +
        '*/'
      );
    });
    functions.add('genCommentTypeColor', function (type, color) {
      var comments = [];
      switch (type.value) {
        case 'text':
          comments = ['Set the text color to {0}', '设置文本颜色为 {0}'];
          break;
        case 'background':
          comments = ['Set the background light color to {0}', '设置背景颜色为 {0}'];
          break;
        case 'hoverBackground':
          comments = ['Set the hover background color to {0}', '设置悬停时背景颜色为 {0}'];
          break;
      }
      if (comments.length === 0) return '';
      var imgColor = ' ![Color](https://dummyimage.com/20x20/' + color.value.substring(1) + '/fff.png&text=+)';
      return (
        '/* AUTOGENERATE: ' +
        comments
          .map(c => c + imgColor)
          .join('|SPLIT|')
          .replace(/(\{0\})/g, '"' + color.value + '"') +
        '*/'
      );
    });
  }
};
