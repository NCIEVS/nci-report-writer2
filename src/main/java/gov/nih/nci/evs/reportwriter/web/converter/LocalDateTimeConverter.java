package gov.nih.nci.evs.reportwriter.web.converter;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
  
    @Override
    public java.sql.Timestamp convertToDatabaseColumn(java.time.LocalDateTime attribute) {
       
        return attribute == null ? null : java.sql.Timestamp.valueOf(attribute);
    }
 
    @Override
    public java.time.LocalDateTime convertToEntityAttribute(java.sql.Timestamp dbData) {
       
        return dbData == null ? null : dbData.toLocalDateTime();
    }
} 