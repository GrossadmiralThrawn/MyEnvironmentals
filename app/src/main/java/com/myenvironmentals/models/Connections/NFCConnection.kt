package com.myenvironmentals.models.Connections





import android.content.Context
import android.nfc.NfcAdapter




class NFCConnection (private val context: Context): IConnection {
    private var nfcAdapter: NfcAdapter? = null



    init {
        nfcAdapter = NfcAdapter.getDefaultAdapter(context)
    }





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