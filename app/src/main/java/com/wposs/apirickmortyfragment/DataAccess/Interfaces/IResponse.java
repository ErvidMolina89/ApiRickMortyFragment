package com.wposs.apirickmortyfragment.DataAccess.Interfaces;

import com.wposs.apirickmortyfragment.Models.BaseModel;
import com.wposs.apirickmortyfragment.Models.MessageResponse;

public interface IResponse {

    void onSuccessResponse(BaseModel objectResponse, String service);
    void onFailedResponse(MessageResponse response);
}
