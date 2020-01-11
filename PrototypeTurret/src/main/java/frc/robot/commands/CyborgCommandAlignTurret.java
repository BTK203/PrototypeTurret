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
import frc.robot.util.MiniPID;
import frc.robot.util.Util;

/**
 * Command that aligns the turret to the target
 */
public class CyborgCommandAlignTurret extends CommandBase {
  private SubsystemTurret turret;
  private SubsystemReceiver kiwiLight;
  private MiniPID turntablePID;

  /**
   * Creates a new CyborgCommandAlignTurret.
   */
  public CyborgCommandAlignTurret(SubsystemTurret turret, SubsystemReceiver kiwiLight) {
    this.turret = turret;
    this.kiwiLight = kiwiLight;
    addRequirements(this.turret);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //init turntable PID
    turntablePID = new MiniPID(
      Util.getAndSetDouble("Turntable kP", Constants.DEFAULT_TURNTABLE_kP), 
      Util.getAndSetDouble("Turntable kI", Constants.DEFAULT_TURNTABLE_kI),
      Util.getAndSetDouble("Turntable kD", Constants.DEFAULT_TURNTABLE_kD)
    );

    turntablePID.setOutputLimits(
      -1 * Util.getAndSetDouble("Turntable Output", Constants.DEFAULT_TURNTABLE_OUTPUT), //minimum
      Util.getAndSetDouble("Turntable Output", Constants.DEFAULT_TURNTABLE_OUTPUT)       //maximum
    );

    turntablePID.setSetpoint(0);

    SmartDashboard.putBoolean("Aligning", true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double horizontalVisionValue = kiwiLight.getHorizontalAngleToTarget();
    if(horizontalVisionValue != 180) {
      double horizontalDriveValue = turntablePID.getOutput(horizontalVisionValue);
      turret.driveYaw(horizontalDriveValue);
      
      SmartDashboard.putNumber("horizontal drive", horizontalDriveValue);
    }
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
