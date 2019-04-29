# free fork of jqGrid
======

jqGrid is a popular jQuery Plugin for displaying and editing data in tabular form. It has some other more sophisticated features, like subgrids, TreeGrids, grouping and so on.

jqGrid was developed mostly by [Tony Tomov](https://github.com/tonytomov) in the past and it was available under MIT/GPL-licenses till the version 4.7.0 published Dec 8, 2014 (see [here](https://github.com/tonytomov/jqGrid/tree/v4.7.0)). Short time after that the license agreement was changed (see <a href="https://github.com/tonytomov/jqGrid/commit/1b2cb55c93ee8b279f15a3faf5a2f82a98da3b4c">here</a>) and new 4.7.1 version was <a href="https://github.com/tonytomov/jqGrid/tree/v4.7.1">published</a>.

The code from the GitHib repository is the fork of jqGrid 4.7.0 - the latest version available under MIT/GPL-licenses. It will be provided under MIT/GPL-licenses.

Below you can find short description of new features and bug fixes implemented in free jqGrid 4.12.1 (compared with version 4.12.1). The version is developed by [Oleg Kiriljuk](https://github.com/OlegKi), alias [Oleg](http://stackoverflow.com/users/315935/oleg) on the stackoverflow and [OlegK](http://www.trirand.com/blog/?page_id=393) on trirand forum.

Read [Wiki](https://github.com/free-jqgrid/jqGrid/wiki) for more detailed information about the features of free-jqGrid. The preliminary version of the documentation can be found [here](http://free-jqgrid.github.io/).

Free jqGrid can be used *for free*. We still ask to contribute the development by donating via PayPal, if one have the possibility for it. One can donate by clicking on the following button [![PayPayl donate button](https://www.paypalobjects.com/webstatic/en_US/btn/btn_donate_pp_142x27.png)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JGTCBLQM2BYHG "Donate once-off to free jqGrid project using Paypal") or by sending money via Paypal to oleg.kiriljuk@ok-soft-gmbh.com with the comment "free jqGrid".

One can install the package with respect of [bower](http://bower.io/search/?q=free-jqgrid) by using "bower install free-jqgrid", with respect of [npm](https://www.npmjs.com/package/free-jqgrid) by using "npm install free-jqgrid" or from [NuGet](https://www.nuget.org/packages/free-jqGrid) by using "Install-Package free-jqGrid".

Free jqGrid is published on [cdnjs](https://cdnjs.com/libraries/free-jqgrid) and [jsDelivr CDN](http://www.jsdelivr.com/#!free-jqgrid). So one can use it directly from Internet by including for example
```html
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.12.1/css/ui.jqgrid.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.12.1/js/i18n/grid.locale-de.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.12.1/js/jquery.jqgrid.min.js"></script>
```

It somebody want to test the *latest* version of free jqGrid, one can load it directly from GitHib using [RawGit](http://rawgit.com/) service:
```html
<link rel="stylesheet" href="https://rawgit.com/free-jqgrid/jqGrid/master/css/ui.jqgrid.css">
<script src="https://rawgit.com/free-jqgrid/jqGrid/master/js/i18n/grid.locale-de.js"></script>
<script src="https://rawgit.com/free-jqgrid/jqGrid/master/js/jquery.jqgrid.src.js"></script>
```
All other language files and plugins are avalable from CDN too. See [the wiki article](https://github.com/free-jqgrid/jqGrid/wiki/Access-free-jqGrid-from-different-CDNs) for more details about the usage of free jqGrid from CDNs and RawGit.

The package is published on [WebJars](http://www.webjars.org/) and it's deployed on [Maven Central]((http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22free-jqgrid%22)) too.

Remark: the above URLs will be available **after publishing** the release of the version of 4.12.1

### Main new features and improvements implemented in the version 4.12.1.

* Add `div.ui-jqgrid-errorbar` and default implementation of `loadError`. Free jqGrid displays now an error message on errors during loading the data from the server.
* Add `afterResizeDblClick` callback and `jqGridAfterResizeDblClick` event. It allows to make some custom actions after Auto-Resizing of columns for example.
* Add support of `key`, `jsonmap`, `xmlmap` for `additionalProperties` items specified in object format.

### The below is the full list of changes in the version 4.12.1 compared with 4.12.0

* Bug fix in displaying of the column name in the validation error message of inline editing.
* Add missing en-locale information in locale embedded in `grid.base.js`/`jquery.jqgrid.src.js`/`jquery.jqgrid.min.js`. Some texts are missed if one used `jquery.jqgrid.src.js`/`jquery.jqgrid.min.js` *without* including the locale file `grid.locale-en.min.js`.
* Bug fix of *local* sorting if old style filters (the `postData` with `searchField`, `searchOper`, `searchString`) are used.
* Improvement of building process, add `watch` to `gruntfile.js`, fix `sourceMappingURL`.
* Implementing `editrules.custom` as function with more information in parameter as `custom_func`.
* Add `div.ui-jqgrid-errorbar` and default implementation of `loadError`. Free jqGrid displays now an error message on errors during loading the data from the server.
* Bug fix of "Next" and "Last" buttons, which should be not enabled on empty local grid.
* Bug fix of `setRowData` to fill `options.rowData` for custom formatters.
* Fix the reference to .css from .css.map files.
* Bug fix in `addChildNode`.
* Bug fix of internal `readInput` method to correctly support `xmlmap` properties of `colModel` items.
* Bug fix in saving of postion of editing dialogs.
* Add `afterResizeDblClick` callback and `jqGridAfterResizeDblClick` event. It allows to make some custom actions after Auto-Resizing of columns for example.
* Add support of `key`, `jsonmap`, `xmlmap` for `additionalProperties` items specified in object format.

Other readmes contain the list of the features and bug fixed implemented before:

* [README4.12.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.12.0.md) contains the readme of free jqGrid 4.12.0.
* [README4.11.1.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.11.1.md) contains the readme of free jqGrid 4.11.1.
* [README4.11.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.11.0.md) contains the readme of free jqGrid 4.11.0.
* [README4.10.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.10.0.md) contains the readme of free jqGrid 4.10.0.
* [README492.md](https://github.com/free-jqgrid/jqGrid/blob/master/README492.md) contains the readme of free jqGrid 4.9.2.
* [README491.md](https://github.com/free-jqgrid/jqGrid/blob/master/README491.md) contains the readme of free jqGrid 4.9.1.
* [README49.md](https://github.com/free-jqgrid/jqGrid/blob/master/README49.md) contains the readme of free jqGrid 4.9.
* [README48.md](https://github.com/free-jqgrid/jqGrid/blob/master/README48.md) contains the readme of free jqGrid 4.8.

**Many thanks to all, who sent bug reports and suggestions to improve free jqGrid!**
