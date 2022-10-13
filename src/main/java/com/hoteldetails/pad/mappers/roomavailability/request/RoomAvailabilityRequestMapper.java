package com.hoteldetails.pad.mappers.roomavailability.request;

import com.hotel.service.roomavailability.RoomAvailabilityRequest;
import com.hoteldetails.pad.mappers.common.request.PosMapper;
import org.opentravel.ota._2003._05.PropertyAvailabilityRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class RoomAvailabilityRequestMapper {
    @Autowired
    private PosMapper posMapper;

    public PropertyAvailabilityRQ map(RoomAvailabilityRequest request) {
        PropertyAvailabilityRQ propertyAvailabilityRQ = new PropertyAvailabilityRQ();
        PropertyAvailabilityRQ.StayDetails stayDetails = new PropertyAvailabilityRQ.StayDetails();
        propertyAvailabilityRQ.setPOS(posMapper.mapPOS(request.getRequestContext(), request.getLanguageCode()));
        stayDetails.setRooms(BigInteger.valueOf(request.getRoomCount()));
        stayDetails.setGuestCount(BigInteger.valueOf(request.getOccupancy()));
        stayDetails.setStart(request.getStartDate());
        stayDetails.setEnd(request.getEndDate());
        propertyAvailabilityRQ.setHotelCode(request.getHotelCode());
        propertyAvailabilityRQ.setStayDetails(stayDetails);
        return propertyAvailabilityRQ;
    }
}
