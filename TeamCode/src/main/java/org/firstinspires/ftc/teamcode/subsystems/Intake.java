package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake implements Subsystem {
    public DcMotorEx motorIntake;

    @Override
    public void init(HardwareMap hwmap) {
        initNoReset(hwmap);
    }


    @Override
    public void initNoReset(HardwareMap hwmap) {
        motorIntake = hwmap.get(DcMotorEx.class, "motorIntake");
    }

    @Override
    public void update() {

    }

    @Override
    public Object getSubsystemState() {
        return null;
    }
}