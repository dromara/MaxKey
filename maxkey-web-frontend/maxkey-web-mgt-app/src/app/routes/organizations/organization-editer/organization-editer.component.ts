import { Component, ChangeDetectorRef, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import format from 'date-fns/format';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';

import { Organizations } from '../../../entity/Organizations';
import { OrganizationsService } from '../../../service/organizations.service';

@Component({
  selector: 'app-organization-editer',
  templateUrl: './organization-editer.component.html',
  styles: [
    `
      nz-form-item,
      nz-tabset {
        width: 90%;
      }
    `
  ],
  styleUrls: ['./organization-editer.component.less']
})
export class OrganizationEditerComponent implements OnInit {
  @Input() id?: String;
  @Input() parentNode?: NzTreeNode;
  @Input() isEdit?: boolean;

  form: {
    submitting: boolean;
    model: Organizations;
  } = {
      submitting: false,
      model: new Organizations()
    };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private modalRef: NzModalRef,
    private orgsService: OrganizationsService,
    private fb: FormBuilder,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    if (this.isEdit) {
      this.orgsService.get(`${this.id}`).subscribe(res => {
        this.form.model.init(res.data);
        this.cdr.detectChanges();
      });
    } else {
      if (this.parentNode) {
        this.form.model.parentCode = this.parentNode?.key;
        this.form.model.parentName = this.parentNode?.title;
      }
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

    (this.isEdit ? this.orgsService.update(this.form.model) : this.orgsService.add(this.form.model)).subscribe(res => {
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
