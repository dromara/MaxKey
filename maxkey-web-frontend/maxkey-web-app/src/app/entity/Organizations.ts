import { BaseEntity } from './BaseEntity';

export class Organizations extends BaseEntity {
    code!: String;
    name!: String;
    fullName!: String;
    parentId!: String;
    parentCode!: string;
    parentName!: String;
    type!: String;
    codePath!: String;
    namePath!: String;
    level!: Number;
    division!: String;
    country!: String;
    region!: String;
    locality!: String;
    street!: String;
    address!: String;
    contact!: String;
    postalCode!: String;
    phone!: String;
    fax!: String;
    email!: String;
    switch_dynamic: boolean = false;

    constructor() {
        super();
        this.status = 1;
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
