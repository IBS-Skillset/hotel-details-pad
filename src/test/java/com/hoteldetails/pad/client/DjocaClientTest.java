package com.hoteldetails.pad.client;

import com.hotel.service.common.Context;
import com.hotel.service.description.HotelDescriptionRequest;
import com.hoteldetails.pad.endpoint.DjocaEndpointFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQ;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DjocaClientTest {

    private String endPointUrl = "https://traveldoo.koedia.com";
    private String service = "hotel-details";

    @Mock
    RestTemplate restTemplate;
    @Mock
    OTAHotelDescriptiveInfoRQ hotelDescriptiveInfoRQ;
    @InjectMocks
    DjocaClient djocaClient;

    @Test
    public void restClient() throws JAXBException {

        JAXBContext context = DjocaEndpointFactory.context;
        final StringWriter requestWriter = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        try {
            marshaller.marshal(hotelDescriptiveInfoRQ, requestWriter);
            HotelDescriptionRequest request = HotelDescriptionRequest.newBuilder().
                    setRequestContext(Context.newBuilder().setSupplierUrl(endPointUrl).build()).build();


            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestContext().getSupplierUrl());
            String url = uriBuilder.path(service).build().toUriString();
            ResponseEntity<String> response = ResponseEntity.ok("RESPONSE");

            when(restTemplate.postForEntity(url, requestWriter.toString(), String.class)).thenReturn(response);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestWriter.toString(), String.class);
            Mockito.verify(restTemplate, Mockito.times(1)).postForEntity(
                    ArgumentMatchers.eq(url),
                    ArgumentMatchers.eq(requestWriter.toString()),
                    ArgumentMatchers.eq(String.class));

            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }
}