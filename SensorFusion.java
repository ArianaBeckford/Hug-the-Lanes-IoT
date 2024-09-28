import javax.print.attribute.IntegerSyntax;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class SensorFusion {
    private boolean isVehicleOn, isEngineOn, isBrakePressed, isDriverInSeat, inPark, cruiseControl, selfDriving, objectExists;
    private boolean detectedStopSignTrafficLight, rainOrSnowStatus, hailStatus, doorLockStatus, obstacleInPath;
    private boolean crashDetected, airbagsDeploymentStatus;
    private boolean isStartButtonPressed, isRemoteStartButtonPressed, isTurnSignalOn, isComputerConnected;
    private int lightStatus, vehicleSpeed, amountOfRS, amountOfH, speedLimit;
    private double distanceFromLaneMarker, keyDistance, stopSignTrafficLightDistance, objectSize, objectDistance, fallingRate;
    private double wipesPerSec;
    private boolean headlightsStatus, call911;
    private int displayMode;

    public SensorFusion(String filename){
            int [] data = new int[30]; //stores normalized sensor data
            int i = 0;
            try {
                // Read data from file
                File dataFile = new File(filename);
                Scanner myReader = new Scanner(dataFile);
                while (myReader.hasNextLine()) {
                    String line = myReader.nextLine();
                    String[] sensorData = line.split(" ");
                    if (sensorData.length == 1) {
                        // If there is only one value for that type of data then put it into the array
                        data[i] = Integer.valueOf(sensorData[0]);
                    } else {
                        if (sensorData[0].equals("*")) {
                            // '*' indicates it is a boolean so take only the last value
                            data[i] = Integer.valueOf(sensorData[sensorData.length - 1]);
                        } else {
                            // If not a boolean, take the average of the data
                            int average = 0;
                            for (String value : sensorData) {
                                average += Integer.valueOf(value);
                            }
                            average = average / sensorData.length;
                            data[i] = average;
                        }
                    }
                    i++;
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
            }

        isVehicleOn = validateBoolean(data[0], 0);
        isEngineOn = validateBoolean(data[1], 1);
        isBrakePressed = validateBoolean(data[2],2);
        isDriverInSeat = validateBoolean(data[3],3);
        inPark = validateBoolean(data[4],4);
        cruiseControl = validateBoolean(data[5],5);
        selfDriving = validateBoolean(data[6],6);
        objectExists = validateBoolean(data[7],7);
        detectedStopSignTrafficLight = validateBoolean(data[8],8);
        rainOrSnowStatus = validateBoolean(data[9],9);
        hailStatus = validateBoolean(data[10],10);
        doorLockStatus = validateBoolean(data[11],11);
        obstacleInPath = validateBoolean(data[12],12);
        crashDetected = validateBoolean(data[13],13);
        airbagsDeploymentStatus = validateBoolean(data[14],14);
        isStartButtonPressed = validateBoolean(data[15],15);
        isRemoteStartButtonPressed = validateBoolean(data[16],16);
        isTurnSignalOn = validateBoolean(data[17],17);
        isComputerConnected = validateBoolean(data[18],18);


        distanceFromLaneMarker = validateNum(data[19],19);
        lightStatus = validateNum(data[20],20);
        keyDistance = validateNum(data[21],21);
        vehicleSpeed = validateNum(data[22],22);
        stopSignTrafficLightDistance = validateNum(data[23],23);
        amountOfRS = validateNum(data[24],24);
        amountOfH = validateNum(data[25],25);
        objectSize = validateNum(data[26],26);
        objectDistance = validateNum(data[27],27);
        speedLimit = validateNum(data[28], 28);
        fallingRate = validateNum(data[29], 29);

    }
    private boolean validateBoolean(int dataValue, int i){
        //assign correct value, true or false, to the boolean
        if(dataValue == 1){
            return true;
        }else if(dataValue == 0){
            return false;
        }else{
            //System.out.println("No valid data entry: " + (i + 1));
            return false;
        }
    }
    private int validateNum(int datavalue, int i){
        if(datavalue < 0){
            //return -1 to signify an error or if there is no value for that data type
            //System.out.println("No valid data entry: " + (i + 1));
            return -1;
        }else{
            return datavalue;
        }
    }
    //get methods that can be called from other classes to retrieve the data
    public boolean getVehicleStatus(){return isVehicleOn;}
    public boolean getEngineStatus(){return isEngineOn;}
    public boolean getBrakePressed(){return isBrakePressed;}
    public boolean getDriverInSeat(){return isDriverInSeat;}
    public boolean getInPark(){return inPark;}
    public boolean getCruiseControl(){return cruiseControl;}
    public boolean getSelfDriving(){return selfDriving;}
    public boolean getObjectExists(){return objectExists;}
    public boolean getStopSignOrTrafficLightDetected(){return detectedStopSignTrafficLight;}
    public boolean getRainOrSnowStatus(){return rainOrSnowStatus;}
    public boolean getHailStatus(){return hailStatus;}
    public boolean getDoorLockStatus(){return doorLockStatus;}
    public boolean getObstacleInPath(){return obstacleInPath;}
    public boolean getCrashDetected(){return crashDetected;}
    public boolean getAirbagsDeploymentStatus(){return airbagsDeploymentStatus;}
    public boolean getStartButtonPressed() {return isStartButtonPressed;}
    public boolean getRemoteStartButtonPressed() {return isRemoteStartButtonPressed;}
    public boolean getTurnSignalStatus() {return isTurnSignalOn;}
    public boolean getComputerConnectionStatus(){return isComputerConnected;}

    public double getDistanceFromLaneMarker(){return distanceFromLaneMarker;}
    public int getLightStatus(){return lightStatus;}
    public double getKeyDistance(){return keyDistance;}
    public int getVehicleSpeed(){return vehicleSpeed;}
    public double getStopSignTrafficLightDistance(){return stopSignTrafficLightDistance;}
    public int getAmountOfRS(){return amountOfRS;}
    public int getAmountOfH(){return amountOfH;}
    public double getObjectSize(){return objectSize;}
    public double getObjectDistance(){return objectDistance;}
    public int getSpeedLimit(){return speedLimit;}
    public double getFallingRate(){return fallingRate;}

    //set methods that can be called from other classes to update the data
    public void setVehicleStatus(boolean newValue){ isVehicleOn = newValue;}
    public void setEngineStatus(boolean newValue){ isEngineOn = newValue;}
    public void setInPark(boolean newValue){inPark = newValue;}
    public void setCruiseControl(boolean newValue){cruiseControl = newValue;}
    public void setSelfDriving(boolean newValue){selfDriving = newValue;}
    public void setDoorLockStatus(boolean newValue){doorLockStatus = newValue;}
    public void setTurnSignalStatus(boolean newValue) {isTurnSignalOn = newValue;}
    public void setComputerConnectionStatus(boolean newValue){isComputerConnected = newValue;}
    public void setVehicleSpeed(int newValue){vehicleSpeed = newValue;}
    public void setWipesPerSec(double newValue){wipesPerSec = newValue;}
    public void setHeadlightsStatus(boolean newValue){headlightsStatus = newValue;}
    public void setDisplayMode(int newValue){displayMode = newValue;}
    public void set911Call(boolean newValue){call911 = newValue;}
    public void setBrakePressed(boolean newValue){isBrakePressed = newValue;}

    public void setDistanceFromLaneMarker(double newValue){distanceFromLaneMarker = newValue;}
}