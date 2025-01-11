javac -d out src/*.java
jar cfm Clock.jar META-INF/MANIFEST.MF -C out .
java -jar Clock.jar
