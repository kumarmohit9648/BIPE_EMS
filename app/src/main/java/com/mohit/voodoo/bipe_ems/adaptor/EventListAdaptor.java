package com.mohit.voodoo.bipe_ems.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mohit.voodoo.bipe_ems.R;
import com.mohit.voodoo.bipe_ems.interfaces.OnItemClickGetPosition;
import com.mohit.voodoo.bipe_ems.responsemodel.EventListResponse;

import java.util.List;

public class EventListAdaptor extends RecyclerView.Adapter<EventListAdaptor.EventListHolder> {

    private Context context;
    private List<EventListResponse.Events> list;
    OnItemClickGetPosition onItemClickGetPosition;

    public EventListAdaptor(Context context, List<EventListResponse.Events> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EventListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recyclerview_pending, viewGroup, false);
        return new EventListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListHolder eventListHolder, int i) {
        eventListHolder.event_id.setText(list.get(i).event_name);
        eventListHolder.start_date.setText(list.get(i).start_date);
        eventListHolder.end_date.setText(list.get(i).end_date);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class EventListHolder extends RecyclerView.ViewHolder {

        private TextView event_id, start_date, end_date;
        private Button btn_allocate;

        public EventListHolder(@NonNull View itemView) {
            super(itemView);
            event_id = itemView.findViewById(R.id.event_id);
            start_date = itemView.findViewById(R.id.start_date);
            end_date = itemView.findViewById(R.id.end_date);
            btn_allocate = itemView.findViewById(R.id.btn_allocate);

            btn_allocate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickGetPosition.getPosition(getAdapterPosition());
                }
            });
        }
    }

    public void getValuePosition(OnItemClickGetPosition onItemClickGetPosition) {
        this.onItemClickGetPosition = onItemClickGetPosition;
    }
}
