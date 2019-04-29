package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;

import junit.framework.TestCase;

public class JSONValueTest extends TestCase {
        public void testByteArrayToString() throws IOException {
                assertEquals("null", JSONValue.toJSONString((byte[])null));
                assertEquals("[]", JSONValue.toJSONString(new byte[0]));
                assertEquals("[12]", JSONValue.toJSONString(new byte[] { 12 }));
                assertEquals("[-7,22,86,-99]", JSONValue.toJSONString(new byte[] { -7, 22, 86, -99 }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONValue.writeJSONString((byte[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new byte[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new byte[] { 12 }, writer);
                assertEquals("[12]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new byte[] { -7, 22, 86, -99 }, writer);
                assertEquals("[-7,22,86,-99]", writer.toString());
        }
        
        public void testShortArrayToString() throws IOException {
                assertEquals("null", JSONValue.toJSONString((short[])null));
                assertEquals("[]", JSONValue.toJSONString(new short[0]));
                assertEquals("[12]", JSONValue.toJSONString(new short[] { 12 }));
                assertEquals("[-7,22,86,-99]", JSONValue.toJSONString(new short[] { -7, 22, 86, -99 }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONValue.writeJSONString((short[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new short[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new short[] { 12 }, writer);
                assertEquals("[12]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new short[] { -7, 22, 86, -99 }, writer);
                assertEquals("[-7,22,86,-99]", writer.toString());
        }
        
        public void testIntArrayToString() throws IOException {
                assertEquals("null", JSONValue.toJSONString((int[])null));
                assertEquals("[]", JSONValue.toJSONString(new int[0]));
                assertEquals("[12]", JSONValue.toJSONString(new int[] { 12 }));
                assertEquals("[-7,22,86,-99]", JSONValue.toJSONString(new int[] { -7, 22, 86, -99 }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONValue.writeJSONString((int[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new int[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new int[] { 12 }, writer);
                assertEquals("[12]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new int[] { -7, 22, 86, -99 }, writer);
                assertEquals("[-7,22,86,-99]", writer.toString());
        }
        
        public void testLongArrayToString() throws IOException {
                assertEquals("null", JSONValue.toJSONString((long[])null));
                assertEquals("[]", JSONValue.toJSONString(new long[0]));
                assertEquals("[12]", JSONValue.toJSONString(new long[] { 12 }));
                assertEquals("[-7,22,9223372036854775807,-99]", JSONValue.toJSONString(new long[] { -7, 22, 9223372036854775807L, -99 }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONValue.writeJSONString((long[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new long[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new long[] { 12 }, writer);
                assertEquals("[12]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new long[] { -7, 22, 86, -99 }, writer);
                assertEquals("[-7,22,86,-99]", writer.toString());
        }
        
        public void testFloatArrayToString() throws IOException {
                assertEquals("null", JSONValue.toJSONString((float[])null));
                assertEquals("[]", JSONValue.toJSONString(new float[0]));
                assertEquals("[12.8]", JSONValue.toJSONString(new float[] { 12.8f }));
                assertEquals("[-7.1,22.234,86.7,-99.02]", JSONValue.toJSONString(new float[] { -7.1f, 22.234f, 86.7f, -99.02f }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONValue.writeJSONString((float[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new float[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new float[] { 12.8f }, writer);
                assertEquals("[12.8]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new float[] { -7.1f, 22.234f, 86.7f, -99.02f }, writer);
                assertEquals("[-7.1,22.234,86.7,-99.02]", writer.toString());
        }
        
        public void testDoubleArrayToString() throws IOException {
                assertEquals("null", JSONValue.toJSONString((double[])null));
                assertEquals("[]", JSONValue.toJSONString(new double[0]));
                assertEquals("[12.8]", JSONValue.toJSONString(new double[] { 12.8 }));
                assertEquals("[-7.1,22.234,86.7,-99.02]", JSONValue.toJSONString(new double[] { -7.1, 22.234, 86.7, -99.02 }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONValue.writeJSONString((double[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new double[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new double[] { 12.8 }, writer);
                assertEquals("[12.8]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new double[] { -7.1, 22.234, 86.7, -99.02 }, writer);
                assertEquals("[-7.1,22.234,86.7,-99.02]", writer.toString());
        }
        
        public void testBooleanArrayToString() throws IOException {
                assertEquals("null", JSONValue.toJSONString((boolean[])null));
                assertEquals("[]", JSONValue.toJSONString(new boolean[0]));
                assertEquals("[true]", JSONValue.toJSONString(new boolean[] { true }));
                assertEquals("[true,false,true]", JSONValue.toJSONString(new boolean[] { true, false, true }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONValue.writeJSONString((boolean[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new boolean[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new boolean[] { true }, writer);
                assertEquals("[true]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new boolean[] { true, false, true }, writer);
                assertEquals("[true,false,true]", writer.toString());
        }
        
        public void testCharArrayToString() throws IOException {
                assertEquals("null", JSONValue.toJSONString((char[])null));
                assertEquals("[]", JSONValue.toJSONString(new char[0]));
                assertEquals("[\"a\"]", JSONValue.toJSONString(new char[] { 'a' }));
                assertEquals("[\"a\",\"b\",\"c\"]", JSONValue.toJSONString(new char[] { 'a', 'b', 'c' }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONValue.writeJSONString((char[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new char[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new char[] { 'a' }, writer);
                assertEquals("[\"a\"]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new char[] { 'a', 'b', 'c' }, writer);
                assertEquals("[\"a\",\"b\",\"c\"]", writer.toString());
        }
        
        public void testObjectArrayToString() throws IOException {
                assertEquals("null", JSONValue.toJSONString((Object[])null));
                assertEquals("[]", JSONValue.toJSONString(new Object[0]));
                assertEquals("[\"Hello\"]", JSONValue.toJSONString(new Object[] { "Hello" }));
                assertEquals("[\"Hello\",12,[1,2,3]]", JSONValue.toJSONString(new Object[] { "Hello", new Integer(12), new int[] { 1, 2, 3 } }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONValue.writeJSONString((Object[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new Object[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new Object[] { "Hello" }, writer);
                assertEquals("[\"Hello\"]", writer.toString());
                
                writer = new StringWriter();
                JSONValue.writeJSONString(new Object[] { "Hello", new Integer(12), new int[] { 1, 2, 3} }, writer);
                assertEquals("[\"Hello\",12,[1,2,3]]", writer.toString());
        }
        
        public void testArraysOfArrays() throws IOException {
                
                StringWriter writer;
                
                final int[][][] nestedIntArray = new int[][][]{{{1}, {5}}, {{2}, {6}}};
                final String expectedNestedIntString = "[[[1],[5]],[[2],[6]]]";
                
                assertEquals(expectedNestedIntString, JSONValue.toJSONString(nestedIntArray));
                
                writer = new StringWriter();
                JSONValue.writeJSONString(nestedIntArray, writer);
                assertEquals(expectedNestedIntString, writer.toString());

                final String[][] nestedStringArray = new String[][]{{"a", "b"}, {"c", "d"}};
                final String expectedNestedStringString = "[[\"a\",\"b\"],[\"c\",\"d\"]]";
                
                assertEquals(expectedNestedStringString, JSONValue.toJSONString(nestedStringArray));
                
                writer = new StringWriter();
                JSONValue.writeJSONString(nestedStringArray, writer);
                assertEquals(expectedNestedStringString, writer.toString());
        }
}