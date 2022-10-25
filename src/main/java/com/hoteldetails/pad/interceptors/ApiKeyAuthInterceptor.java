package com.hoteldetails.pad.interceptors;

import io.grpc.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Objects;


@GrpcGlobalServerInterceptor
@Slf4j
public class ApiKeyAuthInterceptor implements ServerInterceptor {


    public boolean validateSignature(String token)  throws Exception{
        String[] chunks = token.split("\\.");
        SignatureAlgorithm sa = SignatureAlgorithm.RS256;
        BigInteger mod = new BigInteger("19000812822426683865012163618027310378487395154330304998855217229900382730200283232802318000193794545462996454857896773995909120073180895021730312708474638199242368948617010756177040604721432026065868235078315166342546553887368687889480198247689830862136303023640201198180314337176952541013193315987840899973486485532199280808520596592328947878088072286427483154626278976886368730181756526507275308753625752548958224081969926946453900525575829301891927935306879737770979639402074921073866173771711955654133667856118067012688187894392311708798013285013475533536647677724106088022279193658129435945930408108920563375353");
        BigInteger publicExp = new BigInteger("65537");
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PublicKey publicKey = fact.generatePublic(new RSAPublicKeySpec(mod, publicExp));
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;

        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa,rsaPublicKey);
        if (!validator.isValid(tokenWithoutSignature, signature)) {
            return false;
        } else return true;
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        log.info("Sever interceptor {}", serverCall.getMethodDescriptor());

        Metadata.Key<String> apiKeyMetadata = Metadata.Key.of("accesstoken", Metadata.ASCII_STRING_MARSHALLER);
        String apiKey = metadata.get(apiKeyMetadata);
        log.info("accesstoken from client {}", apiKey);
        try {
            if (Objects.nonNull(apiKey) && validateSignature(apiKey)){
                return serverCallHandler.startCall(serverCall, metadata);
            } else {
                Status status = Status.UNAUTHENTICATED.withDescription("Invalid accesstoken");
                serverCall.close(status, metadata);
            }
        } catch (Exception e) {
            Status status = Status.UNAUTHENTICATED.withDescription("Invalid accesstoken");
            serverCall.close(status, metadata);
        }
        return new ServerCall.Listener<>() {

        };
    }
}

