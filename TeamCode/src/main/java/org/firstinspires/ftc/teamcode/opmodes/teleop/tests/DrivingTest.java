package org.firstinspires.ftc.teamcode.opmodes.teleop.tests;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.math.Pose;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;

/**
 * Simple robot-centric mecanum driving with live Pinpoint odometry on telemetry.
 * Uses the Pedro drivetrain and localizer configs directly, so it works before the
 * Follower is built in {@link Constants#create}.
 * Left stick = drive / strafe. Right stick X = turn. A = reset pose to (0, 0, 0).
 */
@TeleOp(name = "DrivingTest", group = "Test")
public class DrivingTest extends LinearOpMode {

    /**
     * Sets up the drivetrain and odometry, then drives from gamepad 1 until the OpMode stops.
     */
    @Override
    public void runOpMode() {
        Mecanum drivetrain = new Mecanum(hardwareMap, Constants.drivetrainConfig);
        PinpointLocalizer localizer = new PinpointLocalizer(hardwareMap, Constants.localizerConfig);

        localizer.setPose(Pose.zero());

        telemetry.addLine("Ready. Left stick = drive, right stick = turn, A = reset pose.");
        telemetry.update();

        waitForStart();

        // Start from zero so the pose reads relative to where the robot sat at start
        localizer.setPose(Pose.zero());

        while (opModeIsActive()) {
            if (gamepad1.a) {
                localizer.setPose(Pose.zero());
            }

            // Stick Y is negative when pushed forward, and stick X is positive to the right,
            // but Pedro wants forward, left, and counter-clockwise to be positive
            double forwardPower = -gamepad1.left_stick_y;
            double strafeLeftPower = -gamepad1.left_stick_x;
            double turnLeftPower = -gamepad1.right_stick_x;

            drivetrain.drive(new DrivePowers(forwardPower, strafeLeftPower, turnLeftPower), true);
            localizer.update();

            Pose currentPose = localizer.pose();
            telemetry.addData("X (in)", "%.2f", currentPose.x());
            telemetry.addData("Y (in)", "%.2f", currentPose.y());
            telemetry.addData("Heading (deg)", "%.1f", Math.toDegrees(currentPose.heading()));
            telemetry.update();
        }

        drivetrain.stop();
    }
}
