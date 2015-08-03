package com.example.kenny.bitdate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChoosingFragment extends Fragment implements UserDataSource.UserDataCallbacks, CardStackContainer.SwipeCallbacks{

    private static final String TAG = "ChoosingFragment";

    private CardStackContainer mCardStack;
    private List<User> mUsers;
    private CardAdapter mCardAdapter;

    public ChoosingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mCardStack = (CardStackContainer)v.findViewById(R.id.card_stack);
        UserDataSource.getUnseenUsers(this);
        mUsers = new ArrayList<>();
        mCardAdapter = new CardAdapter(getActivity(), mUsers);
        mCardStack.setAdapter(mCardAdapter);
        mCardStack.setSwipeCallbacks(this);
        ImageButton nahButton = (ImageButton)v.findViewById(R.id.nah_button);
        nahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardStack.swipeLeft();
            }
        });

        ImageButton yeahButton = (ImageButton)v.findViewById(R.id.yeah_button);
        yeahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardStack.swipeRight();
            }
        });

        return v;
    }

    @Override
    public void onUsersFetched(List<User> users) {
        mUsers.addAll(users);
        mCardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSwipedRight(User user) {
        ActionDataSource.saveUserLiked(user.getID());
    }

    @Override
    public void onSwipedLeft(User user) {
        ActionDataSource.saveUserSkipped(user.getID());
    }
}
