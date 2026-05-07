CXX = g++

CXXFLAGS = -Wall -std=c++17

TARGET = honk.out

all: $(TARGET)

$(TARGET): main.o road.o
	$(CXX) $(CXXFLAGS) main.o road.o -o $(TARGET)

main.o: main.cpp
	$(CXX) $(CXXFLAGS) main.cpp -c -o main.o

road.o: road.h road.cpp
	$(CXX) $(CXXFLAGS) road.cpp -c -o road.o

clean:
	rm -f *.o $(TARGET) output.txt

run:
	touch output.txt
	./$(TARGET)