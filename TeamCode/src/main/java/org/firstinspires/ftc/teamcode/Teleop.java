package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp; //For running TeleOP
import com.qualcomm.robotcore.hardware.DcMotor; //For redefining motors.
import com.qualcomm.robotcore.util.Range; //Allows digital inputs to be clipped to a set range

import java.util.Locale;


@TeleOp(name="Teleop")
public class Teleop extends OpMode
{
    Definitions robot = new Definitions();
    double slowMovement = 0.35; //Slow movement is used as a multiplier to change movement speed
    String rings = "start"; //indicates if our autoSensor detects 0, 1, or 4 rings
    boolean cLiftAlign = false; //indicates if we are aligning the clift with the output
    double CLIFT_DOWN = .77; //the position that aligns our clift with our intake
    double cLiftHeight = CLIFT_DOWN; //represents the current height of our clift
    double CLIFT_INC = .0015; //.005 //.0015 //the value we use to increment/increase the clift height
    boolean pulleyRunning = false; //indicates whether or not the pulleyMotor has been set to run to a preset position
    double outputSpeed = 0; //represents the current speed of the outputMotor
    double OUTPUT_MAX = .8; //the fastest speed we want our outputMotor to spin
    double OUTPUT_INC = .0025; //the value we use to increment/increase the outputMotor speed
    boolean manualLaunch = false;
    boolean autoLaunch = false;


