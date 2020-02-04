/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SubsystemCompressor extends SubsystemBase {
  Compressor comp;
  boolean running;

  /**
   * Creates a new SubsystemCompressor.
   */
  public SubsystemCompressor() {
    comp = new Compressor();
    comp.stop(); //comp.start();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setRunning(boolean running) {
    if(running) {
      comp.start();
    } else {
      comp.stop();
    }

    this.running = running;
  }

  public boolean getRunning() {
    return this.running;
  }
}
