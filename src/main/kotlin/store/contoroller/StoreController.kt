package store.contoroller

import store.model.Storage
import store.view.OutputView

class StoreController {
    fun run() {
        OutputView.showWelcome()

        val storage = Storage.initStorage()
    }
}