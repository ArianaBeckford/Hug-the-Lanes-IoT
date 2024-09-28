public class VehicleControl {


    // Class Attributes
    public Planning plan;




    // Default Constructor
    public VehicleControl(String filename) {
        plan = new Planning(filename);
        mainVC();
    }




    private void mainVC() {
        if(plan.getTurnOnCar()) {
            // 1.3.1.1.1 Driver Starts the Car
            if(turnOnCar()) {
                System.out.println("Vehicle Status: On");
            } else {
                System.out.println("ERROR: Vehicle unable to turn on.");
            }
        } else if(plan.getTurnOnCarRemote()) {
            // 1.3.1.1.2 Remote Start
            if(turnOnCarRemote()) {
                System.out.println("Vehicle Status: On");
            } else {
                System.out.println("ERROR: Vehicle unable to turn on remotely.");
            }
        } else if(plan.getTurnOffCar()) {
            // 1.3.1.1.3 Driver Turns Off the Car
            if(turnOffCar()) {
                System.out.println("Vehicle Status: Off");
            } else {
                System.out.println("ERROR: Vehicle unable to turn off.");
            }
        }


        if(plan.getGradualBrakingStop()) {
            // 1.3.1.2.2 Stop Signs, Traffic Lights, and Automatic Braking
            if (gradualBrakingStop()) {
                System.out.println("Brakes successfully applied! (stop)");
            } else {
                System.out.println("ERROR: Brakes not applied.");
            }
        }


        if(plan.getGradualBrakingEmergency()){
            // 1.3.1.2.7 Potential Crash Detection and Automatic Braking
            if(gradualBrakingEmergency()) {
                System.out.println("WARNING: Potential Crash Detection");
                System.out.println("Brakes successfully applied! (emergency)");
            } else {
                System.out.println("ERROR: Brakes not applied.");
            }
        }


        if(plan.getTurnOnWindshieldWipers()) {
            // 1.3.1.2.3 Automated Front Windshield Wipers
            if(turnOnWindshieldWipers()) {
                System.out.println("Windshield wipers successfully turned on!");
            } else {
                System.out.println("ERROR: Windshield wipers not turned on.");
            }
        }


        if(plan.getTurnOnHeadlights()) {
            // 1.3.1.2.4 Automated Headlights
            if(turnOnHeadlights()) {
                System.out.println("Headlights successfully turned on!");
            } else {
                System.out.println("ERROR: Headlights not turned on.");
            }
        }


        if(plan.getLockDoors()) {
            // 1.3.1.2.5 Automatic Door Locking
            if(lockDoors()) {
                System.out.println("Doors successfully locked!");
            } else {
                System.out.println("ERROR: Doors not locked.");
            }
        }




        if(plan.getTurnSteeringWheel()) {
            // 1.3.1.2.1 Lane Assistance for Assisted Driving
            if(turnSteeringWheel()) {
                System.out.println("Steering wheel successfully turned!");
                //System.out.println("Current distance from lane marker 3 feet");
            } else {
                System.out.println("ERROR: Steering wheel not turned.");
            }
        }




        if(plan.getSpeedUp()) {
            // 1.3.1.2.6 Speed Control
            System.out.println("Starting velocity: " + plan.sensFus.getVehicleSpeed());
            if(speedUp()) {
                System.out.println("Vehicle successfully sped up!");
                System.out.println("Velocity: " + plan.sensFus.getVehicleSpeed());
            } else {
                System.out.println("Vehicle speed increase unsuccessful.");
                System.out.println("Velocity: " + plan.sensFus.getVehicleSpeed());
            }
        }




        if(plan.getSlowDown()) {
            // 1.3.1.2.6 Speed Control
            System.out.println("Starting velocity: " + plan.sensFus.getVehicleSpeed());
            if(slowDown()) {
                System.out.println("Vehicle successfully slowed down!");
                System.out.println("Velocity: " + plan.sensFus.getVehicleSpeed());
            } else {
                System.out.println("Vehicle speed decrease unsuccessful.");
                System.out.println("Velocity: " + plan.sensFus.getVehicleSpeed());
            }
        }




        if(plan.getDial911()) {
            // 1.3.1.2.8 Crash Response
            if(dial911()) {
                System.out.println("Vehicle successfully dialed 911! Help is on the way!");
            } else {
                System.out.println("Vehicle unsuccessful in dialing 911.");
            }
        }




    }




    /**
     * Function called by Planning when preconditions for
     * turning on car are met.
     * Returns true if car turns on successfully.
     * Returns false if car is not successfully turned on.
     * @return boolean
     */
    private boolean turnOnCar() {
        // 1.3.1.1.1 Driver Starts the Car
        plan.sensFus.setVehicleStatus(true); // Vehicle is now on!
        return true;
    }




    /**
     * Function called by Planning when preconditions for
     * turning on car remotely are met.
     * Returns true if car turns on successfully.
     * Returns false if car is not successfully turned on remotely.
     * @return boolean
     */
    private boolean turnOnCarRemote() {
        // 1.3.1.1.2 Remote Start
        plan.sensFus.setVehicleStatus(true); // Vehicle is now on!
        // Vehicle heater or A/C turns on depending on settings the driver last left
        // the heater or A/C on.
        // Display and all buttons light up.
        return true;
    }




    /**
     * Function called by Planning when preconditions for
     * turning off car are met.
     * Returns true if car turns off successfully.
     * Returns false if car is not successfully turned off.
     * @return boolean
     */
    private boolean turnOffCar() {
        // 1.3.1.1.3 Driver Turns Off the Car
        plan.sensFus.setVehicleStatus(false); // Vehicle is now off!
        // Display shuts off
        return true;
    }




    /**
     * Function called by Planning when preconditions for
     * applying brakes are met.
     * Returns true if car applies brakes successfully.
     * Returns false if car does not successfully apply brakes.
     * @return boolean
     */
    private boolean applyBrakes() {
        plan.sensFus.setBrakePressed(true);     //turning brakes on
        return true;
    }




    /**
     * Function called by Planning when preconditions for
     * applying brakes gradually are met.
     * Returns true if car applies brakes gradually successfully.
     * Returns false if car does not successfully gradually apply brakes.
     * @return boolean
     */
    private boolean gradualBrakingEmergency() {  //calls applyBrakes, check if works by showing speed
        // 1.3.1.2.7 Potential Crash Detection and Automatic Braking
        /*
         * Planning transmits a request to Vehicle Control to start braking
         * if the object is both within 100 feet and larger than 8 inches at an
         * intensity calculated using an algorithm to safely stop the vehicle at
         * least 3 feet from the object.
         */


        //object distance crash - within 100, stop less than 3 feet, objectsize > 8 inches


        while (plan.sensFus.getObjectDistance() >= 3 && plan.sensFus.getVehicleSpeed() > 0){
            plan.sensFus.setVehicleSpeed(plan.sensFus.getVehicleSpeed() - 10); //braking intensity
        }


        return true;
    }


    /**
     * Function called by Planning when preconditions for
     * applying brakes gradually are met. (Stop signs, traffic lights, automatic braking)
     * Returns true if car applies brakes gradually successfully.
     * Returns false if car does not successfully gradually apply brakes.
     * @return boolean
     */
    private boolean gradualBrakingStop() {  //calls applyBrakes, check if works by showing speed
        // To be implemented
        // 1.3.1.2.2 Stop Signs, Traffic Lights, and Automatic Braking


        //object distance stop - 250 ft away, will stop less than 5 feet


        while (plan.sensFus.getObjectDistance() >= 5 && plan.sensFus.getVehicleSpeed() > 0){
            plan.sensFus.setVehicleSpeed(plan.sensFus.getVehicleSpeed() - 3); //braking intensity
        }
        return true;
    }




    /**
     * Function called by Planning when preconditions for
     * turning on windshield wipers are met.
     * Returns true if car turns on windshield wipers successfully.
     * Returns false if car does not successfully turn on windshield wipers.
     * @return boolean
     */
    private boolean turnOnWindshieldWipers() {
        // 1.3.1.2.3 Automated Front Windshield Wipers
        plan.sensFus.setWipesPerSec(plan.getWipesPerSec()); // Add setWipesPerSec to SensorFusion class
        return true;
    }




    /**
     * Function called by Planning when preconditions for
     * turning on headlights are met.
     * Returns true if car turns on headlights successfully.
     * Returns false if car does not successfully turn on headlights.
     * @return boolean
     */
    private boolean turnOnHeadlights() {
        // 1.3.1.2.4 Automated Headlights
        plan.sensFus.setHeadlightsStatus(true); // Add setHeadlightsStatus to SensorFusion class
        plan.sensFus.setDisplayMode(1); // Add setDisplayMode to SensorFusion class; 1 indicates night mode, 0 indicates day mode
        return true;
    }




    /**
     * Function called by Planning when preconditions for
     * turning on headlights are met.
     * Returns true if car turns on headlights successfully.
     * Returns false if car does not successfully turn on headlights.
     * @return boolean
     */
    private boolean lockDoors() {
        // 1.3.1.2.5 Automatic Door Locking
        plan.sensFus.setDoorLockStatus(true); // Add setHeadlightsStatus to SensorFusion class
        return true;
    }




    /**
     * Function called by Planning when preconditions for
     * speeding up are met.
     * Returns true if car speeds up successfully.
     * Returns false if car does not successfully speed up.
     * @return boolean
     */
    private boolean speedUp() {
        // 1.3.1.2.6 Speed Control
        int currSpeed = plan.sensFus.getVehicleSpeed();
        while (currSpeed < plan.sensFus.getSpeedLimit()){
            //Increase vehicle speed by 1
            int speedToUpdate = plan.sensFus.getVehicleSpeed();
            speedToUpdate = speedToUpdate + 1;
            plan.sensFus.setVehicleSpeed(speedToUpdate);
            currSpeed = speedToUpdate;
        }
        plan.sensFus.setVehicleSpeed(currSpeed);       //setting it to current speed (should be increased now)
        return true;
    }




    /**
     * Function called by Planning when preconditions for
     * slowing down are met.
     * Returns true if car slows down successfully.
     * Returns false if car does not successfully slows down.
     * @return boolean
     */
    private boolean slowDown() {
        // 1.3.1.2.6 Speed Control
        int currSpeed = plan.sensFus.getVehicleSpeed();
        while (currSpeed > plan.sensFus.getSpeedLimit()){
            //Decrease vehicle speed by 1
            int speedToUpdate = plan.sensFus.getVehicleSpeed();
            speedToUpdate = speedToUpdate - 1;
            plan.sensFus.setVehicleSpeed(speedToUpdate);
            currSpeed = speedToUpdate;
        }
        plan.sensFus.setVehicleSpeed(currSpeed);       //setting it to current speed (should be decreased now)
        return true;
    }




    /**
     * Function called by Planning when preconditions for
     * turning steering wheel are met.
     * Returns true if car turns steering wheel successfully.
     * Returns false if car does not successfully turns steering wheel.
     * @return boolean
     */
    private boolean turnSteeringWheel() {
        // 1.3.1.2.1 Lane Assistance for Assisted Driving
        double dist = plan.sensFus.getDistanceFromLaneMarker();
        System.out.println("WARNING: current distance from lane marker is " + dist + " inches");
        //~3 feet needed on each side of car to be centered
        //rn 6 inches
        //make it 3 feet!
        double distToMove = 36 - dist;
        plan.sensFus.setDistanceFromLaneMarker(dist + distToMove);  //now centered!
        return true;
    }




    /**
     * Function called by Planning when preconditions for
     * dialing 911 are met.
     * Returns true if car dials 911 successfully.
     * Returns false if car does not successfully dial 911.
     * @return boolean
     */
    private boolean dial911() {
        // 1.3.1.2.8 Crash Response
        plan.sensFus.set911Call(true); // Add set911Call to SensorFusion class
        return true;
    }




}
