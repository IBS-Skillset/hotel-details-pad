package com.hoteldetails.pad.service;

import com.hotel.service.roomavailability.RoomAvailabilityRequest;
import com.hotel.service.roomavailability.RoomAvailabilityResponse;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RoomAvailabilityServerServiceTest {

    @Mock
    RoomAvailabilityService roomAvailabilityService;

    @InjectMocks
    private RoomAvailabilityServerService roomAvailabilityServerService;

    @Test
    void getRoomAvailability() {
        RoomAvailabilityRequest.Builder request = RoomAvailabilityRequest.newBuilder();
        StreamRecorder<RoomAvailabilityResponse> responseObserver = StreamRecorder.create();
        roomAvailabilityServerService.getRoomAvailability(request.build(), responseObserver);
        List<RoomAvailabilityResponse> responseList = responseObserver.getValues();
        RoomAvailabilityResponse roomAvailabilityResponse = responseList.get(0);
        assertThat(responseList).isNotEmpty();
        assertThat(roomAvailabilityResponse).isNull();
    }
}