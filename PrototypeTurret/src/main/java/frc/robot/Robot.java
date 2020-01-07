/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.SubsystemDrive;
import frc.robot.subsystems.SubsystemReceiver;
import frc.robot.subsystems.SubsystemShooter;
import frc.robot.subsystems.SubsystemTurret;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  //declare subsystems
  public static SubsystemDrive SUB_DRIVE;
  public static SubsystemTurret SUB_TURRET;
  public static SubsystemShooter SUB_SHOOTER;
  public static SubsystemReceiver SUB_RECIEVER;

  public static OI oi;

  /**
   * Startup method
   */
  @Override
  public void robotInit() {
    DriverStation.reportWarning("ROBOT INIT STARTING", false);

    //define subsystems
    SUB_DRIVE = new SubsystemDrive();
    SUB_TURRET = new SubsystemTurret();
    SUB_SHOOTER = new SubsystemShooter();
    SUB_RECIEVER = new SubsystemReceiver();

    //OI defined last
    oi = new OI();

    DriverStation.reportWarning("ROBOT INIT DONE", false);
  }

  /**
   * Called periodically when robot is on
   */
  @Override
  public void robotPeriodic() {
    
    //put subsystems to dashboard
    SmartDashboard.putData("Drivetrain", SUB_DRIVE);
    SmartDashboard.putData("Turret", SUB_TURRET);
    SmartDashboard.putData("Shooter", SUB_SHOOTER);

    //update some values on the dashboard
    SmartDashboard.putNumber("Turret Yaw", SUB_TURRET.getTurntableEncoderPosition());
    SmartDashboard.putNumber("Turret Pitch", SUB_TURRET.getElevatorEncoderPosition());
  }

  /**
   * Called when auto starts
   */
  @Override
  public void autonomousInit() {
    DriverStation.reportWarning("OH LAWD, HE COMIN", false);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Scheduler.getInstance().run();
  }
}
