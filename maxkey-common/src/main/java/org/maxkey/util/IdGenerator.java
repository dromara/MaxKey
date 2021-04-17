package org.maxkey.util;

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
