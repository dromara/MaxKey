<!DOCTYPE html>
<html>
	<head>
	  	<#include  "formbased_common.ftl">
		<script>
		 
			function sleep(milliseconds) {
			  var start = new Date().getTime();
			  for (var i = 0; i < 1e7; i++) {
				if ((new Date().getTime() - start) > milliseconds){
				  break;
				}
			  }
			}
			function closeWindow() {
				window.opener=null;
				window.close();
			}
	
			var cDiskCommand="C:\\Program Files (x86)\\Tencent\\QQ\\Bin\\QQ.exe";
			
			var command=cDiskCommand;
			var FileSystemObject=new ActiveXObject("Scripting.FileSystemObject");
			var WScriptShell = new ActiveXObject('WScript.Shell');
			var WScript	=	new ActiveXObject("WScript.Network");
			var sktimeout;
			
			function simulationKeyboard() {
				clearTimeout(sktimeout);
				//if not Keyboard layout code in decimal 00000409 ( U.S. English )
				var defaultcode=WScriptShell.RegRead("HKCU\\Keyboard Layout\\Preload\\1");
				if(defaultcode=="00000409"||defaultcode=="00000804"){
					
				}else{
					WScriptShell.SendKeys("^ ");
				}
				WScriptShell.SendKeys("{TAB}");sleep(100);WScriptShell.SendKeys("{TAB}");sleep(100);
				WScriptShell.SendKeys("{TAB}");sleep(100);WScriptShell.SendKeys("{TAB}");sleep(100);
				WScriptShell.SendKeys("{TAB}");sleep(100);WScriptShell.SendKeys("{TAB}");sleep(100);
				WScriptShell.SendKeys("{TAB}");sleep(100);WScriptShell.SendKeys("{TAB}");sleep(100);
				WScriptShell.SendKeys("{TAB}");sleep(100);WScriptShell.SendKeys("{TAB}");sleep(100);
				WScriptShell.SendKeys("{TAB}");sleep(100);WScriptShell.SendKeys("{TAB}");sleep(200);
				//WScriptShell.SendKeys("{TAB}");sleep(200);
				WScriptShell.SendKeys("${username}");
				sleep(100);
				WScriptShell.SendKeys("{TAB}");
				//WScriptShell.SendKeys("{ENTER}");
				//WScriptShell.SendKeys("${password}");
				sleep(100);
				var password="${password}";
				for(var i=0;i<password.length;i++){
					WScriptShell.SendKeys(password.charAt(i));
					sleep(100);
				}
				
				sleep(400);
				WScriptShell.SendKeys("{ENTER}");
				sleep(200);
				//close window after 4s
				setTimeout(closeWindow,4000);
			}
			var erpInstalled=false;
			
			if(FileSystemObject.fileExists(command)){
				erpInstalled=true;
			}
			
			if(erpInstalled){
				if (WScriptShell){
					var oExec =  WScriptShell.Run("\""+command);
					
					sktimeout=setTimeout(simulationKeyboard,5000);
				}
			}else{
				alert("QQ客户端未安装，请先安装QQ.");
				//window.location.href="http://im.qq.com/pcqq/";
			}
		</script>
	</head>
  
	<body>
		<div class="progress_div">
			<div class="progress_bar"></div>
			<div class="progress_bar_text">系统加载中，请勿操作电脑。。。</div>
			<div>出现"是否停止运行此脚本?"的提示</div>
			<div>
				<a href="http://download.microsoft.com/download/5/9/5/595D11B8-A0FD-4EA0-BF0D-F113258FC28A/MicrosoftFixit50403.msi">
					下载补丁MicrosoftFixit50403.msi
				</a>
			</div>
		</div>
  	</body>
</html>
