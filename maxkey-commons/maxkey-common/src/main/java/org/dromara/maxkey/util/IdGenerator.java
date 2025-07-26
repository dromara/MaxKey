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

public class IdGenerator {

	String strategy = "uuid";

	int datacenterId;
	
	int machineId;
	
	SnowFlakeId snowFlakeId = new SnowFlakeId(0,0);
	
	StringGenerator stringGenerator = new StringGenerator();

	
	public String generate(){
		if(strategy.equalsIgnoreCase("uuid")) {
			return stringGenerator.uuidGenerate();
		}else if(strategy.equalsIgnoreCase("SnowFlake")) {
			return snowFlakeId.nextId()+"";
		}else {
			return stringGenerator.randomGenerate();
		}
	}
	
	
	public IdGenerator() {
		super();
	}
	
	public IdGenerator(String strategy) {
		super();
		this.strategy = strategy;
	}


	public int getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(int datacenterId) {
		this.datacenterId = datacenterId;
	}

	public int getMachineId() {
		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	public SnowFlakeId getSnowFlakeId() {
		return snowFlakeId;
	}

	public void setSnowFlakeId(SnowFlakeId snowFlakeId) {
		this.snowFlakeId = snowFlakeId;
	}

	public StringGenerator getStringGenerator() {
		return stringGenerator;
	}

	public void setStringGenerator(StringGenerator stringGenerator) {
		this.stringGenerator = stringGenerator;
	}

}
