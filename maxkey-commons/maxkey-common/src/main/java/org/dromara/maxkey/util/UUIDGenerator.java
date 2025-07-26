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
 

package org.dromara.maxkey.util;

import java.lang.management.ManagementFactory;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * See README.md for more information
 */
public final class UUIDGenerator {
    public static final int PID                 = processId();
    public static final byte[] MAC              = macAddress();

    private static final int MAX_PID            = 65536;
    private static final int INCREMENT          = 198491317;
    private static final char VERSION           = 'b';
    private static final int VERSION_DEC        = mapToByte(VERSION, '0');
    private static final AtomicInteger COUNTER  = new AtomicInteger(new Random(System.nanoTime()).nextInt());
    private static final char[] HEX             =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static boolean sequential           = false;
    private final byte[] content;

    /**
     * Constructor that generates a new UUID using the current process id, MAC address, and timestamp
     */
    public UUIDGenerator() {
        long time = new Date().getTime();
        content = new byte[16];

        if (!sequential) {
            // atomically add a large prime number to the count and get the previous value
            int count = COUNTER.addAndGet(INCREMENT);

            // switch the order of the count in 4 bit segments and place into content
            content[0] = (byte) (((count & 0xF) << 4) | ((count & 0xF0) >> 4));
            content[1] = (byte) (((count & 0xF00) >> 4) | ((count & 0xF000) >> 12));
            content[2] = (byte) (((count & 0xF0000) >> 12) | ((count & 0xF00000) >> 20));
            content[3] = (byte) (((count & 0xF000000) >> 20) | ((count & 0xF0000000) >> 28));
        }
        else {
            int count = COUNTER.addAndGet(1);

            // get the count in order and place into content
            content[0] = (byte) (count >> 24);
            content[1] = (byte) (count >> 16);
            content[2] = (byte) (count >> 8);
            content[3] = (byte) (count);
        }

        // copy pid to content
        content[4]  = (byte) (PID >> 8);
        content[5]  = (byte) (PID);

        // place UUID version (hex 'b') in first four bits and piece of mac address in
        // the second four bits
        content[6]  = (byte) (VERSION_DEC | (0xF & MAC[2]));

        // copy rest of mac address into content
        content[7]  = MAC[3];
        content[8]  = MAC[4];
        content[9]  = MAC[5];

        // copy timestamp into content
        content[10] = (byte) (time >> 40);
        content[11] = (byte) (time >> 32);
        content[12] = (byte) (time >> 24);
        content[13] = (byte) (time >> 16);
        content[14] = (byte) (time >> 8);
        content[15] = (byte) (time);
    }

    /**
     * Constructor that takes a byte array as this UUID's content
     * @param bytes UUID content
     */
    public UUIDGenerator(byte[] bytes) {
        if (bytes.length != 16) {
            throw new RuntimeException("Attempted to parse malformed UUID: " + Arrays.toString(bytes));
        }
        content = Arrays.copyOf(bytes, 16);
    }

    public UUIDGenerator(String id) {
        id = id.trim();

        if (id.length() != 36) {
            throw new RuntimeException("Attempted to parse malformed UUID: " + id);
        }
        content = new byte[16];
        char[] chars = id.toCharArray();

        content[0]  = mapToByte(chars[0],  chars[1]);
        content[1]  = mapToByte(chars[2],  chars[3]);
        content[2]  = mapToByte(chars[4],  chars[5]);
        content[3]  = mapToByte(chars[6],  chars[7]);
        content[4]  = mapToByte(chars[9],  chars[10]);
        content[5]  = mapToByte(chars[11], chars[12]);
        content[6]  = mapToByte(chars[14], chars[15]);
        content[7]  = mapToByte(chars[16], chars[17]);
        content[8]  = mapToByte(chars[19], chars[20]);
        content[9]  = mapToByte(chars[21], chars[22]);
        content[10] = mapToByte(chars[24], chars[25]);
        content[11] = mapToByte(chars[26], chars[27]);
        content[12] = mapToByte(chars[28], chars[29]);
        content[13] = mapToByte(chars[30], chars[31]);
        content[14] = mapToByte(chars[32], chars[33]);
        content[15] = mapToByte(chars[34], chars[35]);
    }

