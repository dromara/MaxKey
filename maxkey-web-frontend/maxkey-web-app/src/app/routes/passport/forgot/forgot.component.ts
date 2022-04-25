import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzStepsModule } from 'ng-zorro-antd/steps';

import { ChangePassword } from '../../../entity/ChangePassword';
import { ForgotPasswordService } from '../../../service/forgot-password.service';
import { ImageCaptchaService } from '../../../service/image-captcha.service';

@Component({
  selector: 'app-forgot',
  templateUrl: './forgot.component.html',
  styleUrls: ['./forgot.component.less']
})
export class ForgotComponent implements OnInit {
  form: {
    submitting: boolean;
    model: ChangePassword;
  } = {
      submitting: false,
      model: new ChangePassword()
    };

  imageCaptcha = '';
  formGroup: FormGroup;
  state = '';
  step = 0;
  forgotType = 'mobile';
  passwordVisible = false;
  loading = false;
  count = 0;
  interval$: any;
  userId = '';
  username = '';

  constructor(
    fb: FormBuilder,
    private forgotPasswordService: ForgotPasswordService,
    private imageCaptchaService: ImageCaptchaService,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) {
    this.formGroup = fb.group({
      mobile: [null, [Validators.required, Validators.pattern(/^1\d{10}$/)]],
      email: [null, [Validators.required]],
      password: [null, [Validators.required]],
      captcha: [null, [Validators.required]],
      otpCaptcha: [null, [Validators.required]],
      confirmPassword: [null, [Validators.required]]
    });
  }

  get email(): AbstractControl {
    return this.formGroup.get('email')!;
  }
  get password(): AbstractControl {
    return this.formGroup.get('password')!;
  }
  get confirmPassword(): AbstractControl {
    return this.formGroup.get('confirmPassword')!;
  }
  get mobile(): AbstractControl {
    return this.formGroup.get('mobile')!;
  }
  get captcha(): AbstractControl {
    return this.formGroup.get('captcha')!;
  }

  get otpCaptcha(): AbstractControl {
    return this.formGroup.get('otpCaptcha')!;
  }

  ngOnInit(): void {
    this.getImageCaptcha();
  }

  getImageCaptcha(): void {
    this.imageCaptchaService.captcha({}).subscribe(res => {
      this.imageCaptcha = res.data.image;
      this.state = res.data.state;
      this.cdr.detectChanges();
    });
  }

  //send sms
  sendOtpCode(): void {
    if (this.forgotType == 'mobile' && this.mobile.invalid) {
      this.mobile.markAsDirty({ onlySelf: true });
      this.mobile.updateValueAndValidity({ onlySelf: true });
      return;
    }

    if (this.forgotType == 'email' && this.email.invalid) {
      this.email.markAsDirty({ onlySelf: true });
      this.email.updateValueAndValidity({ onlySelf: true });
      return;
    }
    if (this.forgotType == 'mobile') {
      this.forgotPasswordService
        .produceOtp({ mobile: this.mobile.value, state: this.state, captcha: this.captcha.value })
        .subscribe(res => {
          if (res.code !== 0) {
            this.msg.success(`发送失败`);
            this.getImageCaptcha();
            this.cdr.detectChanges();
          }
          this.userId = res.data.userId;
          this.username = res.data.username;
          //console.log(res.data);
        });
    } else if (this.forgotType == 'email') {
      this.forgotPasswordService
        .produceEmailOtp({ email: this.email.value, state: this.state, captcha: this.captcha.value })
        .subscribe(res => {
          if (res.code !== 0) {
            this.msg.success(`发送失败`);
            this.getImageCaptcha();
            this.cdr.detectChanges();
          }
          this.userId = res.data.userId;
          this.username = res.data.username;
          //console.log(res.data);
        });
    }
    this.count = 59;
    this.interval$ = setInterval(() => {
      this.count -= 1;
      if (this.count <= 0) {
        clearInterval(this.interval$);
      }
      this.cdr.detectChanges();
    }, 1000);
  }

  onNextReset(e: MouseEvent) {
    if (this.otpCaptcha.invalid) {
      this.otpCaptcha.markAsDirty({ onlySelf: true });
      this.otpCaptcha.updateValueAndValidity({ onlySelf: true });
      return;
    }
    this.step = 1;
  }

  onSubmit(e: MouseEvent) {
    this.forgotPasswordService
      .setPassWord({
        forgotType: this.forgotType,
        userId: this.userId,
        username: this.username,
        password: this.password.value,
        confirmPassword: this.confirmPassword.value,
        otpCaptcha: this.otpCaptcha.value,
        state: this.state
      })
      .subscribe(res => {
        if (res.code !== 0) {
          this.msg.success(`密码修改失败`);
          this.getImageCaptcha();
          this.step = 0;
          this.cdr.detectChanges();
        }
        this.msg.success(`密码修改成功`);
      });
  }

  ngModelChange() {
    if (this.forgotType == 'email') {
      this.mobile.reset();
    }
    if (this.forgotType == 'mobile') {
      this.email.reset();
    }
  }
}
