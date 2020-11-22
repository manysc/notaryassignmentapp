package signin

import kotlinx.css.*
import kotlinx.html.ButtonType
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.br
import react.dom.form
import react.dom.h3
import styled.css
import styled.styledButton
import styled.styledInput
import styled.styledLabel

external interface SignInFormProps : RProps {
    var pageMessage: String
    var pageErrorMessage: String

    var username: String
    var password: String

    var onChangedUsername: (String) -> Any
    var onChangedPassword: (String) -> Any
    var onSignInClicked:  () -> Unit
}

class SignInForm : RComponent<SignInFormProps, RState>() {
    override fun RBuilder.render() {
        form(classes = "formCenterLogin") {
            if (props.pageMessage.isNotEmpty() || props.pageErrorMessage.isNotEmpty()) {
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

                br {  }
                br {  }
            }

            h3 {
                +"Welcome back, please Sign In!"
            }

            styledInput(type = InputType.text, name = "username") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Username"
                    value = props.username
                    required = true
                    onChangeFunction = ::onUsernameChanged
                }
            }

            br {  }

            styledInput(type = InputType.password, name = "password") {
                css {
                    color = Color.white
                }

                attrs {
                    placeholder = "Password"
                    value = props.password
                    required = true
                    onChangeFunction = ::onPasswordChanged
                }
            }

            br {  }

            // Sign In Button
            styledButton {
                css {
                    fontFamily = "Arial"
                    fontStyle = FontStyle("normal")
                    fontSize = 30.px
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                    color = Color.white
                    padding = "10px 44%"
                    cursor = Cursor.pointer
                    whiteSpace = WhiteSpace.nowrap
                    hover {
                        backgroundColor = Color("#004d4d")
                    }
                }

                attrs {
                    type = ButtonType.button
                    text("Sign In")

                    onClickFunction = {
                        props.onSignInClicked()
                    }
                }
            }
        }
    }

    private fun onUsernameChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedUsername(value)
    }

    private fun onPasswordChanged(event: Event) {
        val target = event.target as HTMLInputElement
        val value = target.value

        this.props.onChangedPassword(value)
    }
}

fun RBuilder.signInForm(handler: SignInFormProps.() -> Unit): ReactElement {
    return child(SignInForm::class) {
        this.attrs(handler)
    }
}