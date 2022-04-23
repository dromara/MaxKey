import { BaseEntity } from './BaseEntity';

export class Users extends BaseEntity {
    username!: String;
    password!: String;
    decipherable!: String;
    sharedSecret!: String;
    sharedCounter!: String;
    /**
     * "Employee", "Supplier","Dealer","Contractor",Partner,Customer "Intern",
     * "Temp", "External", and "Unknown" .
     */
    userType!: String;

    userState!: String;
    windowsAccount!: String;

    // for user name
    displayName!: String;
    nickName!: String;
    nameZhSpell!: String;
    nameZhShortSpell!: String;
    givenName!: String;
    middleName!: String;
    familyName!: String;
    honorificPrefix!: String;
    honorificSuffix!: String;
    formattedName!: String;

    married!: Number;
    gender!: Number;
    birthDate!: String;
    picture!: String;
    pictureId!: String;
    pictureBase64!: string;
    idType!: Number;
    idCardNo!: String;
    webSite!: String;
    startWorkDate!: String;

    // for security
    authnType!: String;
    email!: String;
    emailVerified!: Number;
    mobile!: String;
    mobileVerified!: String;
    passwordQuestion!: String;
    passwordAnswer!: String;

    // for apps login protected
    appLoginAuthnType!: String;
    appLoginPassword!: String;
    protectedApps!: String;
    protectedAppsMap!: String;

    passwordLastSetTime!: String;
    badPasswordCount!: Number;
    badPasswordTime!: String;
    unLockTime!: String;
    isLocked!: Number;
    lastLoginTime!: String;
    lastLoginIp!: String;
    lastLogoffTime!: String;
    passwordSetType!: Number;
    loginCount!: Number;
    regionHistory!: String;
    passwordHistory!: String;

    locale!: String;
    timeZone!: String;
    preferredLanguage!: String;

    // for work
    workCountry!: String;
    workRegion!: String; // province;
    workLocality!: String; // city;
    workStreetAddress!: String;
    workAddressFormatted!: String;
    workEmail!: String;
    workPhoneNumber!: String;
    workPostalCode!: String;
    workFax!: String;
    workOfficeName!: String;
    // for home
    homeCountry!: String;
    homeRegion!: String; // province;
    homeLocality!: String; // city;
    homeStreetAddress!: String;
    homeAddressFormatted!: String;
    homeEmail!: String;
    homePhoneNumber!: String;
    homePostalCode!: String;
    homeFax!: String;
    // for company
    employeeNumber!: String;
    costCenter!: String;
    organization!: String;
    division!: String;
    departmentId!: String;
    department!: String;
    jobTitle!: String;
    jobLevel!: String;
    managerId!: String;
    manager!: String;
    assistantId!: String;
    assistant!: String;
    entryDate!: String;
    quitDate!: String;

    // for social contact
    defineIm!: String;
    theme!: String;
    /*
     * for extended Attribute from userType extraAttribute for database
     * extraAttributeName & extraAttributeValue for page submit
     */
    //protected String extraAttribute;
    //protected String extraAttributeName;
    //protected String extraAttributeValue;
    //@JsonIgnore
    //protected HashMap<String, String> extraAttributeMap;

    online!: Number;

    gridList!: Number;
    switch_dynamic: boolean = false;

    gender_select!: String;

    constructor() {
        super();
        this.status = 1;
        this.sortIndex = 1;
        this.gender = 1;
        this.userType = 'EMPLOYEE';
        this.userState = 'RESIDENT';
        this.gender_select = '1';
    }

    override init(data: any): void {
        Object.assign(this, data);
        if (this.status == 1) {
            this.switch_status = true;
        }
        if (this.gender == 1) {
            this.gender_select = '1';
        } else {
            this.gender_select = '2';
        }
    }
    override trans(): void {
        if (this.switch_status) {
            this.status = 1;
        } else {
            this.status = 0;
        }

        if (this.gender_select == '1') {
            this.gender = 1;
        } else {
            this.gender = 2;
        }
    }
}
