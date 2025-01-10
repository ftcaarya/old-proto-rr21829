package org.firstinspires.ftc.teamcode.extraneous;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "PID Loop Testing", group = "exercises")
public class PIDTuningClass extends OpMode {

    private DcMotor elevator, horizontalRight, horizontalLeft;
    private PIDController elevatorController, rightHorController, leftHorController;

    public static double pv = 0, iv = 0, dv = 0;
    public static double ph = 0, ih = 0, dh = 0;
    public static double fv = 0, fh = 0;

    public static int vertTarget = 0;
    public static int horTarget = 0;


    private final double ticks_in_degrees = 576.7/180;

    @Override
    public void init() {
        elevatorController = new PIDController(pv, iv, dv);
        rightHorController = new PIDController(ph, ih, dh);
        leftHorController = new PIDController(ph, ih, dh);

        horizontalRight = hardwareMap.get(DcMotorEx.class, "horizontal 1");
        horizontalLeft = hardwareMap.get(DcMotorEx.class, "horizontal 2");

        horizontalRight.setDirection(DcMotorSimple.Direction.REVERSE);

        elevator = hardwareMap.get(DcMotor.class, "elevator");
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        elevatorController.setPID(pv,iv,dv);
        rightHorController.setPID(ph,ih,dh);
        leftHorController.setPID(ph,ih,dh);

        int elevatorPos = elevator.getCurrentPosition();
        int rightHorPos = horizontalLeft.getCurrentPosition();
        int leftHorPos = horizontalRight.getCurrentPosition();

        double elevatorPid = elevatorController.calculate(elevatorPos, vertTarget);
        double rightHorPid = rightHorController.calculate(rightHorPos, horTarget);
        double leftHorPid = leftHorController.calculate(leftHorPos, horTarget);

        double vertff = Math.cos(Math.toRadians(vertTarget / ticks_in_degrees)) * fv;

        double elevatorPower = elevatorPid + vertff;

        elevator.setPower(elevatorPower);

        horizontalRight.setPower(rightHorPid);
        horizontalLeft.setPower(leftHorPid);

        telemetry.addData("elevator current pos", elevatorPos);
        telemetry.addData("elevator target pos", vertTarget);
        telemetry.addData("Right horizontal pos", rightHorPos);
        telemetry.addData("Left horizontal pos", leftHorPos);
        telemetry.addData("Horizontal Target", horTarget);
        telemetry.update();
    }
}
