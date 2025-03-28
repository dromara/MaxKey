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

import { Component, ChangeDetectorRef, Input, OnInit, Inject, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { environment } from '@env/environment';
import format from 'date-fns/format';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';
import { NzTreeSelectComponent } from 'ng-zorro-antd/tree-select';
import { NzUploadFile, NzUploadChangeParam } from 'ng-zorro-antd/upload';

import { Users } from '../../../../entity/Users';
import { UsersService } from '../../../../service/users.service';

const getBase64 = (file: File): Promise<string | ArrayBuffer | null> =>
  new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });

@Component({
  selector: 'app-user-editer',
  templateUrl: './user-editer.component.html',
  styles: [
    `
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
  styleUrls: ['./user-editer.component.less']
})
export class UserEditerComponent implements OnInit {
  @Input() id?: String;
  @Input() parentNode?: NzTreeNode;
  @Input() isEdit?: boolean;
  @Input() orgNodes!: any[];
  @ViewChild('orgTree') orgTree!: NzTreeSelectComponent;
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

  constructor(
    private modalRef: NzModalRef,
    private usersService: UsersService,
    private fb: FormBuilder,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.isEdit) {
      this.usersService.get(`${this.id}`).subscribe(res => {
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
    } else {
      if (this.parentNode) {
        this.form.model.departmentId = this.parentNode?.key;
        this.form.model.department = this.parentNode?.title;
      }
    }
  }

  onPassword(e: MouseEvent): void {
    e.preventDefault();
    this.usersService.generatePassword({}).subscribe(res => {
      this.form.model.password = res.data;
      this.cdr.detectChanges();
    });
  }

  onDeptChange(key: string): void {
    let node = this.orgTree.getTreeNodeByKey(key);
    if (node) {
      this.form.model.department = node.title;
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
    (this.isEdit ? this.usersService.update(this.form.model) : this.usersService.add(this.form.model)).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(this.i18n.fanyi(this.isEdit ? 'mxk.alert.update.success' : 'mxk.alert.add.success'));
      } else {
        this.msg.error(this.i18n.fanyi(this.isEdit ? 'mxk.alert.update.error' : 'mxk.alert.add.error'));
      }
      this.form.submitting = false;
      this.modalRef.destroy({ refresh: true });
      this.cdr.detectChanges();
    });
  }
}
