import { BaseEntity } from './BaseEntity';

export class SocialsProvider extends BaseEntity {
  provider!: String;
  providerName!: String;
  icon!: String;
  clientId!: String;
  clientSecret!: String;
  agentId!: String;
  hidden!: String;
  scanCode!: String;
  switch_hidden: boolean = false;

  constructor() {
    super();
    this.status = 1;
    this.scanCode = 'none';
    this.sortIndex = 1;
  }

  override init(data: any): void {
    Object.assign(this, data);
    if (this.status == 1) {
      this.switch_status = true;
    }
    if (this.hidden == 'true') {
      this.switch_hidden = true;
    }
  }

  override trans(): void {
    if (this.switch_status) {
      this.status = 1;
    } else {
      this.status = 0;
    }

    if (this.switch_hidden) {
      this.hidden = 'true';
    }
  }
}
