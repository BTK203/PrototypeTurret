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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Util;
import frc.robot.util.Xbox;

public class SubsystemFlywheel extends SubsystemBase {
  private CANSparkMax flywheel;

  /**
   * Creates a new SubsystemFlywheel.
   */
  public SubsystemFlywheel() {
    flywheel = new CANSparkMax(Constants.FLYWHEEL_ID, MotorType.kBrushless);
    flywheel.setIdleMode(IdleMode.kBrake);
    flywheel.setInverted(Constants.FLYWHEEL_INVERT);
    flywheel.setSmartCurrentLimit(Constants.FLYWHEEL_AMP_LIMIT);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    double flywheelRPM = Math.abs(flywheel.getEncoder().getVelocity() * Constants.FLYWHEEL_GEAR_RATIO);
    double targetRPM = Util.getAndSetDouble("Velocity Target", 1000);

    SmartDashboard.putNumber("Velocity", flywheel.getEncoder().getVelocity());
    SmartDashboard.putNumber("Flywheel RPM", flywheelRPM);

    SmartDashboard.putBoolean(
      "RPM Limit Hit",
      flywheelRPM > Util.getAndSetDouble("RPM Limit", 1000)
    );

    double flywheelError = Math.abs(flywheelRPM - Math.abs(targetRPM));
    SmartDashboard.putNumber("Flywheel RPM Error", flywheelError);
    SmartDashboard.putBoolean("RPM Stable", flywheelError <= 50);

    SmartDashboard.putNumber("Flywheel Amps", flywheel.getOutputCurrent());
    SmartDashboard.putNumber("Flywheel Drive", Math.abs(flywheel.getAppliedOutput() * 100));
  }

  public void driveManually(Joystick joy) {
    double drive = Xbox.RIGHT_X(joy);
    drivePercent(drive);
  }

  public void drivePercent(double output) {

    //rpm limit
    double rpmLimit = Util.getAndSetDouble("RPM Limit", 1000) / Constants.FLYWHEEL_GEAR_RATIO;
    if(Math.abs(flywheel.getEncoder().getVelocity()) > rpmLimit) {
      flywheel.set(0);
      DriverStation.reportWarning("HIT RPM LIMIT", false);
    } else {
      flywheel.set(output);
    }
  }

  public void drivePercent() {
    double percent = Util.getAndSetDouble("Percent Output", 0);
    drivePercent(percent);
  }

  public double getVelocity() {
    return flywheel.getEncoder().getVelocity();
  }

  public void setPIDF(double p, double i, double d, double f, double lowLimit, double highLimit, double izone) {
    flywheel.getPIDController().setP(p, 0);
    flywheel.getPIDController().setI(i, 0);
    flywheel.getPIDController().setD(d, 0);
    flywheel.getPIDController().setFF(f, 0);
    flywheel.getPIDController().setOutputRange(lowLimit, highLimit);

    flywheel.getPIDController().setIZone(izone, 0);
  }

  public void setVelocity(double velocity) {
    flywheel.getPIDController().setReference(velocity, ControlType.kVelocity);
  }
}
