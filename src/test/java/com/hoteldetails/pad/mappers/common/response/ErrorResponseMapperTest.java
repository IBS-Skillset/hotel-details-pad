package com.hoteldetails.pad.mappers.common.response;

import com.hotel.service.common.ResponseStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ErrorResponseMapperTest {
    @InjectMocks
    private ErrorResponseMapper errorResponseMapper;

    @Test
    public void testMapErrorResponse() {
        ResponseStatus response = errorResponseMapper.mapErrorResponse("No avail","222");
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(0);
        assertThat(response.getErrorCode()).isEqualTo("222");

    }

}
