package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.common.ResponseStatus;
import com.hotel.service.description.HotelDescriptionResponse;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRS;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRSHotelDescriptiveContentsHotelDescriptiveContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static com.hoteldetails.pad.util.ApiConstants.FAILURE;
import static com.hoteldetails.pad.util.ApiConstants.SUCCESS;
import static java.util.Objects.nonNull;

@Component
public class DescriptionResponseMapper {

    @Autowired
    private MediaMapper mediaMapper;

    @Autowired
    private SafetyInfosMapper safetyInfosMapper;

    @Autowired
    private HotelItemMapper hotelItemMapper;

    @Autowired
    private DescriptionsMapper descriptionsMapper;

    @Autowired
    private ServicesMapper servicesMapper;


    public HotelDescriptionResponse map(final OTAHotelDescriptiveInfoRS response) {
        HotelDescriptionResponse.Builder responseBuilder = HotelDescriptionResponse.newBuilder();
        if (nonNull(response.getSuccess().getValue()) && nonNull(response.getHotelDescriptiveContents())) {
            OTAHotelDescriptiveInfoRSHotelDescriptiveContentsHotelDescriptiveContent content = response.getHotelDescriptiveContents()
                    .getHotelDescriptiveContent().get(0);
            safeSetProtoField(responseBuilder::setMedia, mediaMapper.map(content.getMultimediaDescriptions()));
            if (nonNull(content.getHotelInfo())) {
                safeSetProtoField(responseBuilder::setSafetyInfo, safetyInfosMapper.map(content.getHotelInfo().getSafetyInfo()));
                safeSetProtoField(responseBuilder::setDescriptions, descriptionsMapper.map(content.getHotelInfo().getDescriptions()));
                safeSetProtoField(responseBuilder::setServices, servicesMapper.map(content.getHotelInfo().getServices()));
                safeSetProtoField(responseBuilder::setHotelItem, hotelItemMapper.map(content.getContactInfos(), content.getHotelInfo().getPosition()));
                ResponseStatus.Builder reponseStatusBuilder = ResponseStatus.newBuilder();
                safeSetProtoField(reponseStatusBuilder::setStatus, SUCCESS);
                safeSetProtoField(responseBuilder::setResponseStatus, reponseStatusBuilder);
            }
        } else {
            ResponseStatus.Builder reponseStatusBuilder = ResponseStatus.newBuilder();
            safeSetProtoField(reponseStatusBuilder::setStatus, FAILURE);
            safeSetProtoField(reponseStatusBuilder::setErrorCode, response.getErrors().getError().get(0).getCode());
            safeSetProtoField(reponseStatusBuilder::setErrorMessage, response.getErrors().getError().get(0).getValue());
        }
        return responseBuilder.build();
    }
}
