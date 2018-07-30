package com.boco.soap.cmnet.check.pair.code;

import com.boco.soap.cmnet.check.result.ICheckData;

import java.util.Comparator;

public class CheckDataComparator  implements Comparator<ICheckData> {

    @Override
    public int compare(ICheckData i1, ICheckData i2)
    {
        if (i1.getRangeCheckedBeg().compareTo(i2.getRangeCheckedBeg()) < 0) {
            return -1;
        }
        if ((i1.getRangeCheckedBeg().compareTo(i2.getRangeCheckedBeg()) == 0) && (i1.getRangeCheckedEnd().compareTo(i2.getRangeCheckedEnd()) <= 0)) {
            return -1;
        }
        return 1;
    }
}
