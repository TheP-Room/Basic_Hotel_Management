
/**
 * 
 * Class that represents the manager of hotel, and have related fields
 * and methods for managing and interacting with customer, all the fields
 * and methods are set private as they are part of internal management
 * the constructor is needed only to interact
 *
 * @author TheP-Room
 * @version Java 24.0.2
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;

public class Manager extends Hotel implements Serializable
{
    private String name;
    private String customerName;
    private transient final Scanner sc = new Scanner(System.in);
    private ArrayList<Room> roomsToShow = new ArrayList<>();
    private ArrayList<Room> selectedRooms = new ArrayList<>();
    private ArrayList<Room> beforeCheckoutRooms = new ArrayList<>();
    private double customerBill;
    private boolean isCheckOut;
    private boolean isSelectionSuccess = true;
    private transient CanUpload backUp;
    
    public Manager(String name, CanUpload backUp){
        this.backUp = backUp;
        if (loadState()) {
            System.out.println("Welcome to "+getHotelName()+"!");
            System.out.println("I am "+this.name+
                " (The Manager), How can I help you "+customerName+"?");
        }
        else {
            this.name = name;
            addRooms();
            System.out.println("Welcome to "+getHotelName()+"!");
            System.out.print("Enter your Name : ");
            dealWith(sc.nextLine());
            System.out.println("I am "+this.name+
                " (The Manager), How can I help you "+customerName+"?");
        }
        returnToMainMenu();
    }
    
    private void dealWith(String name) {
        customerName = name;
    }
    
    private void returnToMainMenu() {
        while (true) {
            menu();
            var choice = getChoice();
            if (choice == 5) {
                System.out.println("\nVisit Again!\n");
                saveState();
                return;
            }
            mainMenuOutput(choice);
        }
    }
    
    private void saveState() {
        roomsToShow.addAll(rooms);
        backUp.createBackup(this);
    }
    
    private boolean loadState() {
        Manager saved = backUp.loadBackup(this);
        if (saved == null)
            return false;
        setThis(saved);
        return true;
    }
    
    private void setThis(Manager saved) {
        this.name = saved.name;
        this.customerName = saved.customerName;
        this.rooms = saved.roomsToShow;
        this.noOfRooms = rooms.size();
        this.selectedRooms = saved.selectedRooms;
        this.beforeCheckoutRooms = saved.beforeCheckoutRooms;
        this.customerBill = saved.customerBill;
        this.isCheckOut = saved.isCheckOut;
        this.isSelectionSuccess = saved.isSelectionSuccess;
    }
    
    private void menu() {
        System.out.println("1. Get Room Details");
        System.out.println("2. Select a Room");
        System.out.println("3. Book Selection");
        System.out.println("4. Checkout");
        System.out.println("5. Exit");
        System.out.print(">> ");
    }
    
    private byte getChoice(){
        return sc.nextByte();
    }
    
    private void mainMenuOutput(byte choice) {
        switch (choice) {
            case 1:
                System.out.println("\nAvailable Rooms : ");
                getRoomDetails();
                System.out.println();
                break;
            case 2:
                System.out.println("\nFollow the Instructions\n");
                selectRooms();
                if (isSelectionSuccess)
                    viewSelection();
                isSelectionSuccess = true;
                break;
            case 3:
                confirmSelection();
                break;
            case 4:
                if (customerBill <= 0)
                    System.out.println("\nNo bookings were made.\n");
                else {
                    System.out.println("\nBill Paid : $"+customerBill);
                    System.out.println("Checkout Successfull.\n");
                    isCheckOut = true;
                    abortSelection('N');
                }
                break;
            default:
                System.out.println("\nCan't get it.\n");
        }
    }
    
    private void selectRooms() {
        System.out.print("No of Rooms required : (1-"+getNoOfRooms()+") ");
        byte reqRooms = sc.nextByte();
        if (reqRooms > (byte) getNoOfRooms() || reqRooms <= 0) {
            System.out.println("\nSorry, we don't have that many rooms.\n");
            isSelectionSuccess = false;
            return;
        }
        for (byte i = 0; i < reqRooms; i++){
            System.out.print("\nRoom Type : (Simple / Luxury / Deluxe) ");
            char roomType = sc.next().toUpperCase().charAt(0);
            System.out.print("Capacity : (Single / Double) ");
            char capacity = sc.next().toUpperCase().charAt(0);
            boolean isSingle = (capacity == 'S') ? true : false;
            var unverifiedSelection = checkAvailability(roomType, isSingle);
            if (unverifiedSelection != null){
                selectedRooms.add(unverifiedSelection);
                unverifiedSelection.changeAvailability();
                System.out.println();
            }
            else
                System.out.println("\nSorry, this room is not available.\n");
        }
    }
    
    private void viewSelection() {
        if (selectedRooms.isEmpty())
            return;
        System.out.println("\nYour selection : ");
        for (var room : selectedRooms) {
            System.out.println(room.toStringWOAvailability());
            customerBill += room.getRoomCharge();
        }
        System.out.println("Total Bill : $"+customerBill+"\n");
    }
    
    private void confirmSelection() {
        if (selectedRooms == null || selectedRooms.isEmpty()) {
            System.out.println("\nNo Rooms Selected!\n");
            return;
        }
        System.out.println("\nConfirm booking for selected Rooms");
        System.out.println("Your selection : ");
        for (var room : selectedRooms)
            System.out.println(room.toStringWOAvailability());
        System.out.println("Total Bill : $"+customerBill+"\n");
        getBookingDuration();
        System.out.println("Updated Bill : $"+customerBill+"\n");
        System.out.println("Do you wish to continue?");
        System.out.print(">> (Yes / No) ");
        char choice = sc.next().toUpperCase().charAt(0);
        if (choice == 'Y') {
            System.out.println("\nBooking Successful! Here is the Key.\n");
            beforeCheckoutRooms.addAll(selectedRooms);
        }
        else
            System.out.println("\nBooking Cancel!\n");
        abortSelection(choice);
    }
    
    private void abortSelection(char choice) {
        if (customerBill <= 0) {
            System.out.println("\nNo Rooms Selected!\n");
            return;
        }
        if (choice != 'Y') {
            for (var room : selectedRooms)
                room.changeAvailability();
            if (isCheckOut && !beforeCheckoutRooms.isEmpty()) {
                for (var room : beforeCheckoutRooms)
                    room.changeAvailability();
                beforeCheckoutRooms.clear();
            }
            customerBill = 0;
        }
        selectedRooms.clear();
    }
    
    private void getBookingDuration() {
        System.out.print("Booking Duration (No of Days) : ");
        customerBill = setBookingDuration(sc.nextInt());
    }
    
    private double setBookingDuration(int bookingDays) {
        if (bookingDays <= 0)
            throw new IllegalArgumentException("Can't be less than 1");
        return customerBill * (double) bookingDays;
    }
}