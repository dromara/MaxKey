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
* A more solid timestamp-based UUID generator, this one keeps its
* timestamps within a millisecond or so of the current time, and is
* thread-safe.
*/
public class TimestampUUIDGenerator
 extends UnsynchronizedTimestampUUIDGenerator
 implements UUIDGenerator
{
 /**
  * Creates a UUIDGenerator with the specified clock sequence number
  * and node ID.
  *
  * @throws NullPointerException if node == null
  * @throws IllegalArgumentException if clock_sequence is out of 
  *  range or node.length != 6
  */
 public TimestampUUIDGenerator(int clock_sequence,
				  byte[] node)
 {
	super(clock_sequence, node);
 }

 /**
  * Generates a new UUID.
  *
  * @throws IllegalStateException if adjustmentOverflow() throws it
  */
 @Override
 public UUID nextUUID()
 {
	synchronized(this) {
	    checkSystemTime();
	    return super.nextUUID();
	}
 }

}
