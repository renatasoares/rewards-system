# Reward System

* [Project Organization](https://gitlab.com/renatasoares/reward-system#project-organization)

* [Principal Algorithm](https://gitlab.com/renatasoares/reward-system#principal-algorithm)

* [Paralellism With Future](https://gitlab.com/renatasoares/reward-system#paralellism-with-future)

* [Execute](https://gitlab.com/renatasoares/reward-system#execute)

* [Usage Examples](https://gitlab.com/renatasoares/reward-system#usage-examples)

* [Test](https://gitlab.com/renatasoares/reward-system#test)


# Project Organization

The project is divided in the five namespaces below:

* __Adapter__

Functions that intermediate input and output with the logic functions. 

* __Data__

 Functions related to insert and manage data.

* __Logic__

Functions that compose the main logic of reward system.

* __Handler__

Routes of endpoint to receive input and/or show output.

* __Utility__

Utility functions that are needed and used in others namespaces.

# Principal Algorithm

The main function is __aux-bfs__ that is a recursive function that explore nodes by theirs levels. Is a implementation of the breadth-first search (BFS) algorithm for traversing the graph.

Detailed flow of input: 

1 2

1 3

3 4

2 4

4 5

4 6

The graph below represents the data structured used in the solution. Note that inviteds and confirmed-inviteds are defined before by using sets in insertion (people that invited someone).
 
![Graph](https://gitlab.com/renatasoares/reward-system/wikis/graph.png)

The states used are:

* __Levels__: A hash-map of levels of each node.

* __Visited__: Nodes that are already been visiteds in the tranversing algorithm.

* __Adjacent__: Nodes to be visited in a queue.

* __Vertice__: Current node.

* __Inviteds__: Nodes directly connected to vertice.

* __Confirmed-inviteds__: Quantity of inviteds that are confirmed-inviteds.

* __Level__: Level of vertice.

* __Points__: Intermediate points of vertice calculated by (1/2)^level * number of confirmed inviteds.

## Iteration 1
![Graph](https://gitlab.com/renatasoares/reward-system/wikis/iteration1.png)

## Iteration 2
![Graph](https://gitlab.com/renatasoares/reward-system/wikis/iteration2.png)

## Iteration 3
![Graph](https://gitlab.com/renatasoares/reward-system/wikis/iteration3.png)

## Iteration 4
![Graph](https://gitlab.com/renatasoares/reward-system/wikis/iteration4.png)

## Iteration 5
![Graph](https://gitlab.com/renatasoares/reward-system/wikis/iteration5.png)

## Iteration 6
![Graph](https://gitlab.com/renatasoares/reward-system/wikis/iteration6.png)

## Result

Points of __1__ = 2 + 0.5 + 0.0 + 0.0 + 0.0 + 0.0 = __2.5__

To calcute rewards points of other nodes is necessary to call __aux-bfs__ with the target node being the start-point.

# Paralellism With Future

The run function in reward-system.adapter use future to to parallelize the count of node reward points. And use deref to wait until all futures are done to show result. Since ranking is an atom (thread safe and retriable), the use of future has no damage to the result consistency.

The old-run function in reward-system.adapter count the node reward points sequentially.

## Times of executation using future and doing sequentially

### Sample-in (Input Located at "resources/sample-in" in the project that have 6 lines)

#### Parallel

![Using Run](https://gitlab.com/renatasoares/reward-system/wikis/run-2.png)

#### Sequential

![Using Old-run](https://gitlab.com/renatasoares/reward-system/wikis/old-run-2.png)

### In (Input Located at "resources/in" in the project that have 200 lines)

#### Parallel

![Using Run](https://gitlab.com/renatasoares/reward-system/wikis/run.png)

#### Sequential

![Using Old-run](https://gitlab.com/renatasoares/reward-system/wikis/old-run.png)


Therefore, the parallelism technique has more performance gain with larger inputs.

# Execute

$ lein ring server

# Usage Examples

### Home page

$ curl http://localhost:3000/

### Get Ranking

$ curl http://localhost:3000/ranking

### Insert a file containg the input

$ curl -H "Content-Type: application/json" -X POST -d '{"path": "__path/to/file__"}' http://localhost:3000/insert/file

#### Example

$ curl -H "Content-Type: application/json" -X POST -d '{"path": "resources/sample-in"}' http://localhost:3000/insert/file

### Insert 

THe inviter and invited value must be strings representing numbes like "1".

$ curl -H "Content-Type: application/json" -X POST -d '{"inviter": "__inviter__", "invited": "__invited__"}' http://localhost:3000/insert

#### Example

$ curl -H "Content-Type: application/json" -X POST -d '{"inviter": "1", "invited": "2"}' http://localhost:3000/insert


# Test

$ lein midje