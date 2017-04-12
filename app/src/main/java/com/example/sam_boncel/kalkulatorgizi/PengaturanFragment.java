package com.example.sam_boncel.kalkulatorgizi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PengaturanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PengaturanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ListView lv;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public PengaturanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PengaturanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PengaturanFragment newInstance(String param1, String param2) {
        PengaturanFragment fragment = new PengaturanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getContext(), "Coba pengaturan", Toast.LENGTH_SHORT).show();
        View rootView = inflater.inflate(R.layout.fragment_pengaturan, container, false);

        lv = (ListView) rootView.findViewById(R.id.listViewPengaturan);


        ArrayList<String> li = new ArrayList<>();
        li.add("Pengaturan Akun");
        li.add("Pengaturan Data Diri");
        li.add("Pengaturan Aplikasi");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, li);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    Toolbar toolbar1 = (Toolbar) getActivity().findViewById(R.id.toolbar);
                    toolbar1.setTitle("Akun");
                    DataAkunFragment dataAkunFragment = new DataAkunFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(
                            R.id.relativelayout_for_fragment,
                            dataAkunFragment).commit();
                }   else if (position == 1){
                    Toolbar toolbar1 = (Toolbar) getActivity().findViewById(R.id.toolbar);
                    toolbar1.setTitle("Data Diri");
                    DataDiriFragment dataDiriFragment = new DataDiriFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    manager.beginTransaction().replace(
                            R.id.relativelayout_for_fragment,
                            dataDiriFragment).commit();
                }
// else if (position == 2){
//                    Toolbar toolbar1 = (Toolbar) getActivity().findViewById(R.id.toolbar);
//                    toolbar1.setTitle("Pengaturan Aplikasi");
//                    SetAplikasiFragment setAplikasiFragmentFragment = new SetAplikasiFragment();
//                    FragmentManager manager = getActivity().getSupportFragmentManager();
//                    manager.beginTransaction().replace(
//                            R.id.relativelayout_for_fragment,
//                            setAplikasiFragmentFragment).commit();
//                }
            }
        });
        return rootView;
    }
}
