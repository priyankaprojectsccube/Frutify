package com.app.fr.fruiteefy.user_picorear;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.fr.fruiteefy.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllOfferFragment extends Fragment {
View v;
RecyclerView rv_all_posts;
    public AllOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_all_offer, container, false);
        initView(v);
      return v;
    }

    public void initView(View v)
    {
        rv_all_posts=v.findViewById(R.id.rv_all_posts);


    }//initViewClose

}
