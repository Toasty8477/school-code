CXX = g++

FLAGS = -Wall -std=c++17

TARGET = cow.out

all: $(TARGET)

$(TARGET): main.o generic_string.o cow_string.o
	$(CXX) $(FLAGS) main.o generic_string.o cow_string.o -o $(TARGET)

main.o: main.cpp
	$(CXX) $(FLAGS) main.cpp -c -o main.o

generic_string.o: generic_string.cpp generic_string.h
	$(CXX) $(FLAGS) generic_string.cpp -c -o generic_string.o

cow_string.o: cow_string.cpp cow_string.h
	$(CXX) $(FLAGS) cow_string.cpp -c -o cow_string.o

clean:
	rm -f *.o $(TARGET) output.txt

run:
	./$(TARGET) > output.txt
# 	touch output.txt
# 	./$(TARGET) < input1.txt > output.txt
# 	diff -c output1.txt output.txt --color