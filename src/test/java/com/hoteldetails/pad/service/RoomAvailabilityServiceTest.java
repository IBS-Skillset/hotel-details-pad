package com.hoteldetails.pad.service;

import com.hotel.service.roomavailability.RoomAvailabilityRequest;
import com.hotel.service.roomavailability.RoomAvailabilityResponse;
import com.hoteldetails.pad.client.DjocaClient;
import com.hoteldetails.pad.exception.HotelException;
import com.hoteldetails.pad.mappers.roomavailability.request.RoomAvailabilityRequestMapper;
import com.hoteldetails.pad.mappers.roomavailability.response.RoomAvailabilityResponseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.PropertyAvailabilityRQ;
import org.opentravel.ota._2003._05.PropertyAvailabilityRS;
import javax.xml.bind.JAXBException;
import static com.hoteldetails.pad.util.ApiConstants.ROOMSERVICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@RunWith(MockitoJUnitRunner.class)
public class RoomAvailabilityServiceTest {
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
   public void getRoomAvailabilityFromSupplier()throws JAXBException {
        PropertyAvailabilityRQ propertyAvailabilityRQ = new PropertyAvailabilityRQ();
        PropertyAvailabilityRS propertyAvailabilityRS = new PropertyAvailabilityRS();
        RoomAvailabilityRequest request = getRequest();
        RoomAvailabilityResponse.Builder responseBuilder = RoomAvailabilityResponse.newBuilder();
        when(roomAvailabilityRequestMapper.map(request)).thenReturn(propertyAvailabilityRQ);
        when(djocaClient.restClient(propertyAvailabilityRQ, request.getRequestContext().getSupplierUrl(), ROOMSERVICE)).thenReturn(propertyAvailabilityRS);
        when(roomAvailabilityResponseMapper.map(propertyAvailabilityRS)).thenReturn(responseBuilder.build());
        RoomAvailabilityResponse response = roomAvailabilityService.getRoomAvailabilityFromSupplier(request);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(RoomAvailabilityResponse.newBuilder().build());
        verify(roomAvailabilityRequestMapper, atLeast(1)).map(request);
        verify(djocaClient, atLeast(1)).restClient(propertyAvailabilityRQ, request.getRequestContext().getSupplierUrl(), ROOMSERVICE);
        verify(roomAvailabilityResponseMapper, atLeast(1)).map(propertyAvailabilityRS);
    }

    @Test(expected = HotelException.class)
    public void getHotelDescriptionFromSupplierError() throws JAXBException {
        PropertyAvailabilityRQ propertyAvailabilityRQ = new PropertyAvailabilityRQ();
        RoomAvailabilityRequest request = getRequest();
        when(roomAvailabilityRequestMapper.map(request)).thenReturn(propertyAvailabilityRQ);
        when(djocaClient.restClient(propertyAvailabilityRQ, request.getRequestContext().getSupplierUrl(), ROOMSERVICE)).thenReturn(JAXBException.class);
        RoomAvailabilityResponse response = roomAvailabilityService.getRoomAvailabilityFromSupplier(request);
        assertThat(response).isNull();
        verify(roomAvailabilityRequestMapper, atLeast(1)).map(request);
        verify(djocaClient, atLeast(1)).restClient(propertyAvailabilityRQ, request.getRequestContext().getSupplierUrl(), ROOMSERVICE);
    }
    private  RoomAvailabilityRequest getRequest(){
        RoomAvailabilityRequest request = RoomAvailabilityRequest.newBuilder().build();
        return request;
    }
}

