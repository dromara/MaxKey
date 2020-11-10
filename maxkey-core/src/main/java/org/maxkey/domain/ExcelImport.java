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
 

package org.maxkey.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.mybatis.jpa.persistence.JpaBaseDomain;
import org.springframework.web.multipart.MultipartFile;

/**
 * .
 * @author Crystal.Sea
 * 
 */
public class ExcelImport extends JpaBaseDomain {

    /**
     * 
     */
    private static final long serialVersionUID = 4665009770629818479L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    String id;
 
    @JsonIgnore
    protected MultipartFile excelFile;

    public ExcelImport() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MultipartFile getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(MultipartFile excelFile) {
        this.excelFile = excelFile;
    }
 

}
