package Model

import java.security.acl.Owner


class Workshop(location: String, workshopName: String,
                             workshopPhoneNum: String,profession: String,
                            OwnerName:String)
{
    var location = ""
    var workshopName = ""
    var workshopPhoneNum = ""
    var profession = ""
    var OwnerName = ""

    init {
        this.location = location
        this.workshopName = workshopName
        this.workshopPhoneNum = workshopPhoneNum
        this.OwnerName = OwnerName
        this.profession = profession
    }
}