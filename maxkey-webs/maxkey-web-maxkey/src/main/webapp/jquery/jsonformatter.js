function ClearTextarea() {
  $("textarea").val("");
}

function FormatJSON(oData, sIndent) {
  if (arguments.length < 2) {
    var sIndent = "";
  }
  var sIndentStyle = "    ";
  var sDataType = RealTypeOf(oData);

  // open object
  if (sDataType == "array") {
    if (oData.length == 0) {
      return "[]";
    }
    var sHTML = "[";
  } else {
    var iCount = 0;
    $.each(oData, function() {
      iCount++;
      return;
    });
    if (iCount == 0) { // object is empty
      return "{}";
    }
    var sHTML = "{";
  }

  // loop through items
  var iCount = 0;
  $.each(oData, function(sKey, vValue) {
    if (iCount > 0) {
      sHTML += ",";
    }
    if (sDataType == "array") {
      sHTML += ("\n" + sIndent + sIndentStyle);
    } else {
      sHTML += ("\n" + sIndent + sIndentStyle + "\"" + sKey + "\"" + ": ");
    }

    // display relevant data type
    switch (RealTypeOf(vValue)) {
      case "array":
      case "object":
        sHTML += FormatJSON(vValue, (sIndent + sIndentStyle));
        break;
      case "boolean":
      case "number":
        sHTML += vValue.toString();
        break;
      case "null":
        sHTML += "null";
        break;
      case "string":
        sHTML += ("\"" + vValue + "\"");
        break;
      default:
        sHTML += ("TYPEOF: " + typeof(vValue));                                                                                              }
        // loop
        iCount++;
    }
  )

  // close object
  if (sDataType == "array") {
    sHTML += ("\n" + sIndent + "]");
  } else {
    sHTML += ("\n" + sIndent + "}");
  }

  // return
  return sHTML;
}

function FormatTextarea() {
  var sJSON = $("textarea").val();
  if (sJSON.length > 0) {
    try {
      var oJSON = JSON.parse(sJSON);
      if ( $("#SORT").attr("checked") == true ) {
        oJSON = SortObject(oJSON);
      }
      $("textarea").val( FormatJSON(oJSON) );
    } catch(sError) {
      alert("You are attempting to parse invalid JSON.")
    }
  } else {
    alert("<Bono>I still haven't found what I'm looking for</Bono>");
  }
}

function FullSize() {
  $("#FULLSIZE").hide();
  var iTopPos = ($("textarea").position()).top;
  $(window).scrollTop( iTopPos );
  var iBottomPos = (($("#jsonformat").position()).top + $("#jsonformat").outerHeight());
  $("#json").animate({ height: ($("#json").height() + ($(window).height() - (iBottomPos - iTopPos) + 2)) }, 500);
}

function LoadJSON() {
  var sURL = $("#URL").val();
  if (sURL.length > 7) {
    // get height of the load form
    var iHeight = $("#jsonload").height();
    $("#jsonload").hide();
    $("#jsonthrobber").show().css("height", (iHeight + 10) + "px");

    // do ajax call
    $.ajax({
      type: "POST",
      cache: false,
      url: "../getJSON.php",
      data: ("url=" + sURL + "&method=" + $("#METHOD").val() + "&data=" + encodeURIComponent($("#DATA").val())),
      dataType: "text",
      error: function() {
        alert('Ooops, something went wrong.');
        $("#jsonthrobber").hide();
        $("#jsonload").show();
      },
      success: function(sJSON) {
        $("textarea").val(sJSON);
        if ( $("#AUTO").attr("checked") == true ) {
          FormatTextarea();
        }
        $("#jsonthrobber").hide();
        $("#jsonload").show();
      }
    });
  } else {
    alert("Invalid URL entered.");
  }
}

function RealTypeOf(v) {
  if (typeof(v) == "object") {
    if (v === null) return "null";
    if (v.constructor == (new Array).constructor) return "array";
    if (v.constructor == (new Date).constructor) return "date";
    if (v.constructor == (new RegExp).constructor) return "regex";
    return "object";
  }
  return typeof(v);
}

function SortObject(oData) {
  var oNewData = {};
  var aSortArray = [];

  // sort keys
  $.each(oData, function(sKey) {
    aSortArray.push(sKey);
  });
  aSortArray.sort(SortLowerCase);

  // create new data object
  $.each(aSortArray, function(i) {
    if( RealTypeOf(oData[(aSortArray[i])]) == "object" ) {
      oData[(aSortArray[i])] = SortObject(oData[(aSortArray[i])]);
    }
    oNewData[(aSortArray[i])] = oData[(aSortArray[i])];
  });

  return oNewData;

  function SortLowerCase(a,b) {
    a = a.toLowerCase();
    b = b.toLowerCase();
    return ((a < b) ? -1 : ((a > b) ? 1 : 0));
  }
}