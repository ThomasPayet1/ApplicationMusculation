package com.ismin.android

class SalleMusculationL {
    private val storage = HashMap<String, SalleMusculation>()

    fun addSalleMusculation(salleMusculation: SalleMusculation) {
        storage[salleMusculation.id] = salleMusculation
    }

    fun getSalleMusculation(id: String): SalleMusculation {
        return storage[id] ?: throw Exception("Salle not found")
    }

    fun getAllSalleMusculation(): ArrayList<SalleMusculation> {
        return ArrayList(storage.values
            .sortedBy { salleMusculation -> salleMusculation.name })
    }
    

    fun getTotalNumberOfBooks(): Int {
        return storage.size
    }

    fun clear() {
        storage.clear()
    }
}