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

/**
 * Aligns the turret using Talon PID
 */
public class CyborgCommandSmartAlignTurret extends CommandBase {
  private SubsystemTurret turret;
  private SubsystemReceiver receiver;

  /**
   * Creates a new CyborgCommandSmartAlignTurret.
   */
  public CyborgCommandSmartAlignTurret(SubsystemTurret turret, SubsystemReceiver receiver) {
    this.turret = turret;
    this.receiver = receiver;
    addRequirements(this.turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double kP = Util.getAndSetDouble("Turntable kP", Constants.DEFAULT_TURNTABLE_kP);
    double kI = Util.getAndSetDouble("Turntable kI", Constants.DEFAULT_TURNTABLE_kI);
    double kD = Util.getAndSetDouble("Turntable kD", Constants.DEFAULT_TURNTABLE_kD);
    double kF = Util.getAndSetDouble("Turntable kF", Constants.DEFAULT_TURNTABLE_kF);

    double outputLimit = Util.getAndSetDouble("Turntable Output", Constants.DEFAULT_TURNTABLE_OUTPUT);

    this.turret.setYawPIDF(kP, kI, kD, kF, outputLimit, -1 * outputLimit);

    SmartDashboard.putBoolean("Aligning", true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double horizontalAngleToTarget = this.receiver.getHorizontalAngleToTarget() * -1;

    if(Math.abs(horizontalAngleToTarget) == 180) {
      return;
    }

    double yawTicksPerRotation = Util.getAndSetDouble("Turntable Ticks", Constants.DEFAULT_TURNTABLE_TICKS);

    double rotations = horizontalAngleToTarget / (double) 360;
    double newTargetPosition = (yawTicksPerRotation * rotations) + this.turret.getTurntablePosition();

    SmartDashboard.putNumber("New Target Position", newTargetPosition);

    this.turret.setTargetTurntablePosition((int) newTargetPosition);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("Aligning", false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

