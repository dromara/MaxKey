import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { I18NService } from '@core';
import { SettingsService, User, ALAIN_I18N_TOKEN } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';

import { Users } from '../../../entity/Users';
import { UsersService } from '../../../service/users.service';

@Component({
  selector: 'app-mfa',
  templateUrl: './mfa.component.html',
  styleUrls: ['./mfa.component.less']
})
export class MfaComponent implements OnInit {
  form: {
    submitting: boolean;
    model: Users;
  } = {
    submitting: false,
    model: new Users()
  };
  loading = false;
  constructor(
    private router: Router,
    private fb: FormBuilder,
    private settingsService: SettingsService,
    private usersService: UsersService,

    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    let user: any = this.settingsService.user;
    this.form.model.id = user.userId;
    this.form.model.displayName = user.displayName;
    this.form.model.username = user.username;
    this.form.model.mobile = user.mobile;
    this.form.model.email = user.email;
    this.form.model.authnType = '0';
    this.usersService.getProfile().subscribe(res => {
      this.form.model.init(res.data);
      this.cdr.detectChanges();
    });
  }

  onSubmit(): void {
    this.form.submitting = true;
    this.form.model.trans();
    this.usersService.updateAuthnType(this.form.model).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
      } else {
        this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
      }
      this.form.submitting = false;
      this.cdr.detectChanges();
    });
  }
}
