CXX = g++

CXXFLAGS = -Wall -std=c++17

TARGET = meetup.out

all: $(TARGET)

$(TARGET): main.o pool.o
	$(CXX) $(CXXFLAGS) main.o pool.o -o $(TARGET)

main.o: main.cpp
	$(CXX) $(CXXFLAGS) main.cpp -c -o main.o

pool.o: pool.h pool.cpp
	$(CXX) $(CXXFLAGS) pool.cpp -c -o pool.o

clean:
	rm -f *.o $(TARGET) output.txt

run:
	touch output.txt
	./$(TARGET) < input1.txt > output.txt
	diff -c output1.txt output.txt --color