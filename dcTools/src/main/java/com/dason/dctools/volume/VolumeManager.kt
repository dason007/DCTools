package com.dason.dctools.volume

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import java.lang.ref.WeakReference
import kotlin.math.max
import kotlin.math.min

class VolumeManager(ct: Context) : OnAudioFocusChangeListener {
    private val maxVolume = 0
    private var context: Context = ct.applicationContext
    private var audioManager: AudioManager =
        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var listener: WeakReference<IVolumeListener>? = null

    init {
        this.registerVolumeListener()
        this.registerAudioFocusListener()
    }

    fun release() {
        this.listener = null
        this.unregisterVolumeListener()
        this.unregisterAudioFocusListener()
    }

    fun changeListener(listener: IVolumeListener) {
        this.listener = WeakReference(listener)
    }

    fun getVolume(): Int {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    }

    fun getMaxVolume(): Int {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    }

    fun setVolume(volume: Int) {
        val vl = min(max(0, volume), maxVolume)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vl, 0)
    }

    fun adjustVolume(volume: Int) {
        val current = getVolume()
        var adjust = current + volume
        adjust = min(max(0, adjust), maxVolume)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, adjust, 0)
    }

    private fun registerVolumeListener() {
        val filter = IntentFilter("android.media.VOLUME_CHANGED_ACTION")
        context.registerReceiver(this.volumeBroadcastReceiver, filter)
    }

    private fun unregisterVolumeListener() {
        context.unregisterReceiver(this.volumeBroadcastReceiver)
    }

    private fun registerAudioFocusListener() {
        audioManager.requestAudioFocus(
            this,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        )
    }

    private fun unregisterAudioFocusListener() {
        audioManager.abandonAudioFocus(this)
    }

    private fun fireVolumeChanged() {
        listener?.get()?.onVolumeChanged(getVolume())
    }

    private fun fireAudioFocusLose() {
        listener?.get()?.onAudioFocusLose()
    }

    private fun fireAudioFocusGranted() {
        listener?.get()?.onAudioFocusGranted()
    }


    override fun onAudioFocusChange(focusChange: Int) {
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> fireAudioFocusGranted()
            AudioManager.AUDIOFOCUS_LOSS, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> fireAudioFocusLose()
        }
    }

    private val volumeBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if ("android.media.VOLUME_CHANGED_ACTION" == action) {
                if (AudioManager.STREAM_MUSIC == intent.getIntExtra(
                        "android.media.EXTRA_VOLUME_STREAM_TYPE",
                        AudioManager.STREAM_MUSIC
                    )
                ) {
                    fireVolumeChanged()
                }
            }
        }
    }
}