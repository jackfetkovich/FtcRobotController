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
        r.setDrivePower(gamepad1.left_stick_y, gamepad1.right_stick_y);

        // INTAKE CONTROLS
        if (gamepad2.left_bumper || gamepad1.x) {
            r.intakeIn();
        } else if (gamepad2.right_bumper || gamepad2.right_trigger > 0.5) {
            r.intakeOut();
        } else if(gamepad1.right_trigger > 0){
            r.intakeIn();
        } else if(gamepad1.left_trigger > 0){
            r.intakeOut();
        } else {
            r.intakeStop();
        }

        // DUCKWHEEL CONTROLS
        if (gamepad1.y) {
            r.duckWheelForward();
        } else if (gamepad1.a) {
            r.duckWheelBackward();
        } else {
            r.duckWheelStop();
        }

        // HORIZONTAL SLIDE CONTROLS
        if (gamepad1.x) {
            r.horizontalSlideOut();
        } else if (gamepad1.b) {
            r.horizontalSlideIn();
        }

        // CLAW CONTROLS
        if (gamepad1.right_trigger > 0) {
            r.openClaw();
        } else {
            r.closeClaw();
        }

        // SLIDE CONTROLS
        if (gamepad1.dpad_down) {
            r.slideDown();
        } else if (gamepad1.dpad_left) {
            r.slideLevel1();
        } else if (gamepad1.dpad_right) {
            r.slideLevel2();
        } else if (gamepad1.dpad_up) {
            r.slideLevel3();
        }

        if (r.getSlideReferencePosition() < 0) {
            r.slideDown();
        }


    }

}
