import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NzMessageService } from 'ng-zorro-antd/message';

import { Institutions } from '../../../entity/Institutions';
import { InstitutionsService } from '../../../service/institutions.service';

@Component({
  selector: 'app-institutions',
  templateUrl: './institutions.component.html',
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
  styleUrls: ['./institutions.component.less']
})
export class InstitutionsComponent implements OnInit {
  form: {
    submitting: boolean;
    model: Institutions;
  } = {
      submitting: false,
      model: new Institutions()
    };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private fb: FormBuilder,
    private institutionsService: InstitutionsService,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.institutionsService.get('').subscribe(res => {
      this.form.model.init(res.data);
      this.cdr.detectChanges();
    });
  }

  onSubmit(): void {
    this.form.submitting = true;
    this.form.model.trans();
    this.institutionsService.update(this.form.model).subscribe(res => {
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
