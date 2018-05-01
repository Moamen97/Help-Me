package Utility
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest


object Utility {
    val fireStoreHandler = FirebaseFirestore.getInstance()
     fun isNetworkAvailable(context: Context): Boolean {
         var cm:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         var activeNetwork:NetworkInfo? = cm.getActiveNetworkInfo();
         return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
     fun hashString(input: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
                .getInstance("SHA_256")
                .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }
        return result.toString()
    }
}