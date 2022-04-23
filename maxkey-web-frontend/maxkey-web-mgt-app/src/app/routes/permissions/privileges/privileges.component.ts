import { ChangeDetectionStrategy, ViewContainerRef, ChangeDetectorRef, Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import { format, addDays } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzContextMenuService, NzDropdownMenuComponent } from 'ng-zorro-antd/dropdown';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions, NzTreeComponent } from 'ng-zorro-antd/tree';

import { TreeNodes } from '../../../entity/TreeNodes';
import { ResourcesService } from '../../../service/resources.service';
import { RolePrivilegesService } from '../../../service/role-privileges.service';
import { RolesService } from '../../../service/roles.service';
import { set2String } from '../../../shared/index';
import { SelectAppsComponent } from '../../apps/select-apps/select-apps.component';

@Component({
  selector: 'app-privileges',
  templateUrl: './privileges.component.html',
  styleUrls: ['./privileges.component.less']
})
export class PrivilegesComponent implements OnInit {
  @ViewChild('nzTreeComponent', { static: false }) nzTreeComponent!: NzTreeComponent;
  query: {
    params: {
      name: String;
      displayName: String;
      employeeNumber: String;
      appId: String;
      appName: String;
      startDate: String;
      endDate: String;
      startDatePicker: Date;
      endDatePicker: Date;
      pageSize: number;
      pageNumber: number;
      pageSizeOptions: number[];
    };
    results: {
      records: number;
      rows: NzSafeAny[];
    };
    expandForm: Boolean;
    submitLoading: boolean;
    tableLoading: boolean;
    tableCheckedId: Set<String>;
    indeterminate: boolean;
    checked: boolean;
  } = {
      params: {
        name: '',
        displayName: '',
        employeeNumber: '',
        appId: '',
        appName: '',
        startDate: '',
        endDate: '',
        startDatePicker: addDays(new Date(), -30),
        endDatePicker: new Date(),
        pageSize: 10,
        pageNumber: 1,
        pageSizeOptions: [10, 20, 50]
      },
      results: {
        records: 0,
        rows: []
      },
      expandForm: false,
      submitLoading: false,
      tableLoading: false,
      tableCheckedId: new Set<String>(),
      indeterminate: false,
      checked: false
    };

  treeNodes = new TreeNodes(true);

  constructor(
    private modalService: NzModalService,
    private rolePrivilegesService: RolePrivilegesService,
    private resourcesService: ResourcesService,
    private rolesService: RolesService,
    private viewContainerRef: ViewContainerRef,
    private fb: FormBuilder,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef,
    private http: _HttpClient
  ) { }

  ngOnInit(): void {
    this.fetch();
  }

  onQueryParamsChange(tableQueryParams: NzTableQueryParams): void {
    this.query.params.pageNumber = tableQueryParams.pageIndex;
    this.query.params.pageSize = tableQueryParams.pageSize;
    this.fetch();
  }

  onSearch(): void {
    this.fetch();
  }

  onReset(): void { }

  onSave(e: MouseEvent): void {
    e.preventDefault();
    let _roleId: String = '';
    this.query.tableCheckedId.forEach(value => {
      _roleId = value;
    });

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

    if (this.query.params.appId == '' || _roleId == '' || _resourceId == '') {
      return;
    }
    this.rolePrivilegesService.update({ appId: this.query.params.appId, roleId: _roleId, resourceId: _resourceId }).subscribe(res => {
      this.query.submitLoading = false;
      this.query.tableLoading = false;
      if (res.code == 0) {
        this.msg.success(`提交成功`);
      } else {
        this.msg.success(`提交失败`);
      }
      this.cdr.detectChanges();
    });
  }

  fetch(): void {
    this.query.submitLoading = true;
    this.query.tableLoading = true;
    this.query.indeterminate = false;
    this.query.checked = false;
    this.query.tableCheckedId.clear();
    if (this.query.expandForm) {
      this.query.params.endDate = format(this.query.params.endDatePicker, 'yyyy-MM-dd HH:mm:ss');
      this.query.params.startDate = format(this.query.params.startDatePicker, 'yyyy-MM-dd HH:mm:ss');
    } else {
      this.query.params.endDate = '';
      this.query.params.startDate = '';
    }
    this.rolesService.fetch(this.query.params).subscribe(res => {
      this.query.results = res.data;
      this.query.submitLoading = false;
      this.query.tableLoading = false;
      this.cdr.detectChanges();
    });
  }

  tree(): void {
    this.resourcesService.tree({ appId: this.query.params.appId, appName: this.query.params.appName }).subscribe(res => {
      this.treeNodes.init(res.data);
      this.treeNodes.nodes = this.treeNodes.build();
      this.cdr.detectChanges();
    });
  }

  onSelect(e: MouseEvent): void {
    e.preventDefault();

    const modal = this.modalService.create({
      nzContent: SelectAppsComponent,
      nzViewContainerRef: this.viewContainerRef,
      nzComponentParams: {},
      nzWidth: 700,
      nzOnOk: () => new Promise(resolve => setTimeout(resolve, 1000))
    });
    // Return a result when closed
    modal.afterClose.subscribe(result => {
      if (result.refresh) {
        this.query.params.appName = result.data.name;
        this.query.params.appId = result.data.id;
        this.fetch();
        this.tree();
      }
    });
  }

  updateTableCheckedSet(id: String, checked: boolean): void {
    if (checked) {
      this.query.tableCheckedId.add(id);
    } else {
      this.query.tableCheckedId.delete(id);
    }
  }

  refreshTableCheckedStatus(): void {
    const listOfEnabledData = this.query.results.rows.filter(({ disabled }) => !disabled);
    this.query.checked = listOfEnabledData.every(({ id }) => this.query.tableCheckedId.has(id));
    this.query.indeterminate = listOfEnabledData.some(({ id }) => this.query.tableCheckedId.has(id)) && !this.query.checked;
  }

  onTableItemChecked(id: String, checked: boolean): void {
    this.onTableAllChecked(false);
    this.updateTableCheckedSet(id, checked);
    this.refreshTableCheckedStatus();
    this.rolePrivilegesService.getByParams({ appId: this.query.params.appId, roleId: id }).subscribe(res => {
      this.treeNodes.checkedKeys = [];
      for (let i = 0; i < res.data.length; i++) {
        this.treeNodes.checkedKeys.push(res.data[i].resourceId);
      }
      this.cdr.detectChanges();
    });
  }

  onTableAllChecked(checked: boolean): void {
    this.query.results.rows.filter(({ disabled }) => !disabled).forEach(({ id }) => this.updateTableCheckedSet(id, checked));
    this.refreshTableCheckedStatus();
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
