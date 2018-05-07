package Music

import android.content.Context
import android.media.MediaPlayer
import android.support.graphics.drawable.animated.R

class MusicPlayer
{
    private var mediaplayer:MediaPlayer?=null
    var isplaying = false
    private constructor()
    {
        mediaplayer = MediaPlayer()
    }
    companion object {
        private var instance: MusicPlayer? = null
        fun getInstance(x:Int = 0): MusicPlayer {
            if (instance == null)
                instance = MusicPlayer()
            return instance!!
        }
    } //Singleton
    fun play(context: Context)
    {
        try {
            mediaplayer!!.stop()
            mediaplayer = MediaPlayer.create(context,com.helpme.R.raw.songa)
            mediaplayer!!.start()
        }catch (e:Exception){
            mediaplayer = MediaPlayer.create(context,com.helpme.R.raw.songa)
            mediaplayer!!.start()
        }

    }
    fun stop()
    {
        try {
            mediaplayer!!.stop()
        }catch (e:Exception){}
    }
}