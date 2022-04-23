import { BaseEntity } from './BaseEntity';

export class PasswordPolicy extends BaseEntity {
  minLength!: Number;
  maxLength!: Number;
  lowerCase!: Number;
  upperCase!: Number;
  digits!: Number;
  specialChar!: Number;
  attempts!: Number;
  duration!: Number;
  expiration!: Number;
  username!: Number;
  history!: Number;
  dictionary!: Number;
  alphabetical!: Number;
  numerical!: Number;
  qwerty!: Number;
  occurances!: Number;
  switch_username: boolean = false;
  switch_dictionary: boolean = false;
  switch_alphabetical: boolean = false;
  switch_numerical: boolean = false;
  switch_qwerty: boolean = false;

  override init(data: any): void {
    Object.assign(this, data);

    if (this.alphabetical == 1) {
      this.switch_alphabetical = true;
    }
    if (this.username == 1) {
      this.switch_username = true;
    }
    if (this.qwerty == 1) {
      this.switch_qwerty = true;
    }
    if (this.numerical == 1) {
      this.switch_numerical = true;
    }
    if (this.dictionary == 1) {
      this.switch_dictionary = true;
    }
  }

  override trans(): void {
    if (this.switch_alphabetical) {
      this.alphabetical = 1;
    } else {
      this.alphabetical = 0;
    }
    if (this.switch_username) {
      this.username = 1;
    } else {
      this.username = 0;
    }
    if (this.switch_qwerty) {
      this.qwerty = 1;
    } else {
      this.qwerty = 0;
    }
    if (this.switch_numerical) {
      this.numerical = 1;
    } else {
      this.numerical = 0;
    }
    if (this.switch_dictionary) {
      this.dictionary = 1;
    } else {
      this.dictionary = 0;
    }
  }
}
