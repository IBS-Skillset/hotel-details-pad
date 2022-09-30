package com.hoteldetails.pad.client;

import com.hotel.service.availability.HotelAvailabilityRequest;
import com.hotel.service.description.HotelDescriptionRequest;
import com.hoteldetails.pad.endpoint.DjocaEndpointFactory;
import lombok.extern.slf4j.Slf4j;
import org.opentravel.ota._2003._05.OTAHotelAvailRQ;
import org.opentravel.ota._2003._05.OTAHotelDescriptiveInfoRQ;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;


@Component
@Slf4j
public class DjocaClient {

    public Object restClient(Object request, String supplierUrl, String service) throws JAXBException {
        JAXBContext context= DjocaEndpointFactory.context;
        final StringWriter requestWriter = new StringWriter();
        Marshaller marshaller = context.createMarshaller();
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try {
            marshaller.marshal(request, requestWriter);
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(supplierUrl);
            uriBuilder.path(service);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(uriBuilder.build().toUriString(),requestWriter.toString(), String.class);
            log.info(requestWriter.toString());
            log.info(responseEntity.getBody());
            return unmarshaller.unmarshal(new StringReader(responseEntity.getBody()));
        }
        catch (JAXBException b){
            log.info("JAXBException caught" +b);
            throw b;
        }
        catch (Exception e)
        {
            log.info("Exception occured in request-response to Djoca" +e);
            throw e;
        }
    }

}
