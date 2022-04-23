import { BaseEntity } from './BaseEntity';

export class SmsProvider extends BaseEntity {
  provider!: String;
  providerName!: String;
  message!: String;
  appKey!: String;
  appSecret!: String;
  templateId!: String;
  signName!: String;
  smsSdkAppId!: String;

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
