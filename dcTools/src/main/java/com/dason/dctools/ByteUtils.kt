package com.dason.dctools

import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.experimental.inv

object ByteUtils {

    /**
     * Int转字节数组 大端在前
     */
    @JvmStatic
    fun int2Bytes(value: Int): ByteArray {
        val bytes = ByteArray(4)
        bytes[0] = (0xff and (value shr 24)).toByte()
        bytes[1] = (0xff and (value shr 16)).toByte()
        bytes[2] = (0xff and (value shr 8)).toByte()
        bytes[3] = (0xff and value).toByte()
        return bytes
    }

    /**
     * Int转字节数组 小端在前
     */
    @JvmStatic
    fun int2BytesLE(value: Int): ByteArray {
        val bytes = ByteArray(4)
        bytes[3] = (0xff and (value shr 24)).toByte()
        bytes[2] = (0xff and (value shr 16)).toByte()
        bytes[1] = (0xff and (value shr 8)).toByte()
        bytes[0] = (0xff and value).toByte()
        return bytes
    }

    @JvmStatic
    fun bytes2Int(bytes: ByteArray): Int {
        return bytes2Int(bytes, 0)
    }

    @JvmStatic
    fun bytes2Int(bytes: ByteArray, offset: Int): Int {
        return bytes2Int(bytes, offset, ByteOrder.BIG_ENDIAN)
    }

    @JvmStatic
    fun bytes2Int(bytes: ByteArray, order: ByteOrder): Int {
        return bytes2Int(bytes, 0, order)
    }

    @JvmStatic
    fun bytes2Int(bytes: ByteArray, offset: Int, order: ByteOrder): Int {
        require(!(offset < 0 || offset > bytes.size - 1)) { "The offset index out of bounds" }
        require(bytes.size - offset >= 4) { "The bytes (length - offset) < int bytes(4)" }
        val buffer = ByteBuffer.wrap(bytes, offset, 4)
        if (order == ByteOrder.LITTLE_ENDIAN) {
            buffer.order(ByteOrder.LITTLE_ENDIAN)
        }
        return buffer.getInt()
    }


    @JvmStatic
    fun short2Bytes(value: Short): ByteArray {
        val bytes = ByteArray(2)
        bytes[0] = (0xff and (value.toInt() shr 8)).toByte()
        bytes[1] = (0xff and value.toInt()).toByte()
        return bytes
    }

    @JvmStatic
    fun short2BytesLE(value: Short): ByteArray {
        val bytes = ByteArray(2)
        bytes[1] = (0xff and (value.toInt() shr 8)).toByte()
        bytes[0] = (0xff and value.toInt()).toByte()
        return bytes
    }

    @JvmStatic
    fun bytes2Short(bytes: ByteArray): Short {
        return bytes2Short(bytes, 0)
    }

    @JvmStatic
    fun bytes2Short(bytes: ByteArray, offset: Int): Short {
        return bytes2Short(bytes, offset, ByteOrder.BIG_ENDIAN)
    }

    @JvmStatic
    fun bytes2Short(bytes: ByteArray, order: ByteOrder): Short {
        return bytes2Short(bytes, 0, order)
    }

    @JvmStatic
    fun bytes2Short(bytes: ByteArray, offset: Int, order: ByteOrder): Short {
        require(!(offset < 0 || offset > bytes.size - 1)) { "The offset index out of bounds" }
        require(bytes.size - offset >= 2) { "The bytes (legth - offset) < short bytes(2)" }
        val buffer = ByteBuffer.wrap(bytes, offset, 2)
        if (order == ByteOrder.LITTLE_ENDIAN) {
            buffer.order(ByteOrder.LITTLE_ENDIAN)
        }
        return buffer.getShort()
    }

    @JvmStatic
    fun long2Bytes(value: Long): ByteArray {
        val bytes = ByteArray(8)
        bytes[0] = (0xffL and (value shr 56)).toByte()
        bytes[1] = (0xffL and (value shr 48)).toByte()
        bytes[2] = (0xffL and (value shr 40)).toByte()
        bytes[3] = (0xffL and (value shr 32)).toByte()
        bytes[4] = (0xffL and (value shr 24)).toByte()
        bytes[5] = (0xffL and (value shr 16)).toByte()
        bytes[6] = (0xffL and (value shr 8)).toByte()
        bytes[7] = (0xffL and value).toByte()
        return bytes
    }

    @JvmStatic
    fun long2BytesLE(value: Long): ByteArray {
        val bytes = ByteArray(8)
        bytes[7] = (0xffL and (value shr 56)).toByte()
        bytes[6] = (0xffL and (value shr 48)).toByte()
        bytes[5] = (0xffL and (value shr 40)).toByte()
        bytes[4] = (0xffL and (value shr 32)).toByte()
        bytes[3] = (0xffL and (value shr 24)).toByte()
        bytes[2] = (0xffL and (value shr 16)).toByte()
        bytes[1] = (0xffL and (value shr 8)).toByte()
        bytes[0] = (0xffL and value).toByte()
        return bytes
    }

