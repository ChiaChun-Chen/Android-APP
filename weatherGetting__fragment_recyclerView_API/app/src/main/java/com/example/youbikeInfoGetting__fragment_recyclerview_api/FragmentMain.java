package com.example.youbikeInfoGetting__fragment_recyclerview_api;

import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.youbikeInfoGetting__fragment_recyclerview_api.placeholder.mPlaceholder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    //http
    OkHttpClient client = new OkHttpClient().newBuilder().build();

    public FragmentMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment FragmentMain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMain newInstance(String param1) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

        //http
        Request request = new Request.Builder()
                .url("https://tcgbusfs.blob.core.windows.net/dotapp/youbike/v2/youbike_immediate.json")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(getActivity(), "failedToGet", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String result = response.body().string();

                try {
                    JSONArray obj = new JSONArray(result);
                    for(int i=0; i< obj.length(); i++){
                        mPlaceholder.addItem(mPlaceholder
                                .createMyPlaceholderItem(i, obj.getJSONObject(i).getString("sna"),
                                        obj.getJSONObject(i).getString("sbi")));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                mParam1 = result;
                Log.d("OkHttp result: ", mParam1);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button next = getActivity().findViewById(R.id.btn_toNextFragment);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), mParam1, Toast.LENGTH_SHORT).show();
                TextView txt = getActivity().findViewById(R.id.txtView);
                txt.setText("myPara1= "+mParam1);

                ItemFragment itemFragment = new ItemFragment();

                getFragmentManager().beginTransaction()
                        .add(R.id.main_layout, itemFragment.newInstance(1, mParam1), "itemFrag")
                        .addToBackStack("back")
                        .commit();
            }
        });
    }

}