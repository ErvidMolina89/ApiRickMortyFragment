package com.wposs.apirickmortyfragment.View.Home.Implementations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.wposs.apirickmortyfragment.Base.App;
import com.wposs.apirickmortyfragment.Models.Character;
import com.wposs.apirickmortyfragment.Models.Characters;
import com.wposs.apirickmortyfragment.Models.MessageResponse;
import com.wposs.apirickmortyfragment.R;
import com.wposs.apirickmortyfragment.Utils.Constants;
import com.wposs.apirickmortyfragment.Utils.Extensions;
import com.wposs.apirickmortyfragment.View.Home.Adapter.CategoriasAdapter;
import com.wposs.apirickmortyfragment.View.Home.Adapter.CharactersAdapter;
import com.wposs.apirickmortyfragment.View.Home.Interfaces.IMainView;
import com.wposs.apirickmortyfragment.View.Home.Interfaces.OnItemClickListenerCategorias;
import com.wposs.apirickmortyfragment.View.Interfaces.OnItemClickListenerCharacter;
import com.wposs.apirickmortyfragment.View.Search.Implementations.SearchFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private Button searchView;
    private HomePresenter presenter;
    private ImageSlider carousel;
    private RecyclerView rvCategorias;
    private final List<String> categorias = new ArrayList<>();
    private CharactersAdapter charactersAdapter;
    private String categoria = "Alive";
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        presenter = new HomePresenter(new listenerMainView());

        carousel = view.findViewById(R.id.carousel);
        searchView = view.findViewById(R.id.btn_searchView);
        rvCategorias = view.findViewById(R.id.rvCategorias);
        RecyclerView rvPersonajes = view.findViewById(R.id.rvPersonajes);

        charactersAdapter = new CharactersAdapter(new ArrayList<>(), new listenerAdapterCharacter(), categoria);
        rvPersonajes.setHasFixedSize(true);
        rvPersonajes.setLayoutManager(new GridLayoutManager(App.mContext, 3, RecyclerView.VERTICAL, false));
        rvPersonajes.setAdapter(charactersAdapter);
        callServiceCharacter(categoria);

        categoriasView();
        presenter.getAllCharacterSuccess();
        searchView.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_searchFragment);
        });

        return view;
    }

    private void carouselView(List<Character> characters){
        ArrayList<SlideModel> itemsCarousel = new ArrayList<>();

        for (Character character : characters){
            SlideModel slideModel = new SlideModel(character.getImage(), ScaleTypes.CENTER_INSIDE);
            itemsCarousel.add(slideModel);
        }

        carousel.setImageList(itemsCarousel);
    }
    private void characterlUpdate(List<Character> characters){
        charactersAdapter.updateList(characters, categoria);
    }

    private void categoriasView(){
        Collections.addAll(categorias, Constants.Categorias.ALIVE,
                Constants.Categorias.DEAD, Constants.Categorias.FEMALE,
                Constants.Categorias.MALE,Constants.Categorias.GENDERLESS);
        rvCategorias.setAdapter(null);
        rvCategorias.setHasFixedSize(true);
        rvCategorias.setLayoutManager(new LinearLayoutManager(App.mContext, LinearLayoutManager.HORIZONTAL, false));
        CategoriasAdapter categoriasAdapter = new CategoriasAdapter(categorias, new listenerAdapterCategorias());
        rvCategorias.setAdapter(categoriasAdapter);
    }

    private class listenerMainView implements IMainView {
        @Override
        public void showGetAllCharacterSuccess(Characters characters) {carouselView(characters.getResults());}
        @Override
        public void showGetAllCharacterFilter(Characters characters) {characterlUpdate(characters.getResults());}

        @Override
        public void showMainError(MessageResponse message) {Extensions.showErrorDialog(message.getMessage(), App.mContext);}
    }

    private class listenerAdapterCategorias implements OnItemClickListenerCategorias {
        @Override
        public void onItemClick(String categoria) {
            callServiceCharacter(categoria);
        }
    }

    private void callServiceCharacter(String categoria){
        switch (categoria){
            case Constants.Categorias.ALIVE:
            case Constants.Categorias.DEAD: {
                presenter.getAllCharacterForStatus(categoria);
                break;
            }
            case Constants.Categorias.FEMALE:
            case Constants.Categorias.GENDERLESS:
            case Constants.Categorias.MALE: {
                presenter.getAllCharacterForGender(categoria);
                break;
            }
        }
        this.categoria = categoria;
    }

    private class listenerAdapterCharacter implements OnItemClickListenerCharacter {
        @Override
        public void onItemClick(Character character) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("character", character);
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_detailFragment, bundle);
        }
    }
}