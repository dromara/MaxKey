
<div class="container">
	<div class="row">
			<div class="col-sm-6">
				<div style="float:left;margin-left:20px;margin-top: 5px;"><IMG SRC="<@locale code="global.logo"/>" style="width:55px;heigth:55px"></div>
				<div style="margin-top:15px;margin-left:10px;float:left">
					<div style="letter-spacing:2px;font-size:24px;font-weight:bolder;"><@locale code="global.title"/></div>
				</div>
			</div>
			<div class="col-sm-2"></div>
			<div class="col-sm-4">
				<div style="margin-top:30px;margin-right:10px;float:right;">
				    <div class="dropdown">
                        <button class="btn dropdown-toggle" type="button" id="dropdownLanguage" data-bs-toggle="dropdown" aria-expanded="false">
                            <@locale code="global.language"/> 
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownLanguage">
                            <li><a class="dropdown-item" href="<@currUrl/>?language=zh_CN"><@locale code="global.change.language.zh"/></a></li>
                            <li><a class="dropdown-item" href="<@currUrl/>?language=en"><@locale code="global.change.language.en"/></a></li>
                        </ul>
                    </div>
				</div>
			</div>
	</div>
</div>
