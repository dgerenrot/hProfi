package com.websushibar.hprofpersist.loader;

import com.websushibar.hprofpersist.hprofentries.IDField;

import java.io.*;

public class DataInputStreamWrapper extends PushbackInputStream  {

    protected DataInputStream dis;
    protected InputStream is;

    protected long bytesRead;
    protected long bytesReadMark;

    public DataInputStreamWrapper(InputStream is) {
        super(is);
        this.is = is;
        this.dis = new DataInputStream(this);
    }

    public IDField readId() throws IOException {

        int idSize = IDField.getSize();
        byte[] idBytes = new byte[idSize];
        for (int i = 0; i < idSize; i++) {
            idBytes[i] = readByte();
        }

        return new IDField(idBytes);
    }


    // Override methods

    @Override
    public void unread(int b) throws IOException {
        super.unread(b);
        bytesRead = (bytesRead > 0 ? bytesRead - 1 : 0);
    }

    @Override
    public void unread(byte[] b) throws IOException {
        super.unread(b);
        bytesRead = (bytesRead > 0 ? bytesRead - b.length : 0);
    }

    @Override
    public void unread(byte[] b, int off, int len) throws IOException {
        super.unread(b);
        bytesRead = (bytesRead > 0 ? bytesRead - len : 0);
    }

    @Override
    public int read() throws IOException{
        int retVal = super.read();
        bytesRead += 1;
        return retVal;
    }

    @Override
    public int read(byte[] bytes) throws IOException {
        int retVal = super.read(bytes);
        bytesRead += retVal;
        return retVal;
    }

    @Override
    public int read(byte[] bytes, int off, int len) throws IOException {
        int retVal = super.read(bytes, off, len);
        bytesRead += retVal;
        return retVal;
    }

    @Override
    public void mark(int sizeLimit) {
        super.mark(sizeLimit);
        bytesReadMark = bytesRead;
    }

    @Override
    public void reset() throws IOException {
        bytesRead = bytesReadMark;
        super.reset();
    }

    // DataInput methods
    public boolean readBoolean() throws IOException {
        return dis.readBoolean();
    }

    public char readChar() throws IOException {
        return dis.readChar();
    }

    public byte readByte() throws IOException {
        return dis.readByte();
    }

    public short readShort() throws IOException {
        return dis.readShort();
    }

    public int readInt() throws IOException {
        return dis.readInt();
    }

    public long readLong() throws IOException {
        return dis.readLong();
    }

    public float readFloat() throws IOException {
        return dis.readFloat();
    }

    public double readDouble() throws IOException {
        return dis.readDouble();
    }

    // Getters
    public DataInputStream getDis() {
        return dis;
    }

    public InputStream getIs() {
        return is;
    }

    public long getBytesRead() {
        return bytesRead;
    }
}
