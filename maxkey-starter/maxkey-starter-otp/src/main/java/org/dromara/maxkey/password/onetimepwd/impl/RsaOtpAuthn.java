/*
 * Copyright [2020] [MaxKey of copyright http://www.maxkey.top]
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
 

package org.dromara.maxkey.password.onetimepwd.impl;

import org.dromara.maxkey.entity.idm.UserInfo;
import org.dromara.maxkey.password.onetimepwd.AbstractOtpAuthn;

/**
 * Chip Authentication Program EMV stands for Europay, MasterCard and Visa, a
 * global standard for inter-operation of integrated circuit cards (IC cards or
 * "chip cards") and IC card capable point of sale (POS) terminals and automated
 * teller machines (ATMs), for authenticating credit and debit card
 * transactions.
 * 
 * @author Crystal.Sea
 *
 */
public class RsaOtpAuthn extends AbstractOtpAuthn {

    
    
    public RsaOtpAuthn() {
        otpType = OtpTypes.RSA_OTP;
    }

    @Override
    public boolean produce(UserInfo userInfo) {
        return false;
    }

    @Override
    public boolean validate(UserInfo userInfo, String token) {
        return false;
    }

}
