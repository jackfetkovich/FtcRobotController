package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;

public class Drivetrain implements Subsystem {
    public DcMotorEx left0;
    public DcMotorEx left1;
    public DcMotorEx right2;
    public DcMotorEx right3;



    @Override
    public void init(HardwareMap hwmap) {
        initNoReset(hwmap);
    }

    @Override
    public void initNoReset(HardwareMap hwmap) {
        left0 = hwmap.get(DcMotorEx.class, "motor0");
        left1 = hwmap.get(DcMotorEx.class, "motor1");
        right2 = hwmap.get(DcMotorEx.class, "motor2");
        right3 = hwmap.get(DcMotorEx.class, "motor3");

        left0.setDirection(DcMotorSimple.Direction.REVERSE);
        left1.setDirection(DcMotorSimple.Direction.REVERSE);

        ArrayList<DcMotorEx> leftMotors = new ArrayList<>();
        ArrayList<DcMotorEx> rightMotors = new ArrayList<>();

        leftMotors.add(left0);
        leftMotors.add(left1);
        rightMotors.add(right2);
        rightMotors.add(right3);

    }

    @Override
    public void update() {

    }

    /**
     * arcade drive is incredibly poggers - ben ceo of 19376 thermal equilibrium
     * @param x forward power
     * @param turn turning power
     */
    public void arcadeDrive(double x, double turn) {
        double l = x - turn;
        double r = x + turn;
        left0.setPower(l);
        left1.setPower(l);
        right2.setPower(r);
        right3.setPower(r);
    }

    public void tankDrive(double l, double r){
        if((l < 0 && r > 0) || (l > 0 && r < 0)){
            l *=0.7;
            r *=0.7;
        }
        left0.setPower(l);
        left1.setPower(l);
        right2.setPower(r);
        right3.setPower(r);
    }

    @Override
    public Object getSubsystemState() {
        return null;
    }
}
