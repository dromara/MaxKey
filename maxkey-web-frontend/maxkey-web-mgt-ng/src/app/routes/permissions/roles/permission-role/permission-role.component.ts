/*
 * Copyright [2024] [MaxKey of copyright http://www.maxkey.top]
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

import {
  ChangeDetectionStrategy,
  ViewContainerRef,
  ChangeDetectorRef,
  Component,
  OnInit,
  AfterViewInit,
  ViewChild,
  Input,
  inject
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { format, addDays } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzContextMenuService, NzDropdownMenuComponent } from 'ng-zorro-antd/dropdown';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService, NZ_MODAL_DATA } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions, NzTreeComponent } from 'ng-zorro-antd/tree';

import { IModalData } from '../../../../entity/IModalData';
import { TreeNodes } from '../../../../entity/TreeNodes';
import { GroupsService } from '../../../../service/groups.service';
import { PermissionRoleService } from '../../../../service/permission-role.service';
import { PermissionService } from '../../../../service/permission.service';
import { ResourcesService } from '../../../../service/resources.service';
import { RolesService } from '../../../../service/roles.service';
import { set2String } from '../../../../shared/index';
import { SHARED_IMPORTS } from '../../../../shared/shared-imports';
export interface IPermissionModalData extends IModalData {
  roleId: string;
  roleName: string;
  appId: string;
  appName: string;
}
@Component({
  selector: 'app-permission-role',
  templateUrl: './permission-role.component.html',
  styleUrls: ['./permission-role.component.less'],
  imports: [SHARED_IMPORTS]
})
export class PermissionRoleComponent implements OnInit {
  modalData = inject<IPermissionModalData>(NZ_MODAL_DATA);

  @ViewChild('nzTreeComponent', { static: false }) nzTreeComponent!: NzTreeComponent;

  treeNodes = new TreeNodes(true);

  private readonly modalService = inject(NzModalService);
  private readonly modalRef = inject(NzModalRef);
  private readonly resourcesService = inject(ResourcesService);
  private readonly rolesService = inject(RolesService);
  private readonly permissionRoleService = inject(PermissionRoleService);
  private readonly viewContainerRef = inject(ViewContainerRef);
  private readonly fb = inject(FormBuilder);
  private readonly msg = inject(NzMessageService);
  private readonly i18n = inject<I18NService>(ALAIN_I18N_TOKEN);
  private readonly cdr = inject(ChangeDetectorRef);
  private readonly http = inject(_HttpClient);

  ngOnInit(): void {
    this.tree();
  }

  onReset(): void {}

  onSave(e: MouseEvent): void {
    e.preventDefault();
    let _resourceId = '';
    this.nzTreeComponent.getCheckedNodeList().forEach(node => {
      _resourceId = `${node.key},${_resourceId}`;
      //append Children
      node.getChildren().forEach(childNode => {
        _resourceId = `${childNode.key},${_resourceId}`;
      });
    });
    //HalfChecked
    this.nzTreeComponent.getHalfCheckedNodeList().forEach(node => {
      _resourceId = `${node.key},${_resourceId}`;
    });

    if (_resourceId == '') {
      return;
    }

    this.permissionRoleService
      .update({ appId: this.modalData.appId, roleId: this.modalData.roleId, resourceId: _resourceId })
      .subscribe(res => {
        if (res.code == 0) {
          this.msg.success(this.i18n.fanyi('mxk.alert.operate.success'));
          //this.fetch();
        } else {
          this.msg.error(this.i18n.fanyi('mxk.alert.operate.error'));
        }
        this.cdr.detectChanges();
      });
  }

  tree(): void {
    this.resourcesService.tree({ appId: this.modalData.appId, appName: this.modalData.appName }).subscribe(res => {
      this.treeNodes.init(res.data);
      this.treeNodes.nodes = this.treeNodes.build();
      this.getPermissionRole();
      this.cdr.detectChanges();
    });
  }
  getPermissionRole() {
    this.permissionRoleService.getByParams({ appId: this.modalData.appId, roleId: this.modalData.roleId }).subscribe(res => {
      this.treeNodes.checkedKeys = [];
      for (let i = 0; i < res.data.length; i++) {
        this.treeNodes.checkedKeys.push(res.data[i].resourceId);
      }
      this.cdr.detectChanges();
    });
  }

  onClose(e: MouseEvent): void {
    e.preventDefault();
    this.modalRef.destroy({ refresh: false });
  }

  openFolder(data: NzTreeNode | NzFormatEmitEvent): void {
    // open Folder
    if (data instanceof NzTreeNode) {
      data.isExpanded = !data.isExpanded;
    } else {
      const node = data.node;
      if (node) {
        node.isExpanded = !node.isExpanded;
      }
    }
  }
}
