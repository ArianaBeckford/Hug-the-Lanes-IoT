public class Planning {




    public SensorFusion sensFus;
    // Class Attributes
    private boolean turnOnCar;
    private boolean turnOnCarRemote;
    private boolean turnOffCar;
    private boolean gradualBrakingStop;
    private boolean gradualBrakingEmergency;
    private boolean applyBrakes;
    private boolean turnOnWindshieldWipers;
    private boolean turnOnHeadlights;
    private boolean lockDoors;
    private boolean turnSteeringWheel;
    private boolean slowDown;
    private boolean speedUp;
    private boolean dial911;
    private double wipesPerSec;




    // Default Constructor
    public Planning(String filename) {
        sensFus = new SensorFusion(filename);
        mainPlan();
    }




    /**
     * Function to interpret sensor data and inform VCS about
     * what to do.
     * @return void
     */
    private void mainPlan() {
        // If Vehicle is not on
        if(!sensFus.getVehicleStatus()) {
            // 1.3.1.1.1 Driver Starts the Car
            if(sensFus.getStartButtonPressed()) {
                if(sensFus.getKeyDistance() >= 0 && sensFus.getKeyDistance() <= 1.5 && sensFus.getDriverInSeat() && sensFus.getBrakePressed()) {
                    turnOnCar = true;
                }
            } else if(sensFus.getRemoteStartButtonPressed()) {
                // 1.3.1.1.2 Remote Start
                if(sensFus.getKeyDistance() >=0 && sensFus.getKeyDistance() <= 15) {
                    turnOnCarRemote = true;
                }
            }
        } else {
            // If Vehicle is on


            // If Driver presses start button and Vehicle is on
            if(sensFus.getStartButtonPressed()) {
                // 1.3.1.1.3 Driver Turns Off the Car
                if(sensFus.getVehicleSpeed() == 0 && sensFus.getInPark()) {
                    turnOffCar = true;
                }
            } else if((sensFus.getCruiseControl() || sensFus.getSelfDriving()) && sensFus.getVehicleSpeed() > 0 && (sensFus.getDistanceFromLaneMarker() >= 0 && sensFus.getDistanceFromLaneMarker() < 6 && !sensFus.getTurnSignalStatus())) {   //dist from lane marker >= 0 error handling
                // 1.3.1.2.1 Lane Assistance for Assisted Driving
                turnSteeringWheel = true;
            } else if((sensFus.getCruiseControl() || sensFus.getSelfDriving()) && sensFus.getVehicleSpeed() > 0 && sensFus.getStopSignOrTrafficLightDetected() &&
                    sensFus.getStopSignTrafficLightDistance() >= 0 && sensFus.getStopSignTrafficLightDistance() <= 1000) {  //StopSignTrafficLightDistance >= 0 error handling
                // 1.3.1.2.2 Stop Signs, Traffic Lights, and Automatic Braking
                /*
                 * The Planning module transmits a brake request to Vehicle Control
                 * when the distance is less than or equal to 250 feet with a determined
                 * brake intensity which is calculated using an algorithm based on the
                 * vehicle’s current speed and distance from the stop sign or red traffic
                 * light to ensure a safe stop at least 5 feet away from the stop sign or
                 * traffic light.
                 */
                gradualBrakingStop = true;
            }


            //Use case -- Adverse Driving Conditions
            if(sensFus.getRainOrSnowStatus() || sensFus.getHailStatus()) {
                // 1.3.1.2.3 Automated Front Windshield Wipers
                if(sensFus.getAmountOfRS() > 35 || sensFus.getAmountOfH() > 4) {
                    turnOnWindshieldWipers = true;
                    if(sensFus.getFallingRate() >= 1 && sensFus.getFallingRate() <= 4) {    //error handling (like getFallingRate >= 0) here
                        wipesPerSec = 0.5;
                    } else if(sensFus.getFallingRate() >= 5 && sensFus.getFallingRate() <= 10) { //error handling (like getFallingRate >= 0) here
                        wipesPerSec = 1;
                    } else if(sensFus.getFallingRate() > 10) {
                        wipesPerSec = 2;
                    }
                }
            }




            if(sensFus.getLightStatus() >= 0 && sensFus.getLightStatus() < 400) { //getLightStatus >= 0 error handling
                // 1.3.1.2.4 Automated Headlights
                turnOnHeadlights = true;
            }




            if(!sensFus.getDoorLockStatus() && sensFus.getVehicleSpeed() >= 15) {
                // 1.3.1.2.5 Automatic Door Locking
                lockDoors = true;
            }




            if(sensFus.getSelfDriving() && sensFus.getVehicleSpeed() > 0) {
                // 1.3.1.2.6 Speed Control
                if(sensFus.getSpeedLimit() > sensFus.getVehicleSpeed() && sensFus.getSpeedLimit() >=0 && sensFus.getObjectDistance() >= 300) { //Added in the check for validity //getSpeedLimit >= 0 error handling
                    speedUp = true;
                }
                if(sensFus.getSpeedLimit() < sensFus.getVehicleSpeed() && sensFus.getSpeedLimit() >=0) { //getSpeedLimit >= 0 error handling
                    slowDown = true;
                }
            }




            if((sensFus.getCruiseControl() || sensFus.getSelfDriving()) && sensFus.getVehicleSpeed() > 0 && sensFus.getObjectExists() && sensFus.getObjectSize() > 8 && sensFus.getObjectDistance() >= 0 && sensFus.getObjectDistance() <= 100) { //getVehicleSpeed, getObjectDistance >= 0 error handling
                // 1.3.1.2.7 Potential Crash Detection and Automatic Braking
                gradualBrakingEmergency = true;
            }




            if(sensFus.getCrashDetected() && sensFus.getVehicleSpeed() == 0 && sensFus.getAirbagsDeploymentStatus()) {
                // 1.3.1.2.8 Crash Response
                dial911 = true;
            }

        }
    }


    public boolean getTurnOnCar() {
        return turnOnCar;
    }


    public boolean getTurnOnCarRemote() {
        return turnOnCarRemote;
    }


    public boolean getTurnOffCar() {
        return turnOffCar;
    }


    public boolean getGradualBrakingEmergency() {
        return gradualBrakingEmergency;
    }


    public boolean getGradualBrakingStop() {
        return gradualBrakingStop;
    }



    public boolean getApplyBrakes() {
        return applyBrakes;
    }



    public boolean getTurnOnWindshieldWipers() {
        return turnOnWindshieldWipers;
    }




    public double getWipesPerSec() {
        return wipesPerSec;
    }




    public boolean getTurnOnHeadlights() {
        return turnOnHeadlights;
    }




    public boolean getLockDoors() {
        return lockDoors;
    }




    public boolean getSlowDown() {
        return slowDown;
    }




    public boolean getSpeedUp() {
        return speedUp;
    }




    public boolean getTurnSteeringWheel() {
        return turnSteeringWheel;
    }




    public boolean getDial911() {
        return dial911;
    }




}
