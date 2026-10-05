/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { I18NService } from '@core';
import { ALAIN_I18N_TOKEN } from '@delon/theme';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzUploadFile, NzUploadChangeParam, NzUploadModule } from 'ng-zorro-antd/upload';

import { Users } from '../../../entity/Users';
import { UsersService } from '../../../service/users.service';
import { SHARED_IMPORTS } from '../../../shared/shared-imports';

const getBase64 = (file: File): Promise<string | ArrayBuffer | null> =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styles: [
    `
      form {
        width: 100%;
      }
      nz-tabset {
        width: 90%;
      }

      nz-form-item {
        width: 50%;
      }
      .passwordshow {
        width: 100%;
        margin-bottom: 18px;
      }

      .passwordhidden {
        width: 100%;
        margin-bottom: 18px;
        display: none;
      }
    `
  ],
  imports: [...SHARED_IMPORTS, NzUploadModule],
  styleUrls: ['./profile.component.less']
})
export class ProfileComponent implements OnInit {
  private readonly usersService = inject(UsersService);
  private readonly msg = inject(NzMessageService);
  private readonly i18n = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly cdr = inject(ChangeDetectorRef);

  form: {
    submitting: boolean;
    model: Users;
  } = {
    submitting: false,
    model: new Users()
  };

  formGroup: FormGroup = new FormGroup({});

  previewImage: string | ArrayBuffer | undefined | null = '';
  previewVisible = false;

  uploadVisible = false;

  fileList: NzUploadFile[] = [];
  handlePreview = async (file: NzUploadFile): Promise<void> => {
    let preview;
    if (!file.url) {
      preview = await getBase64(file.originFileObj!);
    }
    this.previewImage = file.url || preview;
    this.previewVisible = true;
  };

  uploadImageChange(uploadChange: NzUploadChangeParam): void {
    if (uploadChange.file.status === 'done') {
      this.form.model.pictureId = uploadChange.file.response.data;
      this.cdr.detectChanges();
    }
  }

  onUpload() {
    this.fileList = [];
    this.uploadVisible = true;
  }

  ngOnInit(): void {
    this.usersService.getProfile().subscribe(res => {
      this.form.model.init(res.data);
      this.previewImage = this.form.model.pictureBase64;
      this.fileList = [
        {
          uid: this.form.model.id.toString(),
          name: this.form.model.displayName.toString(),
          status: 'done',
          url: this.previewImage
        }
      ];
      this.cdr.detectChanges();
    });
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
  }

  onSubmit(e: MouseEvent): void {
    e.preventDefault();
    this.form.submitting = true;
    this.form.model.trans();
    this.usersService.updateProfile(this.form.model).subscribe(res => {
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
