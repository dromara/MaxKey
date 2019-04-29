package com.connsec.desktop.login;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author Crystal.Sea
 *
 */
public class ExecuteSimuKey {
	ExeParam exeParam;
	SimulationKeyboard simulationKeyboard;
	
	public ExecuteSimuKey() {
		
	}
	
	public ExecuteSimuKey(String jsonParam) {
		simulationKeyboard=new SimulationKeyboard();
		
		exeParam=JsonResolve.resolve(jsonParam);
	}
	
	public void executetProgram(){
		final String execPath=exeParam.getProgramPath()+" " +(exeParam.getParameter()==null?"":exeParam.getParameter());
		System.out.println(execPath);
		 AccessController.doPrivileged(new PrivilegedAction<Object>() {
           public Object run() {
               try{
                   Runtime.getRuntime().exec(execPath);
                   simulationKeyboard.getRobot().delay(8000);
               }catch (Exception e){
                   System.out.println("Caught exception in privileged block, Exception:" + e.toString());
               }
               return null; // nothing to return
           }
       });    
	}
	
	public void simulationUsername(){

		if(exeParam.getUsernameType().equalsIgnoreCase("SIMULATION")) {
			if(null!=exeParam.getPreUsername()&&!"".equals(exeParam.getPreUsername())){
				pressKey(exeParam.getPreUsername());
			}
			pressKey(exeParam.getUsername());
		}
	}
	
	public void simulationPassword(){
		if(exeParam.getPasswordType().equalsIgnoreCase("SIMULATION")) {
			if(null!=exeParam.getPrePassword()&&!"".equals(exeParam.getPrePassword())){
				pressKey(exeParam.getPrePassword());
			}
			pressKey(exeParam.getPassword());
		}
	}
	
	public void enterProgram(){
		if(null!=exeParam.getPreSubmit()&&!"".equals(exeParam.getPreSubmit())){
			pressKey(exeParam.getPreSubmit());
		}
		
		if("Enter".equalsIgnoreCase(exeParam.getSubmitType())){
			simulationKeyboard.pressEnter();
		}else if("Key".equalsIgnoreCase(exeParam.getSubmitType())){
			if(null==exeParam.getSubmitKey()||"".equals(exeParam.getSubmitKey())){
				System.out.println("SubmitType is Key , SubmitKey can not be null or empty. ");
			}
			simulationKeyboard.pressAltKey(exeParam.getSubmitKey().charAt(0));
		}
	}
	
	public void executeAll(){

		executetProgram();
		
		simulationUsername();
		
		simulationPassword();
		
		enterProgram();
	}
	
	private void pressKey(String key){
		char[] pressKeyStr = key.toCharArray();
		for (int i = 0; i < pressKeyStr.length; i++) {
			simulationKeyboard.type(pressKeyStr[i]);
		}
	}

}
