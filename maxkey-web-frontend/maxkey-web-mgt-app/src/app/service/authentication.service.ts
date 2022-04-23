import { Injectable, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { StartupService } from '@core';
import { DA_SERVICE_TOKEN, ITokenService } from '@delon/auth';
import { SettingsService, _HttpClient, User } from '@delon/theme';
import * as CryptoJS from 'crypto-js';
import { CookieService } from 'ngx-cookie-service';

import { CONSTS } from '../shared/consts';

import { hostname } from 'os';
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  redirect_uri: string = '';

  constructor(
    private router: Router,
    private settingsService: SettingsService,
    private cookieService: CookieService,
    private startupService: StartupService,
    @Inject(DA_SERVICE_TOKEN) private tokenService: ITokenService,
    private http: _HttpClient
  ) { }

  setRedirectUri(redirect_uri: string) {
    this.redirect_uri = CryptoJS.enc.Base64url.parse(redirect_uri).toString();
    localStorage.setItem('redirect_uri', this.redirect_uri);
  }

  get(authParam: any) {
    return this.http.get('/login/get?_allow_anonymous=true', authParam);
  }

  produceOtp(authParam: any) {
    return this.http.get(`/login/sendotp/${authParam.mobile}?_allow_anonymous=true`, authParam);
  }

  login(authParam: any) {
    return this.http.post('/login/signin?_allow_anonymous=true', authParam);
  }

  congress(authParam: any) {
    return this.http.post('/login/congress?_allow_anonymous=true', authParam);
  }

  clear() {
    this.tokenService.clear();
  }

  auth(authJwt: any) {
    let user: User = {
      name: `${authJwt.displayName}(${authJwt.username})`,
      displayName: authJwt.displayName,
      username: authJwt.username,
      userId: authJwt.id,
      avatar: './assets/img/avatar.svg',
      email: authJwt.email
    };

    let hostnames = window.location.hostname.split('.');
    let subHostName = window.location.hostname;
    if (hostnames.length >= 2) {
      subHostName = `${hostnames[hostnames.length - 2]}.${hostnames[hostnames.length - 1]}`;
    }

    this.cookieService.set(CONSTS.CONGRESS, authJwt.token);
    this.cookieService.set(CONSTS.CONGRESS, authJwt.ticket, { domain: subHostName });

    this.settingsService.setUser(user);
    this.tokenService.set(authJwt);
    this.tokenService.get()?.expired;
  }

  navigate(authJwt: any) {
    // 重新获取 StartupService 内容，我们始终认为应用信息一般都会受当前用户授权范围而影响
    this.startupService.load().subscribe(() => {
      let url = this.tokenService.referrer!.url || '/';
      if (url.includes('/passport')) {
        url = '/';
      }
      if (this.redirect_uri != '') {
        url = this.redirect_uri;
        this.redirect_uri = '';
      }
      this.router.navigateByUrl(url);
    });
  }
}
