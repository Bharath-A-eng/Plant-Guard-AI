package com.rymec.plantguardai;

import android.app.AlertDialog;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<HistoryModel> historyList;

    public HistoryAdapter(List<HistoryModel> historyList) {
        this.historyList = historyList;
    }

    private String getLocalizedPlantName(View view, String plant) {

        switch (plant) {

            case "Apple":
                return view.getContext().getString(R.string.apple);

            case "Blueberry":
                return view.getContext().getString(R.string.blueberry);

            case "Cherry":
                return view.getContext().getString(R.string.cherry);

            case "Corn":
                return view.getContext().getString(R.string.corn);

            case "Grape":
                return view.getContext().getString(R.string.grape);

            case "Orange":
                return view.getContext().getString(R.string.orange);

            case "Peach":
                return view.getContext().getString(R.string.peach);

            case "Bell Pepper":
                return view.getContext().getString(R.string.bell_pepper);

            case "Potato":
                return view.getContext().getString(R.string.potato);

            case "Raspberry":
                return view.getContext().getString(R.string.raspberry);

            case "Soybean":
                return view.getContext().getString(R.string.soybean);

            case "Squash":
                return view.getContext().getString(R.string.squash);

            case "Strawberry":
                return view.getContext().getString(R.string.strawberry);

            case "Tomato":
                return view.getContext().getString(R.string.tomato);

            default:
                return plant;
        }
    }
    private String getTranslatedDisease(String disease) {

        if (disease == null) return "";

        switch (Locale.getDefault().getLanguage()) {

            // ===================== HINDI =====================
            case "hi":

                switch (disease.toLowerCase(Locale.ROOT)) {

                    // ---------- Apple ----------
                    case "apple___apple_scab":
                        return "सेब - एप्पल स्कैब";

                    case "apple___black_rot":
                        return "सेब - ब्लैक रॉट";

                    case "apple___cedar_apple_rust":
                        return "सेब - सीडर एप्पल रस्ट";

                    case "apple___healthy":
                        return "सेब - स्वस्थ";

                    // ---------- Blueberry ----------
                    case "blueberry___healthy":
                        return "ब्लूबेरी - स्वस्थ";

                    // ---------- Cherry ----------
                    case "cherry___healthy":
                        return "चेरी - स्वस्थ";

                    case "cherry___powdery_mildew":
                        return "चेरी - पाउडरी मिल्ड्यू";

                    // ---------- Corn ----------
                    case "corn_(maize)___healthy":
                        return "मक्का - स्वस्थ";

                    case "corn_(maize)___common_rust_":
                        return "मक्का - कॉमन रस्ट";

                    case "corn_(maize)___cercospora_leaf_spot_gray_leaf_spot":
                        return "मक्का - ग्रे लीफ स्पॉट";

                    case "corn_(maize)___northern_leaf_blight":
                        return "मक्का - नॉर्दर्न लीफ ब्लाइट";

                    // ---------- Grape ----------
                    case "grape___healthy":
                        return "अंगूर - स्वस्थ";

                    case "grape___black_rot":
                        return "अंगूर - ब्लैक रॉट";

                    case "grape___esca_(black_measles)":
                        return "अंगूर - एस्का (ब्लैक मीज़ल्स)";

                    case "grape___leaf_blight_(isariopsis_leaf_spot)":
                        return "अंगूर - पत्ती झुलसा रोग";

                    default:
                        return disease;
                }

                // ===================== KANNADA =====================
            case "kn":

                switch (disease.toLowerCase(Locale.ROOT)) {

                    // ---------- Apple ----------
                    case "apple___apple_scab":
                        return "ಸೇಬು - ಆಪಲ್ ಸ್ಕ್ಯಾಬ್";

                    case "apple___black_rot":
                        return "ಸೇಬು - ಕಪ್ಪು ಕೊಳೆ";

                    case "apple___cedar_apple_rust":
                        return "ಸೇಬು - ಸೀಡರ್ ಆಪಲ್ ರಸ್ಟ್";

                    case "apple___healthy":
                        return "ಸೇಬು - ಆರೋಗ್ಯಕರ";

                    // ---------- Blueberry ----------
                    case "blueberry___healthy":
                        return "ಬ್ಲೂಬೆರಿ - ಆರೋಗ್ಯಕರ";

                    // ---------- Cherry ----------
                    case "cherry___healthy":
                        return "ಚೆರ್ರಿ - ಆರೋಗ್ಯಕರ";

                    case "cherry___powdery_mildew":
                        return "ಚೆರ್ರಿ - ಪುಡಿ ಬೂದಿ ರೋಗ";

                    // ---------- Corn ----------
                    case "corn_(maize)___healthy":
                        return "ಜೋಳ - ಆರೋಗ್ಯಕರ";

                    case "corn_(maize)___common_rust_":
                        return "ಜೋಳ - ಸಾಮಾನ್ಯ ರಸ್ಟ್";

                    case "corn_(maize)___cercospora_leaf_spot_gray_leaf_spot":
                        return "ಜೋಳ - ಬೂದು ಎಲೆ ಚುಕ್ಕೆ ರೋಗ";

                    case "corn_(maize)___northern_leaf_blight":
                        return "ಜೋಳ - ಉತ್ತರದ ಎಲೆ ಸುಡುವ ರೋಗ";

                    // ---------- Grape ----------
                    case "grape___healthy":
                        return "ದ್ರಾಕ್ಷಿ - ಆರೋಗ್ಯಕರ";

                    case "grape___black_rot":
                        return "ದ್ರಾಕ್ಷಿ - ಕಪ್ಪು ಕೊಳೆ";

                    case "grape___esca_(black_measles)":
                        return "ದ್ರಾಕ್ಷಿ - ಎಸ್ಕಾ (ಬ್ಲ್ಯಾಕ್ ಮೀಸಲ್ಸ್)";

                    case "grape___leaf_blight_(isariopsis_leaf_spot)":
                        return "ದ್ರಾಕ್ಷಿ - ಎಲೆ ಸುಡುವ ರೋಗ";

                    // ---------- Orange ----------
                    case "orange___haunglongbing_(citrus_greening)":
                        return "ಕಿತ್ತಳೆ - ಸಿಟ್ರಸ್ ಗ್ರೀನಿಂಗ್";

                    // ---------- Peach ----------
                    case "peach___bacterial_spot":
                        return "ಪೀಚ್ - ಬ್ಯಾಕ್ಟೀರಿಯಾ ಚುಕ್ಕೆ ರೋಗ";

                    case "peach___healthy":
                        return "ಪೀಚ್ - ಆರೋಗ್ಯಕರ";

                    // ---------- Bell Pepper ----------
                    case "pepper,_bell___bacterial_spot":
                        return "ಬೆಲ್ ಪೆಪ್ಪರ್ - ಬ್ಯಾಕ್ಟೀರಿಯಾ ಚುಕ್ಕೆ ರೋಗ";

                    case "pepper,_bell___healthy":
                        return "ಬೆಲ್ ಪೆಪ್ಪರ್ - ಆರೋಗ್ಯಕರ";

                    // ---------- Potato ----------
                    case "potato___early_blight":
                        return "ಆಲೂಗಡ್ಡೆ - ಆರಂಭಿಕ ಎಲೆ ಸುಡುವ ರೋಗ";

                    case "potato___late_blight":
                        return "ಆಲೂಗಡ್ಡೆ - ತಡವಾದ ಎಲೆ ಸುಡುವ ರೋಗ";

                    case "potato___healthy":
                        return "ಆಲೂಗಡ್ಡೆ - ಆರೋಗ್ಯಕರ";

                    // ---------- Raspberry ----------
                    case "raspberry___healthy":
                        return "ರಾಸ್ಪ್ಬೆರಿ - ಆರೋಗ್ಯಕರ";

                    // ---------- Soybean ----------
                    case "soybean___healthy":
                        return "ಸೋಯಾಬೀನ್ - ಆರೋಗ್ಯಕರ";

                    // ---------- Squash ----------
                    case "squash___powdery_mildew":
                        return "ಸ್ಕ್ವಾಶ್ - ಪುಡಿ ಬೂದಿ ರೋಗ";

                    // ---------- Strawberry ----------
                    case "strawberry___healthy":
                        return "ಸ್ಟ್ರಾಬೆರಿ - ಆರೋಗ್ಯಕರ";

                    case "strawberry___leaf_scorch":
                        return "ಸ್ಟ್ರಾಬೆರಿ - ಎಲೆ ಸುಡುವ ರೋಗ";

                    // ---------- Tomato ----------
                    case "tomato___bacterial_spot":
                        return "ಟೊಮೇಟೊ - ಬ್ಯಾಕ್ಟೀರಿಯಾ ಚುಕ್ಕೆ ರೋಗ";

                    case "tomato___early_blight":
                        return "ಟೊಮೇಟೊ - ಆರಂಭಿಕ ಎಲೆ ಸುಡುವ ರೋಗ";

                    case "tomato___late_blight":
                        return "ಟೊಮೇಟೊ - ತಡವಾದ ಎಲೆ ಸುಡುವ ರೋಗ";

                    case "tomato___leaf_mold":
                        return "ಟೊಮೇಟೊ - ಎಲೆ ಬೂದಿ";

                    case "tomato___septoria_leaf_spot":
                        return "ಟೊಮೇಟೊ - ಸೆಪ್ಟೋರಿಯಾ ಎಲೆ ಚುಕ್ಕೆ";

                    case "tomato___spider_mites_two-spotted_spider_mite":
                        return "ಟೊಮೇಟೊ - ಜೇಡ ಹುಳು";

                    case "tomato___target_spot":
                        return "ಟೊಮೇಟೊ - ಟಾರ್ಗೆಟ್ ಚುಕ್ಕೆ";

                    case "tomato___tomato_yellow_leaf_curl_virus":
                        return "ಟೊಮೇಟೊ - ಹಳದಿ ಎಲೆ ಮಡಚುವ ವೈರಸ್";

                    case "tomato___tomato_mosaic_virus":
                        return "ಟೊಮೇಟೊ - ಟೊಮೇಟೊ ಮೊಸಾಯಿಕ್ ವೈರಸ್";

                    case "tomato___healthy":
                        return "ಟೊಮೇಟೊ - ಆರೋಗ್ಯಕರ";

                    default:
                        return disease;
                }

                //telugu

            case "te":

                switch (disease.toLowerCase(Locale.ROOT)) {

                    // ---------- Apple ----------
                    case "apple___apple_scab":
                        return "ఆపిల్ - ఆపిల్ స్కాబ్";

                    case "apple___black_rot":
                        return "ఆపిల్ - బ్లాక్ రాట్";

                    case "apple___cedar_apple_rust":
                        return "ఆపిల్ - సీడర్ ఆపిల్ రస్ట్";

                    case "apple___healthy":
                        return "ఆపిల్ - ఆరోగ్యకరం";

                    // ---------- Blueberry ----------
                    case "blueberry___healthy":
                        return "బ్లూబెర్రీ - ఆరోగ్యకరం";

                    // ---------- Cherry ----------
                    case "cherry___healthy":
                        return "చెర్రీ - ఆరోగ్యకరం";

                    case "cherry___powdery_mildew":
                        return "చెర్రీ - పౌడరీ మిల్డ్యూ";

                    // ---------- Corn ----------
                    case "corn_(maize)___healthy":
                        return "మొక్కజొన్న - ఆరోగ్యకరం";

                    case "corn_(maize)___common_rust_":
                        return "మొక్కజొన్న - కామన్ రస్ట్";

                    case "corn_(maize)___cercospora_leaf_spot_gray_leaf_spot":
                        return "మొక్కజొన్న - గ్రే లీఫ్ స్పాట్";

                    case "corn_(maize)___northern_leaf_blight":
                        return "మొక్కజొన్న - ఉత్తర ఆకు మచ్చ వ్యాధి";

                    // ---------- Grape ----------
                    case "grape___healthy":
                        return "ద్రాక్ష - ఆరోగ్యకరం";

                    case "grape___black_rot":
                        return "ద్రాక్ష - బ్లాక్ రాట్";

                    case "grape___esca_(black_measles)":
                        return "ద్రాక్ష - ఎస్కా (బ్లాక్ మీజిల్స్)";

                    case "grape___leaf_blight_(isariopsis_leaf_spot)":
                        return "ద్రాక్ష - ఆకు మచ్చ వ్యాధి";

                    default:
                        return disease;
                }

                //Tamil

            case "ta":

                switch (disease.toLowerCase(Locale.ROOT)) {

                    // ---------- Apple ----------
                    case "apple___apple_scab":
                        return "ஆப்பிள் - ஆப்பிள் ஸ்காப்";

                    case "apple___black_rot":
                        return "ஆப்பிள் - கருப்பு அழுகல்";

                    case "apple___cedar_apple_rust":
                        return "ஆப்பிள் - சீடர் ஆப்பிள் ரஸ்ட்";

                    case "apple___healthy":
                        return "ஆப்பிள் - ஆரோக்கியமானது";

                    // ---------- Blueberry ----------
                    case "blueberry___healthy":
                        return "புளூபெர்ரி - ஆரோக்கியமானது";

                    // ---------- Cherry ----------
                    case "cherry___healthy":
                        return "செர்ரி - ஆரோக்கியமானது";

                    case "cherry___powdery_mildew":
                        return "செர்ரி - பவுடரி மில்டியூ";

                    // ---------- Corn ----------
                    case "corn_(maize)___healthy":
                        return "மக்காச்சோளம் - ஆரோக்கியமானது";

                    case "corn_(maize)___common_rust_":
                        return "மக்காச்சோளம் - சாதாரண ரஸ்ட்";

                    case "corn_(maize)___cercospora_leaf_spot_gray_leaf_spot":
                        return "மக்காச்சோளம் - சாம்பல் இலைப் புள்ளி";

                    case "corn_(maize)___northern_leaf_blight":
                        return "மக்காச்சோளம் - வடக்கு இலை கருகல்";

                    // ---------- Grape ----------
                    case "grape___healthy":
                        return "திராட்சை - ஆரோக்கியமானது";

                    case "grape___black_rot":
                        return "திராட்சை - கருப்பு அழுகல்";

                    case "grape___esca_(black_measles)":
                        return "திராட்சை - எஸ்கா (பிளாக் மீஸில்ஸ்)";

                    case "grape___leaf_blight_(isariopsis_leaf_spot)":
                        return "திராட்சை - இலை கருகல் நோய்";

                    default:
                        return disease;
                }

                //Malyalam

                // ===================== MALAYALAM =====================
            case "ml":

                switch (disease.toLowerCase(Locale.ROOT)) {

                    // ---------- Apple ----------
                    case "apple___apple_scab":
                        return "ആപ്പിൾ - ആപ്പിൾ സ്കാബ്";

                    case "apple___black_rot":
                        return "ആപ്പിൾ - ബ്ലാക്ക് റോട്ട്";

                    case "apple___cedar_apple_rust":
                        return "ആപ്പിൾ - സീഡർ ആപ്പിൾ റസ്റ്റ്";

                    case "apple___healthy":
                        return "ആപ്പിൾ - ആരോഗ്യകരം";

                    // ---------- Blueberry ----------
                    case "blueberry___healthy":
                        return "ബ്ലൂബെറി - ആരോഗ്യകരം";

                    // ---------- Cherry ----------
                    case "cherry___healthy":
                        return "ചെറി - ആരോഗ്യകരം";

                    case "cherry___powdery_mildew":
                        return "ചെറി - പൗഡറി മിൽഡ്യൂ";

                    // ---------- Corn ----------
                    case "corn_(maize)___healthy":
                        return "ചോളം - ആരോഗ്യകരം";

                    case "corn_(maize)___common_rust_":
                        return "ചോളം - കോമൺ റസ്റ്റ്";

                    case "corn_(maize)___cercospora_leaf_spot_gray_leaf_spot":
                        return "ചോളം - ഗ്രേ ലീഫ് സ്പോട്ട്";

                    case "corn_(maize)___northern_leaf_blight":
                        return "ചോളം - നോർതേൺ ലീഫ് ബ്ലൈറ്റ്";

                    // ---------- Grape ----------
                    case "grape___healthy":
                        return "മുന്തിരി - ആരോഗ്യകരം";

                    case "grape___black_rot":
                        return "മുന്തിരി - ബ്ലാക്ക് റോട്ട്";

                    case "grape___esca_(black_measles)":
                        return "മുന്തിരി - എസ്കാ (ബ്ലാക്ക് മീസിൽസ്)";

                    case "grape___leaf_blight_(isariopsis_leaf_spot)":
                        return "മുന്തിരി - ഇല ബ്ലൈറ്റ് രോഗം";

                    default:
                        return disease;
                }

                // ===================== ENGLISH =====================
            default:
                return disease;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HistoryModel model = historyList.get(position);

        holder.tvPlant.setText(getLocalizedPlantName(holder.itemView, model.getPlant()));
        holder.tvDisease.setText(getTranslatedDisease(model.getDisease()));
        holder.tvConfidence.setText(
                String.format(Locale.getDefault(), "%.2f%%", model.getConfidence()));
        holder.tvDate.setText(model.getDate());
        holder.tvTime.setText(model.getTime());

        // Show image
        try {

            String imageUri = model.getImage();

            if (imageUri != null && !imageUri.trim().isEmpty()) {

                try {

                    Uri uri = Uri.parse(imageUri);

                    holder.imgHistory.setImageURI(null);

                    holder.imgHistory.post(() -> {

                        try {
                            holder.imgHistory.setImageURI(uri);

                        } catch (SecurityException e) {

                            holder.imgHistory.setImageResource(R.drawable.ic_launcher_foreground);

                        } catch (Exception e) {

                            holder.imgHistory.setImageResource(R.drawable.ic_launcher_foreground);
                        }

                    });

                } catch (Exception e) {

                    holder.imgHistory.setImageResource(R.drawable.ic_launcher_foreground);

                }

            } else {

                holder.imgHistory.setImageResource(R.drawable.ic_launcher_foreground);

            }

        } catch (Exception e) {

            e.printStackTrace();
            holder.imgHistory.setImageResource(R.drawable.ic_launcher_foreground);

        }

        // Info Button
        holder.btnInfo.setOnClickListener(v -> {

            View dialogView = LayoutInflater.from(v.getContext())
                    .inflate(R.layout.dialog_history_info, null);

            ImageView dialogImage = dialogView.findViewById(R.id.dialogImage);
            TextView dialogText = dialogView.findViewById(R.id.dialogText);

            try {

                if (model.getImage() != null && !model.getImage().isEmpty()) {

                    try {

                        dialogImage.setImageURI(Uri.parse(model.getImage()));

                    } catch (SecurityException e) {

                        dialogImage.setImageResource(R.drawable.ic_launcher_foreground);

                    } catch (Exception e) {

                        dialogImage.setImageResource(R.drawable.ic_launcher_foreground);

                    }

                } else {

                    dialogImage.setImageResource(R.drawable.ic_launcher_foreground);

                }

            } catch (Exception e) {

                dialogImage.setImageResource(R.drawable.ic_launcher_foreground);

            }

            String details =
                    v.getContext().getString(R.string.plant_label) + " " +
                            getLocalizedPlantName(v, model.getPlant()) +
                            "\n\n" +
                            v.getContext().getString(R.string.disease_label) + " " +
                            getTranslatedDisease(model.getDisease()) +
                            "\n\n" +
                            v.getContext().getString(R.string.confidence_label) + " " +
                            String.format(Locale.getDefault(), "%.2f%%", model.getConfidence()) +
                            "\n\n" +
                            v.getContext().getString(R.string.date_label) + " " + model.getDate() +
                            "\n" +
                            v.getContext().getString(R.string.time_label) + " " + model.getTime();

            dialogText.setText(details);

            new AlertDialog.Builder(v.getContext())
                    .setView(dialogView)
                    .setPositiveButton(R.string.close, null)
                    .show();

        });

        // Delete Button
        holder.btnDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(v.getContext())
                    .setTitle(R.string.delete_history)
                    .setMessage(R.string.delete_history_message)
                    .setPositiveButton(R.string.delete, (dialog, which) -> {

                        HistoryDatabaseHelper helper =
                                new HistoryDatabaseHelper(v.getContext());

                        helper.getWritableDatabase().delete(
                                HistoryDatabaseHelper.TABLE_HISTORY,
                                HistoryDatabaseHelper.COL_ID + "=?",
                                new String[]{
                                        String.valueOf(model.getId())
                                }
                        );

                        historyList.remove(position);

                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, historyList.size());

                    })
                    .setNegativeButton(R.string.cancel, null)
                    .show();

        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        Button btnDelete;

        ImageView imgHistory;
        TextView tvPlant;
        TextView tvDisease;
        TextView tvConfidence;
        TextView tvDate;
        TextView tvTime;
        Button btnInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgHistory = itemView.findViewById(R.id.imgHistory);
            tvPlant = itemView.findViewById(R.id.txtPlant);
            tvDisease = itemView.findViewById(R.id.txtDisease);
            tvConfidence = itemView.findViewById(R.id.txtConfidence);
            tvDate = itemView.findViewById(R.id.txtDate);
            tvTime = itemView.findViewById(R.id.txtTime);
            btnInfo = itemView.findViewById(R.id.btnInfo);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}