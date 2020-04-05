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

public class CurrentEventListAdapter extends ArrayAdapter<Events> {
    Context _context;
    public CurrentEventListAdapter(Context context, List<Events> events) {
        super(context, 0, events);
        _context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        final Activity act = (Activity)_context;
        Events events = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_current_event_list_adapter, parent, false);
        }
        TextView email, name, address, time, date;
        email = convertView.findViewById(R.id.email);
        name = convertView.findViewById(R.id.userName);
        address = convertView.findViewById(R.id.address);
        time = convertView.findViewById(R.id.list_time);
        date = convertView.findViewById(R.id.list_date);
        address.setText(events.getAddress());
        time.setText(events.getTime());
        date.setText(events.getDate());
        email.setText(events.getCreatedBy());
        name.setText(events.getEmail());
        return convertView;
    }
}
