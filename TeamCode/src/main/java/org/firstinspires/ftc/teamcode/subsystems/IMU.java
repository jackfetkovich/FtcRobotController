package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IMU implements Subsystem{

    public BNO055IMU imu;
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();


    @Override
    public void init(HardwareMap hwmap) {
        initNoReset(hwmap);
        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = true;

        imu.initialize(parameters);
    }

    @Override
    public void initNoReset(HardwareMap hwmap) {
        imu = hwmap.get(BNO055IMU.class, "imu");
    }

    @Override
    public void update() {
    }

    @Override
    public Object getSubsystemState() {
        return null;
    }
}
