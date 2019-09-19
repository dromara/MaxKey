
<script type="text/javascript" src="<@base/>/jquery/echarts-2.2.1/echarts-all.js"></script>

<script type="text/javascript">	
	$(function () {
	
	});
</script>

<div>
<form id="report_search_form" action="<@base/>/report/login/app">
	<div id="tool_box">
		<table  class="datatable">
			<tr>
				<td  width="120px">
			 		 <@locale code="common.text.startdate"/>
				</td>
				<td  width="375px">
					
					<input  id="datepickerstart"  name="startDate" type="text"  style ="width:150px" value="${startDate}" >
							
				</td>
				<td colspan="2"> 
					 <@locale code="common.text.enddate"/>
					 <input  id="datepickerend"  name="endDate" type="text"  style ="width:150px" value="${endDate}" >
				 	 <input class="button primary"  id="reportSearchBtn" type="submit" size="50" value="<@locale code="button.text.search"/>">
				 	
				</td>
			</tr>
		</table>
 		
		
 	</div>
 </form> 	
	<div class="mainwrap" id="mainChart" style="height:450px;"></div>	
    
	<script type="text/javascript">
	    // 基于准备好的dom，初始化echarts图表
	    var myChart = echarts.init(document.getElementById('mainChart'),'macarons'); 
	    
	   option = {
		    tooltip : {
		        trigger: 'axis'
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: false},
		            dataView : {show: false, readOnly: false},
		            magicType: {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    legend: {
		        data:['bar']
		    },
		    yAxis : [
		        {
		            type : 'category',
		            data : ${categoryReport}
		        }
		    ],
		    xAxis : [
		        {
		            type : 'value',
		            name : 'bar',
		            axisLabel : {
		                formatter: '{value} '
		            }
		        }
		    ],
		    series : [
		        {
		            name:'bar',
		            type:'bar',
		            data:${jsonReport},
		            itemStyle : { normal: {label : {show: true, position: 'right'}}},
		        }
		    ]
		};
                    
	
	    // 为echarts对象加载数据 
	    myChart.setOption(option); 
	</script>		
</div>