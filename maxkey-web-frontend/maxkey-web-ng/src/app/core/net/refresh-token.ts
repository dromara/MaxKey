import { HttpClient, HttpEvent, HttpHandlerFn, HttpRequest, HttpResponseBase } from '@angular/common/http';
import { EnvironmentProviders, Injector, inject, provideAppInitializer } from '@angular/core';
import { DA_SERVICE_TOKEN, ITokenModel } from '@delon/auth';
import { CookieService } from 'ngx-cookie-service';
import { BehaviorSubject, Observable, catchError, filter, switchMap, take, throwError, finalize } from 'rxjs';

import { toLogin } from './helper';
import { CONSTS } from '../../shared/consts';

let logoutURL = '/logout';
let refreshTokenURL = '/auth/token/refresh';
let refreshToking = false;
let refreshToken$ = new BehaviorSubject<unknown>(null);

/**
 * 重新附加新 Token 信息
 *
 * > 由于已经发起的请求，不会再走一遍 `@delon/auth` 因此需要结合业务情况重新附加新的 Token
 */
function reAttachToken(injector: Injector, req: HttpRequest<unknown>): HttpRequest<unknown> {
  const token = injector.get(DA_SERVICE_TOKEN).get()?.token;
  console.log(`reAttachToken ${req.url} token ${token}`);
  return req.clone({
    setHeaders: {
      //token: `Bearer ${token}`
      //maxkey Headers
      Authorization: `Bearer ${token}`,
      hostname: window.location.hostname,
      AuthServer: 'MaxKey'
    }
  });
}

function refreshTokenRequest(injector: Injector): Observable<ITokenModel> {
  const model = injector.get(DA_SERVICE_TOKEN).get();
  //return injector.get(HttpClient).post<ITokenModel>(`/api/auth/refresh`, { headers: { refresh_token: model?.['refresh_token'] || '' } });
  //maxkey refreshTokenURL
  console.log(`refreshTokenRequest ${refreshTokenURL} refresh_token ${model?.['refresh_token']}`);
  //return injector.get(HttpClient).post(refreshTokenURL, {}, { headers: { refresh_token: model?.['refresh_token'] || '' } });
  return injector.get(HttpClient).get<ITokenModel>(refreshTokenURL, { params: { refresh_token: model?.['refresh_token'] || '' } });
}

/**
 * 刷新Token方式一：使用 401 重新刷新 Token
 */
export function tryRefreshToken(
  injector: Injector,
  ev: HttpResponseBase,
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> {
  // 1、若请求为刷新Token请求，表示来自刷新Token可以直接跳转登录页
  //if ([`/api/auth/refresh`].some(url => req.url.includes(url))) {
  //maxkey refreshTokenURL
  if ([refreshTokenURL, logoutURL].some(url => req.url.includes(url))) {
    toLogin(injector);
    return throwError(() => ev);
  }
  // 2、如果 `refreshToking` 为 `true` 表示已经在请求刷新 Token 中，后续所有请求转入等待状态，直至结果返回后再重新发起请求
  if (refreshToking) {
    return refreshToken$.pipe(
      filter(v => !!v),
      take(1),
      switchMap(() => next(reAttachToken(injector, req)))
    );
  }
  // 3、尝试调用刷新 Token
  refreshToking = true;
  refreshToken$.next(null);

  return refreshTokenRequest(injector).pipe(
    switchMap(res => {
      // 通知后续请求继续执行
      refreshToking = false;
      //refreshToken$.next(res);
      // 重新保存新 token
      //injector.get(DA_SERVICE_TOKEN).set(res);
      setAuth(injector, res['data']);
      // 重新发起请求
      return next(reAttachToken(injector, req));
    }),
    finalize(() => {
      refreshToking = false;
      refreshToken$.next(null);
    }),
    catchError(err => {
      refreshToking = false;
      toLogin(injector);
      return throwError(() => err);
    })
  );
}

function buildAuthRefresh(injector: Injector): void {
  const tokenSrv = injector.get(DA_SERVICE_TOKEN);
  tokenSrv.refresh
    .pipe(
      filter(() => !refreshToking),
      switchMap(res => {
        console.log(res);
        refreshToking = true;
        return refreshTokenRequest(injector);
      })
    )
    .subscribe({
      next: res => {
        // TODO: Mock expired value
        //res.expired = +new Date() + 1000 * 60 * 5;
        refreshToking = false;
        //tokenSrv.set(res);
        setAuth(injector, res['data']);
      },
      error: () => toLogin(injector)
    });
}

function setAuth(injector: Injector, jwtToken: any): void {
  console.log(`JWT Token ${jwtToken}`);
  //maxkey 设置cookie
  const currDate = new Date();
  jwtToken.tokenAtTime = currDate.getTime();
  const cookieDate = new Date(currDate.getTime() + jwtToken.expired * 1000);
  console.log(`cookie end at Date ${cookieDate}`);
  let subHostName = getSubHostName();
  const cookieService = injector.get(CookieService);
  cookieService.set(CONSTS.CONGRESS, jwtToken.token, cookieDate, '/');
  cookieService.set(CONSTS.ONLINE_TICKET, jwtToken.ticket, cookieDate, '/', subHostName);
  refreshToken$.next(jwtToken);
  // 重新保存新 token
  injector.get(DA_SERVICE_TOKEN).set(jwtToken);
}

function getSubHostName(): string {
  let hostnames = window.location.hostname.split('.');
  let subHostName = window.location.hostname;
  if (hostnames.length >= 2 && !CONSTS.IP_V4_REGEXEXP.test(subHostName)) {
    subHostName = `${hostnames[hostnames.length - 2]}.${hostnames[hostnames.length - 1]}`;
  }
  return subHostName;
}

/**
 * 刷新Token方式二：使用 `@delon/auth` 的 `refresh` 接口，需要在 `app.config.ts` 中注册 `provideBindAuthRefresh`
 */
export function provideBindAuthRefresh(): EnvironmentProviders[] {
  return [
    provideAppInitializer(() => {
      const initializerFn = (
        (injector: Injector) => () =>
          buildAuthRefresh(injector)
      )(inject(Injector));
      return initializerFn();
    })
  ];
}
