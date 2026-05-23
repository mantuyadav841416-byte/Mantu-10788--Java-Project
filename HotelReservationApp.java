/*
 * =====================================================================
 *                HOTEL ROOM RESERVATION SYSTEM
 * =====================================================================
 * File Name : HotelReservationApp.java
 * =====================================================================
 */

// ======================= SERVICE INTERFACE =======================

interface Service {

    double calculateBill(int days, double rate);
}

// =========================== ROOM CLASS ==========================

class Room {

    private int roomNumber;
    private String roomType;
    private double pricePerDay;
    private boolean isAvailable;

    // Constructor
    public Room(int roomNumber, String roomType, double pricePerDay) {

        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerDay = pricePerDay;
        this.isAvailable = true;
    }

    // Getter Methods
    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Book Room
    public void bookRoom() {
        isAvailable = false;
    }

    // Cancel Booking
    public void cancelBooking() {
        isAvailable = true;
    }
}

// ============================ HOTEL CLASS ============================

class Hotel implements Service {

    private Room[][] rooms;
    private int floors;
    private int roomsPerFloor;

    // Constructor
    public Hotel(int floors, int roomsPerFloor) {

        this.floors = floors;
        this.roomsPerFloor = roomsPerFloor;

        rooms = new Room[floors][roomsPerFloor];

        initializeRooms();
    }

    // Initialize Rooms
    private void initializeRooms() {

        int roomNumber = 101;

        for (int i = 0; i < floors; i++) {

            for (int j = 0; j < roomsPerFloor; j++) {

                String type;
                double price;

                if (j % 3 == 0) {

                    type = "Single";
                    price = 1500;

                } else if (j % 3 == 1) {

                    type = "Double";
                    price = 2500;

                } else {

                    type = "Deluxe";
                    price = 4000;
                }

                rooms[i][j] = new Room(roomNumber, type, price);

                roomNumber++;
            }
        }
    }

    // ================= BOOK ROOM =================

    public void bookRoom(int floor, int roomIndex) {

        if (!isValidRoom(floor, roomIndex)) {

            System.out.println("Invalid Room Selection!");
            return;
        }

        Room room = rooms[floor][roomIndex];

        if (room.isAvailable()) {

            room.bookRoom();

            System.out.println("Room "
                    + room.getRoomNumber()
                    + " booked successfully.");

        } else {

            System.out.println("Room "
                    + room.getRoomNumber()
                    + " is already occupied.");
        }
    }

    // ================= CANCEL BOOKING =================

    public void cancelBooking(int floor, int roomIndex) {

        if (!isValidRoom(floor, roomIndex)) {

            System.out.println("Invalid Room Selection!");
            return;
        }

        Room room = rooms[floor][roomIndex];

        if (!room.isAvailable()) {

            room.cancelBooking();

            System.out.println("Booking cancelled for Room "
                    + room.getRoomNumber());

        } else {

            System.out.println("Room "
                    + room.getRoomNumber()
                    + " is already available.");
        }
    }

    // ================= BILL CALCULATION =================

    @Override
    public double calculateBill(int days, double rate) {

        return days * rate;
    }

    // ================= ROOM STATISTICS =================

    public void displayStatistics() {

        int available = 0;
        int occupied = 0;

        for (int i = 0; i < floors; i++) {

            for (int j = 0; j < roomsPerFloor; j++) {

                if (rooms[i][j].isAvailable()) {

                    available++;

                } else {

                    occupied++;
                }
            }
        }

        System.out.println("\nHOTEL STATISTICS");
        System.out.println("Total Rooms : "
                + (floors * roomsPerFloor));

        System.out.println("Available Rooms : "
                + available);

        System.out.println("Occupied Rooms : "
                + occupied);
    }

    // ================= GET ROOM =================

    public Room getRoom(int floor, int roomIndex) {

        if (!isValidRoom(floor, roomIndex)) {
            return null;
        }

        return rooms[floor][roomIndex];
    }

    // ================= VALIDATION =================

    private boolean isValidRoom(int floor, int roomIndex) {

        return floor >= 0
                && floor < floors
                && roomIndex >= 0
                && roomIndex < roomsPerFloor;
    }
}

// ======================= MAIN CLASS ========================

public class HotelReservationApp {

    public static void main(String[] args) {

        // Create Hotel Object
        Hotel hotel = new Hotel(3, 4);

        // ================= BOOKING =================

        hotel.bookRoom(0, 0); // Room 101
        hotel.bookRoom(0, 1); // Room 102

        // Already Occupied Room
        hotel.bookRoom(0, 0);

        // ================= BILLING =================

        Room bookedRoom = hotel.getRoom(0, 1);

        if (bookedRoom != null
                && !bookedRoom.isAvailable()) {

            int days = 3;

            double basicBill = hotel.calculateBill(
                    days,
                    bookedRoom.getPricePerDay());

            System.out.println("Basic Bill for "
                    + days
                    + " Days = Rs."
                    + basicBill);
        }

        // ================= CANCELLATION =================

        hotel.cancelBooking(0, 0);

        // ================= STATISTICS =================

        hotel.displayStatistics();
    }
}