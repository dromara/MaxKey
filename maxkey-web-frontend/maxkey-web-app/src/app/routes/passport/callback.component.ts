import { Inject, Optional, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReuseTabService } from '@delon/abc/reuse-tab';
import { SettingsService } from '@delon/theme';

import { AuthenticationService } from '../../service/authentication.service';
import { SocialsProviderService } from '../../service/socials-provider.service';

@Component({
  selector: 'app-callback',
  template: ``
})
export class CallbackComponent implements OnInit {
  provider = '';

  constructor(
    private socialsProviderService: SocialsProviderService,
    private settingsSrv: SettingsService,
    private authenticationService: AuthenticationService,
    @Optional()
    @Inject(ReuseTabService)
    private reuseTabService: ReuseTabService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.provider = this.route.snapshot.params['provider'];
    this.socialsProviderService.callback(this.provider, this.route.snapshot.queryParams).subscribe(res => {
      if (res.code === 0) {
        // 清空路由复用信息
        this.reuseTabService.clear();
        // 设置用户Token信息
        this.authenticationService.auth(res.data);
      }
      this.authenticationService.navigate({});
    });
  }
}
