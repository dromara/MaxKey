# free fork of jqGrid
======

jqGrid is a popular jQuery Plugin for displaying and editing data in tabular form. It has some other more sophisticated features, like subgrids, TreeGrids, grouping and so on.

jqGrid was developed mostly by [Tony Tomov](https://github.com/tonytomov) in the past and it was available under MIT/GPL-licences till the version 4.7.0 published Dec 8, 2014 (see [here](https://github.com/tonytomov/jqGrid/tree/v4.7.0)). Short time after that the license agreement was changed (see <a href="https://github.com/tonytomov/jqGrid/commit/1b2cb55c93ee8b279f15a3faf5a2f82a98da3b4c">here</a>) and new 4.7.1 version was <a href="https://github.com/tonytomov/jqGrid/tree/v4.7.1">published</a>.

The code from the GitHib repository is the fork of jqGrid 4.7.0 - the latest version available under MIT/GPL-licences. It will be provided under MIT/GPL-licences.

Below you can find short description of new features and bug fixes implemented in free jqGrid 4.12.0 (compared with version 4.11.1). The version is developed by [Oleg Kiriljuk](https://github.com/OlegKi), alias [Oleg](http://stackoverflow.com/users/315935/oleg) on the stackoverflow and [OlegK](http://www.trirand.com/blog/?page_id=393) on trirand forum.

Read [Wiki](https://github.com/free-jqgrid/jqGrid/wiki) for more detailed information about the features of free-jqGrid.

Free jqGrid can be used *for free*. We still ask to contribute the development by donating via PayPal, if one have the possibility for it. One can donate by clicking on the following button [![PayPayl donate button](https://www.paypalobjects.com/webstatic/en_US/btn/btn_donate_pp_142x27.png)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JGTCBLQM2BYHG "Donate once-off to free jqGrid project using Paypal") or by sending money via Paypal to oleg.kiriljuk@ok-soft-gmbh.com with the comment "free jqGrid".

One can install the package with respect of [bower](http://bower.io/search/?q=free-jqgrid) by using "bower install free-jqgrid", with respect of [npm](https://www.npmjs.com/package/free-jqgrid) by using "npm install free-jqgrid" or from [NuGet](https://www.nuget.org/packages/free-jqGrid) by using "Install-Package free-jqGrid".

Free jqGrid is published on [cdnjs](https://cdnjs.com/libraries/free-jqgrid) and [jsDelivr CDN](http://www.jsdelivr.com/#!free-jqgrid). So one can use it directly from Internet by including for example
```html
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.12.0/css/ui.jqgrid.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.12.0/js/i18n/grid.locale-de.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.12.0/js/jquery.jqgrid.min.js"></script>
```

It somebody want to test the *latest* version of free jqGrid, one can load it directly from GitHib using [RawGit](http://rawgit.com/) service:
```html
<link rel="stylesheet" href="https://rawgit.com/free-jqgrid/jqGrid/master/css/ui.jqgrid.css">
<script src="https://rawgit.com/free-jqgrid/jqGrid/master/js/i18n/grid.locale-de.js"></script>
<script src="https://rawgit.com/free-jqgrid/jqGrid/master/js/jquery.jqgrid.src.js"></script>
```
All other language files and plugins are avalable from CDN too. See [the wiki article](https://github.com/free-jqgrid/jqGrid/wiki/Access-free-jqGrid-from-different-CDNs) for more details about the usage of free jqGrid from CDNs and RawGit.

The package is published on [WebJars](http://www.webjars.org/) and it's deployed on [Maven Central]((http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22free-jqgrid%22)) too.

Remark: the above URLs will be available **after publishing** the release of the version of 4.12.0

### Main new features and improvements implemented in the version 4.12.0.

* Add support of requireJs, improving building process of free jqGrid, add minimized individual modules. Be carefully with the usage of separate modules of free jqGrid. The names and the assignment of the methods to modules can be changed in the future.
* Add support on new `forceClientSorting: true` option, which force **local** sorting of data returned from the server in case of usage `loadonce: true`. The input data for TreeGrid needed be still already sorted. The problem will be solved in the later versions of free jqGrid.
* Add support of sorting and filtering by properties of data specified in `additionalProperties` options. One don't need more to use hidden columns for usage in custom formatters, sortring and filterings.

### The below is the full list of changes in the version 4.12.0 compared with 4.11.1

* Add new Boolean `searchForAdditionalProperties` options used by searching dialog to add all `additionalProperties`, which have no `search: false` property, to searching dialog.
* Change resizing of columns to support `autoResizing.minColWidth` property to set minimal width width are used during column resizing. Previous version had only grid wide `minResizingWidth` option.
* Add support of sorting and filtering by properties of data specified in `additionalProperties` options.
* Improve build process.
* Add support on new `forceClientSorting: true` option, which force **local** sorting of data returned from the server in case of usage `loadonce: true`.
* Add parameters to `sortfunc` callback of `colModel` items. It allows to access the full item data inside of custom sort function `sortfunc`.
* Bug fix of `navSeparatorAdd` code.
* Add new `inlineNavOptions` option of jqGrid, which allows to to use different options for `navGrid` and `inlineNav`.
* Include new localizable properties in locale files: `nav.savetext`, `nav.savetitle`, `nav.canceltext`, `nav.canceltext`, `search.addGroupTitle`, `search.deleteGroupTitle`, `search.addRuleTitle`, `search.deleteRuleTitle`.
* Improve compatibility with old jqGrid versions in case of usage `editoptions.dataUrl` without `formatter: "select"`.
* Small optimization of `ui.jqgrid.css`.
* Many changes in the structure of grouping header.
* Implementing `editrules.custom` as function with more information in parameter as `custom_func`.
* Bug fix in inline editing in setting of focus in case of usage custom controls having more as one focusable elements.
* Bug fix of "Next" and "Last" buttons, which should be not enabled on empty local grid.
* Bug fix of `setRowData` to fill `options.rowData` for custom formatters.
* Formatting local data editing by inline editing if `editable: "hidden"` are used.
* Bug fix in width of grid on `showCol`/`hideCol`.
* Bug fix of internal `readInput` method to correctly support `xmlmap` properties of `colModel` items.
* Bug fix of `excelExport` to use `exportOptions` option which allows to add custom information send by `excelExport`.
* Add support of requireJs for jqGrid modules, plugins and locale files.
* Bug fix of calculation of `scrollOffset` in IE.

Other readmes contains the list of the features and bug fixed implemented before:

* [README4.11.1.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.11.1.md) contains the readme of free jqGrid 4.11.1.
* [README4.11.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.11.0.md) contains the readme of free jqGrid 4.11.0.
* [README4.10.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.10.0.md) contains the readme of free jqGrid 4.10.0.
* [README492.md](https://github.com/free-jqgrid/jqGrid/blob/master/README492.md) contains the readme of free jqGrid 4.9.2.
* [README491.md](https://github.com/free-jqgrid/jqGrid/blob/master/README491.md) contains the readme of free jqGrid 4.9.1.
* [README49.md](https://github.com/free-jqgrid/jqGrid/blob/master/README49.md) contains the readme of free jqGrid 4.9.
* [README48.md](https://github.com/free-jqgrid/jqGrid/blob/master/README48.md) contains the readme of free jqGrid 4.8.

**Many thanks to all, who sent bug reports and suggestions to improve free jqGrid!**
