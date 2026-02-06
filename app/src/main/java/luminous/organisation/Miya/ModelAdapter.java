package luminous.organisation.Miya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide; // For image loading, add Glide dependency

import java.util.HashMap;
import java.util.List;

public class ModelAdapter extends ArrayAdapter<HashMap<String, String>> {

    private Context context;
    private List<HashMap<String, String>> models;

    public ModelAdapter(Context context, List<HashMap<String, String>> models) {
        super(context, 0, models);
        this.context = context;
        this.models = models;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_model, parent, false);
        }

        HashMap<String, String> currentModel = models.get(position);

        ImageView modelImage = convertView.findViewById(R.id.model_pic);
        TextView modelName = convertView.findViewById(R.id.model_name);
        TextView modelDescription = convertView.findViewById(R.id.model_details);

        modelName.setText(currentModel.get("name"));
        modelDescription.setText(currentModel.get("description"));

        // Load image using Glide (or your preferred image loading library)
        String imageUrl = currentModel.get("image");
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(android.R.drawable.sym_def_app_icon) // Placeholder while loading
                    .error(android.R.drawable.ic_menu_close_clear_cancel) // Error image if loading fails
                    .into(modelImage);
        } else {
            modelImage.setImageResource(android.R.drawable.sym_def_app_icon); // Default if no URL
        }

        return convertView;
    }
}