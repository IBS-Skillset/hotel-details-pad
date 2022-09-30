package com.hoteldetails.pad.mappers.hoteldescription.request;

import com.hotel.service.description.HotelDescriptionRequest;
import com.hoteldetails.pad.mappers.common.request.PosMapper;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DescriptionRequestMapper {

    @Autowired
    private PosMapper posMapper;

    @Autowired
    private DescriptiveInfoMapper descriptiveInfoMapper;

    public OTAHotelDescriptiveInfoRQ map(HotelDescriptionRequest request) {
        OTAHotelDescriptiveInfoRQ descriptiveInfoRQ = new OTAHotelDescriptiveInfoRQ();
        descriptiveInfoRQ.setPOS(posMapper.mapPOS(request.getRequestContext(), request.getLanguageCode()));
        descriptiveInfoRQ.setHotelDescriptiveInfos(descriptiveInfoMapper.map(request));
        return descriptiveInfoRQ;
    }
}
