<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
if(request.getParameter("language")!=null && request.getParameter("language").equals("en_US")){
	response.sendRedirect(path+"/page/samlv20/saml1_en.jsp?language=en_US");	
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="/common/head.jsp"/>
</head>

<body>
<jsp:include page="/common/top.jsp"></jsp:include>
<div id="container">
 <jsp:include page="/common/left.jsp">
 	<jsp:param value="idm-pvsrule" name="pageType"/>
 </jsp:include>
  <!-- treeView end -->
  <div class="content">
  	<h1>账号供应策略</h1>
    <div class="text-section">
     <p class="section">账号供应策略是用户信息发生变动后，对不同的应用系统账号进行变更同步，变更触发的策略可以分为三种，1、即时供应策略；2、用户触发策略；3、定时任务策略。
     		<br>
     		</p>
     <p class="section">用户账号同步的条件策略是对符合条件的用户账号进行同步，条件策略的规则在定制连接器Connector中设定，在此不再详述。</p>
    </div><!-- 一段描述结束 -->

    <h3>即时供应(Just in Time)</h3>
 	<p class="section">
 		即时供应-Just in Time;当用户信息发生变更时，账号实时进行同步到相应的应用系统中，实效性高。
 	</p>
 	<h3>用户触发(User Manual)</h3>
	<p class="section">
		用户触发-User Manual;同步需要管理员手动触发，否则不会执行同步动作。
	</p>
	<h3>定时任务(CronTab)</h3>
	<p class="section">
		定时任务-CronTab；即定时同步，用户信息变更按照固定周期性进行规律性的同步，管理员需要按照定时的规则设定一个合理的同步时间点，
		在此时间前做的变动不做同步，当时间达到此时间时，系统自动对所有的变动信息进行批量同步。
		<br/>
		同步时间规则
		<table class="basisTable" cellspacing="1">
			<thead>
				<tr>
					<th>秒</th><th>分钟</th><th>小时</th><th>日期</th><th>月份</th><th>周</th><th>年份</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>0-59</td><td>0-59</td><td>0-23</td><td>1-31</td><td>1-12</td><td>1-7</td><td>空,1970-2099</td>
				</tr>
			</tbody>
		</table>
		<br>
		常用通配符
		<table class="basisTable" cellspacing="1">
			<thead>
				<tr>
					<th>序号</th><th>通配符</th><th>作用</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td><td>*(星号)</td><td>代表任何时刻都接受</td>
				</tr>
				<tr>
					<td>2</td><td>-(减号)</td><td>指定一个值的范围</td>
				</tr>
				<tr>
					<td>3</td><td>,(逗号)</td><td>指定数个值</td>
				</tr>				
				<tr>
					<td>4</td><td>/(斜线)</td><td>指定一个值的增加幅度。n/m表示从n开始，每次增加m</td>
				</tr>
				<tr>
					<td>5</td><td>L</td><td>用在日表示一个月中的最后一天，用在周表示该月最后一个星期X</td>
				</tr>
				<tr>
					<td>6</td><td>W</td><td>指定离给定日期最近的工作日(周一到周五)</td>
				</tr>
				<tr>
					<td>7</td><td>#</td><td>表示该月第几个周X。6#3表示该月第3个周五</td>
				</tr>
				<tr>
					<td>8</td><td>?</td><td>表示不确定的值</td>
				</tr>
			</tbody>
		</table>	
		<br/>
		表达式范例：<br/>
<table class="basisTable" cellspacing="1">
			<thead>
				<tr>
					<th>序号</th><th>描述</th><th>表达式</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>1</td><td>每隔5秒执行一次</td><td>*/5 * * * * ?</td>
				</tr>
				<tr>
					<td>2</td><td>每隔1分钟执行一次</td><td>0 */1 * * * ?</td>
				</tr>
				<tr>
					<td>3</td><td>每天23点执行一次</td><td>0 0 23 * * ?</td>
				</tr>				
				<tr>
					<td>4</td><td>每天凌晨1点执行一次</td><td>0 0 1 * * ?</td>
				</tr>
				<tr>
					<td>5</td><td>每月1号凌晨1点执行一次</td><td>0 0 1 1 * ?</td>
				</tr>
				<tr>
					<td>6</td><td>每月最后一天23点执行一次</td><td>0 0 23 L * ?</td>
				</tr>
				<tr>
					<td>7</td><td>每周星期天凌晨1点实行一次</td><td>0 0 1 ? * L</td>
				</tr>
				<tr>
					<td>8</td><td>在26分、29分、33分执行一次</td><td>0 26,29,33 * * * ?</td>
				</tr>
				<tr>
					<td>9</td><td> 每天的0点、13点、18点、21点都执行一次</td><td>0 0 0,13,18,21 * * ?</td>
				</tr>
			</tbody>
		</table>
	</p>
	
 </div>
 <!-- content end -->
 <!-- //content end -->
<div class="clear"></div>
</div>
<div id="footer">
	<jsp:include page="/common/footer.jsp"/>
</div>
</body>
</html>
