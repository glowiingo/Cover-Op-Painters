package ca.bcit.coveropspainters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CurrentAdapter extends ArrayAdapter<GraffitiData> {
    Context _context;
    public CurrentAdapter(Context context, List<GraffitiData> graffiti) {
        super(context, 0, graffiti);
        _context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Activity act = (Activity)_context;
        GraffitiData graffiti = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_current_item, parent, false);
        }
        TextView field, id;
        field = convertView.findViewById(R.id.fields);
        id = convertView.findViewById(R.id.record_id);
        field.setText(graffiti.getDatasetid());
        id.setText(graffiti.getRecordid());
        return convertView;
    }
}