    /**
     * Toggle uuid generator into sequential mode, so the random segment is in order and increases by one
     */
    public static void useSequentialIds() {
        if (!sequential) {
            // get string that changes every 10 minutes
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
            df.setTimeZone(tz);
            String date = df.format(new Date()).substring(0, 11);

            // run an md5 hash of the string, no reason this needs to be secure
            byte[] digest;
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                digest = md.digest(date.getBytes("UTF-8"));
            }
            catch (Exception e) {
                throw new RuntimeException("Could not create hash of date for the sequential counter", e);
            }

            // create integer from first 4 bytes of md5 hash
            int x;
            x  = ((int)digest[0] & 0xFF);
            x |= ((int)digest[1] & 0xFF) << 8;
            x |= ((int)digest[2] & 0xFF) << 16;
            x |= ((int)digest[3] & 0xFF) << 24;

            COUNTER.set(x);
        }
        sequential = true;
    }

    /**
     * Toggle uuid generator into variable mode, so the random segment is in reverse order and
     * increases by a large increment. This is the default mode.
     */
    public static void useVariableIds() {
        sequential = false;
    }

    /**
     * map hex character to 4 bit number
     * @param x hex character
     * @return four bit number representing offset from '0'
     */
    private static int intValue(char x) {
        if (x >= '0' && x <= '9') {
            return x - '0';
        }
        if (x >= 'a' && x <= 'f') {
            return x - 'a' + 10;
        }
        if (x >= 'A' && x <= 'F') {
            return x - 'A' + 10;
        }
        throw new RuntimeException("Error parsing UUID at character: " + x);
    }

    /**
     * map two hex characters to 4 bit numbers and combine them
     * @param a hex character 1
     * @param b hex character 2
     * @return single byte value of combined characters
     */
    private static byte mapToByte(char a, char b) {
        int ai = intValue(a);
        int bi = intValue(b);
        return (byte) ((ai << 4) | bi);
    }

    /**
     * copy the content of this UUID, so that it can't be changed, and return it
     * @return raw byte array of UUID
     */
    public byte[] getBytes() {
        return Arrays.copyOf(content, 16);
    }

    @Override
    public String toString() {
        char[] id = new char[36];

        // split each byte into 4 bit numbers and map to hex characters
        id[0]  = HEX[(content[0]  & 0xF0) >> 4];
        id[1]  = HEX[(content[0]  & 0x0F)];
        id[2]  = HEX[(content[1]  & 0xF0) >> 4];
        id[3]  = HEX[(content[1]  & 0x0F)];
        id[4]  = HEX[(content[2]  & 0xF0) >> 4];
        id[5]  = HEX[(content[2]  & 0x0F)];
        id[6]  = HEX[(content[3]  & 0xF0) >> 4];
        id[7]  = HEX[(content[3]  & 0x0F)];
        id[8]  = '-';
        id[9]  = HEX[(content[4]  & 0xF0) >> 4];
        id[10] = HEX[(content[4]  & 0x0F)];
        id[11] = HEX[(content[5]  & 0xF0) >> 4];
        id[12] = HEX[(content[5]  & 0x0F)];
        id[13] = '-';
        id[14] = HEX[(content[6]  & 0xF0) >> 4];
        id[15] = HEX[(content[6]  & 0x0F)];
        id[16] = HEX[(content[7]  & 0xF0) >> 4];
        id[17] = HEX[(content[7]  & 0x0F)];
        id[18] = '-';
        id[19] = HEX[(content[8]  & 0xF0) >> 4];
        id[20] = HEX[(content[8]  & 0x0F)];
        id[21] = HEX[(content[9]  & 0xF0) >> 4];
        id[22] = HEX[(content[9]  & 0x0F)];
        id[23] = '-';
        id[24] = HEX[(content[10] & 0xF0) >> 4];
        id[25] = HEX[(content[10] & 0x0F)];
        id[26] = HEX[(content[11] & 0xF0) >> 4];
        id[27] = HEX[(content[11] & 0x0F)];
        id[28] = HEX[(content[12] & 0xF0) >> 4];
        id[29] = HEX[(content[12] & 0x0F)];
        id[30] = HEX[(content[13] & 0xF0) >> 4];
        id[31] = HEX[(content[13] & 0x0F)];
        id[32] = HEX[(content[14] & 0xF0) >> 4];
        id[33] = HEX[(content[14] & 0x0F)];
        id[34] = HEX[(content[15] & 0xF0) >> 4];
        id[35] = HEX[(content[15] & 0x0F)];

        return new String(id);
    }

    /**
     * extract version field as a hex char from raw UUID bytes
     * @return version char
     */
    public char getVersion() {
        return HEX[(content[6] & 0xF0) >> 4];
    }

    /**
     * extract process id from raw UUID bytes and return as int
     * @return id of process that generated the UUID, or -1 for unrecognized format
     */
    public int getProcessId() {
        if (getVersion() != VERSION) {
            return -1;
        }
        return ((content[4] & 0xFF) << 8) | (content[5] & 0xFF);
    }

    /**
     * extract timestamp from raw UUID bytes and return as int
     * @return millisecond UTC timestamp from generation of the UUID, or null for unrecognized format
     */
    public Date getTimestamp() {
        if (getVersion() != VERSION) {
            return null;
        }
        long time;
        time  = ((long)content[10] & 0xFF) << 40;
        time |= ((long)content[11] & 0xFF) << 32;
        time |= ((long)content[12] & 0xFF) << 24;
        time |= ((long)content[13] & 0xFF) << 16;
        time |= ((long)content[14] & 0xFF) << 8;
        time |= ((long)content[15] & 0xFF);
        return new Date(time);
    }

    /**
     * extract MAC address fragment from raw UUID bytes, setting missing values to 0,
     * thus the first 2 and a half bytes will be 0, followed by 3 and a half bytes
     * of the active MAC address when the UUID was generated
     * @return byte array of UUID fragment, or null for unrecognized format
     */
    public byte[] getMacFragment() {
        if (getVersion() != 'b') {
            return null;
        }
        byte[] x = new byte[6];

        x[0] = 0;
        x[1] = 0;
        x[2] = (byte) (content[6] & 0xF);
        x[3] = content[7];
        x[4] = content[8];
        x[5] = content[9];

        return x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }

        UUIDGenerator that = (UUIDGenerator) o;

        if (this.content.length != that.content.length) {
            return false;
        }

        for (int i = 0; i < this.content.length; i++) {
            if (this.content[i] != that.content[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }

    private static byte[] macAddress() {
        try {
        	//old
            //byte[] mac = NetworkInterface.getNetworkInterfaces().nextElement().getHardwareAddress();
            byte[] mac = EthernetAddress.fromInterface().toByteArray();
            // if the machine is not connected to a network it has no active MAC address
            if (mac == null) {
                mac = new byte[] {0, 0, 0, 0, 0, 0};
            }
            return mac;
        } catch (Exception e) {
            throw new RuntimeException("Could not get MAC address");
        }
    }

    // pulled from http://stackoverflow.com/questions/35842/how-can-a-java-program-get-its-own-process-id
    private static int processId() {
        // Note: may fail in some JVM implementations
        // something like '<pid>@<hostname>', at least in SUN / Oracle JVMs
        final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        final int index = jvmName.indexOf('@');

        if (index < 1) {
            throw new RuntimeException("Could not get PID");
        }
        try {
            return Integer.parseInt(jvmName.substring(0, index)) % MAX_PID;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Could not get PID");
        }
    }
    

    public static void version(org.dromara.maxkey.util.UUIDGenerator generated){
    	System.out.println("UUID            : " + generated.toString());
        System.out.println("raw bytes       : " + Arrays.toString(generated.getBytes()));
        System.out.println("process id      : " + generated.getProcessId());
        System.out.println("MAC fragment    : " + Arrays.toString(generated.getMacFragment()));
        System.out.println("timestamp       : " + generated.getTimestamp());
        System.out.println("UUID version    : " + generated.getVersion());

        UUIDGenerator copy = new UUIDGenerator(generated.toString());
        System.out.println("copied          : " + generated.equals(copy));
    }
}
