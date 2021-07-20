<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<#include  "layout/header.ftl"/>
	<#include  "layout/common.cssjs.ftl"/>
	<style>
	canvas {
		-moz-user-select: none;
		-webkit-user-select: none;
		-ms-user-select: none;
	}
	</style>
</head>
<body> 
<div class="app header-default side-nav-dark">
<div class="layout">
	<div class="header navbar">
		<#include  "layout/top.ftl"/>
	</div>
	
	<div class="col-md-3 sidebar-nav side-nav" >
		<#include  "layout/sidenav.ftl"/>
	</div>
<div class="page-container">

<div class="main-content">
					<div class="container-fluid">

						<div class="breadcrumb-wrapper row">
							<div class="col-12 col-lg-3 col-md-6">
								<h4 class="page-title"><@locale code="navs.home"/></h4>
							</div>
							<div class="col-12 col-lg-9 col-md-6">
								<ol class="breadcrumb float-right">
									<li><a href="<@base/>/main"><@locale
											code="navs.system"/></a></li>
									<li class="active">/ <@locale code="navs.home"/></li>
								</ol>
							</div>
						</div>

					</div>
<div class="container-fluid">
						<div class="row" style="height:115px; padding-top: 10px;">
							<div class="col-lg-3 col-md-6 col-xs-12">
								<div class="card text-white bg-primary">
									<div class="card-body card-body pb-0 d-flex justify-content-between align-items-start">
										<i class="lni-home"></i>
									</div>
									<div class="info-box-content">
										<h4 class="number">${rptOnlineUsers}</h4>
										<p class="info-text"><@locale code="main.rpt.onlineuser"/></p>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-6 col-xs-12">
								<div class="card text-white bg-info">
									<div class="card-body card-body pb-0 d-flex justify-content-between align-items-start">
										<i class="lni-tag"></i>
									</div>
									<div class="info-box-content">
										<h4 class="number">${rptDayCount}</h4>
										<p class="info-text"><@locale code="main.rpt.daycount"/></p>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-6 col-xs-12">
								<div class="card text-white  bg-warning">
									<div class="card-body card-body pb-0 d-flex justify-content-between align-items-start">
										<i class="lni-cart"></i>
									</div>
									<div class="info-box-content">
										<h4 class="number">${rptNewUsers}</h4>
										<p class="info-text"><@locale code="main.rpt.newuser"/></p>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-6 col-xs-12">
								<div class="card text-white  bg-danger">
									<div class="card-body card-body pb-0 d-flex justify-content-between align-items-start">
										<i class="lni-wallet"></i>
									</div>
									<div class="info-box-content">
										<h4 class="number">${rptActiveUsers}</h4>
										<p class="info-text"><@locale code="main.rpt.activeuser"/></p>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="card">
									<div class="card-header">
										<h5 class="card-title"><@locale code="main.rpt.dayhour"/></h5>
										<div class="float-right">
											<ul class="list-inline d-none d-sm-block">
												<li><span class="status bg-primary"></span> <span
													class="text-semibold"></span></li>
												<li><span class="status bg-success"></span> <span
													class="text-semibold"></span></li>
											</ul>
										</div>
									</div>
									<div class="card-body">
										<div id="morris-line-example">
											<canvas id="canvasDayHour" style="height: 400px; width: 98%;"></canvas>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="card">
									<div class="card-header">
										<h5 class="card-title"><@locale code="main.rpt.month"/></h5>
										<div class="float-right">
											<ul class="list-inline d-none d-sm-block">
												<li><span class="status bg-primary"></span> <span
													class="text-semibold"></span></li>
												<li><span class="status bg-success"></span> <span
													class="text-semibold"></span></li>
											</ul>
										</div>
									</div>
									<div class="card-body">
										<div id="morris-line-example">
											<canvas id="canvasMonth" style="height: 400px; width: 98%;"></canvas>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="card">
									<div class="card-header">
										<h5 class="card-title"><@locale code="main.rpt.appaccess"/></h5>
										<div class="float-right">
											<ul class="list-inline d-none d-sm-block">
												<li><span class="status bg-primary"></span> <span
													class="text-semibold"></span></li>
												<li><span class="status bg-success"></span> <span
													class="text-semibold"></span></li>
											</ul>
										</div>
									</div>
									<div class="card-body">
										<div id="morris-line-example">
											<table  class="table table-bordered" >
												<tr><td><@locale code="main.rpt.app"/></td><td><@locale code="main.rpt.count"/></td></tr>
												<#list rptApp as apps><tr><td>${apps.appname}</td><td>${apps.reportcount}</td></tr></#list>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="card">
									<div class="card-header">
										<h5 class="card-title"><@locale code="main.rpt.browseraccess"/></h5>
										<div class="float-right">
											<ul class="list-inline d-none d-sm-block">
												<li><span class="status bg-primary"></span> <span
													class="text-semibold"></span></li>
												<li><span class="status bg-success"></span> <span
													class="text-semibold"></span></li>
											</ul>
										</div>
									</div>
									<div class="card-body">
										<div id="morris-line-example">
											<table  class="table table-bordered" >
												<tr><td><@locale code="main.rpt.browser"/></td><td><@locale code="main.rpt.count"/></td></tr>
												<#list rptBrowser as browser><tr><td>${browser.reportstring}</td><td>${browser.reportcount}</td></tr></#list>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
<script>
	var configMonth = {
		type: 'line',
		data: {
			labels: [<#list rptMonth as month>'${month.reportstring}',</#list>],
			datasets: [{
				label:'<@locale code="main.rpt.count"/>',
				backgroundColor: 'rgb(75, 192, 192)',
				borderColor: 'rgb(75, 192, 192)',
				fill: false,
				data: [<#list rptMonth as month>${month.reportcount},</#list>],
			}]
		},
		options: {
			responsive: true,
			title: {
				display: true
				//,text: '访问情况'
			},
			scales: {
				xAxes: [{
					display: true,
				}],
				y: {
                        title: {
                            display: true
                          },
                         ticks: {
                            precision : 0
                        },
                        beginAtZero: true
                    }
			}
		}
	};
	var configDayHour = {
			type: 'line',
			data: {
				labels: [<#list rptDayHour as dayHour>'${dayHour.reportstring}',</#list>],
				datasets: [{
					label:'<@locale code="main.rpt.count"/>',
					backgroundColor: 'rgb(178 ,34, 34)',
					borderColor: 'rgb(178 ,34, 34)',
					fill: false,
					data: [<#list rptDayHour as dayHour>${dayHour.reportcount},</#list>],
				}]
			},
			options: {
				responsive: true,
				title: {
					display: true
					//,text: '访问情况'
				},
				scales: {
					xAxes: [{
						display: true
					}],
					y: {
					    title: {
                            display: true
                          },
                         ticks: {
                            precision : 0
                         /*
                            callback: function(value, index, values) {
                                return  parseInt(value);
                            }*/
                        },
                        beginAtZero: true
                    }
				}
			}
		};
		
	window.onload = function() {
	    var ctxdh = document.getElementById('canvasDayHour').getContext('2d');
        window.myLineDayHour = new Chart(ctxdh, configDayHour);
        
        
		var ctx = document.getElementById('canvasMonth').getContext('2d');
		window.myLineMonth = new Chart(ctx, configMonth);
		
		
	};

	</script>	
	<footer class="content-footer">
		<#include  "layout/footer.ftl"/>
	</footer>

</div>

</div>
</div>

<div id="preloader">
<div class="loader" id="loader-1"></div>
</div>

</body>
</html>