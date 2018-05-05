package Model.postData.Feedback

class Feedback(var userImage: String,
               var from: String, var message: String,
               var timestamp: String, var deleteIt: Boolean = false, var rate: String = "1", var firstName: String = "", var midName: String = "", var lastName: String = "") {
    var feedbackId: String = ""

}
