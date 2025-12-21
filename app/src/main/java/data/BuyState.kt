package data

sealed interface BuyState {
    data object Idle : BuyState
    data object Loading : BuyState
    data class Success(val answer: Answer) : BuyState
    data class Error(val message: String) : BuyState
}