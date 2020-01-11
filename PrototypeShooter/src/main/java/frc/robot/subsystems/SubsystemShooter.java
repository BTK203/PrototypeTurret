/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Util;
import frc.robot.util.Xbox;

public class SubsystemShooter extends SubsystemBase {
  private CANSparkMax shooter;

  /**
   * Creates a new SubsystemShooter.
   */
  public SubsystemShooter() {
    shooter = new CANSparkMax(Constants.SHOOTER_ID, MotorType.kBrushless);

    configureMotors();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Shooter Velocity", shooter.getEncoder().getVelocity());
  }

  public void driveManually(Joystick joy) {
    double drive = Xbox.RT(joy);
    shooter.set(drive);
  }

  public void setPIDOutput() {
    
  }

  public void setTargetVelocity(double velocity) {
    //set the sensor thingy
    shooter.getPIDController().setReference(velocity, ControlType.kSmartVelocity);
  }

  private void configureMotors() {
    shooter.setIdleMode(IdleMode.kBrake);

    shooter.getPIDController().setOutputRange(-1, 1);
    
    double kP = Util.getAndSetDouble("Velocity kP", Constants.VELOCITY_kP);
    double kI = Util.getAndSetDouble("Velocity kI", Constants.VELOCITY_kI);
    double kD = Util.getAndSetDouble("Velocity kD", Constants.VELOCITY_kD);

    shooter.getPIDController().setP(kP);
    shooter.getPIDController().setI(kI);
    shooter.getPIDController().setD(kD);
  }
}
