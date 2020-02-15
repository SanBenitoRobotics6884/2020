package frc.robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class CustomDrive {

    private final SpeedControllerGroup m_leftMotors;
    private final SpeedControllerGroup m_rightMotors;

    double m_leftMotorSpeed;
    double m_rightMotorSpeed;

    double m_turnGain;
    double m_speedCoefficient;
    double m_trim;
    double m_fixedTrim;

    public CustomDrive (SpeedControllerGroup leftMotors, SpeedControllerGroup rightMotors, double trim) {

        m_leftMotors = leftMotors;
        m_rightMotors = rightMotors;
        m_trim = trim;

    }

    private double skim(double v, double gain) {
        if (v > 1.0) {
            return -((v - 1.0) * gain);
        } else if (v < -1.0) {
            return -((v + 1.0) * gain);
        } return 0; 
    }

    public void Drive (double throttle, double turnValue, double turnGain, double speedCoefficient) {

        m_turnGain = turnGain;
        m_speedCoefficient = speedCoefficient;
        
        if (throttle > 0) {
            m_fixedTrim = m_trim;
        } else {
            m_fixedTrim = m_trim - 0.1;
        }

        m_leftMotorSpeed = -(throttle - turnValue) * m_speedCoefficient;
        m_rightMotorSpeed = (throttle + turnValue) * m_speedCoefficient;

        m_leftMotors.set( m_leftMotorSpeed + skim(m_rightMotorSpeed, m_turnGain));
        m_rightMotors.set( (m_rightMotorSpeed + skim(m_leftMotorSpeed, m_turnGain)) * m_fixedTrim );
        

    }

    public double getLeftMotor() {
        return m_leftMotorSpeed + skim(m_rightMotorSpeed, m_turnGain);
    }

    public double getRightMotor() {
        return m_rightMotorSpeed + skim(m_leftMotorSpeed, m_turnGain);            
    }

}