    public void init()
    {
        robot.robotHardwareMapInit(hardwareMap);
        robot.leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.driveWithOutEncoders();
        robot.attachmentsWithEncoders();
        robot.servoInit();
        robot.pulleyMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void loop() {
        /**
         * DRIVING SECTION
         */
        //Using Range.clip to limit joystick values from -1 to 1 (clipping the outputs)
        //If gamepad1 presses the right bumper the speed will be multiplied by the slowMovement constant
        if (!gamepad1.right_bumper) {
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


        //gamepad2 controls
        // right trigger powers intake
        // left trigger reverses the intake
        //prevents intake from running if clift in not in "down" position
        if (robot.linearServo.getPosition() == CLIFT_DOWN){
            if (gamepad2.right_trigger > .001){
                robot.intakeMotor.setPower(-gamepad2.right_trigger);
            }else if (gamepad2.left_trigger > .001){
                robot.intakeMotor.setPower(gamepad2.left_trigger);
            }else{
                robot.intakeMotor.setPower(0);
            }
        }else {
            robot.intakeMotor.setPower(0);
        }


        //gamepad2 controls
        //left bumper moves outputServo to "out" position
        // outputServo automatically returns to "in" position
        if (gamepad2.left_bumper){
            manualLaunch = true;
        }else{
            manualLaunch = false;
        }



        //gamepad2 controls
        //when the right bumper is held, the outputMotor ramps up to full speed and then the outputServo moves to "out" position
        //outputServo automatically returns to "in" position
        if (gamepad2.right_bumper) {
            robot.outputMotor.setPower(outputSpeed);
            if (outputSpeed < OUTPUT_MAX) {
                outputSpeed += OUTPUT_INC;
            }else {
                if (robot.outputServo.getPosition() != 0) {
                    autoLaunch = true;
                }
            }
        }else {
            outputSpeed = 0;
            robot.outputMotor.setPower(gamepad2.left_stick_y);
            autoLaunch = false;
        }

        
        if (autoLaunch || manualLaunch){
            robot.outputServo.setPosition(0);
        }else{
            robot.outputServo.setPosition(1);
        }


        //gamepad2 controls
        //if the right joystick is being used the pulleyMotor runs based on that value (but cannot pass the upper and lower limits)
        //otherwise, pressing dpad up will move the pulleyMotor to our highest launch angle and pressing dpad down moves it to our launch line angle
        if (gamepad2.right_stick_y == 0) {
            if (pulleyRunning){
                if (Math.abs(robot.pulleyMotor.getCurrentPosition() - robot.pulleyMotor.getTargetPosition()) < 5){
                    robot.pulleyMotor.setPower(0);
                    pulleyRunning = false;
                }
            } else {
                if (gamepad2.dpad_up) {
                    robot.pulleyMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.pulleyMotor.setPower(.25);
                    robot.pulleyMotor.setTargetPosition(-400);
                    pulleyRunning = true;
                } else if (gamepad2.dpad_down) {
                    robot.pulleyMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.pulleyMotor.setPower(.25);
                    robot.pulleyMotor.setTargetPosition(-280);
                    pulleyRunning = true;
                } else {
                    robot.pulleyMotor.setPower(0);
                    robot.pulleyMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
            }
        } else {
            robot.pulleyMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            pulleyRunning = false;
            if (robot.pulleyMotor.getCurrentPosition() < 0 && robot.pulleyMotor.getCurrentPosition() > -490){
                robot.pulleyMotor.setPower(gamepad2.right_stick_y * .25);
            } else {
                robot.pulleyMotor.setPower(0);
            }
        }


        //gamepad2 controls
        //pressing a sets the clift to the intake/bottom position
        //pressing b aligns the clift with the output by incrementing the clift height until it reaches the magnetic limit switch (on the output)
        //pressing x moves the clift to the initialized position
        //pressing y moves the clift to a saftey "top" position
        if (gamepad2.a){
            robot.linearServo.setPosition(CLIFT_DOWN);
            cLiftAlign = false;
        }else if (gamepad2.b){
            cLiftAlign = true;
        }else if (gamepad2.x){
            robot.linearServo.setPosition(.5);
        }else if (gamepad2.y){
            robot.linearServo.setPosition(.3);
        }

        if (cLiftAlign && !robot.magneticLimitSwitch.isPressed()){
            cLiftHeight -= CLIFT_INC;
            if (cLiftHeight < .3){
                cLiftHeight = .3;
                cLiftAlign = false;
            }
            if (cLiftHeight > CLIFT_DOWN){
                cLiftHeight = CLIFT_DOWN;
            }
            robot.linearServo.setPosition(cLiftHeight);
        }else{
            cLiftAlign = false;
            cLiftHeight = CLIFT_DOWN;
        }


        // allowed us to test and refine our color sensor program for detecting the rings in auto
        if (robot.autoColor.red() < 65)
        {
            rings = "zero";
        }else
        {
            if (robot.autoColor.red() < 81)
            {
                rings = "one";
            }else
            {
                rings = "four";
            }
        }

        if (gamepad2.dpad_right){
            robot.wobbleGoalR.setPosition(1);
            robot.wobbleGoalL.setPosition(1);
        }else if (gamepad2.dpad_left){
            robot.wobbleGoalR. setPosition(0);
            robot.wobbleGoalL.setPosition(0);
        }


//        if (gamepad1.a){
//            robot.wobbleGoalR.setPosition(1);
//        }
//
//        if (gamepad1.b){
//            robot.wobbleGoalL.setPosition(1);
//        }
//
//        if (gamepad1.x){
//            robot.sensorArm.setPosition(1);
//        }


        //telemetry helps us to test, modify, and debug our code and hardware
        //it is especially useful during the initial integration of software and hardware
        telemetry.addData("Right Trigger", gamepad2.right_trigger);
        telemetry.addData("Left Back", robot.leftBackMotor.getCurrentPosition());
        telemetry.addData("Pulley Motor", robot.pulleyMotor.getCurrentPosition());
        telemetry.addData("Pulley Power", robot.pulleyMotor.getPower());
        telemetry.addData("Right Joystick 1", gamepad2.right_stick_y);
        telemetry.addData("Left Joystick", gamepad1.left_stick_y);
//        telemetry.addData("Distance (cm)", String.format(Locale.US, "%.02f", robot.autoDistance.getDistance(DistanceUnit.CM)));
//        telemetry.addData("Alpha", robot.autoColor.alpha());
//        telemetry.addData("Red  ", robot.autoColor.red());
//        telemetry.addData("Green", robot.autoColor.green());
//        telemetry.addData("Blue ", robot.autoColor.blue());
//        telemetry.addData("sensorTest", rings);
////        telemetry.addData("outputPower", robot.outputMotor.getPower());
//        telemetry.addData("cLiftHeight", cLiftHeight);
//        telemetry.addData("cLiftServo", robot.cLiftServo.getPosition());

        telemetry.update();


    }

    public void stop()
    {
        robot.setPower(0);
    }
}

