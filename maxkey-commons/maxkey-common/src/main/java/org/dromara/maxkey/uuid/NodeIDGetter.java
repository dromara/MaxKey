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

public class NodeIDGetter
{
 private static Object lock = new Object();
 private static byte[] nodeID = null;

 private NodeIDGetter() { throw new Error(); }

 private static native void getNodeID(byte[] nodeID);

 public static byte[] getNodeID()
 {
	if(nodeID == null) {
	    synchronized(lock) {
		if(nodeID == null) {
		    try {
			byte[] data = new UUID("00000000-0000-0000-0000-" 
	    + System.getProperty("org.apache.tsik.uuid.nodeid")).toByteArray();
			nodeID = new byte[6];
			System.arraycopy(data, 10, nodeID, 0, 6);
			return nodeID;
		    } catch(Exception ex) {
			// phooey.
		    }

		    try {
			System.loadLibrary("NodeIDGetter");
			nodeID = new byte[6];
			getNodeID(nodeID);
		    } catch(LinkageError ex) {
			// phooey again.
		    }

		    nodeID = UUIDRandomness.randomNodeID();
		}
	    }
	}
	return nodeID;
 }
}
