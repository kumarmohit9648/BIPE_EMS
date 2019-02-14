package com.mohit.voodoo.bipe_ems.adaptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohit.voodoo.bipe_ems.R;
import com.mohit.voodoo.bipe_ems.interfaces.OnItemClickGetPosition;
import com.mohit.voodoo.bipe_ems.responsemodel.InvitationListResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InviteeListAdaptor extends RecyclerView.Adapter<InviteeListAdaptor.InviteeListHolder> {

    private static final String TAG = "InviteeListAdaptor";
    private Context context;
    OnItemClickGetPosition onItemClickGetPosition;
    private List<InvitationListResponse.Invitation> list;

    public InviteeListAdaptor(Context context, List<InvitationListResponse.Invitation> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public InviteeListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_invitee_list, viewGroup, false);
        return new InviteeListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InviteeListHolder inviteeListHolder, int i) {
        inviteeListHolder.invitation_id.setText(list.get(i).invitation_id);
        inviteeListHolder.name.setText(list.get(i).name);
        inviteeListHolder.mobile_no.setText(list.get(i).mobile_no);
        inviteeListHolder.email_id.setText(list.get(i).email_id);
        inviteeListHolder.description.setText(list.get(i).description);
        inviteeListHolder.note.setText(list.get(i).note);

        Log.d(TAG, "onBindViewHolder: " + list.get(i).qr);
        Picasso.with(context).load(list.get(i).qr).fit().into(inviteeListHolder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class InviteeListHolder extends RecyclerView.ViewHolder {

        private TextView invitation_id, name, mobile_no, email_id, description, note;
        private Button btn_allocate;
        private ImageView image;

        public InviteeListHolder(@NonNull View itemView) {
            super(itemView);
            invitation_id = itemView.findViewById(R.id.invitation_id);
            name = itemView.findViewById(R.id.name);
            mobile_no = itemView.findViewById(R.id.mobile_no);
            email_id = itemView.findViewById(R.id.email_id);
            description = itemView.findViewById(R.id.description);
            note = itemView.findViewById(R.id.note);
            btn_allocate = itemView.findViewById(R.id.btn_allocate);
            image = itemView.findViewById(R.id.image);

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
