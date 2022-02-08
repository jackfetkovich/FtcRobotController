package org.firstinspires.ftc.teamcode.TuningParams;

import com.ThermalEquilibrium.homeostasis.Parameters.FeedforwardCoefficients;
import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;

public class ControllerCoefficients {

    public static final PIDCoefficients turretCoefficients = new PIDCoefficients(0.02,0.0,0.0);
    public static final FeedforwardCoefficients turretFFcoefficients = new FeedforwardCoefficients(0,0,0);
    public static final PIDCoefficients slideCoefficients = new PIDCoefficients(0.01,0.0,.1);
    public static final PIDCoefficients turnCoefficients = new PIDCoefficients(0.15,0.0,0.01);

}