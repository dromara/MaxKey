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
  Inject
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { I18NService } from '@core';
import { _HttpClient, ALAIN_I18N_TOKEN, SettingsService } from '@delon/theme';
import { format, addDays } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzContextMenuService, NzDropdownMenuComponent } from 'ng-zorro-antd/dropdown';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions, NzTreeComponent } from 'ng-zorro-antd/tree';

import { TreeNodes } from '../../../../entity/TreeNodes';
import { GroupsService } from '../../../../service/groups.service';
import { PermissionRoleService } from '../../../../service/permission-role.service';
import { PermissionService } from '../../../../service/permission.service';
import { ResourcesService } from '../../../../service/resources.service';
import { RolesService } from '../../../../service/roles.service';
import { set2String } from '../../../../shared/index';
@Component({
  selector: 'app-permission-role',
  templateUrl: './permission-role.component.html',
  styleUrls: ['./permission-role.component.less']
})
export class PermissionRoleComponent implements OnInit {
  @Input() roleId?: String;
  @Input() roleName?: String;
  @Input() appId?: String;
  @Input() appName?: String;

  @ViewChild('nzTreeComponent', { static: false }) nzTreeComponent!: NzTreeComponent;

  treeNodes = new TreeNodes(true);

  constructor(
    private modalService: NzModalService,
    private modalRef: NzModalRef,
    private resourcesService: ResourcesService,
    private rolesService: RolesService,
    private permissionRoleService: PermissionRoleService,
    private viewContainerRef: ViewContainerRef,
    private fb: FormBuilder,
    private msg: NzMessageService,
    @Inject(ALAIN_I18N_TOKEN) private i18n: I18NService,
    private cdr: ChangeDetectorRef,
    private http: _HttpClient
  ) { }

  ngOnInit(): void {
    this.tree();
  }

  onReset(): void { }

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

    this.permissionRoleService.update({ appId: this.appId, roleId: this.roleId, resourceId: _resourceId }).subscribe(res => {
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
    this.resourcesService.tree({ appId: this.appId, appName: this.appName }).subscribe(res => {
      this.treeNodes.init(res.data);
      this.treeNodes.nodes = this.treeNodes.build();
      this.getPermissionRole();
      this.cdr.detectChanges();
    });
  }
  getPermissionRole() {
    this.permissionRoleService.getByParams({ appId: this.appId, roleId: this.roleId }).subscribe(res => {
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
