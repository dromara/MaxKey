# free fork of jqGrid
---
jqGrid is a popular jQuery Plugin for displaying and editing data in tabular form. It has some other more sophisticated features, like subgrids, TreeGrids, grouping and so on.

jqGrid was developed mostly by [Tony Tomov](https://github.com/tonytomov) in the past and it was available under MIT/GPL-licenses till the version 4.7.0 published Dec 8, 2014 (see [here](https://github.com/tonytomov/jqGrid/tree/v4.7.0)). Short time after that the license agreement was changed (see <a href="https://github.com/tonytomov/jqGrid/commit/1b2cb55c93ee8b279f15a3faf5a2f82a98da3b4c">here</a>) and new 4.7.1 version was <a href="https://github.com/tonytomov/jqGrid/tree/v4.7.1">published</a>.

The code from the GitHib repository is the fork of jqGrid 4.7.0 - the latest version available under MIT/GPL-licenses. It will be provided under MIT/GPL-licenses.

Below you can find short description of new features and bug fixes implemented in free jqGrid 4.13.1 (compared with version 4.13.0). The version is developed by [Oleg Kiriljuk](https://github.com/OlegKi), alias [Oleg](http://stackoverflow.com/users/315935/oleg) on the stackoverflow and [OlegK](http://www.trirand.com/blog/?page_id=393) on trirand forum.

Read [Wiki](https://github.com/free-jqgrid/jqGrid/wiki) for more detailed information about the features of free-jqGrid. The preliminary version of the documentation can be found [here](http://free-jqgrid.github.io/).

Free jqGrid can be used *for free*. We still ask to contribute the development by donating via PayPal, if one have the possibility for it. One can donate by clicking on the following button [![PayPayl donate button](https://www.paypalobjects.com/webstatic/en_US/btn/btn_donate_pp_142x27.png)](https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JGTCBLQM2BYHG "Donate once-off to free jqGrid project using PayPal") or by sending money via PayPal to oleg.kiriljuk@ok-soft-gmbh.com with the comment "free jqGrid". Bank transfer based on the invoice from OK soft GmbH is another option of donating. Just send the email with the information about the amount of donation and you will get the corresponding invoice with the full information about our bank account and our VAT number.

One can install the package with respect of [bower](http://bower.io/search/?q=free-jqgrid) by using "bower install free-jqgrid", with respect of [npm](https://www.npmjs.com/package/free-jqgrid) by using "npm install free-jqgrid" or from [NuGet](https://www.nuget.org/packages/free-jqGrid) by using "Install-Package free-jqGrid".

The package is published on [WebJars](http://www.webjars.org/) and it's deployed on [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cfree-jqgrid) too.

Free jqGrid is is available from [cdnjs](https://cdnjs.com/libraries/free-jqgrid) and [jsDelivr CDN](http://www.jsdelivr.com/#!free-jqgrid). Thus one can use it directly from Internet by including for example the URLs like
```html
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.13.1/css/ui.jqgrid.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.13.1/js/i18n/grid.locale-de.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/free-jqgrid/4.13.1/js/jquery.jqgrid.min.js"></script>
```
or
```html
<link rel="stylesheet" href="https://cdn.jsdelivr.net/free-jqgrid/4.13.1/css/ui.jqgrid.min.css">
<script src="https://cdn.jsdelivr.net/free-jqgrid/4.13.1/js/i18n/grid.locale-de.min.js"></script>
<script src="https://cdn.jsdelivr.net/free-jqgrid/4.13.1/js/jquery.jqgrid.min.js"></script>
```
The locale file is optional. One can, but one don't need to include `grid.locale-en.min.js`, becusue the same information is already included in `jquery.jqgrid.min.js`.

If somebody want to test the *latest* version of free jqGrid, one can load it directly from GitHib using [RawGit](http://rawgit.com/) service:
```html
<link rel="stylesheet" href="https://rawgit.com/free-jqgrid/jqGrid/master/css/ui.jqgrid.css">
<script src="https://rawgit.com/free-jqgrid/jqGrid/master/js/i18n/grid.locale-de.js"></script>
<script src="https://rawgit.com/free-jqgrid/jqGrid/master/js/jquery.jqgrid.src.js"></script>
```
All other language files and plugins are available from CDN too. See [the wiki article](https://github.com/free-jqgrid/jqGrid/wiki/Access-free-jqGrid-from-different-CDNs) for more details about the usage of free jqGrid from CDNs and RawGit.

Remark: the above URLs will be available **after publishing** the release of the version of 4.13.1.

### Main new features and improvements implemented in the version 4.13.1:

* The new `noFilterText` property of `searchoptions` is added, which allows to add the entry in the filter to clear filtering by the column.
* The new option `loadFilterDefaults: true` of the `filterToolbar` and the event `jqGridRefreshFilterValues` of jqGrid are added. They force filling the filter toolbar based of the filter applied to the grid. Only `postData.filters` is supported.
* The ruls of building of ids of input elements of the filter toolbar are changed. The new option `idMode` of `filterToolbar` allows to customize the changes or to force the usage of old id rules. See the comment to [the commit](https://github.com/free-jqgrid/jqGrid/commit/7cc612034a48d3521d97d2445456cf672a262b0c).
* Performance improvement of scrolling.

For example, one can use now
```JavaScript
{ name: "ship_via", width: 85, align: "center", formatter: "select", autoResizing: { minColWidth: 85 },
	edittype: "select", editoptions: { value: "FE:FedEx;TN:TNT;DH:DHL", defaultValue: "DH" },
	stype: "select", searchoptions: { sopt: ["eq", "ne"], noFilterText: "Any" } }
```
instead of
```JavaScript
{ name: "ship_via", width: 85, align: "center", formatter: "select", autoResizing: { minColWidth: 85 },
	edittype: "select", editoptions: { value: "FE:FedEx;TN:TNT;DH:DHL", defaultValue: "DH" },
	stype: "select", searchoptions: { sopt: ["eq", "ne"], value: ":Any;FE:FedEx;TN:TNT;DH:DHL" } }
```
Additional advantage of the usage of `noFilterText: "Any"`: the option `<option value="">Any</option>` will be included as the first entry *only in the filter toolbar*. The searching dialog will don't have the option.

The new default option `loadFilterDefaults: true` of the filter toolbar is very practical if one uses both the Filter Toolbar and the Searching Dialog in one grid. Setting of a simple filter via the Searching Dialog will be displayed in the filter toolbar. Another important case is loading the grid with the filter applied.

[The demo](http://www.ok-soft-gmbh.com/jqGrid/OK/formEditOnDoubleClick-jqueryui-fa1.htm) and [another one](http://www.ok-soft-gmbh.com/jqGrid/OK/formEditOnDoubleClick-jqueryui-fa-bootstrap.htm), which uses Bootsrap GUI style, use the following options:
```JavaScript
search: true,
postData: {
    filters: {
        groupOp: "AND",
        rules: [
            { op: "le", field: "tax", data: "20" },
            { op: "gt", field: "amount", data: "250" }
        ]
    }
}
```
and the filter will be displayed in the filter toolbar immediately during creating.

**BREAKING CHANGES**: The ruls of building ids of input elements of the filter toolbar are changed. Free jqGrid uses now the rule: `"gs_" + gridId + columnName` instead of using `"gs_" + columnName` before. It fixes problems, like the usage of jQuery UI Datepicker for the second grid on the page, in case of using the filter toolbar by multiple grids and having the same column names. The new option `idMode` allows to manage the rule of building ids. The option `idMode: "old"` forces the usage of old rule (`"gs_" + columnName`) for building of ids. The option `idMode: "compatibility"` creates the ids based on another rule: `"gs_" + idPrefix + columnName`. The rule would be helpful only if one uses `idPrefix` options with *different* `idPrefix` values for every grid. One can still have id duplicates if one don't use any `idPrefix` on multiple grids. The default value `idMode: "new"` uses `"gs_" + gridId + columnName`. It garantees, that there are no id duplicates in multiple grids.

We recommend everybody to scan your existing code for the string `gs_`, which you could use for direct accessing of input fields of the filter toolbar. If no such places will be found, then you can be sure that your code will have no compatibility problems. If you will find such places in your code then you can decide yourself, whether to change the code and to include the grid id in your code or to force the usage of the "old" or the "compatibility" rule (`idMode: "old"` or `idMode: "compatibility"`). To force compatibility rule you can add the following code
```JavaScript
$.jgrid = $.jgrid || {};
$.jgrid.search = $.jgrid.search || {};
$.jgrid.search.idMode = "compatibility"; // or "old" instead of default "new", which creates safe id values
```
somewhere at the beginning of your code. Alternatively you can use `searching: {idMode: "compatibility"}` option (or `searching: {idMode: "old"}` option).

### The below is the full list of changes in the version 4.13.1 compared with 4.13.0:

* Bug fix of the usage of `idPrefix` in the form editing.
* The ruls of building ids of input elements of the filter toolbar are changed. The new `idMode` of `filterToolbar` allows to customize the changes or to force the usage of old id rules.
* New `loadFilterDefaults: true` option and `jqGridRefreshFilterValues` event added. It force filling the filter toolbar based of the filter applied to the grid. Only `postData.filters` will be supported.
* Remove some additional places which works only for IE7 and lower.
* Fix styling of legacy/simple subgrid for Bootstrap style.
* Bug fix of compatibility to IE8 in `formatter: "checkbox"`.
* Improvement performance of scrolling.
* Add new `noFilterText` property of `searchoptions`, which allows to add the entry in the filter to clear filtering by the column.
* Properties of `searchoptions` can be skipped if the same properties exists in `editoptions` and if `stype: "select"`.
* Add normalizing of undefined values of the column with `formatter: "number"` or `formatter: "integer"`. Undefiled data for such cloumns will be interpreted now correctly as `0` during filtering.
* remove usage global `jQuery` variable for local filtering. `jQuery.noConflict` is full supported now.
* Bug fix in `getFullTreeNode` in case of usage numeric `id` (instead of string `id`).
* Updated bootstrap styling for the "loading" mesage.

Other readmes contain the list of the features and bug fixed implemented before:

* [README4.13.0.md](https://github.com/free-jqgrid/jqGrid/blob/master/README4.13.0.md) contains the readme of free jqGrid 4.13.0.
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
