package com.wposs.apirickmortyfragment.Utils;

import com.wposs.apirickmortyfragment.R;

public enum TypeDialogue {
    ERROR,
    ADVERTENCIA,
    OK;

    public int getIcono() {
        switch (this) {
            case OK:
                return R.drawable.ic_check;
            case ADVERTENCIA:
                return R.drawable.ic_warning;
            case ERROR:
                return R.drawable.ic_close;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
