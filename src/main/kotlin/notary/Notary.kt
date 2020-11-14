package notary

import framework.Worker
import kotlin.js.Date

data class Notary(
    override val id: Long,
    override var name: String,
    override var lastName: String,
    override var address: String,
    override var phoneNumber: String,
    override var email: String,
    override var company: String,
    var certification: NotaryCertification,
    var licenseExpirationDate: Date,
    var errorOmissionInsuranceAmount: String,
    var experienceYears: Short,
    var score: Short
) : Worker