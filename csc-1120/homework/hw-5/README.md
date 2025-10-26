[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/oNAaH6Nj)
# CSC1120 Homework 5

We are providing you with an implementation of a simplified `ArrayList<E>` that implements the `Java List<E>` interface. You will write the following public methods:

### toArray()

The `toArray()` method has no parameters and returns an `Object` array of the same size as the `ArrayList` that contains all the elements stored in the `ArrayList`, maintaining the identical order of the elements in the `ArrayList`.

### IndexOf(E target)

The `indexOf()` method takes a single parameter, *`target`*, of type `E` and returns the index of the first instance of *`target`*, or -1 if *`target`* does not exist in the `ArrayList`.

### middleToEnd()

The `middleToEnd()` method has no parameters and will take the middle element of the `ArrayList` and move it to the end of the list, shifting all the elements so there are no empty indices in the backing array. If there are less than 3 elements in the `ArrayList`, there will be no change. If there are an even number of elements (and more than 3), the middle element will be the higher middle index, so in a list of size 8, the middle would be index 4. The method will not return anything.
