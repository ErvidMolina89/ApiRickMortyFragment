package com.wposs.apirickmortyfragment.View.Search.Interfaces;

import com.wposs.apirickmortyfragment.Models.Characters;
import com.wposs.apirickmortyfragment.Models.MessageResponse;

public interface ISearchView {
    void showGetAllCharacterNameSuccess(Characters characters);
    void showSearchError(MessageResponse message);
}
