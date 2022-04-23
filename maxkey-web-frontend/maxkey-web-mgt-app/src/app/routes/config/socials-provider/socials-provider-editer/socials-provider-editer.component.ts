import { Component, ChangeDetectorRef, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import format from 'date-fns/format';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { SocialsProvider } from '../../../../entity/SocialsProvider';
import { SocialsProviderService } from '../../../../service/socials-provider.service';

@Component({
  selector: 'app-socials-provider-editer',
  templateUrl: './socials-provider-editer.component.html',
  styles: [
    `
      nz-form-item {
        width: 100%;
      }
    `
  ],
  styleUrls: ['./socials-provider-editer.component.less']
})
export class SocialsProviderEditerComponent implements OnInit {
  @Input() id?: String;
  @Input() isEdit?: boolean;

  form: {
    submitting: boolean;
    model: SocialsProvider;
  } = {
      submitting: false,
      model: new SocialsProvider()
    };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private modalRef: NzModalRef,
    private socialsProviderService: SocialsProviderService,
    private fb: FormBuilder,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    if (this.isEdit) {
      this.socialsProviderService.get(`${this.id}`).subscribe(res => {
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
    (this.isEdit ? this.socialsProviderService.update(this.form.model) : this.socialsProviderService.add(this.form.model)).subscribe(
      res => {
        if (res.code == 0) {
          this.msg.success(`提交成功`);
        } else {
          this.msg.success(`提交失败`);
        }
        this.form.submitting = false;
        this.modalRef.destroy({ refresh: true });
        this.cdr.detectChanges();
      }
    );
  }
}
