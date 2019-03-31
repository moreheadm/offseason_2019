
package com.falconxrobotics.lib.units;

public class Units {
    public static double convert(double value, double from, double to) {
        return value / from * to; 
    }
}