package com.chaitanya.quicksoft.glutton.activities.restaurant;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chaitanya.quicksoft.glutton.interfaces.BottomSheetDialogInterface;
import com.chaitanya.quicksoft.glutton.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Bottomfrag extends BottomSheetDialogFragment{

    public static String selected_mode="";
    public Context context1;
    public BottomSheetDialogInterface bottomSheetDialogInterface;

    public Bottomfrag(BottomSheetDialogInterface bottomSheetDialogInterface1) {
        this.bottomSheetDialogInterface = bottomSheetDialogInterface1;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ImageView cancel_dialog = view.findViewById(R.id.cancel_dialog);
        Button place_order = view.findViewById(R.id.place_order);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RadioButton cashradioButton = view.findViewById(R.id.cashradioButton);
        RadioButton onlineradioButton = view.findViewById(R.id.onlineradioButton);
        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    switch (selectedId) {

                        case R.id.cashradioButton:
                            bottomSheetDialogInterface.getresponse("COD");
                            dismiss();

                            break;
                        case R.id.onlineradioButton:
                            bottomSheetDialogInterface.getresponse("Online_pay");
                            dismiss();

                            break;

                    }
                }else {
                    Toast.makeText(getActivity(),"Please select payment type",Toast.LENGTH_LONG).show();
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
