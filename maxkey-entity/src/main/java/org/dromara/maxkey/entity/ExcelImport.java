/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dromara.mybatis.jpa.entity.JpaEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * .
 * @author Crystal.Sea
 * 
 */
public class ExcelImport extends JpaEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 4665009770629818479L;

    @Id
    @Column
    @GeneratedValue
    String id;
 
    @JsonIgnore
    protected MultipartFile excelFile;
    
    
    String updateExist;

    InputStream inputStream = null;
    
    Workbook 	workbook 	= null;
    
    public ExcelImport() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdateExist() {
        return updateExist;
    }

    public void setUpdateExist(String updateExist) {
        this.updateExist = updateExist;
    }

    public MultipartFile getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(MultipartFile excelFile) {
        this.excelFile = excelFile;
    }
 
    public boolean isExcelNotEmpty() {
    	return excelFile != null && !excelFile.isEmpty() ;
    }
    
    
    public Workbook biuldWorkbook() throws IOException {
    	workbook = null;
    	inputStream = excelFile.getInputStream();
    	if (excelFile.getOriginalFilename().toLowerCase().endsWith(".xls")) {
    		workbook = new HSSFWorkbook(inputStream);
        } else if (excelFile.getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
        	workbook = new XSSFWorkbook(inputStream);
        } else {
            throw new RuntimeException("Excel suffix error.");
        }
    	return workbook;
    }
    
    public void closeWorkbook() {
    	if (inputStream != null) {
            try {
            	inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(workbook != null) {
            try {
            	workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
