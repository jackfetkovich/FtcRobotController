package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Actions.Actions;
import org.firstinspires.ftc.teamcode.subsystems.Robot;

@Autonomous
public class Auto2 extends LinearOpMode {
    Robot robot;
    Actions r;


    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot();
        robot.init(hardwareMap);
        r = new Actions(robot, this);
        waitForStart();
        r.resetSlidesAndTurret();
        r.updateSlides();

        r.drive(0.5, 30, 30, 2);
        r.slideLevel1();
        r.updateSlides();
        r.horizontalSlideIn();
        r.duckWheelForward();
        r.duckWheelStop();
        r.slideDown();
        r.updateSlides();
        r.drive(0.5, -30, -30, 2);
    }
}
