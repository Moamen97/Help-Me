package Model

import java.security.acl.Owner


class Workshop(location: String, workshopName: String,
               workshopPhoneNum: String, profession: String,
               OwnerName: String, workshopid: String, OwnerFullName: String) {
    var location = ""
    var workshopName = ""
    var workshopPhoneNum = ""
    var profession = ""
    var OwnerName = ""
    var workshopid = ""
    var OwnerFullName = ""

    init {
        this.location = location
        this.workshopName = workshopName
        this.workshopPhoneNum = workshopPhoneNum
        this.OwnerName = OwnerName
        this.profession = profession
        this.workshopid = workshopid
        this.OwnerFullName = OwnerFullName
    }
}