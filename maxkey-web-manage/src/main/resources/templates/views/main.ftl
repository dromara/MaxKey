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
						<div class="row">
							<div class="col-lg-3 col-md-6 col-xs-12">
								<div class="info-box bg-primary">
									<div class="icon-box">
										<i class="lni-home"></i>
									</div>
									<div class="info-box-content">
										<h4 class="number">${rptOnlineUsers}</h4>
										<p class="info-text"><@locale code="main.rpt.onlineuser"/></p>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-6 col-xs-12">
								<div class="info-box bg-success">
									<div class="icon-box">
										<i class="lni-tag"></i>
									</div>
									<div class="info-box-content">
										<h4 class="number">${rptDayCount}</h4>
										<p class="info-text"><@locale code="main.rpt.daycount"/></p>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-6 col-xs-12">
								<div class="info-box bg-info">
									<div class="icon-box">
										<i class="lni-cart"></i>
									</div>
									<div class="info-box-content">
										<h4 class="number">${rptNewUsers}</h4>
										<p class="info-text"><@locale code="main.rpt.newuser"/></p>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-6 col-xs-12">
								<div class="info-box bg-purple">
									<div class="icon-box">
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
												<#list rptApp as apps><tr><td>${apps.APPNAME}</td><td>${apps.REPORTCOUNT}</td></tr></#list>
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
												<#list rptBrowser as browser><tr><td>${browser.REPORTSTRING}</td><td>${browser.REPORTCOUNT}</td></tr></#list>
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
			labels: [<#list rptMonth as month>'${month.REPORTSTRING}',</#list>],
			datasets: [{
				label:'<@locale code="main.rpt.count"/>',
				backgroundColor: 'rgb(75, 192, 192)',
				borderColor: 'rgb(75, 192, 192)',
				fill: false,
				data: [<#list rptMonth as month>${month.REPORTCOUNT},</#list>],
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
				yAxes: [{
					display: true,
					stacked: true
				}]
			}
		}
	};
	var configDayHour = {
			type: 'line',
			data: {
				labels: [<#list rptDayHour as dayHour>'${dayHour.REPORTSTRING}',</#list>],
				datasets: [{
					label:'<@locale code="main.rpt.count"/>',
					backgroundColor: 'rgb(178 ,34, 34)',
					borderColor: 'rgb(178 ,34, 34)',
					fill: false,
					data: [<#list rptDayHour as dayHour>${dayHour.REPORTCOUNT},</#list>],
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
					yAxes: [{
						display: true,
						stacked: true
					}]
				}
			}
		};
	window.onload = function() {
		var ctx = document.getElementById('canvasMonth').getContext('2d');
		window.myLineMonth = new Chart(ctx, configMonth);
		
		var ctxdh = document.getElementById('canvasDayHour').getContext('2d');
		window.myLineDayHour = new Chart(ctxdh, configDayHour);
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