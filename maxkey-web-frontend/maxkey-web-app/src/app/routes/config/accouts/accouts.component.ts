import { Component, ChangeDetectorRef, OnInit, Input } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { environment } from '@env/environment';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { Accounts } from '../../../entity/Accounts';
import { AccountsService } from '../../../service/accounts.service';
@Component({
  selector: 'app-accouts',
  templateUrl: './accouts.component.html',
  styleUrls: ['./accouts.component.less']
})
export class AccoutsComponent implements OnInit {
  @Input() appId?: String;

  form: {
    submitting: boolean;
    model: Accounts;
  } = {
      submitting: false,
      model: new Accounts()
    };
  redirect_uri: string = '';
  formGroup: FormGroup = new FormGroup({});

  confirmPasswordVisible = false;

  passwordVisible = false;

  constructor(
    fb: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private modalRef: NzModalRef,
    private accountsService: AccountsService,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    if (this.appId) {
      this.accountsService.get(this.appId).subscribe(res => {
        console.log(res.data);
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    }
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }

  onSubmit(): void {
    this.form.submitting = true;
    this.form.model.trans();
    //if (this.formGroup.valid) {
    this.accountsService.update(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.form.model.init(res.data);
        this.msg.success(`提交成功`);
        if (this.redirect_uri) {
          window.location.href = `${environment.api.baseUrl}${this.redirect_uri}`;
        }
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
}
