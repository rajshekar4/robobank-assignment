call mvn clean install -Dmaven.test.failure.ignore=true
call echo "test"
call java -jar target/rabobank-0.0.1-SNAPSHOT.jar