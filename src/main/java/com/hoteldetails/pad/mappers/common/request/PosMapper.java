package com.hoteldetails.pad.mappers.common.request;

import com.hotel.service.common.Context;
import org.opentravel.ota._2003._05.ArrayOfSourceType;
import org.opentravel.ota._2003._05.SourceType;
import org.springframework.stereotype.Component;

@Component
public class PosMapper {

    public ArrayOfSourceType mapPOS(Context context, String languageCode){
        ArrayOfSourceType arrayOfSourceType = new ArrayOfSourceType();
        SourceType sourceType = new SourceType();
        SourceType.RequestorID requestorID= new SourceType.RequestorID();

        requestorID.setID(context.getSupplierRequestorId());
        requestorID.setMessagePassword(context.getSupplierCredential());
        requestorID.setLanguageCode(languageCode);
        requestorID.setType("1");

        sourceType.setRequestorID(requestorID);
        arrayOfSourceType.getSource().add(sourceType);
        return arrayOfSourceType;

    }
}
