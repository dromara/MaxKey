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
 

package org.dromara.maxkey.authz.oauth2.provider.wellknown;

import java.util.Set;

public class OauthServerConfiguration {
	String client_id;
	String issuer;
	String authorization_endpoint;
	String token_endpoint;
	String userinfo_endpoint;
	String registration_endpoint;
	String jwks_uri;
	Set<String> code_challenge_methods_supported;
	
	Set<String> response_types_supported;
	Set<String> response_modes_supported;
	Set<String> grant_types_supported;
	Set<String> subject_types_supported;
	Set<String> id_token_signing_alg_values_supported;

	Set<String> scopes_supported;
	Set<String> token_endpoint_auth_methods_supported;
	Set<String> claims_supported;
	String introspection_endpoint;
	Set<String> introspection_endpoint_auth_methods_supported;
	String revocation_endpoint;
	Set<String> revocation_endpoint_auth_methods_supported;
	String end_session_endpoint;
	boolean request_parameter_supported;
	Set<String> request_object_signing_alg_values_supported;
	Set<String> backchannel_token_delivery_modes_supported;
	Set<String> backchannel_authentication_request_signing_alg_values_supported;

	
	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getAuthorization_endpoint() {
		return authorization_endpoint;
	}

	public void setAuthorization_endpoint(String authorization_endpoint) {
		this.authorization_endpoint = authorization_endpoint;
	}

	public Set<String> getCode_challenge_methods_supported() {
		return code_challenge_methods_supported;
	}

	public void setCode_challenge_methods_supported(Set<String> code_challenge_methods_supported) {
		this.code_challenge_methods_supported = code_challenge_methods_supported;
	}

	public String getToken_endpoint() {
		return token_endpoint;
	}

	public void setToken_endpoint(String token_endpoint) {
		this.token_endpoint = token_endpoint;
	}

	public String getUserinfo_endpoint() {
		return userinfo_endpoint;
	}

	public void setUserinfo_endpoint(String userinfo_endpoint) {
		this.userinfo_endpoint = userinfo_endpoint;
	}

	public String getRegistration_endpoint() {
		return registration_endpoint;
	}

	public void setRegistration_endpoint(String registration_endpoint) {
		this.registration_endpoint = registration_endpoint;
	}

	public String getJwks_uri() {
		return jwks_uri;
	}

	public void setJwks_uri(String jwks_uri) {
		this.jwks_uri = jwks_uri;
	}

	public Set<String> getResponse_types_supported() {
		return response_types_supported;
	}

	public void setResponse_types_supported(Set<String> response_types_supported) {
		this.response_types_supported = response_types_supported;
	}

	public Set<String> getResponse_modes_supported() {
		return response_modes_supported;
	}

	public void setResponse_modes_supported(Set<String> response_modes_supported) {
		this.response_modes_supported = response_modes_supported;
	}

	public Set<String> getGrant_types_supported() {
		return grant_types_supported;
	}

	public void setGrant_types_supported(Set<String> grant_types_supported) {
		this.grant_types_supported = grant_types_supported;
	}

	public Set<String> getSubject_types_supported() {
		return subject_types_supported;
	}

	public void setSubject_types_supported(Set<String> subject_types_supported) {
		this.subject_types_supported = subject_types_supported;
	}

	public Set<String> getId_token_signing_alg_values_supported() {
		return id_token_signing_alg_values_supported;
	}

	public void setId_token_signing_alg_values_supported(Set<String> id_token_signing_alg_values_supported) {
		this.id_token_signing_alg_values_supported = id_token_signing_alg_values_supported;
	}

	public Set<String> getScopes_supported() {
		return scopes_supported;
	}

	public void setScopes_supported(Set<String> scopes_supported) {
		this.scopes_supported = scopes_supported;
	}

	public Set<String> getToken_endpoint_auth_methods_supported() {
		return token_endpoint_auth_methods_supported;
	}

	public void setToken_endpoint_auth_methods_supported(Set<String> token_endpoint_auth_methods_supported) {
		this.token_endpoint_auth_methods_supported = token_endpoint_auth_methods_supported;
	}

	public Set<String> getClaims_supported() {
		return claims_supported;
	}

	public void setClaims_supported(Set<String> claims_supported) {
		this.claims_supported = claims_supported;
	}

	public String getIntrospection_endpoint() {
		return introspection_endpoint;
	}

	public void setIntrospection_endpoint(String introspection_endpoint) {
		this.introspection_endpoint = introspection_endpoint;
	}

	public Set<String> getIntrospection_endpoint_auth_methods_supported() {
		return introspection_endpoint_auth_methods_supported;
	}

	public void setIntrospection_endpoint_auth_methods_supported(
			Set<String> introspection_endpoint_auth_methods_supported) {
		this.introspection_endpoint_auth_methods_supported = introspection_endpoint_auth_methods_supported;
	}

	public String getRevocation_endpoint() {
		return revocation_endpoint;
	}

	public void setRevocation_endpoint(String revocation_endpoint) {
		this.revocation_endpoint = revocation_endpoint;
	}

	public Set<String> getRevocation_endpoint_auth_methods_supported() {
		return revocation_endpoint_auth_methods_supported;
	}

	public void setRevocation_endpoint_auth_methods_supported(
			Set<String> revocation_endpoint_auth_methods_supported) {
		this.revocation_endpoint_auth_methods_supported = revocation_endpoint_auth_methods_supported;
	}

	public String getEnd_session_endpoint() {
		return end_session_endpoint;
	}

	public void setEnd_session_endpoint(String end_session_endpoint) {
		this.end_session_endpoint = end_session_endpoint;
	}

	public boolean isRequest_parameter_supported() {
		return request_parameter_supported;
	}

	public void setRequest_parameter_supported(boolean request_parameter_supported) {
		this.request_parameter_supported = request_parameter_supported;
	}

	public Set<String> getRequest_object_signing_alg_values_supported() {
		return request_object_signing_alg_values_supported;
	}

	public void setRequest_object_signing_alg_values_supported(
			Set<String> request_object_signing_alg_values_supported) {
		this.request_object_signing_alg_values_supported = request_object_signing_alg_values_supported;
	}

	public Set<String> getBackchannel_token_delivery_modes_supported() {
		return backchannel_token_delivery_modes_supported;
	}

	public void setBackchannel_token_delivery_modes_supported(
			Set<String> backchannel_token_delivery_modes_supported) {
		this.backchannel_token_delivery_modes_supported = backchannel_token_delivery_modes_supported;
	}

	public Set<String> getBackchannel_authentication_request_signing_alg_values_supported() {
		return backchannel_authentication_request_signing_alg_values_supported;
	}

	public void setBackchannel_authentication_request_signing_alg_values_supported(
			Set<String> backchannel_authentication_request_signing_alg_values_supported) {
		this.backchannel_authentication_request_signing_alg_values_supported = backchannel_authentication_request_signing_alg_values_supported;
	}

	public OauthServerConfiguration() {
		super();
	}

}
