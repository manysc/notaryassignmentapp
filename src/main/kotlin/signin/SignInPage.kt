package signin

import react.*

external interface SignInPageProps : RProps {
    var onSignedIn:  () -> Unit
}

external interface SignInPageState : RState {
    var pageMessage: String
    var pageErrorMessage: String

    var username: String
    var password: String
}

class SignInPage : RComponent<SignInPageProps, SignInPageState>() {
    override fun SignInPageState.init() {
        pageMessage = ""
        pageErrorMessage = ""

        username = ""
        password = ""
    }

    override fun RBuilder.render() {
        signInForm {
            pageMessage = state.pageMessage
            pageErrorMessage = state.pageErrorMessage
            username = state.username
            password = state.password

            onChangedUsername = { user ->
                setState {
                    username = user
                }
            }

            onChangedPassword = { pass ->
                setState {
                    password = pass
                }
            }

            onSignInClicked = {
                setState {
                    if(username == "Administrator" && password == "Administrator1") {
                        pageMessage = "Successfully logged in."
                        pageErrorMessage = ""
                        props.onSignedIn()
                    } else {
                        pageErrorMessage = "Invalid User, please check."
                        pageMessage = ""
                        password = ""
                    }
                }
            }
        }
    }
}

fun RBuilder.signInPage(handler: SignInPageProps.() -> Unit): ReactElement {
    return child(SignInPage::class) {
        this.attrs(handler)
    }
}