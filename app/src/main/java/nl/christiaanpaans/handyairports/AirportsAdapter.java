package nl.christiaanpaans.handyairports;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AirportsAdapter extends RecyclerView.Adapter<AirportsAdapter.ViewHolder> {

    private Context context;

    public AirportsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View airportView = inflater.inflate(R.layout.airport_recycler_item, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(airportView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AirportsAdapter.ViewHolder viewHolder, int i) {
        Airports airport = AirportFactory.getInstance().getAirports().get(i);

        viewHolder.textView.setText(airport.getName());

        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;

        String country = airport.getIso_country();

        int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;

        final String flag = new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));

        viewHolder.flagBox.setTextColor(Color.argb(255, 255,255,255));
        viewHolder.flagBox.setText(flag);

        viewHolder.municipalityBox.setText(airport.getMunicipality());

        final int index = i;
        viewHolder.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("AIRPORT_INDEX", index);
                intent.putExtra("AIRPORT_FLAG", flag);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return AirportFactory.getInstance().getAirports().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public TextView flagBox;
        public TextView municipalityBox;

        public void setListener(View.OnClickListener listener) {
            this.itemView.setOnClickListener(listener);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            flagBox = itemView.findViewById(R.id.flagBox);
            municipalityBox = itemView.findViewById(R.id.municipalityBox);
        }
    }
}
