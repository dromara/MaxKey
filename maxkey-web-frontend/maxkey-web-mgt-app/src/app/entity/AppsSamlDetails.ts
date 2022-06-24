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

import format from 'date-fns/format';

import { Apps } from './Apps';

export class AppsSamlDetails extends Apps {
    certIssuer!: String;

    certSubject!: String;

    certExpiration!: String;

    signature!: String;

    digestMethod!: String;

    entityId!: String;

    spAcsUrl!: String;

    issuer!: String;

    audience!: String;

    nameidFormat!: String;

    validityInterval!: String;
    /**
     * Redirect-Post Post-Post IdpInit-Post Redirect-PostSimpleSign
     * Post-PostSimpleSign IdpInit-PostSimpleSign
     */

    binding!: String;

    /**
     * 0 false 1 true
     */

    encrypted!: Number;
    /**
     * metadata_file metadata_url or certificate
     */
    fileType!: String;

    metaUrl!: String;

    metaFileId!: String;

    /**
     * 0 original 1 uppercase 2 lowercase
     */

    nameIdConvert!: Number;

    nameIdSuffix!: String;

    constructor() {
        super();
        this.fileType = 'certificate';
    }

    override init(data: any): void {
        Object.assign(this, data);
        super.init(data);
        this.fileType = 'certificate';
        this.metaUrl = '';
        if (this.category == null || this.category == '') {
            this.category = 'NONE';
        }
        if (this.status == 1) {
            this.switch_status = true;
        }
    }

    override trans(): void {
        if (this.switch_status) {
            this.status = 1;
        } else {
            this.status = 0;
        }
    }
}
