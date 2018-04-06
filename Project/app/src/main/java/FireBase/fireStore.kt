package FireBase
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.firebase.firestore.FirebaseFirestore


object fireStore {
    val fireStoreHandler = FirebaseFirestore.getInstance()
     fun isNetworkAvailable(context: Context): Boolean {
         var cm:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         var activeNetwork:NetworkInfo? = cm.getActiveNetworkInfo();
         return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}