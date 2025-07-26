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
 

package org.dromara.maxkey.uuid;

//$Id$

//
//(C) Copyright 2005 VeriSign, Inc.  All Rights Reserved.
//
//VeriSign, Inc. shall have no responsibility, financial or
//otherwise, for any consequences arising out of the use of
//this material. The program material is provided on an "AS IS"
//BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
//express or implied. The user is responsible for determining
//any necessary third party rights or authorizations that may
//be required for the use of the materials. Users are advised 
//that they may need authorizations under certain patents from 
//Microsoft and IBM, or others. Please see notice.txt file. 
//VeriSign disclaims any obligation to notify the user of any 
//such third party rights.
//

/**
* Immutable representation of a Universally Unique Identifier (UUID),
* also known (less presumptuously) as a Globally Unique Identifier (GUID).
* These identifiers can be generated in a distributed fashion without
* central coordination, and are reasonably small (128 bits), making them
* suitable for a wide range of purposes.
*
* @see http://www.opengroup.org/onlinepubs/9629399/apdxa.htm
* @see http://www.ics.uci.edu/~ejw/authoring/uuid-guid/draft-leach-uuids-guids-01.txt
*/

import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;

public final class UUID implements Serializable
{
 /**
	 * 
	 */
	private static final long serialVersionUID = 687078561200656066L;
// Format variants.
 public static final int VARIANT_NCS = 0x00;
 public static final int VARIANT_NCS_MASK = 0x80;
 public static final int VARIANT_DCE = 0x80;
 public static final int VARIANT_DCE_MASK = 0xC0;
 public static final int VARIANT_MICROSOFT = 0xC0;
 public static final int VARIANT_MICROSOFT_MASK = 0xE0;
 public static final int VARIANT_RESERVED = 0xE0;
 public static final int VARIANT_RESERVED_MASK = 0xE0;

 // Version numbers for VARIANT_DCE.
 public static final int VERSION_TIMESTAMP = 0x1000;
 public static final int VERSION_UID = 0x2000;
 public static final int VERSION_NAME = 0x3000;
 public static final int VERSION_RANDOM = 0x4000;
 public static final int VERSION_MASK = 0xF000;

 // Data representing the UUID.
 private transient int time_low;
 private transient short time_mid, time_hi_and_version;
 private transient byte clock_seq_hi_and_reserved;
 private transient byte clock_seq_low;
 private transient byte[] node;

 // Some annotations so we don't have to compute them multiple times.
 private transient int hash_code;
 private transient String string_rep;
 private transient byte[] binary_rep;

 private static UUIDGenerator default_generator;

 /**
  * The distinguished nil UUID, one whose bits are all zeroes.
  */
 public static final UUID nil = new UUID((int)0,
                                         (short)0,
                                         (short)0,
                                         (byte)0,
                                         (byte)0,
                                         new byte[] { 0, 0, 0, 0, 0, 0 });

 /**
  * Creates a new UUID with the specified value.
  *
  * @throws NullPointerException if node is null
  * @throws IllegalArgumentException if node.length != 6
  */
 public UUID(int time_low,
             short time_mid,
             short time_hi_and_version,
             byte clock_seq_low,
             byte clock_seq_hi_and_reserved,
             byte[] node)
     throws NullPointerException, IllegalArgumentException
 {
     if(node == null) {
    	 throw new NullPointerException();
     }
     if(node.length != 6) {
    	 throw new IllegalArgumentException();
     }

     this.time_low = time_low;
     this.time_mid = time_mid;
     this.time_hi_and_version = time_hi_and_version;
     this.clock_seq_low = clock_seq_low;
     this.clock_seq_hi_and_reserved = clock_seq_hi_and_reserved;
     this.node = new byte[6];
     System.arraycopy(node, 0, this.node, 0, 6);
 }

 /**
  * Creates a UUID from its string representation.
  *
  * @throws NullPointerException if s is null
  * @throws IllegalArgumentException if s.length() != 36 or any expected hyphens are missing
  * @throws NumberFormatException if an expected hex digit isn't one
  */
 public UUID(String s)
     throws NullPointerException,
            IllegalArgumentException,
            NumberFormatException
 {
     if(s == null) {
    	 throw new NullPointerException();
     }
     if(s.length() != 36) {
    	 throw new IllegalArgumentException();
     }
     time_low = parseHex(s.substring(0, 8));
     if(s.charAt(8) != '-') {
    	 throw new IllegalArgumentException();
     }
     time_mid = (short) parseHex(s.substring(9, 13));
     if(s.charAt(13) != '-') {
    	 throw new IllegalArgumentException();
     }
     time_hi_and_version = (short) parseHex(s.substring(14, 18));
     if(s.charAt(18) != '-') {
    	 throw new IllegalArgumentException();
     }
     clock_seq_hi_and_reserved = (byte) parseHex(s.substring(19, 21));
     clock_seq_low = (byte) parseHex(s.substring(21, 23));
     if(s.charAt(23) != '-') {
    	 throw new IllegalArgumentException();
     }
     node = new byte[6];
     for(int i = 0; i < 6; i++) {
         node[i] = (byte) parseHex(s.substring(2 * i + 24, 2 * i + 26));
     }
 }

 /**
  * Reads the 16 bytes of a UUID from the specified
  * DataInput source.
  *
  * @throws NullPointerException if in == null
  * @throws IOException if the read fails
  */
 public UUID(DataInput in) throws IOException
 {
     if(in == null) {
    	 throw new NullPointerException();
     }
     readData(in);
 }

