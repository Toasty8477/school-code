rectangle: Rectangle.o RectangleDriver.o
	g++ Rectangle.o RectangleDriver.o -o a.out

rectangledriver.o: RectangleDriver.cpp Rectangle.h
	g++ RectangleDriver.cpp -o RectangleDriver.o

rectangle.o: Rectangle.cpp Rectangle.h
	g++ Rectangle.cpp -o Rectangle.o

clean:
	rm -f *.o

run:
	./a.out