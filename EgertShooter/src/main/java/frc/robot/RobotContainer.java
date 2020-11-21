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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.CyborgCommandAlignTurret;
import frc.robot.commands.CyborgCommandFlywheelVelocity;
import frc.robot.subsystems.SubsystemFlywheel;
import frc.robot.subsystems.SubsystemReceiver;
import frc.robot.subsystems.SubsystemTurret;
import frc.robot.util.Xbox;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final SubsystemTurret turret = new SubsystemTurret();
  private final SubsystemFlywheel flywheel = new SubsystemFlywheel();
  private final SubsystemReceiver kiwilight = new SubsystemReceiver();

  private final Joystick 
    operator = new Joystick(0);

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
    turret.setDefaultCommand(
      new RunCommand(() -> {
        turret.drive(operator);
      }, turret)
    );

    JoystickButton toggleFlywheel = new JoystickButton(operator, Xbox.START);
      toggleFlywheel.toggleWhenPressed(new CyborgCommandFlywheelVelocity(flywheel));

    JoystickButton toggleAlign = new JoystickButton(operator, Xbox.RB);
      toggleAlign.toggleWhenPressed(new CyborgCommandAlignTurret(turret, kiwilight));
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
