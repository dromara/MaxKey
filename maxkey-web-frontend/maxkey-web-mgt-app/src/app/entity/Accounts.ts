import format from 'date-fns/format';

import { BaseEntity } from './BaseEntity';

export class Accounts extends BaseEntity {
    strategyId!: String;
    strategyName!: String;
    appId!: String;
    appName!: String;
    userId!: String;
    username!: String;
    displayName!: String;
    relatedUsername!: String;
    relatedPassword!: String;
    createType!: String;

    constructor() {
        super();
        this.createType = 'manual';
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
