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

public class SubsystemDrive extends SubsystemBase {
  
  private TalonSRX 
    rightMaster,
    rightSlave,
    leftMaster,
    leftSlave;

  /**
   * Creates a new SubsystemDrive.
   */
  public SubsystemDrive() {
    rightMaster = new TalonSRX(Constants.DRIVE_RIGHT_MASTER_ID);
    rightSlave  = new TalonSRX(Constants.DRIVE_RIGHT_SLAVE_ID);
    leftMaster  = new TalonSRX(Constants.DRIVE_LEFT_MASTER_ID);
    leftSlave   = new TalonSRX(Constants.DRIVE_LEFT_SLAVE_ID);

    configureTalons();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(Joystick joy) {
    double throttle = Xbox.RT(joy) - Xbox.LT(joy);
    double steering = Xbox.LEFT_X(joy);

    double right = throttle + steering;
    double left  = throttle - steering;

    right = (right < -1 ? -1 : (right > 1 ? 1 : right));
    left = (left < -1 ? -1 : (left > 1 ? 1 : left));

    rightMaster.set(ControlMode.PercentOutput, right);
    rightSlave.set(ControlMode.PercentOutput, right);
    leftMaster.set(ControlMode.PercentOutput, left);
    leftSlave.set(ControlMode.PercentOutput, left);
  }

  private void configureTalons() {
    rightMaster.setInverted(Constants.DRIVE_RIGHT_MASTER_INVERT);
    rightSlave.setInverted(Constants.DRIVE_RIGHT_SLAVE_INVERT);
    leftMaster.setInverted(Constants.DRIVE_LEFT_MASTER_INVERT);
    leftSlave.setInverted(Constants.DRIVE_LEFT_SLAVE_INVERT);

    rightMaster.setNeutralMode(NeutralMode.Brake);
    rightSlave.setNeutralMode(NeutralMode.Brake);
    leftMaster.setNeutralMode(NeutralMode.Brake);
    leftSlave.setNeutralMode(NeutralMode.Brake);
  }
}
