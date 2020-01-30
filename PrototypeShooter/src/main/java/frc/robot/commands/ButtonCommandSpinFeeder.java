/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SubsystemFeeder;
import frc.robot.util.Util;

public class ButtonCommandSpinFeeder extends CommandBase {
  private SubsystemFeeder feeder;

  /**
   * Creates a new ButtonCommandSpinFeeder.
   */
  public ButtonCommandSpinFeeder(SubsystemFeeder feeder) {
    this.feeder = feeder;
    addRequirements(this.feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double value = Util.getAndSetDouble("Feeder Drive", 1);
    feeder.drivePercent(value);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    feeder.drivePercent(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
