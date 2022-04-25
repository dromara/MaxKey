import { ChangeDetectionStrategy, ViewContainerRef, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { _HttpClient } from '@delon/theme';
import { format, addDays } from 'date-fns';
import { NzSafeAny } from 'ng-zorro-antd/core/types';
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzModalRef, NzModalService } from 'ng-zorro-antd/modal';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { logging } from 'protractor';

import { SocialsAssociateService } from '../../../service/socials-associate.service';
import { SocialsProviderService } from '../../../service/socials-provider.service';
import { set2String } from '../../../shared/index';

import { log } from 'console';

@Component({
  selector: 'app-socials-associate',
  templateUrl: './socials-associate.component.html',
  styleUrls: ['./socials-associate.component.less']
})
export class SocialsAssociateComponent implements OnInit {
  query: {
    params: {
      providerName: String;
      displayName: String;
      employeeNumber: String;
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
        providerName: '',
        displayName: '',
        employeeNumber: '',
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

  constructor(
    private modalService: NzModalService,
    private socialsProviderService: SocialsProviderService,
    private socialsAssociateService: SocialsAssociateService,
    private viewContainerRef: ViewContainerRef,
    private fb: FormBuilder,
    private msg: NzMessageService,
    private cdr: ChangeDetectorRef
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

  onAdd(e: MouseEvent, provider: string): void {
    e.preventDefault();
    this.socialsProviderService.authorize(provider).subscribe(res => {
      //console.log(res.data);
      window.location.href = res.data;
    });
  }

  onDelete(e: MouseEvent, deleteId: String): void {
    e.preventDefault();
    this.socialsAssociateService.delete(deleteId).subscribe(res => {
      if (res.code == 0) {
        this.msg.success(`提交成功`);
        this.fetch();
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
    this.socialsAssociateService.fetch(this.query.params).subscribe(res => {
      console.log(res.data);
      this.query.results.rows = res.data;
      this.query.submitLoading = false;
      this.query.tableLoading = false;
      this.cdr.detectChanges();
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
    this.updateTableCheckedSet(id, checked);
    this.refreshTableCheckedStatus();
  }

  onTableAllChecked(checked: boolean): void {
    this.query.results.rows.filter(({ disabled }) => !disabled).forEach(({ id }) => this.updateTableCheckedSet(id, checked));
    this.refreshTableCheckedStatus();
  }
}
