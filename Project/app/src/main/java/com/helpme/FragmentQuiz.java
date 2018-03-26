package com.helpme;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import Model.Contact;

public class FragmentQuiz extends android.support.v4.app.Fragment {
    View view;

    private RecyclerView recyclerView;
    private List<Contact> contactList;

    public FragmentQuiz() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactList = new ArrayList<>();
        contactList.add(new Contact("Moamen Hassan", "11111", R.drawable.carpenter));
        contactList.add(new Contact("Moamen Attia", "11111", R.drawable.carpenter));
        contactList.add(new Contact("Moamen Saleh", "11111", R.drawable.carpenter));
        contactList.add(new Contact("Moamen Ali", "11111", R.drawable.carpenter));
        contactList.add(new Contact("Moamen Abdellal", "11111", R.drawable.carpenter));
        contactList.add(new Contact("Moamen Omar", "11111", R.drawable.carpenter));
        contactList.add(new Contact("Moamen Ahmed", "11111", R.drawable.carpenter));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quiz_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.contact_recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }
}
