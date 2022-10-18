package com.hoteldetails.pad.service;

import com.hotel.service.common.Context;
import com.hotel.service.common.ResponseStatus;
import com.hotel.service.description.HotelDescriptionRequest;
import com.hotel.service.description.HotelDescriptionResponse;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeast;

@ExtendWith(MockitoExtension.class)
public class HotelDescriptionServerServiceTest {
    @Mock
    HotelDescriptionService hotelDescriptionService;
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
}
