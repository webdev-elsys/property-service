package elsys.propertyapi.api.booking_api;

import java.time.LocalDate;

class BookingApiEndpoints {
    private static final String baseUrl = System.getenv("BOOKING_SERVICE_API_URL");
    private static final String reservations = "/reservations";

    public static String getBookedRooms(String propertyUuid, LocalDate checkInDate, LocalDate checkOutDate) {
        return baseUrl + reservations + "/booked-rooms?propertyUuid=" + propertyUuid + "&checkInDate=" + checkInDate + "&checkOutDate=" + checkOutDate;
    }
}
