package com.hoteldetails.pad.mappers.common.request;

import com.hotel.service.common.Context;
import com.hoteldetails.pad.util.ApiConstants;
import org.opentravel.ota._2003._05.ArrayOfSourceType;
import org.opentravel.ota._2003._05.SourceType;
import org.springframework.stereotype.Component;

@Component
public class PosMapper {

    public ArrayOfSourceType mapPOS(Context context, String languageCode){
        ArrayOfSourceType arrayOfSourceType = new ArrayOfSourceType();
        SourceType sourceType = new SourceType();
        SourceType.RequestorID requesterID= new SourceType.RequestorID();

        requesterID.setID(context.getSupplierRequestorId());
        requesterID.setMessagePassword(context.getSupplierCredential());
        requesterID.setLanguageCode(languageCode);
        requesterID.setType(ApiConstants.TYPE);

        sourceType.setRequestorID(requesterID);
        arrayOfSourceType.getSource().add(sourceType);
        return arrayOfSourceType;

    }
}
