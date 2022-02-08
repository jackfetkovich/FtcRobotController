package org.firstinspires.ftc.teamcode.Actions;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.TuningParams.ControllerCoefficients;
import org.firstinspires.ftc.teamcode.Utilities;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;
import org.firstinspires.ftc.teamcode.values.Constants;
import org.firstinspires.ftc.teamcode.values.SlideState;
import org.slf4j.helpers.Util;

import java.util.ArrayList;

public class Actions {


    BasicPID turnController = new BasicPID(ControllerCoefficients.turnCoefficients);

    LinearOpMode opMode;
    private Robot r;
    int turretPosition = Integer.valueOf(Constants.turretPosition);
    public Actions(Robot r, LinearOpMode opMode){
        this.r = r;
        this.opMode = opMode;
    }

    // Intake Actions
    public void intakeIn(){
        r.getIntake().motorIntake.setPower(1);
    }
    public void intakeOut(){
        r.getIntake().motorIntake.setPower(-1);
    }
    public void intakeStop(){
        r.getIntake().motorIntake.setPower(0);
    }

    // Drivetrain Actions
    public void setDrivePower(double left, double right){
        r.getDriveTrain().tankDrive(left, right);
    }

    public void driveForward(double distance){

    }

    public void rotate(double degrees){
        double power = 0;
        while(Utilities.percentError(degrees, getXRotation()) > 0.5 && opMode.opModeIsActive()) {
            power = turnController.calculate(degrees, getXRotation());

            if (degrees >= 0) {
                setDrivePower((float) -power, (float) power);
            } else {
                setDrivePower((float) power, (float) -power);
            }
            opMode.telemetry.addData("Goal: ", degrees);
            opMode.telemetry.addData("Current: ", getXRotation());
            opMode.telemetry.addData("Power: ", power);
            opMode.telemetry.update();
        }
    }

    // DuckWheel Actions
    public void duckWheelForward(){
        r.getDuckWheel().wheelServo.setPower(1);
    }
    public void duckWheelBackward(){
        r.getDuckWheel().wheelServo.setPower(-1);
    }
    public void duckWheelStop(){
        r.getDuckWheel().wheelServo.setPower(0);
    }

    // Horizontal Slide Actions
    public void horizontalSlideOut(){
        r.getSlides().servoHorizSlide.setPosition(0.7);
    }
    public void horizontalSlideIn(){
        r.getSlides().servoHorizSlide.setPosition(0.5);
    }

    // Claw Actions
    public void openClaw(){
        r.getClaw().clawServo.setPosition(Constants.clawRelease);
    }
    public void closeClaw(){
        r.getClaw().clawServo.setPosition(Constants.clawSqueeze);
    }

    // Slide Actions

    public double getSlideReferencePosition(){
        return r.getSlides().getSlidePositionReference();
    }
    public void slideDown(){
        r.getSlides().setSlidePositionReference(SlideState.DOWN);

    }
    public void slideLevel1(){
        r.getSlides().setSlidePositionReference(SlideState.LEVEL1);

    }
    public void slideLevel2(){
        r.getSlides().setSlidePositionReference(SlideState.LEVEL2);

    }
    public void slideLevel3(){
        r.getSlides().setSlidePositionReference(SlideState.LEVEL3);

    }

    private void updateSlides(){
        r.getSlides().update();
    }

    public void resetSlidesAndTurret() {
        double referenceForSlideFirst = SlideState.LEVEL2;
        boolean isComplete = false;
        r.slides.servoHorizSlide.setPosition(0.7);

        while (!isComplete) {

            double turretErrorFromZero = Utilities.percentError(1,r.turret.getSubsystemState());
            double slideErrorFromDown = Utilities.percentError(SlideState.DOWN, r.slides.getSubsystemState());
            double slideErrorFromSecond = Utilities.percentError(SlideState.LEVEL2, r.slides.getSubsystemState());

            if (Math.abs(-r.turret.getSubsystemState()) < 25 && slideErrorFromDown < 0.05) isComplete = true;

            if (slideErrorFromSecond < 0.05) {
                r.turret.setReferencePosition(0);
            }

            if (Math.abs(-r.turret.getSubsystemState()) > 25)  {
                r.slides.setSlidePositionReference(SlideState.LEVEL3);
            } else {
                r.slides.setSlidePositionReference(SlideState.DOWN);
            }

            r.slides.update();
            r.turret.update();

        }
        r.turret.turretMotor.setPower(0);
        r.slides.motorVertSlide.setPower(0);

    }

    // IMU Actions
    public float getXRotation(){
        return r.getImu().imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }
}
