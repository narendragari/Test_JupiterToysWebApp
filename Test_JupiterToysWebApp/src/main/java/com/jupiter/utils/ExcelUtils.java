package com.jupiter.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {


	public static String getTestCaseName(String sTestCase) throws Exception {
		String value = sTestCase;
		try {
			int position = value.indexOf("@");
			value = value.substring(0, position);
			position = value.lastIndexOf(".");	
			value = value.substring(position + 1);
			return value;

		}catch (Exception e) {
			throw (e);
		}

	}



	/**
	 * 
	 * Excel Data will be handled through below code
	 * 
	 * **/

	Workbook wb;
	Sheet sheet;
	Map<String, List<Object[]>> dataList;
	String strClassName;
	
	// constructor
	public ExcelUtils() {
		this.wb = null;
		this.sheet = null;
		this.dataList = null;
	}
	
	
	
	public Iterator<Object[]> dataProvider(final String methodName, final String className) {
		final File file = new File("."+PropertiesUtility.TEST_DATA_FILE);
		if (!file.exists()) {
			throw new CustomException("Data file does not exist");
		}
		try {
			this.wb = WorkbookFactory.create(file);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		this.sheet = this.wb.getSheet(className);

		final List<Object[]> list = this.readData(methodName);
		return list.iterator();
	}

	private List<Object[]> readData(final String methodName) {
		if (this.dataList == null || this.dataList.isEmpty()) {
			this.dataList = new LinkedHashMap<String, List<Object[]>>();
		}
		if (!this.dataList.containsKey(methodName.toUpperCase())) {
			final List<Object[]> dataForMethod = new LinkedList<Object[]>();
			List<String> data = null;
			Row row = null;
			Cell cell = null;
			for (int i = 1; i <= this.sheet.getLastRowNum(); ++i) {
				row = this.sheet.getRow(i);
				if (row != null && row.getLastCellNum() > 0) {
					cell = row.getCell(1);
					if (cell != null) {
						// cell.setCellType(1);
						if (cell.getStringCellValue().trim().equals(methodName.trim())) {
							data = new LinkedList<String>();
							for (int j = 2; j < row.getLastCellNum(); ++j) {
								cell = row.getCell(j);
								if (cell != null) {
									// cell.setCellType(1);
									data.add(cell.getStringCellValue());
								}
							}
							if (data != null && !data.isEmpty()) {
								Object[] actualData = new Object[data.size()];
								for (int k = 0; k < data.size(); ++k) {
									actualData[k] = data.get(k);
								}
								data.clear();
								dataForMethod.add(actualData);
								actualData = null;
							}
							this.dataList.put(methodName.toUpperCase(), dataForMethod);
						}
					}
				}
			}
		}
		final List<Object[]> list1 = this.dataList.get(methodName.toUpperCase());
		if (list1 != null) {
			return list1;
		}
		throw new CustomException("No data available for test method name : " + methodName);
	}


}