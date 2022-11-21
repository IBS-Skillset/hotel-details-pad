package com.hoteldetails.pad.mappers.hoteldescription.response;

import com.hotel.service.common.AvailableHotelItem;
import com.hotel.service.common.ResponseStatus;
import com.hotel.service.description.HotelDescriptionResponse;
import com.hoteldetails.pad.mappers.common.response.ErrorResponseMapper;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRS;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRSHotelDescriptiveContentsHotelDescriptiveContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;
import static com.hoteldetails.pad.util.ApiConstants.SUCCESS;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

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

    @Autowired
    private ErrorResponseMapper errorResponseMapper;


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
                AvailableHotelItem.Builder hotelItem = hotelItemMapper.map(content.getContactInfos(), content.getHotelInfo().getPosition());
                if (isNotEmpty(response.getHotelDescriptiveContents().getHotelName())) {
                    safeSetProtoField(hotelItem::setHotelName, response.getHotelDescriptiveContents().getHotelName());
                }
                safeSetProtoField(responseBuilder:: setHotelItem, hotelItem.build());
                ResponseStatus.Builder responseStatusBuilder = ResponseStatus.newBuilder();
                safeSetProtoField(responseStatusBuilder::setStatus, SUCCESS);
                safeSetProtoField(responseBuilder::setResponseStatus, responseStatusBuilder);
            }
        } else {
            safeSetProtoField(responseBuilder::setResponseStatus,
                    errorResponseMapper.mapErrorResponse(response.getErrors().getError().get(0).getCode(),
                    response.getErrors().getError().get(0).getValue()));
        }
        return responseBuilder.build();
    }
}
