/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2016, Connect2id Ltd and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.nimbusds.jose.crypto.utils;


import java.math.BigInteger;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;


/**
 * Elliptic curve checks.
 *
 * @author Vladimir Dzhuvinov
 * @version 2017-04-13
 */
public class ECChecks {
	
	
	/**
	 * Checks if the specified (ephemeral) public key is on the curve of
	 * the private key. Intended to prevent an "Invalid Curve Attack",
	 * independent from any JCA provider checks (the SUN provider in Java
	 * 1.8.0_51+ and BouncyCastle have them, other / older provider do
	 * not).
	 *
	 * <p>See https://www.cs.bris.ac.uk/Research/CryptographySecurity/RWC/2017/nguyen.quan.pdf
	 *
	 * @param publicKey  The public EC key. Must not be {@code null}.
	 * @param privateKey The private EC key. Must not be {@code null}.
	 *
	 * @return {@code true} if public key passed the curve check.
	 */
	public static boolean isPointOnCurve(final ECPublicKey publicKey, final ECPrivateKey privateKey) {
		
		return isPointOnCurve(publicKey, privateKey.getParams());
	}
	
	
	/**
	 * Checks if the specified (ephemeral) public key is on the given
	 * curve. Intended to prevent an "Invalid Curve Attack", independent
	 * from any JCA provider checks (the SUN provider in Java 1.8.0_51+ and
	 * BouncyCastle have them, other / older provider do not).
	 *
	 * <p>See https://www.cs.bris.ac.uk/Research/CryptographySecurity/RWC/2017/nguyen.quan.pdf
	 *
	 * @param publicKey       The public EC key. Must not be {@code null}.
	 * @param ecParameterSpec The EC spec. Must not be {@code null}.
	 *
	 * @return {@code true} if public key passed the curve check.
	 */
	public static boolean isPointOnCurve(final ECPublicKey publicKey, final ECParameterSpec ecParameterSpec) {
		
		ECPoint point = publicKey.getW();
		return isPointOnCurve(point.getAffineX(), point.getAffineY(), ecParameterSpec);
	}
	
	
	/**
	 * Checks if the specified (ephemeral) public key is on the given
	 * curve. Intended to prevent an "Invalid Curve Attack", independent
	 * from any JCA provider checks (the SUN provider in Java 1.8.0_51+ and
	 * BouncyCastle have them, other / older provider do not).
	 *
	 * <p>See https://www.cs.bris.ac.uk/Research/CryptographySecurity/RWC/2017/nguyen.quan.pdf
	 *
	 * @param x               The public EC x coordinate. Must not be
	 *                        {@code null}.
	 * @param y               The public EC y coordinate. Must not be
	 *                        {@code null}.
	 * @param ecParameterSpec The EC spec. Must not be {@code null}.
	 *
	 * @return {@code true} if public key passed the curve check.
	 */
	public static boolean isPointOnCurve(final BigInteger x, final BigInteger y, final ECParameterSpec ecParameterSpec) {
		
		// Ensure the following condition is met:
		// (y^2) mod p = (x^3 + ax + b) mod p
		EllipticCurve curve = ecParameterSpec.getCurve();
		BigInteger a = curve.getA();
		BigInteger b = curve.getB();
		BigInteger p = ((ECFieldFp) curve.getField()).getP();
		BigInteger leftSide = (y.pow(2)).mod(p);
		BigInteger rightSide = (x.pow(3).add(a.multiply(x)).add(b)).mod(p);
		
		return leftSide.equals(rightSide);
	}
	
	
	/**
	 * Prevents public instantiation.
	 */
	private ECChecks() {}
}
