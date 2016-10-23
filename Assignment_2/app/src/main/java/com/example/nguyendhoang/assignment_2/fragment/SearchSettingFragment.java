package com.example.nguyendhoang.assignment_2.fragment;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.nguyendhoang.assignment_2.Common.Constant;
import com.example.nguyendhoang.assignment_2.R;
import com.example.nguyendhoang.assignment_2.api.SearchRequest;
import com.example.nguyendhoang.assignment_2.databinding.FragmentSearchSettingBinding;
import com.example.nguyendhoang.assignment_2.util.CalendarUtil;
import com.example.nguyendhoang.assignment_2.util.StringUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class SearchSettingFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    private FragmentSearchSettingBinding mFragmentSearchSettingBinding;

    private SearchRequest mSearchRequest;
    private SearchSettingListener mSearchSettingListener;

    public void setSearchSettingListener(SearchSettingListener searchSettingListener) {
        this.mSearchSettingListener = searchSettingListener;
    }

    public SearchSettingFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static SearchSettingFragment newInstance(SearchRequest searchRequest) {
        SearchSettingFragment fragment = new SearchSettingFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.SEARCH_REQUEST_PARAMS, searchRequest);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchRequest = getArguments().getParcelable(Constant.SEARCH_REQUEST_PARAMS);
        }


        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.85), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentSearchSettingBinding = DataBindingUtil
                .inflate(inflater,R.layout.fragment_search_setting, container, false);

        mFragmentSearchSettingBinding.setSearchRequest(mSearchRequest);
        mFragmentSearchSettingBinding.setSearchSetting(this);
        mFragmentSearchSettingBinding.executePendingBindings();

        final View view = mFragmentSearchSettingBinding.getRoot();
        final LinearLayout linearLayout = mFragmentSearchSettingBinding.editName;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
        if(orientation()){
            params.setMargins(0,100,0,0);
        }
        linearLayout.setLayoutParams(params);


        return view;
    }

    private boolean orientation(){
        Configuration configuration = getResources()
                .getConfiguration();

        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        }
        return false;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setupView();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupView(){
        getDialog().setTitle("Search Setting");
        prepareSpinnerSort(mSearchRequest.getOrder());
    }

//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//        alertDialogBuilder.setTitle("Search Setting");
//        alertDialogBuilder.setPositiveButton("Save",  new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // on success
//                onBtnSaveClick();
//            }
//        });
//        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        return alertDialogBuilder.create();
//
//    }

    private void prepareSpinnerSort(String sortOrder) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_order, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFragmentSearchSettingBinding.spSort.setAdapter(adapter);

        int posSort = adapter.getPosition(sortOrder);
        mFragmentSearchSettingBinding.spSort.setSelection(posSort);
    }



    public void onDateBegin() {
        int year, month, dayOfMonth;

        String currentBirthday = mSearchRequest.getBeginDate();
        if (currentBirthday != null && !currentBirthday.equals("")) {

            Calendar currentCalendar = CalendarUtil.convertStringToCalendar(currentBirthday);
            year = currentCalendar.get(Calendar.YEAR);
            month = currentCalendar.get(Calendar.MONTH);
            dayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);
        } else {
            Calendar now = Calendar.getInstance();
            year = now.get(Calendar.YEAR);
            month = now.get(Calendar.MONTH);
            dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                year,
                month,
                dayOfMonth
        );

        datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String beginDate = StringUtil.createDateDDMMYY(dayOfMonth, monthOfYear, year);

        mSearchRequest.setBeginDate(beginDate);
        mFragmentSearchSettingBinding.etBeginDate.setText(beginDate);
    }

    public interface SearchSettingListener{
        void onFinishDialog(SearchRequest searchRequest);
    }


    public void onBtnSaveClick(){
        String sort= mFragmentSearchSettingBinding.spSort.getSelectedItem().toString();
        boolean arts= mFragmentSearchSettingBinding.cbArts.isChecked();
        boolean fashionAndStyle= mFragmentSearchSettingBinding.cbFashionAndStyle.isChecked();
        boolean sports= mFragmentSearchSettingBinding.cbSports.isChecked();

        mSearchRequest.setOrder(sort);
        mSearchRequest.setHasArts(arts);
        mSearchRequest.setHasFashionStyle(fashionAndStyle);
        mSearchRequest.setHasSports(sports);

        mSearchSettingListener.onFinishDialog(mSearchRequest);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(this)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();

    }
}
