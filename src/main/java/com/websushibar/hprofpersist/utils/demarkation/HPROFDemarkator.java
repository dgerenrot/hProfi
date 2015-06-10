package com.websushibar.hprofpersist.utils.demarkation;

import com.websushibar.hprofpersist.hprofentries.HPROFEntity;
import com.websushibar.hprofpersist.hprofentries.HPROFMainEntry;
import com.websushibar.hprofpersist.hprofentries.HasId;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.DumpSubtag;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.DumpSubtagEntry;

import java.util.Map;

public class HPROFDemarkator {

    public HPROFDemarkator(Map<Long, HPROFEntity> byLocation) {
        this.byLocation = (byLocation);
    }

    private Map<Long, HPROFEntity> byLocation;


    public String demarkationAt(long location) {
        return demarkationOf(byLocation.get(location));
    }

    public String demarkationOf(HPROFEntity e) {

        String retVal = "";

        if (e instanceof HPROFMainEntry) {
            HPROFMainEntry mainEntry = (HPROFMainEntry)e;
            retVal = mainEntry.getTag().name()
                    + " starting at" + mainEntry.getStartsAtByte()
                    + ",\n" + (mainEntry.getBytesAfterHeader() + 9)
                    + " bytes.\n";

        } else if (e instanceof DumpSubtagEntry) {
            DumpSubtagEntry subtagEntry = (DumpSubtagEntry)e;

            retVal = DumpSubtag.getTag(subtagEntry.getClass()).name()
                    + " starting at " + subtagEntry.getStartsAtByte()
                    + "\n";

        }

        if (e instanceof HasId) {
            retVal += "Id: " + ((HasId) e).getId() + "\n";
        }

        return retVal;
    }
}
