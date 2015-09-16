package com.websushibar.hprofpersist.persistence.mongo.converters;

import com.websushibar.hprofpersist.hprofentries.IDField;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class IDWriteLongConverter implements Converter<IDField, Long> {
    @Override
    public Long convert(IDField idField) {
        return idField.getNumeric();
    }
}
