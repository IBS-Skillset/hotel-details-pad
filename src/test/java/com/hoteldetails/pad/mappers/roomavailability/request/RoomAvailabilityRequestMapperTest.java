package com.hoteldetails.pad.mappers.roomavailability.request;

import com.hotel.service.common.Context;
import com.hotel.service.roomavailability.RoomAvailabilityRequest;
import com.hoteldetails.pad.mappers.common.request.PosMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.PropertyAvailabilityRQ;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RoomAvailabilityRequestMapperTest {
    @Mock
    private PosMapper posMapper;
    @InjectMocks
    public RoomAvailabilityRequestMapper roomAvailabilityRequestMapper;

    @Test
    public void map() {
        RoomAvailabilityRequest roomAvailabilityRequest = getRoomAvailabilityRequest();
        PropertyAvailabilityRQ propertyAvailabilityRQ = roomAvailabilityRequestMapper.map(roomAvailabilityRequest);
        assertThat(propertyAvailabilityRQ).isNotNull();
    }

    public RoomAvailabilityRequest getRoomAvailabilityRequest() {

        RoomAvailabilityRequest.Builder requestBuilder = RoomAvailabilityRequest.newBuilder();
        Context.Builder context = Context.newBuilder();
        context.setSupplierRequestorId("requestId")
                .setSupplierCredential("credential")
                .build();
        requestBuilder
                .setLanguageCode("FRE")
                .setRoomCount(1)
                .setOccupancy(2)
                .setStartDate("2022-11-18")
                .setEndDate("2022-11-25")
                .setHotelCode("2149846")
                .setRequestContext(context);
        return requestBuilder.build();

    }
}