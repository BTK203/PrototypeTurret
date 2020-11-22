/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.Xbox;

public class SubsystemTurret extends SubsystemBase {
  private TalonSRX
    yaw,
    pitch;
  
  private int
    yawTicks,
    pitchTicks;

  /**
   * Creates a new SubsystemTurret.
   */
  public SubsystemTurret() {
    yaw = new TalonSRX(Constants.YAW_ID);
    pitch = new TalonSRX(Constants.PITCH_ID);

    yaw.setInverted(Constants.YAW_INVERT);
    pitch.setInverted(Constants.PITCH_INVERT);

    yawTicks = Constants.DEFAULT_YAW_TICKS;
    pitchTicks = Constants.DEFAULT_PITCH_TICKS;

    yaw.setSensorPhase(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Pitch Ticks", pitch.getSensorCollection().getQuadraturePosition());
    SmartDashboard.putNumber("Yaw Ticks", yaw.getSensorCollection().getQuadraturePosition());
  }

  public void drive(Joystick joy) {
    setYawOutput(Xbox.LEFT_X(joy));
    setPitchOutput(Xbox.RIGHT_Y(joy));
  }

  public void setPitchOutput(double output) {
    output *= Constants.PITCH_INHIBITOR;
    pitch.set(ControlMode.PercentOutput, output);
  }

  public void setYawOutput(double output) {
    output *= Constants.YAW_INHIBITOR;
    yaw.set(ControlMode.PercentOutput, output);
  }

  public double getYawPosition() {
    return yaw.getSensorCollection().getQuadraturePosition();
  }

  public double getPitchPosition() {
    return pitch.getSensorCollection().getQuadraturePosition();
  }

  public void setYawPosition(int pos) {
    yaw.set(ControlMode.Position, pos);
  }

  public void setPitchPosition(int pos) {
    pitch.set(ControlMode.Position, pos);
  }

  public int getYawTicks() {
    return yawTicks;
  }

  public int getPitchTicks() {
    return pitchTicks;
  }

  public void zero() {
    pitch.getSensorCollection().setQuadraturePosition(0, 0);
    yaw.getSensorCollection().setQuadraturePosition(0, 0);
  }

  public void setYawPIDF(double p, double i, double d, double f, int izone) {
    yaw.config_kP(0, p);
    yaw.config_kI(0, i);
    yaw.config_kD(0, d);
    yaw.config_kF(0, f);
    yaw.config_IntegralZone(0, izone);

    yaw.configPeakOutputForward(Constants.YAW_INHIBITOR);
    yaw.configPeakOutputReverse(-1 * Constants.YAW_INHIBITOR);
  }

  public void setPitchPIDF(double p, double i, double d, double f, int izone) {
    pitch.config_kP(0, p);
    pitch.config_kI(0, i);
    pitch.config_kD(0, d);
    pitch.config_kF(0, f);
    pitch.config_IntegralZone(0, izone);

    pitch.configPeakOutputForward(Constants.PITCH_INHIBITOR);
    pitch.configPeakOutputReverse(-1 * Constants.PITCH_INHIBITOR);
  }
}
