import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.countryapi.CountryData
import com.example.countryapi.R

class CountryAdapter(private val countryList: List<CountryData>) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flagImage: ImageView
        val nameTextView : TextView
        val regionTextView : TextView

        init {
            // Find our RecyclerView item's views
            flagImage = view.findViewById(R.id.flagImage)
            nameTextView = view.findViewById(R.id.nameTextView)
            regionTextView = view.findViewById(R.id.regionTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = countryList[position]

        // Load the image using Glide
        Glide.with(holder.itemView)
            .load(country.flagURL)
            .centerCrop()
            .into(holder.flagImage)

        // Set text values
        holder.nameTextView.text = country.name + " , " + country.code
        holder.regionTextView.text = country.region

        // `holder` can used to reference any View within the RecyclerView item's layout file
        holder.flagImage.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Country at position $position clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = countryList.size

}