    @JvmStatic
    fun bytes2Long(bytes: ByteArray): Long {
        return bytes2Long(bytes, 0)
    }

    @JvmStatic
    fun bytes2Long(bytes: ByteArray, offset: Int): Long {
        return bytes2Long(bytes, offset, ByteOrder.BIG_ENDIAN)
    }

    @JvmStatic
    fun bytes2Long(bytes: ByteArray, order: ByteOrder): Long {
        return bytes2Long(bytes, 0, order)
    }

    @JvmStatic
    fun bytes2Long(bytes: ByteArray, offset: Int, order: ByteOrder): Long {
        require(!(offset < 0 || offset > bytes.size - 1)) { "The offset index out of bounds" }
        require(bytes.size - offset >= 8) { "The bytes (length - offset) < long bytes(8)" }
        val buffer = ByteBuffer.wrap(bytes, offset, 8)
        if (order == ByteOrder.LITTLE_ENDIAN) {
            buffer.order(ByteOrder.LITTLE_ENDIAN)
        }
        return buffer.getLong()
    }

    @JvmStatic
    fun float2Bytes(value: Float): ByteArray {
        return int2Bytes(java.lang.Float.floatToIntBits(value))
    }

    @JvmStatic
    fun float2BytesLE(value: Float): ByteArray {
        return int2BytesLE(java.lang.Float.floatToIntBits(value))
    }

    @JvmStatic
    fun bytes2Float(bytes: ByteArray): Float {
        return bytes2Float(bytes, 0)
    }

    @JvmStatic
    fun bytes2Float(bytes: ByteArray, offset: Int): Float {
        return bytes2Float(bytes, offset, ByteOrder.BIG_ENDIAN)
    }

    @JvmStatic
    fun bytes2Float(bytes: ByteArray, order: ByteOrder): Float {
        return bytes2Float(bytes, 0, order)
    }

    @JvmStatic
    fun bytes2Float(bytes: ByteArray, offset: Int, order: ByteOrder): Float {
        val temp = bytes2Int(bytes, offset, order)
        return java.lang.Float.intBitsToFloat(temp)
    }

    @JvmStatic
    fun double2Bytes(value: Double): ByteArray {
        return long2Bytes(java.lang.Double.doubleToLongBits(value))
    }

    @JvmStatic
    fun double2BytesLE(value: Double): ByteArray {
        return long2BytesLE(java.lang.Double.doubleToLongBits(value))
    }

    @JvmStatic
    fun bytes2Double(bytes: ByteArray): Double {
        return bytes2Double(bytes, 0)
    }

    @JvmStatic
    fun bytes2Double(bytes: ByteArray, offset: Int): Double {
        return bytes2Double(bytes, offset, ByteOrder.BIG_ENDIAN)
    }

    @JvmStatic
    fun bytes2Double(bytes: ByteArray, order: ByteOrder): Double {
        return bytes2Double(bytes, 0, order)
    }

    @JvmStatic
    fun bytes2Double(bytes: ByteArray, offset: Int, order: ByteOrder): Double {
        val temp = bytes2Long(bytes, offset, order)
        return java.lang.Double.longBitsToDouble(temp)
    }

    @JvmStatic
    fun bytes2HexString(data: ByteArray): String {
        return bytes2HexString(data, false)
    }

    @JvmStatic
    fun bytes2HexString(data: ByteArray, hexFlag: Boolean): String {
        return bytes2HexString(data, hexFlag, null)
    }

    @JvmStatic
    fun bytes2HexString(data: ByteArray, hexFlag: Boolean, separator: String?): String {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        return bytes2HexString(data, 0, data.size, hexFlag, separator)
    }

    @JvmStatic
    fun bytes2HexString(data: ByteArray, offset: Int, len: Int): String {
        return bytes2HexString(data, offset, len, false)
    }

    @JvmStatic
    fun bytes2HexString(data: ByteArray, offset: Int, len: Int, hexFlag: Boolean): String {
        return bytes2HexString(data, offset, len, hexFlag, null)
    }

    @JvmStatic
    fun bytes2HexString(
        data: ByteArray,
        offset: Int,
        len: Int,
        hexFlag: Boolean,
        separator: String?
    ): String {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        require(!(offset < 0 || offset > data.size - 1)) { "The offset index out of bounds" }
        require(!(len < 0 || offset + len > data.size)) { "The len can not be < 0 or (offset + len) index out of bounds" }
        var format = "%02X"
        if (hexFlag) {
            format = "0x%02X"
        }
        val buffer = StringBuffer()
        for (i in offset until offset + len) {
            buffer.append(String.format(format!!, data[i]))
            if (EmptyUtils.isEmpty(separator)) {
                continue
            }
            if (i != (offset + len - 1)) {
                buffer.append(separator)
            }
        }
        return buffer.toString()
    }

    @JvmStatic
    fun computeXORCode(data: ByteArray): Byte {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        return computeXORCode(data, 0, data.size)
    }

