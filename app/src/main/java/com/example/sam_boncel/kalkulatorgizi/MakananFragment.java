package com.example.sam_boncel.kalkulatorgizi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sam_boncel.kalkulatorgizi.entities.Makanan;
import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.KeyValue;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakananFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakananFragment extends Fragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ListView lv;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Makanan> listMakanan;


    public MakananFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakananFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakananFragment newInstance(String param1, String param2) {
        MakananFragment fragment = new MakananFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_makanan, container, false);

        lv = (ListView)rootView.findViewById(R.id.listView);
        this.listMakanan = new ArrayList<>();
        getMakanan();


//        lv.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1));
        return rootView;
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    public void getMakanan(){
            FormData data = new FormData();
            data.add("method", "get_makanan");
            InternetTask uploadTask = new InternetTask("Makanan", data);
            uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
                @Override
                public void OnInternetTaskFinished(InternetTask internetTask) {
                    try {
                        JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                        if (jsonObject.get("code").equals(200)){
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if (jsonArray.length() > 0){
                                ArrayList<String> listpost = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length();i++){
                                    Makanan makanan = new Makanan(jsonArray.getJSONObject(i));
                                    listMakanan.add(new Makanan(jsonArray.getJSONObject(i)));
                                    listpost.add(makanan.getNama_makanan().toString()+"\n"+ makanan.getKkal().toString()+"kkal");
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listpost);
                                lv.setAdapter(adapter);
                            }
                        }else{
//                            Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        //                       Snackbar.make(clContent, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void OnInternetTaskFailed(InternetTask internetTask) {
                    //                   Snackbar.make(clContent, internetTask.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            });
            uploadTask.execute();
        }
    }
