package com.myenvironmentals.models.connections





import android.nfc.NfcAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class NFCConnection (): IConnection {
    private var nfcAdapter: NfcAdapter? = null
    private val nfcOn = MutableStateFlow(false)
    override val connectionStatus: StateFlow<Boolean> = nfcOn




    override fun connect(): Boolean {
        return when {
            nfcAdapter == null ->false
            !nfcAdapter!!.isEnabled -> false
            else -> true
        }
    }





    override fun send(data: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun receive(): ByteArray {
        TODO("Not yet implemented")
    }

    override fun disconnect() {
        TODO("Not yet implemented")
    }
}