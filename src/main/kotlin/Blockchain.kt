import org.apache.commons.codec.digest.DigestUtils

class Blockchain(
    private val difficulty: Int
) {

    private val chain = mutableListOf<Block>()

    fun addBlocks(amount: Int) {
        addGenesisBlock()

        repeat(amount - 1) {
            val previousHash = chain.last().hash
            val data = "Block ${chain.size + 1}"
            val newBlock = Block(previousHash, data)

            mine(newBlock)
        }
    }

    private fun addGenesisBlock() {
        val genesisBlock = Block("0", "Block 1")
        genesisBlock.calculateHash()
        chain.add(genesisBlock)
        println("Genesis block added")
        println(genesisBlock)
    }

    private fun mine(block: Block) {
        println("Mining...")

        val validPrefix = "0".repeat(difficulty)

        do {
            val isValidHash = block.calculateHash().startsWith(validPrefix)
            block.nonce++
        } while (!isValidHash)

        println(block)
    }

    fun checkValidity() {
        val validatableBlocksRange = 1 until chain.size

        val isBlockchainValid = validatableBlocksRange.all { index ->
            val previousHash = chain[index - 1].hash
            val data = chain[index].data
            val timestampEpoch = chain[index].timestampEpoch
            val nonce = chain[index].nonce

            chain[index].hash == DigestUtils
                .sha256Hex("$previousHash$data$timestampEpoch$nonce")
        }

        println("Blockchain is valid: $isBlockchainValid")
    }

}