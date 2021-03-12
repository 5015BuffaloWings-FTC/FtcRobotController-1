package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Auto")

public class Auto extends LinearOpMode {

    Definitions robot = new Definitions();

    enum State { INIT, MOVE, STOP };
    @Override
    public void runOpMode() {
        robot.driveWithEncoders();
        State state_s = null;

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        telemetry.clear();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            switch(state_s){
                case INIT:
                    break;
                case MOVE:
                    break;
                case STOP:
                    break;
                default:
                    break;

            }

            telemetry.addData("LeftFrontMotor Current Encoder Value: ", robot.leftFrontMotor.getCurrentPosition());
            telemetry.update();
        }
    }
}

