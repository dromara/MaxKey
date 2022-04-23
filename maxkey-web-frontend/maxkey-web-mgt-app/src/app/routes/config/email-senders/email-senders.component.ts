import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';

import { EmailSenders } from '../../../entity/EmailSenders';
import { EmailSendersService } from '../../../service/email-senders.service';

@Component({
  selector: 'app-email-senders',
  templateUrl: './email-senders.component.html',
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
  styleUrls: ['./email-senders.component.less']
})
export class EmailSendersComponent implements OnInit {
  form: {
    submitting: boolean;
    model: EmailSenders;
  } = {
      submitting: false,
      model: new EmailSenders()
    };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private fb: FormBuilder,
    private emailSendersService: EmailSendersService,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef,
    private http: _HttpClient
  ) { }

  ngOnInit(): void {
    this.emailSendersService.get('').subscribe(res => {
      this.form.model.init(res.data);
      this.cdr.detectChanges();
    });
  }

  onSubmit(): void {
    this.form.submitting = true;
    this.form.model.trans();
    this.emailSendersService.update(this.form.model).subscribe(res => {
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
