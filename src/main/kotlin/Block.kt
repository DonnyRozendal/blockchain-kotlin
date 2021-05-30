import org.apache.commons.codec.digest.DigestUtils
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

data class Block(
    val previousHash: String,
    val data: String
) {

    val timestampEpoch = Instant.now().toEpochMilli()
    private val readableTimestamp = ZonedDateTime.ofInstant(
        Instant.ofEpochMilli(timestampEpoch),
        ZoneId.systemDefault()
    )

    var nonce = 0
    var hash = ""

    fun calculateHash(): String {
        return DigestUtils
            .sha256Hex("$previousHash$data$timestampEpoch$nonce")
            .also { hash = it }
    }

    override fun toString(): String {
        val divider = "-".repeat(30)
        return "$divider\n" +
                "Previous hash: $previousHash\n" +
                "Data: $data\n" +
                "Timestamp: $readableTimestamp\n" +
                "Nonce: $nonce\n" +
                "Hash: $hash\n" +
                divider
    }

}