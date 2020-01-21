/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Constants {

    /**
     * Drivetrain motor IDs
     */
    public static final int
        DRIVE_RIGHT_MASTER_ID = 1,
        DRIVE_RIGHT_SLAVE_ID = 2,
        DRIVE_LEFT_MASTER_ID = 3,
        DRIVE_LEFT_SLAVE_ID = 4;

    /**
     * Turret motor IDs
     */
    public static final int
        TURRET_PITCH_ID = 5,
        TURRET_YAW_ID = 6,
        SHOOTER_ID = 7;

    /**
     * Solenoid IDs
     */
    public static final int
        MAGAZINE_LOADER_ID = 7,
        MAGAZINE_RESET_ID = 6;

    public static final boolean
        DRIVE_RIGHT_MASTER_INVERT = true,
        DRIVE_RIGHT_SLAVE_INVERT  = true,
        DRIVE_LEFT_MASTER_INVERT  = false,
        DRIVE_LEFT_SLAVE_INVERT   = false;

    /**
     * Turret inverts
     */
    public static final boolean
        TURRET_YAW_INVERT = false;

    /**
     * Default Dashboard Values
     */
    public static final double
        DEFAULT_SHOOTER_DRIVE_VALUE = 0.75,
        DEFAULT_YAW_INHIBITOR = 0.5,
        DEFAULT_SHOOTER_YAW_LEFT_LIMIT = -1105,
        DEFAULT_SHOOTER_YAW_RIGHT_LIMIT = 1105,

        DEFAULT_TURNTABLE_kP = 0,
        DEFAULT_TURNTABLE_kI = 0,
        DEFAULT_TURNTABLE_kD = 0,
        DEFAULT_TURNTABLE_kF = 0,
        DEFAULT_TURNTABLE_OUTPUT = 0.3,

        DEFAULT_PITCH_kP = 0,
        DEFAULT_PITCH_kI = 0,
        DEFAULT_PITCH_kD = 0,
        DEFAULT_PITCH_kF = 0,
        DEFAULT_PITCH_OUTPUT = 0.3;
}
