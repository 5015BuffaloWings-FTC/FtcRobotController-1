package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp; //For running TeleOP
import com.qualcomm.robotcore.hardware.DcMotor; //For redefining motors.
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range; //Allows digital inputs to be clipped to a set range

@TeleOp(name="Teleop")
public class Teleop extends OpMode
{
    Definitions robot = new Definitions();
    double slowMovement = 0.5; //Slow movement is used as a multiplier to change movement speed
    double armLowerLimit; //Used to set the lower limit of the scoring arm's range of motion
    double armHigherLimit; //Used to set the upper limit of the scoring arm's range of motion

    public void init()
    {
        robot.robotHardwareMapInit(hardwareMap);
        robot.resetEncoders();
        robot.runWithOutEncoders();
        //robot.pulleyMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void loop() {
        /**
         * DRIVING SECTION
         */
        //Using Range.clip to limit joystick values from -1 to 1 (clipping the outputs)
        //If gamepad 1 presses the right bumper the speed will be multiplied by the slowMovement constant
        if (gamepad1.right_bumper) {
            robot.rightFrontMotor.setPower(Range.clip((-gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
            robot.leftFrontMotor.setPower(Range.clip((gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
            robot.rightBackMotor.setPower(Range.clip((-gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
            robot.leftBackMotor.setPower(Range.clip((gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x) * slowMovement, -1, 1));
        } else {
            robot.rightFrontMotor.setPower(Range.clip((-gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
            robot.leftFrontMotor.setPower(Range.clip((gamepad1.left_stick_y - (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
            robot.rightBackMotor.setPower(Range.clip((-gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
            robot.leftBackMotor.setPower(Range.clip((gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x), -1, 1));
        }

        robot.intakeMotor.setPower(gamepad2.right_trigger);
        robot.intakeMotor.setPower(-gamepad2.left_trigger);

      /*  if (gamepad2.right_bumper) {
            robot.outputServo.setPosition(0);
            robot.outputMotor.setPower(1);
        } else {
            robot.outputServo.setPosition(1);
            robot.outputMotor.setPower(gamepad2.left_stick_y);
        }
        */

       /* if (gamepad2.right_stick_y == 0) {
            robot.pulleyMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            if (gamepad2.x) {
                robot.pulleyMotor.setTargetPosition(400);
            } else if (gamepad2.y) {
                robot.pulleyMotor.setTargetPosition(800);
            } else {
                robot.pulleyMotor.setTargetPosition(1000);
            }
        } else {
            robot.pulleyMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.pulleyMotor.setPower(gamepad2.right_stick_y);

        } */

       /* if ((robot.bottomLimitSwitch.getState() == false) && (robot.topLimitSwitch.getState() == false) ) {
            if (gamepad2.a) {
                robot.cLiftServo.setPosition(0);
            } else if (gamepad2.b) {
                robot.cLiftServo.setPosition(1);
            } else if (gamepad2.dpad_up) {
                robot.cLiftServo.setPosition(0.8);
            } else if (gamepad2.dpad_down) {
                robot.cLiftServo.setPosition(0.5);
            }

        } else robot.cLiftServo.setPosition(0.4);

        */

        telemetry.addData("Left Back", robot.leftBackMotor.getCurrentPosition());
        telemetry.addData("Left Joystick", gamepad1.left_stick_y);
       // telemetry.addData("cLiftServo: ", robot.cLiftServo.getPosition());
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
