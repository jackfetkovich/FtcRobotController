package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DuckWheel implements Subsystem {
    public CRServo wheelServo;

    @Override
    public void init(HardwareMap hwmap) {
        initNoReset(hwmap);
    }

    @Override
    public void initNoReset(HardwareMap hwmap) {
        wheelServo = hwmap.get(CRServo.class, "duckServo");
    }

    @Override
    public void update() {

    }

    @Override
    public Object getSubsystemState() {
        return null;
    }
}