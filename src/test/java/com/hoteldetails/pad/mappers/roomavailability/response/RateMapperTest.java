package com.hoteldetails.pad.mappers.roomavailability.response;

import com.hotel.service.roomavailability.Rate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ArrayOfPropertyAvailabilityRSHotelRatesHotel;
import org.opentravel.ota._2003._05.TPAExtensionsType;

import static com.hoteldetails.pad.util.ApiConstants.FALSE;
import static com.hoteldetails.pad.util.ApiConstants.TRUE;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RateMapperTest {
    @InjectMocks
    RateMapper rateMapper;

    @Test
    public void map() {
        ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan ratePlan = getRate();
        Rate rate = rateMapper.map(ratePlan);
        assertThat(rate).isNotNull();
        assertThat(rate.getAmount()).isEqualTo(1234.0);
        assertThat(rate.getTotalAmount()).isEqualTo(1234.0);
        assertThat(rate.getCurrency()).isEqualTo("EUR");
        assertThat(rate.getAvailable()).isEqualTo("Rooms Available");
        assertThat(rate.getRateType()).isEqualTo("A");
        assertThat(rate.getRatePlan()).isEqualTo("1001");
        assertThat(rate.getBookingCode()).isEqualTo("1001");
        assertThat(rate.getBreakFastDetails(0).getBreakfast()).isEqualTo("Breakfast details");
        assertThat(rate.getRateCategory()).isEqualTo("Rate Description");
        assertThat(rate.getRoomDescriptionList(0).getDescription()).isEqualTo("room rate description");
        assertThat(rate.getIsBreakfastIncluded()).isTrue();
        assertThat(rate.getIsCancellable()).isEqualTo(true);
    }

    private ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan getRate() {
        ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan ratePlan =
                new ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan();
        ratePlan.setID("123");
        ratePlan.setRatePlanType(10);
        ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan.RateDetails rateDetails =
                new ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan.RateDetails();
        ratePlan.setRateDetails(rateDetails);
        ratePlan.setRateDescription("Rate Description");
        ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan.RatePerDay ratePerDay =
                new ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan.RatePerDay();
        ratePerDay.setAmount("1234");
        ratePerDay.setCurrencyCode("EUR");
        ratePlan.setRatePerDay(ratePerDay);
        ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan.TotalAmount totalAmount =
                new ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan.TotalAmount();
        totalAmount.setAmount("1234");
        ratePlan.setTotalAmount(totalAmount);
        ratePlan.setRoomsAvailable("Rooms Available");
        ratePlan.setID("1001");
        rateDetails.setBreakfast("Breakfast details");
        ratePlan.setRateDetails(rateDetails);
        ratePlan.setRatePlanType(10);
        ratePlan.setRoomRateDescription("room rate description");
        TPAExtensionsType tpaExtensionsType = new TPAExtensionsType();
        tpaExtensionsType.setBreakfastIncluded(TRUE);
        tpaExtensionsType.setNonRefundable(FALSE);
        ratePlan.setTPAExtensions(tpaExtensionsType);
        return ratePlan;
    }
}
