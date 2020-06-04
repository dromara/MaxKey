<h3>验证码</h3>

<b>验证码（CAPTCHA）</b>是“Completely Automated Public Turing test to tell Computers and Humans Apart”（全自动区分计算机和人类的图灵测试）的缩写，是一种区分用户是计算机还是人的公共全自动程序。可以防止用户用特定程序暴力破解方式进行不断的登陆尝试。

<b>文本验证码</b>

<img src="{{ "/images/authn/captcha_text.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>

<b>算术验证码</b>

<img src="{{ "/images/authn/captcha_arithmetic.png" | prepend: site.baseurl }}?{{ site.time | date: "%Y%m%d%H%M" }}"  alt=""/>


<h3>验证码配置</h3>

文件
maxkey/maxkey.properties

<pre><code class="ini hljs">
#是否支持验证码
config.login.captcha=true
#text 文本， arithmetic 算术验证码
config.login.captcha.type=text
</code></pre>

<h3>验证码配置文件</h3>
MaxKey使用kaptcha作为验证码的插件，详细可参考Kaptchar详细配置表

文件
maxkey/kaptcha.properties

默认配置如下

<pre><code class="ini hljs">
#宽度
kaptcha.image.width=80
#长度
kaptcha.image.height=25
kaptcha.border=no
kaptcha.obscurificator.impl=com.google.code.kaptcha.impl.ShadowGimpy
kaptcha.textproducer.font.size=23
#生成字符，默认值为数字
kaptcha.textproducer.char.string=0123456789
#位数
kaptcha.textproducer.char.length=4
kaptcha.noise.impl=com.google.code.kaptcha.impl.NoNoise
</code></pre>


当配置算术验证码时即 config.login.captcha.type=arithmetic

kaptcha的配置中必须为数字

<pre><code class="ini hljs">
kaptcha.textproducer.char.string=0123456789
</code></pre>


<h3>Kaptchar详细配置表</h3>

<table border="0" class="table table-striped table-bordered ">
	<tbody>
		<tr>
			<td><strong>Constant</strong></td>
			<td><strong>描述</strong></td>
			<td><strong>默认值</strong></td>
		</tr>
		<tr>
			<td>kaptcha.border</td>
			<td>图片边框，合法值：yes , no</td>
			<td>yes</td>
		</tr>
		<tr>
			<td>kaptcha.border.color</td>
			<td>边框颜色，合法值： r,g,b (and optional alpha) 或者 white,black,blue.</td>
			<td>black</td>
		</tr>
		<tr>
			<td>kaptcha.border.thickness</td>
			<td>边框厚度，合法值：&gt;0</td>
			<td>1</td>
		</tr>
		<tr>
			<td>kaptcha.image.width</td>
			<td>图片宽</td>
			<td>200</td>
		</tr>
		<tr>
			<td>kaptcha.image.height</td>
			<td>图片高</td>
			<td>50</td>
		</tr>
		<tr>
			<td>kaptcha.producer.impl</td>
			<td>图片实现类</td>
			<td>com.google.code.kaptcha.impl.DefaultKaptcha</td>
		</tr>
		<tr>
			<td>kaptcha.textproducer.impl</td>
			<td>文本实现类</td>
			<td>com.google.code.kaptcha.text.impl.DefaultTextCreator</td>
		</tr>
		<tr>
			<td>kaptcha.textproducer.char.string</td>
			<td>文本集合，验证码值从此集合中获取</td>
			<td>abcde2345678gfynmnpwx</td>
		</tr>
		<tr>
			<td>kaptcha.textproducer.char.length</td>
			<td>验证码长度</td>
			<td>5</td>
		</tr>
		<tr>
			<td>kaptcha.textproducer.font.names</td>
			<td>字体</td>
			<td>Arial, Courier</td>
		</tr>
		<tr>
			<td>kaptcha.textproducer.font.size</td>
			<td>字体大小</td>
			<td>40px.</td>
		</tr>
		<tr>
			<td>kaptcha.textproducer.font.color</td>
			<td>字体颜色，合法值： r,g,b &nbsp;或者 white,black,blue.</td>
			<td>black</td>
		</tr>
		<tr>
			<td>kaptcha.textproducer.char.space</td>
			<td>文字间隔</td>
			<td>2</td>
		</tr>
		<tr>
			<td>kaptcha.noise.impl</td>
			<td>干扰实现类</td>
			<td>com.google.code.kaptcha.impl.DefaultNoise</td>
		</tr>
		<tr>
			<td>kaptcha.noise.color</td>
			<td>干扰&nbsp;颜色，合法值： r,g,b 或者 white,black,blue.</td>
			<td>black</td>
		</tr>
		<tr>
			<td>kaptcha.obscurificator.impl</td>
			<td>图片样式：&nbsp;<br>
				水纹com.google.code.kaptcha.impl.WaterRipple&nbsp;<br>
				鱼眼com.google.code.kaptcha.impl.FishEyeGimpy<br>
				阴影com.google.code.kaptcha.impl.ShadowGimpy
			</td>
			<td>com.google.code.kaptcha.impl.WaterRipple</td>
		</tr>
		<tr>
			<td>kaptcha.background.impl</td>
			<td>背景实现类</td>
			<td>com.google.code.kaptcha.impl.DefaultBackground</td>
		</tr>
		<tr>
			<td>kaptcha.background.clear.from</td>
			<td>背景颜色渐变，开始颜色</td>
			<td>light grey</td>
		</tr>
		<tr>
			<td>kaptcha.background.clear.to</td>
			<td>背景颜色渐变，&nbsp;结束颜色</td>
			<td>white</td>
		</tr>
		<tr>
			<td>kaptcha.word.impl</td>
			<td>文字渲染器</td>
			<td>com.google.code.kaptcha.text.impl.DefaultWordRenderer</td>
		</tr>
		<tr>
			<td>kaptcha.session.key</td>
			<td>session key</td>
			<td>KAPTCHA_SESSION_KEY</td>
		</tr>
		<tr>
			<td>kaptcha.session.date</td>
			<td>session date</td>
			<td>KAPTCHA_SESSION_DATE</td>
		</tr>
	</tbody>
</table>