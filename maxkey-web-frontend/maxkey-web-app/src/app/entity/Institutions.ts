import { BaseEntity } from './BaseEntity';

export class Institutions extends BaseEntity {
    name!: String;
    fullName!: String;
    contact!: String;
    email!: String;
    phone!: String;
    address!: String;
    logo!: String;
    frontTitle!: String;
    consoleTitle!: String;
    domain!: String;
    captchaType!: String;
    captchaSupport!: String;
    defaultUri!: String;
}
