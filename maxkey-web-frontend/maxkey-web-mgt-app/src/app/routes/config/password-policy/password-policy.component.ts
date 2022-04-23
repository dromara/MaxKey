import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';

import { PasswordPolicy } from '../../../entity/PasswordPolicy';
import { PasswordPolicyService } from '../../../service/password-policy.service';

@Component({
  selector: 'app-password-policy',
  templateUrl: './password-policy.component.html',
  styleUrls: ['./password-policy.component.less'],
  styles: [
    `
      [nz-form] {
        max-width: 90%;
      }

      nz-form-item {
        width: 50%;
      }
    `
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PasswordPolicyComponent implements OnInit {
  form: {
    submitting: boolean;
    model: PasswordPolicy;
  } = {
      submitting: false,
      model: new PasswordPolicy()
    };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private fb: FormBuilder,
    private passwordPolicyService: PasswordPolicyService,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    /*this.form = this.fb.group({
    title: [null, [Validators.required]],
    date: [null, [Validators.required]],
    goal: [null, [Validators.required]],
    standard: [null, [Validators.required]],
    client: [null, []],
    invites: [null, []],
    weight: [null, []],
    public: [1, [Validators.min(1), Validators.max(3)]],
    publicUsers: [null, []]
  });*/
    this.passwordPolicyService.get('').subscribe(res => {
      this.form.model.init(res.data);
      this.cdr.detectChanges();
    });
  }

  onSubmit(): void {
    this.form.submitting = true;
    this.form.model.trans();
    this.passwordPolicyService.update(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(`提交成功`);
      } else {
        this.msg.success(`提交失败`);
      }
      this.form.submitting = false;
      this.cdr.detectChanges();
    });
  }
}
