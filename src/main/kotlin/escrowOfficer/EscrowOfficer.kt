package escrowOfficer

import framework.Worker

data class EscrowOfficer(
    override val id: Long,
    override var name: String,
    override var lastName: String,
    override var address: String,
    override var phoneNumber: String,
    override var email: String,
    override var company: String
) : Worker