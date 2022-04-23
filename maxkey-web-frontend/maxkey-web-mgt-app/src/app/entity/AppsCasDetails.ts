import format from 'date-fns/format';

import { Apps } from './Apps';

export class AppsCasDetails extends Apps {
    service!: String;
    callbackUrl!: String;
    casUser!: String;
    expires!: Number;

    constructor() {
        super();
        this.expires = 30;
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
