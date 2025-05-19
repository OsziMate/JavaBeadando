package parking.facility;

import java.util.ArrayList;
import parking.*;
import vehicle.*;

public class Gate{
    
    private final ArrayList<Car> cars;
    private final ParkingLot parkingLot;

    public Gate(ParkingLot parkingLot){
        this.parkingLot = parkingLot;
        cars = new ArrayList<Car>();
    }


    private Space findTakenSpaceByCar(Car vehicle){
        Space[][] floorPlan = parkingLot.getFloorPlan(); 
        for (int i=0; i < floorPlan.length; i++){
            for (int j=0; j < floorPlan[i].length; j++){
                if (floorPlan[i][j].isTaken() && floorPlan[i][j].getCarLicensePlate().equals(vehicle.getLicensePlate())){
                    return floorPlan[i][j];
                }
            }
        }
        return null;
    }

    
    private Space[] findTakenSpacesByLargeCar(Car vehicle){
        Space[][] floorPlan = parkingLot.getFloorPlan(); 
        for (int i=0; i < floorPlan.length; i++){
            for (int j=0; j < floorPlan[i].length; j++){
                if (floorPlan[i][j].isTaken() && floorPlan[i][j].getCarLicensePlate().equals(vehicle.getLicensePlate())){
                    return new Space[] {floorPlan[i][j], floorPlan[i][j+1]};
                }
            }
        }
        return null;
    }


    private Space findAvailableSpaceOnFloor(int floor, Car c){
        if (c.getSpotOccupation() == Size.SMALL){
            return findAvailableSpaceOnFloorForSmallCar(floor, c);
        }
        else{
            return findAvailableSpaceOnFloorForLargeCar(floor, c);
        }
    }

    private Space findAvailableSpaceOnFloorForSmallCar(int floor, Car c){
        Space[][] floorPlan = parkingLot.getFloorPlan();
        for (int i=0; i < floorPlan[floor].length; i++){
            if (!floorPlan[floor][i].isTaken()){
                return floorPlan[floor][i];
            }
        }
        return null;
    }

    private Space findAvailableSpaceOnFloorForLargeCar(int floor, Car c){
        Space[][] floorPlan = parkingLot.getFloorPlan();
        for (int i=0; i < floorPlan[floor].length-1; i++){
            if (!floorPlan[floor][i].isTaken() && !floorPlan[floor][i+1].isTaken()){
                return floorPlan[floor][i+1];
            }
        }
        return null;
    }

    public Space findAnyAvailableSpaceForCar(Car c){
        Space[][] floorPlan = parkingLot.getFloorPlan();
        for (int i = 0; i < floorPlan.length; i++){
            Space availabe = findAvailableSpaceOnFloor(i, c);
             if (availabe != null){
                return availabe;
             }
        }
        return null;
    }

    public Space findPreferredAvailableSpaceForCar(Car c){
        Space[][] floorPlan = parkingLot.getFloorPlan();
        Space availabe= findAvailableSpaceOnFloor(c.getPreferredFloor(), c);
        if (availabe== null && c.getPreferredFloor() != 0){
            availabe = findAvailableSpaceOnFloor(c.getPreferredFloor()-1, c);
        }
        if (availabe == null){
            int i = c.getPreferredFloor();
            int j = 1;
            while ((i-j>=0 || i+j<floorPlan.length) && availabe== null){
                availabe = findAvailableSpaceOnFloor(i-j,c);
                if (availabe == null){
                    availabe = findAvailableSpaceOnFloor(i+j,c);
                }
                j++;
            }
        }
        return availabe;
    }

    public boolean registerCar(Car c){
        Space available = findPreferredAvailableSpaceForCar(c);
        if (available == null){
            available = findAnyAvailableSpaceForCar(c);
        }
        if (available == null){
            return false;
        }
        else{
            available.addOccupyingCar(c);
            if (c.getSpotOccupation() == Size.LARGE){
                Space[][] floorPlan = parkingLot.getFloorPlan();
                for (int i = 0; i < floorPlan.length; i++){
                    for (int j = 0; j < floorPlan[i].length - 1; j++){
                        if (floorPlan[i][j+1] == available){
                            floorPlan[i][j].addOccupyingCar(c);
                            floorPlan[i][j+1].addOccupyingCar(c);
                        }
                    } 
                }
            }
            cars.add(c);
            return true;
        }
    }
    
    public boolean registerCarStoopid(Car c){
        Space available = findAnyAvailableSpaceForCar(c);
        if (available == null){
            return false;
        }
        else{
            available.addOccupyingCar(c);
            if (c.getSpotOccupation() == Size.LARGE){
                Space[][] floorPlan = parkingLot.getFloorPlan();
                for (int i = 0; i < floorPlan.length; i++){
                    for (int j = 0; j < floorPlan[i].length - 1; j++){
                        if (floorPlan[i][j+1] == available){
                            floorPlan[i][j].addOccupyingCar(c);
                        }
                    } 
                }
            }
            cars.add(c);
            return true;
        }
    }

    public void registerCars(Car... cars){
        for (Car c : cars){
            boolean register = registerCarStoopid(c);
            if(!register){
                System.err.println("Hibás regisztrálás:" + c.getLicensePlate());
            }
        }

    }

    public void deRegisterCar(String plate){
        Car car = null;
        for (Car c : cars){
            if (c.getLicensePlate().equals(plate)){
                car = c;
            }
        }
        if (car.getSpotOccupation() == Size.SMALL){
            Space space = findTakenSpaceByCar(car);
            if (space != null){
                space.removeOccupyingCar();
                cars.remove(car);
            }
        }
        else{
            Space[] spaces = findTakenSpacesByLargeCar(car);
            if (spaces != null){
                for (Space s : spaces){
                    s.removeOccupyingCar();
                    cars.remove(car);
                }
            }
        }
    }

    public int getCarsLength(){
        return cars.size();
    }
}