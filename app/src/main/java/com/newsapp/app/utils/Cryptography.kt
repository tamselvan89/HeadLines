package com.newsapp.app.utils

import android.util.Base64
import com.newsapp.app.BuildConfig

import java.security.InvalidKeyException
import java.security.Key
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.SecretKeySpec

object Cryptography {

    private val ALGORITHM_MD5 = "MD5"
    private val ALGORITHM_SHA256 = "HmacSHA256"
    private val TRANSFORMATION = "AES"
    private val BASE64_FLAGS = Base64.NO_WRAP
    private var key: Key? = null
    private var encryptCipher: Cipher? = null
    private var decryptCipher: Cipher? = null

    fun md5(input: String?): String? {
        return if (input == null || input.length == 0) {
            null
        } else Base64.encodeToString(md5(input.toByteArray()), Base64.NO_WRAP)

    }

    fun md5(input: ByteArray): ByteArray? {
        val digest: MessageDigest
        try {
            digest = MessageDigest.getInstance(ALGORITHM_MD5)
        } catch (e: NoSuchAlgorithmException) {
            return null
        }

        return digest.digest(input)
    }

    fun encrypt(input: String?): String? {
        if (input == null) {
            return null
        }

        try {
            val encodedBytes = getEncryptCipher()?.doFinal(input.toByteArray())
            return Base64.encodeToString(encodedBytes, BASE64_FLAGS)

        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                throw AssertionError("Cryptography.encrypt failed for: $input")
            }
            e.printStackTrace()
            return null
        }

    }

    fun decrypt(input: String?): String? {
        if (input == null) {
            return null
        }

        try {
            val decodedBytes = getDecryptCipher()?.doFinal(Base64.decode(input, BASE64_FLAGS))
            return decodedBytes?.let { String(it) }

        } catch (e: Exception) {
            if (BuildConfig.DEBUG) {
                throw AssertionError("Cryptography.decrypt failed for: $input")
            }
            e.printStackTrace()
            return null
        }

    }

    @Synchronized
    private fun getKey(): Key {
        if (key == null) {
            key = SecretKeySpec(a(), ALGORITHM_SHA256)
        }
        return key as Key
    }

    @Synchronized
    private fun getEncryptCipher(): Cipher? {
        if (encryptCipher == null) {
            encryptCipher = createCipher(Cipher.ENCRYPT_MODE)
        }
        return encryptCipher
    }

    @Synchronized
    private fun getDecryptCipher(): Cipher? {
        if (decryptCipher == null) {
            decryptCipher = createCipher(Cipher.DECRYPT_MODE)
        }
        return decryptCipher
    }

    private fun createCipher(operationMode: Int): Cipher? {
        try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(operationMode, getKey())
            return cipher
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
            return null
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
            return null
        }

    }

    private fun a(): ByteArray? {
        return md5(Base64.decode("IU1ZaypiTiVYc3AtPXMxNWNMYGUmVUF8YUAtUSMuKVI=", BASE64_FLAGS))
    }
}
