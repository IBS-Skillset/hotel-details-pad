package com.hoteldetails.pad.mappers.roomavailability.response;

import com.hotel.service.common.ResponseStatus;
import com.hotel.service.roomavailability.Rate;
import com.hotel.service.roomavailability.RoomAvailabilityResponse;
import com.hoteldetails.pad.mappers.common.response.ErrorResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.PropertyAvailabilityRS;
import org.opentravel.ota._2003._05.SuccessType;
import org.opentravel.ota._2003._05.ErrorType;
import org.opentravel.ota._2003._05.ArrayOfPropertyAvailabilityRSHotelRatesHotel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@ExtendWith(MockitoExtension.class)
class RoomAvailabilityResponseMapperTest {
    @Mock
    RateMapper rateMapper;
    @Mock
    ErrorResponseMapper errorResponseMapper;
    @InjectMocks
    RoomAvailabilityResponseMapper roomAvailabilityResponseMapper;

    @Test
    public void map() {
        PropertyAvailabilityRS response = getResponse();
        Rate.Builder ratePlan = Rate.newBuilder();
        when(rateMapper.map(response.getHotelRates().getHotel().get(0)
                .getBookingChannel().get(0).getRatePlan().get(0))).thenReturn(ratePlan.build());
        RoomAvailabilityResponse availResponse = roomAvailabilityResponseMapper.map(response);
        assertThat(availResponse).isNotNull();
        assertThat(availResponse.getResponseStatus().getStatus()).isEqualTo(1);
        assertThat(availResponse.getRateListList()).isNotEmpty();
        verify(rateMapper, atLeast(1)).map(response.getHotelRates().getHotel().get(0)
                .getBookingChannel().get(0).getRatePlan().get(0));
    }

    @Test
    public void testErrorCase() {
        PropertyAvailabilityRS response = new PropertyAvailabilityRS();
        SuccessType success = new SuccessType();
        success.setValue("FAILURE");
        response.setSuccess(success);
        ErrorsType errors = new ErrorsType();
        ErrorType error = new ErrorType();
        error.setCode("1234");
        error.setValue("room not available");
        errors.getError().add(error);
        response.setErrors(errors);
        ResponseStatus.Builder responseSatus = ResponseStatus.newBuilder();
        when(errorResponseMapper.mapErrorResponse(response.getErrors().getError().get(0).getCode(),
                response.getErrors().getError().get(0).getValue())).thenReturn(responseSatus.build());
        RoomAvailabilityResponse roomAvailabilityResponse = roomAvailabilityResponseMapper.map(response);
        assertThat(roomAvailabilityResponse).isNotNull();
        assertThat(response.getSuccess().getValue()).isEqualTo("FAILURE");
        assertThat(response.getErrors().getError().get(0).getCode()).isEqualTo("1234");
        verify(errorResponseMapper,atLeast(1)).mapErrorResponse(response.getErrors().getError().get(0).getCode(),
                response.getErrors().getError().get(0).getValue());
    }

    private PropertyAvailabilityRS getResponse() {
        PropertyAvailabilityRS response = new PropertyAvailabilityRS();
        SuccessType success = new SuccessType();
        success.setValue("SUCCESS");
        response.setSuccess(success);
        ArrayOfPropertyAvailabilityRSHotelRatesHotel hotelRatesHotel = new ArrayOfPropertyAvailabilityRSHotelRatesHotel();
        ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel hotel = new ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel();
        ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel bookingChannel = new ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel();
        ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan ratePlan = new ArrayOfPropertyAvailabilityRSHotelRatesHotel.Hotel.BookingChannel.RatePlan();
        bookingChannel.getRatePlan().add(ratePlan);
        hotel.getBookingChannel().add(bookingChannel);
        hotelRatesHotel.getHotel().add(hotel);
        response.setHotelRates(hotelRatesHotel);
        return response;
    }
}