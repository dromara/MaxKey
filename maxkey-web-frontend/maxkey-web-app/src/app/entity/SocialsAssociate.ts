import { BaseEntity } from './BaseEntity';
import { SocialsProvider } from './SocialsProvider';

export class SocialsAssociate extends SocialsProvider {
  redirectUri!: String;
  accountId!: String;
  bindTime!: String;
  unBindTime!: String;
  lastLoginTime!: String;
  state!: String;
  userBind!: String;

  constructor() {
    super();
  }

  override init(data: any): void {
    Object.assign(this, data);
  }

  override trans(): void { }
}
