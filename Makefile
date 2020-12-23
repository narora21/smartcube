path = src/java
heuristicspath = src/java/heuristics
mainpath = src/main

test:
	javac -Xlint -d classes/test $(mainpath)/Test.java $(path)/*.java $(heuristicspath)/*.java
build:
	javac -d classes/main $(mainpath)/Main.java $(path)/*.java $(heuristicspath)/*.java

run:
	java -cp classes/main Main

testrun: test
	java -cp classes/test -enableassertions Test

clean:
	rm -f classes/test/* classes/main/*