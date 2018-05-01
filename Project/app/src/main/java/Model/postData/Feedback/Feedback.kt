package Model.postData.Feedback

class Feedback(var id: Int, var picture: String,
               var from: String, var message: String,
               var timestamp: String,  var color: Int, var deleteIt: Boolean = false, var rate: Int = 1)