/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SubsystemReceiver;
import frc.robot.subsystems.SubsystemTurret;
import frc.robot.util.Util;

public class CyborgCommandAlignTurret extends CommandBase {
  private SubsystemTurret turret;
  private SubsystemReceiver kiwilight;
  private boolean
    endable,
    yawAligned,
    pitchAligned;

  private long 
    lastAlignedTime,
    alignedTime;

  /**
   * Creates a new CyborgCommandAlignTurret.
   */
  public CyborgCommandAlignTurret(SubsystemTurret turret, SubsystemReceiver kiwilight, boolean endable) {
    this.turret = turret;
    this.kiwilight = kiwilight;
    this.endable = endable;
    addRequirements(this.turret);
  }

  public CyborgCommandAlignTurret(SubsystemTurret turret, SubsystemReceiver kiwilight) {
    this(turret, kiwilight, false);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //set yaw pid
    double yawkP = Util.getAndSetDouble("Yaw Position kP", 0.004);
    double yawkI = Util.getAndSetDouble("Yaw Position kI", 0.001);
    double yawIZone = Util.getAndSetDouble("Yaw Position IZone", 100000);
    double yawkD = Util.getAndSetDouble("Yaw Position KD", 0);
    double yawkF = Util.getAndSetDouble("Yaw Position KF", 0);

    turret.setYawPIDF(yawkP, yawkI, yawkD, yawkF, (int) yawIZone);

    //pitch pid
    double pitchkP = Util.getAndSetDouble("Pitch Position kP", 0);
    double pitchkI = Util.getAndSetDouble("Pitch Position kI", 0);
    double pitchIZone = Util.getAndSetDouble("Pitch Position IZone", 75);
    double pitchkD = Util.getAndSetDouble("Pitch Position kD", 0);
    double pitchkF = Util.getAndSetDouble("Pitch Position kF", 0);

    turret.setPitchPIDF(pitchkP, pitchkI, pitchkD, pitchkF, (int) pitchIZone);

    SmartDashboard.putBoolean("Aligning", true);
    lastAlignedTime = Long.MAX_VALUE;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double horizontalAngle = kiwilight.getHorizontalAngleToTarget() * -1;
    horizontalAngle *= Util.getAndSetDouble("Vision multiplier", 1);

    double horizontalPosition = turret.getYawPosition();
    double horizontalTicks = turret.getYawTicks();
    double targetDistance = kiwilight.getDistanceToTarget();

    //horizontal angle
    if(kiwilight.targetSpotted()) {
      double horizontalTicksPerDegree = horizontalTicks / (double) Constants.YAW_DEGREES;
      double horizontalTicksToTurn = horizontalAngle * horizontalTicksPerDegree;

      SmartDashboard.putNumber("H Ticks To Turn", horizontalTicksToTurn);
      SmartDashboard.putNumber("Yaw Ticks To Turn", horizontalTicksToTurn);

      double newTargetPosition = horizontalPosition + horizontalTicksToTurn;
      turret.setYawPosition((int) newTargetPosition);

      yawAligned = Math.abs(newTargetPosition - horizontalPosition) < Constants.YAW_ALLOWABLE_ERROR;
      SmartDashboard.putBoolean("Yaw Aligned", yawAligned);
    } else {
      //disable motors
      turret.setYawOutput(0);
    }

    //vertical angle
    if(kiwilight.targetSpotted()) {
      double newPitchPosition = turret.getPitchPosition();
      if(targetDistance > 5) {
        //use the cool parabola equation to calculate the pitch position
        //equation: f(x) = 0.006851x^2 - 2.654x - 447.8 | where: x is the distance kiwilight reports and f returns the pitch position.
        double ax2 = 0.006851 * Math.pow(targetDistance, 2);
        double bx  = -2.654 * targetDistance;
        double c   = -375;

        newPitchPosition = ax2 + bx + c;
        newPitchPosition += Util.getAndSetDouble("Align Degree Boost", 50);

        SmartDashboard.putNumber("Pitch Error", turret.getPitchPosition() - newPitchPosition);
      } else {
        //use the slightly less cool linear equation to calculate the pitch position
        //equation: f(x) = -9.512x - 65.85 | where: x is the distance kiwilight reports and f returns the pitch position.

        newPitchPosition = (-9.512 * targetDistance) -65.85;
      }

      turret.setPitchPosition((int) newPitchPosition);

      pitchAligned = Math.abs(newPitchPosition - turret.getPitchPosition()) < Constants.PITCH_ALLOWABLE_ERROR;
      SmartDashboard.putBoolean("Pitch Aligned", pitchAligned);
    }

    if(stable()) {
      long timeSinceLastFrame = System.currentTimeMillis() - lastAlignedTime;
      alignedTime += timeSinceLastFrame;
      lastAlignedTime = System.currentTimeMillis();
    } else {
      alignedTime = 0;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.setYawOutput(0);
    turret.setPitchOutput(0);

    SmartDashboard.putBoolean("Aligning", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(endable) {
      boolean stableForTime = alignedTime > 250;
      return stable() && stableForTime;
    } else {
      return false;
    }
  }

  private boolean stable() {
    return yawAligned && pitchAligned;
  }
}
