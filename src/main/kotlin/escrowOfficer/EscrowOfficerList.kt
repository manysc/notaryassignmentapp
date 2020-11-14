package escrowOfficer

import framework.WorkerList
import framework.WorkerListProps
import react.RBuilder
import react.ReactElement

external interface EscrowOfficerListProps : WorkerListProps<EscrowOfficer> {
    override var workers: List<EscrowOfficer>
    override var selectedWorker: EscrowOfficer?
    override var onSelectedWorker: (EscrowOfficer) -> Unit
}

class EscrowOfficerList : WorkerList<EscrowOfficer, EscrowOfficerListProps>() {
    override val tableTittle: String
        get() = "Escrow Officers"
}

fun RBuilder.escrowOfficerList(handler: EscrowOfficerListProps.() -> Unit): ReactElement {
    return child(EscrowOfficerList::class) {
        this.attrs(handler)
    }
}