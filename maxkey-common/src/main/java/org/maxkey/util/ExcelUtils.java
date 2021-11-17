package org.maxkey.util;

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
