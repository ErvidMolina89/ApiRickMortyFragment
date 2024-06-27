package com.wposs.apirickmortyfragment.View.Detail;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wposs.apirickmortyfragment.Models.Character;
import com.wposs.apirickmortyfragment.R;
import com.wposs.apirickmortyfragment.Utils.DateFormatType;
import com.wposs.apirickmortyfragment.Utils.DateUtils;
import com.wposs.apirickmortyfragment.Utils.Extensions;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailFragment extends Fragment {
    private Character character;
    private CircleImageView image;
    private TextView name, status, species, type, gender, origin, location, date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        image = view.findViewById(R.id.iv_imageDetail);
        name = view.findViewById(R.id.tv_nameDetail);
        status = view.findViewById(R.id.tv_statusDetail);
        species = view.findViewById(R.id.tv_spesiesDetail);
        type = view.findViewById(R.id.tv_typeDetail);
        gender = view.findViewById(R.id.tv_genderDetail);
        origin = view.findViewById(R.id.tv_originDetail);
        location = view.findViewById(R.id.tv_locationDetail);
        date = view.findViewById(R.id.tv_dateDetail);

        if (getArguments() != null) {
            Character character = getArguments().getParcelable("character");
            if (character != null) {
                this.character = character; // Show character details immediately
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        completeTransactionData();
    }

    private void completeTransactionData(){
        if (character != null){
            Extensions.convertImageService(character.getImage(), image, 300);
            name.setText(character.getName());
            status.setText(character.getStatus());
            species.setText(character.getSpecies());
            type.setText(character.getType());
            gender.setText(character.getGender());
            origin.setText(character.getOrigin().getName());
            location.setText(character.getLocation().getName());
            date.setText(DateUtils.formatDateString(character.getCreated(), DateFormatType.FORMAT_2));
        }
    }
}