/*
 * Copyright [2021] [MaxKey of copyright http://www.maxkey.top]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 

package org.dromara.maxkey;

import java.sql.SQLException;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.boot.actuate.endpoint.ApiVersion;

public class EscapeHtml4Test {
	public static void main(String[] args) throws SQLException {
		String value="<IMG SRC=javascript:alert('XSS')<javascript>>";
		System.out.println(StringEscapeUtils.escapeHtml4(value));
		System.out.println(StringEscapeUtils.escapeEcmaScript(value));
		System.out.println(ApiVersion.V2.getProducedMimeType().toString());
	}
}
