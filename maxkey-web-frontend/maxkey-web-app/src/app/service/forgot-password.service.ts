import { Injectable, Inject } from '@angular/core';
import { _HttpClient, User } from '@delon/theme';
@Injectable({
  providedIn: 'root'
})
export class ForgotPasswordService {
  constructor(private http: _HttpClient) { }

  produceOtp(param: any) {
    return this.http.get('/forgotpassword/produceOtp?_allow_anonymous=true', param);
  }

  produceEmailOtp(param: any) {
    return this.http.get(`/forgotpassword/produceEmailOtp?_allow_anonymous=true`, param);
  }

  setPassWord(param: any) {
    return this.http.get('/forgotpassword/setpassword?_allow_anonymous=true', param);
  }
}
