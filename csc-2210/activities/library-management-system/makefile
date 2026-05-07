CXX = g++

CXXFLAGS = -Wall -std=c++17

TARGET = library.out

all: $(TARGET)

# This is the only way main will compile
# Compiling main by itself results in function reference errors from the linker
$(TARGET): main.cpp Library.o Book.o
	$(CXX) $(CXXFLAGS) main.cpp Library.o Book.o -o $(TARGET)

library.o: Library.cpp Library.h
	$(CXX) $(CXXFLAGS) Library.cpp -o Library.o

book.o: Book.cpp Book.h
	$(CXX) $(CXXFLAGS) Book.cpp -o Book.o


clean:
	rm -f *.o $(TARGET)

run:
	./$(TARGET)