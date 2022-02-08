package org.firstinspires.ftc.teamcode.Actions;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

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
    private ElapsedTime runtime = new ElapsedTime();

    LinearOpMode opMode;
    private Robot r;
    int turretPosition = Constants.turretPosition;
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

    public void drive(double speed,
                      double leftInches, double rightInches,
                      double timeoutS){
        int newLeftTarget;
        int newRightTarget;
        int newLeftTarget2;
        int newRightTarget2;
        // Ensure that the opmode is still active
        if (opMode.opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = r.getDriveTrain().left0.getCurrentPosition() + (int)(leftInches * Constants.COUNTS_PER_INCH);
            newRightTarget = r.getDriveTrain().right2.getCurrentPosition() + (int)(rightInches * Constants.COUNTS_PER_INCH);
            newLeftTarget2 = r.getDriveTrain().left1.getCurrentPosition() + (int)(leftInches * Constants.COUNTS_PER_INCH);
            newRightTarget2 = r.getDriveTrain().right3.getCurrentPosition() + (int)(rightInches * Constants.COUNTS_PER_INCH);

            r.getDriveTrain().left0.setTargetPosition(newLeftTarget);
            r.getDriveTrain().right2.setTargetPosition(newRightTarget);
            r.getDriveTrain().left1.setTargetPosition(newLeftTarget2);
            r.getDriveTrain().right3.setTargetPosition(newRightTarget2);

            // Turn On RUN_TO_POSITION
            r.getDriveTrain().left0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            r.getDriveTrain().right2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            r.getDriveTrain().left1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            r.getDriveTrain().right3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            r.getDriveTrain().left0.setPower(Math.abs(speed));
            r.getDriveTrain().right2.setPower(Math.abs(speed));
            r.getDriveTrain().left1.setPower(Math.abs(speed));
            r.getDriveTrain().right3.setPower(Math.abs(speed));
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opMode.opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (r.getDriveTrain().left0.isBusy() && r.getDriveTrain().right2.isBusy())) {

//                // Display it for the driver.
//                opMode.telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
//                opMode.telemetry.addData("Path2",  "Running at %7d :%7d",
//                        r.getDriveTrain().left0.getCurrentPosition(),
//                        r.getDriveTrain().right2.getCurrentPosition());
//                opMode.telemetry.update();
            }
            r.getDriveTrain().left0.setPower(Math.abs(0));
            r.getDriveTrain().right2.setPower(Math.abs(0));
            r.getDriveTrain().left1.setPower(Math.abs(0));
            r.getDriveTrain().right3.setPower(Math.abs(0));

            // Turn off RUN_TO_POSITION
            r.getDriveTrain().left0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            r.getDriveTrain().right2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            r.getDriveTrain().left1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            r.getDriveTrain().right3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //  sleep(250);   // optional pause after each move
        }
        runtime.reset();
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
        runtime.reset();
        while(runtime.time() < 5 && opMode.opModeIsActive()) {
            r.getDuckWheel().wheelServo.setPower(1);
        }

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
        opMode.telemetry.addData("Reference:" , r.getSlides().getSlidePositionReference());
        opMode.telemetry.addData("Current: ", r.getSlides().motorVertSlide.getCurrentPosition());
        opMode.telemetry.addData("Calculated Power: ", r.getSlides().publishPower());
        opMode.telemetry.update();
    }
    public void slideLevel2(){
        r.getSlides().setSlidePositionReference(SlideState.LEVEL2);
    }
    public void slideLevel3(){
        r.getSlides().setSlidePositionReference(SlideState.LEVEL3);
    }

    public void updateSlides(){
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
