import { Component, inject, signal } from '@angular/core';
import { RouterLink, RouterOutlet, Router } from '@angular/router';
import { DA_SERVICE_TOKEN } from '@delon/auth';
import { I18nPipe, SettingsService } from '@delon/theme';
import { SettingDrawerModule } from '@delon/theme/setting-drawer';
import { ThemeBtnComponent } from '@delon/theme/theme-btn';
import { environment } from '@env/environment';
import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzDropdownModule } from 'ng-zorro-antd/dropdown';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzMenuModule } from 'ng-zorro-antd/menu';

import { HeaderClearStorage } from './widgets/clear-storage';
import { HeaderFullScreen } from './widgets/fullscreen';
import { HeaderI18n } from './widgets/i18n';
import { HeaderIcon } from './widgets/icon';
import { HeaderNotify } from './widgets/notify';
import { HeaderRTL } from './widgets/rtl';
import { HeaderSearch } from './widgets/search';
import { HeaderTask } from './widgets/task';
import { HeaderUser } from './widgets/user';
import { AuthnService } from '../../service/authn.service';
import { CONSTS } from '../../shared/consts';
import { knowHost } from '../../shared/utils/knowhost';
import { LayoutDefaultModule, LayoutDefaultOptions } from '../../theme/layout-default';

@Component({
  selector: 'layout-basic',
  templateUrl: './basic.html',
  imports: [
    RouterOutlet,
    RouterLink,
    I18nPipe,
    LayoutDefaultModule,
    NzIconModule,
    NzMenuModule,
    NzDropdownModule,
    NzAvatarModule,
    SettingDrawerModule,
    ThemeBtnComponent,
    //HeaderSearch,
    //HeaderNotify,
    //HeaderTask,
    //HeaderIcon,
    HeaderRTL,
    HeaderI18n,
    HeaderClearStorage,
    HeaderFullScreen,
    HeaderUser
  ]
})
export class LayoutBasic {
  CONSTS = CONSTS;
  inst: any;
  product = { category: 'Evaluation', verify: false };
  copyrightYear = new Date().getFullYear();
  readonly user = inject(SettingsService).user;
  private readonly router = inject(Router);
  readonly authnService = inject(AuthnService);

  private readonly tokenService = inject(DA_SERVICE_TOKEN);
  protected options: LayoutDefaultOptions = {
    logoExpanded: `./assets/logo-full.svg`,
    logoCollapsed: `./assets/logo.svg`
  };
  protected searchToggleStatus = signal(false);
  protected showSettingDrawer = !environment.production;

  constructor() {
    this.inst = this.authnService.getInst();
    if (this.inst == null) {
      this.inst = { custom: false };
      this.authnService.initInst().subscribe(res => {
        this.authnService.setInst(res.data, !knowHost());
        this.inst = this.authnService.getInst();
      });
    }
  }

  profile(): void {
    this.router.navigateByUrl('/config/profile');
  }

  changePassword(): void {
    this.router.navigateByUrl('/config/password');
  }
  logout(): void {
    this.authnService.logout().subscribe(res => {
      console.log(`Logout Response ${res.data}`);
      this.tokenService.clear();
      this.router.navigateByUrl(this.tokenService.login_url!);
    });
  }
}
