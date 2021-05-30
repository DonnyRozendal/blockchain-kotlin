fun main() {
    Blockchain(5).apply {
        addBlocks(3)
        checkValidity()
    }
}