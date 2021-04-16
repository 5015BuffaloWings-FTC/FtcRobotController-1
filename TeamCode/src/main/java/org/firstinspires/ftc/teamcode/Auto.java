/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
    double STRAFECORRECTION = 32.0/29.5;
    int UPPER_LIMIT = 81; //49
    int LOWER_LIMIT = 65; //35
    double autoPower = .5;
    int SLEEP90TURN = 1190;

    double outputSpeed = 0; //represents the current speed of the outputMotor
    double OUTPUT_MAX = .8; //the fastest speed we want our outputMotor to spin
    double OUTPUT_INC = .0025; //the value we use to increment/increase the outputMotor speed



    void outputMotorRamp () {

        while (outputSpeed < OUTPUT_MAX){
            outputSpeed += OUTPUT_INC;
            robot.outputMotor.setPower(outputSpeed);
            sleep(20);
        }
        robot.outputServo.setPosition(0);
        sleep (2000);
        robot.outputServo.setPosition(1);
        robot.outputMotor.setPower(0);
    }

    @Override
    public void runOpMode() {
        robot.robotHardwareMapInit(hardwareMap);
        robot.resetEncoders();
        robot.setStrafeLeft();

        telemetry.addData("Status", "Initialized");
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();

//        robot.driveTo(-500, -500, 500, 500);
//        robot.setDrivePower(0.25);

        robot.sensorArm.setPosition(1);

        robot.wobbleGoalL.setPosition(0);
        robot.wobbleGoalR.setPosition(0);


        robot.moveInches(robot.STRAFELEFT, 32 * STRAFECORRECTION, autoPower);
        while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
            sleep(1000);
        }


        if (robot.autoColor.red() < LOWER_LIMIT)
        {
            robot.sensorArm.setPosition(0);

            robot.moveInches(robot.STRAFELEFT, 28 * STRAFECORRECTION, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.moveInches(robot.FORWARD, 6, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.wobbleGoalL.setPosition(1);
            robot.wobbleGoalR.setPosition(1);

            sleep (1000);

            robot.moveInches(robot.STRAFERIGHT, 4 * STRAFECORRECTION, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.moveInches(robot.BACKWARD, 27, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.resetEncoders();
            robot.setRotateLeft();
            robot.setDrivePower(autoPower);
            sleep (SLEEP90TURN);
            robot.setDrivePower(0);

            outputMotorRamp();

            robot.moveInches(robot.FORWARD, 18, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }


        }else if (robot.autoColor.red() < UPPER_LIMIT){
            robot.sensorArm.setPosition(0);

            robot.moveInches(robot.STRAFELEFT, 52 * STRAFECORRECTION, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.moveInches(robot.BACKWARD, 18, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.wobbleGoalL.setPosition(1);
            robot.wobbleGoalR.setPosition(1);

            sleep (1000);

            robot.moveInches(robot.STRAFERIGHT, 29 * STRAFECORRECTION, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.moveInches(robot.BACKWARD, 4, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.resetEncoders();
            robot.setRotateLeft();
            robot.setDrivePower(autoPower);
            sleep (SLEEP90TURN);
            robot.setDrivePower(0);

            outputMotorRamp();

            robot.moveInches(robot.FORWARD, 18, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }


        }else{
            robot.sensorArm.setPosition(0);

            robot.moveInches(robot.STRAFELEFT, 76 * STRAFECORRECTION, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.moveInches(robot.FORWARD, 6, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.wobbleGoalL.setPosition(1);
            robot.wobbleGoalR.setPosition(1);

            sleep (1000);

            robot.moveInches(robot.STRAFERIGHT, 537 * STRAFECORRECTION, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.moveInches(robot.BACKWARD, 26, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

            robot.resetEncoders();
            robot.setRotateLeft();
            robot.setDrivePower(autoPower);
            sleep (SLEEP90TURN);
            robot.setDrivePower(0);

            outputMotorRamp();

            robot.moveInches(robot.FORWARD, 18, autoPower);
            while (Math.abs(robot.leftBackMotor.getCurrentPosition() - robot.leftBackMotor.getTargetPosition()) > 10){
                sleep(10);
            }

        }

        robot.pulleyMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.pulleyMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.pulleyMotor.setTargetPosition(280);
        robot.pulleyMotor.setPower(.25);

        while (Math.abs(robot.pulleyMotor.getCurrentPosition() - robot.pulleyMotor.getTargetPosition()) > 5){
            sleep(10);
        }

        robot.pulleyMotor.setPower(0);

//        while(robot.rightFrontMotor.getCurrentPosition() < 6300 && opModeIsActive())
//        {
//            robot.setDrivePower(.25);
////            robot.leftBackMotor.setPower(-1);
////            robot.leftFrontMotor.setPower(-1);
////            robot.rightBackMotor.setPower(1);
////            robot.rightFrontMotor.setPower(1);
//        }
//
//        robot.setDrivePower(0);
//
//        sleep(1000);
//
//        robot.resetEncoders();
//        robot.setStrafeRight();
//
//        while(robot.rightFrontMotor.getCurrentPosition() < 1800 && opModeIsActive())
//        {
//            robot.setDrivePower(.25);
//        }

        robot.setDrivePower(0);

        robot.resetEncoders();

        while (opModeIsActive()) {
            telemetry.addData("LeftFrontMotor Current Encoder Value: ", robot.leftFrontMotor.getCurrentPosition());
            telemetry.addData("Red  ", robot.autoColor.red());
            telemetry.update();
        }
    }
}

