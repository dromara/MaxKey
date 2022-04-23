import { BaseEntity } from './BaseEntity';

export class Resources extends BaseEntity {
    name!: String;
    appId!: String;
    appName!: String;
    parentId!: String;
    parentName!: String;
    resourceType!: String;
    resourceIcon!: String;
    resourceStyle!: String;
    resourceUrl!: String;
    resourceAction!: String;
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
