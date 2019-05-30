package co.edu.manuelcardona.brsys;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class TurnAdapter extends RecyclerView.Adapter<TurnAdapter.MyViewHolder> {

    private List<Turn> turnList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView id, day, hour, barber_id, servide_id, customer_id, state;

        public MyViewHolder(View view){
            super(view);

        }
    }

    public TurnAdapter(List<Turn> turnList) {
        this.turnList = turnList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.turn_list_row, parent, false);

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
        Turn turn = turnList.get(position);

        holder.id.setText(""+turn.getId());
        holder.day.setText(""+turn.getDay());
        holder.hour.setText(""+turn.getHour());
        holder.barber_id.setText(""+turn.getBarber_id());
        holder.servide_id.setText(""+turn.getServide_id());
        holder.customer_id.setText(""+turn.getCustomer_id());
        holder.state.setText(""+turn.isState());
    }

    @Override
    public int getItemCount() {
        return turnList.size();
    }

    private View.OnClickListener mClickListener;

    public void setClickListener(View.OnClickListener callback){ mClickListener = callback; }
}
