package com.hoteldetails.pad.mappers.common.request;

import com.hotel.service.common.Context;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.ArrayOfSourceType;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PosMapperTest {
    @InjectMocks
    public PosMapper posMapper;

    @Test
    public void mapPOS() {
        String languagecode = "FRE";
        Context context = getContext();
        ArrayOfSourceType response = posMapper.mapPOS(context, languagecode);
        assertThat(response).isNotNull();
        assertThat(response.getSource().get(0).getRequestorID().getID()).isEqualTo("1234");
        assertThat(response.getSource().get(0).getRequestorID().getMessagePassword()).isEqualTo("Abc@123");
        assertThat(response.getSource().get(0).getRequestorID().getLanguageCode()).isEqualTo("FRE");
        assertThat(response.getSource().get(0).getRequestorID().getType()).isEqualTo("1");
    }

    private Context getContext() {
        Context.Builder context = Context.newBuilder();
        return context.setSupplierRequestorId("1234")
                .setSupplierCredential("Abc@123")
                .build();
    }
}