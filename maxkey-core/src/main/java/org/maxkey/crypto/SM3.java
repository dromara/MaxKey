package org.maxkey.crypto;

import java.util.Arrays;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * SM3.
 * @author Crystal.Sea
 *
 */
public class SM3 {
    /**
             * 计算SM3摘要值
     *
     * @param simple 原文
     * @return 摘要值，对于SM3算法来说是32字节
     */
    public static byte[] encode(byte[] simple) {
        SM3Digest digest = new SM3Digest();
        digest.update(simple, 0, simple.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
             *   验证摘要
     *
     * @param simple 原文
     * @param cipher 摘要值
     * @return 返回true标识验证成功，false标识验证失败
     */
    public static boolean verify(byte[] simple, byte[] cipher) {
        byte[] newHash = encode(simple);
        if (Arrays.equals(newHash, cipher)) {
            return true;
        } else {
            return false;
        }
    }

    /**
             *   计算SM3 Mac值
     *
     * @param key     key值，可以是任意长度的字节数组
     * @param srcData 原文
     * @return Mac值，对于HMac-SM3来说是32字节
     */
    public static byte[] hmac(byte[] key, byte[] simple) {
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(simple, 0, simple.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }
}
