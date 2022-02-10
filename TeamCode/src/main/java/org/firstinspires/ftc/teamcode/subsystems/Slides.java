package org.firstinspires.ftc.teamcode.subsystems;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TuningParams.ControllerCoefficients;

public class Slides implements Subsystem {
    public Servo servoHorizSlide;
    public DcMotorEx motorVertSlide;
    //public final double slideDown = 50;
//    public final double level1 = 300; //dpad left
//    public final double level2 = 1200; //dpad right
//    public final double level3 = 1900;//dpad up
    public double power = 0;

    protected double slidePositionReference = 0;


    BasicPID slideController = new BasicPID(ControllerCoefficients.slideCoefficients);

    @Override
    public void init(HardwareMap hwmap) {
        initNoReset(hwmap);
        motorVertSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorVertSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void initNoReset(HardwareMap hwmap) {
        servoHorizSlide = hwmap.get(Servo.class, "servoHorizSlide");
        motorVertSlide = hwmap.get(DcMotorEx.class, "motorVertSlide");
        motorVertSlide.setDirection(DcMotorSimple.Direction.FORWARD);
        motorVertSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void update() {
        this.power = slideController.calculate(slidePositionReference, motorVertSlide.getCurrentPosition());
        motorVertSlide.setPower(power);
    }

    @Override
    public Integer getSubsystemState() {
        return motorVertSlide.getCurrentPosition();
    }


    public double getSlidePositionReference() {
        return slidePositionReference;
    }

    public void setSlidePositionReference(double slidePositionReference) {
        this.slidePositionReference = slidePositionReference;
    }

    public void adjustSlideReference(double adjustment) {
        this.slidePositionReference += adjustment;
    }

    public double publishPower(){
        return this.power;
    }


    public double getError() {
        return slidePositionReference -  motorVertSlide.getCurrentPosition();
    }
}