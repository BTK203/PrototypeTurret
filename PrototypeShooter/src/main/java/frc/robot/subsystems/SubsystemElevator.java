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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Xbox;

public class SubsystemElevator extends SubsystemBase {
  private TalonSRX elevator;

  /**
   * Creates a new SubsystemElevator.
   */
  public SubsystemElevator() {
    elevator = new TalonSRX(Constants.ELEVATOR_ID);

    elevator.setNeutralMode(NeutralMode.Brake);
    elevator.setInverted(Constants.ELEVATOR_INVERT);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void driveWithController(Joystick joy) {
    double drive = Xbox.RIGHT_Y(joy);
    elevator.set(ControlMode.PercentOutput, drive);
  }
}
