import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { GlobalFooterModule } from '@delon/abc/global-footer';
import { DA_SERVICE_TOKEN } from '@delon/auth';
import { I18nPipe } from '@delon/theme';
import { ThemeBtnComponent } from '@delon/theme/theme-btn';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzIconModule } from 'ng-zorro-antd/icon';

import { AuthnService } from '../../service/authn.service';
import { CONSTS } from '../../shared/consts';
import { knowHost } from '../../shared/utils/knowhost';
import { HeaderI18n } from '../basic/widgets/i18n';

@Component({
  selector: 'layout-passport',
  templateUrl: './passport.html',
  styleUrls: ['./passport.less'],
  imports: [RouterOutlet, HeaderI18n, GlobalFooterModule, NzIconModule, ThemeBtnComponent, I18nPipe, NzGridModule]
})
export class LayoutPassport implements OnInit {
  private tokenSrv = inject(DA_SERVICE_TOKEN);
  CONSTS = CONSTS;
  copyrightYear = new Date().getFullYear();

  private authnService = inject(AuthnService);
  inst: any;

  links = [
    {
      title: '帮助',
      href: ''
    },
    {
      title: '隐私',
      href: ''
    },
    {
      title: '条款',
      href: ''
    }
  ];

  constructor() {
    this.tokenSrv.clear();
  }

  ngOnInit(): void {
    this.tokenSrv.clear();
    this.inst = this.authnService.getInst();
    if (this.inst == null) {
      this.inst = { custom: false };
      this.authnService.initInst().subscribe(res => {
        this.authnService.setInst(res.data, !knowHost());
        this.inst = this.authnService.getInst();
      });
    }
  }
}
