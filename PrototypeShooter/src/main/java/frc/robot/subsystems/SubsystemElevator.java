/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Util;
import frc.robot.util.Xbox;

public class SubsystemElevator extends SubsystemBase {
  private TalonSRX pitch;

  /**
   * Creates a new SubsystemElevator.
   */
  public SubsystemElevator() {
    pitch = new TalonSRX(Constants.PITCH_ID);

    pitch.setNeutralMode(NeutralMode.Brake);
    pitch.setInverted(Constants.ELEVATOR_INVERT);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double encoderPosition = pitch.getSensorCollection().getPulseWidthPosition();
    double encoderVelocity = pitch.getSensorCollection().getPulseWidthVelocity();
    SmartDashboard.putNumber("Pitch Encoder", encoderPosition);
    SmartDashboard.putNumber("Pitch Velocity", encoderVelocity);
    
    SmartDashboard.putNumber("Pitch Amps", pitch.getStatorCurrent());
    SmartDashboard.putNumber("Pitch Supply", pitch.getSupplyCurrent());

    
  }

  public void driveWithController(Joystick joy) {
    double drive = Xbox.RIGHT_Y(joy);
    drive *= Util.getAndSetDouble("Pitch Inhibitor", 1);
    pitch.set(ControlMode.PercentOutput, drive);
  }
}
