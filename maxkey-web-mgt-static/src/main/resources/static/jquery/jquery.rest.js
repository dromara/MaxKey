;(function($) {
	/*
	 * Copyright (c) 2011 Lyconic, LLC.
	 *
	 * Permission is hereby granted, free of charge, to any person obtaining a copy
	 * of this software and associated documentation files (the "Software"), to deal
	 * in the Software without restriction, including without limitation the rights
	 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	 * copies of the Software, and to permit persons to whom the Software is
	 * furnished to do so, subject to the following conditions:
	 *
	 * The above copyright notice and this permission notice shall be included in
	 * all copies or substantial portions of the Software.
	 *
	 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
	 * THE SOFTWARE.
	 */

	    var _ajax = $.ajax;

	    // Will only use method override if $.restSetup.useMethodOverride is set to true
	    // Change the values of this global object if your method parameter is different.
	    $.restSetup = { 
	      methodParam: '_method',
	      useMethodOverride: false,
	      verbs: {
	        create:  'POST',
	        update:  'PUT',
	        destroy: 'DELETE'
	      }
	    };

	    // collect csrf param & token from meta tags if they haven't already been set
	    $(document).ready(function(){
	      $.restSetup.csrfParam = $.restSetup.csrfParam || $('meta[name=csrf-param]').attr('content');
	      $.restSetup.csrfToken = $.restSetup.csrfToken || $('meta[name=csrf-token]').attr('content');
	    });

	    function collect_options (url, data, success, error) {
	      var options = { dataType: 'json' };
	      if (arguments.length === 1 && typeof arguments[0] !== "string") {
	        options = $.extend(options, url);
	        if ("url" in options)
	        if ("data" in options) {
	          fill_url(options.url, options.data);
	        }
	      } else {
	        // shift arguments if data argument was omitted
	        if ($.isFunction(data)) {
	          error = success;
	          success = data;
	          data = null;
	        }

	        url = fill_url(url, data);

	        options = $.extend(options, {
	          url: url,
	          data: data,
	          success: function (data, text, xhr) {
	            if (success) success.call(options.context || options, data, get_headers(xhr), xhr);
	          },
	          error: function (xhr) {
	            if (error) error.call(options.context || options, xhr, get_headers(xhr));
	          }
	        });
	      }
	      return options;
	    }

	    function fill_url (url, data) {
	      var key, u, val;
	      for (key in data) {
	        val = data[key];
	        u = url.replace('{'+key+'}', val);
	        if (u != url) {
	          url = u;
	          delete data[key];
	        }
	      }
	      return url;
	    }

	    function get_headers(xhr) {
	      // trim the headers because IE likes to include the blank line between the headers
	      // and the content as part of the headers
	      var headers = {}, stringHeaders = $.trim(xhr.getAllResponseHeaders());
	      $.each(stringHeaders.split("\n"), function (i, header) {
	        if (header.length) {
	          var matches = header.match(/^([\w\-]+):(.*)/);
	          if (matches.length === 3) headers[ matches[1] ] = $.trim(matches[2]);
	        }
	      });
	      xhr.responseHeaders = headers;
	      return headers;
	    }

	    $.ajax = function (settings) {
	      var csrfParam = new RegExp("(" + $.restSetup.csrfParam + "=)", "i"),
	          userBeforeSend = settings.beforeSend,
	          methodOverride;

	      if (typeof settings.data !== "string")
	      if (settings.data != null) {
	          settings.data = $.param(settings.data);
	      }

	      settings.data = settings.data || "";
	      if ($.restSetup.csrfParam && $.restSetup.csrfToken)
	      if (!/^(get)$/i.test(settings.type))
	      if (!csrfParam.test(settings.data)) {
	          settings.data += (settings.data ? "&" : "") + $.restSetup.csrfParam + '=' + $.restSetup.csrfToken;
	      }

	      if ($.restSetup.useMethodOverride)
	      if (!/^(get|post)$/i.test(settings.type)) {
	          methodOverride = settings.type.toUpperCase();
	          settings.data += (settings.data ? "&" : "") + $.restSetup.methodParam + '=' + settings.type.toLowerCase();
	          settings.type = "POST";
	      }

	      settings.beforeSend = function (xhr, ajaxSettings) {
	        var context = settings.context || settings,
	            contentType = settings.contentType,
	            resourceContentType = /.*\.(json|xml)/i.exec(settings.url);

	        if (!contentType) contentType = $.restSetup.contentType;
	        if (!contentType && resourceContentType) {
	          contentType = 'application/' + resourceContentType[1].toLowerCase();
	        }
	        if (settings.contentType != contentType) $.extend(settings, { contentType: contentType });

	        if ( methodOverride ) xhr.setRequestHeader('X-HTTP-Method-Override', methodOverride);

	        if ( $.isFunction(userBeforeSend) ) userBeforeSend.call(context, xhr, ajaxSettings);
	     };

	      return _ajax.call(this, settings);
	    };

	    $.read = function () {
	      var options = collect_options.apply(this, arguments);
	      options.type = 'GET';
	      return $.ajax(options);
	    };

	    $.create = function () {
	      var options = collect_options.apply(this, arguments);
	      options.type = $.restSetup.verbs.create;
	      return $.ajax(options);
	    };

	    $.update = function () {
	      var options = collect_options.apply(this, arguments);
	      options.type = $.restSetup.verbs.update;
	      return $.ajax(options);
	    };

	    $.destroy = function () {
	      var options = collect_options.apply(this, arguments);
	      options.type = $.restSetup.verbs.destroy;
	      return $.ajax(options);
	    };

});