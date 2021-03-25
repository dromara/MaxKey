package org.maxkey;

import java.sql.SQLException;

import org.apache.commons.text.StringEscapeUtils;

public class EscapeHtml4Test {
	public static void main(String[] args) throws SQLException {
		String value="<IMG SRC=javascript:alert('XSS')<javascript>>";
		System.out.println(StringEscapeUtils.escapeHtml4(value));
		System.out.println(StringEscapeUtils.escapeEcmaScript(value));
	}
}
