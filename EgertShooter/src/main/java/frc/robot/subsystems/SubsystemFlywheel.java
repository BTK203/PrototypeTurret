/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubsystemFlywheel extends SubsystemBase {
  private CANSparkMax flywheel;

  /**
   * Creates a new SubsystemFlywheel.
   */
  public SubsystemFlywheel() {
    flywheel = new CANSparkMax(Constants.FLYWHEEL_ID, MotorType.kBrushless);
    flywheel.setInverted(Constants.FLYWHEEL_INVERT);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("FW Velocity", flywheel.getEncoder().getVelocity());
  }

  public void setVelocity(double rpm) {
    flywheel.getPIDController().setReference(rpm, ControlType.kVelocity);
  }

  public void stop() {
    flywheel.set(0);
  }

  public void setPIDF(double p, double i, double d, double f, double izone) {
    flywheel.getPIDController().setP(p);
    flywheel.getPIDController().setI(i);
    flywheel.getPIDController().setD(d);
    flywheel.getPIDController().setFF(f);
    flywheel.getPIDController().setIZone(izone);

    flywheel.getPIDController().setOutputRange(-1 * Constants.FLYWHEEL_INHIBITOR, Constants.FLYWHEEL_INHIBITOR);
  }
}
