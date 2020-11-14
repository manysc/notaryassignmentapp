package notary

import framework.DateUtils
import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLTextAreaElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import styled.*
import styled.styledLabel
import kotlin.js.Date

external interface NotaryFormProps : RProps {
    var pageMessage: String
    var pageErrorMessage: String
    var notary: Notary

    var onChangedName: (String) -> Any
    var onChangedLastName: (String) -> Any
    var onChangedAddress: (String) -> Any
    var onChangedPhoneNumber: (String) -> Any
    var onChangedEmail: (String) -> Any
    var onChangedCompany: (String) -> Any

    var onChangedRealEstateAgentCert: () -> Any
    var onChangedSigningAgentCert: () -> Any
    var onChangedAttorneyCert: () -> Any
    var onChangedLoanOfficerCert: () -> Any

    var onChangedLicenseExpirationDate: (Date) -> Any
    var onChangedErrorOmissionInsuranceAmount: (String) -> Any
    var onChangedExperienceYears: (Short) -> Any
    var onChangedScore: (Short) -> Any
}

class NotaryForm : RComponent<NotaryFormProps, RState>() {

    override fun RBuilder.render() {

        form(classes = "formCenter") {
            br {  }
            br {  }

            styledLabel {
                css {
                    color = Color.limeGreen
                }

                attrs {
                    +props.pageMessage
                }
            }

            styledLabel {
                css {
                    color = Color.red.darken(15)
                }

                attrs {
                    +props.pageErrorMessage
                }
            }

            if (props.pageMessage.isNotEmpty() || props.pageErrorMessage.isNotEmpty()) {
                br {}
            }

            br {}

            styledInput(type = InputType.text, name = "notaryName") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "First Name"
                    value = props.notary.name
                    required = true
                    onChangeFunction = ::onNameChanged
                }
            }

            br {}

