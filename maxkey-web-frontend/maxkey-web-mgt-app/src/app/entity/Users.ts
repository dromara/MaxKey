/*
 * Copyright [2022] [MaxKey of copyright http://www.maxkey.top]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
  education!: String;
  graduateFrom!: String;
  graduateDate!: String;
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
  str_married!: String;
  str_idType!: String;
  constructor() {
    super();
    this.status = 1;
    this.sortIndex = 1;
    this.gender = 1;
    this.userType = 'EMPLOYEE';
    this.userState = 'RESIDENT';
    this.gender_select = '1';
    this.str_married = '0';
    this.str_idType = '0';
    this.str_status = '1';
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
    this.str_status = `${this.status}`;
    this.str_married = `${this.married}`;
    this.str_idType = `${this.idType}`;
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
    this.status = Number.parseInt(`${this.str_status}`);
    this.married = Number.parseInt(`${this.str_married}`);
    this.idType = Number.parseInt(`${this.str_idType}`);
  }
}
