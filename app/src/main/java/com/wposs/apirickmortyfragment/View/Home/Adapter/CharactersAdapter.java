package com.wposs.apirickmortyfragment.View.Home.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.wposs.apirickmortyfragment.Base.App;
import com.wposs.apirickmortyfragment.Models.Character;
import com.wposs.apirickmortyfragment.R;
import com.wposs.apirickmortyfragment.Utils.Extensions;
import com.wposs.apirickmortyfragment.View.Interfaces.OnItemClickListenerCharacter;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {

    private List<Character> characters;
    private String categoria;
    private final OnItemClickListenerCharacter listener;

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Character> characters, String filter) {
        this.characters = characters;
        this.categoria = filter;
        notifyDataSetChanged();
    }

    public CharactersAdapter(List<Character> characters, OnItemClickListenerCharacter listener, String filter) {
        this.characters = characters;
        this.categoria = filter;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(App.mContext).inflate(R.layout.item_characters, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setDetailCategoria(characters.get(position));
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView name;
        private final TextView filter;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_character);
            name = itemView.findViewById(R.id.tv_nameCharacter);
            filter = itemView.findViewById(R.id.tv_filterCharacter);

        }
        void setDetailCategoria(Character character){
            name.setText(character.getName());
            filter.setText(categoria);
            Extensions.convertImageService(character.getImage(), imageView, 150);
            imageView.setOnClickListener(v -> {
                listener.onItemClick(character);
            });
        }
    }
}

