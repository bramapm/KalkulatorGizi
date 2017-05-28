package com.example.sam_boncel.kalkulatorgizi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.sam_boncel.kalkulatorgizi.entities.Olahraga;
import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SaranOlahraga.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SaranOlahraga#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaranOlahraga extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Olahraga> listSaranOlg;
    public ArrayAdapter<String> adapter;
    public ListView lv;
    public TextView teks;
    double hasil;

    private OnFragmentInteractionListener mListener;

    public SaranOlahraga() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaranOlahraga.
     */
    // TODO: Rename and change types and number of parameters
    public static SaranOlahraga newInstance(String param1, String param2) {
        SaranOlahraga fragment = new SaranOlahraga();
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
        View rootView = inflater.inflate(R.layout.fragment_saran_makan, container, false);
        teks = (TextView) rootView.findViewById(R.id.teks);
        lv = (ListView)rootView.findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Olahraga selectedOlahraga = listSaranOlg.get(position);
                Intent i = new Intent(getContext(), DetailMakananActivity.class);
                i.putExtra("id_olahraga", selectedOlahraga.getId_olahraga());
                i.putExtra("nama_olahraga", selectedOlahraga.getNama_olahraga());
                i.putExtra("kkal", selectedOlahraga.getKkal());
                i.putExtra("keterangan", selectedOlahraga.getKeterangan());
                i.putExtra("foto", selectedOlahraga.getFoto());
                startActivity(i);
            }
        });
        this.listSaranOlg= new ArrayList<>();

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
        data.add("key", "lebih");
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
                                Olahraga olahraga = new Olahraga(jsonArray.getJSONObject(i));
                                listSaranOlg.add(new Olahraga(jsonArray.getJSONObject(i)));
                                listpost.add(olahraga.getNama_olahraga().toString()+"\n"+ olahraga.getKkal().toString()+"kkal");
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}