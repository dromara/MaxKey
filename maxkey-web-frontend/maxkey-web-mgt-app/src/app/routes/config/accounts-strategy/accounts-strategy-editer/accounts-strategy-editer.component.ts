import { Component, ChangeDetectorRef, ViewContainerRef, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import format from 'date-fns/format';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { AccountsStrategy } from '../../../../entity/AccountsStrategy';
import { AccountsStrategyService } from '../../../../service/accounts-strategy.service';
import { SelectAppsComponent } from '../../../apps/select-apps/select-apps.component';

@Component({
  selector: 'app-accounts-strategy-editer',
  templateUrl: './accounts-strategy-editer.component.html',
  styles: [
    `
      nz-form-item {
        width: 100%;
      }
    `
  ],
  styleUrls: ['./accounts-strategy-editer.component.less']
})
export class AccountsStrategyEditerComponent implements OnInit {
  @Input() id?: String;
  @Input() isEdit?: boolean;

  form: {
    submitting: boolean;
    model: AccountsStrategy;
  } = {
      submitting: false,
      model: new AccountsStrategy()
    };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private modal: NzModalRef,
    private accountsStrategyService: AccountsStrategyService,
    private fb: FormBuilder,
    private modalService: NzModalService,
    private viewContainerRef: ViewContainerRef,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    if (this.isEdit) {
      this.accountsStrategyService.get(`${this.id}`).subscribe(res => {
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    }
  }

  onSelect(e: MouseEvent): void {
    e.preventDefault();
    const modal = this.modalService.create({
      nzContent: SelectAppsComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {},
      nzWidth: 700,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.form.model.appId = result.data.id;
        this.form.model.appName = result.data.name;
        this.cdr.detectChanges();
      }
    });
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modal.destroy({ refresh: false });
  }

  onSubmit(e: MouseEvent): void {
    e.preventDefault();
    this.form.submitting = true;
    this.form.model.trans();
    (this.isEdit ? this.accountsStrategyService.update(this.form.model) : this.accountsStrategyService.add(this.form.model)).subscribe(
      res => {
        if (res.code == 0) {
          this.msg.success(`提交成功`);
        } else {
          this.msg.success(`提交失败`);
        }
        this.form.submitting = false;
        this.modal.destroy({ refresh: true });
        this.cdr.detectChanges();
      }
    );
  }
}
