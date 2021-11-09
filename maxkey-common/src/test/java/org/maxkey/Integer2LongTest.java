package org.maxkey;

public class Integer2LongTest {

    public static void main(String[] args) {
        Integer intValue =20000;
        Long v=Integer.toUnsignedLong(intValue);
        System.out.println(v);
        System.out.println(v.getClass());
    }

}
