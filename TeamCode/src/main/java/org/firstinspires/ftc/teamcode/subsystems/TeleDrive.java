package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.Actions.Actions;
import org.firstinspires.ftc.teamcode.values.Constants;
import org.firstinspires.ftc.teamcode.values.SlideState;

public class TeleDrive {
    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private Actions r;

    public TeleDrive(Gamepad gamepad1, Gamepad gamepad2, Actions r){
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.r = r;
    }

    public void driveLoop(){

        // DRIVE CONTROLS
        // Alice Controls
//        r.setDrivePower(gamepad1.left_stick_y, gamepad1.right_stick_y);

        // INTAKE CONTROLS
//        if (gamepad2.right_trigger > 0) {
//            r.intakeIn();
//            r.openClaw();
//        } else if (gamepad2.x || gamepad1.left_bumper) {
//            r.intakeOut();
//        }
//        else if(gamepad1.right_trigger > 0){
//            r.intakeIn();
//        } else if(gamepad1.left_trigger > 0){
//            r.intakeOut();
//        }
//        else {
//            r.intakeStop();
//            r.closeClaw();
//        }
//
//        // DUCKWHEEL CONTROLS
//        if (gamepad2.right_bumper) {
//            r.duckWheelForward();
//        } else if (gamepad1.a) {
//            r.duckWheelBackward();
//        } else {
//            r.duckWheelStop();
//        }
//
//        // HORIZONTAL SLIDE CONTROLS
//        if (gamepad2.b) {
//            r.horizontalSlideOut();
//        } else if (gamepad2.left_trigger > 0) {
//            r.horizontalSlideIn();
//        }
//
//        // CLAW CONTROLS
//        if (gamepad1.right_trigger > 0) {
//            r.openClaw();
//        } else {
//            r.closeClaw();
//        }
//
//        // SLIDE CONTROLS
//        if (gamepad1.dpad_down) {
//            r.slideDown();
//        } else if (gamepad1.dpad_left) {
//            r.slideDuckLevel();
//        } else if (gamepad1.dpad_right) {
//            r.slideLevel2();
//        } else if (gamepad1.dpad_up) {
//            r.slideLevel3();
//        }
//
//        if (r.getSlideReferencePosition() < 0) {
//            r.slideDown();
//        }





















        // Nico Controls
        if (gamepad1.right_trigger > 0.5) {
            r.r.driveTrain.arcadeDrive(gamepad1.left_stick_y, .2 * gamepad1.right_stick_x);
        } else if (gamepad1.left_trigger > 0.5) {
            r.r.driveTrain.arcadeDrive(gamepad1.left_stick_y * .5, .4 * gamepad1.right_stick_x);
        } else {
            r.r.driveTrain.arcadeDrive(gamepad1.left_stick_y, .4 * gamepad1.right_stick_x);
        }


        // SLIDE CONTROLS
        if (gamepad2.a) {
            r.slideDown();
        } else if (gamepad2.dpad_left) {
            r.slideDuckLevel();
        } else if (gamepad2.dpad_right) {
            r.slideLevel2();
        } else if (gamepad2.y) {
            r.slideLevel3();
        }

        if (r.getSlideReferencePosition() < 0) {
            r.slideDown();
        }

        // Claw side controls
        if(gamepad2.b){
            r.horizontalSlideOut();
        }
        if(gamepad2.left_trigger > 0){
            r.horizontalSlideIn();
        }

        // Intake controls
        if(gamepad2.right_trigger > 0){
            r.intakeIn();
            r.openClaw();
        } else if (gamepad2.left_bumper || gamepad1.x){
            r.intakeOut();
        } else {
            r.intakeStop();
            r.closeClaw();
        }

        // Turret Controls
        if (gamepad2.left_stick_x != 0){
            float turretPosition = gamepad2.left_stick_x * 5;

            r.r.getTurret().setReferencePosition(turretPosition);
            turretPosition += gamepad2.left_stick_x*5;

            if (gamepad1.left_stick_button) {
                turretPosition = 0;
                r.r.slides.servoHorizSlide.setPosition(0);
            }
            r.r.turret.setReferencePosition(turretPosition);
        }



        // Duckwheel
        if(gamepad1.a){
            r.duckWheelForwardTele();
        } else {
            r.duckWheelStop();
        }






    }

}
