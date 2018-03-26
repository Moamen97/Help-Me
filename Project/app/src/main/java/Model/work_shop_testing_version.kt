package Model

/**
 * Created by Lenovo on 3/27/2018.
 */
class WorkshopTestingVersion(location: String, workshopName: String, workshopPhoneNum: String, workshopRate: Int?, workingHours: String) {
    var location = ""
    var workshopName = ""
    var workshopPhoneNum = ""
    var workshopRate: Int? = 0
    var workingHours = ""

    init {
        this.location = location
        this.workshopName = workshopName
        this.workshopPhoneNum = workshopPhoneNum
        this.workshopRate = workshopRate
        this.workingHours = workingHours
    }
}