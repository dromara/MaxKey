/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class ExcelUtils {

	/**
	 * 根据数据格式返回数据
	 *
	 * @param cell
	 * @return
	 */
	public static String getValue(Cell cell) {

		if (cell == null) {
			return "";
		} else if (cell.getCellType() == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == CellType.NUMERIC) {
			if ("General".equals(cell.getCellStyle().getDataFormatString())) {
				return new DecimalFormat("0").format(cell.getNumericCellValue());
			} else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
				return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
			} else {
				return new DecimalFormat("0").format(cell.getNumericCellValue());
			}
		} else {
			return String.valueOf(cell.getStringCellValue().trim());
		}
	}
	
	public static String getValue(Row row,int i) {
		return getValue(row.getCell(i));
	}
}
