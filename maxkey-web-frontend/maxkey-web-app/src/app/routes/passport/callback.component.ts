import { Inject, Optional, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
    private router: Router,
    private socialsProviderService: SocialsProviderService,
    private settingsService: SettingsService,
    private authenticationService: AuthenticationService,
    @Optional()
    @Inject(ReuseTabService)
    private reuseTabService: ReuseTabService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.provider = this.route.snapshot.params['provider'];
    if (!this.settingsService.user.name) {
      this.socialsProviderService.callback(this.provider, this.route.snapshot.queryParams).subscribe(res => {
        if (res.code === 0) {
          // 清空路由复用信息
          this.reuseTabService.clear();
          // 设置用户Token信息
          this.authenticationService.auth(res.data);
        }
        this.authenticationService.navigate({});
      });
    } else {
      this.socialsProviderService.bind(this.provider, this.route.snapshot.queryParams).subscribe(res => {
        if (res.code === 0) {
        }
        this.router.navigateByUrl('/config/socialsassociate');
      });
    }
  }
}
