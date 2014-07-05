package com.scorpio4.application.scorpio4.iq

import com.scorpio4.QUERY
import com.scorpio4.vocab.CORE

println "*"*80;
println "* Active Applications:"
println "*"*80;

addPhrase.with {
    list(   where("?this", "a", CORE.ASSETS+"Application").
            where("?this", CORE.LABEL, "?label").
            where("?this", CORE.FACT+"core", "?core").
            optional("?this", CORE.COMMENT, "?comment")
    ).each { app ->
        println ">> "+app.label.padRight(40)+app.get("this").padRight(40)+app.get("core");
        list(QUERY.APP_FINDERS,app).each {
            if (it.get("this")==app.get("core")) print "\t** ";
            else print "\t>> ";
            println it.label.padRight(40)+it.get("this");
        }
        println "-"*80;
    }
}
