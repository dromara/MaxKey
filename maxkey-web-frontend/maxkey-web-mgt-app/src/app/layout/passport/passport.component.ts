import { Component, Inject, OnInit } from '@angular/core';
import { DA_SERVICE_TOKEN, ITokenService } from '@delon/auth';

@Component({
  selector: 'layout-passport',
  templateUrl: './passport.component.html',
  styleUrls: ['./passport.component.less']
})
export class LayoutPassportComponent implements OnInit {
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

  constructor(@Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService) { }

  ngOnInit(): void {
    this.tokenService.clear();
  }
}
