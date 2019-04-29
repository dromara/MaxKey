package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import junit.framework.TestCase;

public class JSONArrayTest extends TestCase {

        public void testJSONArray() {
                final JSONArray jsonArray = new JSONArray();
                
                assertEquals("[]", jsonArray.toJSONString());
        }

        public void testJSONArrayCollection() {
                final ArrayList testList = new ArrayList();
                testList.add("First item");
                testList.add("Second item");
                
                final JSONArray jsonArray = new JSONArray(testList);
                
                assertEquals("[\"First item\",\"Second item\"]", jsonArray.toJSONString());
        }

        public void testWriteJSONStringCollectionWriter() throws IOException, ParseException {
                final HashSet testSet = new HashSet();
                testSet.add("First item");
                testSet.add("Second item");
                
                final JSONArray jsonArray = new JSONArray(testSet);
                final StringWriter writer = new StringWriter();
                
                jsonArray.writeJSONString(writer);
                
                final JSONParser parser = new JSONParser();
                final JSONArray parsedArray = (JSONArray)parser.parse(writer.toString());
                
                assertTrue(parsedArray.containsAll(jsonArray));
                assertTrue(jsonArray.containsAll(parsedArray));
                assertEquals(2, jsonArray.size());
        }

        public void testToJSONStringCollection() throws ParseException {
                final HashSet testSet = new HashSet();
                testSet.add("First item");
                testSet.add("Second item");
                
                final JSONArray jsonArray = new JSONArray(testSet);
                
                final JSONParser parser = new JSONParser();
                final JSONArray parsedArray = (JSONArray)parser.parse(jsonArray.toJSONString());
                
                assertTrue(parsedArray.containsAll(jsonArray));
                assertTrue(jsonArray.containsAll(parsedArray));
                assertEquals(2, jsonArray.size());
        }

