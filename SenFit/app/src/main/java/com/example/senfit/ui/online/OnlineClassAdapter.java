
/*Author : Portia Siddiqua 107741175*/


package com.example.senfit.ui.online;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.senfit.R;
import com.example.senfit.covidLog.DateHelper;
import com.example.senfit.dataContext.views.OnlineClassView;
import com.example.senfit.ui.inperson.InpersonAdapter;
import com.example.senfit.ui.inperson.InpersonClassData;
import com.example.senfit.uiHelpers.DateTimeFormatHelper;

import java.util.List;

public class OnlineClassAdapter extends RecyclerView.Adapter<OnlineClassAdapter.ViewHolder>{

    private List<OnlineClassView> mDataSet;

    private InpersonAdapter.SelectClassListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mClassImageView;
        private final TextView mClassNameTextView;
        private final TextView mClassDateTextView;
        private final TextView mClassTimeTextView;
        private final TextView mClassInstrctorTextView;
        private final Button mClassEnrollButton;
        private final View mInpersonRowView;
        private final TextView mEnrolledTextView;
        private final TextView classDescription;
        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                private String TAG = "OnlineClass";

                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");

                }
            });
            mInpersonRowView =  v.findViewById(R.id.inperson_row);
            mClassImageView = (ImageView) v.findViewById(R.id.inperson_row_image);
            mClassNameTextView = (TextView) v.findViewById(R.id.inperson_row_class_name);
            mClassDateTextView = (TextView) v.findViewById(R.id.inperson_row_class_date);
            mClassTimeTextView = (TextView) v.findViewById(R.id.inperson_row_class_time);
            mClassInstrctorTextView = (TextView) v.findViewById(R.id.inperson_row_class_instructor_name);
            mClassEnrollButton = (Button) v.findViewById(R.id.inperson_row_class_enroll);
            mEnrolledTextView = (TextView) v.findViewById(R.id.inperson_row_class_enrolled_txt);
            classDescription=v.findViewById(R.id.online_row_class_class_description);
        }

        public ImageView getClassImageView() {
            return mClassImageView;
        }

        public TextView getClassNameTextView() {
            return mClassNameTextView;
        }

        public TextView getClassDateTextView() {
            return mClassDateTextView;
        }

        public TextView getClassTimeTextView() {
            return mClassTimeTextView;
        }

        public TextView getClassInstrctorTextView() {
            return mClassInstrctorTextView;
        }

        public Button getClassEnrollButton() {
            return mClassEnrollButton;
        }

        public TextView getClassEnroledTextView() {
            return mEnrolledTextView;
        }

        public View getInpersonRowView() {
            return mInpersonRowView;
        }

        public TextView getClassDescription() {
            return classDescription;
        }
    }


    public OnlineClassAdapter(List<OnlineClassView> dataSet, InpersonAdapter.SelectClassListener listener) {
        this.listener=listener;
        mDataSet = dataSet;
    }


    public void updateDataSet(List<OnlineClassView> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OnlineClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.online_row_item, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull OnlineClassAdapter.ViewHolder viewHolder, int position) {
        viewHolder.getClassNameTextView().setText(mDataSet.get(position).className);
        viewHolder.getClassDateTextView().setText(DateTimeFormatHelper.formatDate(mDataSet.get(position).classDate));
        viewHolder.getClassTimeTextView().setText(DateTimeFormatHelper.formatTime(mDataSet.get(position).startTime));
        viewHolder.getClassInstrctorTextView().setText(mDataSet.get(position).instructorName);
        viewHolder.getClassDescription().setText(mDataSet.get(position).classDescription);
        if (mDataSet.get(position).enrolled) {
            viewHolder.getClassEnroledTextView().setVisibility(View.VISIBLE);
            viewHolder.getClassEnrollButton().setVisibility(View.INVISIBLE);
        } else {
            viewHolder.getClassEnrollButton().setVisibility(View.VISIBLE);
            viewHolder.getClassEnroledTextView().setVisibility(View.INVISIBLE);
        }

        viewHolder.getClassEnrollButton().setOnClickListener((v)->{
            OnlineClassView onlineClassView = mDataSet.get(position);
            //gets the current gym class being selected
            if (listener!=null)//checks listener is not null
                listener.selectClassItem(position, onlineClassView.onlineClassId);//notifies the main ui of selected class
        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
