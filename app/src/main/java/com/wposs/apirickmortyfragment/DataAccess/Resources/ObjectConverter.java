package com.wposs.apirickmortyfragment.DataAccess.Resources;

import com.wposs.apirickmortyfragment.Base.App;
import com.wposs.apirickmortyfragment.Models.BaseModel;
import com.wposs.apirickmortyfragment.Models.Characters;
import com.wposs.apirickmortyfragment.R;

public class ObjectConverter {
    public static Characters convertObjectToCharacters(BaseModel objectResponse) {
        if (objectResponse instanceof Characters) {
            return (Characters) objectResponse;
        } else {
            throw new IllegalArgumentException(App.mContext.getString(R.string.object_no_cast_characters));
        }
    }
}
