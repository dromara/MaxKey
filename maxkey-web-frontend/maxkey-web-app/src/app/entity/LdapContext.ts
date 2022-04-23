import { BaseEntity } from './BaseEntity';

export class LdapContext extends BaseEntity {
  product!: String;
  providerUrl!: String;
  principal!: String;
  credentials!: String;
  sslSwitch!: Number;
  filters!: String;
  basedn!: String;
  msadDomain!: String;
  accountMapping!: String;
  trustStore!: String;
  trustStorePassword!: String;
  switch_sslSwitch: boolean = false;
  switch_accountMapping: boolean = false;

  override init(data: any): void {
    Object.assign(this, data);
    if (this.sslSwitch == 1) {
      this.switch_sslSwitch = true;
    }
    if (this.status == 1) {
      this.switch_status = true;
    }
    if (this.accountMapping == 'YES') {
      this.switch_accountMapping = true;
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
    if (this.switch_accountMapping) {
      this.accountMapping = 'YES';
    } else {
      this.accountMapping = 'NO';
    }
  }
}
