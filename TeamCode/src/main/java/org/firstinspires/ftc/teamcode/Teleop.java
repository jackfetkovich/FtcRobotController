package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.subsystems.Robot;
@TeleOp(name="Teleop")
public class Teleop extends OpMode {

    Robot robot = new Robot();


    double clawSqueeze = 1;
    double clawRelease = 0.8;

    int turretPosition = -143;


    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.claw.clawServo.setPosition(clawSqueeze);
    }
    @Override
    public void loop() {

        actuateIntake();
        driveRobot();
        actuateDuckWheel();
        actuateHorizontalSlide();
        actuateClaw();
        actuateTurret();
        setSlideState();

        robot.slides.update();
        robot.turret.update();

        telemetry.addData("slide pos", robot.slides.getSubsystemState());
        telemetry.addData("turret pos", robot.turret.turretMotor.getCurrentPosition());
        telemetry.addData("reference", robot.turret.getReferencePosition());
        telemetry.addData("power",robot.slides.power);
        telemetry.update();
    }

    public void actuateIntake() {
        if (gamepad2.left_bumper || gamepad1.x) {
            robot.intake.motorIntake.setPower(-1);
        } else if (gamepad2.right_bumper || gamepad2.right_trigger > 0.5) {
            robot.intake.motorIntake.setPower(1);
        } else {
            robot.intake.motorIntake.setPower(0);
        }

    }

    public void driveRobot() {
//        if (gamepad1.right_trigger > 0.5) {
//            robot.driveTrain.arcadeDrive(gamepad1.left_stick_y, .2 * gamepad1.right_stick_x);
//        } else if (gamepad1.left_trigger > 0.5) {
//            robot.driveTrain.arcadeDrive(gamepad1.left_stick_y * .5, .4 * gamepad1.right_stick_x);
//        } else {
//            robot.driveTrain.arcadeDrive(gamepad1.left_stick_y, .4 * gamepad1.right_stick_x);
//        }

        robot.driveTrain.tankDrive(gamepad1.left_stick_y, gamepad1.right_stick_y);
    }
    public void setSlideState() {
        if (gamepad2.dpad_down) {
            robot.slides.setSlidePositionReference(robot.slides.slideDown);
        } else if (gamepad2.dpad_left) {
            robot.slides.setSlidePositionReference(robot.slides.level1);
        } else if (gamepad2.dpad_right) {
            robot.slides.setSlidePositionReference(robot.slides.level2);
        } else if (gamepad2.dpad_up) {
            robot.slides.setSlidePositionReference(robot.slides.level3);
        }

        // TODO max change this if the slides are too fast
//        double multiplier = 5;
//        robot.slides.adjustSlideReference(gamepad2.left_stick_y * multiplier);

        if (robot.slides.getSlidePositionReference() < 0) {
            robot.slides.setSlidePositionReference(robot.slides.slideDown);
        }


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
            double percentError = Math.abs((slidePosition - robot.slides.level2) / robot.slides.level2);
            robot.slides.setSlidePositionReference(robot.slides.level2);
            robot.slides.servoHorizSlide.setPosition(0);

            if (percentError < 0.05) {
                turretPosition = 0;

            }

        }
//        robot.turret.turretMotor.setPower(Range.clip(gamepad2.left_stick_x,-0.5,0.5));
        robot.turret.setReferencePosition(turretPosition);

    }

    public void actuateClaw() {
        if (gamepad2.right_trigger > 0.5) {
            robot.claw.clawServo.setPosition(clawRelease);
        } else {
            robot.claw.clawServo.setPosition(clawSqueeze);
        }
    }

    public void actuateHorizontalSlide() {
        if (gamepad2.b) {
            robot.slides.servoHorizSlide.setPosition(0.7);
        } else if (gamepad2.x) {
            robot.slides.servoHorizSlide.setPosition(0);
        } else if (gamepad2.left_trigger > 0.5) {
            robot.slides.servoHorizSlide.setPosition(0.5);
        }
    }

    public void actuateDuckWheel() {
        if (gamepad1.y) {
            robot.duckWheel.wheelServo.setPower(1);
        } else if (gamepad1.a) {
            robot.duckWheel.wheelServo.setPower(-1);
        } else {
            robot.duckWheel.wheelServo.setPower(0);
        }
    }


}