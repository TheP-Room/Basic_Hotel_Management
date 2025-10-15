
/**
 * 
 * Class that contains basic fields and getters setters and print
 * methods regarding a room
 *
 * @author TheP-Room
 * @version Java 24.0.2
 */

import java.io.Serializable;

public class Room implements Serializable
{
    private int roomNo;
    private char roomType;
    private boolean isSingleBed;
    private boolean isOccupied;
    private double chargePerDay = 1_100;
    
    public Room(
        int roomNo,
        char roomType,
        boolean isSingleBed,
        boolean isOccupied) {
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.isSingleBed = isSingleBed;
        this.isOccupied = isOccupied;
        this.chargePerDay = ((roomType == 'L') ? this.chargePerDay+900 :
            (roomType == 'D') ? this.chargePerDay+1_200 : this.chargePerDay);
        this.chargePerDay = ((isSingleBed) ? this.chargePerDay :
            (2*this.chargePerDay)-100);
    }
    
    public boolean isAvailable(){
        return !this.isOccupied;
    }
    
    public char getRoomType() {
        return this.roomType;
    }
    
    public boolean getCapacity() {
        return this.isSingleBed;
    }
    
    public double getRoomCharge() {
        return this.chargePerDay;
    }
    
    public void changeAvailability() {
        this.isOccupied = (this.isOccupied) ? false : true;
    }
    
    @Override
    public String toString() {
        return "Room No : "+this.roomNo+" , Room Type : "+
            ((this.roomType == 'S') ? "Simple" :
                (this.roomType == 'L') ? "Luxury" :
                    "Deluxe")+" , Capacity : "+
                        ((isSingleBed) ? "Single" : "Double")+
                            " , Availability : "+
                                ((isOccupied) ? "Not Available" : "Available")
                                    +" , Charge : $"+this.chargePerDay;
    }
    
    public String toStringWOAvailability() {
        return "Room No : "+this.roomNo+" , Room Type : "+
            ((this.roomType == 'S') ? "Simple" :
                (this.roomType == 'L') ? "Luxury" :
                    "Deluxe")+" , Capacity : "+
                        ((isSingleBed) ? "Single" : "Double")+
                            " , Availability : Available"+
                                    " , Charge : $"+this.chargePerDay;
    }
}