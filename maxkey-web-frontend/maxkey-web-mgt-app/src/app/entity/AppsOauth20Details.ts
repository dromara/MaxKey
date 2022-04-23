import format from 'date-fns/format';

import { Apps } from './Apps';

export class AppsOauth20Details extends Apps {
    clientId!: String;

    clientSecret!: String;

    scope!: String;

    resourceIds!: String;

    authorizedGrantTypes!: String;

    registeredRedirectUris!: String;

    authorities!: String;

    accessTokenValiditySeconds!: String;

    refreshTokenValiditySeconds!: String;

    approvalPrompt!: String;

    // for OpenID Connect

    issuer!: String;

    audience!: String;

    algorithm!: String;

    algorithmKey!: String;

    encryptionMethod!: String;

    signature!: String;

    signatureKey!: String;

    subject!: String;

    userInfoResponse!: String;

    pkce!: String;

    select_authorizedGrantTypes!: string[];

    select_scope!: string[];

    constructor() {
        super();
        this.select_authorizedGrantTypes = ['authorization_code'];
        this.select_scope = ['read'];
        this.pkce = 'no';
        this.approvalPrompt = 'auto';
    }

    override init(data: any): void {
        Object.assign(this, data);
        console.log(data);
        if (this.status == 1) {
            this.switch_status = true;
        }
        if (this.approvalPrompt == '') {
        }
        this.select_scope = [''];
        let scopeArray: String[] = `${this.scope},`.split(',');
        for (let i = 0; i < scopeArray.length; i++) {
            this.select_scope.push(`${scopeArray[i]}`);
        }

        this.select_authorizedGrantTypes = [''];
        let authorizedGrantTypesArray = `${this.authorizedGrantTypes},`.split(',');
        for (let i = 0; i < authorizedGrantTypesArray.length; i++) {
            this.select_authorizedGrantTypes.push(authorizedGrantTypesArray[i]);
        }
        if (this.pkce == null || this.pkce == '') {
            this.pkce = 'no';
        }
        this.pkce = this.pkce.toLowerCase();
    }

    override trans(): void {
        if (this.switch_status) {
            this.status = 1;
        } else {
            this.status = 0;
        }
    }
}
