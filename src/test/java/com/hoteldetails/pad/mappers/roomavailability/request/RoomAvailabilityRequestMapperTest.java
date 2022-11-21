package com.hoteldetails.pad.mappers.roomavailability.request;

import com.hotel.service.common.Context;
import com.hotel.service.roomavailability.RoomAvailabilityRequest;
import com.hoteldetails.pad.mappers.common.request.PosMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ArrayOfSourceType;
import org.opentravel.ota._2003._05.PropertyAvailabilityRQ;
import org.opentravel.ota._2003._05.SourceType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@ExtendWith(MockitoExtension.class)
class RoomAvailabilityRequestMapperTest {
    @Mock
    private PosMapper posMapper;
    @InjectMocks
    public RoomAvailabilityRequestMapper roomAvailabilityRequestMapper;

    @Test
    public void map() {
        RoomAvailabilityRequest roomAvailabilityRequest = getRoomAvailabilityRequest();
        ArrayOfSourceType arrayOfSourceType = new ArrayOfSourceType();
        SourceType sourceType = new SourceType();
        SourceType.RequestorID requestorID = new SourceType.RequestorID();
        requestorID.setID("1234");
        requestorID.setLanguageCode("FRE");
        sourceType.setRequestorID(requestorID);
        arrayOfSourceType.getSource().add(sourceType);
        when(posMapper.mapPOS(roomAvailabilityRequest.getRequestContext(), roomAvailabilityRequest.getLanguageCode())).thenReturn(arrayOfSourceType);
        PropertyAvailabilityRQ response = roomAvailabilityRequestMapper.map(roomAvailabilityRequest);
        assertThat(response).isNotNull();
        assertThat(response.getPOS().getSource().get(0).getRequestorID().getID()).isEqualTo("1234");
        assertThat(response.getPOS().getSource().get(0).getRequestorID().getLanguageCode()).isEqualTo("FRE");
        verify(posMapper, atLeast(1)).mapPOS(roomAvailabilityRequest.getRequestContext(), roomAvailabilityRequest.getLanguageCode());
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