package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Document
public class StringEntry extends HPROFMainEntry
        implements HasId{

    public String getContent() {
        return content;
    }

    private IDField id;
    private String content;

    /**
     * Assumes that number of bytes specified in the header is never larger than 16G
     * so as to present the unsigned ints problem.
     *
     * @param l wrapper to read the body from
     * @throws IOException
     */
    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate((int)bytesAfterHeader - IDField.getSize());

        id = l.readId();

        for (int i = 0; i < bytesAfterHeader - IDField.getSize(); i++) {
            buffer.put(l.readByte());
        }
        buffer.position(0);
        content = String.valueOf(StandardCharsets.UTF_8.decode(buffer));
    }

    @Override
    public IDField getId() { return id; }

    @Override
    public String toString() {
        return content;
    }
}
