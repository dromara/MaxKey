import { Component, ChangeDetectorRef, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import { environment } from '@env/environment';
import format from 'date-fns/format';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';
import { NzUploadFile, NzUploadChangeParam } from 'ng-zorro-antd/upload';

import { Users } from '../../../entity/Users';
import { UsersService } from '../../../service/users.service';

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
  styleUrls: ['./profile.component.less']
})
export class ProfileComponent implements OnInit {
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

  constructor(private usersService: UsersService, private fb: FormBuilder, private msg: NzMessageService, private cdr: ChangeDetectorRef) { }

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
        this.msg.success(`提交成功`);
      } else {
        this.msg.success(`提交失败`);
      }
      this.form.submitting = false;
      this.cdr.detectChanges();
    });
  }
}
