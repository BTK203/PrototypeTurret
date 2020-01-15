/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.SubsystemFlywheel;
import frc.robot.util.MiniPID;
import frc.robot.util.Util;

public class CyborgCommandDriveVelocity extends CommandBase {
  private SubsystemFlywheel flywheel;
  private MiniPID pid;

  /**
   * Creates a new CyborgCommandDriveVelocity.
   */
  public CyborgCommandDriveVelocity(SubsystemFlywheel flywheel) {
    this.flywheel = flywheel;
    addRequirements(flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double p = Util.getAndSetDouble("velocity kP", Constants.DEFAULT_P);
    double i = Util.getAndSetDouble("velocity kI", Constants.DEFAULT_I);
    double d = Util.getAndSetDouble("velocity kD", Constants.DEFAULT_D);
    double f = Util.getAndSetDouble("velocity kF", Constants.DEFAULT_F);

    double lowerLimit = Util.getAndSetDouble("lower limit", Constants.DEFAULT_LOWER_LIMIT);
    double upperLimit = Util.getAndSetDouble("upper limit", Constants.DEFAULT_UPPER_LIMIT);

    this.pid = new MiniPID(p, i, d, f);
    pid.setOutputLimits(lowerLimit, upperLimit);
    pid.setOutputRampRate(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double drive = pid.getOutput(flywheel.getVelocity());
    flywheel.drivePercent(drive);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
