import format from 'date-fns/format';

import { BaseEntity } from './BaseEntity';

export class Apps extends BaseEntity {
    name!: String;
    loginUrl!: String;
    category!: String;
    protocol!: String;
    secret!: String;
    iconBase64!: String;
    visible!: String;
    inducer!: String;
    vendor!: String;
    vendorUrl!: String;
    credential!: String;
    sharedUsername!: String;
    sharedPassword!: String;
    systemUserAttr!: String;
    principal!: String;
    credentials!: String;

    logoutUrl!: String;
    logoutType!: String;
    isExtendAttr!: String;
    extendAttr!: String;
    userPropertys!: String;
    isSignature!: String;
    isAdapter!: String;
    adapterId!: String;
    adapterName!: String;
    adapter!: String;
    iconId!: String;

    select_userPropertys!: String[];

    constructor() {
        super();
        this.category = 'none';
        this.visible = '0';
        this.isAdapter = '0';
        this.logoutType = '0';
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
