import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';

import { TimeBased } from '../../../entity/TimeBased';
import { TimeBasedService } from '../../../service/time-based.service';

@Component({
  selector: 'app-timebased',
  templateUrl: './timebased.component.html',
  styleUrls: ['./timebased.component.less']
})
export class TimebasedComponent implements OnInit {
  form: {
    submitting: boolean;
    model: TimeBased;
  } = {
      submitting: false,
      model: new TimeBased()
    };

  formGroup: FormGroup = new FormGroup({});

  constructor(
    private fb: FormBuilder,
    private timeBasedService: TimeBasedService,
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
    this.timeBasedService.get('').subscribe(res => {
      this.form.model.init(res.data);
      this.cdr.detectChanges();
    });
  }

  onSubmit(): void {
    this.form.submitting = true;
    this.form.model.trans();
    this.timeBasedService.update(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.form.model.init(res.data);
        this.msg.success(`提交成功`);
      } else {
        this.msg.success(`提交失败`);
      }
      this.form.submitting = false;
      this.cdr.detectChanges();
    });
  }
}
