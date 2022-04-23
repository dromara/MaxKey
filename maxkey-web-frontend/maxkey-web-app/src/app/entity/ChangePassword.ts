import { BaseEntity } from './BaseEntity';

export class ChangePassword extends BaseEntity {
    userId!: String;
    username!: String;
    email!: String;
    mobile!: String;
    displayName!: String;
    oldPassword!: String;
    password!: String;
    confirmPassword!: String;
    decipherable!: String;
}
