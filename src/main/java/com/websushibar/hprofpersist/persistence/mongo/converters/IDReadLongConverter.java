package com.websushibar.hprofpersist.persistence.mongo.converters;

import com.websushibar.hprofpersist.hprofentries.IDField;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class IDReadLongConverter implements Converter<Long, IDField> {
    @Override
    public IDField convert(Long id) {
        return new IDField(id.longValue());
    }
}
