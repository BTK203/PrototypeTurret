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
import frc.robot.util.Util;

public class ToggleCommandDriveFlywheel extends CommandBase {
  SubsystemFlywheel subsystem;

  /**
   * Creates a new ToggleCommandDriveFlywheel.
   */
  public ToggleCommandDriveFlywheel(SubsystemFlywheel sub) {
    subsystem = sub;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double driveValue = Util.getAndSetDouble("Flywheel Drive Value", Constants.DEFAULT_SHOOTER_DRIVE_VALUE);
    subsystem.setPercentOutput(driveValue);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    subsystem.setPercentOutput(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
