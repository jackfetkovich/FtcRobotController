package org.firstinspires.ftc.teamcode.values;

public class Constants {
    public static final double clawSqueeze = 1;
    public static final double clawRelease = 0.8;
    public static final int turretPosition = -143;

    static final double     COUNTS_PER_MOTOR_REV    = 28 * 13.7;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.78 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

}
