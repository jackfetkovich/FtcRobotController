package org.firstinspires.ftc.teamcode;

public class Utilities {
    public static double percentError(double expected, double measured) {
        return Math.abs((measured-expected)/expected);
    }

}
