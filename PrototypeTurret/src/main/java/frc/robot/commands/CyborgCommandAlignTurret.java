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
  private MiniPID pitchPID;

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
      Util.getAndSetDouble("Turntable kD", Constants.DEFAULT_TURNTABLE_kD),
      Util.getAndSetDouble("Turntable kF", Constants.DEFAULT_TURNTABLE_kF)
    );

    pitchPID = new MiniPID(
      Util.getAndSetDouble("Pitch kP", Constants.DEFAULT_PITCH_kP),
      Util.getAndSetDouble("Pitch kI", Constants.DEFAULT_PITCH_kI),
      Util.getAndSetDouble("Pitch kD", Constants.DEFAULT_PITCH_kD),
      Util.getAndSetDouble("Pitch kF", Constants.DEFAULT_PITCH_kF)
    );

    turntablePID.setOutputLimits(
      -1 * Util.getAndSetDouble("Turntable Output", Constants.DEFAULT_TURNTABLE_OUTPUT), //minimum
      Util.getAndSetDouble("Turntable Output", Constants.DEFAULT_TURNTABLE_OUTPUT)       //maximum
    );

    pitchPID.setOutputLimits(
      -1 * Util.getAndSetDouble("Pitch Output", Constants.DEFAULT_PITCH_OUTPUT), //min
      Util.getAndSetDouble("Pitch Output", Constants.DEFAULT_PITCH_OUTPUT)  //max
    );

    turntablePID.setSetpoint(0);
    pitchPID.setSetpoint(0);

    SmartDashboard.putBoolean("Aligning", true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //HORIZONTAL PID
    double horizontalVisionValue = kiwiLight.getHorizontalAngleToTarget();

    turntablePID.setP(Util.getAndSetDouble("Turntable kP", Constants.DEFAULT_TURNTABLE_kP));
    turntablePID.setI(Util.getAndSetDouble("Turntable kI", Constants.DEFAULT_TURNTABLE_kI));
    turntablePID.setD(Util.getAndSetDouble("Turntable kD", Constants.DEFAULT_TURNTABLE_kD));
    turntablePID.setF(Util.getAndSetDouble("Turntable kF", Constants.DEFAULT_TURNTABLE_kF));

    if(Math.abs(horizontalVisionValue) != 180) {
      double horizontalDriveValue = turntablePID.getOutput(horizontalVisionValue);
      turret.driveYaw(horizontalDriveValue);
      
      SmartDashboard.putNumber("horizontal drive", horizontalDriveValue);
    } else {
      turret.driveYaw(0);
    }

    //VERTICAL PID
    double verticalVisionValue = kiwiLight.getVerticalAngleToTarget() * -1;
    
    pitchPID.setP(Util.getAndSetDouble("Pitch kP", Constants.DEFAULT_PITCH_kP));
    pitchPID.setI(Util.getAndSetDouble("Pitch kI", Constants.DEFAULT_PITCH_kI));
    pitchPID.setD(Util.getAndSetDouble("Pitch kD", Constants.DEFAULT_PITCH_kD));
    pitchPID.setF(Util.getAndSetDouble("Pitch kF", Constants.DEFAULT_PITCH_kF));

    if(Math.abs(verticalVisionValue) != 180) {
      double verticalDriveValue = pitchPID.getOutput(verticalVisionValue);
      turret.drivePitch(verticalDriveValue);

      SmartDashboard.putNumber("Vertical drive", verticalDriveValue);
    } else {
      turret.drivePitch(0);
    }

    boolean horizontalStable = Math.abs(horizontalVisionValue) < 2;
    boolean verticalStable = Math.abs(verticalVisionValue) < 2;
    boolean turretStable = horizontalStable && verticalStable;
    SmartDashboard.putBoolean("Horizontal Stable", horizontalStable);
    SmartDashboard.putBoolean("Vertical Stable", verticalStable);
    SmartDashboard.putBoolean("Stable", turretStable);
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
