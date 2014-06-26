package com.factcore.application.factcore.iq

import com.factcore.vocab.CORE
println "*"*80;
println "*"*80;
println "~ ${setContainer.label} Booted ${setContainer.this}: @ "+new Date();
println "*"*80;
println "*"*80;

println "Installed Ontologies:"

addPhrase.with {
    list(   where("?this", "a", CORE.OWL+"Ontology").
            where("?this", CORE.LABEL, "?label").
            optional("?this", CORE.COMMENT, "?comment")
    ).each {
        println ">> "+it.label.padRight(40)+it.get("this");
    }
}
