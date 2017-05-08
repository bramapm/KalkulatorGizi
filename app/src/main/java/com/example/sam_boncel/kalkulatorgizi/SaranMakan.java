package com.example.sam_boncel.kalkulatorgizi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sam_boncel.kalkulatorgizi.entities.Makanan;
import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaranMakan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaranMakan extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Makanan> listSaranMkn;
    public ArrayAdapter<String> adapter;
    public ListView lv;
    public TextView teks;
    double hasil;

    public SaranMakan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaranMakan.
     */
    // TODO: Rename and change types and number of parameters
    public static SaranMakan newInstance(String param1, String param2) {
        SaranMakan fragment = new SaranMakan();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_saran_makan, container, false);
        teks = (TextView) rootView.findViewById(R.id.teks);
        lv = (ListView)rootView.findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Makanan selecetedMakanan = listSaranMkn.get(position);
                Intent i = new Intent(getContext(), DetailMakananActivity.class);
                //DetailMakananFragment dmFragment = new DetailMakananFragment();
//                Bundle i = new Bundle();
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//                manager.beginTransaction().replace(
//                        R.id.relativelayout_for_fragment,
//                        dmFragment).commit();
                i.putExtra("id_makanan", String.valueOf(selecetedMakanan.getId_makanan()));
                i.putExtra("nama_makanan", selecetedMakanan.getNama_makanan());
                i.putExtra("jenis", selecetedMakanan.getJenis());
                i.putExtra("kkal", selecetedMakanan.getKkal());
                i.putExtra("karbo", selecetedMakanan.getKarbo());
                i.putExtra("protein", selecetedMakanan.getProtein());
                i.putExtra("lemak", selecetedMakanan.getLemak());
                i.putExtra("foto", selecetedMakanan.getFoto());
                i.putExtra("keterangan", selecetedMakanan.getKeterangan());
                startActivity(i);
            }
        });
        this.listSaranMkn= new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null){
            hasil = bundle.getDouble("hasil");
            Log.d("hasil", String.valueOf(hasil));
            //Log.d("hasil", teks.toString());
            teks.setText(String.valueOf(hasil));
        }
        getSaran();

        return rootView;
    }

    private void getSaran() {

        FormData data = new FormData();
        data.add("method", "saran");
        data.add("key", "a");
        Log.d("H", "H:"+ String.valueOf(hasil));
        data.add("kal", String.valueOf(hasil));
        InternetTask uploadTask = new InternetTask("Record", data);
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
                                listSaranMkn.add(new Makanan(jsonArray.getJSONObject(i)));
                                listpost.add(makanan.getNama_makanan().toString()+"\n"+ makanan.getKkal().toString()+"kkal");
                            }
                            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listpost);
                            lv.setAdapter(adapter);
                        }
                    }else{
                    }
                } catch (JSONException e) {
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


