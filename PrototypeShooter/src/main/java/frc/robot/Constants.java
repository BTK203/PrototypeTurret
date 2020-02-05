/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int
        FLYWHEEL_ID = 0;

    public static final int 
        YAW_ID      = 1,
        PITCH_ID    = 2,
        FEEDER_ID   = 3;

    /**
     * Inverts
     */
    public static final boolean
        FLYWHEEL_INVERT = false,
        ELEVATOR_INVERT = false,
        FEEDER_INVERT = false;

    /**
     * Amp Limits
     */
    public static final int
        FLYWHEEL_AMP_LIMIT = 50;

    /**
     * Dashboard values
     */
    public static final double
        DEFAULT_P = 0.5,
        DEFAULT_I = 0,
        DEFAULT_D = 0,
        DEFAULT_F = 0,
        DEFAULT_LOWER_LIMIT = -1,
        DEFAULT_UPPER_LIMIT = 1,
        DEFAULT_VELOCITY = 3000;

    /**
     * Misc
     */
    public static final double
        FLYWHEEL_GEAR_RATIO = 1.6071;
}
