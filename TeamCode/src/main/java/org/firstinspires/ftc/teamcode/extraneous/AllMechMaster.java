package org.firstinspires.ftc.teamcode.extraneous;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class AllMechMaster {
    // declare all the hardware

    //slide motors
    public static DcMotor elevator, horizontalRight, horizontalLeft;
    //drivetrain motors
    public static DcMotor frontLeft, frontRight, rearLeft, rearRight;

    //declare the servos right here

    //declare the PID Controllers
    PIDController elevatorController, rightHorController, leftHorController;

    // declare the PID constants
    public static double pv = 0, iv = 0, dv = 0;
    public static double ph = 0, ih = 0, dh = 0;
    public static double fv = 0, fh = 0;

    public volatile int vertTarget = 0;
    public static int horTarget = 0;

    private final double ticks_in_degrees = 576.7/180;

    public AllMechMaster(HardwareMap hardwareMap) {
        // initialize all the motors
        horizontalRight = hardwareMap.get(DcMotorEx.class, "horizontal 1");
        horizontalLeft = hardwareMap.get(DcMotorEx.class, "horizontal 2");

        horizontalRight.setDirection(DcMotorSimple.Direction.REVERSE);

        elevator = hardwareMap.get(DcMotor.class, "elevator");
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft = hardwareMap.get(DcMotorEx.class, "left front");
        rearLeft = hardwareMap.get(DcMotorEx.class, "left rear");
        rearRight = hardwareMap.get(DcMotorEx.class, "right rear");
        frontRight = hardwareMap.get(DcMotorEx.class, "right front");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        rearLeft.setDirection(DcMotor.Direction.REVERSE);



        // initialize all the PID controllers
        elevatorController = new PIDController(pv, iv, dv);
        rightHorController = new PIDController(ph, ih, dh);
        leftHorController = new PIDController(ph, ih, dh);

    }

    public Action setElevatorTarget(int target) {
        return new InstantAction(() -> vertTarget = target);
    }

    public Action setHorizontalTarget(int target) {
        return new InstantAction(() -> horTarget = target);
    }


}
