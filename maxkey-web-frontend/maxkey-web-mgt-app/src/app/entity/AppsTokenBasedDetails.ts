import format from 'date-fns/format';

import { Apps } from './Apps';

export class AppsTokenBasedDetails extends Apps {
    redirectUri!: String;
    tokenType!: String;
    cookieName!: String;
    algorithm!: String;
    algorithmKey!: String;
    expires!: Number;

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
