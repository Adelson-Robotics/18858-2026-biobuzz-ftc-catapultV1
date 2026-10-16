# CLAUDE.md

FTC BIOBUZZ 2026 catapult robot. See [PROJECT_OVERVIEW.md](PROJECT_OVERVIEW.md) for the robot's hardware and hardware-map names.

## Reference code

If you're ever unsure how to do something, check the freshStart project for an example: `C:\Users\ARC18858\Documents\Team-Code\2026-2027-BIOBUZZ\freshStart` (`../freshStart` from this repo).

## Code style

- Use long, easy-to-read variable names: `poseFactory`, not `p`.
- Give every function a Javadoc comment.
- Comment any line that's hard to understand.

## Architecture

- **Every subsystem is a state machine:** a `State` enum plus a `switch` in `update()` that sets the power every loop.
- **Each subsystem owns its own hardware** and never talks to another subsystem.
- **`Robot.java` owns every subsystem** and is the only place that decides which states they're in. That includes the robot's overall states and which mechanisms can't run together.
- **Subsystems never read the gamepad.**

## Command-based

- **The default is always enum + switch.**
- Only use Ivy commands for:
  - steps that run in order with different "done" rules (like autonomous),
  - running two things at once, or whichever-finishes-first,
  - pieces reused across several autos.
- TeleOp and the mechanisms stay enum + switch.
- "For consistency" is not a reason to switch to commands.

## Constants

- `RobotConstants` holds only:
  - the whole gamepad button map, and
  - numbers that more than one class uses.
- A number only one class uses goes in a labeled `CONSTANTS` block at the top of that class.

## File locations

- Test autos go in `opmodes/autonomous/tests`.
