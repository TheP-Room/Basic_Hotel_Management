
/**
 * 
 * Abstract class with fields and methods related to a Hotel, such as
 * hotelName, noOfRooms, addingRooms(), roomAvailability()
 *
 * @author TheP-Room
 * @version Java 24.0.2
 */

import java.util.ArrayList;

public abstract class Hotel
{
    private String name = "Hotel SunShine";
    protected int noOfRooms;
    protected ArrayList<Room> rooms = new ArrayList<>();
    
    protected String getHotelName() {
        return name;
    }
    
    protected void addRooms() {
        rooms.add(new Room(101,'S',true,false));
        rooms.add(new Room(102,'S',false,false));
        rooms.add(new Room(103,'L',true,false));
        rooms.add(new Room(104,'L',false,false));
        rooms.add(new Room(105,'D',true,false));
        rooms.add(new Room(106,'D',false,false));
        noOfRooms = rooms.size();
    }
    
    protected void getRoomDetails() {
        for (var room : rooms)
            System.out.println(room);
    }
    
    protected int getNoOfRooms() {
        return noOfRooms;
    }
    
    protected Room checkAvailability(char roomType, boolean isSingle) {
        for (var room : rooms)
            if (room.getRoomType() == roomType &&
                room.getCapacity() == isSingle)
                if (room.isAvailable())
                    return room;
        return null;
    }
}