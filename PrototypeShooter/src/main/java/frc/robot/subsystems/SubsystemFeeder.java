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

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubsystemFeeder extends SubsystemBase {
  private TalonSRX feeder;

  /**
   * Creates a new SubsystemFeeder.
   */
  public SubsystemFeeder() {
    feeder = new TalonSRX(Constants.FEEDER_ID);
    feeder.setNeutralMode(NeutralMode.Brake);
    feeder.setInverted(Constants.FEEDER_INVERT);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drivePercent(double percent) {
    feeder.set(ControlMode.PercentOutput, percent);
  }
}
