package pt.ipg.desportoescolar;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class DesportoEscolarCursorAdapter extends RecyclerView.Adapter<DesportoEscolarCursorAdapter.DesportoEscolarViewHolder>{

    private Context context;
    private Cursor cursor = null;
    private View.OnClickListener viewHolderClickListener = null;
    private int lastAtletaClicked = -1;
    public DesportoEscolarCursorAdapter(Context context) {

        this.context = context;
    }

    public void refreshData(Cursor cursor) {
        if (this.cursor != cursor) {
            this.cursor = cursor;
            notifyDataSetChanged();
        }
    }

    public void setViewHolderClickListener(View.OnClickListener viewHolderClickListener) {
        this.viewHolderClickListener = viewHolderClickListener;
    }

    public int getLastFIlmekClicked() {
        return lastAtletaClicked;
    }

    @NonNull
    @Override

    public DesportoEscolarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.activity_atletas, parent, false);

        return new DesportoEscolarViewHolder(item);
    }





    @Override
    public void onBindViewHolder(@NonNull DesportoEscolarViewHolder holder, int position) {
        cursor.moveToPosition(position);
        Atletas atletas = DbTableAtletas.getCurrentAtletaFromCursor(cursor);
        holder.setAtleta(atletas);
    }


    @Override
    public int getItemCount() {
        if (cursor == null) return 0;

        return cursor.getCount();
    }

    public class DesportoEscolarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewNome;
        private TextView textViewData;
        private int AtletaId;

        public DesportoEscolarViewHolder(View itemView) {
            super(itemView);

            textViewNome = (TextView) itemView.findViewById(R.id.textViewNome);
            textViewAge = (TextView) itemView.findViewById(R.id.textViewAge);
            textViewCourse = (TextView) itemView.findViewById(R.id.textViewCourse);

            itemView.setOnClickListener(this);
        }

        public void setAtleta(Atletas atletas) {
            textViewNome.setText(atletas.getName());
            textViewAge.setText(String.format("%.2f", atletas.getAge()) + "");
            textViewCourse.setText(atletas.getCourse());
            AtletaId = atletas.getId();
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position == RecyclerView.NO_POSITION) {
                return;
            }

            if (viewHolderClickListener != null) {
                lastAtletaClicked = AtletaId;
                viewHolderClickListener.onClick(v);
            }
        }
    }
}


