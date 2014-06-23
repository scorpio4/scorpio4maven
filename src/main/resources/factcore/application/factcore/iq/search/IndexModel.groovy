package factcore.application.factcore.iq.search

def emails = core.list("SELECT DISTINCT ?this ?label ?comment WHERE { ?this rdfs:label ?label.?this a mail:Message. OPTIONAL {?this mail:body ?comment.}}",[:])
index.addAbility(emails);
println "Indexed ${emails.size()} Emails"

index.stop();
