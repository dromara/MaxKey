/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { Component, Inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DA_SERVICE_TOKEN, ITokenService } from '@delon/auth';
import { CONSTS } from 'src/app/shared/consts';

import { AuthnService } from '../../service/authn.service';
import { knowHost } from '../../shared/utils/knowhost';

@Component({
  selector: 'layout-passport',
  templateUrl: './passport.component.html',
  styleUrls: ['./passport.component.less']
})
export class LayoutPassportComponent implements OnInit {
  version = CONSTS.VERSION;
  copyrightYear = new Date().getFullYear();
  inst: any;

  links = [
    {
      title: '帮助',
      href: 'https://www.maxkey.top'
    },
    {
      title: '条款',
      href: 'https://www.maxkey.top/zh/about/licenses.html'
    }
  ];

  constructor(
    private authnService: AuthnService,
    @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
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
