package co.edu.manuelcardona.brsys;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class BarbershopAdapter extends RecyclerView.Adapter<BarbershopAdapter.MyViewHolder> {

    private List<Barbershop> barbershopList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView businessName;

        public MyViewHolder(View view){
            super(view);
            businessName = (TextView) view.findViewById(R.id.businessName);
        }
    }

    public BarbershopAdapter(List<Barbershop> barbershopList){
        this.barbershopList = barbershopList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.barbershop_list_row, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(view);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Barbershop barbershop = barbershopList.get(position);
        holder.businessName.setText(barbershop.getBusinessName());
    }

    @Override
    public int getItemCount() {
        return barbershopList.size();
    }

    private View.OnClickListener mClickListener;

    public void setClickListener(View.OnClickListener callback){ mClickListener = callback; }

}
