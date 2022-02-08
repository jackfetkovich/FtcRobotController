package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Actions.Actions;

import java.util.ArrayList;
import java.util.Collections;

public class Robot {
    public Drivetrain driveTrain = new Drivetrain();
    public Intake intake = new Intake();
    public Slides slides = new Slides();
    public DuckWheel duckWheel = new DuckWheel();
    public Claw claw = new Claw();

    public Drivetrain getDriveTrain() {
        return driveTrain;
    }

    public Intake getIntake() {
        return intake;
    }

    public Slides getSlides() {
        return slides;
    }

    public DuckWheel getDuckWheel() {
        return duckWheel;
    }

    public Claw getClaw() {
        return claw;
    }

    public Turret getTurret() {
        return turret;
    }

    public Turret turret = new Turret();

    ArrayList<Subsystem> subsystems = new ArrayList<>();

    public Robot() {
        subsystems.add(driveTrain);
        subsystems.add(intake);
        subsystems.add(slides);
        subsystems.add(duckWheel);
        subsystems.add(claw);
        subsystems.add(turret);
    }

    public void init(HardwareMap hwmap) {
        for (Subsystem s : subsystems) {
            s.init(hwmap);
        }
    }

    public void initNoReset(HardwareMap hwmap) {
        for (Subsystem s : subsystems) {
            s.initNoReset(hwmap);
        }
    }

    public void updateMechanisms() {
        for (Subsystem s : subsystems) {
            s.update();
        }
    }

}