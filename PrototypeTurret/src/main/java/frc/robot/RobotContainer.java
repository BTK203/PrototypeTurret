/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.CyborgCommandAlignTurret;
import frc.robot.commands.CyborgCommandSmartAlignTurret;
import frc.robot.commands.ToggleCommandDriveFlywheel;
import frc.robot.subsystems.SubsystemCompressor;
import frc.robot.subsystems.SubsystemDrive;
import frc.robot.subsystems.SubsystemFlywheel;
import frc.robot.subsystems.SubsystemMagazine;
import frc.robot.subsystems.SubsystemReceiver;
import frc.robot.subsystems.SubsystemTurret;
import frc.robot.util.Xbox;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SubsystemDrive      SUB_DRIVE    = new SubsystemDrive();
  private final SubsystemTurret     SUB_TURRET   = new SubsystemTurret();
  private final SubsystemFlywheel   SUB_FLYWHEEL = new SubsystemFlywheel();
  // private final SubsystemMagazine   SUB_MAGAZINE = new SubsystemMagazine();
  private final SubsystemCompressor SUB_COMP     = new SubsystemCompressor();
  private final SubsystemReceiver   SUB_RECEIVER = new SubsystemReceiver();

  private final Joystick DRIVER   = new Joystick(0);
  private final Joystick OPERATOR = new Joystick(1);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //manual commands
    SUB_DRIVE.setDefaultCommand(
      new RunCommand(() -> SUB_DRIVE.drive(DRIVER), SUB_DRIVE)
    );

    SUB_TURRET.setDefaultCommand(
      new RunCommand(() -> SUB_TURRET.driveTurret(OPERATOR), SUB_TURRET)
    );

    //button commands
    JoystickButton toggleFlywheel = new JoystickButton(OPERATOR, Xbox.Y);
      toggleFlywheel.toggleWhenPressed(new ToggleCommandDriveFlywheel(SUB_FLYWHEEL));

    // JoystickButton toggleMagazine = new JoystickButton(OPERATOR, Xbox.A);
    //   toggleMagazine.whenPressed(
    //     new InstantCommand(() -> SUB_MAGAZINE.toggle(), SUB_MAGAZINE)
    //   );

    //dashboard buttons
    SmartDashboard.putData(
      "Zero Turntable Encoder",
      new InstantCommand(() -> SUB_TURRET.zeroTurntableEncoder(), SUB_TURRET)
    );

    SmartDashboard.putData(
      "Toggle Compressor",
      new InstantCommand(() -> SUB_COMP.setRunning(!SUB_COMP.getRunning()))
    );

    SmartDashboard.putData(
      "Align",
      new CyborgCommandAlignTurret(SUB_TURRET, SUB_RECEIVER)
    );

    SmartDashboard.putData (
      "Smart Align",
      new CyborgCommandSmartAlignTurret(SUB_TURRET, SUB_RECEIVER)
    );
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
