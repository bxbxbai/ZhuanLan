package io.bxbxbai.zhuanlan.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.timessquare.CalendarPickerView;
import io.bxbxbai.zhuanlan.R;
import io.bxbxbai.zhuanlan.core.Constants;

import java.util.Calendar;
import java.util.Date;

public class PickDateFragment extends Fragment {
    private PickDateListener mOnDateSelectedListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mOnDateSelectedListener = (PickDateListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnDateSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_date, container, false);

        assert view != null;
        CalendarPickerView calendarPickerView = (CalendarPickerView) view.findViewById(R.id.calendar_view);
        Calendar nextDay = Calendar.getInstance();
        nextDay.add(Calendar.DAY_OF_YEAR, 1);
        calendarPickerView.init(Constants.Date.birthday, nextDay.getTime())
                .withSelectedDate(mOnDateSelectedListener.getDate());
        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                mOnDateSelectedListener.onValidDateSelected(date);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
        calendarPickerView.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
            @Override
            public void onInvalidDateSelected(Date date) {
                mOnDateSelectedListener.onInvalidDateSelected(date);
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        mOnDateSelectedListener = null;

        super.onDetach();
    }

    public static interface PickDateListener {
        public void onValidDateSelected(Date date);

        public void onInvalidDateSelected(Date date);

        public Date getDate();
    }
}
