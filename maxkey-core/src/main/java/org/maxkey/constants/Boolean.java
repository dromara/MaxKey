package org.maxkey.constants;

/**
 * Define int for boolean 0 false 1 true.
 * 
 * @author Crystal.Sea
 *
 */
public class Boolean {

    public static final int FALSE = 0;

    public static  final int TRUE = 1;

    private int value = FALSE;

    public Boolean() {

    }

    public int getValue() {
        return value;
    }

    public boolean isValue() {
        return TRUE == value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static boolean isTrue(int value) {
        return TRUE == value;
    }

    public static boolean isFalse(int value) {
        return FALSE == value;
    }

}
