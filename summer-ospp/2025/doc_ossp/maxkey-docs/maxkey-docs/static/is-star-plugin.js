// 

// 应用参数 
const client_id = 'ee6fdc484b3398d17e77d6ff37fd8b9fe502106398c7b22bf5522d3c01303f45';
const client_secret = '2574c2aac8ce2142e34752dc5957dddcb30bc68df5c61de64251a3a6b11a51e5';
var redirect_uri = 'https://www.maxkey.top/doc/docs/overview/intro/';
const docDomain = 'maxkey.top';
if(location.host.indexOf("sso.maxkey.top")>-1) {
	redirect_uri = 'http://sso.maxkey.top/maxkey/';
}

// const redirect_uri = 'http://127.0.0.1:8848/sa-token-doc/doc.html';
// const docDomain = '127.0.0.1:8848';
		
// 检查成功后，多少天不再检查 
const allowDisparity = 1000 * 60 * 60 * 24 * 30;
// const allowDisparity = 1000 * 10;


// 判断当前是否已 star
function isStarRepo() {
	
	// 判断是否在主域名下
	if(location.host.substring(docDomain)<=-1) {
		console.log('非主域名，不检测... , host '+location.host);
		return;
	}
	console.log('主域名检测 , host '+location.host);
	// 判断是否近期已经判断过了
	try{
		const isStarRepo = localStorage.isStarRepo;
		if(isStarRepo) {
			// 记录 star 的时间，和当前时间的差距
			const disparity = new Date().getTime() - parseInt(isStarRepo);
			
			// 差距小于一月，不再检测，大于一月，再检测一下
			if(disparity < allowDisparity) {
				console.log('checked ...');
				return;
			}
		}
	}catch(e){
		console.error(e);
	}
	
	// 开始获取 code 
	$('body').css({'overflow': 'hidden'});
	getCode();
}
		
// 去请求授权
function getCode() {
	
	// 检查url中是否有code
	const code = getParam('code');
	if(code) {
		// 有 code，进一步去请求 access_token
		getAccessToken(code);
	} else {
		// 不存在code，弹窗提示询问
		confirmStar();
	}
}

// 弹窗提示点 star 
function confirmStar() {
	
	// 弹窗提示文字 
	const tipStr = `
		<div>
			<p><b>嗨，同学，来支持一下 MaxKey 吧，为项目点个 star ！</b></p>
			<div>仅需两步即可完成：<br>
				<div>1、打开 MaxKey <a href="https://gitee.com/dromara/MaxKey" target="_blank">开源仓库主页</a>，在右上角点个 star 。</div>
				<div>2、点击下方 [ 同意授权检测 ] 按钮，同意 MaxKey 获取 API 权限进行检测。</div>
			</div>
			<p><b>文档或演示系统将在 star 后正常开放展示</b></p>
		</div>
		`;
	
	const index = layer.confirm(tipStr, {
			title: '提示', 
			btn: ['同意授权检测'], 
			// btn: ['同意授权检测', '暂时不要，我先看看文档'], 
			area: '450px', 
			offset: '25%',
			closeBtn: false
		}, 
		function(index) {
			// 
			layer.close(index);
			// 用户点了确认，去 gitee 官方请求授权获取
			goAuth();
		}
	);
	const closeLayer = 
	`	
		<!-- 
			↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓
			 在 f12 控制台 执行一下：
				 localStorage.isStarRepo = new Date().getTime()
			 即可取消弹窗 ，执行完刷新一下页面
			↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑  ↑
		-->
	`;
	$('#layui-layer' + index).prepend(closeLayer)
}


// 跳转到 gitee 授权界面
function goAuth() {
	const authUrl = "https://gitee.com/oauth/authorize" +
					"?client_id=" + client_id + 
					"&redirect_uri=" + redirect_uri + 
					"&response_type=code";
	location.href = authUrl;
}


// 获取 access_token 
function getAccessToken(code) {
	// 根据 code 获取 access_token
	$.ajax({
		url: 'https://www.maxkey.top/gitst/oauth/token',
		method: 'post',
		data: {
			grant_type: 'authorization_code',
			code: code,
			client_id: client_id,
			redirect_uri: redirect_uri,
			client_secret: client_secret,
		},
		success: function(res) {
			// 如果返回的不是 200
			if(res.code !== 200) {
				return layer.alert(res.msg, {closeBtn: false}, function(){
					// 刷新url，去掉 code 参数 
					location.href = 'doc.html';
				});
			}
			
			// 拿到 access_token 
			const access_token = res.access_token;
			
			// 根据 access_token 判断是否 star 了仓库
			$.ajax({
				url: 'https://gitee.com/api/v5/user/starred/dromara/MaxKey',
				method: 'get',
				data: {
					access_token: access_token
				},
				success: function(res) {
					// success 回调即代表已经 star，gitee API 请求体不返回任何数据
					console.log('-> stared ...');
					// 记录本次检查时间 
					localStorage.isStarRepo = new Date().getTime();
					// 
					layer.alert('感谢你的支持  ❤️ ❤️ ❤️ ，MaxKey 将努力变得更加完善！', function(index) {
						layer.close(index);
						// 刷新url，去掉 code 参数 
						location.href = location.href.replace("?code=" + code, '');
					})
				},
				error: function(e) {
					// console.log('ff请求错误 ', e);
					// 如下返回，代表没有 star 
					if(e.statusText = 'Not Found'){
						console.log('not star ...');
						layer.alert('未检测到 star 数据...', {closeBtn: false}, function() {
							// 刷新url，去掉 code 参数 
							location.href = location.href.replace("?code=" + code, '');
						});
					}
				}
			});
			
		},
		error: function(e) {
			console.log('请求错误 ', e);
			// 如果请求地址有错，可能是服务器宕机了，暂停一天检测
			if(e.status === 0 || e.status === 502) {
				return layer.alert(JSON.stringify(e), {closeBtn: false}, function(){
					// 一天内不再检查 
					const ygTime = allowDisparity - (1000 * 60 * 60 * 24);
					localStorage.isStarRepo = new Date().getTime() - ygTime;
					// 刷新 url，去掉 code 参数 
					location.href = location.href.replace("?code=" + code, '');
				});
			}
			
			// 无效授权，可能是 code 无效 
			const errorMsg = (e.responseJSON && e.responseJSON.error) || JSON.stringify(e);
			if(errorMsg == 'invalid_grant') {
				console.log('无效code', code);
			}
			layer.alert('check error... ' + errorMsg, function(index) {
				layer.close(index);
				// 刷新url，去掉 code 参数 
				let url = location.href.replace("?code=" + code, '');
				url = url.replace("&code=" + code, '');
				location.href = url;
			});
		}
	})
}

// 疑问
function authDetails() {
	const str = "用于检测的凭证信息将仅保存你的浏览器本地，MaxKey 文档已完整开源，源码可查";
	alert(str);
}

// 获取 url 携带的参数 
function getParam(name, defaultValue){
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i=0;i<vars.length;i++) {
		var pair = vars[i].split("=");
		if(pair[0] == name){return pair[1];}
	}
	return(defaultValue == undefined ? null : defaultValue);
}
	
$(document).ready(function () {	
	isStarRepo();
});