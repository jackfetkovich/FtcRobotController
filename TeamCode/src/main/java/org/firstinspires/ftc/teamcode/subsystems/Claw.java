package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw implements Subsystem {
    public Servo clawServo;

    @Override
    public void init(HardwareMap hwmap) {
        initNoReset(hwmap);
    }

    @Override
    public void initNoReset(HardwareMap hwmap) {
        clawServo = hwmap.get(Servo.class, "clawServo");
    }

    @Override
    public void update() {

    }

    @Override
    public Object getSubsystemState() {
        return null;
    }
}
