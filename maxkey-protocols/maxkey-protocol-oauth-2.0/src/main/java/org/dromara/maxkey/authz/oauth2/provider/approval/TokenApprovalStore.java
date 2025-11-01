/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.maxkey.authz.oauth2.provider.approval;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.dromara.maxkey.authz.oauth2.common.OAuth2AccessToken;
import org.dromara.maxkey.authz.oauth2.provider.OAuth2Authentication;
import org.dromara.maxkey.authz.oauth2.provider.approval.Approval.ApprovalStatus;
import org.dromara.maxkey.authz.oauth2.provider.token.AuthorizationServerTokenServices;
import org.dromara.maxkey.authz.oauth2.provider.token.TokenStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link ApprovalStore} that works with an existing {@link TokenStore}, extracting implicit {@link Approval
 * Approvals} from the content of tokens already in the store. Useful interface so that users can list and revoke
 * approvals even if they are not really represented in such a way internally. For full fine-grained control of user
 * approvals don't use a TokenStore at all, and don't use this ApprovalStore with Approval-based
 * {@link AuthorizationServerTokenServices} implementations.
 * 
 * @author Dave Syer
 * 
 */
public class TokenApprovalStore implements ApprovalStore {

    static final Logger _logger = LoggerFactory.getLogger(TokenApprovalStore.class);
            
    private TokenStore store;

    /**
     * @param store the token store to set
     */
    public void setTokenStore(TokenStore store) {
        this.store = store;
    }

    /**
     * This implementation is a no-op. We assume that the {@link TokenStore} is populated elsewhere, by (for example) a
     * token services instance that knows more about granted tokens than we could possibly infer from the approvals.
     * 
     * @see org.dromara.maxkey.authz.oauth2.provider.approval.ApprovalStore#addApprovals(java.util.Collection)
     */
    @Override
    public boolean addApprovals(Collection<Approval> approvals) {
        _logger.debug("add Approvals...");
        return true;
    }

    /**
     * Revoke all tokens that match the client and user in the approvals supplied.
     * 
     * @see org.dromara.maxkey.authz.oauth2.provider.approval.ApprovalStore#revokeApprovals(java.util.Collection)
     */
    @Override
    public boolean revokeApprovals(Collection<Approval> approvals) {
        _logger.debug("revoke Approvals " + approvals);
        boolean success = true;
        for (Approval approval : approvals) {
            Collection<OAuth2AccessToken> tokens = store.findTokensByClientIdAndUserName(approval.getClientId(), approval.getUserId());
            for (OAuth2AccessToken token : tokens) {
                OAuth2Authentication authentication = store.readAuthentication(token);
                if (authentication != null
                        && approval.getClientId().equals(authentication.getOAuth2Request().getClientId())) {
                    store.removeAccessToken(token);
                }
            }
        }
        return success;
    }

    /**
     * Extract the implied approvals from any tokens associated with the user and client id supplied.
     * 
     * @see org.dromara.maxkey.authz.oauth2.provider.approval.ApprovalStore#getApprovals(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Collection<Approval> getApprovals(String userId, String clientId) {
        _logger.trace("userId " + userId+" , clientId " + clientId);
        Collection<Approval> result = new HashSet<Approval>();
        Collection<OAuth2AccessToken> tokens = store.findTokensByClientIdAndUserName(clientId, userId);
        _logger.trace("tokens Collection " + tokens);
        for (OAuth2AccessToken token : tokens) {
            _logger.trace("token " + token);
            if(token != null) {
                OAuth2Authentication authentication = store.readAuthentication(token);
                _logger.trace("authentication " + authentication);
                if (authentication != null) {
                    Date expiresAt = token.getExpiration();
                    for (String scope : token.getScope()) {
                        Approval approval = new Approval(userId, clientId, scope, expiresAt, ApprovalStatus.APPROVED);
                        result.add(approval);
                        _logger.trace("add approval " + approval);
                    }
                }
            }
        }
        return result;
    }

}
