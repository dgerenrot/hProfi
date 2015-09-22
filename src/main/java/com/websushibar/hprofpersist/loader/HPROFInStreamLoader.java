package com.websushibar.hprofpersist.loader;

import com.websushibar.hprofpersist.hprofentries.HPROFHeaderInfo;
import com.websushibar.hprofpersist.hprofentries.HPROFMainEntry;
import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.Tag;
import com.websushibar.hprofpersist.hprofentries.exceptions.HPROFFormatException;
import com.websushibar.hprofpersist.store.HPROFStore;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;


public class HPROFInStreamLoader {

    private DataInputStreamWrapper dis;

    private byte[] idBytes;

    public HPROFInStreamLoader(InputStream is) {
        this.dis = new DataInputStreamWrapper(is);
    }

    // TODO : Whatever for?
    public DataInputStreamWrapper getDataInput() {
        return dis;
    }

    /**
     * Load the entire DataInputStream into the store provided.
     * @param store
     * @throws IOException
     */
    public void loadInto(HPROFStore store) throws IOException {

        try {
            try {
                HPROFHeaderInfo headerInfo = readHeader();
                store.addHPROFHeader(headerInfo);
            } catch (EOFException e) {
                throw new HPROFFormatException("EOF reading tag!", e);
            }

            HPROFMainEntry entry;

            while ((entry = readEntry()) != null) {
                store.addHPROFEntry(entry);
            }
        } finally {
            if (dis != null) {
                dis.close();
            }
        }
    }


    /**
     *
     * @return HPROF file header (formatString, objID size, timestamp)
     * @throws IOException
     */
    public HPROFHeaderInfo readHeader() throws IOException {
        String fmtAndVersion = readStringBytes();
        readSizeofId();
        long timestamp = readFileTimestamp();

        return new HPROFHeaderInfo(fmtAndVersion, IDField.getSize(), timestamp);
    }

    /**
     * Assumes the stream is positioned at the next tag
     * @return next HPROFMainEntry in stream or null or EOF.
     * @throws IOException
     */
    public HPROFMainEntry readEntry()
            throws IOException {
        Tag tag;

        try {
            tag = readTag();
        } catch (EOFException e) {
            return null;
        }

        HPROFMainEntry entry;
        try {
            entry = Tag.getClassByTag(tag).newInstance();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        entry.readSelf(dis);

        return entry;
    }

    protected String readStringBytes() throws IOException {
        StringBuilder sb = new StringBuilder();
        byte b;
        while ((b = dis.readByte()) != 0x0) {
            sb.append((char)b);
        }

        return sb.toString();
    }

    protected int readSizeofId() throws IOException {
        int idSize = dis.readInt();

        int bitsInByte = 8;

        if (idSize != Long.SIZE / bitsInByte
                && idSize != Integer.SIZE / bitsInByte
                && idSize != Short.SIZE / bitsInByte)
            throw new IDSizeUnimplementedException(idSize);

        IDField.setSize(idSize);
        idBytes = new byte[idSize];
        return idSize;
    }

    protected long readFileTimestamp() throws IOException {
        long highBytes = dis.readInt();
        long lowBytes = dis.readInt();

        return lowBytes | (highBytes << 32);
    }

    protected Tag readTag() throws IOException {
        int tagCode = dis.readByte();
        return Tag.getTag(tagCode);
    }
}
