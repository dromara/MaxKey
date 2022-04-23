import format from 'date-fns/format';

import { BaseEntity } from './BaseEntity';

export class AccountsStrategy extends BaseEntity {
    name!: String;
    appId!: String;
    appName!: String;
    filters!: String;
    orgIdsList!: String;
    mapping!: String;
    suffixes!: String;
    createType!: String;
    switch_dynamic: boolean = false;
    picker_resumeTime: Date = new Date(format(new Date(), 'yyyy-MM-dd 00:00:00'));
    picker_suspendTime: Date = new Date(format(new Date(), 'yyyy-MM-dd 00:00:00'));

    constructor() {
        super();
        this.createType = 'manual';
        this.mapping = 'username';
    }

    override init(data: any): void {
        Object.assign(this, data);
        if (this.status == 1) {
            this.switch_status = true;
        }
    }

    override trans(): void {
        if (this.switch_status) {
            this.status = 1;
        } else {
            this.status = 0;
        }
    }
}
