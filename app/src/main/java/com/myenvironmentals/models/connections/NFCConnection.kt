package com.myenvironmentals.models.connections





import android.nfc.NfcAdapter




class NFCConnection (): IConnection {
    private var nfcAdapter: NfcAdapter? = null





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