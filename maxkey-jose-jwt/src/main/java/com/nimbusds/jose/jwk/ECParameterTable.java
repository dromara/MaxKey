/*
 * nimbus-jose-jwt
 *
 * Copyright 2012-2016, Connect2id Ltd.
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

package com.nimbusds.jose.jwk;


import java.math.BigInteger;
import java.security.spec.*;


/**
 * Elliptic curve parameter table.
 *
 * <p>Supports the following standard EC JWK curves:
 *
 * <ul>
 *     <li>{@link com.nimbusds.jose.jwk.Curve#P_256}
 *     <li>{@link com.nimbusds.jose.jwk.Curve#P_256K}
 *     <li>{@link com.nimbusds.jose.jwk.Curve#P_384}
 *     <li>{@link com.nimbusds.jose.jwk.Curve#P_521}
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 * @author Aleksei Doroganov
 * @version 2018-03-28
 */
class ECParameterTable {


	/**
	 * The parameter spec for a
	 * {@link com.nimbusds.jose.jwk.Curve#P_256} curve.
	 */
	private static final ECParameterSpec P_256_SPEC;
	

	/**
	 * The parameter spec for a
	 * {@link com.nimbusds.jose.jwk.Curve#P_256K} curve.
	 */
	private static final ECParameterSpec P_256K_SPEC;

	
	/**
	 * The parameter spec for a
	 * {@link com.nimbusds.jose.jwk.Curve#P_384} curve.
	 */
	private static final ECParameterSpec P_384_SPEC;

	
	/**
	 * The parameter spec for a
	 * {@link com.nimbusds.jose.jwk.Curve#P_521} curve.
	 */
	private static final ECParameterSpec P_521_SPEC;


	/**
	 * Simple EC field implementation.
	 */
	private static class ECFieldImpl implements ECField {


		/**
		 * The field size.
		 */
		private int size;


		/**
		 * Creates a new EC field with the specified size.
		 *
		 * @param size The EC field size.
		 */
		public ECFieldImpl(final int size) {

			this.size = size;
		}


		@Override
		public int getFieldSize() {
			return size;
		}
	}


