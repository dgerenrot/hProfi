package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.hprofentries.dumpSubtags.DumpSubtag;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.DumpSubtagEntry;
import com.websushibar.hprofpersist.hprofentries.exceptions.HPROFFormatException;
import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;
import org.springframework.data.annotation.Transient;

import java.io.EOFException;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

public abstract class AbstractHeapDumpEntity extends HPROFMainEntry {

    // We want to explicitly insert these into separate table
    // TODO: pointing back to parent?
    @Transient
    private Collection<DumpSubtagEntry> subtagEntries = new LinkedList<>();

    @Override
    public void readBody(DataInputStreamWrapper l)
            throws IOException {

        while(true) {

            byte subtagByte = 0;
            try {
                subtagByte = l.readByte();
            }
            catch (EOFException eoe) {
                break; // Only if this is the very last entry
            }

            if (l.getBytesRead() - startsAtByte - SIZE_OF_HEADER == bytesAfterHeader + 1) {
                l.unread(subtagByte);
                break;
            }

            // Should this ever happen?
            if (l.getBytesRead() - startsAtByte - SIZE_OF_HEADER > bytesAfterHeader + 1) {
                l.unread(subtagByte);
                throw new HPROFFormatException("Too many bytes read by " + getClass().getName());
            }

            // Should this ever happen?
            if (subtagByte == Tag.HEAP_DUMP_SEGMENT.getCode()
                    || subtagByte == Tag.HEAP_DUMP_END.getCode()) {
                l.unread(subtagByte);
                break;
            }

            DumpSubtag subtag = DumpSubtag.getTag(subtagByte);

            if (subtag == null)
                throw new HPROFFormatException(this, "No HPROFDump subtag for tag " + subtagByte);

            DumpSubtagEntry currEntry = null;

            try {
                currEntry = DumpSubtag.getClassByTag(subtag).newInstance();
                currEntry.readBody(l);
                currEntry.setParent(this);

                subtagEntries.add(currEntry);
            } catch (InstantiationException e) {
                throw new HPROFFormatException(this, e);
            } catch (IllegalAccessException e) {
                throw new HPROFFormatException(this, e);
            }
        }
    }

    public Collection<DumpSubtagEntry> getSubtagEntries() {
        return subtagEntries;
    }
}
