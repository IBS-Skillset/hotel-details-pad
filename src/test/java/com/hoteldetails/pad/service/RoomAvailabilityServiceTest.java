package com.hoteldetails.pad.service;

import com.hotel.service.roomavailability.RoomAvailabilityRequest;
import com.hotel.service.roomavailability.RoomAvailabilityResponse;
import com.hoteldetails.pad.client.DjocaClient;
import com.hoteldetails.pad.mappers.roomavailability.request.RoomAvailabilityRequestMapper;
import com.hoteldetails.pad.mappers.roomavailability.response.RoomAvailabilityResponseMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.PropertyAvailabilityRQ;
import org.opentravel.ota._2003._05.PropertyAvailabilityRS;
import javax.xml.bind.JAXBException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class RoomAvailabilityServiceTest {
    private String SERVICE = "property-availability";

    @Mock
    private DjocaClient djocaClient;
    @InjectMocks
    private RoomAvailabilityService roomAvailabilityService;
    @Mock
    RoomAvailabilityResponseMapper roomAvailabilityResponseMapper;

    @Mock
    RoomAvailabilityRequestMapper roomAvailabilityRequestMapper;

    @Test
    void getRoomAvailabilityFromSupplier() {
        PropertyAvailabilityRQ propertyAvailabilityRQ =new PropertyAvailabilityRQ();
        PropertyAvailabilityRS propertyAvailabilityRS=new PropertyAvailabilityRS();

        RoomAvailabilityRequest request = RoomAvailabilityRequest.newBuilder().build();
        when(roomAvailabilityRequestMapper.map(any())).thenReturn(mock(PropertyAvailabilityRQ.class));
        try {
            lenient().when(djocaClient.restClient(propertyAvailabilityRQ,request.getRequestContext().getSupplierUrl(),SERVICE)).thenReturn(propertyAvailabilityRS);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        lenient().when(roomAvailabilityResponseMapper.map(propertyAvailabilityRS)).thenReturn(RoomAvailabilityResponse.newBuilder().build());
        RoomAvailabilityResponse response =roomAvailabilityService.getRoomAvailabilityFromSupplier(request);
        Assertions.assertThat(response).isNull();

    }
}

