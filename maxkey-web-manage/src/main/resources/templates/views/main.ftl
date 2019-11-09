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
 <div class="swlink">Collect from <a href="http://www.scnoob.com/" title="网站模板">网站模板</a></div>

<div class="page-container">

<div class="main-content">
<div class="container-fluid">

<div class="breadcrumb-wrapper row">
<div class="col-12 col-lg-3 col-md-6">
<h4 class="page-title">Dashboard 2</h4>
</div>
<div class="col-12 col-lg-9 col-md-6">
<ol class="breadcrumb float-right">
<li><a href="index.html">Dashboard</a></li>
<li class="active"> / Dashboard 2</li>
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
<h4 class="number">1125</h4>
<p class="info-text">用户在线</p>
</div>
</div>
</div>
<div class="col-lg-3 col-md-6 col-xs-12">
<div class="info-box bg-success">
<div class="icon-box">
<i class="lni-tag"></i>
</div>
<div class="info-box-content">
<h4 class="number">351</h4>
<p class="info-text">当天访问量</p>
</div>
</div>
</div>
<div class="col-lg-3 col-md-6 col-xs-12">
<div class="info-box bg-info">
<div class="icon-box">
<i class="lni-cart"></i>
</div>
<div class="info-box-content">
<h4 class="number">774</h4>
<p class="info-text">当月新用户</p>
</div>
</div>
</div>
<div class="col-lg-3 col-md-6 col-xs-12">
<div class="info-box bg-purple">
<div class="icon-box">
<i class="lni-wallet"></i>
</div>
<div class="info-box-content">
<h4 class="number">49450</h4>
<p class="info-text">本月活跃用户</p>
</div>
</div>
</div>
</div>
<div class="row">
<div class="col-md-12">
<div class="card">
<div class="card-header">
<h5 class="card-title">本月访问情况</h5>
<div class="float-right">
<ul class="list-inline d-none d-sm-block">
<li>
<span class="status bg-primary"></span>
<span class="text-semibold"></span>
</li>
<li>
<span class="status bg-success"></span>
<span class="text-semibold"></span>
</li>
</ul>
</div>
</div>
<div class="card-body">
<div id="morris-line-example" >
<canvas id="canvas" style="height: 400px;width:98%;"></canvas>
</div>
</div>
</div>
</div>
</div>
	<script>
	var randomScalingFactor = function() {
		return Math.ceil(Math.random() * 10.0) * Math.pow(10, Math.ceil(Math.random() * 5));
	};

	var config = {
		type: 'line',
		data: {
			labels: ['1', '2', '3', '4', '5', '6', '7','8','9','10',
					'11','12','13','14','15','16','17','18','19','20',
					'21','22','23','24','25','26','27','28','29','30','31'],
			datasets: [{
				label:"访问量",
				backgroundColor: 'rgb(75, 192, 192)',
				borderColor: 'rgb(75, 192, 192)',
				fill: false,
				data: [
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor(),
					randomScalingFactor()
				],
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
					type: 'logarithmic',
				}]
			}
		}
	};

	window.onload = function() {
		var ctx = document.getElementById('canvas').getContext('2d');
		window.myLine = new Chart(ctx, config);
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