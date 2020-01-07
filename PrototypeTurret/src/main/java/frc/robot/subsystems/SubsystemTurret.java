/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Constants;
import frc.robot.commands.ManualCommandDriveTurret;
import frc.robot.util.Xbox;

/**
 * Add your docs here.
 */
public class SubsystemTurret extends Subsystem {
  private TalonSRX
    pitch,
    yaw;

  public SubsystemTurret() {
    pitch = new TalonSRX(Constants.TURRET_PITCH_ID);
    yaw = new TalonSRX(Constants.TURRET_YAW_ID);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualCommandDriveTurret());
  }

  public void DriveManually(Joystick joy) {
    double yawDrive = Xbox.RIGHT_X(joy);
    double pitchDrive = Xbox.RIGHT_Y(joy);

    //do things with soft limits here for yaw, hard limits for pitch

    //set motors
    yaw.set(ControlMode.PercentOutput, yawDrive);
    pitch.set(ControlMode.PercentOutput, pitchDrive);
  }

  public double getTurntableEncoderPosition() {
    return yaw.getSensorCollection().getQuadraturePosition();
  }

  public double getElevatorEncoderPosition() {
    return pitch.getSensorCollection().getQuadraturePosition();
  }

  public void zeroTurntableEncoder() {
    yaw.getSensorCollection().setQuadraturePosition(0, 0);
  }

  public void  zeroElevatorEncoder() {
    pitch.getSensorCollection().setQuadraturePosition(0, 0);
  }
}
