package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Definitions
{

    DcMotor leftFrontMotor = null;
    DcMotor leftBackMotor = null;
    DcMotor rightFrontMotor = null;
    DcMotor rightBackMotor = null;
    DcMotor intakeMotor = null;
    DcMotor outputMotor = null;
    Servo outputServo = null;
    DcMotor pulleyMotor = null;
    //Servo cLiftServo = null;
    //DigitalChannel bottomLimitSwitch = null;
    //DigitalChannel topLimitSwitch = null;

    public void robotHardwareMapInit(HardwareMap Map)
    {
        //Naming Scheme for configuring robot controller app
        leftBackMotor = Map.dcMotor.get("leftBackMotor");
        leftFrontMotor = Map.dcMotor.get("leftFrontMotor");
        rightBackMotor = Map.dcMotor.get("rightBackMotor");
        rightFrontMotor = Map.dcMotor.get("rightFrontMotor");
        intakeMotor = Map.dcMotor.get("intakeMotor");
        outputMotor = Map.dcMotor.get("outputMotor");
        outputServo = Map.servo.get("outputServo");
        pulleyMotor = Map.dcMotor.get("pulleyMotor");
       // cLiftServo = Map.servo.get("cLiftServo");
       // bottomLimitSwitch = Map.digitalChannel.get("bottomLimitSwitch");
       // topLimitSwitch = Map.digitalChannel.get("topLimitSwitch");

    }

  void servoInit()
    {
        outputServo.setPosition(0);
        outputServo.setDirection(Servo.Direction.FORWARD);

        //cLiftServo.setPosition(0);
        //cLiftServo.setDirection(Servo.Direction.FORWARD);

    }

    void runWithOutEncoders()
    {
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pulleyMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outputMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    void driveWithEncoders()
    {
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
    }

    void resetEncoders()
    {
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    void setPower(double power)
    {
        leftBackMotor.setPower(power);
        leftFrontMotor.setPower(power);
        rightBackMotor.setPower(power);
        rightFrontMotor.setPower(power);
        intakeMotor.setPower(power);

    }

}
