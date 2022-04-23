import format from 'date-fns/format';

import { BaseEntity } from './BaseEntity';

export class Adapters extends BaseEntity {
    name!: String;
    protocol!: String;
    adapter!: String;
    switch_dynamic: boolean = false;

    constructor() {
        super();
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