            styledInput(type = InputType.text, name = "notaryLastName") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Last Name"
                    value = props.notary.lastName
                    required = true
                    onChangeFunction = ::onLastNameChanged
                }
            }

            styledTextArea(rows = "1") {
                css {
                    color = Color.white
                }

                attrs {
                    if (props.notary.address.isNotEmpty()) {
                        rows = "2"
                    }

                    name = "notaryAddress"
                    placeholder = "Address"
                    value = props.notary.address
                    required = true
                    onChangeFunction = ::onAddressChanged
                }
            }

            styledInput(type = InputType.text, name = "notaryPhone") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Phone Number"
                    value = props.notary.phoneNumber
                    required = true
                    onChangeFunction = ::onPhoneNumberChanged
                }
            }

            styledInput(type = InputType.text, name = "notaryEmail") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Email"
                    value = props.notary.email
                    required = true
                    onChangeFunction = ::onEmailChanged
                }
            }

            styledInput(type = InputType.text, name = "notaryCompany") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Company"
                    value = props.notary.company
                    required = true
                    onChangeFunction = ::onCompanyChanged
                }
            }

            // Notary Certifications
            styledH3 {
                css {
                    fontWeight = FontWeight.bold
                }

                hr { }
                +"Certifications:"
            }

            styledLabel {
                css {
                    background = "transparent"
                }

                if (props.notary.certification.realStateAgent) {
                    css {
                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                    }
                    +"\uD83D\uDDF9  "
                } else {
                    +"☒ "
                }

                +"Real State Agent"

                attrs {
                    onClickFunction = {
                        props.onChangedRealEstateAgentCert()
                    }
                }
            }

            br { }

            styledLabel {
                css {
                    background = "transparent"
                }

                if (props.notary.certification.signingAgent) {
                    css {
                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                    }
                    +"\uD83D\uDDF9  "
                } else {
                    +"☒ "
                }

                +"Signing Agent"

                attrs {
                    onClickFunction = {
                        props.onChangedSigningAgentCert()
                    }
                }
            }

            br { }

            styledLabel {
                css {
                    background = "transparent"
                }

                if (props.notary.certification.attorney) {
                    css {
                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                    }
                    +"\uD83D\uDDF9  "
                } else {
                    +"☒ "
                }

                +"Attorney Agent"

                attrs {
                    onClickFunction = {
                        props.onChangedAttorneyCert()
                    }
                }
            }

            br { }

            styledLabel {
                css {
                    background = "transparent"
                }

                if (props.notary.certification.loanOfficer) {
                    css {
                        background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                    }
                    +"\uD83D\uDDF9  "
                } else {
                    +"☒ "
                }

                +"Loan Officer Agent"

                attrs {
                    onClickFunction = {
                        props.onChangedLoanOfficerCert()
                    }
                }
            }

            br { }
            br { }

            label {
                +"Expiration Date:  "
            }

            styledInput(type = InputType.date, name = "licenseExpirationDate") {
                css {
                    color = Color.white
                    backgroundColor = Color.transparent
                    fontFamily = "Arial"
                    fontSize = 30.px
                }

                attrs {
                    val rawToDate = Date("${props.notary.licenseExpirationDate}")
                    // Parse UTC Date, i.e. Sat, 24 Oct 2020 18:46:09 GMT
                    val dateToArray = rawToDate.toUTCString().split(' ')
                    val day = dateToArray[1]
                    val month = DateUtils.getNumberedMonth(dateToArray[2])
                    val year = dateToArray[3]
                    value = "$year-$month-$day"
                    onChangeFunction = ::onLicenseExpirationDateChanged
                }
            }

            br { }

            label {
                +"Error Omission Insurance Amount:"
            }

            styledSelect {
                css {
                    color = Color.white
                    background = "transparent"
                    fontFamily = "Arial"
                    fontSize = 30.px
                    width = 140.px
                }

                attrs {
                    value = props.notary.errorOmissionInsuranceAmount
                    onChangeFunction = ::onErrorOmissionInsuranceAmountChanged
                }

                styledOption {
                    css {
                        backgroundColor = Color.gray
                        color = Color.white
                    }
                    +"25 K"
                }

                styledOption {
                    css {
                        backgroundColor = Color.gray
                        color = Color.white
                    }
                    +"50 K"
                }

                styledOption {
                    css {
                        backgroundColor = Color.gray
                        color = Color.white
                    }
                    +"75 K"
                }

                styledOption {
                    css {
                        backgroundColor = Color.gray
                        color = Color.white
                    }
                    +"100 K"
                }
            }

            br { }

            label {
                +"Years of Experience:  "
            }

            styledInput(type = InputType.number, name = "yearsOfExperience") {
                css {
                    color = Color.white
                    backgroundColor = Color.transparent
                    fontFamily = "Arial"
                    fontSize = 30.px
                    width = 100.px
                }

                attrs {
                    value = props.notary.experienceYears.toString()
                    onChangeFunction = ::onExperienceYearsChanged
                }
            }

            br { }
            br { }

            label {
                +"Score:  "
            }

            styledInput(type = InputType.number, name = "score") {
                css {
                    color = Color.white
                    backgroundColor = Color.transparent
                    fontFamily = "Arial"
                    fontSize = 30.px
                    width = 100.px
                }

                attrs {
                    value = props.notary.score.toString()
                    onChangeFunction = ::onScoreChanged
                }
            }

            br { }
        }
    }

    private fun onNameChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedName(value)
    }

    private fun onLastNameChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedLastName(value)
    }

    private fun onAddressChanged(event: Event) {
        val target = event.target as HTMLTextAreaElement
        val value = target.value

        this.props.onChangedAddress(value)
    }

    private fun onPhoneNumberChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedPhoneNumber(value)
    }

    private fun onEmailChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedEmail(value)
    }

    private fun onCompanyChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedCompany(value)
    }

    private fun onLicenseExpirationDateChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.valueAsDate

        this.props.onChangedLicenseExpirationDate(value as Date)
    }

    private fun onErrorOmissionInsuranceAmountChanged(event: Event) {
        val target = event.target as HTMLSelectElement
        val value = target.value

        this.props.onChangedErrorOmissionInsuranceAmount(value)
    }

    private fun onExperienceYearsChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value.toShort()

        this.props.onChangedExperienceYears(value)
    }

    private fun onScoreChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value.toShort()

        this.props.onChangedScore(value)
    }
}

fun RBuilder.notaryForm(handler: NotaryFormProps.() -> Unit): ReactElement {
    return child(NotaryForm::class) {
        this.attrs(handler)
    }
}