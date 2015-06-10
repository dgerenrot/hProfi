package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.hprofentries.exceptions.HPROFFormatException;
import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.EOFException;
import java.io.IOException;

import static com.websushibar.hprofpersist.utils.UnsignedIntTypes.unsignedIntVal;


// TODO : Do we need that HPROF prefix on all the subclasses?
public abstract class HPROFMainEntry extends HPROFEntity{

    protected static final int SIZE_OF_HEADER = 9;

    private Tag tag;
    protected int millisSinceHeaderTimeStamp;

    protected long bytesAfterHeader;

    protected  long startsAtByte;

    protected byte[] bodyBytes;

    protected HPROFMainEntry() {}
    public Tag getTag() {
        if (tag == null) {
            tag = Tag.getTag(this.getClass());
        }

        return tag;
    }

    @Override
    public void readSelf(DataInputStreamWrapper l)
            throws IOException {

        int sizeofByteInBytes = 1;
        startsAtByte = l.getBytesRead() - sizeofByteInBytes;

        try {
            readEnryHeadSansTag(l);
        } catch (EOFException e) {

            // TODO : HPROFFormatException
            throw new HPROFFormatException("Premature end of stream, reading "
                        + tag.name() + "\n Class:" );
        }

        try {
            readBody(l);
        } catch (EOFException e) {
            throw new HPROFFormatException("Premature end of stream, reading "
                    + tag.name() + "\n Class:" );
        }
    }

    public void readEnryHeadSansTag(DataInputStreamWrapper l) throws IOException {
        millisSinceHeaderTimeStamp = l.readInt();
        int signedSize = l.readInt();
        bytesAfterHeader = unsignedIntVal(signedSize);
    }

    public long getStartsAtByte() {
        return startsAtByte;
    }

    public long getBytesAfterHeader() {
        return bytesAfterHeader;
    }
}
