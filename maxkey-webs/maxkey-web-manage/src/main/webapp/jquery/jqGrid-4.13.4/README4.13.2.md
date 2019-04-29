# free fork of jqGrid
[![npm version](https://img.shields.io/npm/v/free-jqgrid.svg?style=flat)](https://www.npmjs.com/package/free-jqgrid)![downloadstotal](https://img.shields.io/npm/dt/free-jqgrid.svg?style=flat-square)&nbsp;[![Bower](https://img.shields.io/bower/v/free-jqgrid.svg?style=flat-square)](http://bower.io/search/?q=free-jqgrid)&nbsp;[![NuGet](https://img.shields.io/nuget/v/free-jqgrid.svg?style=flat-square)](https://www.nuget.org/packages/free-jqGrid/)&nbsp;[![Maven Central](https://img.shields.io/maven-central/v/org.webjars.npm/free-jqgrid.svg?style=flat-square)](http://search.maven.org/#search%7Cga%7C1%7Cfree-jqgrid)<br>[![GitHub commits](https://img.shields.io/github/commits-since/free-jqgrid/jqgrid/v4.7.0.svg)](https://github.com/free-jqgrid/jqGrid/compare/v4.7.0...master)&nbsp;![Image1](http://issuestats.com/github/free-jqgrid/jqGrid/badge/pr?style=flat-square)![Image2](http://issuestats.com/github/free-jqgrid/jqGrid/badge/issue?style=flat-square)&nbsp;[![GitHub watchers](https://img.shields.io/github/watchers/free-jqgrid/jqGrid.svg)](https://github.com/free-jqgrid/jqGrid/watchers)[![GitHub forks](https://img.shields.io/github/forks/free-jqgrid/jqGrid.svg)](https://github.com/free-jqgrid/jqGrid/network)[![GitHub stars](https://img.shields.io/github/stars/free-jqgrid/jqGrid.svg)](https://github.com/free-jqgrid/jqGrid/stargazers)&nbsp;[![GitHub license](https://img.shields.io/badge/license-MIT%20or%20GNU%20GPLv2-blue.svg)](https://github.com/free-jqgrid/jqGrid/blob/master/LICENSE.md)
---
jqGrid is a popular jQuery Plugin for displaying and editing data in tabular form. It has some other more sophisticated features, like subgrids, TreeGrids, grouping and so on.

jqGrid was developed mostly by [Tony Tomov](https://github.com/tonytomov) in the past and it was available under MIT/GPL-licenses till the version 4.7.0 published Dec 8, 2014 (see [here](https://github.com/tonytomov/jqGrid/tree/v4.7.0)). Short time after that the license agreement was changed (see <a href="https://github.com/tonytomov/jqGrid/commit/1b2cb55c93ee8b279f15a3faf5a2f82a98da3b4c">here</a>) and new 4.7.1 version was <a href="https://github.com/tonytomov/jqGrid/tree/v4.7.1">published</a>.

The code from the GitHib repository is the fork of jqGrid 4.7.0 - the latest version available under MIT/GPL-licenses. It will be provided under MIT/GPL-licenses.

Below you can find short description of minor new features and the bug fixes implemented in free jqGrid 4.13.2 (compared with version 4.13.1). The version is developed by [Oleg Kiriljuk](https://github.com/OlegKi), alias [Oleg](http://stackoverflow.com/users/315935/oleg) on the stackoverflow and [OlegK](http://www.trirand.com/blog/?page_id=393) on trirand forum.

Read [Wiki](https://github.com/free-jqgrid/jqGrid/wiki) for more detailed information about the features of free-jqGrid. The preliminary version of the documentation can be found [here](http://free-jqgrid.github.io/).

Free jqGrid can be used *for free*. We still ask to contribute the development by donating via PayPal, if one have the possibility for it. One can donate by clicking on the following button [![PayPayl donate button](https://www.paypalobjects.com/webstatic/en_US/btn/btn_donate_pp_142x27.png)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JGTCBLQM2BYHG "Donate once-off to free jqGrid project using PayPal") or by sending money via PayPal to oleg.kiriljuk@ok-soft-gmbh.com with the comment "free jqGrid". Bank transfer based on the invoice from OK soft GmbH is another option of donating. Just send the email with the information about the amount of donation and you will get the corresponding invoice with the full information about our bank account and our VAT number.

One can install the package with respect of [npm](https://www.npmjs.com/package/free-jqgrid) by using "npm install free-jqgrid", with respect of [bower](http://bower.io/search/?q=free-jqgrid) by using "bower install free-jqgrid" or from [NuGet](https://www.nuget.org/packages/free-jqGrid) by using "Install-Package free-jqGrid".

The package is published on [WebJars](http://www.webjars.org/) too and it's deployed to [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cfree-jqgrid).

Free jqGrid is is available from [cdnjs](https://cdnjs.com/libraries/free-jqgrid) and [jsDelivr CDN](http://www.jsdelivr.com/#!free-jqgrid). Thus one can use it directly from Internet by including for example the URLs like
```html
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.13.2/css/ui.jqgrid.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.13.2/js/i18n/grid.locale-de.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.13.2/js/jquery.jqgrid.min.js"></script>
```
or
```html
<link rel="stylesheet" href="https://cdn.jsdelivr.net/free-jqgrid/4.13.2/css/ui.jqgrid.min.css">
<script src="https://cdn.jsdelivr.net/free-jqgrid/4.13.2/js/i18n/grid.locale-de.min.js"></script>
<script src="https://cdn.jsdelivr.net/free-jqgrid/4.13.2/js/jquery.jqgrid.min.js"></script>
```
**The locale file is optional**. One can, but one don't need to include `grid.locale-en.min.js`, because the same information is already included in the `jquery.jqgrid.min.js` (or `jquery.jqgrid.src.js`).

If somebody want to test the *latest* version of free jqGrid, one can load it directly from GitHib using [RawGit](http://rawgit.com/) service:
```html
<link rel="stylesheet" href="https://rawgit.com/free-jqgrid/jqGrid/master/css/ui.jqgrid.css">
<script src="https://rawgit.com/free-jqgrid/jqGrid/master/js/i18n/grid.locale-de.js"></script>
<script src="https://rawgit.com/free-jqgrid/jqGrid/master/js/jquery.jqgrid.src.js"></script>
```
All other language files and plugins are available from CDN too. See [the wiki article](https://github.com/free-jqgrid/jqGrid/wiki/Access-free-jqGrid-from-different-CDNs) for more details about the usage of free jqGrid from CDNs and RawGit.

Remark: the above URLs will be available **after publishing** the release of the version of 4.13.2.

### Below one can see the full list of changes in the version 4.13.2 compared with 4.13.1:

* Bug fix in encodding of attributes inside of predefined formatters (like `formatter: "showlink"`).
* Making `parseFilter` safer, It will be used in `filterToolbar` to restore the values in the filter based on the value of `postData.filters`.
* Bug fixes in `filterToolbar` in case of usage `searchOnEnter: true` option.
* Bug fixes in Seaching Dialog: the usage of `readonly` and `disabled` attributes defined in `editoptions` will be not used in Searching Dialog.
* The value of `this`, used in `afterRedraw` and `onChange` callbacks of the Searching Dialog, is fixed corresponds to the standard behavior of jqGrid.
* Add more parameters in `onChange` callback and `jqGridFilterAfterChange` event.
* Bug fix in `filterToolbar` in the wrong usage of `name` property of `colModel` instead of `index`.
* Add icons on top of README with some statistic and links to NPM, Bower, NuGet and Maven Central.
* Replace the usage `complete` to the usage of `success` & `error` callbacks inside of legacy/simplitied subgrid module.
* Add `aria-label='Close'` to closing icons used in free jqGrid.
* Some improvmenes in the error div used by Bootstrap. It will be used now `alert` and `alert-danger` Bootstrap classes.
* Bug fix in the usage of `jsonmap` property of `colModel` in case of `datatype: "jsonstring"`.
* Bug fix of missing dependencies used by `requireJs` for `grid.common`, `grid.treegrid` and `grid.import`.
* Bug fix in the behavior of the filter toolbar after pressing of Reset buttons of Searching Dialog.
* Bug fix in removing of trailing spaces from input fileds of the filter toolbar in case of usage `searchOnEnter: false` mode.
* More save processing of `formatter` and return value of some callbacks of cell and form editing mode, which should return arrays like `[true]` or `[false, "error description"]`. Old code could produce exceptions if wrong `undefined` or `null` value will be returned from the callbacks. After the fix the `undefined`/`null` value will be intrpreted as no error (`[true]`).
* Use `JSON.stringify` as the first choice instead of `xmlJsonClass.toJson` during processing of `postData.filters` in Searching Dialog.
* Bug fix in `footerData` and `setLabel` for the case of usage frozen columns.

Other readmes contain the list of the features and bug fixed implemented before:

* [README4.13.1.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.13.1.md) contains the readme of free jqGrid 4.13.1.
* [README4.13.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.13.0.md) contains the readme of free jqGrid 4.13.0.
* [README4.12.1.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.12.1.md) contains the readme of free jqGrid 4.12.1.
* [README4.12.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.12.0.md) contains the readme of free jqGrid 4.12.0.
* [README4.11.1.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.11.1.md) contains the readme of free jqGrid 4.11.1.
* [README4.11.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.11.0.md) contains the readme of free jqGrid 4.11.0.
* [README4.10.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.10.0.md) contains the readme of free jqGrid 4.10.0.
* [README492.md](https://github.com/free-jqgrid/jqGrid/blob/master/README492.md) contains the readme of free jqGrid 4.9.2.
* [README491.md](https://github.com/free-jqgrid/jqGrid/blob/master/README491.md) contains the readme of free jqGrid 4.9.1.
* [README49.md](https://github.com/free-jqgrid/jqGrid/blob/master/README49.md) contains the readme of free jqGrid 4.9.0.
* [README48.md](https://github.com/free-jqgrid/jqGrid/blob/master/README48.md) contains the readme of free jqGrid 4.8.0.

**Many thanks to all, who sent bug reports and suggestions to improve free jqGrid!**
