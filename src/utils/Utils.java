/*
 * This file belongs to the project SDDCalc
 *
 * See enclosed license file for licensing issues.
 * 
 */
package utils;

import java.text.NumberFormat;

public class Utils {
    
    private static NumberFormat formatter;
    
    static {
        formatter = NumberFormat.getInstance();
        formatter.setMinimumFractionDigits(4); 
    }
    
    public static String format(double s) {
        return formatter.format(s);
    }

}
