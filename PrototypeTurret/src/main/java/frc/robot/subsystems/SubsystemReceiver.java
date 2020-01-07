/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*---------------------------------------------------------------*/
/*          .d8888b.   .d8888b.   .d8888b.  888888888            */
/*         d88P  Y88b d88P  Y88b d88P  Y88b 888                  */
/*              .d88P 888        888    888 888                  */
/*             8888"  888d888b.  Y88b. d888 8888888b.            */
/*              "Y8b. 888P "Y88b  "Y888P888      "Y88b           */
/*         888    888 888    888        888        888           */
/*         Y88b  d88P Y88b  d88P Y88b  d88P Y88b  d88P           */
/*          "Y8888P"   "Y8888P"   "Y8888P"   "Y8888P"            */
/*                                                               */
/*  This code was written by FRC3695 - Foximus Prime and stored  */
/*   at github.com/wh1ter0se/KiwiLight. KiwiLight is published   */
/*     under a GPL-3.0 license, permitting modification and      */
/*      distribution under the condition that the source is      */
/* disclosed and distribution is accompanied by the same license */
/*---------------------------------------------------------------*/
package frc.robot.subsystems;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.util.Util;

/**
 * The receiver code that runs on the Rio to listen for UDP data
 */
public class SubsystemReceiver extends Subsystem {

  private static String latestSegment;
  private static double[] latestData;

  private static DatagramSocket serverSocket;
  private static byte[]         receiveData;

  private static long latestTime;

  @Override
  public void initDefaultCommand() {
  }

  public SubsystemReceiver() {
    latestSegment = "-1,-1,-1,180,180";
    latestTime    = System.currentTimeMillis();

    SmartDashboard.putString("RPi Data", latestSegment);
    SmartDashboard.putBoolean("Target Spotted", false);
    SmartDashboard.putBoolean("Updated", false);

    // EXPECTED FORMAT OF INPUT STRING:
    // :X,Y,H,D,A;
      // X = X-coordinate
      // Y = Y-coordinate
      // D = Distance from target
      // A = Angle from center (positive = CW)

    Thread listener = new Thread(() -> {
      while(!Thread.interrupted()) {
        try {
          DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); //create a new packet for the receiving data 
          serverSocket.receive(receivePacket); //receive the packet from the Socket
          String segment = new String(receivePacket.getData()).replaceAll("\\s+",""); //remove whitespace and place data in 'segment'
          latestSegment = segment.substring(segment.indexOf(":") + 1, segment.indexOf(";")); // store segment without borders
          latestData = analyzePacket(latestSegment);
          latestTime = System.currentTimeMillis(); // add timestamp for stored segment
          SmartDashboard.putString("RPi Data", segment.substring(segment.indexOf(":") + 1, segment.indexOf(";"))); // put string on dashboard without borders
          SmartDashboard.putBoolean("Target Spotted", targetSpotted());
          SmartDashboard.putBoolean("Updated", getSecondsSinceUpdate() < 1);
        } catch (IOException e) { //thrown when the socket cannot receive the packet
          DriverStation.reportError("IO EXCEPTION", true);
        }
      }
    });
    
    listener.start();
  }

  /**
   * Gets the distance between the camera and the target.
   * @return The distance, in inches, between the camera and the target, or -1 if no target is seen.
   */
  public double getDistanceToTarget() {
    return latestData[2];
  }

  /**
   * Gets the horizontal angle between the camera and the target.
   * @return The horizontal angle bwtween the camera aand the target, or 180 if no target is seen.
   */
  public double getHorizontalAngleToTarge() {
    return latestData[3];
  }

  /**
   * Gets the vertical angle between the camera and the target.
   * @return The vertical angle bwtween the camera aand the target, or 180 if no target is seen.
   */
  public double getVerticalAngleToTarget() {
    return latestData[4];
  }

  /**
   * Returns the miliseconds since the pi sent the LastKnownLocation
   * @return ms since last received UDP packet
   */
  public double getSecondsSinceUpdate() {
    return Util.roundTo((double) ((System.currentTimeMillis() - latestTime) / 1000), 2);
  }

  /**
   * Checks if the JeVois is sending coordinates
   * @return true if receiving a distance, false if distance is -1
   */
  public Boolean targetSpotted() {
    return latestData[2] != -1;
  }

  /**
   * Retrieves the last known pixel coordinates of the target
   * @return [0] = X-coordinate (in pixels from left)
   *         [1] = Y-coordinate (in pixels from bottom)
   *         [2] = Distance (in inches)
   *         [3] = Angle from center (in degrees; positive = CW)
   *         {-1,-1,-1, 180, 180} for no known location
   */
  private double[] analyzePacket(String packet) {
    double[] parsedData = {-1, -1, -1, 180, 180};
    try {
      String[] splitData = packet.split(",");
      if(splitData.length != 5) {
        DriverStation.reportWarning("Data Improperly formatted! 5 segments were expected but " + splitData.length + " were found.", true);
        return parsedData;
      }

      for(int i=0; i<splitData.length; i++) {
        parsedData[i] = Integer.parseInt(splitData[i]);
      }

    } catch(Exception ex) {
      DriverStation.reportError("An Exception was encountered in analyzePacket()! ex: " + ex.getMessage(), true);
    }

    return parsedData;
  }
}