	static {
		// Values obtained from org.bouncycastle.jce.ECNamedCurveTable

		P_256_SPEC = new ECParameterSpec(
			new EllipticCurve(
				new ECFieldFp(new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853951")),
				new BigInteger("115792089210356248762697446949407573530086143415290314195533631308867097853948"),
				new BigInteger("41058363725152142129326129780047268409114441015993725554835256314039467401291")),
			new ECPoint(
				new BigInteger("48439561293906451759052585252797914202762949526041747995844080717082404635286"),
				new BigInteger("36134250956749795798585127919587881956611106672985015071877198253568414405109")),
			new BigInteger("115792089210356248762697446949407573529996955224135760342422259061068512044369"),
			1);

		P_256K_SPEC = new ECParameterSpec(
			new EllipticCurve(
				new ECFieldFp(new BigInteger("115792089237316195423570985008687907853269984665640564039457584007908834671663")),
				new BigInteger("0"),
				new BigInteger("7")),
			new ECPoint(
				new BigInteger("55066263022277343669578718895168534326250603453777594175500187360389116729240"),
				new BigInteger("32670510020758816978083085130507043184471273380659243275938904335757337482424")),
			new BigInteger("115792089237316195423570985008687907852837564279074904382605163141518161494337"),
			1);

		P_384_SPEC = new ECParameterSpec(
			new EllipticCurve(
				new ECFieldFp(new BigInteger("39402006196394479212279040100143613805079739270465446667948293404245721771496870329047266088258938001861606973112319")),
				new BigInteger("39402006196394479212279040100143613805079739270465446667948293404245721771496870329047266088258938001861606973112316"),
				new BigInteger("27580193559959705877849011840389048093056905856361568521428707301988689241309860865136260764883745107765439761230575")),
			new ECPoint(
				new BigInteger("26247035095799689268623156744566981891852923491109213387815615900925518854738050089022388053975719786650872476732087"),
				new BigInteger("8325710961489029985546751289520108179287853048861315594709205902480503199884419224438643760392947333078086511627871")),
			new BigInteger("39402006196394479212279040100143613805079739270465446667946905279627659399113263569398956308152294913554433653942643"),
			1);

		P_521_SPEC = new ECParameterSpec(
			new EllipticCurve(
				new ECFieldFp(new BigInteger("6864797660130609714981900799081393217269435300143305409394463459185543183397656052122559640661454554977296311391480858037121987999716643812574028291115057151")),
				new BigInteger("6864797660130609714981900799081393217269435300143305409394463459185543183397656052122559640661454554977296311391480858037121987999716643812574028291115057148"),
				new BigInteger("1093849038073734274511112390766805569936207598951683748994586394495953116150735016013708737573759623248592132296706313309438452531591012912142327488478985984")),
			new ECPoint(
				new BigInteger("2661740802050217063228768716723360960729859168756973147706671368418802944996427808491545080627771902352094241225065558662157113545570916814161637315895999846"),
				new BigInteger("3757180025770020463545507224491183603594455134769762486694567779615544477440556316691234405012945539562144444537289428522585666729196580810124344277578376784")),
			new BigInteger("6864797660130609714981900799081393217269435300143305409394463459185543183397655394245057746333217197532963996371363321113864768612440380340372808892707005449"),
			1);
	}


	/**
	 * Gets the parameter specification for the specified elliptic curve.
	 *
	 * @param curve The JWK elliptic curve. May be {@code null}.
	 *
	 * @return The EC parameter spec, {@code null} if it cannot be
	 *         determined.
	 */
	public static ECParameterSpec get(final Curve curve) {

		if (Curve.P_256.equals(curve)) {
			return P_256_SPEC;
		} else if (Curve.P_256K.equals(curve)) {
			return P_256K_SPEC;
		} else if (Curve.P_384.equals(curve)) {
			return P_384_SPEC;
		} else if (Curve.P_521.equals(curve)) {
			return P_521_SPEC;
		} else {
			return null;
		}
	}


	/**
	 * Gets the JWK elliptic curve for the specified parameter
	 * specification.
	 *
	 * @param spec The EC parameter spec. May be {@code null}.
	 *
	 * @return The JWK elliptic curve, {@code null} if it cannot be
	 *         determined.
	 */
	public static Curve get(final ECParameterSpec spec) {

		if (spec == null) {
			return null;
		}

		if (spec.getCurve().getField().getFieldSize() == P_256_SPEC.getCurve().getField().getFieldSize() &&
			spec.getCurve().getA().equals(P_256_SPEC.getCurve().getA()) &&
			spec.getCurve().getB().equals(P_256_SPEC.getCurve().getB()) &&
			spec.getGenerator().getAffineX().equals(P_256_SPEC.getGenerator().getAffineX()) &&
			spec.getGenerator().getAffineY().equals(P_256_SPEC.getGenerator().getAffineY()) &&
			spec.getOrder().equals(P_256_SPEC.getOrder()) &&
			spec.getCofactor() == P_256_SPEC.getCofactor()) {

			return Curve.P_256;

		} else if (spec.getCurve().getField().getFieldSize() == P_256K_SPEC.getCurve().getField().getFieldSize() &&
			spec.getCurve().getA().equals(P_256K_SPEC.getCurve().getA()) &&
			spec.getCurve().getB().equals(P_256K_SPEC.getCurve().getB()) &&
			spec.getGenerator().getAffineX().equals(P_256K_SPEC.getGenerator().getAffineX()) &&
			spec.getGenerator().getAffineY().equals(P_256K_SPEC.getGenerator().getAffineY()) &&
			spec.getOrder().equals(P_256K_SPEC.getOrder()) &&
			spec.getCofactor() == P_256K_SPEC.getCofactor()) {

			return Curve.P_256K;

		} else if (spec.getCurve().getField().getFieldSize() == P_384_SPEC.getCurve().getField().getFieldSize() &&
			spec.getCurve().getA().equals(P_384_SPEC.getCurve().getA()) &&
			spec.getCurve().getB().equals(P_384_SPEC.getCurve().getB()) &&
			spec.getGenerator().getAffineX().equals(P_384_SPEC.getGenerator().getAffineX()) &&
			spec.getGenerator().getAffineY().equals(P_384_SPEC.getGenerator().getAffineY()) &&
			spec.getOrder().equals(P_384_SPEC.getOrder()) &&
			spec.getCofactor() == P_384_SPEC.getCofactor()) {

			return Curve.P_384;

		} else if (spec.getCurve().getField().getFieldSize() == P_521_SPEC.getCurve().getField().getFieldSize() &&
			spec.getCurve().getA().equals(P_521_SPEC.getCurve().getA()) &&
			spec.getCurve().getB().equals(P_521_SPEC.getCurve().getB()) &&
			spec.getGenerator().getAffineX().equals(P_521_SPEC.getGenerator().getAffineX()) &&
			spec.getGenerator().getAffineY().equals(P_521_SPEC.getGenerator().getAffineY()) &&
			spec.getOrder().equals(P_521_SPEC.getOrder()) &&
			spec.getCofactor() == P_521_SPEC.getCofactor()) {

			return Curve.P_521;

		} else {
			return null;
		}
	}


	/**
	 * Prevents public instantiation.
	 */
	private ECParameterTable() {

	}
}
