import { Component, ChangeDetectorRef, ViewContainerRef, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import format from 'date-fns/format';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { Accounts } from '../../../entity/Accounts';
import { AccountsService } from '../../../service/accounts.service';
import { UsersService } from '../../../service/users.service';
import { SelectAccountsStrategyComponent } from '../../config/accounts-strategy/select-accounts-strategy/select-accounts-strategy.component';
import { SelectUserComponent } from '../../users/select-user/select-user.component';

@Component({
  selector: 'app-account-editer',
  templateUrl: './account-editer.component.html',
  styles: [
    `
      nz-form-item {
        width: 100%;
      }
    `
  ],
  styleUrls: ['./account-editer.component.less']
})
export class AccountEditerComponent implements OnInit {
  @Input() id?: String;
  @Input() isEdit?: boolean;

  passwordVisible = false;

  form: {
    submitting: boolean;
    model: Accounts;
  } = {
      submitting: false,
      model: new Accounts()
    };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private modalRef: NzModalRef,
    private modalService: NzModalService,
    private accountsService: AccountsService,
    private usersService: UsersService,
    private viewContainerRef: ViewContainerRef,
    private fb: FormBuilder,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    if (this.isEdit) {
      this.accountsService.get(`${this.id}`).subscribe(res => {
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    }
  }

  onSelectUser(e: MouseEvent): void {
    e.preventDefault();
    const modal = this.modalService.create({
      nzContent: SelectUserComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {},
      nzWidth: 900,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.form.model.userId = result.data.id;
        this.form.model.username = result.data.username;
        this.form.model.displayName = result.data.displayName;
        this.cdr.detectChanges();
      }
    });
  }

  onSelectStrategy(e: MouseEvent): void {
    e.preventDefault();
    const modal = this.modalService.create({
      nzContent: SelectAccountsStrategyComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {},
      nzWidth: 600,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.form.model.appId = result.data.appId;
        this.form.model.appName = result.data.appName;
        this.form.model.strategyName = result.data.name;
        this.form.model.strategyId = result.data.id;
        this.cdr.detectChanges();
      }
    });
  }

  onGenerate(e: MouseEvent): void {
    e.preventDefault();
    this.accountsService.generate({ strategyId: this.form.model.strategyId, userId: this.form.model.userId }).subscribe(res => {
      this.form.model.relatedUsername = res.data;
      this.cdr.detectChanges();
    });
  }

  onPassword(e: MouseEvent): void {
    e.preventDefault();
    this.usersService.generatePassword({}).subscribe(res => {
      this.form.model.relatedPassword = res.data;
      this.cdr.detectChanges();
    });
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }

  onSubmit(e: MouseEvent): void {
    e.preventDefault();
    this.form.submitting = true;
    this.form.model.trans();
    (this.isEdit ? this.accountsService.update(this.form.model) : this.accountsService.add(this.form.model)).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(`提交成功`);
      } else {
        this.msg.success(`提交失败`);
      }
      this.form.submitting = false;
      this.modalRef.destroy({ refresh: true });
      this.cdr.detectChanges();
    });
  }
}
