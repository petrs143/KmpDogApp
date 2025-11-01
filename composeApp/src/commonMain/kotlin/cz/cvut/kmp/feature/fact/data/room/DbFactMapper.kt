package cz.cvut.kmp.feature.fact.data.room

import cz.cvut.kmp.core.database.entity.DbDogFact
import cz.cvut.kmp.feature.fact.domain.Fact

class DbFactMapper {

    fun map(dbDogFact: DbDogFact): Fact {
        return Fact(
            text = dbDogFact.fact
        )
    }

    fun map(fact: Fact): DbDogFact {
        return DbDogFact(
            fact = fact.text
        )
    }
}
