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

public class SubsystemTurret extends SubsystemBase {
  private TalonSRX
    pitch,
    yaw;

  /**
   * Creates a new SubsystemTurret.
   */
  public SubsystemTurret() {
    pitch = new TalonSRX(Constants.TURRET_PITCH_ID);
    yaw   = new TalonSRX(Constants.TURRET_YAW_ID);

    configureTalons();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Yaw Encoder Value", yaw.getSensorCollection().getQuadraturePosition());
    SmartDashboard.putNumber("Pitch Encoder Value", pitch.getSensorCollection().getQuadraturePosition());

    SmartDashboard.putNumber("Yaw Amps", yaw.getSupplyCurrent());
    SmartDashboard.putNumber("PitchAmps", pitch.getSupplyCurrent());

    boolean pitchUpperLimitClosed = pitch.getSensorCollection().isFwdLimitSwitchClosed();
    if(pitchUpperLimitClosed) {
      pitch.getSensorCollection().setQuadraturePosition(0, 0);
    }

    double yawEncoderValue = yaw.getSensorCollection().getQuadraturePosition();
    SmartDashboard.putBoolean("Yaw RSL", (yawEncoderValue > Util.getAndSetDouble("Yaw Right SL", Constants.DEFAULT_SHOOTER_YAW_RIGHT_LIMIT)));
    SmartDashboard.putBoolean("Yaw LSL", (yawEncoderValue < Util.getAndSetDouble("Yaw Left SL", Constants.DEFAULT_SHOOTER_YAW_LEFT_LIMIT)));
    SmartDashboard.putBoolean("Pitch Upper", pitchUpperLimitClosed);
  }

  public void driveTurret(Joystick joy) {
    double pitchDrive = Xbox.RIGHT_Y(joy);
    double yawDrive   = Xbox.RIGHT_X(joy);

    //check limits
    double yawEncoderValue = yaw.getSensorCollection().getQuadraturePosition();
    double rightSoftLimit = Util.getAndSetDouble("Yaw Right SL", Constants.DEFAULT_SHOOTER_YAW_RIGHT_LIMIT);
    double leftSoftLimit  = Util.getAndSetDouble("Yaw Left SL", Constants.DEFAULT_SHOOTER_YAW_LEFT_LIMIT);
    
    if(yawEncoderValue > rightSoftLimit && yawDrive > 0) 
      yawDrive = 0;

    if(yawEncoderValue < leftSoftLimit && yawDrive < 0) 
      yawDrive = 0;

    //inhibit the yaw value
    yawDrive *= Util.getAndSetDouble("Yaw Inhibitor", Constants.DEFAULT_YAW_INHIBITOR);

    pitch.set(ControlMode.PercentOutput, pitchDrive);
    yaw.set(ControlMode.PercentOutput, yawDrive);
  }

  public void driveYaw(double drive) {
    double yawEncoderValue = yaw.getSensorCollection().getQuadraturePosition();
    double rightSoftLimit = Util.getAndSetDouble("Yaw Right SL", Constants.DEFAULT_SHOOTER_YAW_RIGHT_LIMIT);
    double leftSoftLimit  = Util.getAndSetDouble("Yaw Left SL", Constants.DEFAULT_SHOOTER_YAW_LEFT_LIMIT);

    boolean atRightLimit = (yawEncoderValue >= rightSoftLimit && drive > 0);
    boolean atLeftLimit = (yawEncoderValue <= leftSoftLimit && drive < 0);

    if(!atRightLimit && !atLeftLimit) {
      yaw.set(ControlMode.PercentOutput, drive);
    }
  }

  public void drivePitch(double drive) {
    pitch.set(ControlMode.PercentOutput, drive);

    SmartDashboard.putNumber("vertical drive", drive);
  }

  public void zeroTurntableEncoder() {
    yaw.getSensorCollection().setQuadraturePosition(0, 0);
  }

  public int getTurntablePosition() {
    return yaw.getSensorCollection().getQuadraturePosition();
  }

  public void setTargetTurntablePosition(int position) {
    yaw.set(ControlMode.Position, position);
  }

  public void setYawPIDF(double p, double i, double d, double f, double forOutLimit, double revOutLimit) {
    yaw.config_kP(0, p);
    yaw.config_kI(0, i);
    yaw.config_kD(0, d);
    yaw.config_kF(0, f);

    yaw.configPeakOutputForward(forOutLimit);
    yaw.configPeakOutputReverse(revOutLimit);

    yaw.configAllowableClosedloopError(0, 0, 0);
  }

  public void setPitchPIDF(double p, double i, double d, double f, double forOutLimit, double revOutLimit) {
    pitch.config_kP(0, p);
    pitch.config_kI(0, i);
    pitch.config_kD(0, d);
    pitch.config_kF(0, f);

    pitch.configPeakOutputForward(forOutLimit);
    pitch.configPeakOutputReverse(revOutLimit);
  }

  private void configureTalons() {
    yaw.setNeutralMode(NeutralMode.Brake);
    pitch.setNeutralMode(NeutralMode.Brake);

    pitch.setInverted(true); //TODO create a constant for this
    yaw.setInverted(Constants.TURRET_YAW_INVERT);
  }
}
