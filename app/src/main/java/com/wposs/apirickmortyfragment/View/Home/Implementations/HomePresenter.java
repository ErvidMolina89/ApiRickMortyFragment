package com.wposs.apirickmortyfragment.View.Home.Implementations;

import com.wposs.apirickmortyfragment.Base.App;
import com.wposs.apirickmortyfragment.Models.Characters;
import com.wposs.apirickmortyfragment.Models.MessageResponse;
import com.wposs.apirickmortyfragment.R;
import com.wposs.apirickmortyfragment.Utils.Extensions;
import com.wposs.apirickmortyfragment.View.Home.Interfaces.IMainListener;
import com.wposs.apirickmortyfragment.View.Home.Interfaces.IMainPresenter;
import com.wposs.apirickmortyfragment.View.Home.Interfaces.IMainView;

public class HomePresenter implements IMainPresenter {
    private final IMainView view;
    private final HomeBL bl;

    public HomePresenter(IMainView view) {
        this.view = view;
        this.bl = new HomeBL(new listenerMainBL());
    }

    @Override
    public void getAllCharacterSuccess() {
        if (!Extensions.isNetworkAvailable(App.mContext)){
            view.showMainError(new MessageResponse(2, App.mContext.getString(R.string.error_internet)));
            return;
        }
        bl.getAllCharacterSuccess();
    }

    @Override
    public void getAllCharacterForStatus(String status) {
        bl.getAllCharacterForStatus(status);
    }

    @Override
    public void getAllCharacterForGender(String gender) {
        bl.getAllCharacterForGender(gender);
    }

    private class listenerMainBL implements IMainListener {

        @Override
        public void showGetAllCharacterSuccess(Characters characters) {view.showGetAllCharacterSuccess(characters);}

        @Override
        public void showGetAllCharacterFilter(Characters characters) {view.showGetAllCharacterFilter(characters);}

        @Override
        public void showMainError(MessageResponse message) {
            view.showMainError(message);
        }
    }
}
