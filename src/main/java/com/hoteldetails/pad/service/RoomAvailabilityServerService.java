package com.hoteldetails.pad.service;

import com.hotel.service.roomavailability.HotelRoomAvailabilityServiceGrpc;
import com.hotel.service.roomavailability.RoomAvailabilityRequest;
import com.hotel.service.roomavailability.RoomAvailabilityResponse;
import com.hoteldetails.pad.exception.HotelException;
import com.hoteldetails.pad.mappers.common.response.ErrorResponseMapper;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;

@GrpcService
@Slf4j
public class RoomAvailabilityServerService extends HotelRoomAvailabilityServiceGrpc.HotelRoomAvailabilityServiceImplBase {

    @Autowired
    RoomAvailabilityService roomAvailabilityService;

    @Autowired
    ErrorResponseMapper errorResponseMapper;

    @Override
    public void getRoomAvailability(RoomAvailabilityRequest request, StreamObserver<RoomAvailabilityResponse> responseObserver) {
        log.info(request.toString());
        RoomAvailabilityResponse response = null;
        try {
            response = roomAvailabilityService.getRoomAvailabilityFromSupplier(request);
            if (Objects.nonNull(response)) {
                log.info(response.toString());
            }
        } catch (HotelException e) {
            response = mapErrorResponse(e.getMessage(),e.getCode());
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private RoomAvailabilityResponse mapErrorResponse(String message, String code) {
        RoomAvailabilityResponse.Builder responseBuilder = RoomAvailabilityResponse.newBuilder();
        safeSetProtoField(responseBuilder::setResponseStatus, errorResponseMapper.mapErrorResponse(message,code));
        return responseBuilder.build();
    }
}

