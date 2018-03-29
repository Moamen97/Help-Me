package UtilityClasses

import android.app.Application
import android.content.Context
import android.widget.Toast
/**
 * Created by shehab on 3/29/18.
 */
class debugger : Application() {
    override fun onCreate() {
        super.onCreate();debugger.appContext = applicationContext
    }
    companion object {
        var appContext: Context? = null
            private set

        fun showMess(mess: String) {
            Toast.makeText(debugger.appContext, mess, Toast.LENGTH_SHORT)
                    .show()
        }
    }
}
