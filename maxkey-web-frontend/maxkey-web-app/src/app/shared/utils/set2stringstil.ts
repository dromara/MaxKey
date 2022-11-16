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

export function set2String(set: Set<String>): string {
  let setValues = '';
  set.forEach(value => {
    setValues = `${setValues + value},`;
  });
  return setValues;
}

export function splitString(str: String, length: number): string[] {
  let arrayValues: string[] = [];
  let tempStr = str;
  let index = 0;
  while (tempStr != '') {
    arrayValues[index] = tempStr.substring(0, length);
    tempStr = tempStr.substring(length, tempStr.length);
    index++;
  }
  return arrayValues;
}

export function concatArrayString(arrayValues: String[], split: String): string {
  let tempStr = '';
  for (let index in arrayValues) {
    if (tempStr !== '') {
      tempStr = tempStr + split;
    }
    tempStr = tempStr + arrayValues[index];
  }
  return tempStr;
}
