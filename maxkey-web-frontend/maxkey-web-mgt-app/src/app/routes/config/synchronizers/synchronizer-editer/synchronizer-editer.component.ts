import { Component, ChangeDetectorRef, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import format from 'date-fns/format';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { Synchronizers } from '../../../../entity/Synchronizers';
import { SynchronizersService } from '../../../../service/synchronizers.service';

@Component({
  selector: 'app-synchronizer-editer',
  templateUrl: './synchronizer-editer.component.html',
  styles: [
    `
      nz-form-item {
        width: 100%;
      }
    `
  ],
  styleUrls: ['./synchronizer-editer.component.less']
})
export class SynchronizerEditerComponent implements OnInit {
  @Input() id?: String;
  @Input() isEdit?: boolean;

  form: {
    submitting: boolean;
    model: Synchronizers;
  } = {
      submitting: false,
      model: new Synchronizers()
    };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private modalRef: NzModalRef,
    private synchronizersService: SynchronizersService,
    private fb: FormBuilder,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    if (this.isEdit) {
      this.synchronizersService.get(`${this.id}`).subscribe(res => {
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    }
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }

  onSubmit(e: MouseEvent): void {
    e.preventDefault();
    this.form.submitting = true;
    this.form.model.trans();
    (this.isEdit ? this.synchronizersService.update(this.form.model) : this.synchronizersService.add(this.form.model)).subscribe(res => {
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
