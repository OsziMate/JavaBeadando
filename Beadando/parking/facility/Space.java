package parking.facility;

import vehicle.*;

public class Space{
    private final int floorNumber;
    private final int spaceNumber;
    private Car occupyingCar;

    public Space(int floorNumber, int spaceNumber){
        this.floorNumber = floorNumber;
        this.spaceNumber = spaceNumber;
    }

    public int getFloorNumber(){
        return floorNumber;
    }
    public int getSpaceNumber(){
        return spaceNumber;
    }

    public boolean isTaken(){
        if (occupyingCar == null){
            return false;
        }
        else{
            return true;
        }
    }

    public void addOccupyingCar(Car car){
        this.occupyingCar = car;
    }

    public void removeOccupyingCar(){
        this.occupyingCar = null;
    }

    public String getCarLicensePlate(){
        return this.occupyingCar.getLicensePlate();
    }

    public Size getOccupyingCarSize(){
        return this.occupyingCar.getSpotOccupation();
    }
}