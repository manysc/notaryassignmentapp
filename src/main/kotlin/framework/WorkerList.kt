package framework

import kotlinx.css.borderBottom
import kotlinx.css.borderTop
import kotlinx.css.padding
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h2
import react.dom.thead
import react.dom.tr
import styled.css
import styled.styledTable
import styled.styledTd

external interface WorkerListProps<T : Worker> : RProps {
    var workers: List<T>
    var selectedWorker: T?
    var onSelectedWorker: (T) -> Unit
}

abstract class WorkerList<T : Worker, P : WorkerListProps<T>> : RComponent<P, RState>() {
    abstract val tableTittle: String

    override fun RBuilder.render() {

        div(classes = "formLeft") {
            h2 {
                +tableTittle
            }

            div(classes = "workerTable") {

                styledTable {
                    var count = props.workers.size

                    for (worker in props.workers.sortedWith(compareBy({ it.name }, { it.lastName }))) {
                        count--

                        thead {
                            tr {
                                key = worker.id.toString()
                                attrs {
                                    onClickFunction = {
                                        props.onSelectedWorker(worker)
                                    }
                                }

                                styledTd {
                                    if (count == 0) {
                                        css {
                                            borderTop = "1px solid white"
                                            borderBottom = "1px solid white"
                                            padding = "10px 10px"
                                        }
                                    } else {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"

                                        }
                                    }

                                    if (worker == props.selectedWorker) {
                                        css {
                                            borderTop = "5px solid white"
                                            borderBottom = "5px solid white"
                                        }
                                        +"▶ "
                                    }

                                    +worker.name
                                }
                                styledTd {
                                    if (count == 0) {
                                        css {
                                            borderTop = "1px solid white"
                                            borderBottom = "1px solid white"
                                            padding = "10px 10px"
                                        }
                                    } else {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"

                                        }
                                    }
                                    if (worker == props.selectedWorker) {
                                        css {
                                            borderTop = "5px solid white"
                                            borderBottom = "5px solid white"
                                        }
                                    }
                                    +worker.lastName
                                }
                                styledTd {
                                    if (count == 0) {
                                        css {
                                            borderTop = "1px solid white"
                                            borderBottom = "1px solid white"
                                            padding = "10px 10px"
                                        }
                                    } else {
                                        css {
                                            borderTop = "1px solid white"
                                            padding = "10px 10px"

                                        }
                                    }
                                    if (worker == props.selectedWorker) {
                                        css {
                                            borderTop = "5px solid white"
                                            borderBottom = "5px solid white"
                                        }
                                    }
                                    +worker.phoneNumber
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}