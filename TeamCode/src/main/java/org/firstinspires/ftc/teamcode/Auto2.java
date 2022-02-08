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

        r.rotate(-90);


    }
}
