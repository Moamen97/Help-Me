package Model.postData.Feedback

interface FeedbackAdapterListener {
    fun onIconClicked(position: Int)
    fun onRateFirstStarClicked(position: Int)
    fun onRateSecondStarClicked(position: Int)
    fun onRateThirdStarClicked(position: Int)
    fun onRateFourthStarClicked(position: Int)
    fun onRateFifthStarClicked(position: Int)
    fun onMessageRowClicked(position: Int)
    fun onRowLongClicked(position: Int)
}