 /**
  * Constructs a UUID from 16 data bytes.
  *
  * @throws NullPointerException if data == null
  * @throws IllegalArgumentException if data.length != 16
  */
 public UUID(byte[] data)
 {
     if(data == null) {
    	 throw new NullPointerException();
     }
     if(data.length != 16) {
    	 throw new IllegalArgumentException();
     }
     try {
         readData(new DataInputStream(new ByteArrayInputStream(data)));
     } catch(IOException ex) {
         throw new IllegalArgumentException();
     }
 }

 private void readData(DataInput in) throws IOException
 {
     time_low = in.readInt();
     time_mid = in.readShort();
     time_hi_and_version = in.readShort();
     clock_seq_hi_and_reserved = in.readByte();
     clock_seq_low = in.readByte();
     node = new byte[6];
     in.readFully(node);
 }

 private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
 {
     readData(in);
 }

 /**
  * Writes the 16 data bytes of this UUID to the specified
  * DataOutput interface.
  *
  * @throws IOException if the DataOutput interface does
  */
 public void writeData(DataOutput out) throws IOException
 {
     out.writeInt(time_low);
     out.writeShort(time_mid);
     out.writeShort(time_hi_and_version);
     out.writeByte(clock_seq_hi_and_reserved);
     out.writeByte(clock_seq_low);
     out.write(node);
 }

 private void writeObject(ObjectOutputStream out) throws IOException
 {
     writeData(out);
 }

 /*
  * Returns true if two UUIDs are equal by value.
  */
 @Override
 public boolean equals(Object obj){
     if(obj == null || !(obj instanceof UUID)) {
         return false;
     }
     UUID other = (UUID) obj;

     if(this == other) {
    	 return true;
     }

     if(hash_code != 0 &&
        other.hash_code != 0 &&
        hash_code != other.hash_code) {
         return false;
     }

     return
         time_low == other.time_low &&
         time_mid == other.time_mid &&
         time_hi_and_version == other.time_hi_and_version &&
         clock_seq_low == other.clock_seq_low &&
         clock_seq_hi_and_reserved == other.clock_seq_hi_and_reserved &&
         Arrays.equals(node, other.node);
 }

 /**
  * Returns a hash code for this UUID.
  */
 @Override
 public int hashCode()
 {
     if(hash_code == 0) {
         synchronized(this) {
             if(hash_code == 0) {
                 hash_code = toString().hashCode();
                 if(hash_code == 0) {
                     hash_code = -1;
                 }
             }
         }
     }
     return hash_code;
 }

 /**
  * Utility method for toString().
  */
 private static void appendHex(StringBuffer sb, long num, int digits)
 {
     if(digits > 0 && digits < 16) {
         num = num & ((1L << (digits * 4)) - 1);
     }
     String str = Long.toHexString(num);
     int len = str.length();
     while(len < digits) {
         sb.append('0');
         len++;
     }
     sb.append(str);
 }

 /**
  * Utility method for UUID(String) constructor.
  */
 private static int parseHex(String s) throws NumberFormatException
 {
     if(s.charAt(0) == '-') {
         throw new NumberFormatException();
     }
     return Integer.parseInt(s, 16);
 }

 /**
  * Returns the string representation of this UUID.
  */
 @Override
 public String toString()
 {
     if(string_rep == null) {
         synchronized(this) {
             if(string_rep == null) {
                 StringBuffer sb = new StringBuffer();
                 appendHex(sb, time_low, 8);
                 sb.append('-');
                 appendHex(sb, time_mid, 4);
                 sb.append('-');
                 appendHex(sb, time_hi_and_version, 4);
                 sb.append('-');
                 appendHex(sb, clock_seq_hi_and_reserved, 2);
                 appendHex(sb, clock_seq_low, 2);
                 sb.append('-');
                 for(int i = 0; i < 6; i++) {
                     appendHex(sb, node[i], 2);
                 }
                 string_rep = sb.toString();
             }
         }
     }
     return string_rep;
 }

 /**
  * Returns an array of 16 bytes containing the binary representation
  * of this UUID.
  */
 public byte[] toByteArray()
 {
     if(binary_rep == null) {
         synchronized(this) {
             if(binary_rep == null) {
                 try {
                     ByteArrayOutputStream baos = new ByteArrayOutputStream(16);
                     writeData(new DataOutputStream(baos));
                     binary_rep = baos.toByteArray();
                 } catch(IOException ex) {
                     throw new RuntimeException();
                 }
             }
         }
     }
     return (byte[]) binary_rep.clone();
 }

 /**
  * Creates a new, unique UUID.
  */
 public static UUID generate()
 {
     if(default_generator == null) {
         default_generator = new TimestampUUIDGenerator(UUIDRandomness.randomClockSequence(), NodeIDGetter.getNodeID());
     }
     return default_generator.nextUUID();
 }

 /**
  * Creates a UUID based on a hash of a namespace designator
  * UUID and a name.
  */
 public static UUID fromName(UUID namespace, String name)
 {
     try {
         MessageDigest md5 = MessageDigest.getInstance("MD5");
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         DataOutputStream dos = new DataOutputStream(baos);

         namespace.writeData(dos);
         dos.flush();
         md5.update(baos.toByteArray());
         baos.reset();
         dos.writeUTF(name);
         dos.flush();
         byte[] data = baos.toByteArray();
         md5.update(data, 2, data.length - 2);
         data = md5.digest(); // this should be 16 bytes
         UUID uuid = new UUID(data);
         uuid.clock_seq_hi_and_reserved = (byte) ((uuid.clock_seq_hi_and_reserved & ~VARIANT_DCE_MASK) | VARIANT_DCE);
         uuid.time_hi_and_version = (short) ((uuid.time_hi_and_version & ~VERSION_MASK) | VERSION_NAME);
         return uuid;
     } catch(Exception ex) {
         throw new RuntimeException();
     }
 }
}
