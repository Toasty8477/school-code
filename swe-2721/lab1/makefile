all: compare

run:
	gradle spotlessApply
	gradle run --args='Lab1TestCases.txt out.txt'

compare: run
	./compareResults.sh Lab1TestCasesExpectedResults.txt out.txt
