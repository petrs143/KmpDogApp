package cz.cvut.kmp.feature.fact.data.ktor

import cz.cvut.kmp.feature.fact.domain.Fact

class ApiFactMapper {

    fun map(apiFact: ApiFact): Fact {
        return Fact(
            text = apiFact.attributes.body
        )
    }
}
