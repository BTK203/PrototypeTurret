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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.ButtonCommandSpinFeeder;
import frc.robot.commands.CyborgCommandDriveVelocity;
import frc.robot.commands.ToggleCommandFlywheelPercentOutput;
import frc.robot.subsystems.SubsystemElevator;
import frc.robot.subsystems.SubsystemFeeder;
import frc.robot.subsystems.SubsystemFlywheel;
import frc.robot.util.Util;
import frc.robot.util.Xbox;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final SubsystemFlywheel SUB_FLYWHEEL = new SubsystemFlywheel();
  private final SubsystemElevator SUB_ELEVATOR = new SubsystemElevator();
  private final SubsystemFeeder   SUB_FEEDER   = new SubsystemFeeder();

  private final Joystick DRIVER = new Joystick(0);

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
    SUB_ELEVATOR.setDefaultCommand(
      new RunCommand(() -> SUB_ELEVATOR.driveWithController(DRIVER), SUB_ELEVATOR)
    );

    //button commands
    JoystickButton startPercentOutput = new JoystickButton(DRIVER, Xbox.START);
      startPercentOutput.toggleWhenPressed(new ToggleCommandFlywheelPercentOutput(SUB_FLYWHEEL));

    JoystickButton driveFeeder = new JoystickButton(DRIVER, Xbox.A);
      driveFeeder.whileHeld(new ButtonCommandSpinFeeder(SUB_FEEDER, 1));

    JoystickButton driveFeederBackward = new JoystickButton(DRIVER, Xbox.X);
      driveFeederBackward.whileHeld(new ButtonCommandSpinFeeder(SUB_FEEDER, -1));

    //dashboard buttons
    SmartDashboard.putData("Run Velocity PID", new CyborgCommandDriveVelocity(SUB_FLYWHEEL));
    SmartDashboard.putData("Run Manual PO", new ToggleCommandFlywheelPercentOutput(SUB_FLYWHEEL));


  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
