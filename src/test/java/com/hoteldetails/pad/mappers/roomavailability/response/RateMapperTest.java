package com.hoteldetails.pad.mappers.roomavailability.response;

import com.hotel.service.roomavailability.Rate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ArrayOfPropertyAvailabilityRSHotelRatesHotel;
import org.opentravel.ota._2003._05.TPAExtensionsType;

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
        ratePlan.setRatePerDay(ratePerDay);
        ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan.TotalAmount totalAmount =
                new ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan.TotalAmount();
        totalAmount.setAmount("1234");
        ratePlan.setTotalAmount(totalAmount);

        ratePlan.setRoomsAvailable("Rooms Available");
        ratePlan.setRateTypeCode("Rate type code");
        ratePlan.setRateDetails(rateDetails);
        TPAExtensionsType tpaExtensionsType = new TPAExtensionsType();
        tpaExtensionsType.setBreakfastIncluded("BreakFast Included ");
        ratePlan.setTPAExtensions(tpaExtensionsType);
        return ratePlan;
    }
}
