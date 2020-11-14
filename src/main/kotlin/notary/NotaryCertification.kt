package notary

data class NotaryCertification(
    var realStateAgent: Boolean = false,
    var signingAgent: Boolean = false,
    var attorney: Boolean = false,
    var loanOfficer: Boolean = false
)