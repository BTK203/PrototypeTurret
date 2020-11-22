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
    /**
     * Motor IDs
     */
    public static final int
        PITCH_ID = 3,
        YAW_ID = 1,
        FLYWHEEL_ID = 2,
        FEEDER_ID = 4;

    /**
     * Inhibitors
     */
    public static final double 
        YAW_INHIBITOR = 0.2,
        PITCH_INHIBITOR = 0.5,
        FLYWHEEL_INHIBITOR = 1,
        FEEDER_INHIBITOR = 1;

    /**
     * Inverts
     */
    public static final boolean
        YAW_INVERT = true,
        PITCH_INVERT = false,
        FLYWHEEL_INVERT = true,
        FEEDER_INVERT = true;

    public static final double
        FLYWHEEL_GEAR_RATIO = 1.6071;

    public static final int
        DEFAULT_YAW_TICKS = 1615628,
        DEFAULT_PITCH_TICKS = 1000,
        YAW_DEGREES = 345,
        YAW_ALLOWABLE_ERROR = 50000,
        PITCH_ALLOWABLE_ERROR = 35;
    
}
