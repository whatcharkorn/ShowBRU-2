package com.example.android.showbru.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.showbru.R;
import com.example.android.showbru.utility.GetAllData;
import com.example.android.showbru.utility.MyAlert;
import com.example.android.showbru.utility.MyConstant;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainFragment extends Fragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Register Controller
        registerController();

//        Login Controller
        loginController();

    } //Main Method

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);


                String userString = userEditText.getText().toString().trim();
                String passwordString = passwordEditText.getText().toString().trim();

                if (userString.isEmpty() || passwordString.isEmpty()) {

                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.normalDialog("Have Space", "Please Fill Every Blanks");

                } else {
//                    No Space

                    MyConstant myConstant = new MyConstant();
                    boolean b = true;
                    String truePass = null, nameUser = null;
                    MyAlert myAlert = new MyAlert(getActivity());

                    try {

                        GetAllData getAllData = new GetAllData(getActivity());
                        getAllData.execute(myConstant.getUrlGetAllUser());

                        String jsonString = getAllData.get();
                        Log.d("26AprilV1", "JSON ===>" + jsonString);

                        JSONArray jsonArray = new JSONArray(jsonString);

                        for (int i=0; i<jsonArray.length(); i+=1) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            if (userString.equals(jsonObject.getString("User"))) {

                                b = false;
                                truePass = jsonObject.getString("Password");
                                nameUser = jsonObject.getString("Name");

                            }

                        }

                        if (b) {

                            myAlert.normalDialog("User False", "no match in Database");

                        } else if (passwordString.equals(truePass)) {
                            Toast.makeText(getActivity(),"Welcome " + nameUser, Toast.LENGTH_SHORT).show();

                        } else {
                            myAlert.normalDialog("False", "wrong Pass");

                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    if

                }
            }
        });
    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Replace Fragment
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();


            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;


    }

}   //Main Class
