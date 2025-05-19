package parking;

import parking.facility.*;
import vehicle.*;

public class ParkingLot{
    private final Space[][] floorPlan;

    public ParkingLot(int floorNumber, int spaceNumber){
        if (floorNumber < 1 || spaceNumber < 1){
            throw new IllegalArgumentException("Invalid number of spaces.");
        }
        else{
            this.floorPlan = new Space[floorNumber][spaceNumber];
            for (int i = 0; i < floorNumber; i++){
                for (int j = 0; j < spaceNumber; j++){
                    floorPlan[i][j] = new Space(i, j);
                }
            }
        }
    }

    public Space[][] getFloorPlan(){
        return this.floorPlan;
    }

    @Override
    public String toString() {
        StringBuilder emeletek = new StringBuilder();
        for (int i = 0; i < floorPlan.length; i++) {
            for (int j = 0; j < floorPlan[i].length; j++) {
                Space space = floorPlan[i][j];
                if (space.isTaken() == false){
                    emeletek.append("X");
                }
                else {
                    if (space.getOccupyingCarSize() == Size.SMALL){ 
                        emeletek.append("S");
                    }
                    else{
                        emeletek.append("L");
                    }
                }
                if( j != floorPlan[i].length - 1 ){
                    emeletek.append(" ");
                } 
            }
            if ( i != floorPlan.length - 1 ) {
                    emeletek.append("\n");
                }
        }
        return emeletek.toString();
    }
}