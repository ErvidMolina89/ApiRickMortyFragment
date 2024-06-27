package com.wposs.apirickmortyfragment.View.Search.Implementations;

import com.wposs.apirickmortyfragment.Base.App;
import com.wposs.apirickmortyfragment.Models.Characters;
import com.wposs.apirickmortyfragment.Models.MessageResponse;
import com.wposs.apirickmortyfragment.R;
import com.wposs.apirickmortyfragment.Utils.Extensions;
import com.wposs.apirickmortyfragment.View.Search.Interfaces.ISearchListener;
import com.wposs.apirickmortyfragment.View.Search.Interfaces.ISearchPresenter;
import com.wposs.apirickmortyfragment.View.Search.Interfaces.ISearchView;

public class SearchPresenter implements ISearchPresenter {
    private final ISearchView view;
    private final SearchBL bl;

    public SearchPresenter(ISearchView view) {
        this.view = view;
        this.bl = new SearchBL(new listenerSearchBL());
    }

    @Override
    public void getAllCharacterNameSuccess(String name, int page) {
        if (!Extensions.isNetworkAvailable(App.mContext)){
            view.showSearchError(new MessageResponse(2, App.mContext.getString(R.string.error_internet)));
            return;
        }
        bl.getAllCharacterNameSuccess(name, page);
    }

    private class listenerSearchBL implements ISearchListener {

        @Override
        public void showGetAllCharacterNameSuccess(Characters characters) {
            view.showGetAllCharacterNameSuccess(characters);
        }

        @Override
        public void showSearchError(MessageResponse message) {
            view.showSearchError(message);
        }
    }
}
