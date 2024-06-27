package com.wposs.apirickmortyfragment.View.Home.Interfaces;

import com.wposs.apirickmortyfragment.Models.Characters;
import com.wposs.apirickmortyfragment.Models.MessageResponse;

public interface IMainListener {
    void showGetAllCharacterSuccess(Characters characters);
    void showGetAllCharacterFilter(Characters characters);
    void showMainError(MessageResponse message);
}
