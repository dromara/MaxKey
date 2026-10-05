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
  username!: string;
  password!: string;
  decipherable!: string;
  sharedSecret!: string;
  sharedCounter!: string;
  /**
   * "Employee", "Supplier","Dealer","Contractor",Partner,Customer "Intern",
   * "Temp", "External", and "Unknown" .
   */
  userType!: string;

  userState!: string;
  windowsAccount!: string;

  // for user name
  displayName!: string;
  nickName!: string;
  nameZhSpell!: string;
  nameZhShortSpell!: string;
  givenName!: string;
  middleName!: string;
  familyName!: string;
  honorificPrefix!: string;
  honorificSuffix!: string;
  formattedName!: string;

  married!: number;
  gender!: number;
  birthDate!: string;
  picture!: string;
  pictureId!: string;
  pictureBase64!: string;
  idType!: number;
  idCardNo!: string;
  education!: string;
  graduateFrom!: string;
  graduateDate!: string;
  webSite!: string;
  startWorkDate!: string;

  // for security
  authnType!: string;
  email!: string;
  emailVerified!: number;
  mobile!: string;
  mobileVerified!: string;
  passwordQuestion!: string;
  passwordAnswer!: string;

  // for apps login protected
  appLoginAuthnType!: string;
  appLoginPassword!: string;
  protectedApps!: string;
  protectedAppsMap!: string;

  passwordLastSetTime!: string;
  badPasswordCount!: number;
  badPasswordTime!: string;
  unLockTime!: string;
  isLocked!: number;
  lastLoginTime!: string;
  lastLoginIp!: string;
  lastLogoffTime!: string;
  passwordSetType!: number;
  loginCount!: number;
  regionHistory!: string;
  passwordHistory!: string;

  locale!: string;
  timeZone!: string;
  preferredLanguage!: string;

  // for work
  workCountry!: string;
  workRegion!: string; // province;
  workLocality!: string; // city;
  workStreetAddress!: string;
  workAddressFormatted!: string;
  workEmail!: string;
  workPhoneNumber!: string;
  workPostalCode!: string;
  workFax!: string;
  workOfficeName!: string;
  // for home
  homeCountry!: string;
  homeRegion!: string; // province;
  homeLocality!: string; // city;
  homeStreetAddress!: string;
  homeAddressFormatted!: string;
  homeEmail!: string;
  homePhoneNumber!: string;
  homePostalCode!: string;
  homeFax!: string;
  // for company
  employeeNumber!: string;
  costCenter!: string;
  organization!: string;
  division!: string;
  departmentId!: string;
  department!: string;
  jobTitle!: string;
  jobLevel!: string;
  managerId!: string;
  manager!: string;
  assistantId!: string;
  assistant!: string;
  entryDate!: string;
  quitDate!: string;

  // for social contact
  defineIm!: string;
  theme!: string;
  /*
   * for extended Attribute from userType extraAttribute for database
   * extraAttributeName & extraAttributeValue for page submit
   */
  //protected String extraAttribute;
  //protected String extraAttributeName;
  //protected String extraAttributeValue;
  //@JsonIgnore
  //protected HashMap<String, String> extraAttributeMap;

  online!: number;

  gridList!: number;
  switch_dynamic: boolean = false;

  gender_select!: string;
  str_married!: string;
  str_idType!: string;
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
