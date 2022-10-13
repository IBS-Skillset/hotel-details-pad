package com.hoteldetails.pad.service;

import com.hotel.service.roomavailability.HotelRoomAvailabilityServiceGrpc;
import com.hotel.service.roomavailability.RoomAvailabilityRequest;
import com.hotel.service.roomavailability.RoomAvailabilityResponse;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@GrpcService
@Slf4j
public class RoomAvailabilityServerService extends HotelRoomAvailabilityServiceGrpc.HotelRoomAvailabilityServiceImplBase {

    @Autowired
    RoomAvailabilityService roomAvailabilityService;

    @Override
    public void getRoomAvailability(RoomAvailabilityRequest request, StreamObserver<RoomAvailabilityResponse> responseObserver) {
        log.info(request.toString());
        RoomAvailabilityResponse response = roomAvailabilityService.getRoomAvailabilityFromSupplier(request);
        if (Objects.nonNull(response)) {
            log.info(response.toString());
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

