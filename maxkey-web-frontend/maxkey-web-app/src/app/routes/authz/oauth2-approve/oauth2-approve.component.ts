import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, Inject, OnDestroy, Optional } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { NzMessageService } from 'ng-zorro-antd/message';

import { Oauth2ApproveService } from '../../../service/oauth2-approve.service';

@Component({
  selector: 'app-oauth2-approve',
  templateUrl: './oauth2-approve.component.html',
  styleUrls: ['./oauth2-approve.component.less']
})
export class Oauth2ApproveComponent implements OnInit {
  form: {
    submitting: boolean;
    model: {
      clientId?: string;
      appName?: string;
      iconBase64?: string;
      oauth_version?: string;
      user_oauth_approval?: string;
      approval_prompt?: string;
    };
  } = {
      submitting: false,
      model: {}
    };
  redirect_uri: string = '';
  formGroup: FormGroup = new FormGroup({});

  constructor(
    private oauth2ApproveService: Oauth2ApproveService,
    private router: Router,
    private route: ActivatedRoute,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.oauth2ApproveService.get(this.route.snapshot.queryParams['oauth_approval']).subscribe(res => {
      console.log(res.data);
      this.form.model.clientId = res.data.clientId;
      this.form.model.appName = res.data.appName;
      this.form.model.iconBase64 = res.data.iconBase64;
      this.form.model.oauth_version = res.data.oauth_version;
      this.form.model.user_oauth_approval = 'true';
      this.form.model.approval_prompt = res.data.approval_prompt;
      //this.form.model.init(res.data);
      this.cdr.detectChanges();

      if (this.form.model.approval_prompt == 'auto') {
        this.onSubmit();
      }
    });
  }

  onSubmit(): void {
    this.form.submitting = true;
    //if (this.formGroup.valid) {
    this.oauth2ApproveService.approval(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(`提交成功`);
        window.location.href = res.data;
      } else {
        this.msg.success(`提交失败`);
      }
    });
    // } else {
    //  this.formGroup.updateValueAndValidity({ onlySelf: true });
    // this.msg.success(`提交失败`);
    //}
    this.form.submitting = false;
    this.cdr.detectChanges();
  }
  onDeny(): void {
    window.close();
  }
}
