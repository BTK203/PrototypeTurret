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
    double p = Util.getAndSetDouble("Velocity kP", Constants.DEFAULT_P);
    double i = Util.getAndSetDouble("Velocity kI", Constants.DEFAULT_I);
    double d = Util.getAndSetDouble("Velocity kD", Constants.DEFAULT_D);
    double f = Util.getAndSetDouble("Velocity kF", Constants.DEFAULT_F);

    double upperOutLimit = Util.getAndSetDouble("Velocity Upper Output", Constants.DEFAULT_UPPER_LIMIT);
    double lowerOutLimit = Util.getAndSetDouble("Velocity Lower Output", Constants.DEFAULT_LOWER_LIMIT);

    double izone = Util.getAndSetDouble("IZone", 100);

    flywheel.setPIDF(p, i, d, f, lowerOutLimit, upperOutLimit, izone);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double speed = Util.getAndSetDouble("Velocity Target", Constants.DEFAULT_VELOCITY) / Constants.FLYWHEEL_GEAR_RATIO;
    flywheel.setVelocity(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    flywheel.setVelocity(0);
    flywheel.drivePercent(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
