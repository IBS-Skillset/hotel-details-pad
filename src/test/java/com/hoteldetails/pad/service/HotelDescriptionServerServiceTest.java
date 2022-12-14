package com.hoteldetails.pad.service;

import com.hotel.service.common.Context;
import com.hotel.service.common.ResponseStatus;
import com.hotel.service.description.HotelDescriptionRequest;
import com.hotel.service.description.HotelDescriptionResponse;
import com.hoteldetails.pad.exception.HotelException;
import com.hoteldetails.pad.mappers.common.response.ErrorResponseMapper;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@RunWith(MockitoJUnitRunner.class)
public class HotelDescriptionServerServiceTest {
    @Mock
    HotelDescriptionService hotelDescriptionService;
    @Mock
    ErrorResponseMapper errorResponseMapper;

    @InjectMocks
    private HotelDescriptionServerService hotelDescriptionServerService;

    @Test
    public void getHotelDescription() {
        HotelDescriptionRequest request = HotelDescriptionRequest.newBuilder()
                .setRequestContext(Context.newBuilder().setSupplierUrl("http://traveldoo.com").build()).build();
        HotelDescriptionResponse response = HotelDescriptionResponse.newBuilder()
                .setResponseStatus(ResponseStatus.newBuilder().setStatus(1).build()).build();
        StreamRecorder<HotelDescriptionResponse> responseObserver = StreamRecorder.create();
        when(hotelDescriptionService.getHotelDescriptionFromSupplier(request)).thenReturn(response);
        hotelDescriptionServerService.getHotelDescription(request, responseObserver);
        List<HotelDescriptionResponse> responseList = responseObserver.getValues();
        HotelDescriptionResponse hotelDescriptionResponse = responseList.get(0);
        assertThat(responseList).isNotEmpty();
        assertThat(hotelDescriptionResponse).isNotNull();
        assertThat(hotelDescriptionResponse.getResponseStatus().getStatus()).isEqualTo(1);
        verify(hotelDescriptionService, atLeast(1)).getHotelDescriptionFromSupplier(request);
    }

    @Test
    public void testForException() {
        String message = "message";
        String code = "code";
        HotelDescriptionRequest request = HotelDescriptionRequest.newBuilder()
                .setRequestContext(Context.newBuilder().setSupplierUrl("http://traveldoo.com").build()).build();
        StreamRecorder<HotelDescriptionResponse> responseObserver = StreamRecorder.create();
        ResponseStatus.Builder reponseStatusBuilder = ResponseStatus.newBuilder();
        reponseStatusBuilder.setErrorMessage("message");
        reponseStatusBuilder.setErrorCode("code");
        HotelException hotelException = new HotelException(message,code);
        when(hotelDescriptionService.getHotelDescriptionFromSupplier(request)).thenThrow(hotelException);
        when(errorResponseMapper.mapErrorResponse(hotelException.getMessage(),hotelException.getCode())).thenReturn(reponseStatusBuilder.build());
        hotelDescriptionServerService.getHotelDescription(request, responseObserver);
        hotelDescriptionServerService.getHotelDescription(request, responseObserver);
        List<HotelDescriptionResponse> responseList = responseObserver.getValues();
        HotelDescriptionResponse hotelDescriptionResponse = responseList.get(0);
        assertThat(responseList).isNotEmpty();
        assertThat(hotelDescriptionResponse).isNotNull();
        assertThat(hotelDescriptionResponse.getResponseStatus().getErrorMessage()).isEqualTo("message");
        assertThat(hotelDescriptionResponse.getResponseStatus().getErrorCode()).isEqualTo("code");
        verify(hotelDescriptionService,atLeast(1)).getHotelDescriptionFromSupplier(request);
        verify(errorResponseMapper,atLeast(1)).mapErrorResponse(message,code);
    }
}
