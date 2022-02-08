package org.firstinspires.ftc.teamcode.Actions;

import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;
import org.firstinspires.ftc.teamcode.values.Constants;
import org.firstinspires.ftc.teamcode.values.SlideState;

import java.util.ArrayList;

public class Actions {

    private Robot r;
    int turretPosition = Integer.valueOf(Constants.turretPosition);
    public Actions(Robot r){
        this.r = r;
    }

    // Intake Actions
    public void intakeIn(){
        r.getIntake().motorIntake.setPower(-1);
    }
    public void intakeOut(){
        r.getIntake().motorIntake.setPower(1);
    }
    public void intakeStop(){
        r.getIntake().motorIntake.setPower(0);
    }

    // Drivetrain Actions
    public void setDrivePower(double left, double right){
        r.getDriveTrain().tankDrive(left, right);
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

}
