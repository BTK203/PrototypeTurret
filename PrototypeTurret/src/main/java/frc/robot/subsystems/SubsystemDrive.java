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
import frc.robot.commands.ManualCommandDrive;
import frc.robot.util.Xbox;

/**
 * Add your docs here.
 */
public class SubsystemDrive extends Subsystem {

  private TalonSRX
    rightMaster,
    rightSlave,
    leftMaster,
    leftSlave;


  public SubsystemDrive() {
    rightMaster = new TalonSRX(Constants.DRIVE_RIGHT_MASTER_ID);
    rightSlave  = new TalonSRX(Constants.DRIVE_RIGHT_SLAVE_ID);
    leftMaster  = new TalonSRX(Constants.DRIVE_LEFT_MASTER_ID);
    leftSlave   = new TalonSRX(Constants.DRIVE_LEFT_SLAVE_ID);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualCommandDrive());
  }

  public void DriveManually(Joystick joy) {
    double throttle = Xbox.RT(joy) - Xbox.LT(joy);
    double steering = Xbox.LEFT_X(joy);

    double rightDrive = throttle + steering;
    double leftDrive  = throttle - steering;

    //make sure right and left drive are between -1 and 1
    rightDrive = (rightDrive > 1 ? 1 : (rightDrive < -1 ? -1 : rightDrive));
    leftDrive = (leftDrive > 1 ? 1 : (leftDrive < -1 ? -1 : leftDrive));

    //set motors
    rightMaster.set(ControlMode.PercentOutput, rightDrive);
    rightSlave.set(ControlMode.PercentOutput, rightDrive);
    leftMaster.set(ControlMode.PercentOutput, leftDrive);
    leftSlave.set(ControlMode.PercentOutput, leftDrive);
  }
}
