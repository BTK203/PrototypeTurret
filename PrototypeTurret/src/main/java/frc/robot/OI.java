/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.InstantCommandZeroTurntable;
import frc.robot.commands.ToggleCommandSpinShooter;
import frc.robot.util.Xbox;

/**
 * Operator Interface
 */
public class OI {
    public static final Joystick DRIVER = new Joystick(0);
    public static final Joystick OPERATOR = new Joystick(1);

    // button commands and dashboard buttons configured here
    public OI() {
        SmartDashboard.putData("Zero Turntable", new InstantCommandZeroTurntable());

        JoystickButton toggleShooter = new JoystickButton(OPERATOR, Xbox.A);
            toggleShooter.toggleWhenPressed(new ToggleCommandSpinShooter());

    }
}
