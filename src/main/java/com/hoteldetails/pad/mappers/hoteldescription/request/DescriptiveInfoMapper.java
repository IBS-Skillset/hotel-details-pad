package com.hoteldetails.pad.mappers.hoteldescription.request;

import com.hotel.service.description.HotelDescriptionRequest;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQHotelDescriptiveInfos;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQHotelDescriptiveInfosHotelDescriptiveInfo;
import org.springframework.stereotype.Component;

@Component
public class DescriptiveInfoMapper {

    public OTAHotelDescriptiveInfoRQHotelDescriptiveInfos map(HotelDescriptionRequest request){
        OTAHotelDescriptiveInfoRQHotelDescriptiveInfos hotelDescriptiveInfos=new OTAHotelDescriptiveInfoRQHotelDescriptiveInfos();
        OTAHotelDescriptiveInfoRQHotelDescriptiveInfosHotelDescriptiveInfo hotelDescriptiveInfo=new OTAHotelDescriptiveInfoRQHotelDescriptiveInfosHotelDescriptiveInfo();
        hotelDescriptiveInfo.setHotelCode(request.getHotelCode());
        hotelDescriptiveInfo.setHotelCityCode(request.getHotelCityCode());
        hotelDescriptiveInfos.getHotelDescriptiveInfo().add(hotelDescriptiveInfo);
        return hotelDescriptiveInfos;
    }
}
