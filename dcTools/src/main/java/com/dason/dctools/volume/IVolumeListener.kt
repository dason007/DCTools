package com.dason.dctools.volume

interface IVolumeListener {
    fun onAudioFocusLose()

    fun onAudioFocusGranted()

    fun onVolumeChanged(volume: Int)
}