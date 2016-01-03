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

e.g

`java Simulation 10000`

Above execution will generate 10000 riders. If command line arguments are not provided, 
riders will be initialized to default value which is 14300.

`java Simulation`

Above execution will initialize the riders to the default value.

The code will generate buses and riders randomly and the buses will be generated until
all the riders are finished. This ensures that the termination of the programme.