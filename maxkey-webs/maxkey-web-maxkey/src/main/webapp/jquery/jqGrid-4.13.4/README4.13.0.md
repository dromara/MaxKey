# free fork of jqGrid
======

jqGrid is a popular jQuery Plugin for displaying and editing data in tabular form. It has some other more sophisticated features, like subgrids, TreeGrids, grouping and so on.

jqGrid was developed mostly by [Tony Tomov](https://github.com/tonytomov) in the past and it was available under MIT/GPL-licenses till the version 4.7.0 published Dec 8, 2014 (see [here](https://github.com/tonytomov/jqGrid/tree/v4.7.0)). Short time after that the license agreement was changed (see <a href="https://github.com/tonytomov/jqGrid/commit/1b2cb55c93ee8b279f15a3faf5a2f82a98da3b4c">here</a>) and new 4.7.1 version was <a href="https://github.com/tonytomov/jqGrid/tree/v4.7.1">published</a>.

The code from the GitHib repository is the fork of jqGrid 4.7.0 - the latest version available under MIT/GPL-licenses. It will be provided under MIT/GPL-licenses.

Below you can find short description of new features and bug fixes implemented in free jqGrid 4.13.0 (compared with version 4.12.1). The version is developed by [Oleg Kiriljuk](https://github.com/OlegKi), alias [Oleg](http://stackoverflow.com/users/315935/oleg) on the stackoverflow and [OlegK](http://www.trirand.com/blog/?page_id=393) on trirand forum.

Read [Wiki](https://github.com/free-jqgrid/jqGrid/wiki) for more detailed information about the features of free-jqGrid. The preliminary version of the documentation can be found [here](http://free-jqgrid.github.io/).

Free jqGrid can be used *for free*. We still ask to contribute the development by donating via PayPal, if one have the possibility for it. One can donate by clicking on the following button [![PayPayl donate button](https://www.paypalobjects.com/webstatic/en_US/btn/btn_donate_pp_142x27.png)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JGTCBLQM2BYHG "Donate once-off to free jqGrid project using PayPal") or by sending money via PayPal to oleg.kiriljuk@ok-soft-gmbh.com with the comment "free jqGrid". Bank transfer based on the invoice from OK soft GmbH is another option of donating. Just send the email with the information about the amount of donation and you will get the corresponding invoice with the full information about our bank account and our VAT number.

One can install the package with respect of [bower](http://bower.io/search/?q=free-jqgrid) by using "bower install free-jqgrid", with respect of [npm](https://www.npmjs.com/package/free-jqgrid) by using "npm install free-jqgrid" or from [NuGet](https://www.nuget.org/packages/free-jqGrid) by using "Install-Package free-jqGrid".

Free jqGrid is published on [cdnjs](https://cdnjs.com/libraries/free-jqgrid) and [jsDelivr CDN](http://www.jsdelivr.com/#!free-jqgrid). Thus one can use it directly from Internet by including for example the URLs like
```html
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.13.0/css/ui.jqgrid.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.13.0/js/i18n/grid.locale-de.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.13.0/js/jquery.jqgrid.min.js"></script>
```
or
```html
<link rel="stylesheet" href="https://cdn.jsdelivr.net/free-jqgrid/4.13.0/css/ui.jqgrid.min.css">
<script src="https://cdn.jsdelivr.net/free-jqgrid/4.13.0/js/i18n/grid.locale-de.min.js"></script>
<script src="https://cdn.jsdelivr.net/free-jqgrid/4.13.0/js/jquery.jqgrid.min.js"></script>
```
The locale file is optional. One can, but one don't need to include `grid.locale-en.min.js`, becusue the same information is already included in `jquery.jqgrid.min.js`.

If somebody want to test the *latest* version of free jqGrid, one can load it directly from GitHib using [RawGit](http://rawgit.com/) service:
```html
<link rel="stylesheet" href="https://rawgit.com/free-jqgrid/jqGrid/master/css/ui.jqgrid.css">
<script src="https://rawgit.com/free-jqgrid/jqGrid/master/js/i18n/grid.locale-de.js"></script>
<script src="https://rawgit.com/free-jqgrid/jqGrid/master/js/jquery.jqgrid.src.js"></script>
```
All other language files and plugins are available from CDN too. See [the wiki article](https://github.com/free-jqgrid/jqGrid/wiki/Access-free-jqGrid-from-different-CDNs) for more details about the usage of free jqGrid from CDNs and RawGit.

The package is published on [WebJars](http://www.webjars.org/) and it's deployed on [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cfree-jqgrid) too.

Remark: the above URLs will be available **after publishing** the release of the version of 4.13.0.

### Main new features and improvements implemented in the version 4.13.0.

* Free jqGrid can be used now without jQuery UI CSS, only with Bootstrap CSS and optional Font Awesome. One need just add `guiStyle: "bootstrap"` or `guiStyle: "bootstrapPrimary"` to existing grid.
* New `iconSet: "glyph"` is supported now. It allows to use only Bootstrap CSS without Font Awesome.
* One can customize the `iconSet` now more easy by usage of `baseIconSet` property.
* The CSS settings of jqGrid are changed a little because of including support of Bootstrap CSS. As the results some width of columns and some other CSS setting could be changed a little. If you created the grids, which uses every pixel of the results exactly, then you could have to make some changes of the width and height values after upgrade to free jqGrid 4.13.0.
* Some minor changes are made in `$.jgrid.viewModal`. Such changes can produces compatibility problems with the old code. It could be important **only if you call the method directly in your code**. In the case one should add `.call(gridDOM, ...` to the call of `$.jgrid.viewModal` to initialize `this` of `$.jgrid.viewModal` to the DOM element of the grid.

Some demos could be helpful for understanding how `guiStyle: "bootstrap"`, `guiStyle: "bootstrapPrimary"` and `iconSet` can be used:

* [Bootstrap with Font Awesome](http://www.ok-soft-gmbh.com/jqGrid/OK/formEditOnDoubleClick-bootstrap.htm) - The demo demonstrates the usage of the new option `guiStyle: "bootstrap"`. No jQuery UI CSS is used in the demo.
* [BootstrapPrimary with Font Awesome](http://www.ok-soft-gmbh.com/jqGrid/OK/formEditOnDoubleClick-bootstrapPrimary.htm) - The demo is very close to the previous one, but it uses `guiStyle: "bootstrapPrimary"`. It changes the style of buttons in the modal dialogs.
* [Bootstrap glyph fonts](http://www.ok-soft-gmbh.com/jqGrid/OK/formEditOnDoubleClick-bootstrap-glyph.htm) - The demo uses no jQuery UI CSS and Font Awesome. All CSS and icons are from Bootstrap. It uses `iconSet: "glyph"`.
* [TreeGrid with Bootstrap and custom iconSet based on glyph fonts](http://www.ok-soft-gmbh.com/jqGrid/OK/LocalAdjacencyTree-glyph.htm) - The demo domonstrates how one can ovewrite some icons from `iconSet: "glyph"`. The demo use custom icons for tree nodes and it uses no icons for leafs.
* [Grouping with Bootstrap glyph fonts and custom colors](http://www.ok-soft-gmbh.com/jqGrid/OK/grouping-bootstrap-glyph.htm) - The demo uses `guiStyle: "bootstrap"` with data groupinh. It shows additionally how one can customize colors or border and background color of some jqGrid elements.
* [jQuery UI](http://www.ok-soft-gmbh.com/jqGrid/OK/formEditOnDoubleClick-jqueryui.htm) - the base demo which uses jQuery UI CSS and the icons from jQuery UI.
* [jQuery UI with Font Awesome](http://www.ok-soft-gmbh.com/jqGrid/OK/formEditOnDoubleClick-jqueryui-fa.htm) - the same demo, but with Font Awesome CSS and the option `iconSet: "fontAwesome"`.
* [jQuery UI with glyph fonts](http://www.ok-soft-gmbh.com/jqGrid/OK/formEditOnDoubleClick-jqueryui-glyph.htm) - the demo which uses jQuery UI CSS, but glyph icons from Bootsrap.

### The below is the full list of changes in the version 4.13.0 compared with 4.12.1.

Remark: The list below doesn’t include temporary changes and fixes, which are not important for the final state of the version 4.13.0.

* New `baseIconSet` property can be used in the definition of new `iconSet` in `$.jgrid.icons`. It allows easy to define new `iconSet` by modifying of some settings of existing `iconSet` (currently `"jQueryUI"`, `"fontAwesome"` and `"glyph"`). In the same way the new `guiStyles` can be defined using `baseGuiStyle` property. The definition of the guiStyle `bootstrapPrimary` reference the setting of `bootstrap` and just overwrite *some properties*.
* Some old and unused files are removed from GitHub repository. Gradle is not more used for the build process.
* Unifications of checkbox formatters and templates. There are exist now only one `formatter: "checkbox"` and `template: "booleanCheckbox"`. The old `formatter: "checkboxFontAwesome4"` and `template: "booleanCheckboxFa"` are still supported, but contain just the reference to the implementation of `formatter: "checkbox"` and `template: "booleanCheckbox"`.
* Including new methods `isBootstrapGuiStyle` and `isBootstrapGuiStyle` used internally to detect the `guiStyle` and the `iconSet`.
* Include support of the third `iconSet`: glyphicons. One can now include Bootstrap CSS and to use `iconSet: "glyph"`.
* Some renaming of internal variables in the code to prevent the usage of the names defined in outer scope.
* Fix the usage of correct font of Alert dialog. It is now based on the `guiStyle` settings.
* Drop `$.jgrid.parse` setting and use always `$.parseJSON` to parse JSON data.
* Drop code fragment for IE7 and jQuery versions less the 1.6.
* Bug fix: remove unneeded trimming of value of `<select>` of the searching toolbar created by `stype:"select"`.
* Fix closing of Modal dialogs in case of click of right mouse button on the "Close" button of the dialog title.
* Saving of the last position of Add/Edit dialog is *removed* from free jqGrid. The problem was incorrect restoring of the position in more complex CSS scenarios. The problem exist in advanced scenarios or in case of different combination of other dialog parameters (`toTop`, `overlay`, ..,). For example, if jqGrid is created inside of jQuery UI dialog or inside of inside of complex Bootstrap structurs or just inside of the hirarchy of parents of jqGrid which contains mix values of `position` settings (`position: absolute`, `position: relative`, ...) then the calculation of position is very complex. Between of two opening of the Edit dialog the position of the parents (jQuery UI Dialog, which contains the grid) of the grid could be changed and the old position could be outside of visible area. There are too many aspects and the simple saving of previos position become really complex. Because of the complexity and many bug reports in the past the new versions of free jqGrid dropped the feature. One can still use `onClose` and `afterShowForm` callbacks to implement the feature in more simple scenario.
* Bug fix of processing of <kbd>Enter</kbd> key in `navGrid`. The old code produced incorrect results in View dialog on pressing <kbd>Enter</kbd> key.
* Bug fix of `addRowData` by usage of array of data as the input.
* Updated Catalan and Spanish translations: `grid.locale-ca.js` and `grid.locale-es.js`.
* Reorganize many parts of code and internal structures of jqGrid to support new `guiStyle: "bootstrap"` option, which allows to format jqGrid using CSS classes of Bootstrap.
* CSS structure of buttons of navigator, pager and `formatter: "actions"` is changed. The old "dancing" of buttons on hovering is not exist more (the "dancing" was before in case of zooming the page to any value exepting 100%, 200% and so on).
* The bug with placing duplicates of rowids in `selarrrow`, in case of usage `multiPageSelection: true, multiselect: true` and clicking on "Select All" button, is fixed.
* The bug with sorting of local grid filled *without* `id` values is fixed.
* Resizable icon used with Font Awesome to resize the dialog is changed. Not one uses vector icon from Font Awesome.
* The `$.jgrid.viewModal` is changed. It has to be called with `this` initialized to DOM of grid. **The code which uses the methods directrly have to be changed to intialize `this` to the DOM of the grid**.
* Default value of `cols` for `edittype: "textarea"` is changed from 20 to 19 because of increasing the margin and padding values in the Add/Edit dialog.
* Remove chaching of some jqGrid methods (`$.jgrid.viewModal`, `$.jgrid.hideModal`, `$.jgrid.createModal` and `$.jgrid.info_dialog`) from form editing module (`grid.formedit.js`). It allows to ovewrite (to subclass) the methods.
* Fix `localData` property of `afterSetRow` callback.
* Update Polish translation `grid.locale-pl.js`.
* Improve the usage of `beforeSubmit` and `afterSubmit` callbacks of form editing. Old versions of the callbackes **required** that the methods return results as an array. If, for example, one forgets to return the value in the required format it produed unhandled exception. The new changes test the results of `beforeSubmit` and `afterSubmit` callbacks and continues the standard processing.
* Improve performance of `showHideCol`, `hideCol`, `showCol` and `columnChooser`. New additional parameter of `showHideCol`, `hideCol` and `showCol` methods is object with three Boolean properties `skipSetGroupHeaders`, `skipFeedback`, `skipSetGridWidth`. Specifying of `true` values for any from the properties force skipping of the corresponding parts of `showHideCol` code. The usage of the options is very helpful in case of sequensial multipe calles, for example from `columnChooser`. Skipping of unneeded actions in the intermediate calls improves the performance and holds the same final results.
* Bug fix `createEl` (existing in versions starting with the version 4.9.2) in parsing of option `value` of `edittype: "select"`.

Other readmes contain the list of the features and bug fixed implemented before:

* [README4.12.1.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.12.1.md) contains the readme of free jqGrid 4.12.1.
* [README4.12.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.12.0.md) contains the readme of free jqGrid 4.12.0.
* [README4.11.1.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.11.1.md) contains the readme of free jqGrid 4.11.1.
* [README4.11.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.11.0.md) contains the readme of free jqGrid 4.11.0.
* [README4.10.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.10.0.md) contains the readme of free jqGrid 4.10.0.
* [README492.md](https://github.com/free-jqgrid/jqGrid/blob/master/README492.md) contains the readme of free jqGrid 4.9.2.
* [README491.md](https://github.com/free-jqgrid/jqGrid/blob/master/README491.md) contains the readme of free jqGrid 4.9.1.
* [README49.md](https://github.com/free-jqgrid/jqGrid/blob/master/README49.md) contains the readme of free jqGrid 4.9.
* [README48.md](https://github.com/free-jqgrid/jqGrid/blob/master/README48.md) contains the readme of free jqGrid 4.8.

**Many thanks to all, who sent bug reports and suggestions to improve free jqGrid!**
