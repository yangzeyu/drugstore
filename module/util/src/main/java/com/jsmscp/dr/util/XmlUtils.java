package com.jsmscp.dr.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import java.io.IOException;
import java.io.InputStream;

/**
 * xml jackson 工具类.
 */
@SuppressWarnings("unused")
public class XmlUtils {

    public static <T> T readString(String content, Class<T> clazz) {
        XmlMapper mapper = new XmlMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JaxbAnnotationModule jaxbModule = new JaxbAnnotationModule();
        mapper.registerModule(jaxbModule);
        try {
            return mapper.readValue(content, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T readString(InputStream is, Class<T> clazz) {
        XmlMapper mapper = new XmlMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JaxbAnnotationModule jaxbModule = new JaxbAnnotationModule();
        mapper.registerModule(jaxbModule);
        try {
            return mapper.readValue(is, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> String writeString(T obj) {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JaxbAnnotationModule jaxbModule = new JaxbAnnotationModule();
        mapper.registerModule(jaxbModule);
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
