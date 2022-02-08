package org.firstinspires.ftc.teamcode;
import android.transition.Slide;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Actions.Actions;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.TeleDrive;
import org.firstinspires.ftc.teamcode.values.Constants;
import org.firstinspires.ftc.teamcode.values.SlideState;

@TeleOp(name="Teleop")
public class Teleop extends OpMode {

    Robot robot = new Robot();
    Actions r = new Actions(robot);
    TeleDrive teleDrive ;
    int turretPosition = -143;


    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.claw.clawServo.setPosition(Constants.clawSqueeze);
        teleDrive = new TeleDrive(gamepad1, gamepad2, r);
    }
    @Override
    public void loop() {
        teleDrive.driveLoop();
        actuateTurret();

        robot.slides.update();
        robot.turret.update();

        telemetry.addData("slide pos", robot.slides.getSubsystemState());
        telemetry.addData("turret pos", robot.turret.turretMotor.getCurrentPosition());
        telemetry.addData("reference", robot.turret.getReferencePosition());
        telemetry.addData("power",robot.slides.power);
        telemetry.update();
    }

    public void actuateTurret() {

        turretPosition += gamepad2.left_stick_x*5;
        if (turretPosition > 323){
            turretPosition = 323;
        } else if (turretPosition < -213){

            turretPosition = -213;
        }

        if (gamepad2.left_stick_button) {
            double slidePosition = robot.slides.getSubsystemState();
            double percentError = Math.abs((slidePosition - SlideState.LEVEL2) / SlideState.LEVEL2);
            robot.slides.setSlidePositionReference(SlideState.LEVEL2);
            robot.slides.servoHorizSlide.setPosition(0);

            if (percentError < 0.05) {
                turretPosition = 0;

            }

        }
        // Used for manual control of turret as opposed to PID
//        robot.turret.turretMotor.setPower(Range.clip(gamepad2.left_stick_x,-0.5,0.5));
        robot.turret.setReferencePosition(turretPosition);

    }
}