package frc.robot;

public class CustomPIDF {

    private double kP, kI, kD, kF, kFeedForward, m_setpoint, m_error, m_pastError, m_errorSum;

    public CustomPIDF(double P, double I, double D, double F, double feedForward) {
        kP = P;
        kI = I;
        kD = D;
        kF = F;
        kFeedForward = feedForward;
    }

    public void setSetpoint(double setpoint) {
        m_setpoint = setpoint;
    }

    public double getSetpoint() {
        return m_setpoint;
    }

    public boolean atSetpoint() {
        if (m_error == m_setpoint) {
            return true;
        } else {
            return false;
        }
    }

    public boolean atSetpoint(double range) {
        double error = Math.abs(m_setpoint) - Math.abs(m_error);
        if (Math.abs(error) < range) {
            return true;
        } else {
            return false;
        }
    }

    public double calculate(double input) {
        m_pastError = m_error;
        m_error = Math.abs(m_setpoint) - Math.abs(m_error);
        m_errorSum += m_error;

        double P_Gain = 1 / m_error;
        double I_Gain = m_errorSum;
        double D_Gain = (m_error - m_pastError) / 20;

        return kP * P_Gain + kI * I_Gain + kD * D_Gain + kF * kFeedForward;
    }

    public void reset() {
        m_error = 0;
        m_pastError = 0;
        m_errorSum = 0;
    }

}