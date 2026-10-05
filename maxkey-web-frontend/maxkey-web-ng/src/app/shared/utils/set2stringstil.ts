/*
 * Copyright (c) 2024, MaxKey and/or its affiliates. All rights reserved.
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * License Restrictions
 * This software and related documentation are provided under a license
 * agreement containing restrictions on use and disclosure and are
 * protected by intellectual property laws. Except as expressly permitted
 * in your license agreement or allowed by law, you may not use, copy,
 * reproduce, translate, broadcast, modify, license, transmit, distribute,
 * exhibit, perform, publish, or display any part, in any form, or by any means.
 * Reverse engineering, disassembly, or decompilation of this software,
 * unless required by law for interoperability, is prohibited.
 *
 * Please contact MaxKey, visit www.maxkey.top if you need additional information
 * or have any questions,support email support@maxsso.net .
 *
 */

export function set2String(set: Set<string>): string {
  let setValues = '';
  set.forEach(value => {
    setValues = `${setValues + value},`;
  });
  return setValues;
}

export function splitString(str: string, length: number): string[] {
  let arrayValues: string[] = [];
  let tempStr = str;
  let index = 0;
  while (tempStr != '') {
    //console.log(`tempStr: ${tempStr}`);
    if (tempStr === undefined) {
      break;
    }
    arrayValues[index] = tempStr.substring(0, length);
    tempStr = tempStr.substring(length, tempStr.length);
    index++;
  }
  return arrayValues;
}

export function concatArrayString(arrayValues: string[], split: string): string {
  let tempStr = '';
  for (let index in arrayValues) {
    if (tempStr !== '') {
      tempStr = tempStr + split;
    }
    tempStr = tempStr + arrayValues[index];
  }
  return tempStr;
}
