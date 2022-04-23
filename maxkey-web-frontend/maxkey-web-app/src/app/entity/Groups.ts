import format from 'date-fns/format';

import { BaseEntity } from './BaseEntity';

export class Groups extends BaseEntity {
    name!: String;
    dynamic!: String;
    filters!: String;
    orgIdsList!: String;
    resumeTime!: String;
    suspendTime!: String;
    isdefault!: String;

    switch_dynamic: boolean = false;
    picker_resumeTime: Date = new Date(format(new Date(), 'yyyy-MM-dd 00:00:00'));
    picker_suspendTime: Date = new Date(format(new Date(), 'yyyy-MM-dd 00:00:00'));

    constructor() {
        super();
    }

    override init(data: any): void {
        Object.assign(this, data);
        if (this.status == 1) {
            this.switch_status = true;
        }
        if (this.dynamic == '1') {
            this.switch_dynamic = true;
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
        if (this.switch_dynamic) {
            this.dynamic = '1';
        } else {
            this.dynamic = '0';
        }

        if (this.picker_resumeTime) {
            this.resumeTime = format(this.picker_resumeTime, 'HH:mm');
        }
        if (this.picker_suspendTime) {
            this.suspendTime = format(this.picker_suspendTime, 'HH:mm');
        }
    }
}
