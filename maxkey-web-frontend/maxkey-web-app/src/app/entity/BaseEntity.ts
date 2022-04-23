export class BaseEntity {
    id!: String;
    instId!: String;
    instName!: String;
    sortIndex!: Number;
    status!: Number;
    description!: String;
    switch_status: boolean = false;

    constructor() {
        this.status = 1;
    }
    init(data: any): void {
        Object.assign(this, data);
        if (this.status == 1) {
            this.switch_status = true;
        }
    }
    trans(): void {
        if (this.switch_status) {
            this.status = 1;
        } else {
            this.status = 0;
        }
    }
}
