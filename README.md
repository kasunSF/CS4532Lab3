# CS4532Lab3

##How to Run

#####Compile all the sources

```shell
cd CS4532Lab3
javac Simulation.java Bus.java BusHalt.java Rider.java
```

#####Run the Simulation

`java Simulation [number_of_riders] [number_of_buses]`

`number_of_riders` = Integer value that denotes number of riders.

`number_of_buses` = Integer value that denotes number of buses.

e.g

`java Simulation 10000 500`

Above execution will generate 10000 riders and 500 buses. If command line arguments
are not provided, riders and buses will be initialized to default values which are
14300 and 386 respectively.

`java Simulation`

Above execution will initialize the riders and buses to default values.