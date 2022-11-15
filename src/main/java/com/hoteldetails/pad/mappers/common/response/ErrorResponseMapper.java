package com.hoteldetails.pad.mappers.common.response;

import com.hotel.service.common.ResponseStatus;
import com.hoteldetails.pad.util.ApiConstants;
import org.springframework.stereotype.Component;

import static com.hotel.service.util.ProtoBufUtil.safeSetProtoField;

@Component
public class ErrorResponseMapper {

    public ResponseStatus mapErrorResponse (String message, String code) {
        ResponseStatus.Builder reponseStatusBuilder = ResponseStatus.newBuilder();
        safeSetProtoField(reponseStatusBuilder::setStatus, ApiConstants.FAILURE);
        safeSetProtoField(reponseStatusBuilder::setErrorCode, code);
        safeSetProtoField(reponseStatusBuilder::setErrorMessage, message);
        return reponseStatusBuilder.build();

    }
}
