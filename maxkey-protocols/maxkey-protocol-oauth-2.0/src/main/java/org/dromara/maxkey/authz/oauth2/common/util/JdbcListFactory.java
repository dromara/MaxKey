/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.maxkey.authz.oauth2.common.util;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Dave Syer
 * 
 */
public interface JdbcListFactory {

    /**
     * @param sql
     * @param parameters
     * @return a list of {@link T}
     */
    <T> List<T> getList(String sql, Map<String, Object> parameters, RowMapper<T> rowMapper);

}
