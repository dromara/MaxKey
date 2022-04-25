import { Injectable, Inject } from '@angular/core';
import { _HttpClient, User } from '@delon/theme';
@Injectable({
  providedIn: 'root'
})
export class SignUpService {
  constructor(private http: _HttpClient) { }

  produceOtp(param: any) {
    return this.http.get('/signup/produceOtp?_allow_anonymous=true', param);
  }

  register(param: any) {
    return this.http.get('/signup/register?_allow_anonymous=true', param);
  }
}