        public void testByteArrayToString() throws IOException {
                assertEquals("null", JSONArray.toJSONString((byte[])null));
                assertEquals("[]", JSONArray.toJSONString(new byte[0]));
                assertEquals("[12]", JSONArray.toJSONString(new byte[] { 12 }));
                assertEquals("[-7,22,86,-99]", JSONArray.toJSONString(new byte[] { -7, 22, 86, -99 }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONArray.writeJSONString((byte[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new byte[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new byte[] { 12 }, writer);
                assertEquals("[12]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new byte[] { -7, 22, 86, -99 }, writer);
                assertEquals("[-7,22,86,-99]", writer.toString());
        }
        
        public void testShortArrayToString() throws IOException {
                assertEquals("null", JSONArray.toJSONString((short[])null));
                assertEquals("[]", JSONArray.toJSONString(new short[0]));
                assertEquals("[12]", JSONArray.toJSONString(new short[] { 12 }));
                assertEquals("[-7,22,86,-99]", JSONArray.toJSONString(new short[] { -7, 22, 86, -99 }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONArray.writeJSONString((short[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new short[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new short[] { 12 }, writer);
                assertEquals("[12]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new short[] { -7, 22, 86, -99 }, writer);
                assertEquals("[-7,22,86,-99]", writer.toString());
        }
        
        public void testIntArrayToString() throws IOException {
                assertEquals("null", JSONArray.toJSONString((int[])null));
                assertEquals("[]", JSONArray.toJSONString(new int[0]));
                assertEquals("[12]", JSONArray.toJSONString(new int[] { 12 }));
                assertEquals("[-7,22,86,-99]", JSONArray.toJSONString(new int[] { -7, 22, 86, -99 }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONArray.writeJSONString((int[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new int[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new int[] { 12 }, writer);
                assertEquals("[12]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new int[] { -7, 22, 86, -99 }, writer);
                assertEquals("[-7,22,86,-99]", writer.toString());
        }
        
        public void testLongArrayToString() throws IOException {
                assertEquals("null", JSONArray.toJSONString((long[])null));
                assertEquals("[]", JSONArray.toJSONString(new long[0]));
                assertEquals("[12]", JSONArray.toJSONString(new long[] { 12 }));
                assertEquals("[-7,22,9223372036854775807,-99]", JSONArray.toJSONString(new long[] { -7, 22, 9223372036854775807L, -99 }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONArray.writeJSONString((long[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new long[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new long[] { 12 }, writer);
                assertEquals("[12]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new long[] { -7, 22, 86, -99 }, writer);
                assertEquals("[-7,22,86,-99]", writer.toString());
        }
        
        public void testFloatArrayToString() throws IOException {
                assertEquals("null", JSONArray.toJSONString((float[])null));
                assertEquals("[]", JSONArray.toJSONString(new float[0]));
                assertEquals("[12.8]", JSONArray.toJSONString(new float[] { 12.8f }));
                assertEquals("[-7.1,22.234,86.7,-99.02]", JSONArray.toJSONString(new float[] { -7.1f, 22.234f, 86.7f, -99.02f }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONArray.writeJSONString((float[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new float[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new float[] { 12.8f }, writer);
                assertEquals("[12.8]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new float[] { -7.1f, 22.234f, 86.7f, -99.02f }, writer);
                assertEquals("[-7.1,22.234,86.7,-99.02]", writer.toString());
        }
        
        public void testDoubleArrayToString() throws IOException {
                assertEquals("null", JSONArray.toJSONString((double[])null));
                assertEquals("[]", JSONArray.toJSONString(new double[0]));
                assertEquals("[12.8]", JSONArray.toJSONString(new double[] { 12.8 }));
                assertEquals("[-7.1,22.234,86.7,-99.02]", JSONArray.toJSONString(new double[] { -7.1, 22.234, 86.7, -99.02 }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONArray.writeJSONString((double[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new double[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new double[] { 12.8 }, writer);
                assertEquals("[12.8]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new double[] { -7.1, 22.234, 86.7, -99.02 }, writer);
                assertEquals("[-7.1,22.234,86.7,-99.02]", writer.toString());
        }
        
        public void testBooleanArrayToString() throws IOException {
                assertEquals("null", JSONArray.toJSONString((boolean[])null));
                assertEquals("[]", JSONArray.toJSONString(new boolean[0]));
                assertEquals("[true]", JSONArray.toJSONString(new boolean[] { true }));
                assertEquals("[true,false,true]", JSONArray.toJSONString(new boolean[] { true, false, true }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONArray.writeJSONString((boolean[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new boolean[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new boolean[] { true }, writer);
                assertEquals("[true]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new boolean[] { true, false, true }, writer);
                assertEquals("[true,false,true]", writer.toString());
        }
        
        public void testCharArrayToString() throws IOException {
                assertEquals("null", JSONArray.toJSONString((char[])null));
                assertEquals("[]", JSONArray.toJSONString(new char[0]));
                assertEquals("[\"a\"]", JSONArray.toJSONString(new char[] { 'a' }));
                assertEquals("[\"a\",\"b\",\"c\"]", JSONArray.toJSONString(new char[] { 'a', 'b', 'c' }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONArray.writeJSONString((char[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new char[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new char[] { 'a' }, writer);
                assertEquals("[\"a\"]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new char[] { 'a', 'b', 'c' }, writer);
                assertEquals("[\"a\",\"b\",\"c\"]", writer.toString());
        }
        
        public void testObjectArrayToString() throws IOException {
                assertEquals("null", JSONArray.toJSONString((Object[])null));
                assertEquals("[]", JSONArray.toJSONString(new Object[0]));
                assertEquals("[\"Hello\"]", JSONArray.toJSONString(new Object[] { "Hello" }));
                assertEquals("[\"Hello\",12,[1,2,3]]", JSONArray.toJSONString(new Object[] { "Hello", new Integer(12), new int[] { 1, 2, 3 } }));
                
                StringWriter writer;
                
                writer = new StringWriter();
                JSONArray.writeJSONString((Object[])null, writer);
                assertEquals("null", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new Object[0], writer);
                assertEquals("[]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new Object[] { "Hello" }, writer);
                assertEquals("[\"Hello\"]", writer.toString());
                
                writer = new StringWriter();
                JSONArray.writeJSONString(new Object[] { "Hello", new Integer(12), new int[] { 1, 2, 3} }, writer);
                assertEquals("[\"Hello\",12,[1,2,3]]", writer.toString());
        }
}