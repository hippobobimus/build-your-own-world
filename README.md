# Overview

A random dungeon generator built as the final project in the UC Berkeley course CS61B.

# Implementation of Procedural World Generation

The world space is divided into a grid of tiles. During the below processes a graph that contains
all tiles on the user navigable pathway is maintained, as well as a union find data structure to
track regions of connected pathway tiles.

#### Stage 1 - Room Placement

A number of attempts are made to place randomly sized rectangular rooms at random locations in the
world. If the placement of the room would overlap another room then the attempt fails and the room
is discarded.  A limit is placed on the total number of attempts and, when reached, this stage is
complete. The set imit for the total number of attempts will determine the typical room density.

#### Stage 2 - Maze Fill

A randomised version of Prim's algorithm is used to fill all the available free space surrounding
the rooms with mazes by creating a spanning tree between all empty tiles and filling it with floor
tiles as it is built.

First a valid start point is determined, somewhere a floor tile surrounded by wall tiles can be
placed. Then adjacent tiles in cardinal directions are evaluated to determine whether they
are possible onward paths. Valid candidates found are added to a priority queue with a randomly
generated priority.

The algorithm then progresses by taking the tile at the head of the priority queue and, if it is
still a valid onward path, extending the maze into this tile. The cardinal neighbours of this tile
are then evaluated and added to the priority queue if they are valid onward paths.

The algorithm repeats in this way until the queue is empty. The world is then searched for a new
maze start point from which another maze can be built. When no more start points can be found, the
maze fill is complete.

#### Stage 3 - Bridging

There are now a series of unconnected rooms and mazes that fill all the available space. In this
next step, all wall tiles that are adjacent to two unconnected regions are found. These are the
regions that have been tracked by the previously mentioned union find data structure and the tiles
found are the potential bridging points between these regions.

The list of bridge candidates is shuffled and the candidate at the head of the list is removed. It
is checked to make sure it is still a valid bridging point and, if so, converted into a floor tile,
connecting the regions. In addition, with a certain set probability a candidate _may_ be converted
even if it is no longer valid. This allows for multiple connections between regions.

Subsequently another candidate is selected and the process repeats until the candidate list is
empty, at which point all regions will be fully connected.

#### Stage 4 - Retracting Dead-ends

Finally dead-ends are removed to increase the sparseness of the maze. Dead-ends are leaf nodes in
the pathway graph and they are incrementally removed, retracting dead-end corridors back a step at
a time. The number of retraction steps is fixed and represents the volume of dead-ends desired.

# Running

After compilation, run the `Main.java` file located in the `byow/Core` directory.

On starting a new game, you will be prompted to enter a numeric value. This will be used to seed the
random number generator that powers elements of the procedural generation algorithm.

Subsequently you will see an animation of the level being generated that represents a visualisation
of the algorithm steps. The animation can be disabled within the settings menu before starting a
new game to speed up the level load process significantly.

The main objective I had was to understand procedural generation. Therefore, gameplay itself is
limited and simply allows the player to move a chicken avatar around the level, eating randomly
placed pieces of corn.

Controls:

- ':' - Menu
- 'w' - Move up
- 'a' - Move left
- 's' - Move down
- 'd' - Move right

# Credits

The algorithm implementation was inspired by the description in this blog post from Bob Nystrom:

http://journal.stuffwithstuff.com/2014/12/21/rooms-and-mazes/

The project outline and tile renderer are taken from the UC Berkeley course CS61B, project 3:

https://sp21.datastructur.es/materials/proj/proj3/proj3
