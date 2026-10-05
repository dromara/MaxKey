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
 


export function knowHost() {
  let hostArray: string[] = new Array('localhost', 'sso.maxkey.top', 'mgt.maxkey.top', 'sso.maxsso.net', 'mgt.maxsso.net');
  for (var i = 0; i < hostArray.length; i++) {
    if (hostArray[i] == location.hostname) {
      return true;
    }
  }
  return false;
}
