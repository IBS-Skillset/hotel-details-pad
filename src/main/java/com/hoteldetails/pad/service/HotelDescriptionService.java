package com.hoteldetails.pad.service;

import com.hotel.service.description.HotelDescriptionRequest;
import com.hotel.service.description.HotelDescriptionResponse;
import com.hoteldetails.pad.client.DjocaClient;

import com.hoteldetails.pad.exception.HotelException;
import com.hoteldetails.pad.mappers.hoteldescription.request.DescriptionRequestMapper;
import com.hoteldetails.pad.mappers.hoteldescription.response.DescriptionResponseMapper;
import com.hoteldetails.pad.util.ApiConstants;
import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQ;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.hoteldetails.pad.util.ApiConstants.SERVICE;

@Service
@Slf4j
public class HotelDescriptionService {

    @Autowired
    private DescriptionRequestMapper descriptionRequestMapper;

    @Autowired
    private DescriptionResponseMapper descriptionResponseMapper;

    @Autowired
    private DjocaClient client;

    public HotelDescriptionResponse getHotelDescriptionFromSupplier(HotelDescriptionRequest request) {
        OTAHotelDescriptiveInfoRQ hotelDescriptiveInfoRQ = descriptionRequestMapper.map(request);
        OTAHotelDescriptiveInfoRS descriptiveInfoRS = new OTAHotelDescriptiveInfoRS();
        try {
            Object response = client.restClient(hotelDescriptiveInfoRQ, request.getRequestContext().getSupplierUrl(), SERVICE);
            if (Objects.nonNull(response)) {
                descriptiveInfoRS = (OTAHotelDescriptiveInfoRS) response;
            }
            log.info("Successful OTA hotel description Response");
            return descriptionResponseMapper.map(descriptiveInfoRS);
        } catch (Exception e) {
            log.info("Error while retrieving the HotelAvail Response" + e);
            throw new HotelException(e.getMessage(), ApiConstants.SUPPLIER_SERVER_ERROR);
        }
    }
}
