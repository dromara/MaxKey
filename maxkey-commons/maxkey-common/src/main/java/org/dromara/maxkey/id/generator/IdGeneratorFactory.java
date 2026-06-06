/*
 * Copyright [2026] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.id.generator;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.dromara.maxkey.id.generator.impl.RandomStringGenerator;
import org.dromara.maxkey.id.generator.impl.SnowFlakeIdGenerator;
import org.dromara.maxkey.id.generator.impl.UuidStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdGeneratorFactory {
	private static final  Logger logger = LoggerFactory.getLogger(IdGeneratorFactory.class);
	
	static ConcurrentMap<String, IdGenerator> idGeneratorMap = new ConcurrentHashMap<>();
    
    public static class GeneratorStrategy {
        public static final String UUID         = "uuid";
        public static final String RANDOM       = "random";
        public static final String SNOWFLAKE    = "snowflake";
    }
    
    String strategy = GeneratorStrategy.SNOWFLAKE;

    static {
        register(GeneratorStrategy.UUID         , new UuidStringGenerator());
        register(GeneratorStrategy.RANDOM       , new RandomStringGenerator());
        register(GeneratorStrategy.SNOWFLAKE    , new SnowFlakeIdGenerator());
    }
    
    public static void register(String strategy, IdGenerator generator) {
        strategy = strategy.toLowerCase();
        idGeneratorMap.put(strategy, generator);
        logger.debug( "Register IdGenerator strategy {} -> {}", strategy, generator.getClass().getName() );
    }
   
    public String generate() {
        return this.generate(strategy);
    }
    
    public String generate(String strategy){
        if(idGeneratorMap.containsKey(strategy.toLowerCase())) {
            return idGeneratorMap.get(strategy.toLowerCase()).generate();
        }
        return idGeneratorMap.get(GeneratorStrategy.RANDOM).generate();
    }
    
    public IdGeneratorFactory() {
        super();
    }
    
    public IdGeneratorFactory(String strategy) {
        super();
        this.strategy = strategy.toLowerCase();
    }
}
