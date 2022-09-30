package com.hoteldetails.pad.service;

import com.hotel.service.description.HotelDescriptionRequest;
import com.hotel.service.description.HotelDescriptionResponse;
import com.hotel.service.description.HotelDescriptionServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@GrpcService
@Slf4j
public class HotelDescriptionServerService extends HotelDescriptionServiceGrpc.HotelDescriptionServiceImplBase {

    @Autowired
    private HotelDescriptionService hotelDescriptionService;

    @Override
    public void getHotelDescription(HotelDescriptionRequest request, StreamObserver<HotelDescriptionResponse> responseObserver) {
       log.info(request.toString());
        HotelDescriptionResponse response = hotelDescriptionService.getHotelDescriptionFromSupplier(request);
        if (Objects.nonNull(response)) {
            log.info(response.toString());
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
