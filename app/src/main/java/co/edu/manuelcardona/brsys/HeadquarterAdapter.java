package co.edu.manuelcardona.brsys;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HeadquarterAdapter extends RecyclerView.Adapter<HeadquarterAdapter.MyViewHolder> {

    private List<Headquarter> headquarterList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView businessName, address, phone, longitude, latitude;

        public MyViewHolder(View view) {
            super(view);
            businessName = (TextView) view.findViewById(R.id.businessNameH);
            address = (TextView) view.findViewById(R.id.address);
            phone = (TextView) view.findViewById(R.id.phone);
            longitude = (TextView) view.findViewById(R.id.longitude);
            latitude = (TextView) view.findViewById(R.id.latitude);

        }
    }

    public HeadquarterAdapter(List<Headquarter> headquarterList)
    {
        this.headquarterList = headquarterList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.headquarter_list_row, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        /////////

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });
        //////

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Headquarter headquarter = headquarterList.get(position);
        holder.businessName.setText(""+headquarter.getBusinessName());
        holder.address.setText(""+headquarter.getAddress());
        holder.phone.setText(""+headquarter.getPhone());
        holder.longitude.setText(""+ headquarter.getLongitude());
        holder.latitude.setText(""+headquarter.getLatitude());
    }

    @Override
    public int getItemCount() { return headquarterList.size(); }

    private View.OnClickListener mClickListener;

    public void setClickListener(View.OnClickListener callback){
        mClickListener = callback;
    }
}
