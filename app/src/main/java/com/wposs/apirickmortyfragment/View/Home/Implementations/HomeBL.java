package com.wposs.apirickmortyfragment.View.Home.Implementations;

import com.wposs.apirickmortyfragment.DataAccess.Interfaces.IResponse;
import com.wposs.apirickmortyfragment.DataAccess.ImplementationRetrofit;
import com.wposs.apirickmortyfragment.DataAccess.Resources.ObjectConverter;
import com.wposs.apirickmortyfragment.Models.BaseModel;
import com.wposs.apirickmortyfragment.Models.Characters;
import com.wposs.apirickmortyfragment.Models.MessageResponse;
import com.wposs.apirickmortyfragment.Utils.Constants;
import com.wposs.apirickmortyfragment.View.Home.Interfaces.IMainBL;
import com.wposs.apirickmortyfragment.View.Home.Interfaces.IMainListener;

public class HomeBL implements IMainBL {
    private final IMainListener listener;
    private final ImplementationRetrofit service;
    public HomeBL(IMainListener listener) {
        this.listener = listener;
        service = new ImplementationRetrofit(new listenerRetrofitGetCharacters());

    }

    @Override
    public void getAllCharacterSuccess() {
        service.getCharactersApi(Constants.Service.CHARACTERS, new String());
    }

    @Override
    public void getAllCharacterForStatus(String status) {service.getCharactersApi(Constants.Service.STATUS, status);}

    @Override
    public void getAllCharacterForGender(String gender) {service.getCharactersApi(Constants.Service.GENDER, gender);}

    private class listenerRetrofitGetCharacters implements IResponse {

        @Override
        public void onSuccessResponse(BaseModel objectResponse, String service) {
            try {
                switch (service){
                    case Constants.Service.CHARACTERS: {
                        Characters characters = ObjectConverter.convertObjectToCharacters(objectResponse);
                        listener.showGetAllCharacterSuccess(characters);
                        break;
                    }
                    case Constants.Service.GENDER:
                    case Constants.Service.STATUS: {
                        Characters characters = ObjectConverter.convertObjectToCharacters(objectResponse);
                        listener.showGetAllCharacterFilter(characters);
                        break;
                    }
                }
            } catch (IllegalArgumentException e) {
                listener.showMainError(new MessageResponse(0, e.getMessage()));
            }
        }

        @Override
        public void onFailedResponse(MessageResponse response) {
            listener.showMainError(response);
        }
    }
}
