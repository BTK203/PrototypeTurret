/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubsystemMagazine extends SubsystemBase {
  private Solenoid
    load,
    reset;

  private boolean loading = false;

  /**
   * Creates a new SubsystemMagazine.
   */
  public SubsystemMagazine() {
    load  = new Solenoid(Constants.MAGAZINE_LOADER_ID);
    reset = new Solenoid(Constants.MAGAZINE_RESET_ID);

    reset();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void load() {
    load.set(true);
    reset.set(false);
    this.loading = true;
  }

  public void reset() {
    reset.set(true);
    load.set(false);
    this.loading = false;
  }

  public void toggle() {
    if(this.loading) {
      reset();
    } else {
      load();
    }
  }
}
