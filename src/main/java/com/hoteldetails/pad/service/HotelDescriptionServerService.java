package com.hoteldetails.pad.service;

import com.hotel.service.description.HotelDescriptionRequest;
import com.hotel.service.description.HotelDescriptionResponse;
import com.hotel.service.description.HotelDescriptionServiceGrpc;
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
public class HotelDescriptionServerService extends HotelDescriptionServiceGrpc.HotelDescriptionServiceImplBase {

    @Autowired
    private HotelDescriptionService hotelDescriptionService;

    @Autowired
    private ErrorResponseMapper errorResponseMapper;

    @Override
    public void getHotelDescription(HotelDescriptionRequest request, StreamObserver<HotelDescriptionResponse> responseObserver) {
       log.info(request.toString());
        HotelDescriptionResponse response = null;
        try {
            response = hotelDescriptionService.getHotelDescriptionFromSupplier(request);
            if (Objects.nonNull(response)) {
                log.info(response.toString());
            }
        } catch(HotelException e) {
            response = mapErrorResponse(e.getMessage(),e.getCode());

        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private HotelDescriptionResponse mapErrorResponse(String message, String code) {
        HotelDescriptionResponse.Builder responseBuilder = HotelDescriptionResponse.newBuilder();
        safeSetProtoField(responseBuilder::setResponseStatus, errorResponseMapper.mapErrorResponse(message,code));
        return responseBuilder.build();
    }
}
