package com.hoteldetails.pad.service;


import com.hotel.service.roomavailability.RoomAvailabilityRequest;
import com.hotel.service.roomavailability.RoomAvailabilityResponse;
import com.hoteldetails.pad.client.DjocaClient;
import com.hoteldetails.pad.mappers.roomavailability.request.RoomAvailabilityRequestMapper;
import com.hoteldetails.pad.mappers.roomavailability.response.RoomAvailabilityResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.PropertyAvailabilityRQ;
import org.opentravel.ota._2003._05.PropertyAvailabilityRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.hoteldetails.pad.util.ApiConstants.ROOMSERVICE;

@Service
@Slf4j
public class RoomAvailabilityService {

    @Autowired
    private DjocaClient djocaClient;

    @Autowired
    RoomAvailabilityResponseMapper roomAvailabilityResponseMapper;

    @Autowired
    RoomAvailabilityRequestMapper roomAvailabilityRequestMapper;


    public RoomAvailabilityResponse getRoomAvailabilityFromSupplier(RoomAvailabilityRequest request) {
        PropertyAvailabilityRQ propertyAvailabilityRQ = roomAvailabilityRequestMapper.map(request);
        PropertyAvailabilityRS propertyAvailabilityRS = new PropertyAvailabilityRS();
        try {
            Object response = djocaClient.restClient(propertyAvailabilityRQ, request.getRequestContext().getSupplierUrl(), ROOMSERVICE);
            if (Objects.nonNull(response)) {
                propertyAvailabilityRS = (PropertyAvailabilityRS) response;
            }
            log.info("Successful Room Availability Response");
            return roomAvailabilityResponseMapper.map(propertyAvailabilityRS);
        } catch (Exception e) {
            log.info("Error while retrieving the Room Availability" + e);
        }
        return null;
    }
}
