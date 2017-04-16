web: target/universal/stage/bin/mafiatorten -Dhttp.port=${PORT}
-Dplay.evolutions.db.default.autoApply=true -Ddb.default.driver=org.postgresql.Driver -Ddb.default.url=${DATABASE_URL}
console: target/universal/stage/bin/mafiatorten -main scala.tools.nsc.MainGenericRunner -usejavacp