    @JvmStatic
    fun computeXORCode(data: ByteArray, offset: Int, len: Int): Byte {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        require(!(offset < 0 || offset > data.size - 1)) { "The offset index out of bounds" }
        require(!(len < 0 || offset + len > data.size)) { "The len can not be < 0 or (offset + len) index out of bounds" }
        var temp = data[offset]
        for (i in offset + 1 until offset + len) {
            temp = (temp.toInt() xor data[i].toInt()).toByte()
        }
        return temp
    }

    @JvmStatic
    fun computeXORInverse(data: ByteArray): Byte {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        return computeXORInverse(data, 0, data.size)
    }

    @JvmStatic
    fun computeXORInverse(data: ByteArray, offset: Int, len: Int): Byte {
        val xor = computeXORCode(data, offset, len)
        return xor.inv()
    }

    @JvmStatic
    fun computeL8SumCode(data: ByteArray): Byte {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        return computeL8SumCode(data, 0, data.size)
    }

    @JvmStatic
    fun computeL8SumCode(data: ByteArray, offset: Int, len: Int): Byte {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        require(!(offset < 0 || offset > data.size - 1)) { "The offset index out of bounds" }
        require(!(len < 0 || offset + len > data.size)) { "The len can not be < 0 or (offset + len) index out of bounds" }
        var sum = 0
        for (pos in offset until offset + len) {
            sum += data[pos].toInt()
        }
        return sum.toByte()
    }

    @JvmStatic
    fun computeCRC16Code(data: ByteArray): ByteArray {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        return computeCRC16Code(data, 0xA001)
    }

    @JvmStatic
    fun computeCRC16Code(data: ByteArray, seed: Int): ByteArray {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        return computeCRC16Code(data, seed, 0, data.size)
    }

    @JvmStatic
    fun computeCRC16Code(data: ByteArray, offset: Int, len: Int): ByteArray {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        require(!(offset < 0 || offset > data.size - 1)) { "The offset index out of bounds" }
        require(!(len < 0 || offset + len > data.size)) { "The len can not be < 0 or (offset + len) index out of bounds" }
        return computeCRC16Code(data, 0xA001, 0, data.size)
    }

    @JvmStatic
    fun computeCRC16Code(data: ByteArray, seed: Int, offset: Int, len: Int): ByteArray {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        require(!(offset < 0 || offset > data.size - 1)) { "The offset index out of bounds" }
        require(!(len < 0 || offset + len > data.size)) { "The len can not be < 0 or (offset + len) index out of bounds" }
        var crc = 0xFFFF
        for (pos in offset until offset + len) {
            crc = if (data[pos] < 0) {
                crc xor data[pos].toInt() + 256 // XOR byte into least sig. byte of
                // crc
            } else {
                crc xor data[pos].toInt() // XOR byte into least sig. byte of crc
            }
            for (i in 8 downTo 1) { // Loop over each bit
                if ((crc and 0x0001) != 0) { // If the LSB is set
                    crc = crc shr 1 // Shift right and XOR 0xA001
                    crc = crc xor seed
                } else {
                    // Else LSB is not set
                    crc = crc shr 1 // Just shift right
                }
            }
        }
        return short2Bytes(crc.toShort())
    }

    @JvmStatic
    fun computeCRC16CodeLE(data: ByteArray): ByteArray {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        return computeCRC16CodeLE(data, 0xA001)
    }

    @JvmStatic
    fun computeCRC16CodeLE(data: ByteArray, seed: Int): ByteArray {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        return computeCRC16CodeLE(data, seed, 0, data.size)
    }

    @JvmStatic
    fun computeCRC16CodeLE(data: ByteArray, offset: Int, len: Int): ByteArray {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        require(!(offset < 0 || offset > data.size - 1)) { "The offset index out of bounds" }
        require(!(len < 0 || offset + len > data.size)) { "The len can not be < 0 or (offset + len) index out of bounds" }
        return computeCRC16CodeLE(data, 0xA001, 0, data.size)
    }

    @JvmStatic
    fun computeCRC16CodeLE(data: ByteArray, seed: Int, offset: Int, len: Int): ByteArray {
        require(!EmptyUtils.isEmpty(data)) { "The data can not be blank" }
        require(!(offset < 0 || offset > data.size - 1)) { "The offset index out of bounds" }
        require(!(len < 0 || offset + len > data.size)) { "The len can not be < 0 or (offset + len) index out of bounds" }
        var crc = 0xFFFF
        for (pos in offset until offset + len) {
            crc = if (data[pos] < 0) {
                crc xor data[pos].toInt() + 256 // XOR byte into least sig. byte of
                // crc
            } else {
                crc xor data[pos].toInt() // XOR byte into least sig. byte of crc
            }
            for (i in 8 downTo 1) { // Loop over each bit
                if ((crc and 0x0001) != 0) { // If the LSB is set
                    crc = crc shr 1 // Shift right and XOR 0xA001
                    crc = crc xor seed
                } else {
                    // Else LSB is not set
                    crc = crc shr 1 // Just shift right
                }
            }
        }
        return short2BytesLE(crc.toShort())
    }
}