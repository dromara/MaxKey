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
