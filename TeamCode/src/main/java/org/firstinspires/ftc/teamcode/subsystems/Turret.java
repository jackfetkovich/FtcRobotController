package org.firstinspires.ftc.teamcode.subsystems;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedforward.BasicFeedforward;
import com.ThermalEquilibrium.homeostasis.Controllers.Feedforward.FeedforwardController;
import com.ThermalEquilibrium.homeostasis.Filters.Estimators.RawValue;
import com.ThermalEquilibrium.homeostasis.Systems.BasicSystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.TuningParams.ControllerCoefficients;

import java.util.function.DoubleSupplier;

public class Turret implements Subsystem {

    public DcMotorEx turretMotor;
    // protected double previousPower = 0;

    protected double referencePosition = 0;

    BasicPID controller = new BasicPID(ControllerCoefficients.turretCoefficients);
    //   protected RawValue noFilter;
    // protected BasicFeedforward feedforward = new BasicFeedforward(controllerCoefficients.turretFFcoefficients);
    //  protected BasicSystem controlSystem;


   /* @RequiresApi(api = Build.VERSION_CODES.N)
   / public turret() {
        DoubleSupplier motorPosition = new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return turretMotor.getCurrentPosition();
            }
        };
        this.noFilter = new RawValue(motorPosition);
        this.controlSystem = new BasicSystem(noFilter,controller,feedforward);
    } */


    @Override
    public void init(HardwareMap hwmap) {
        initNoReset(hwmap);
        turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void initNoReset(HardwareMap hwmap) {
        turretMotor = hwmap.get(DcMotorEx.class, "motorTurret");
        turretMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void update() {

        double power = controller.calculate(referencePosition, turretMotor.getCurrentPosition()-145);
        turretMotor.setPower(power);

    }

    @Override
    public Integer getSubsystemState() {
        return turretMotor.getCurrentPosition() - 145;
    }

    public double getReferencePosition() {
        return referencePosition;
    }

    public void setReferencePosition(double referencePosition) {
        this.referencePosition = referencePosition;
    }

    /**
     * set the power of the turret while optimizing calls to the lynx controller
     * @param power the motor power, clipped to be between -1 <= x <= 1
     */
   /* protected void setTurretMotorPower(double power) {
        power = Range.clip(power,-1,1);
        if (power != previousPower) {
            this.turretMotor.setPower(power);
        }
        previousPower = power;
    }
*/
}