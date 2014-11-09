/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Joystick; 
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.smartdashboard.*;

            
public class MecanumRobot extends SimpleRobot {
    
    //Global Stuff
    private Watchdog wd = Watchdog.getInstance();
    RobotDrive chassis = new RobotDrive(1,2,3,4); // leftFront, leftRear, rightFront,rightBack
    
  
    
   
    Joystick Extreme3DPro     = new Joystick(3);
    Joystick Attack3DProLeft  = new Joystick(2);
    Joystick Attack3DProRight = new Joystick(1);
   
    Victor compressor  = new Victor(9);
    
    //Debug Options
    boolean IsDebugModeOn   = true;
    boolean DebugJoyAxis    = true;
    boolean DebugJoyButtons = true;
      
    

        
    public void operatorControl() 
    {        
    wd.setEnabled(true);
        
        while (isOperatorControl() && isEnabled())
        {
            
        Extreme3DProDrive() ;
        CompressorControll();
        wd.feed();
               
        }
    chassis.stopMotor();
    }
    
   
    
    public void Extreme3DProDrive() 
    {
       SmartDashboard.putBoolean("DriveStatus", true );
       
        //Reading Right Joystick Position
        double Extreme3DProXValue     = Extreme3DPro.getX();
        double Extreme3DProYValue     = Extreme3DPro.getY();
        double Extreme3DProTwistValue = Extreme3DPro.getTwist();
        double Extreme3DProZValue     = Extreme3DPro.getZ();
        
        if(Extreme3DProTwistValue> -.5 && Extreme3DProTwistValue < .5){Extreme3DProTwistValue = 0.000;}
        if(Extreme3DProXValue > -.5 && Extreme3DProXValue < .5){Extreme3DProXValue = 0.000;}
        if(Extreme3DProYValue > -.5 && Extreme3DProYValue < .5){Extreme3DProYValue = 0.000;}
        //                             forward/backward -  Twist/Rotate - 
        chassis.mecanumDrive_Cartesian(Extreme3DProXValue, -(Extreme3DProTwistValue), Extreme3DProZValue,0);
       SmartDashboard.putNumber("XAXIS", Extreme3DProXValue);
       SmartDashboard.putNumber("YAXIS", Extreme3DProYValue);
       SmartDashboard.putNumber("TWISTAXIS", Extreme3DProTwistValue);
       SmartDashboard.putNumber("ZAXIS", Extreme3DProZValue);
    }
  
    
    
   JoystickButton CompressorOn  = new JoystickButton(Extreme3DPro, 6);
   JoystickButton CompressorOff = new JoystickButton(Extreme3DPro, 7);
   
   boolean LastCompressorState = false;
   
  
   
   public void CompressorControll() {
     
       if(Extreme3DPro.getRawButton(6))
       {  LastCompressorState = false; } 
       
       else if(Extreme3DPro.getRawButton(7))
       {  LastCompressorState = true;}

       //Keeps the last known compressor state on or off
       if(LastCompressorState== false)
       { 
          compressor.set(0.0);
          SmartDashboard.putString("Compressor Status", "Off");
       } 
       else if(LastCompressorState == true)
       {
          compressor.set(1.0);
          SmartDashboard.putString("Compressor Status", "On");
       }
       
   }
    
    
    Solenoid DefensePistonUp   = new Solenoid(1);
    Solenoid DefensePistonDown = new Solenoid(2);
    
    public void pistonControl(){

  
       if(Attack3DProLeft.getTrigger()){
           DefensePistonDown.set(false);
           DefensePistonUp.set(true);
           SmartDashboard.putString("Defense Piston", "Up");
       } else {
           DefensePistonUp.set(false);
           DefensePistonDown.set(true);
           SmartDashboard.putString("Defense Piston", "Down");
       }

    }
    
    public void robotInit() {
        SmartDashboard.putString("Compressor Status", "Off");
        SmartDashboard.putString("Attack Piston", "Off");
        SmartDashboard.putString("Defense Piston", "Off");
        
        
        
        SmartDashboard.putBoolean("DriveStatus", false);
        SmartDashboard.putBoolean("ClawsStatus", false);
    }

    public void autonomous() {
        wd.setEnabled(false);
        
    }
}

