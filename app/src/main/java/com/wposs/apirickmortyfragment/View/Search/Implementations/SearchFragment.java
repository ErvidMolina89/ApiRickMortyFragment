package com.wposs.apirickmortyfragment.View.Search.Implementations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.wposs.apirickmortyfragment.Base.App;
import com.wposs.apirickmortyfragment.Models.Character;
import com.wposs.apirickmortyfragment.Models.Characters;
import com.wposs.apirickmortyfragment.Models.MessageResponse;
import com.wposs.apirickmortyfragment.R;
import com.wposs.apirickmortyfragment.Utils.Extensions;
import com.wposs.apirickmortyfragment.View.Interfaces.OnItemClickListenerCharacter;
import com.wposs.apirickmortyfragment.View.Search.Adapter.CharactersSearchAdapter;
import com.wposs.apirickmortyfragment.View.Search.Interfaces.ISearchView;
import com.wposs.apirickmortyfragment.View.Splash.SplashFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private SearchPresenter presenter;
    private RecyclerView rvCharacter;
    private CharactersSearchAdapter charactersAdapter;
    private int currentPage = 1;
    private String currentName = "";
    private List<Character> listCharacter = new ArrayList<>();

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.searchView);
        rvCharacter = view.findViewById(R.id.rvSearchPersonajes);
        presenter = new SearchPresenter(new listenerSearchView());
        presenter.getAllCharacterNameSuccess(currentName, currentPage);
        charactersAdapter = new CharactersSearchAdapter(listCharacter, new listenerAdapterCharacter());
        rvCharacter.setHasFixedSize(true);
        rvCharacter.setLayoutManager(new GridLayoutManager(App.mContext, 2, RecyclerView.VERTICAL, false));
        rvCharacter.setAdapter(charactersAdapter);

        searchName();
        onListenerRecycler();
        return view;
    }

    private void onListenerRecycler(){
        rvCharacter.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == charactersAdapter.getItemCount() - 1) {
                    currentPage++;
                    loadMoreCharacters();
                }
            }
        });
    }

    private void searchName(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.getAllCharacterNameSuccess(query, currentPage);
                currentName = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void loadMoreCharacters() {
        presenter.getAllCharacterNameSuccess(currentName, currentPage);
    }

    private class listenerSearchView implements ISearchView {
        @Override
        public void showGetAllCharacterNameSuccess(Characters characters) {
            if(currentPage == 1) {
                charactersAdapter.updateList(characters.getResults());
                listCharacter = characters.getResults();
            }else {
                listCharacter.addAll(characters.getResults());
                charactersAdapter.updateList(listCharacter);
            }
        }

        @Override
        public void showSearchError(MessageResponse message) {
            Extensions.showErrorDialog(message.getMessage(), App.mContext);
        }
    }
    private class listenerAdapterCharacter implements OnItemClickListenerCharacter {
        @Override
        public void onItemClick(Character character) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("character", character);
            Navigation.findNavController(requireView()).navigate(R.id.action_searchFragment_to_detailFragment, bundle);
        }
    }
}