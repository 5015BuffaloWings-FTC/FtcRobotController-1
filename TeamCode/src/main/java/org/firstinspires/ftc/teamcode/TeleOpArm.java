package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TeleopArm")
public class TeleOpArm extends OpMode
{
    Definitions robot = new Definitions();
    double slowMovement = 0.5; //Slow movement is used as a multiplier to change movement speed
    double armLowerLimit; //Used to set the lower limit of the scoring arm's range of motion
    double armHigherLimit; //Used to set the upper limit of the scoring arm's range of motion

    public void init()
    {
        robot.robotHardwareMapInit(hardwareMap);
        //robot.leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.driveWithOutEncoders();
        //robot.pulleyMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void loop() {
        /**
         * DRIVING SECTION
         */

       if (gamepad2.right_bumper) {
            robot.outputServo.setPosition(0);
            robot.outputMotor.setPower(1);
        } else {
            robot.outputServo.setPosition(1);
            robot.outputMotor.setPower(gamepad2.left_stick_y);
        }


        //telemetry.addData("Left Back", robot.leftBackMotor.getCurrentPosition());
        telemetry.addData("Left Joystick", gamepad1.left_stick_y);
        telemetry.addData("Output Servo", robot.outputServo.getPosition());
        //telemetry.addData("cLiftServo: ", robot.cLiftServo.getPosition());
        //telemetry.addData("Arm Position", robot.scoringArmMotor.getCurrentPosition());24000
        telemetry.update();


    }

    public void stop()
    {
        robot.setPower(0);
        //robot.leadScrewMotor.setPower(0);
        //robot.scoringArmMotor.setPower(0);
    }
}

//trial
