import { BaseEntity } from './BaseEntity';

export class EmailSenders extends BaseEntity {
  account!: String;
  credentials!: String;
  smtpHost!: String;
  port!: Number;
  sslSwitch!: Number;
  sender!: String;
  encoding!: String;
  protocol!: String;
  switch_sslSwitch: boolean = false;

  override init(data: any): void {
    Object.assign(this, data);
    if (this.sslSwitch == 1) {
      this.switch_sslSwitch = true;
    }
    if (this.status == 1) {
      this.switch_status = true;
    }
  }

  override trans(): void {
    if (this.switch_sslSwitch) {
      this.sslSwitch = 1;
    } else {
      this.sslSwitch = 0;
    }
    if (this.switch_status) {
      this.status = 1;
    } else {
      this.status = 0;
    }
  }
}
