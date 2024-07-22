import format from 'date-fns/format';

import { BaseEntity } from './BaseEntity';

export class Roles extends BaseEntity {
  roleCode!: String;
  roleName!: String;
  appId!: String;
  appName!: String;
  category!: String;
  filters!: String;
  orgIdsList!: String;
  resumeTime!: String;
  suspendTime!: String;
  isdefault!: String;
  picker_resumeTime: Date = new Date(format(new Date(), 'yyyy-MM-dd 00:00:00'));
  picker_suspendTime: Date = new Date(format(new Date(), 'yyyy-MM-dd 00:00:00'));

  constructor() {
    super();
    this.status = 1;
  }

  override init(data: any): void {
    Object.assign(this, data);
    if (this.status == 1) {
      this.switch_status = true;
    } else {
      this.switch_status = false;
    }

    if (this.resumeTime != '') {
      this.picker_resumeTime = new Date(format(new Date(), `yyyy-MM-dd ${this.resumeTime}:00`));
    }
    if (this.suspendTime != '') {
      this.picker_suspendTime = new Date(format(new Date(), `yyyy-MM-dd ${this.suspendTime}:00`));
    }
  }

  override trans(): void {
    if (this.switch_status) {
      this.status = 1;
    } else {
      this.status = 0;
    }
    if (this.picker_resumeTime) {
      this.resumeTime = format(this.picker_resumeTime, 'HH:mm');
    }
    if (this.picker_suspendTime) {
      this.suspendTime = format(this.picker_suspendTime, 'HH:mm');
    }
  }
}
