package com.websushibar.hprofpersist.utils.demarkation;

import java.io.*;

public class PrintWithDemarcation {

    private HPROFDemarkator demarkator;

    public void printDemarkated(InputStream is, OutputStream os) throws IOException {

        int currByte;
        long count = 0;

        BufferedOutputStream bos = new BufferedOutputStream(os);
        PrintWriter pw;

        while ((currByte = is.read()) != -1) {



            os.write(currByte);
            count++;

        }

    }

    public void setDemarkator(HPROFDemarkator demarkator) {
        this.demarkator = demarkator;
    }

}
