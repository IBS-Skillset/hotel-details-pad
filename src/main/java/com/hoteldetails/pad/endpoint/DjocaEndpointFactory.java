package com.hoteldetails.pad.endpoint;

import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQ;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRS;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class DjocaEndpointFactory {
    public static final JAXBContext context;

    private DjocaEndpointFactory() {
    }

    static {
        try {
            context = JAXBContext.newInstance(
                    OTAHotelDescriptiveInfoRQ.class,
                    OTAHotelDescriptiveInfoRS.class);
        } catch (JAXBException e) {
            throw new IllegalStateException("Cannot initialize DJOCA services");
        }
    }
}
