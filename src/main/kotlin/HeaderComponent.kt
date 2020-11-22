import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.h1
import react.dom.link
import react.dom.title
import styled.css
import styled.styledButton
import styled.styledDiv
import styled.styledImg

external interface HeaderProps : RProps {
    var showSignOutButton: Boolean

    var onSignOutClicked: () -> Unit
}

class HeaderComponent : RComponent<HeaderProps, RState>() {

    override fun RBuilder.render() {
        link(href = "styles/styles.css", rel = "stylesheet") { }
        link(href = "styles/buttonStyles.css", rel = "stylesheet") { }
        link(href = "styles/formStyles.css", rel = "stylesheet") { }
        link(href = "styles/tableStyles.css", rel = "stylesheet") { }

        // Webpage Title
        title {
            +"Notary Assignment System"
        }

        // Header Logo
        styledImg(src = "images/fill_sign_logo.png") {
            css {
                width = 175.px
                height = 125.px
                float = kotlinx.css.Float.left
            }
        }

        // Header Title
        styledDiv {
            css {
                float = kotlinx.css.Float.left
                fontFamily = "Segoe Print"
                fontStyle = FontStyle("normal")
                fontSize = 23.px
            }

            h1 {
                +"Notary Assignment System"
            }
        }

        if (props.showSignOutButton) {
            // Signout Button
            styledButton {
                css {
                    float = kotlinx.css.Float.right
                    fontFamily = "Arial"
                    fontStyle = FontStyle("normal")
                    fontSize = 25.px
                    background = "-webkit-radial-gradient(circle, #1a82f7, #2F2727)"
                    color = Color.white
                    padding = "8px 8px"
                    cursor = Cursor.pointer
                    width = 7.pct
                    height = 9.pct
                    borderRadius = 5.px
                    marginTop = 15.px
                    marginRight = 30.px
                    whiteSpace = WhiteSpace.nowrap
                    hover {
                        backgroundColor = Color("#004d4d")
                    }
                }

                attrs {
                    text("Sign Out")

                    onClickFunction = {
                        props.onSignOutClicked()
                    }
                }
            }
        }
    }

}

fun RBuilder.header(handler: HeaderProps.() -> Unit): ReactElement {
    return child(HeaderComponent::class) {
        this.attrs(handler)
    }
}