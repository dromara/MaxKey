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
 

package org.dromara.maxkey.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 描述: Twitter的分布式自增ID雪花算法snowflake (Java版)
 *
 * @author Crystal.Sea
 * @create 2021-04-17
 **/
public class SnowFlakeId {

    /**
     * 起始的时间戳
     */
    private static final  long START_STMP = 1480166465631L;

    /**
     * 每一部分占用的位数
     */
    private static final  long SEQUENCE_BIT = 12; //序列号占用的位数
    private static final  long MACHINE_BIT = 5;   //机器标识占用的位数
    private static final  long DATACENTER_BIT = 5;//数据中心占用的位数

    /**
     * 每一部分的最大值
     */
    private static final  long MAX_DATACENTER_NUM = -1L ^ (-1L << DATACENTER_BIT);
    private static final  long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private static final  long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private static final  long MACHINE_LEFT = SEQUENCE_BIT;
    private static final  long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final  long TIMESTMP_LEFT = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳
    private String dateTime;

    public SnowFlakeId(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public SnowFlakeId(long datacenterId, long machineId, long sequence, long lastStmp) {
		super();
		this.datacenterId = datacenterId;
		this.machineId = machineId;
		this.sequence = sequence;
		this.lastStmp = lastStmp;
		dateTime =DateUtils.toUtc( fromatTime(lastStmp));
	}

	/**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = getNewstmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = currStmp;

        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }
    
    
    public  long currId() {
        long currStmp = lastStmp;
        
        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }
    

    private long getNextMill() {
        long mill = getNewstmp();
        while (mill <= lastStmp) {
            mill = getNewstmp();
        }
        return mill;
    }

    private long getNewstmp() {
        return System.currentTimeMillis();
    }
    
    public SnowFlakeId parse(long id) {
        String sonwFlakeId = Long.toBinaryString(id);
        System.out.println(sonwFlakeId);
        int len = sonwFlakeId.length();
        int sequenceStart = (int) (len < MACHINE_LEFT ? 0 : len - MACHINE_LEFT);
        int workerStart = (int) (len < DATACENTER_LEFT ? 0 : len - DATACENTER_LEFT);
        int timeStart = (int) (len < TIMESTMP_LEFT ? 0 : len - TIMESTMP_LEFT);
        String sequence = sonwFlakeId.substring(sequenceStart, len);
        String workerId = sequenceStart == 0 ? "0" : sonwFlakeId.substring(workerStart, sequenceStart);
        String dataCenterId = workerStart == 0 ? "0" : sonwFlakeId.substring(timeStart, workerStart);
        String time = timeStart == 0 ? "0" : sonwFlakeId.substring(0, timeStart);
        int sequenceInt = Integer.valueOf(sequence, 2);
        int workerIdInt = Integer.valueOf(workerId, 2);
        int dataCenterIdInt = Integer.valueOf(dataCenterId, 2);
        long diffTime = Long.parseLong(time, 2);
        long timeLong = diffTime + START_STMP;
        
        SnowFlakeId snowFlakeIdParse =new SnowFlakeId(dataCenterIdInt,workerIdInt,sequenceInt,timeLong);
        
        return snowFlakeIdParse;
    }

    private static Date fromatTime(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar.getTime();
    }
    
    

    public long getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(long datacenterId) {
		this.datacenterId = datacenterId;
	}

	public long getMachineId() {
		return machineId;
	}

	public void setMachineId(long machineId) {
		this.machineId = machineId;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public long getLastStmp() {
		return lastStmp;
	}

	public void setLastStmp(long lastStmp) {
		this.lastStmp = lastStmp;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
