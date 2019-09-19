
<script type="text/javascript" src="<@base/>/jquery/echarts-2.2.1/echarts-all.js"></script>

<script type="text/javascript">	
	$(function () {
	
	});
</script>

<div>

	<div id="tool_box">
		<table  class="datatable">
			<tr>
				<td  width="120px">
			 		 <@locale code="common.text.date"/>:
				</td>
				<td  width="375px">
					<form id="report_search_form" action="<@base/>/report/login/month">
							<input class="datepicker" name="reportDate" type="text"  style ="width:150px" value="${reportDate}" >
				 			<input class="button primary"  id="reportSearchBtn" type="submit" size="50" value="<@locale code="button.text.search"/>">
				 	</form>
				</td>
				<td colspan="2"> 
					 
				</td>
			</tr>
		</table>
 		
		
 	</div>
 	
	<div class="mainwrap" id="mainChart" style="height:450px"></div>	
    
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
		        data:['bar','line']
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31']
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name : 'bar',
		            axisLabel : {
		                formatter: '{value} '
		            }
		        },
		        {
		            type : 'value',
		            name : 'line',
		            axisLabel : {
		                formatter: '{value} '
		            }
		        }
		    ],
		    series : [
		        {
		            name:'bar',
		            type:'bar',
		            data:${jsonMonthReport},
		            itemStyle : { normal: {label : {show: true, position: 'top'}}},
		        },
		        {
		            name:'line',
		            type:'line',
		            yAxisIndex: 1,
		            data:${jsonMonthReport}
		        }
		    ]
		};
                    
	
	    // 为echarts对象加载数据 
	    myChart.setOption(option); 
	</script>		
</div>