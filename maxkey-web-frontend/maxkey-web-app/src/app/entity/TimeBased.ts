import format from 'date-fns/format';

import { BaseEntity } from './BaseEntity';

export class TimeBased extends BaseEntity {
    displayName!: String;
    username!: String;
    digits!: String;
    period!: String;
    sharedSecret!: String;
    hexSharedSecret!: String;
    rqCode!: String;
}
