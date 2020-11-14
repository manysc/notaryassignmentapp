package notary

import framework.WorkerList
import framework.WorkerListProps
import react.RBuilder
import react.ReactElement

external interface NotaryListProps : WorkerListProps<Notary> {
    override var workers: List<Notary>
    override var selectedWorker: Notary?
    override var onSelectedWorker: (Notary) -> Unit
}

class NotaryList : WorkerList<Notary, NotaryListProps>() {
    override val tableTittle: String
        get() = "Notaries"
}

fun RBuilder.notaryList(handler: NotaryListProps.() -> Unit): ReactElement {
    return child(NotaryList::class) {
        this.attrs(handler)
    }
}