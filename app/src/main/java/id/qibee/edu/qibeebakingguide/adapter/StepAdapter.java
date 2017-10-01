package id.qibee.edu.qibeebakingguide.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.qibee.edu.qibeebakingguide.InterfaceOnItemClickListener;
import id.qibee.edu.qibeebakingguide.R;
import id.qibee.edu.qibeebakingguide.model.StepModel;

/**
 * Created by masq2 on 16/09/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder>{

    private Context mContext;
    private List<StepModel> mStep;
    private InterfaceOnItemClickListener mListener;

    public StepAdapter(Context mContext, List<StepModel> mStep, InterfaceOnItemClickListener mListener) {
        this.mContext = mContext;
        this.mStep = mStep;
        this.mListener = mListener;
    }

    @Override
    public StepAdapter.StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View rootView = layoutInflater.inflate(R.layout.item_step,parent,false);
        return new StepHolder(rootView);
    }

    @Override
    public void onBindViewHolder(StepAdapter.StepHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mStep.size();
    }

    public class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_step_description)
        TextView textStepDescription;

        public StepHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.onItemClick(position);
        }

        public void bind(int position) {
            StepModel step = mStep.get(position);
            textStepDescription.setText(step.getShortDescription());
        }
    }
}
