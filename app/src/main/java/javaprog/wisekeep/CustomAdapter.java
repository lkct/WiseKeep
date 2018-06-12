package javaprog.wisekeep;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewType;
        private final TextView textViewAmt;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Element " + getAdapterPosition() + " clicked.", Toast.LENGTH_LONG).show();
                }
            });
            textViewType = (TextView) v.findViewById(R.id.textViewItemType);
            textViewAmt = (TextView) v.findViewById(R.id.textViewItemAmt);
        }

        public List getTextView() {
            return Arrays.asList(textViewType, textViewAmt);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        List l = viewHolder.getTextView();
        TextView textViewType = (TextView) l.get(0);
        TextView textViewAmt = (TextView) l.get(1);
        // textView.setText("left"); // TODO: data in list at position
        // textView2.setText("right");
    }

    @Override
    public int getItemCount() {
        return 1; // TODO: length of list
    }

}
