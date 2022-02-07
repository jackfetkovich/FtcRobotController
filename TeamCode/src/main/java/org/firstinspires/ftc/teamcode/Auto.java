package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Robot;

@Autonomous
public class Auto extends LinearOpMode {

    static final double     COUNTS_PER_MOTOR_REV    = 28 * 13.7;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.78 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    Robot robot = new Robot();
    private ElapsedTime runtime = new ElapsedTime();



    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();
        moveSlidesAndTurretForBeginningOfAuto();
//        encoderDrive(DRIVE_SPEED, 12,12,6);
    }
    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int newLeftTarget2;
        int newRightTarget2;
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.driveTrain.left0.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.driveTrain.right2.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            newLeftTarget2 = robot.driveTrain.left1.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget2 = robot.driveTrain.right3.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);

            robot.driveTrain.left0.setTargetPosition(newLeftTarget);
            robot.driveTrain.right2.setTargetPosition(newRightTarget);
            robot.driveTrain.left1.setTargetPosition(newLeftTarget2);
            robot.driveTrain.right3.setTargetPosition(newRightTarget2);

            // Turn On RUN_TO_POSITION
            robot.driveTrain.left0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.driveTrain.right2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.driveTrain.left1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.driveTrain.right3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.driveTrain.left0.setPower(Math.abs(speed));
            robot.driveTrain.right2.setPower(Math.abs(speed));
            robot.driveTrain.left1.setPower(Math.abs(speed));
            robot.driveTrain.right3.setPower(Math.abs(speed));
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.driveTrain.left0.isBusy() && robot.driveTrain.right2.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.driveTrain.left0.getCurrentPosition(),
                        robot.driveTrain.right2.getCurrentPosition());
                telemetry.update();
            }
            robot.driveTrain.left0.setPower(Math.abs(0));
            robot.driveTrain.right2.setPower(Math.abs(0));
            robot.driveTrain.left1.setPower(Math.abs(0));
            robot.driveTrain.right3.setPower(Math.abs(0));

            // Turn off RUN_TO_POSITION
            robot.driveTrain.left0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.driveTrain.right2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.driveTrain.left1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.driveTrain.right3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //  sleep(250);   // optional pause after each move
        }
    }

    public void moveSlidesAndTurretForBeginningOfAuto() {
        double referenceForSlideFirst = robot.slides.level2;
        boolean isComplete = false;
        robot.slides.servoHorizSlide.setPosition(0.7);


        while (!isComplete && opModeIsActive()) {

            double turretErrorFromZero = percentError(1,robot.turret.getSubsystemState());
            double slideErrorFromDown = percentError(robot.slides.slideDown, robot.slides.getSubsystemState());
            double slideErrorFromSecond = percentError(robot.slides.level2, robot.slides.getSubsystemState());

            if (Math.abs(-robot.turret.getSubsystemState()) < 25 && slideErrorFromDown < 0.05) isComplete = true;

            if (slideErrorFromSecond < 0.05) {
                robot.turret.setReferencePosition(0);
            }

            if (Math.abs(-robot.turret.getSubsystemState()) > 25)  {
                robot.slides.setSlidePositionReference(robot.slides.level3);
            } else {
                robot.slides.setSlidePositionReference(robot.slides.slideDown);
            }

            robot.slides.update();
            robot.turret.update();

        }

        robot.turret.turretMotor.setPower(0);
        robot.slides.motorVertSlide.setPower(0);

    }
    public double percentError(double expected, double measured) {
        return Math.abs((measured-expected)/expected);
    }
}