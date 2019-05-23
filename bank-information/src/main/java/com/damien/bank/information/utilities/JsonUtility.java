package com.damien.bank.information.utilities;

import com.damien.bank.information.exceptions.BankInformationServiceException;
import com.damien.bank.information.exceptions.BankInformationServiceExceptionErrorCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonUtility {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    public String convertObjectToString(Object objectToConvertToString) {
        try {
            return OBJECT_MAPPER.writeValueAsString(objectToConvertToString);
        } catch (JsonProcessingException jpe) {
            throw new BankInformationServiceException(BankInformationServiceExceptionErrorCodes.JSON_EXCEPTION);
        }
    }

    public <T> T convertStringToObject(String stringToConvertToObject, Class<T> expectedClassType) {
        try {
            return OBJECT_MAPPER.readValue(stringToConvertToObject, expectedClassType);
        } catch (JsonProcessingException ex) {
            throw new BankInformationServiceException(BankInformationServiceExceptionErrorCodes.JSON_EXCEPTION);
        } catch (IOException e) {
            throw new BankInformationServiceException(BankInformationServiceExceptionErrorCodes.IO_EXCEPTION);
        }
    }

    public <T> T convertStringToObject(String stringToConvertToObject, TypeReference<T> reference) {
        try {
            return OBJECT_MAPPER.readValue(stringToConvertToObject, reference);
        } catch (JsonProcessingException ex) {
            throw new BankInformationServiceException(BankInformationServiceExceptionErrorCodes.JSON_EXCEPTION);
        } catch (IOException e) {
            throw new BankInformationServiceException(BankInformationServiceExceptionErrorCodes.IO_EXCEPTION);
        }
    }
}
