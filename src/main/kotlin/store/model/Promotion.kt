package store.model

import java.util.Date

data class Promotion(val name: String, val buy: Int, val get: Int, val startDate: Date, val endDate: Date) {

    override fun toString(): String {
        if (name == "null") {
            return ""
        }
        return name
    }
}