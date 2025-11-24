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
 

/**
 * 
 */
package org.dromara.maxkey.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 20位的流水号
 * 8位系统日期YYYYMMDD+2位节点号+6位时间戳为HHMMSS+4位顺序流水号。
 * 其中4位顺序流水号要求为“数值格式，位数不足左补零，各系统顺序生成”,为了避免顺序号重复，4位流水为该秒内的顺序流水，即每秒内每个节点最多1万笔交易
 * 
 * @author Crystal.sea
 *
 */
public class IdTimeSequence {
    private static final  Logger logger = LoggerFactory.getLogger(IdTimeSequence.class);
    
    /**
     * 默认节点编码
     */
    public static final String DEFAULT_NODE_NUMBER    = "01";
    /**
     * 历史的时间 yyyyMMddHHmmss 20250101010101
     */
    public static String OLD_DATETIME           = "";

    /**
     * 节点编码
     */
    public static String NODE_NUMBER     = "--";
    /**
     * 静态属性
     */
    public static int   STATIC_SEQUENCE         = 0;
    
 
    
    /**
     * 生成20位的流水号
     * @return 流水号
     */
    public static synchronized String next(){
        String currentDateTime = getSystemDateTime();
        StringBuilder sequenceNumber = new StringBuilder();
        sequenceNumber.append(currentDateTime.substring(0, 8))
                      .append(getNodeNumber())
                      .append(currentDateTime.substring(8))
                      .append(nextSequence());
        return sequenceNumber.toString();
    }
    
    public static final String initNode(String nodeNumber){
        if(NODE_NUMBER.equals("--")){
            if(StringUtils.isNotBlank(nodeNumber) 
                    && StringUtils.length(nodeNumber) == 2){
                NODE_NUMBER = nodeNumber;
            }else if(NODE_NUMBER.length()!=2){
                    logger.error("系统节点号必须2位");
            }else{
                NODE_NUMBER = DEFAULT_NODE_NUMBER;
            }
            logger.info("NODE_NUMBER : {}",NODE_NUMBER);
        }
        return NODE_NUMBER;
    }
    
    public static final String getNodeNumber(){
        return NODE_NUMBER;
    }
    /**
     * 同一时刻只有一个访问
     * @return
     */
    private static final  synchronized String nextSequence(){
        STATIC_SEQUENCE = (STATIC_SEQUENCE + 1 ) %10000;
        return String.format("%04d", STATIC_SEQUENCE);
    }
    
    /**
     * 获取系统当前日期，格式为yyyyMMddHHmmSS
     * @return 当前系统日期
     */
    private static  synchronized String  getSystemDateTime(){
        String currentDateTime = (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
        /**
         * 判断是否是新的时间，如果是新时间则STATIC_SEQUENCE从0开始计数
         */
        if(!currentDateTime.equals(OLD_DATETIME)){
            STATIC_SEQUENCE = 0;
            OLD_DATETIME    = currentDateTime;
        }
        return currentDateTime;
    }
}
