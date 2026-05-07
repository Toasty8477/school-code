# Simulating LOOOOOOOOOONG Trains

>Using the FLyweight Design Pattern, we attempted to reduce the memory used for a
>program that simulates running long trains.

### Group Members and Responsibilities
- Alex Horton: Created Path Generation Algorithm; Resource monitor, Implemented FlyweightFactory
- Noah Dinan:  Created the Traincar, Train; Implemented ConcreteFlyweight

### Repository Breakdown

| File                                   | Purpose     |
| -------------------------------------- | ----------- |
| src/finalproject/ConcreteFlyweight.java| Implements the Flyweight interface and stores any intrinsic state (image, path) |
| src/finalproject/Flyweight.java        | Specifies the instrinsic behavior (movement) |
| src/finalproject/FlyweightFactory.java | Acts as a singleton, creates and provides a flyweight |
| src/finalproject/Main.java             | Entrypoint, launches the JavaFX Application |
| src/finalproject/PathGenerator.java    | Generates a path |
| src/finalproject/PathNode.java         | A Node in the generated path |
| src/finalproject/Train.java            | A train; stores an array of traincars |
| src/finalproject/Traincar.java         | A traincar; stores position and rotation |

### Running the project
We used gradle to manage dependencies and run our code. Our final implementation is
on the `flyweight` branch.

```bash
git switch flyweight
gradle run
```
