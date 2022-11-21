package com.hoteldetails.pad.service;

import com.hotel.service.common.Context;
import com.hotel.service.common.ResponseStatus;
import com.hotel.service.roomavailability.RoomAvailabilityRequest;
import com.hotel.service.roomavailability.RoomAvailabilityResponse;
import com.hoteldetails.pad.exception.HotelException;
import com.hoteldetails.pad.mappers.common.response.ErrorResponseMapper;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@RunWith(MockitoJUnitRunner.class)
public class RoomAvailabilityServerServiceTest {
    @Mock
    RoomAvailabilityService roomAvailabilityService;
    @Mock
    ErrorResponseMapper errorResponseMapper;

    @InjectMocks
    private RoomAvailabilityServerService roomAvailabilityServerService;

    @Test
    public void getRoomAvailability() {
        RoomAvailabilityRequest request = RoomAvailabilityRequest.newBuilder()
                .setRequestContext(Context.newBuilder().setSupplierUrl("http://traveldoo.com").build()).build();
        RoomAvailabilityResponse response = RoomAvailabilityResponse.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(1).build()).build();
        StreamRecorder<RoomAvailabilityResponse> responseObserver = StreamRecorder.create();
        when(roomAvailabilityService.getRoomAvailabilityFromSupplier(request)).thenReturn(response);
        roomAvailabilityServerService.getRoomAvailability(request, responseObserver);
        List<RoomAvailabilityResponse> responseList = responseObserver.getValues();
        RoomAvailabilityResponse roomAvailabilityResponse = responseList.get(0);
        assertThat(responseList).isNotEmpty();
        assertThat(roomAvailabilityResponse).isNotNull();
        assertThat(roomAvailabilityResponse.getResponseStatus().getStatus()).isEqualTo(1);
        verify(roomAvailabilityService, atLeast(1)).getRoomAvailabilityFromSupplier(request);
    }

    @Test
    public void testForException(){
        String message = "message";
        String code ="code";
        RoomAvailabilityRequest request = RoomAvailabilityRequest.newBuilder()
                .setRequestContext(Context.newBuilder().setSupplierUrl("http://traveldoo.com").build()).build();
        StreamRecorder<RoomAvailabilityResponse> responseObserver = StreamRecorder.create();
        ResponseStatus.Builder reponseStatusBuilder = ResponseStatus.newBuilder();
        reponseStatusBuilder.setErrorMessage("message");
        reponseStatusBuilder.setErrorCode("code");
        HotelException hotelException = new HotelException(message,code);
        when(roomAvailabilityService.getRoomAvailabilityFromSupplier(request)).thenThrow(hotelException);
        when(errorResponseMapper.mapErrorResponse(hotelException.getMessage(),hotelException.getCode())).thenReturn(reponseStatusBuilder.build());
        roomAvailabilityServerService.getRoomAvailability(request, responseObserver);
        List<RoomAvailabilityResponse> responseList = responseObserver.getValues();
        RoomAvailabilityResponse roomAvailabilityResponse = responseList.get(0);
        assertThat(responseList).isNotEmpty();
        assertThat(roomAvailabilityResponse).isNotNull();
        assertThat(roomAvailabilityResponse.getResponseStatus().getErrorMessage()).isEqualTo("message");
        assertThat(roomAvailabilityResponse.getResponseStatus().getErrorCode()).isEqualTo("code");
        verify(roomAvailabilityService, atLeast(1)).getRoomAvailabilityFromSupplier(request);
        verify(errorResponseMapper,atLeast(1)).mapErrorResponse(message,code);
    }

}



