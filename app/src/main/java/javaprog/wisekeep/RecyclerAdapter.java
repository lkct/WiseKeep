package javaprog.wisekeep;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final List<FileApp.Term> list;
    private final List<Integer> typeStrId;
    private final Class detailAct;

    RecyclerAdapter(String i_o) {
        if (i_o.equals(FileApp.OUT)) {
            list = FileApp.outList;
            typeStrId = FileApp.outTypeStrId;
            detailAct = OutcomeDetail.class;
        } else {
            list = FileApp.inList;
            typeStrId = FileApp.inTypeStrId;
            detailAct = IncomeDetail.class;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewType;
        private final TextView textViewAmt;
        private final Class detailAct;

        ViewHolder(View v, Class act) {
            super(v);
            detailAct = act;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context ctx = v.getContext();
                    FileApp.curDetail = getAdapterPosition();
                    Intent intent = new Intent(ctx, detailAct);
                    ctx.startActivity(intent);
                }
            });
            textViewType = v.findViewById(R.id.textViewItemType);
            textViewAmt = v.findViewById(R.id.textViewItemAmt);
        }

        private List<TextView> getTextView() {
            return Arrays.asList(textViewType, textViewAmt);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item, viewGroup, false);

        return new ViewHolder(v, detailAct);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        List<TextView> l = viewHolder.getTextView();
        TextView textViewType = l.get(0);
        TextView textViewAmt = l.get(1);
        FileApp.Term t = list.get(position);
        // TODO: textViewType.setCompoundDrawablesRelative(start, null, null, null);
        textViewType.setText(typeStrId.get(t.type));
        textViewAmt.setText(textViewAmt.getResources().getString(R.string.yuan_amt, t.amount));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
