import { Component, ChangeDetectorRef, OnInit, Input, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { I18NService } from '@core';
import { ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';

import { Users } from '../../../../entity/Users';
import { UsersService } from '../../../../service/users.service';

@Component({
  selector: 'app-mfa',
  templateUrl: './mfa.component.html',
  styleUrls: ['./mfa.component.less']
})
export class MfaComponent implements OnInit {
  @Input() id?: String;
  @Input() username?: String;
  @Input() displayName?: String;

  form: {
    submitting: boolean;
    model: Users;
  } = {
    submitting: false,
    model: new Users()
  };

  constructor(
    private usersService: UsersService,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private router: Router,
    private modalRef: NzModalRef,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.id) {
      this.usersService.get(this.id).subscribe(res => {
        this.form.model = res.data;
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

    this.usersService.updateAuthnType(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
        this.modalRef.destroy({ refresh: true });
        this.cdr.detectChanges();
      } else {
        this.msg.error(res.message);
      }
    });
  }
}
