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

import { Injectable, Inject } from '@angular/core';
import { _HttpClient, User } from '@delon/theme';
@Injectable({
  providedIn: 'root'
})
export class ForgotPasswordService {
  constructor(private http: _HttpClient) {}

  produceOtp(param: any) {
    return this.http.get('/forgotpassword/produceOtp?_allow_anonymous=true', param);
  }

  produceEmailOtp(param: any) {
    return this.http.get(`/forgotpassword/produceEmailOtp?_allow_anonymous=true`, param);
  }

  setPassWord(param: any) {
    return this.http.get('/forgotpassword/setpassword?_allow_anonymous=true', param);
  }

  validateCaptcha(param: any) {
    return this.http.get(`/forgotpassword/validateCaptcha?_allow_anonymous=true`, param);
  }

  passwordpolicy() {
    return this.http.get('/forgotpassword/passwordpolicy?_allow_anonymous=true',null);
  }
}
