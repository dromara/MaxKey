import { Injectable } from '@angular/core';
import { SettingsService, _HttpClient, User } from '@delon/theme';

@Injectable({
  providedIn: 'root'
})
export class ImageCaptchaService {
  constructor(private http: _HttpClient) { }

  captcha(param: any) {
    return this.http.get('/captcha?_allow_anonymous=true', param);
  }
}
