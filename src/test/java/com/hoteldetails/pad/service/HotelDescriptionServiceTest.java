package com.hoteldetails.pad.service;

import com.hotel.service.description.HotelDescriptionRequest;
import com.hotel.service.description.HotelDescriptionResponse;
import com.hoteldetails.pad.client.DjocaClient;
import com.hoteldetails.pad.exception.HotelException;
import com.hoteldetails.pad.mappers.hoteldescription.request.DescriptionRequestMapper;
import com.hoteldetails.pad.mappers.hoteldescription.response.DescriptionResponseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQ;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRS;
import javax.xml.bind.JAXBException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@RunWith(MockitoJUnitRunner.class)
public class HotelDescriptionServiceTest {
    private String SERVICE = "hotel-descriptive-info";
    @Mock
    private DescriptionRequestMapper descriptionRequestMapper;
    @Mock
    private DjocaClient djocaClient;
    @InjectMocks
    private HotelDescriptionService hotelDescriptionService;
    @Mock
    private DescriptionResponseMapper descriptionResponseMapper;

    @Test
    public void getHotelDescriptionFromSupplier() throws JAXBException {
        OTAHotelDescriptiveInfoRQ hotelDescriptiveInfoRQ = new OTAHotelDescriptiveInfoRQ();
        OTAHotelDescriptiveInfoRS hotelDescriptiveInfoRS = new OTAHotelDescriptiveInfoRS();
        HotelDescriptionResponse.Builder descriptionResponse = HotelDescriptionResponse.newBuilder();
        HotelDescriptionRequest request = getRequest();
        when(descriptionRequestMapper.map(request)).thenReturn(hotelDescriptiveInfoRQ);
        when(djocaClient.restClient(hotelDescriptiveInfoRQ, request.getRequestContext().getSupplierUrl(), SERVICE)).thenReturn(hotelDescriptiveInfoRS);
        when(descriptionResponseMapper.map(hotelDescriptiveInfoRS)).thenReturn(descriptionResponse.build());
        HotelDescriptionResponse response = hotelDescriptionService.getHotelDescriptionFromSupplier(request);
        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(HotelDescriptionResponse.newBuilder().build());
        verify(descriptionRequestMapper, atLeast(1)).map(request);
        verify(djocaClient, atLeast(1)).restClient(hotelDescriptiveInfoRQ, request.getRequestContext().getSupplierUrl(), SERVICE);
        verify(descriptionResponseMapper, atLeast(1)).map(hotelDescriptiveInfoRS);
    }

    @Test(expected = HotelException.class)
    public void getHotelDescriptionFromSupplierError() throws JAXBException {
        OTAHotelDescriptiveInfoRQ hotelDescriptiveInfoRQ = new OTAHotelDescriptiveInfoRQ();
        HotelDescriptionRequest request = getRequest();
        when(descriptionRequestMapper.map(request)).thenReturn(hotelDescriptiveInfoRQ);
        when(djocaClient.restClient(hotelDescriptiveInfoRQ, request.getRequestContext().getSupplierUrl(), SERVICE)).thenReturn(JAXBException.class);
        HotelDescriptionResponse response = hotelDescriptionService.getHotelDescriptionFromSupplier(request);
        assertThat(response).isNull();
        verify(descriptionRequestMapper, atLeast(1)).map(request);
        verify(djocaClient, atLeast(1)).restClient(hotelDescriptiveInfoRQ, request.getRequestContext().getSupplierUrl(), SERVICE);
    }

    private HotelDescriptionRequest getRequest() {
        HotelDescriptionRequest request = HotelDescriptionRequest.newBuilder().build();
        return request;
    }
}
